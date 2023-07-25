# weather-station-hub

Weather station hub is a project carried out as a way to learn more about Spring Boot, Apache Kafka, MySQL, Docker and microservices in general. The idea is to have a hub that receives data from different weather stations and persist it.

The data-gen module simulates data and publishes it in Kafka's streams. A consumer service, subscribed to the weather station topics, sends the received data to the Spring Boot API via POST. Using Spring JPA, the data is persisted in MySQL and made available via GET requests.

## Setup

`docker compose up -d`

That should be it.
