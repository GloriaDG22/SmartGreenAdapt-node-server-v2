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
 * Devuelve todos los datos relacionados con notificaciones.
 * Devuelve todos los datos relacionados con notificaciones.
 *
 * date String Fecha de la recogida de la información

 * returns String
 **/
module.exports.getNotification = function(req, res, next) {
    console.log("get notification data");

    var query = 'SELECT * FROM notification WHERE idUsuario = '+ req.idUsuario.originalValue;

    connection.query(query,  function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};


/**
 * Registra una nueva notificación.
 * Creacion de nuevas notificaciones.
 *
 * notification Notification 

 * returns String
 **/
module.exports.postNotification = function(req, res, next) {
    console.log("Post notification data");

    var query = 'INSERT INTO notification SET ?';
    var date;

    if (!req.undefined.originalValue.date) date = new Date();
    else date = req.undefined.originalValue.date;

    var is_warning;
    if(req.undefined.originalValue.is_warning){
        is_warning = 1;
    } else {
        is_warning = 0;
    }

    var data = {
        date: date,
        is_warning: is_warning,
        status: req.undefined.originalValue.status,
        problem: req.undefined.originalValue.problem,
        idUsuario: req.undefined.originalValue.idUsuario,
        greenhouseName: req.undefined.originalValue.greenhouseName
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
            client.publish('notification', req.undefined.originalValue.amount.toString(), options);
        }
    });

    client.on('error', function (error) {
        /** TODO no fuciona mqtt
         *  console.log('Error, cannot connect to MQTT ' + error);
         */
    });
};




