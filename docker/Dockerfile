#################################################
#   Docker file Social Semantic Enricher      ###
#################################################

FROM centos:7

# Initial update and java 8 installation
RUN yum update -t -y && \
    yum upgrade -y && \
    yum autoremove -y && \
    yum install -y wget


# JAVA 8 installation
RUN wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u65-b17/jdk-8u65-linux-x64.rpm

RUN rpm -ivh jdk-8u65-linux-x64.rpm

ENV JAVA_HOME /usr/jdk1.8.0_65
ENV PATH $PATH:$JAVA_HOME/bin


# Retrieve sse 
ADD https://github.com/FiwareTIConsoft/SSECore/releases/download/0.1-bin/ssecore-install-package.noarch.rpm  /tmp

RUN rpm -ivh /tmp/ssecore-install-package.noarch.rpm

RUN ["/etc/init.d/ssecore","stop"]

# Expose port
EXPOSE 8895 

COPY ./ssecore.sh /ssecore.sh


ENTRYPOINT ["/ssecore.sh"]
