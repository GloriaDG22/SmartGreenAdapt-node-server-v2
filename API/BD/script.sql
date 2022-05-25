CREATE DATABASE SmartGreenAdapt;
USE SmartGreenAdapt;

DROP TABLE if exists AirQuality;
DROP TABLE if exists Luminosity;
DROP TABLE if exists Notification;
DROP TABLE if exists Humidity;
DROP TABLE if exists Temperature;
DROP TABLE if exists Windows;
DROP TABLE if exists Irrigation;
DROP TABLE if exists Sprinklers;
DROP TABLE if exists Heating;
DROP TABLE if exists GreenHouse;
DROP TABLE if exists UserInfo;
DROP TABLE if exists weather;

CREATE TABLE UserInfo(
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(25),
    password VARCHAR(25),
    email VARCHAR(100),
    PRIMARY KEY(id)
);

CREATE TABLE GreenHouse(
  id INT NOT NULL AUTO_INCREMENT,
  idUsuario INT,
  name VARCHAR(50),
  PRIMARY KEY (id),
  FOREIGN KEY (idUsuario) REFERENCES UserInfo(id) ON DELETE CASCADE
);

CREATE TABLE AirQuality (
	id INT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(4,2),
    date TIMESTAMP,
    idGreenhouse INT,
	PRIMARY KEY(id),
    FOREIGN KEY (idGreenhouse) REFERENCES GreenHouse(id) ON DELETE CASCADE
);
CREATE TABLE Luminosity (
    id INT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(6,2),
    date TIMESTAMP,
    idGreenhouse INT,
    PRIMARY KEY(id),
    FOREIGN KEY (idGreenhouse) REFERENCES GreenHouse(id) ON DELETE CASCADE
);
CREATE TABLE Notification (
    id INT NOT NULL AUTO_INCREMENT,
    is_warning INT,
    problem VARCHAR(100),
    status VARCHAR(10),
    date TIMESTAMP,
    idGreenhouse INT,
    PRIMARY KEY(id),
    FOREIGN KEY (idGreenhouse) REFERENCES GreenHouse(id) ON DELETE CASCADE
);
CREATE TABLE Humidity (
    id INT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(4,2),
    date TIMESTAMP,
    idGreenhouse INT,
    PRIMARY KEY(id),
    FOREIGN KEY (idGreenhouse) REFERENCES GreenHouse(id) ON DELETE CASCADE
);
CREATE TABLE Temperature (
	id INT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(4,2),  
    date TIMESTAMP,
    idGreenhouse INT,
    PRIMARY KEY(id),
    FOREIGN KEY (idGreenhouse) REFERENCES GreenHouse(id) ON DELETE CASCADE
);
CREATE TABLE Windows (
    id INT NOT NULL AUTO_INCREMENT,
    is_on INT,
    affects VARCHAR(100),
    name VARCHAR(100),
    idGreenhouse INT,
    PRIMARY KEY(id),
    FOREIGN KEY (idGreenhouse) REFERENCES GreenHouse(id) ON DELETE CASCADE
);
CREATE TABLE Irrigation(
   id INT NOT NULL AUTO_INCREMENT,
   is_on INT,
   affects VARCHAR(100),
   idGreenhouse INT,
   PRIMARY KEY(id),
   FOREIGN KEY (idGreenhouse) REFERENCES GreenHouse(id) ON DELETE CASCADE
);
CREATE TABLE Sprinklers(
   id INT NOT NULL AUTO_INCREMENT,
   is_on INT,
   affects VARCHAR(100),
   idGreenhouse INT,
   PRIMARY KEY(id),
   FOREIGN KEY (idGreenhouse) REFERENCES GreenHouse(id) ON DELETE CASCADE
);
CREATE TABLE Heating(
    id INT NOT NULL AUTO_INCREMENT,
    affects VARCHAR(100),
    type VARCHAR(50),
    value INT,
    idGreenhouse INT,
    PRIMARY KEY(id),
    FOREIGN KEY (idGreenhouse) REFERENCES GreenHouse(id) ON DELETE CASCADE
);
CREATE TABLE Weather (
	id INT NOT NULL AUTO_INCREMENT,
    state VARCHAR(10),  
    temp DECIMAL(4,2),
    temp_feel DECIMAL(4,2),
    temp_min DECIMAL(4,2),
    temp_max DECIMAL(4,2),
    pressure INT,
    humidity INT,
    wind DECIMAL(5,2),
    date TIMESTAMP,
    season VARCHAR(10),
    PRIMARY KEY(id)    
);

