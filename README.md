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

## Add new user: `[POST]: http://localhost:8080/auth/add`

```php
{
  "username": "user1",
  "password": "123",
  "email": "user@gmail.com",
  "roles": "ROLE_USER"
}
```

## Add new user: `[POST]: http://localhost:8080/auth/generateToken`

```php
{
  "username": "user1",
  "password": "123"
}
```

## Get user profile: `[GET]: http://localhost:8080/auth/user`

**Header: Bearer generateToken**

```php
{
  "Authorization": Bearer token,
}
```

## Get user profile: `[GET]: http://localhost:8080/auth/admin`

**Header: Bearer generateToken**

```php
{
  "Authorization": Bearer token,
}
```