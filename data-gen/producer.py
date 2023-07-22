import json
import logging
import sys
import time
from argparse import ArgumentParser, FileType
from configparser import ConfigParser
from random import randint

from confluent_kafka import Producer

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
    # Parse the command line.
    parser = ArgumentParser()
    parser.add_argument("config_file", type=FileType("r"))
    args = parser.parse_args()

    config_parser = ConfigParser()
    config_parser.read_file(args.config_file)
    config = dict(config_parser["default"])

    # Create Producer instance
    producer = Producer(config)

    # create 5 stations with random variation (1-5)
    stations = [Station(i) for i in range(5)]

    while True:
        time.sleep(randint(0, 5))
        # choose random station
        station = stations[randint(0, len(stations) - 1)]
        topic, data = station.generate_data()
        producer.produce(f"weather_station-{topic}", key="real", value=json.dumps(data), callback=acked)
        producer.poll(1)
