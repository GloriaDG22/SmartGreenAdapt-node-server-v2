'use strict';

var Aspersor = require('../service/AspersorService');

module.exports.deleteSprinklers = function deleteSprinklers (req, res, next) {

    Aspersor.deleteSprinklers(req.swagger.params, res, next);

};

module.exports.getSprinklers = function getSprinklers (req, res, next) {

    Aspersor.getSprinklers(req.swagger.params, res, next);

};

module.exports.postSprinklers = function postSprinklers (req, res, next) {

    Aspersor.postSprinklers(req.swagger.params, res, next);

};

module.exports.putSprinklers = function putSprinklers (req, res, next) {

    Aspersor.putSprinklers(req.swagger.params, res, next);

};
