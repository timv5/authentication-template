# authentication-template
Simple authentication template application with login and registration written in 
Springboot application. 

## Technologies
- Gradle
- Java 17
- Springboot
- Docker, Docker compose
- Postgres

## How to run the application? 
- git checkout
- go to the root directory
- run: docker-compose -f docker-compose up -d
- application will be accessible on: http://localhost:8080

## Database (Postgres) settings
- available and configurable in application.properties file

## Usage and Endpoints
All the secured endpoints require Bearer token, which will be accessible in /login 
response body as "accessToken".

### (POST) /api/user/auth/login
- Not secured endpoint
- Request:
{
"email": "final1registr@email.com",
"password": "1223"
}
- Response
  {
  "success": true,
  "code": "1",
  "message": "SUCCESS",
  "userId": 9,
  "type": "Bearer",
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5IiwiaWF0IjoxNjcyMjI5MjMxLCJleHAiOjE2NzMwOTMyMzEsIklzVXNlckFjdGl2ZSI6dHJ1ZX0.JdGZ0CBhvyJL5EfaTpWvxe9L_9ZwqUtCl4B7fe4ZQANNzgx7FKG4uJGaNnTp-sBpYCXwWEjaEx5E6ELPjjaycQ",
  "expirationTime": 3600
  }

### (POST) /api/user/auth/register
- Not secured endpoint
- Request:
{
"email": "final1registr@email.com",
"password": "1223"
}

### (GET) /api/user/details
- Secured endpoint
- Logged-in user requesting this resource needs to be active
Response:
  {
  "success": true,
  "code": "1",
  "message": "SUCCESS",
  "email": "final1registr@email.com",
  "timeCreated": null
  }

### (POST) /api/user/change/password
- Secured endpoint
- ROLE_USER is required to access this endpoint
- Request
{
"oldPassword": "1223",
"newPassword": "12234"
}
- Response:
  {
  "success": true,
  "code": "1",
  "message": "SUCCESS"
  }

### (GET) /api/role/all
- Secured endpoint
- ROLE_ADMIN is required to access this endpoint
- Response:
  {
  "success": true,
  "code": "1",
  "message": "SUCCESS",
  "roles":
[
  {
  "roleId": 1,
  "rleName": "ROLE_USER"
  },
  {
  "roleId": 2,
  "rleName": "ROLE_ADMIN"
  }
  ]
  }
