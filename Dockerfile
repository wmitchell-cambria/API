FROM cwds/javajdk
RUN mkdir /opt/cws-api
RUN mkdir /opt/cws-api/logs
ADD config/api.yml /opt/cws-api/api.yml
ADD build/libs/api-dist.jar /opt/cws-api/api-all.jar
EXPOSE 8080
WORKDIR /opt/cws-api
CMD ["java", "-jar", "api-all.jar","server","api.yml"]
