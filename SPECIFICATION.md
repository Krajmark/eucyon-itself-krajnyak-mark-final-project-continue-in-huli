## Technical specification

### Web API

The application should be a JSON based Web API, meaning all request and response bodies should contain JSON formatted strings.

### Authentication

- The token that the user receives should be a JWT containing the user's id and name
- All requests other than the login should check the presence of the JWT token in the header, if not present the action should not happen and proper status and message should be responded
- Authentication should be configured with the framework

### Database

- Any kind of relational database (MySQL, PostgreSQL, ...) should be used to store the data of the application
- A migration tool should be used to create the database structure
- On startup the application should create a few users if there isn't any

### Architecture

- The application should follow a typical layered architecture pattern
- The database should be configured through environment variables
- The application should be deployed (Heroku or anything else)

### Tests

- All endpoints should have integration tests
    - either with test database or mocked services
- All services should have unit tests with mocked repositories
