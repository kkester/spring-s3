# spring-s3

## About the project

This project is a sample Spring boot application that showcases the following technologies.
- Swagger UI API Documentation leveraging Open API
- Simple RESTful APIs leveraging Spring Starter Web
- S3 integration leveraging Minio
- PostGRES database integration leveraging flyway
- Including S3 connection status in actuator health response details.

## Running the application

### Required Software

1. [Install Homebrew](https://brew.sh/)
2. [Install Docker](https://docs.docker.com/get-docker/)
3. <details>
    <summary>Java</summary>

    ```shell script
    brew update
    brew install openjdk@17
    ```
    </details>

### Start dependent services

Run `docker-compose up -d` to start Postgres and Minio(S3) containers.

Verify [Minio](http://localhost:9001) by logging in with admin/password and create a bucket named `sample`.

### Start the Spring Boot application

By default, the application runs at [http://localhost:8080](http://localhost:8080)

Run your application using the `./gradlew bootRun` command.

Verify everything is working by performing the following
1. Open `http://localhost:8080/actuator/health` in a browser window
2. Type `http://localhost:8080/swagger-ui.html` in the same browser tab and validate that you receive the `Swagger UI Api Documentation` page. 

## Running Tests

| Tests to Run       | Command(s)                                         |
|:-------------------|:---------------------------------------------------|
| Backend            | Run `./gradlew test` in the project root directory |