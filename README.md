# CWDS API

The CWDS API provides RESTful services for the CWDS Modules.

## Wiki 

The development team is actively using the [Github Wiki](https://github.com/ca-cwds/API/wiki).  

## Configuration

The CWDS API requires the following environment variables:

- DB_USER -- the database username
- DB_PASSWORD -- the database password
- DB_JDBC_URL -- the database URL in Java Database Connectivity format

The Docker env-file option provides a convenient method to supply these variables. These instructions assume an env file called .env located in the current directory. The repository contains a sample env file called env.sample.

Further configuration options are available in the file config/api.yml.

## Installation

### Prerequisites

1.  DB2 10.x

### Using Docker

The CWDS API is available as a Docker container on Dockerhub:

    https://hub.docker.com/r/cwds/api/

Run the application with Docker using a command like this:

    % docker run --env-file=.env -p 8080:8080 cwds/api

## Development Environment

### Prerequisites

1. Source code, available at [GitHub](https://github.com/ca-cwds/API)
1. Java SE 8 development kit
1. DB2 Database

### Database 

#### Docker
A [Docker Image](https://hub.docker.com/r/cwds/db2/) with DB2 is available to develop against.  The database server running in the container does not contain a database, to create one attach the container and create the database:
    
    % docker attach container_name
    % su - db2inst1
    % db2 create database DB0TDEV using CODESET ISO-8859-1 TERRITORY US

#### Initialization
The CWDS API database must be initialized prior to starting the first time. To initialize, execute the
following commands:

    % ./gradlew shadowJar
    % java -jar build/libs/api-all.jar db clean config/api.yml

The first time the application starts it will initialize the database schema during the boot process.

### Database Migrations

The CWDS API uses [Flyway](https://flywaydb.org/) for handling database migrations.  To update your database to the latest version run the following:

   % java -jar build/libs/api-all.jar db migrate config/api.yml
   
### Database Seeding  

Use your favorite database tool to execute the SQL files in src/main/resources/db/seeds.

### Development Server

Use the gradlew command to execute the run task:

    % ./gradlew run

This will run the server on your local machine, port 8080.

### Unit Testing

Use the gradlew command to execute the test task:

    % ./gradlew test

### Integration Testing
Tests that access the database utilize the src/test/resources/hibernate.cfg.xml configuration file. Edit this file to utilize a local testing database.

Use the gradlew command to execute the test task:

    % ./gradlew integrationTest

### Building Docker Container

The continuous delivery server builds images for the container registry but developers can build local containers with
the following command:

    % docker build -t api .

This results in a local docker image in a repository called api with the tag latest.
