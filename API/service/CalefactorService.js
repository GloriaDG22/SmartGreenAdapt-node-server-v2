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
 * Eliminación de datos de calefactor.
 * Eliminación un dato de calefactor en la base de datos.
 *
 * idHeating Integer Id del dato de heating

 * returns String
 **/
module.exports.deleteHeating = function(req, res, next) {
    console.log("Delete heating data");
    var query = 'DELETE FROM heating WHERE id = ?';

    connection.query(query, [req.idHeating.originalValue], function (error, result) {
        if (error) throw error;

        res.send({
            message: result
        });
    });
};


/**
 * Devuelve todos los datos relacionados con los calefactores.
 * Devuelve todos los datos relacionados con los calefactores.
 *

 * returns String
 **/
module.exports.getHeatings = function(req, res, next) {
    console.log("get heatings data");

    var query = 'SELECT * FROM heatings WHERE idGreenhouse = ' + req.idGreenhouse.originalValue;

    connection.query(query, function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};


/**
 * Registra un nuevo dato de calefactor.
 * Creacion de nuevos datos asociados a los calefactores.
 *
 * heating Heating 

 * returns String
 **/
module.exports.postHeating = function(req, res, next) {
    console.log("Post heating data");

    var query = 'INSERT INTO heating SET ?';
    var is_on;
    if(req.undefined.originalValue.isOn){
        is_on = 1;
    } else {
        is_on = 0;
    }

    var data = {
        affects: req.undefined.originalValue.affects,
        is_on: is_on,
        type: req.undefined.originalValue.type,
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
            client.publish('heating', req.undefined.originalValue.isOn.toString(), options);
        }
    });

    client.on('error', function (error) {
        /** TODO no fuciona mqtt
         *  console.log('Error, cannot connect to MQTT ' + error);
         */
    });
};


/**
 * Modifica un dato de calefactor previamente registrado
 * Modifica un dato de calefactor previamente registrado
 *
 * heating Heating 

 * returns String
 **/
module.exports.putHeating = function(req, res, next) {
    console.log("Put heating data");

    var query = 'UPDATE heating SET ?';
    var is_on;
    if(req.undefined.originalValue.isOn){
        is_on = 1;
    } else {
        is_on = 0;
    }

    var data = {
        affects: req.undefined.originalValue.affects,
        is_on: is_on,
        type: req.undefined.originalValue.type,
        idGreenhouse : req.undefined.originalValue.idGreenhouse
    }

    connection.query(query, [data, req.undefined.originalValue.idHeating], function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};




