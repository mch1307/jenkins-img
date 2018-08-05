FROM jenkins:2.121.2-centos
LABEL maintainer=mch1307@gmail.com 
# Build: docker build . --build-arg UCPPWD=  --build-arg UCPUSER=  --build-arg UCPURL= --build-arg LDAP_GROUP=
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ENV UCPURL=$UCPURL
ARG UCPUSER
ARG UCPPWD
ARG LDAP_GROUP

USER root

RUN yum install -y  \
        device-mapper-persistent-data \
        lvm2 \
        openssl \
        unzip \
        yum-utils

RUN curl -sk https://${UCPURL}/ca > /etc/pki/ca-trust/source/anchors/ucp-ca.crt && \
    update-ca-trust extract && \
    #cp /etc/pki/ca-trust/source/anchors/ucp-ca.crt $JAVA_HOME/jre/lib/security/ucp-ca.crt && \
    #cd $JAVA_HOME/jre/lib/security && \
    openssl x509 -in /etc/pki/ca-trust/source/anchors/ucp-ca.crt -inform pem -out ucp-ca.der -outform der && \
    keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias ucpcert -file ucp-ca.der && \
    curl -sk https://${UCPURL}/linux/docker > /usr/local/bin/docker && \
    chmod +x /usr/local/bin/docker

USER jenkins

COPY init.groovy/*.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt

RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

ENTRYPOINT ["gvs &&","/sbin/tini", "-s", "--", "/usr/local/bin/jenkins.sh"]

