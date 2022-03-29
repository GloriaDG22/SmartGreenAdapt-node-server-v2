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
DROP TABLE if exists weather;


CREATE TABLE AirQuality (
	id INT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(4,2),
    date TIMESTAMP,
	PRIMARY KEY(id)
);
CREATE TABLE Luminosity (
    id INT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(6,2),
    date TIMESTAMP,
    PRIMARY KEY(id)
);
CREATE TABLE Notification (
    id INT NOT NULL AUTO_INCREMENT,
    is_warning INT,
    problem VARCHAR(100),
    status VARCHAR(10),
    date TIMESTAMP,
    PRIMARY KEY(id)
);
CREATE TABLE Humidity (
    id INT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(4,2),
    date TIMESTAMP,
    PRIMARY KEY(id)
);
CREATE TABLE Temperature (
	id INT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(4,2),  
    date TIMESTAMP,
    PRIMARY KEY(id)    
);
CREATE TABLE Windows (
    id INT NOT NULL AUTO_INCREMENT,
    is_on INT,
    affects VARCHAR(100),
    name VARCHAR(100),
    PRIMARY KEY(id)
);
CREATE TABLE Irrigation(
   id INT NOT NULL AUTO_INCREMENT,
   is_on INT,
   affects VARCHAR(100),
   PRIMARY KEY(id)
);
CREATE TABLE Sprinklers(
   id INT NOT NULL AUTO_INCREMENT,
   is_on INT,
   affects VARCHAR(100),
   PRIMARY KEY(id)
);
CREATE TABLE Heating(
    id INT NOT NULL AUTO_INCREMENT,
    affects VARCHAR(100),
    type VARCHAR(50),
    value INT,
    PRIMARY KEY(id)
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

-- Prueba 1 - Warning humidity y error luminosity
INSERT INTO smartgreenadapt.temperature (amount, date)
VALUES (30.00, '2022-03-22 12:10:44');
INSERT INTO smartgreenadapt.luminosity (amount, date)
VALUES (1600.00, '2022-03-22 12:10:44');
INSERT INTO smartgreenadapt.airquality (amount, date)
VALUES (10.00, '2022-03-28 12:10:44');
INSERT INTO smartgreenadapt.humidity (amount, date)
VALUES (40.00, '2022-03-22 12:10:44');

-- Prueba 2 - Error luminosity y warning airquality
INSERT INTO smartgreenadapt.temperature (amount, date)
VALUES (20.00, '2022-03-23 12:10:44');
INSERT INTO smartgreenadapt.luminosity (amount, date)
VALUES (-10.00, '2022-03-23 12:10:44');
INSERT INTO smartgreenadapt.airquality (amount, date)
VALUES (60.00, '2022-03-23 12:10:44');
INSERT INTO smartgreenadapt.humidity (amount, date)
VALUES (70.00, '2022-03-23 12:10:44');

-- Prueba 3 - Warning luminosity y error temperature
INSERT INTO smartgreenadapt.temperature (amount, date)
VALUES (50.00, '2022-03-24 12:10:44');
INSERT INTO smartgreenadapt.luminosity (amount, date)
VALUES (1200.00, '2022-03-24 12:10:44');
INSERT INTO smartgreenadapt.airquality (amount, date)
VALUES (30.00, '2022-03-24 12:10:44');
INSERT INTO smartgreenadapt.humidity (amount, date)
VALUES (70.00, '2022-03-24 12:10:44');





INSERT INTO smartgreenadapt.notification (is_warning, problem, status, date)
VALUES (0, 'Temperature', 'low', '2022-03-22 12:10:44');
INSERT INTO smartgreenadapt.notification (is_warning, problem, status, date)
VALUES (0, 'Humidity', 'high', '2022-03-23 12:10:44');
INSERT INTO smartgreenadapt.notification (is_warning, problem, status, date)
VALUES (1, 'Temperature', 'error', '2022-03-24 12:10:44');


