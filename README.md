# spring-s3

## About the project

This project is a headless reference application based on the tesseract [spring-starter](https://gitlab.create.army.mil/tesseract/starter/spring-starter) template that showcases the following technologies.
- Swagger UI API Documentation leveraging Open API
- Reactive RESTful APIs leveraging Spring Starter Weblux
- PostGRES database integration leveraging R2DBC.

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

Run `docker-compose up -d` to start Postgres container.

[Minio](http://localhost:9001)

### Start the Spring Boot application

By default, the application runs at [http://localhost:8080](http://localhost:8080) with the `keycloak` profile.

Run your application using the `IDP_DOMAIN=http://keycloak:10000 ./gradlew bootRun` command.

Verify everything is working by performing the following
1. Open `http://localhost:8888` in an incognito window
2. Accept T&Cs and login to keycloak using username `user` with password `password`.  This should result in landing on a `404` whitelabel error page.
3. Type `http://localhost:8888/swagger-ui/index.html` in the same browser tab and validate that you receive the `Swagger UI Api Documentation` page. 

## Running Tests

| Tests to Run       | Command(s)                                         |
|:-------------------|:---------------------------------------------------|
| Backend            | Run `./gradlew test` in the project root directory |