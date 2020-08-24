# Dropwizard JWT Auth
## Usage:
`mvn clean install && java -jar target/dropwizard-jwt-0.1.jar server dropwizard/config.yml`

## Resources
### Login
Log in using basic auth and obtain a JWT token.

#### Example request:
`curl -u user:pass -X GET --header 'Accept: application/json' 'http://localhost:8080/auth/login'`

Try it without auth:
`curl -X GET --header 'Accept: application/json' 'http://localhost:8080/auth/login'`

#### Example response:

```
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJVU0VSIiwidXNlciI6InVzZXIiLCJpYXQiOjE1OTgyNjg4OTMsImp0aSI6IlN5T21tWTVmYzdzMmgzdGFSZmoxVWcifQ.KIno3sAZl-IqgWstxB33CmT4q8nXoLSx2nOT9OV28no"
}
```

Go decode the token on [jwt.io](https://jwt.io) to see the contents.

### ProtectedResourceUser:
Will accept the JWT obtained from the login above and echo some values from the JWT back.

#### Example request:
`curl -X GET --header 'Accept: application/json' --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJVU0VSIiwidXNlciI6InVzZXIiLCJpYXQiOjE1OTgyNjg4OTMsImp0aSI6IlN5T21tWTVmYzdzMmgzdGFSZmoxVWcifQ.KIno3sAZl-IqgWstxB33CmT4q8nXoLSx2nOT9OV28no' 'http://localhost:8080/protected/user'`

#### Example response:

```
{
  "role": "user",
  "username": "user"
}
```

### ProtectedResourceAdmin:
Will not accept the JWT obtained from the login above, because it expects a different role.

#### Example request:
`curl -X GET --header 'Accept: application/json' --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJVU0VSIiwidXNlciI6InVzZXIiLCJpYXQiOjE1OTgyNjg4OTMsImp0aSI6IlN5T21tWTVmYzdzMmgzdGFSZmoxVWcifQ.KIno3sAZl-IqgWstxB33CmT4q8nXoLSx2nOT9OV28no' 'http://localhost:8080/protected/admin '`

#### Example response:

```
{
  "code": 403,
  "message": "User not authorized."
}
```
