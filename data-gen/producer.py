import json
import logging
import socket
import sys
import time
from random import randint

from confluent_kafka import Producer
from confluent_kafka.admin import AdminClient, NewTopic
from station import Station

logging.basicConfig(
    level=logging.DEBUG,
    format="%(asctime)s,%(msecs)03d: %(module)17s->%(funcName)-15s - [%(levelname)7s] - %(message)s",
    handlers=[logging.StreamHandler(stream=sys.stdout)],
)

logger = logging.getLogger().getChild("System")


def acked(err, msg):
    if err:
        logger.info("Failed to deliver message: %s: %s" % (str(msg), str(err)))
    else:
        logger.info(
            "Produced event to topic {topic}: key = {key} value = {value}".format(
                topic=msg.topic(), key=msg.key().decode("utf-8"), value=msg.value().decode("utf-8")
            )
        )


if __name__ == "__main__":
    # Create Producer instance
    conf = {"bootstrap.servers": "localhost:9092", "client.id": socket.gethostname()}
    producer = Producer(conf)
    # create some topics
    admin = AdminClient({"bootstrap.servers": "localhost:9092"})
    topics = [
        "real_temperature",
        "apparent_temperature",
        "wind_direction",
        "wind_speed",
        "humidity",
        "pressure",
        "precipitation",
        "solar_radiation",
        "uv",
    ]
    admin.create_topics([NewTopic(f"weather_station-{i}", num_partitions=1, replication_factor=1) for i in topics])

    # create 5 stations with variation 1-5
    stations = [Station(i) for i in range(5)]

    while True:
        time.sleep(randint(0, 5))
        # choose random station
        station = stations[randint(0, len(stations) - 1)]
        topic, data = station.generate_data()
        producer.produce(f"weather_station-{topic}", key="real", value=json.dumps(data), callback=acked)
        producer.poll(1)
