FROM cwds/java
RUN mkdir /opt/cws-api
ADD config/api.yml /opt/cws-api/api.yml
ADD build/libs/api.jar /opt/cws-api/api.jar
EXPOSE 8080
WORKDIR /opt/cws-api
CMD ["java", "-jar", "api.jar","server","api.yml"]
