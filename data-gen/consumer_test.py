from argparse import ArgumentParser, FileType
from configparser import ConfigParser

from confluent_kafka import Consumer

if __name__ == "__main__":
    # Create Consumer instance
    config = {"bootstrap.servers": "localhost:9092", "group.id": "weather-station", "auto.offset.reset": "earliest"}
    consumer = Consumer(config)

    # Subscribe to topic
    topic = ["temperature", "wind", "humidity", "pressure", "precipitation", "radiation"]
    consumer.subscribe([f"weather_station-{x}" for x in topic])

    # Poll for new messages from Kafka and print them.
    try:
        while True:
            msg = consumer.poll(1.0)
            if msg is None:
                # Initial message consumption may take up to
                # `session.timeout.ms` for the consumer group to
                # rebalance and start consuming
                print("Waiting...")
            elif msg.error():
                print(f"ERROR: {msg.error()}")
            else:
                # Extract the (optional) key and value, and print.
                print(
                    "Consumed event from topic {topic}: key = {key} value = {value}".format(
                        topic=msg.topic(), key=msg.key().decode("utf-8"), value=msg.value().decode("utf-8")
                    )
                )
    except KeyboardInterrupt:
        pass
    finally:
        # Leave group and commit final offsets
        consumer.close()
