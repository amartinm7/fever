FROM openjdk:19

MAINTAINER amartinm7@gmail.com

COPY build/libs/fever*.jar /usr/local/fever/fever.jar

WORKDIR /usr/local/fever

ENTRYPOINT ["java","-jar","fever.jar", "--spring.config.name=application", "--spring.profiles.active=dev"]


