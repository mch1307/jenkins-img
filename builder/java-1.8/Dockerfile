FROM centos:7.5.1804

ARG user=jenkins
ARG group=jenkins
ARG uid=1000
ARG gid=1000
ARG http_port=8080
ARG agent_port=50000
ENV JENKINS_HOME=/home/jenkins
ENV JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk
ENV M2_HOME=/opt/apache-maven-3.5.4

USER root

RUN rm -f /etc/localtime && ln -s /usr/share/zoneinfo/Europe/Brussels /etc/localtime  \
  && yum -y update \ 
  && yum -y install \
   java-1.8.0-openjdk \
   java-1.8.0-openjdk-devel \
   git \
   which \
  && yum -y clean all \
  && curl -sk http://apache.belnet.be/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz > apache-maven-3.5.4-bin.tar.gz \
  && tar xzvf apache-maven-3.5.4-bin.tar.gz -C /opt \
  && rm apache-maven-3.5.4-bin.tar.gz \
  && ln -s /opt/apache-maven-3.5.4/bin/mvn /usr/local/bin/mvn 
