FROM maven:3.5-jdk-8-alpine
WORKDIR /app/starwars-api
RUN mkdir -p /app/starwars-api
COPY ./pom.xml /app/starwars-api
RUN mvn dependency:go-offline
COPY . /app/starwars-api
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]