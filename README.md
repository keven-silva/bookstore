# bookstore
The bookstore project has two entities, including book and person, which may or may not have a relationship with each other. 
The project has swagger and unit tests, in addition to having a docker file for the database.

## Dependencies
- Java 17
- Spring Boot 4
- Maven 4.0
- Docker

## Functionalities
- Pagination and sorting
- Jwt token authentication
- Swagger doc with authentication
- Caching
- Automated testings

## Getting starter
Run database in file docker-compose

    docker-compose up -d build

## Run application
    mvn spring-boot:run
