# Trade Data History Service
This service fetches live data from a partner web socket and exposes it in form of candlesticks.

## How to Run
The application can be run through source code with a compatible IDE or by running the docker image.

#### Through Source Code
- Simply clone or copy the source code to preferred directory and open it in a compatible IDE (e.g Intellij Idea)
- Run the main driver class TradeDataServiceApplication.java
- The application starts on port 8080

#### Through Docker Image
- The project contains a Dockerfile in the root directory for any docker image creation
- Simply run docker by building image with this Dockerfile or
- -or
- Copy the code to where your docker-compose.yml rests and add to your docker-compose.yml following configuration
```
app:
  build:
  context: ./trade-data-service
  dockerfile: Dockerfile
  image: app
  container_name: app
  environment:
    SPRING_KAFKA_BOOTSTRAPSERVERS: kafka:29092
  ports:
    - "8080:8080"
  depends_on:
    - java
```
- Then run command ```docker-compose up -d```

## Documentation
This application uses Swagger for api documentation. Documentation ui can be found at url ```http://localhost:8080/swagger-ui.html``` after running the application.

## Monitoring
This application uses Spring Actuator with Hal Explorer for monitoring of web api. For monitoring web ui simply navigate to ```http://localhost:8080/```
