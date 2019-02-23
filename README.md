# Chess engine with Spring boot, redis and JWT authentication

Thats a sample chess rest service with spring boot, redis and JWT authentication.

This application has an engine in order to check if a movement is valid or one player is in check mate.

It is not a fully functional application and it's intention is only for investigation / learning purposes.

## Usage

Install and start redis. Run spring boot application.

Create some users:

```
POST/ localhost:8080/api/account/create
```

Login with at least two users, and store the JWT token returned as a header.

```
POST/ localhost:8080/login
```

From now on, every call (createGame or move) will need this token in order to authenticate the user.
```
POST/ localhost:8080/api/chess/createGame
```

```
POST/ localhost:8080/api/chess/move
```
