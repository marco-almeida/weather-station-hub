import itertools
import time
from random import randint


class Station:
    id_iter = itertools.count(1)

    def __init__(self, variation: int):
        self.id = next(self.id_iter)
        self.variation = variation

    def generate_data(self):
        functions = [
            (self.generate_real_temperature_data, "real_temperature"),
            (self.generate_apparent_temperature_data, "apparent_temperature"),
            (self.generate_wind_direction_data, "wind_direction"),
            (self.generate_wind_speed_data, "wind_speed"),
            (self.generate_humidity_data, "humidity"),
            (self.generate_pressure_data, "pressure"),
            (self.generate_precipitation_data, "precipitation"),
            (self.generate_solar_radiation_data, "solar_radiation"),
            (self.generate_uv_data, "uv"),
        ]
        func, topic = functions[randint(0, len(functions) - 1)]
        payload = func()
        return topic, {"station_id": self.id, "timestamp": int(time.time()), "type": topic, "payload": payload}

    def generate_real_temperature_data(self):
        apparent = 25 + randint(-self.variation, self.variation)  # °C
        return apparent - randint(-self.variation, self.variation)  # °C

    def generate_apparent_temperature_data(self):
        return 25 + randint(-self.variation, self.variation)  # °C

    def generate_wind_speed_data(self):
        return 20 + randint(-self.variation, self.variation * 8)  # km/h

    def generate_wind_direction_data(self):
        return randint(0, 360)  # °

    def generate_humidity_data(self):
        return 60 + randint(-self.variation, self.variation * 2)  # %

    def generate_pressure_data(self):
        return 1015 + randint(-self.variation, self.variation)  # hPa

    def generate_precipitation_data(self):
        return randint(0, 10)  # mm

    def generate_solar_radiation_data(self):
        return 300 + randint(-self.variation * 20, self.variation * 20)  # scale

    def generate_uv_data(self):
        return randint(1, 11)  # scale

    def __str__(self) -> str:
        return f"Station {self.id}"
