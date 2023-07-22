#!/bin/bash
docker compose exec broker kafka-topics --create --topic weather_station-temperature --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
docker compose exec broker kafka-topics --create --topic weather_station-wind --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
docker compose exec broker kafka-topics --create --topic weather_station-humidity --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
docker compose exec broker kafka-topics --create --topic weather_station-pressure --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
docker compose exec broker kafka-topics --create --topic weather_station-station --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
docker compose exec broker kafka-topics --create --topic weather_station-radiation --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
docker compose exec broker kafka-topics --create --topic weather_station-precipitation --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
