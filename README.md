# 1. Install mysql

## Run Docker: `docker-compose -f .\docker-compose.yml up`

## PhpMyAdmin: `http://localhost:8090`

```php
username: root
password: password
```

## Create database: `spring_boot_db`

# 2. Config setting

## application.properties: `src/main/resources/application.properties`

```php
# configs/AuthEntryPointJwt.java/PasswordEncoder
spring.main.allow-circular-references=true

spring.datasource.url=jdbc:mysql://localhost:3306/spring_boot_db
spring.datasource.username=root
spring.datasource.password=password

spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.diatect=org.hibernate.dialect.MySQL8InnoDBDialect
```

# 3. API Testing

## Register: `[POST]: http://localhost:8080/auth/register`

```php
{
  "username": "user1",
  "password": "123",
  "email": "user@gmail.com",
  "roles": "ROLE_USER"
}
```

## Login: `[POST]: http://localhost:8080/auth/login`

```php
{
  "username": "user1",
  "password": "123"
}
```

## Refresh: `[POST]: http://localhost:8080/auth/refresh`

**Header: Bearer generateToken**

```php
{
  "Authorization": Bearer accessToken
}
```

## Get user profile: `[GET]: http://localhost:8080/auth/profile`

**Header: Bearer generateToken**

```php
{
  "Authorization": Bearer token
}
```

## Check user role: `[GET]: http://localhost:8080/auth/user`

**Header: Bearer generateToken**

```php
{
  "Authorization": Bearer token
}
```

## Check admin role: `[GET]: http://localhost:8080/auth/admin`

**Header: Bearer generateToken**

```php
{
  "Authorization": Bearer token
}
```