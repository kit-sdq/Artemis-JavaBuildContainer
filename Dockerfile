# Explicitly use the image that is based on Ubuntu 20.04 Focal, as the newer Ubuntu release includes an updated glibc that does not work with older Docker hosts.
FROM maven:3-eclipse-temurin-17-focal

MAINTAINER Dominik Fuchss <dominik.fuchss@kit.edu>

RUN apt-get update && apt-get install -y gnupg && rm -rf /var/lib/apt/lists/*
 
ENV M2_HOME /usr/share/maven

RUN echo "$LANG -- $LANGUAGE -- $LC_ALL" \
    && curl --version \
    && gpg --version \
    && git --version \
    && mvn --version \
    && java --version \
    && javac --version
    

ADD . /opt/java-template
RUN cd /opt/java-template && pwd && ls -la && mvn clean install test && mvn spotbugs:spotbugs checkstyle:checkstyle pmd:pmd && cd / && rm -rf /opt/java-template

CMD ["mvn"]
