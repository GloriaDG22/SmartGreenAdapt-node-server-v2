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
 * Eliminación de datos de ventana.
 * Eliminación un dato de ventana en la base de datos.
 *
 * idWindow Integer Id del dato de ventana

 * returns String
 **/
module.exports.deleteWindow = function(req, res, next) {
    console.log("Delete window data");
    var query = 'DELETE FROM airquality WHERE id = ?';

    connection.query(query, [req.idWindows.originalValue], function (error, result) {
        if (error) throw error;

        res.send({
            message: result
        });
    });
};


/**
 * Devuelve todas las ventanas.
 * Devuelve todos los datos relacionados con el estado Ventana
 *

 * returns String
 **/
module.exports.getWindows = function(req, res, next) {
    console.log("get window data");

    var query = 'SELECT * FROM windows';

    connection.query(query,function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};


/**
 * Registra un nuevo dato de Ventana.
 * Creación de una nueva ventana
 *
 * windows Windows 

 * returns String
 **/
module.exports.postWindow = function(req, res, next) {
    console.log("Post window data");

    var query = 'INSERT INTO windows SET ?';
    var is_on;
    if(req.undefined.originalValue.isOn){
        is_on = 1;
    } else {
        is_on = 0;
    }

    var data = {
        affects: req.undefined.originalValue.affects,
        is_on: is_on,
        name: req.undefined.originalValue.name
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
            client.publish('air quality', req.undefined.originalValue.isOn.toString(), options);
        }
    });

    client.on('error', function (error) {
        /** TODO no fuciona mqtt
         *  console.log('Error, cannot connect to MQTT ' + error);
         */
    });
};


/**
 * Modifica un dato de ventana previamente registrado
 * Modifica un dato de ventana previamente registrado
 *
 * windows Windows 

 * returns String
 **/
module.exports.putWindow = function(req, res, next) {
    console.log("Put air quality data");

    var query = 'UPDATE windows SET ? WHERE id = ?';
    var is_on;
    if(req.undefined.originalValue.isOn){
        is_on = 1;
    } else {
        is_on = 0;
    }

    var data = {
        affects: req.undefined.originalValue.affects,
        is_on: is_on,
        name: req.undefined.originalValue.name
    }

    connection.query(query, [data, req.undefined.originalValue.idWindows], function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};




