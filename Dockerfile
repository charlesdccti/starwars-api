FROM maven:3.5-jdk-8-alpine
WORKDIR /app/starwars-api
COPY . .
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]