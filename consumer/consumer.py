import json
import logging
import sys

import requests
from confluent_kafka import Consumer, KafkaError, KafkaException

logging.basicConfig(
    level=logging.DEBUG,
    format="%(asctime)s,%(msecs)03d: %(module)17s->%(funcName)-15s - [%(levelname)7s] - %(message)s",
    handlers=[logging.StreamHandler(stream=sys.stdout)],
)

logger = logging.getLogger().getChild("System")
API_URL = "http://localhost:8080/api/v1"


def commit_completed(err, partitions):
    if err:
        logger.info(str(err))
    else:
        logger.info("Committed")


conf = {
    "bootstrap.servers": "localhost:9092,broker:9092",
    "group.id": "foo",
    "default.topic.config": {"auto.offset.reset": "earliest"},
    "on_commit": commit_completed,
}

MIN_COMMIT_COUNT = 1


def msg_process(msg):
    logger.info(f"Received message from topic {msg.topic()}: key = {msg.key().decode('utf-8')} value = {msg.value().decode('utf-8')}")
    payload = json.loads(msg.value().decode("utf-8"))
    station_id = payload.pop("station_id")
    msg_type = msg.topic().split("-")[1]
    payload["type"] = msg_type
    print(f"Sending {msg_type} data {payload} to station {station_id}")
    requests.post(f"{API_URL}/station/{station_id}/payload", json=payload)


def consume_loop(consumer, topics):
    try:
        consumer.subscribe(topics)

        msg_count = 0
        while True:
            msg = consumer.poll(timeout=1.0)
            if msg is None:
                continue
            if msg.error():
                if msg.error().code() == KafkaError._PARTITION_EOF:
                    # End of partition event
                    sys.stderr.write("%% %s [%d] reached end at offset %d\n" % (msg.topic(), msg.partition(), msg.offset()))
                elif msg.error():
                    raise KafkaException(msg.error())
            else:
                msg_process(msg)
                msg_count += 1
                if msg_count % MIN_COMMIT_COUNT == 0:
                    consumer.commit(asynchronous=True)
    finally:
        # Close down consumer to commit final offsets.
        consumer.close()


if __name__ == "__main__":
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
    consumer = Consumer(conf)
    real_topics = [f"weather_station-{topic}" for topic in topics]
    consume_loop(consumer, real_topics)