-- Creación de usuario e invernadero de prueba
INSERT INTO smartgreenadapt.UserInfo(id, username, password, email)
VALUES (3, 'admin', 'adminadmin', 'a@g.c');


INSERT INTO smartgreenadapt.UserInfo(id, username, password, email)
VALUES (1, 'Juan', 'qqqqqq', 'j@g.c');
INSERT INTO smartgreenadapt.GreenHouse(id, idUsuario, name)
VALUES (1, 1, 'Greenhouse potatoes');
INSERT INTO smartgreenadapt.GreenHouse(id, idUsuario, name)
VALUES (2, 1, 'Greenhouse strawberries');


INSERT INTO smartgreenadapt.UserInfo(id, username, password, email)
VALUES (2, 'Petra', '111111', 'p@g.c');
INSERT INTO smartgreenadapt.GreenHouse(id, idUsuario, name)
VALUES (3, 2, 'Greenhouse sunflower');


-- Prueba 1 - Warning humidity y error luminosity
INSERT INTO smartgreenadapt.temperature (amount, date, idGreenhouse)
VALUES (30.00, '2022-03-22 12:10:44', 1);
INSERT INTO smartgreenadapt.luminosity (amount, date, idGreenhouse)
VALUES (1600.00, '2022-03-22 12:10:44', 1);
INSERT INTO smartgreenadapt.airquality (amount, date, idGreenhouse)
VALUES (10.00, '2022-03-22 12:10:44', 1);
INSERT INTO smartgreenadapt.humidity (amount, date, idGreenhouse)
VALUES (40.00, '2022-03-22 12:10:44', 1);

-- Prueba 2 - Error luminosity y warning airquality
INSERT INTO smartgreenadapt.temperature (amount, date, idGreenhouse)
VALUES (20.00, '2022-03-23 12:10:44', 1);
INSERT INTO smartgreenadapt.luminosity (amount, date, idGreenhouse)
VALUES (-10.00, '2022-03-23 12:10:44', 1);
INSERT INTO smartgreenadapt.airquality (amount, date, idGreenhouse)
VALUES (60.00, '2022-03-23 12:10:44', 1);
INSERT INTO smartgreenadapt.humidity (amount, date, idGreenhouse)
VALUES (70.00, '2022-03-23 12:10:44', 1);

-- Prueba 3 - Warning luminosity y error temperature
INSERT INTO smartgreenadapt.temperature (amount, date, idGreenhouse)
VALUES (50.00, '2022-03-24 12:10:44', 1);
INSERT INTO smartgreenadapt.luminosity (amount, date, idGreenhouse)
VALUES (1200.00, '2022-03-24 12:10:44', 1);
INSERT INTO smartgreenadapt.airquality (amount, date, idGreenhouse)
VALUES (30.00, '2022-03-24 12:10:44', 1);
INSERT INTO smartgreenadapt.humidity (amount, date, idGreenhouse)
VALUES (70.00, '2022-03-24 12:10:44', 1);

-- Datos invernaderos
INSERT INTO smartgreenadapt.temperature (amount, date, idGreenhouse)
VALUES (5.00, '2022-03-25 12:10:44', 1);                                -- Warning
INSERT INTO smartgreenadapt.luminosity (amount, date, idGreenhouse)
VALUES (1000.00, '2022-03-25 12:10:44', 1);
INSERT INTO smartgreenadapt.airquality (amount, date, idGreenhouse)
VALUES (10.00, '2022-03-25 12:10:44', 1);
INSERT INTO smartgreenadapt.humidity (amount, date, idGreenhouse)
VALUES (10.00, '2022-03-25 12:10:44', 1);

