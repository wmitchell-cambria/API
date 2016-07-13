FROM vinodtaborda/centos:java8u91
RUN mkdir /opt/cws-api
COPY config/api.yml /opt/cws-api
COPY build/libs/api.jar /opt/cws-api
EXPOSE 8080
WORKDIR /opt/cws-api
CMD ["java', "-jar", "api.jar","server","api.yml"]
