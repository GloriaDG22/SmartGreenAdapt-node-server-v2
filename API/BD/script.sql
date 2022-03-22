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
CREATE TABLE AirQuality (
	id INT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(4,2),
    date TIMESTAMP,
    min_value DECIMAL(4,2),
    max_value DECIMAL(4,2),
	PRIMARY KEY(id)
);
CREATE TABLE Luminosity (
	id INT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(4,2),
    date TIMESTAMP,
    min_value DECIMAL(4,2),
    max_value DECIMAL(4,2),
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
    min_value DECIMAL(4,2),
    max_value DECIMAL(4,2),
    PRIMARY KEY(id)    
);
CREATE TABLE Temperature (
	id INT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(4,2),  
    date TIMESTAMP,
    min_value DECIMAL(4,2),
    max_value DECIMAL(4,2),
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
     is_on INT,
     affects VARCHAR(100),
     type VARCHAR(50),
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