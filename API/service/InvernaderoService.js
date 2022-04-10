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
 * Eliminación de datos del invernadero
 * Eliminación de un dato de invernadero guardado en la base de datos.
 *
 * idGreenhouse Integer Id del invernadero

 * returns String
 **/
module.exports.deleteGreenhouse = function(req, res, next) {
    //Parameters
    console.log("Delete greenhouse data");

    var query = 'DELETE FROM GreenHouse WHERE id = ?';

    connection.query(query, [req.idGreenhouse.originalValue], function (error, result) {
        if (error) throw error;

        res.send({
            message: result
        });
    });
};


/**
 * Devuelve todos los datos relacionados con el invernadero.
 * Devuelve todos los datos relacionados con el invernadero.
 *
 * idUsuario Integer Id del usuario

 * returns String
 **/
module.exports.getInvernadero = function(req, res, next) {
    //Parameters
    console.log("Get greenhouse data");
    var query =  'SELECT * FROM GreenHouse WHERE idUsuario = ' + req.idUsuario.originalValue;

    connection.query(query, function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};


/**
 * Registra un nuevo dato del invernadero.
 * Creacion de nuevos datos asociados al invernadero.
 *
 * greenhouse Greenhouse 

 * returns String
 **/
module.exports.postInvernadero = function(req, res, next) {
    //Parameters
    console.log("Post a new greenhouse data");
    var query = 'INSERT INTO GreenHouse SET ?';

    var data = {
        idUsuario: req.undefined.originalValue.idUsuario,
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
            client.publish('air quality', req.undefined.originalValue.amount.toString(), options);
        }
    });

    client.on('error', function (error) {
        /** TODO no fuciona mqtt
         *  console.log('Error, cannot connect to MQTT ' + error);
         */
    });
};


/**
 * Modifica un dato de invernadero previamente registrado
 * Modifica un dato de invernadero previamente registrado
 *
 * greenhouse Greenhouse 

 * returns String
 **/
module.exports.putInvernadero = function(req, res, next) {
    //Parameters
    console.log("Update greenhouse data");
    var query = 'UPDATE GreenHouse SET ? where id = ?';

    var data = {
        idUsuario: req.undefined.originalValue.idUsuario,
        name: req.undefined.originalValue.name
    }

    connection.query(query, [data, req.undefined.originalValue.idGreenhouse], function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};




