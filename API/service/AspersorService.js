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
 * Eliminación de datos de aspersores.
 * Eliminación un dato de aspersores en la base de datos.
 *
 * idSprinklers Integer Id del dato de aspersor

 * returns String
 **/
module.exports.deleteSprinklers = function(req, res, next) {
    //Parameters
    console.log("Delete sprinklers data");
    var query = 'DELETE FROM sprinklers WHERE id = ?';

    connection.query(query, [req.id.originalValue], function (error, result) {
        if (error) throw error;

        res.send({
            message: result
        });
    });
};


/**
 * Devuelve todos los datos relacionados con los aspersores.
 * Devuelve todos los datos relacionados con los aspersores.
 *

 * returns String
 **/
module.exports.getSprinklers = function(req, res, next) {
    console.log("get sprinklers data");

    var query = 'SELECT * FROM sprinklers WHERE idGreenhouse = ' + req.idGreenhouse.originalValue;

    connection.query(query, function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};


/**
 * Registra un nuevo dato de aspersor.
 * Creacion de nuevos datos asociados a los aspersores.
 *
 * sprinklers Sprinklers 

 * returns String
 **/
module.exports.postSprinklers = function(req, res, next) {
    console.log("Post sprinklers data");

    var query = 'INSERT INTO sprinklers SET ?';
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
            client.publish('sprinklers', req.undefined.originalValue.isOn.toString(), options);
        }
    });

    client.on('error', function (error) {
        /** TODO no fuciona mqtt
         *  console.log('Error, cannot connect to MQTT ' + error);
         */
    });
};


/**
 * Modifica un dato de aspersor previamente registrado
 * Modifica un dato de aspersor previamente registrado
 *
 * sprinklers Sprinklers 

 * returns String
 **/
module.exports.putSprinklers = function(req, res, next) {
    var query = 'UPDATE airquality SET ? WHERE id = ?';
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




