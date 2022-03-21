'use strict';

var Riego = require('../service/RiegoService');

module.exports.deleteIrrigation = function deleteIrrigation (req, res, next) {

    Riego.deleteIrrigation(req.swagger.params, res, next);

};

module.exports.getIrrigation = function getIrrigation (req, res, next) {

    Riego.getIrrigation(req.swagger.params, res, next);

};

module.exports.postIrrigation = function postIrrigation (req, res, next) {

    Riego.postIrrigation(req.swagger.params, res, next);

};

module.exports.putIrrigation = function putIrrigation (req, res, next) {

    Riego.putIrrigation(req.swagger.params, res, next);

};
