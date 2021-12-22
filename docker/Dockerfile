# https://question-it.com/questions/6661363/konfiguratsija-klastera-activemq-artemis-na-mnogih-kontejnerah-docker
FROM openjdk:8-alpine
# ubuntu:latest
# AS msg_with_script

ARG ARTEMIS_USER
ARG ARTEMIS_PASSWORD

ENV ARTEMIS_USER ${ARTEMIS_USER}
ENV ARTEMIS_PASSWORD ${ARTEMIS_PASSWORD}

RUN mkdir /opt/jms && mkdir /var/lib/broker-instance; \
    chown -R 1000:1000 /var/lib/broker-instance

WORKDIR /opt/jms

# RUN apt-get update
#RUN apt-get -y install wget
#RUN apt-get -y install openjdk-11-jdk
#RUN java --version

COPY ./apache-artemis-2.19.0-bin.tar.gz /tmp

RUN tar -xvf /tmp/apache-artemis-2.19.0-bin.tar.gz -C . && \
    ln -s ./apache-artemis-2.19.0/ ./activemq && \
    rm /tmp/apache-artemis-2.19.0-bin.tar.gz

# Web Server
EXPOSE 8161 \
# JMX Exporter
    9404 \
# Port for CORE,MQTT,AMQP,HORNETQ,STOMP,OPENWIRE
    61616 \
# Port for HORNETQ,STOMP
    5445 \
# Port for AMQP
    5672 \
# Port for MQTT
    1883 \
#Port for STOMP
    61613

# USER artemis

# Expose some outstanding folders
# VOLUME ["/var/lib/broker-instance"]

RUN ./activemq/bin/artemis create /var/lib/broker-instance \
        --user ${ARTEMIS_USER} \
        --password ${ARTEMIS_PASSWORD} \
        --http-host 0.0.0.0 \
        --relax-jolokia \
        --require-login; \
    chown -R 1000:1000 /var/lib/broker-instance/data

WORKDIR /var/lib/broker-instance/bin

ENTRYPOINT ["./artemis", "run"]
