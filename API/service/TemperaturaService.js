'use strict';

var mysql = require('mysql');
var connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'root',
    database: 'smartgreenadapt'
});

connection.connect();

var options = {
    clientId: 'mqtthp'
}

var mqtt = require('mqtt');
var client = mqtt.connect('mqtt://localhost:1883', options);


/**
 * Eliminación de datos de temperatura.
 * Eliminación un dato de temperatura en la base de datos.
 *
 * idTemperature Integer Id del dato de temperatura

 * returns String
 **/
module.exports.deleteTemperature = function(req, res, next) {

    console.log("Delete temperature data");
    var query = 'DELETE FROM Temperature WHERE id = ?';

    connection.query(query, [req.idTemperature.originalValue], function (error, result) {
        if (error) throw error;

        res.send({
            message: result
        });
    });
};


/**
 * Devuelve todos la temperatura de una fecha concreta.
 * Devuelve todos la temperatura de una fecha concreta.
 *
 * date String Fecha de la recogida de la información

 * returns String
 **/
module.exports.getTemperature = function(req, res, next) {
    console.log("get temperature data");

    var query = 'SELECT * FROM Temperature order by date desc limit 1';

    connection.query(query, function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};


/**
 * Registra un nuevo dato de temperatura.
 * Creacion de nuevos datos asociados a la temperatura.
 *
 * temperature Temperature 

 * returns String
 **/
module.exports.postTemperature = function(req, res, next) {
    console.log("Post temperature data");

    var query = 'INSERT INTO Temperature SET ?';
    var date;

    if (!req.undefined.originalValue.date) date = new Date();
    else date = req.undefined.originalValue.date;

    var data = {
        amount: Number((req.undefined.originalValue.amount).toFixed(2)),
        date: date,
        min_value: Number((req.undefined.originalValue.minValue).toFixed(2)),
        max_value: Number((req.undefined.originalValue.maxValue).toFixed(2))
    }

    connection.query(query, [data], function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });

    /**
     * MQTT
     */

    client.on('connect', function () {

        let options = {
            retain: true,
            qos: 1
        };
        if (client.connected === true) {
            client.publish('temperature', req.undefined.originalValue.amount.toString(), options);
        }
    });

    client.on('error', function (error) {
        /** TODO no fuciona mqtt
         *  console.log('Error, cannot connect to MQTT ' + error);
         */
    });
};


/**
 * Modifica un dato de temperatura previamente registrado
 * Modifica un dato de temperatura previamente registrado
 *
 * temperature Temperature 

 * returns String
 **/
module.exports.putTemperature = function(req, res, next) {
    console.log("Put temperature data");

    var query = 'UPDATE Temperature SET ? WHERE id = ?';
    var data = {
        amount: Number((req.undefined.originalValue.amount).toFixed(2)),
        date: req.undefined.originalValue.date.toString(),
        min_value: Number((req.undefined.originalValue.minValue).toFixed(2)),
        max_value: Number((req.undefined.originalValue.maxValue).toFixed(2))
    }

    connection.query(query, [data, req.undefined.originalValue.idTemperature], function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};