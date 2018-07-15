FROM jenkins/jenkins:lts-alpine
LABEL maintainer=mch1307@gmail.com 
# Build: docker build . --build-arg UCPPWD=  --build-arg UCPUSER=  --build-arg UCPURL= --build-arg LDAP_GROUP=
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ARG UCPURL
ARG UCPUSER
ARG UCPPWD
ARG LDAP_GROUP
ENV LDAP_GROUP=$LDAP_GROUP

USER root

RUN curl -sk https://${UCPURL}/ca > /usr/local/share/ca-certificates/ucp.crt && \
    update-ca-certificates && \
    cp /usr/local/share/ca-certificates/ucp.crt $JAVA_HOME/jre/lib/security/ucp.crt && \
    cd $JAVA_HOME/jre/lib/security && \
    keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias ucpcert -file ucp.crt

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
