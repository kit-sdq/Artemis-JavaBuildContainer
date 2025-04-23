FROM maven:3-eclipse-temurin-24

MAINTAINER Dominik Fuchss <dominik.fuchss@kit.edu>

RUN apt-get update && apt-get install -y gnupg && rm -rf /var/lib/apt/lists/*

ENV M2_HOME="/usr/share/maven"
ENV MAVEN_OPTS="-Dmaven.repo.local=/repo"

RUN echo "$LANG -- $LANGUAGE -- $LC_ALL" \
    && curl --version \
    && gpg --version \
    && git --version \
    && mvn --version \
    && java --version \
    && javac --version \
    && mkdir /repo && chmod 777 /repo


ADD . /opt/java-template
RUN cd /opt/java-template && pwd && ls -la && mvn clean install test && cd / && rm -rf /opt/java-template && chmod -R 777 /repo

CMD ["mvn"]
