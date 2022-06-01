FROM openjdk:11

# Install maven
RUN apt-get update
RUN apt-get install -y maven

WORKDIR /deps/core

ADD bd1-todo-core/pom.xml /deps/core/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD bd1-todo-core/src /deps/core/src
RUN ["mvn", "install"]



WORKDIR /app

# Prepare by downloading dependencies
ADD bd1-todo-api/pom.xml /app/pom.xml
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD bd1-todo-api/src /app/src
RUN ["mvn", "package"]

EXPOSE 4567
CMD ["java", "-jar", "target/todo-api-jar-with-dependencies.jar"]
