FROM jenkins/jenkins:lts-alpine
LABEL maintainer=mch1307@gmail.com 
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ENV UCPURL="https://ucp.csnet.me" 

USER root

RUN (curl -sk ${UCPURL}/ca > /usr/local/share/ca-certificates/ucp.crt && \
    update-ca-certificates && \
    cp /usr/local/share/ca-certificates/ucp.crt $JAVA_HOME/jre/lib/security/ucp.crt && \
    cd $JAVA_HOME/jre/lib/security && \
    keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias ucpcert -file ucp.crt && \
    rm -rf /home/jenkins)
USER jenkins

COPY *.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt

RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
