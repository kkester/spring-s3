# spring-s3

## About the project

This project is a sample Spring boot application that showcases the following technologies.
- Swagger UI API Documentation leveraging Open API
- Simple RESTful APIs leveraging Spring Starter Web
- S3 integration leveraging Minio
- PostGRES database integration leveraging flyway
- Including S3 connection status in actuator health response details.
- Project leverages `spring-boot-docker-compose`

## Running the application

### Required Software

1. [Install Docker](https://docs.docker.com/get-docker/)
1. OpenJdk17

### Start the Spring Boot application

Execute `./gradle bootRun`.  This will launch the application and startup dependent services.

By default, the application runs at [http://localhost:8080](http://localhost:8080)

Run your application using the `./gradlew bootRun` command.

Verify everything is working by performing the following
1. Open [http://localhost:8081/actuator/health](http://localhost:8081/actuator/health) in a browser window
2. Type [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) in the same browser tab and validate that you receive the `Swagger UI Api Documentation` page. 

## Running Tests

| Tests to Run       | Command(s)                                         |
|:-------------------|:---------------------------------------------------|
| Backend            | Run `./gradlew test` in the project root directory |