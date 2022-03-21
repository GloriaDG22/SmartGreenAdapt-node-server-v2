'use strict';

var Calefactor = require('../service/CalefactorService');

module.exports.deleteHeating = function deleteHeating (req, res, next) {

    Calefactor.deleteHeating(req.swagger.params, res, next);

};

module.exports.getHeatings = function getHeatings (req, res, next) {

    Calefactor.getHeatings(req.swagger.params, res, next);

};

module.exports.postHeating = function postHeating (req, res, next) {

    Calefactor.postHeating(req.swagger.params, res, next);

};

module.exports.putHeating = function putHeating (req, res, next) {

    Calefactor.putHeating(req.swagger.params, res, next);

};
