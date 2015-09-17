# springboot-example

This is a sample of SpringBoot.

http://qiita.com/uzresk/items/31a4585f7828c4a9334f

---

## Description

* Account of registration , login , and offers a search function using itunes search API.

## Includes Samples

* thymeleaf
* SpringSecurity
* Spring MVC
* sample of twitter, facebook oauth
* sample of TOTP authentication
* jpa
* spring actuator
* lombok

---

## Requires

* Java8
* PostgreSQL8.5 later

---

## Usage

### DataBaseSettings

* change application.yml
* apply specify the DDL（account.sql)

### Set up twitter oauth

* /src/main/resources/twitter4j.properties

```
oauth.consumerKey=***
oauth.consumerSecret=***
```

### Set up facebook oauth

* /src/main/resources/facebook4j.properties

```
oauth.appId=
oauth.appSecret=
```

### Packaging

`mvn clean package`

### To Start Springboot Example Application

` ${JAVA_HOME}/bin/java -Dlogging.config=logback.xml -jar sbex-0.0.1-SNAPSHOT.jar &`

### Open Browser

`http://localhost:8080/app/`

