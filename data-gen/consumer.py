from argparse import ArgumentParser, FileType
from configparser import ConfigParser

from confluent_kafka import OFFSET_BEGINNING, Consumer


# Set up a callback to handle the '--reset' flag.
def reset_offset(consumer, partitions):
    if args.reset:
        for p in partitions:
            p.offset = OFFSET_BEGINNING
        consumer.assign(partitions)


if __name__ == "__main__":
    # Parse the command line.
    parser = ArgumentParser()
    parser.add_argument("config_file", type=FileType("r"))
    parser.add_argument("--reset", action="store_true")
    args = parser.parse_args()

    config_parser = ConfigParser()
    config_parser.read_file(args.config_file)
    config = dict(config_parser["default"])
    config.update(config_parser["consumer"])

    # Create Consumer instance
    consumer = Consumer(config)

    # Subscribe to topic
    topic = ["temperature", "wind", "humidity", "pressure", "precipitation", "radiation"]
    consumer.subscribe([f"weather_station-{x}" for x in topic], on_assign=reset_offset)

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
                    "Consumed event from topic {topic}: key = {key} value = {value} timestamp = {timestamp}".format(
                        topic=msg.topic(), key=msg.key().decode("utf-8"), value=msg.value().decode("utf-8"), timestamp=msg.timestamp()
                    )
                )
    except KeyboardInterrupt:
        pass
    finally:
        # Leave group and commit final offsets
        consumer.close()
