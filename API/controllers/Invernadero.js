'use strict';

var Invernadero = require('../service/InvernaderoService');

module.exports.deleteGreenhouse = function deleteGreenhouse (req, res, next) {

    Invernadero.deleteGreenhouse(req.swagger.params, res, next);

};

module.exports.getInvernadero = function getInvernadero (req, res, next) {

    Invernadero.getInvernadero(req.swagger.params, res, next);

};

module.exports.postInvernadero = function postInvernadero (req, res, next) {

    Invernadero.postInvernadero(req.swagger.params, res, next);

};

module.exports.putInvernadero = function putInvernadero (req, res, next) {

    Invernadero.putInvernadero(req.swagger.params, res, next);

};
