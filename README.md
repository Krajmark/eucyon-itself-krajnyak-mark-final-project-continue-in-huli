# E-bay clone Final Project exercise

## Description
This is project is made in preparation for the [final exam](https://github.com/green-fox-academy/definitions/blob/master/exam/final-exam.md) of the [eucyon-itself](https://github.com/green-fox-academy/itself-eucyon-otpc-syllabus) class.  
Specifications [here](SPECIFICATION.md) or full docs [here](https://github.com/green-fox-academy/final-exam-homework/blob/master/backend-spec.md)  
Java backend requirements [here](https://github.com/green-fox-academy/definitions/blob/master/project-phase/education/java-backend-project-requirements.md)  

## Techical details

### Build the application

```bash
./gradlew build
```

### Run the application

```bash
./gradlew bootRun
```

## Requirements

The application requires the following environment variables:

```bash
MYSQL_URL=mysql://mysql_database:mysql_port/database_name
MYSQLUSERNAME=mysql-username
MYSQLPASSWORD=mysql-password
```
## Super-Linter added to CI pipeline

[![GitHub Super-Linter](https://github.com/green-fox-academy/eucyon-itself-krajnyak-mark-final-project/actions/workflows/ci.yml/badge.svg)](https://github.com/marketplace/actions/super-linter)

## JaCoCo code coverage tool added CI pipeline

Output file is under `/build/reports/jacoco/index.html`  
[Docs](https://www.jacoco.org/jacoco/trunk/doc/)  
But because it's terrible [here](https://reflectoring.io/jacoco/#:~:text=define%20verification%20rules.-,Global%20Coverage%20Rule,-The%20following%20configuration) is an article

## Test report available under

`/build/reports/tests/index.html`

## Swagger docs

We use swagger to document our API  
Docs [here](https://springdoc.org/v2/) and [here](https://swagger.io/specification/v3/)  
Available locally [at](https://localhost:8080/swagger-ui)
or deployed [at](https://ebayclone-production.up.railway.app/swagger-ui/index.html)
