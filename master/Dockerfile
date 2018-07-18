FROM dtr.csnet.me/dkradmin/jenkins:2.121.2
LABEL maintainer=mch1307@gmail.com 
# Build: docker build . --build-arg UCPPWD=  --build-arg UCPUSER=  --build-arg UCPURL= --build-arg LDAP_GROUP=
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ARG UCPURL
ARG UCPUSER
ARG UCPPWD
ARG LDAP_GROUP
ENV LDAP_GROUP=$LDAP_GROUP

USER root

RUN yum install -y  \
        device-mapper-persistent-data \
        lvm2 \
        openssl \
        unzip \
        yum-utils && \
    yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo && \
    yum -y install docker-ce

RUN curl -sk https://${UCPURL}/ca > /etc/pki/ca-trust/source/anchors/ucp-ca.crt && \
    update-ca-trust extract && \
    #cp /etc/pki/ca-trust/source/anchors/ucp-ca.crt $JAVA_HOME/jre/lib/security/ucp-ca.crt && \
    #cd $JAVA_HOME/jre/lib/security && \
    openssl x509 -in /etc/pki/ca-trust/source/anchors/ucp-ca.crt -inform pem -out ucp-ca.der -outform der && \
    keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias ucpcert -file ucp-ca.der

USER jenkins

COPY init.groovy/*.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt

RUN cd /tmp && echo curl -sk -d '{"username":"'$UCPUSER'","password":"'${UCPPWD}'"}' https://${UCPURL}/auth/login | awk -F '[":]' '{print $5}' && \
    AUTHTOKEN=$(curl -sk -d '{"username":"'$UCPUSER'","password":"'${UCPPWD}'"}' https://${UCPURL}/auth/login | awk -F '[":]' '{print $5}') && \
    curl -k -H "Authorization: Bearer $AUTHTOKEN" https://${UCPURL}/api/clientbundle -o bundle.zip && \
    unzip bundle.zip && \
    echo $LDAP_GROUP > ldap_group && \
    /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt && \
    rm -rf /tmp/ca.cert    
