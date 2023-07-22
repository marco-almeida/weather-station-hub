import json
import time
from argparse import ArgumentParser, FileType
from configparser import ConfigParser
from random import randint

from confluent_kafka import Producer


def acked(err, msg):
    if err:
        print("Failed to deliver message: %s: %s" % (str(msg), str(err)))
    else:
        print(
            "Produced event to topic {topic}: key = {key} value = {value}".format(
                topic=msg.topic(), key=msg.key().decode("utf-8"), value=msg.value().decode("utf-8")
            )
        )


def get_json_payload(timestamp: int, value: int) -> str:
    return f'{{"timestamp": {timestamp}, "value": {value}}}'


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

    while True:
        time.sleep(randint(1, 5))
        timestamp = int(time.time())
        apparent = 25 + randint(-5, 5)  # °C
        real = apparent - randint(0, 5)  # °C
        wind_speed = 60 + randint(-40, 20)  # km/h
        wind_direction = randint(0, 360)  # °
        humidity = 60 + randint(-10, 20)  # %
        pressure = 1015 + randint(-5, 5)  # hPa
        precipitation = randint(0, 10)  # mm
        uv = randint(1, 11)  # scale
        solar = randint(100, 600)  # scale
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
