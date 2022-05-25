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
 * Eliminación de datos de riego.
 * Eliminación un dato de riego en la base de datos.
 *
 * idIrrigation Integer Id del dato de riego

 * returns String
 **/
module.exports.deleteIrrigation = function(req, res, next) {
    console.log("Delete air quality data");
    var query = 'DELETE FROM Irrigation WHERE id = ?';

    connection.query(query, [req.id.originalValue], function (error, result) {
        if (error) throw error;

        res.send({
            message: result
        });
    });
};


/**
 * Devuelve todos los datos relacionados con el riego.
 * Devuelve todos los datos relacionados con el riego.
 *

 * returns String
 **/
module.exports.getIrrigation = function(req, res, next) {
    console.log("get irrigation data");

    var query = 'SELECT * FROM irrigation  WHERE idGreenhouse = ' + req.idGreenhouse.originalValue;

    connection.query(query, function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};


/**
 * Registra un nuevo dato de riego.
 * Creacion de nuevos datos asociados al riego.
 *
 * irrigation Irrigation 

 * returns String
 **/
module.exports.postIrrigation = function(req, res, next) {
    console.log("Post irrigation data");

    var query = 'INSERT INTO irrigation SET ?';
    var is_on;
    if(req.undefined.originalValue.is_on){
        is_on = 1;
    } else {
        is_on = 0;
    }

    var data = {
        affects: req.undefined.originalValue.affects,
        is_on: is_on,
        idGreenhouse : req.undefined.originalValue.idGreenhouse
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
            client.publish('irrigation', req.undefined.originalValue.is_on.toString(), options);
        }
    });

    client.on('error', function (error) {
        /** TODO no fuciona mqtt
         *  console.log('Error, cannot connect to MQTT ' + error);
         */
    });
};


/**
 * Modifica un dato de riego previamente registrado
 * Modifica un dato de riego previamente registrado
 *
 * irrigation Irrigation 

 * returns String
 **/
module.exports.putIrrigation = function(req, res, next) {
    console.log("Put air quality data");

    var query = 'UPDATE irrigation SET ? WHERE id = ?';
    var is_on;
    if(req.undefined.originalValue.is_on){
        is_on = 1;
    } else {
        is_on = 0;
    }

    var data = {
        affects: req.undefined.originalValue.affects,
        is_on: is_on,
        idGreenhouse : req.undefined.originalValue.idGreenhouse
    }

    connection.query(query, [data, req.undefined.originalValue.id], function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};




