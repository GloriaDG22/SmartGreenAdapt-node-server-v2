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
 * Eliminación de datos del usuario.
 * Eliminación de un dato de usuario guardado en la base de datos.
 *
 * idUsuario Integer Id del user

 * returns String
 **/
module.exports.deleteUser = function(req, res, next) {
    //Parameters
    console.log("Delete user data");
    var query = "DELETE FROM UserInfo WHERE email = '" + req.email.originalValue + "'"

    connection.query(query, function (error, result) {
        if (error) throw error;

        res.send({
            message: result
        });
    });
};


/**
 * Devuelve todos los datos relacionados con los usuarios.
 * Devuelve todos los datos relacionados con los usuarios.
 *

 * returns String
 **/
module.exports.getUsers = function(req, res, next) {
    //Parameters
    console.log("Get all users");
    var query = 'SELECT * FROM UserInfo'
    connection.query(query, function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};


/**
 * Registra un nuevo dato de usuario.
 * Creacion de nuevos datos asociados a los usuarios.
 *
 * user User 

 * returns String
 **/
module.exports.postUser = function(req, res, next) {
    //Parameters
    console.log("Post a new user data");
    var query = 'INSERT INTO UserInfo SET ?'

    var data = {
        username: req.undefined.originalValue.username,
        password: req.undefined.originalValue.password,
        email: req.undefined.originalValue.email
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
 * Modifica un dato de usuario previamente registrado
 * Modifica un dato de usuarios previamente registrado
 *
 * user User 

 * returns String
 **/
module.exports.putUser = function(req, res, next) {
    //Parameters
    console.log("Update a user");
    var query = 'UPDATE UserInfo SET ? WHERE id = ?';

    var data = {
        username: req.undefined.originalValue.username,
        password: req.undefined.originalValue.password,
        email: req.undefined.originalValue.email
    }
    connection.query(query, [data, req.undefined.originalValue.idUsuario], function (error, results) {
        if (error) throw error;

        res.send({
            message: results
        });
    });
};




