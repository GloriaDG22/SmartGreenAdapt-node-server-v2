'use strict';

var Ventana = require('../service/VentanaService');

module.exports.deleteWindow = function deleteWindow (req, res, next) {

    Ventana.deleteWindow(req.swagger.params, res, next);

};

module.exports.getWindows = function getWindows (req, res, next) {

    Ventana.getWindows(req.swagger.params, res, next);

};

module.exports.postWindow = function postWindow (req, res, next) {

    Ventana.postWindow(req.swagger.params, res, next);

};

module.exports.putWindow = function putWindow (req, res, next) {

    Ventana.putWindow(req.swagger.params, res, next);

};
