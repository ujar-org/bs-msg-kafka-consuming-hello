# Hello, World! - Kafka consumer app

[![Build Status](https://drone.c2a2.com/api/badges/ujar-org/bs-msg-kafka-consuming-hello/status.svg)](https://drone.c2a2.com/ujar-org/bs-msg-kafka-consuming-hello)
[![Quality Gate Status](https://sonarqube.c2a2.com/api/project_badges/measure?project=ujar-org%3Abs-msg-kafka-consuming-hello&metric=alert_status&token=c73469fcf259c78d39051a89c5094fcdbd5fda04)](https://sonarqube.c2a2.com/dashboard?id=ujar-org%3Abs-msg-kafka-consuming-hello)

Minimal Spring Boot based sample of Kafka consumer app.

### Pre-Requisites to run this example locally

- Setup git command line tool (https://help.github.com/articles/set-up-git)
- Clone source code to the local machine:

```
git clone https://github.com/ujar-org/bs-msg-kafka-consuming-hello.git

cd bs-msg-kafka-consuming-hello
```

- Install Docker [https://docs.docker.com/get-docker/](https://docs.docker.com/get-docker/) - at least 1.6.0
- Add new version of docker-compose [https://docs.docker.com/compose/install/](https://docs.docker.com/compose/install/)
- Spin-up single instance of Kafka broker, zookeeper by running command:

```
docker-compose -f docker-compose.dev.yml up -d
```

### Running locally

This application is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built
using [Maven](https://spring.io/guides/gs/maven/). You can build a jar files and run it from the command line:

- Create jar packages:

```
mvn package
```

- Run **kafka-consuming-hello** app:

```
java -jar target/*.jar
```

## Code conventions

The code follows [Google Code Conventions](https://google.github.io/styleguide/javaguide.html). Code
quality is measured by:

- [Sonarqube](https://sonarqube.c2a2.com/dashboard?id=ujar-org%3Abs-msg-kafka-consuming-hello)
- [PMD](https://pmd.github.io/)
- [CheckStyle](https://checkstyle.sourceforge.io/)
- [SpotBugs](https://spotbugs.github.io/)

### Tests

This project has standard JUnit tests. To run them execute this command:

```
mvn test -P testcontainers-support
```

It is mandatory to keep test code coverage not below **80** percents and cover all business logic and edge cases.
