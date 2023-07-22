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
            (self.generate_temperature_data, "temperature"),
            (self.generate_wind_data, "wind"),
            (self.generate_humidity_data, "humidity"),
            (self.generate_pressure_data, "pressure"),
            (self.generate_precipitation_data, "precipitation"),
            (self.generate_radiation_data, "radiation"),
        ]
        func, topic = functions[randint(0, len(functions) - 1)]
        payload = func()
        return topic, {"station_id": self.id, "timestamp": int(time.time()), "payload": payload}

    def generate_temperature_data(self):
        apparent = 25 + randint(-self.variation, self.variation)  # °C
        real = apparent - randint(-self.variation, self.variation)  # °C
        return {"apparentTemperature": apparent, "realTemperature": real}

    def generate_wind_data(self):
        wind_speed = 20 + randint(-self.variation, self.variation * 8)  # km/h
        wind_direction = randint(0, 360)  # °
        return {"windSpeed": wind_speed, "windDirection": wind_direction}

    def generate_humidity_data(self):
        humidity = 60 + randint(-self.variation, self.variation * 2)  # %
        return {"humidity": humidity}

    def generate_pressure_data(self):
        pressure = 1015 + randint(-self.variation, self.variation)  # hPa
        return {"pressure": pressure}

    def generate_precipitation_data(self):
        precipitation = randint(0, 10)  # mm
        return {"precipitation": precipitation}

    def generate_radiation_data(self):
        uv = randint(1, 11)  # scale
        solar = 300 + randint(-self.variation * 20, self.variation * 20)  # scale
        return {"uv": uv, "solar": solar}

    def __str__(self) -> str:
        return f"Station {self.id}"
