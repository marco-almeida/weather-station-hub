# weather-station-hub

Weather station hub is a project carried out as a way to learn more about Spring Boot Restful APIs, Spring Data JPA, Apache Kafka, MySQL, Docker and microservices in general. The idea is to have a hub that receives *(simulated)* data from different weather stations and persists it in a database.

- The data-gen module simulates data and publishes it in Kafka's streams.

- A consumer service, subscribed to the weather station topics, sends the received data to the Spring Boot API via POST.

- Using Spring Data JPA, the data is persisted in MySQL and made available via GET requests.

- All these services are containerized using Docker and orchestrated with Docker Compose.

## Setup

`docker compose up --build # use -d to run in detached mode`

That should be it.

## Development

To rebuild a specific service, use: `docker compose up -d --no-deps --build <service_name>`
