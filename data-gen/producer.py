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
        time.sleep(randint(1, 5))
        # choose random station
        station = stations[randint(0, len(stations) - 1)]
        data = station.generate_data()
        producer.produce("weather_station-temperature", key="real", value=json.dumps(data), callback=acked)
        send_data()
        # producer.produce("weather_station-temperature", key="apparent", value=str(25 + randint(-5, 5)), callback=acked)
        # producer.produce("weather_station-temperature", key="real", value=str(20 + randint(-5, 10)), callback=acked)
        producer.produce("weather_station-temperature", key="real", value=json.dumps({"cena": 1, "ts": timestamp}), callback=acked)
        # producer.produce("weather_station-wind", key="speed", value=str(60 + randint(-40, 20)), callback=acked)  # km/h
        # producer.produce("weather_station-wind", key="direction", value=str(randint(0, 360)), callback=acked)  # °
        # producer.produce("weather_station-humidity", key="default", value=str(60 + randint(-10, 20)), callback=acked)  # %
        # producer.produce("weather_station-pressure", key="default", value=str(1015 + randint(-5, 5)), callback=acked)  # hPa
        # producer.produce("weather_station-precipitation", key="default", value=str(randint(0, 10)), callback=acked)  # mm
        # producer.produce("weather_station-radiation", key="uv", value=str(randint(1, 11)), callback=acked)  #
        # producer.produce("weather_station-radiation", key="solar", value=str(randint(100, 600)), callback=acked)  # mm
        producer.poll(1)
