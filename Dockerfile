FROM vinodtaborda/centos:java8u91
RUN mkdir /opt/cws-api
ADD ./ /opt/cws-api
EXPOSE 8080
WORKDIR /opt/cws-api
CMD ["./gradlew", "run"]
