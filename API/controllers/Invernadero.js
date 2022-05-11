




'use strict';

var Invernadero = require('../service/InvernaderoService');

module.exports.deleteGreenhouse = function deleteGreenhouse (req, res, next) {

    Invernadero.deleteGreenhouse(req.swagger.params, res, next);

};
/*
module.exports.getGreenhouse = function getGreenhouse (req, res, next) {

    Invernadero.getGreenhouse(req.swagger.params, res, next);

};

 */

module.exports.getGreenhouses = function getGreenhouses (req, res, next) {

    Invernadero.getGreenhouses(req.swagger.params, res, next);

};

module.exports.postGreenhouse = function postGreenhouse (req, res, next) {

    Invernadero.postGreenhouse(req.swagger.params, res, next);

};

module.exports.putGreenhouses = function putGreenhouses (req, res, next) {

    Invernadero.putGreenhouses(req.swagger.params, res, next);

};