-- Datos invernaderos
INSERT INTO smartgreenadapt.temperature (amount, date, idGreenhouse)
VALUES (30.00, '2022-03-25 12:10:44', 2);
INSERT INTO smartgreenadapt.luminosity (amount, date, idGreenhouse)
VALUES (1200.00, '2022-03-25 12:10:44', 2);                                -- Warning
INSERT INTO smartgreenadapt.airquality (amount, date, idGreenhouse)
VALUES (30.00, '2022-03-25 12:10:44', 2);
INSERT INTO smartgreenadapt.humidity (amount, date, idGreenhouse) -- Sin valor
VALUES (70.00, '2022-03-25 12:10:44', 2);

-- Datos invernaderos
INSERT INTO smartgreenadapt.temperature (amount, date, idGreenhouse)
VALUES (40.00, '2022-03-25 12:10:44', 3);
INSERT INTO smartgreenadapt.luminosity (amount, date, idGreenhouse)
VALUES (1000.00, '2022-03-25 12:10:44', 3);
INSERT INTO smartgreenadapt.airquality (amount, date, idGreenhouse)
VALUES (70.00, '2022-03-25 12:10:44', 3);                                -- Warning
INSERT INTO smartgreenadapt.humidity (amount, date, idGreenhouse)
VALUES (95.00, '2022-03-25 12:10:44', 3);                                -- Warning


INSERT INTO smartgreenadapt.notification (is_warning, problem, status, date, idGreenhouse)
VALUES (0, 'Temperature', 'low', '2022-03-25 12:10:44', 1);
INSERT INTO smartgreenadapt.notification (is_warning, problem, status, date, idGreenhouse)
VALUES (0, 'Luminosity', 'high', '2022-03-25 12:10:44', 2);
INSERT INTO smartgreenadapt.notification (is_warning, problem, status, date, idGreenhouse)
VALUES (1, 'Airquality', 'error', '2022-03-25 12:10:44', 3);
INSERT INTO smartgreenadapt.notification (is_warning, problem, status, date, idGreenhouse)
VALUES (0, 'Humidity', 'high', '2022-03-25 12:10:44', 3);


INSERT INTO smartgreenadapt.irrigation (id, is_on, affects, idGreenhouse)
VALUES (1, 0, 'Humidity,', 1);
INSERT INTO smartgreenadapt.windows (id, is_on, affects, name, idGreenhouse)
VALUES (1, 1, 'Temperature,Airquality,Luminosity,Humidity,', 'Lateral', 1);
INSERT INTO smartgreenadapt.sprinklers (id, is_on, affects, idGreenhouse)
VALUES (1, 1, 'Humidity,Temperature,', 1);
INSERT INTO smartgreenadapt.heating (id, affects, type, value, idGreenhouse)
VALUES (1, 'Temperature,Humidity,', 'Infrared', 20, 1);

INSERT INTO smartgreenadapt.irrigation (id, is_on, affects, idGreenhouse)
VALUES (2, 1, 'Humidity,', 2);
INSERT INTO smartgreenadapt.windows (id, is_on, affects, name, idGreenhouse)
VALUES (2, 1, 'Temperature,Airquality,Luminosity,Humidity', 'Lateral', 2);
INSERT INTO smartgreenadapt.windows (id, is_on, affects, name, idGreenhouse)
VALUES (3, 0, 'Temperature,Airquality,Luminosity,Humidity,', 'Frontal', 2);
INSERT INTO smartgreenadapt.sprinklers (id, is_on, affects, idGreenhouse)
VALUES (2, 0, 'Temperature,Humidity,', 2);


INSERT INTO smartgreenadapt.irrigation (id, is_on, affects, idGreenhouse)
VALUES (3, 0, 'Humidity,', 3);
INSERT INTO smartgreenadapt.windows (id, is_on, affects, name, idGreenhouse)
VALUES (4, 1, 'Temperature,Airquality,Luminosity,Humidity,', 'Frontal', 3);
INSERT INTO smartgreenadapt.heating (id, affects, type, value, idGreenhouse)
VALUES (3, 'Temperature,Humidity,', 'Ventilate', 28, 3);
