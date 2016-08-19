# CWDS API

The CWDS API provides RESTful services for the CWDS Modules.

## Wiki 

The development team is actively using the [Github Wiki](https://github.com/ca-cwds/API/wiki).  

## Configuration

The CWDS API requires the following environment variables:

- DB_USER -- the database username
- DB_PASSWORD -- the database password
- DB_JDBC_URL -- the database URL in Java Database Connectivity format

Further configuration options are available in the file config/api.yml.

## Installation

### Prerequisites

1.  DB2 10.x

### Using Docker

## Development Environment

### Prerequisites

1. Source code, available at GitHub
1. Java SE 8 development kit

### Building

### Database Initialization

### Database Seeding  

Use your favorite database tool to execute the SQL files in src/main/resources/db/seeds.

### Development Server

Use the gradlew command to execute the run task:

    % ./gradlew run

This will run the server on your local machine, port 8080.

### Testing

Use the gradlew command to execute the test task:

    % ./gradlew test

### Building Docker Container

