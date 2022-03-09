from tkinter import Tk, Scale, Label, Button, CENTER, HORIZONTAL, DoubleVar

import paho.mqtt.client as mqtt

# Window settings
WINDOW_TITLE = "Smart GreenAdapt"
WINDOW_HEIGHT = 480
WINDOW_WIDTH = 280
# Scale values
MIN_TEMP = -20.0
MAX_TEMP = 60.0
DEFAULT_TEMP = 22.0
DEFAULT_API_TEMP = 22.0
DEFAULT_API_TEMP_MIN = 22.0
DEFAULT_API_TEMP_MAX = 22.0
DEFAULT_API_TEMP_FEELS = 22.0
MIN_LUMINOSITY = 0.0
MAX_LUMINOSITY = 100.0
DEFAULT_LUMINOSITY = 20.0
MIN_HUMIDITY = 0.0
MAX_HUMIDITY = 100.0
DEFAULT_HUMIDITY = 40.0
MIN_NOISE = 0.0
MAX_NOISE = 200.0
DEFAULT_NOISE = 50.0
MIN_AIRQUALITY = 0.0
MAX_AIRQUALITY = 1000.0
DEFAULT_AIRQUALITY = 400.0


class Simulator:

    def __init__(self):
        # MQTT Client
        self.client = mqtt.Client(client_id="simulator")
        self.client.connect(host="localhost", bind_address="localhost", bind_port=1883)

        # Window settings
        self.window = Tk()
        self.window.title(WINDOW_TITLE)
        self.window.geometry(str(WINDOW_WIDTH) + 'x' + str(WINDOW_HEIGHT))

        # Temperature slider
        self.temp_lbl = Label(self.window, text="Temperature")
        self.temp_lbl.grid(column=0, row=0)
        self.temp_var = DoubleVar(value=DEFAULT_TEMP)
        self.slider_temperature = Scale(self.window, variable=self.temp_var,
                                        from_=MIN_TEMP, to_=MAX_TEMP, resolution=-1, orient=HORIZONTAL)
        self.slider_temperature.grid(column=0, row=1, padx=20, ipady=10)

        # API Temperature slider
        self.api_temp_lbl = Label(self.window, text="API Temperature")
        self.api_temp_lbl.grid(column=1, row=0)

        self.api_temp_var = DoubleVar(value=DEFAULT_TEMP)
        self.slider_api_temperature = Scale(self.window, variable=self.api_temp_var,
                                            from_=MIN_TEMP, to_=MAX_TEMP, resolution=-1, orient=HORIZONTAL)
        self.slider_api_temperature.grid(column=1, row=1, ipady=10)

        # API Min temperature slider
        self.api_min_temp_lbl = Label(self.window, text="API Min Temperature", fg="#00b347")
        self.api_min_temp_lbl.grid(column=0, row=2)
        self.api_min_temp_var = DoubleVar(value=DEFAULT_TEMP)
        self.slider_api_min_temperature = Scale(
            self.window, variable=self.api_min_temp_var, from_=MIN_TEMP, to_=MAX_TEMP, resolution=-1,
            orient=HORIZONTAL)
        self.slider_api_min_temperature.grid(column=0, row=3, ipady=10)

        # API Max temperature slider
        self.api_max_temp_lbl = Label(self.window, text="API Max Temperature", fg="#ff4040")
        self.api_max_temp_lbl.grid(column=1, row=2)
        self.api_max_temp_var = DoubleVar(value=DEFAULT_TEMP)
        self.slider_api_max_temperature = Scale(
            self.window, variable=self.api_max_temp_var, from_=MIN_TEMP, to_=MAX_TEMP, resolution=-1,
            orient=HORIZONTAL)
        self.slider_api_max_temperature.grid(column=1, row=3, ipady=10)

        # API Feels temperature slider
        self.api_feels_temp_lbl = Label(
            self.window, text="API Feels Temperature")
        self.api_feels_temp_lbl.grid(column=0, row=4)
        self.api_feels_temp_var = DoubleVar(value=DEFAULT_TEMP)
        self.slider_api_feels_temperature = Scale(
            self.window, variable=self.api_feels_temp_var, from_=MIN_TEMP, to_=MAX_TEMP, resolution=-1,
            orient=HORIZONTAL)
        self.slider_api_feels_temperature.grid(column=0, row=5, ipady=10)

        # Luminosity slider
        self.luminosity_lbl = Label(self.window, text="Luminosity")
        self.luminosity_lbl.grid(column=1, row=4)
        self.luminosity_var = DoubleVar(value=DEFAULT_LUMINOSITY)
        self.slider_luminosity = Scale(self.window, variable=self.luminosity_var,
                                       from_=MIN_LUMINOSITY, to_=MAX_LUMINOSITY, resolution=-1, orient=HORIZONTAL)
        self.slider_luminosity.grid(column=1, row=5, ipady=10)

        # Humidity slider
        self.humidity_lbl = Label(self.window, text="Humidity")
        self.humidity_lbl.grid(column=0, row=6)
        self.humidity_var = DoubleVar(value=DEFAULT_HUMIDITY)
        self.slider_humidity = Scale(self.window, variable=self.humidity_var,
                                     from_=MIN_HUMIDITY, to_=MAX_HUMIDITY, resolution=-1, orient=HORIZONTAL)
        self.slider_humidity.grid(column=0, row=7, ipady=10)

        # API Humidity slider
        self.api_humidity_lbl = Label(self.window, text="API Humidity")
        self.api_humidity_lbl.grid(column=1, row=6)
        self.api_humidity_var = DoubleVar(value=DEFAULT_HUMIDITY)
        self.slider_api_humidity = Scale(
            self.window, variable=self.api_humidity_var, from_=MIN_HUMIDITY, to_=MAX_HUMIDITY, resolution=-1,
            orient=HORIZONTAL)
        self.slider_api_humidity.grid(column=1, row=7, ipady=10)

        # Air Quality slider
        self.airquality_lbl = Label(self.window, text="AirQuality")
        self.airquality_lbl.grid(columnspan=2, column=0, row=8)
        self.airquality_var = DoubleVar(value=DEFAULT_AIRQUALITY)
        self.slider_airquality = Scale(self.window, variable=self.airquality_var,
                                       from_=MIN_AIRQUALITY, to_=MAX_AIRQUALITY, resolution=-1, orient=HORIZONTAL)
        self.slider_airquality.grid(columnspan=2, column=0, row=9, ipady=10)

        # Button to publish the data
        self.submit_btn = Button(self.window, text="Simulate", bg='#567', fg='White', padx=5, pady=5, font=12, command=self.publish)
        self.submit_btn.grid(columnspan=2, column=0, row=10)

    def publish(self):
        self.client.publish(topic="temperature",
                            payload=self.slider_temperature.get(), qos=1)
        self.client.publish(topic="weather_temp",
                            payload=self.slider_api_temperature.get(), qos=1)
        self.client.publish(topic="weather_temp_min",
                            payload=self.slider_api_min_temperature.get(), qos=1)
        self.client.publish(topic="weather_temp_max",
                            payload=self.slider_api_max_temperature.get(), qos=1)
        self.client.publish(topic="weather_temp_feels",
                            payload=self.slider_api_feels_temperature.get(), qos=1)
        self.client.publish(topic="weather_humidity",
                            payload=self.slider_api_humidity.get(), qos=1)
        self.client.publish(topic="luminosity",
                            payload=self.slider_luminosity.get(), qos=1)
        self.client.publish(topic="humidity",
                            payload=self.slider_humidity.get(), qos=1)
        self.client.publish(topic="airquality",
                            payload=self.slider_airquality.get(), qos=1)
        self.client.loop()

    def loop(self):
        self.window.mainloop()


if __name__ == "__main__":
    s = Simulator()
    s.loop()