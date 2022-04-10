'use strict';

var Usuarios = require('../service/UsuariosService');

module.exports.deleteUser = function deleteUser (req, res, next) {

    Usuarios.deleteUser(req.swagger.params, res, next);

};

module.exports.getUsers = function getUsers (req, res, next) {

    Usuarios.getUsers(req.swagger.params, res, next);

};

module.exports.postUser = function postUser (req, res, next) {

    Usuarios.postUser(req.swagger.params, res, next);

};

module.exports.putUser = function putUser (req, res, next) {

    Usuarios.putUser(req.swagger.params, res, next);

};
