/* eslint-disable one-var */
"use strict";
const parameter_readers = require('./system_parameter_readers');


var resourceRegion = process.env.SSMRegion;
var customer = process.env.Customer;
var env = process.env.Environment;

//Get all data on path - heirarchy if data required on multiple path pass '/'
var resourcePath = '/';
var resource_by_path = parameter_readers.getParametersFromSystemManagerByPath(resourcePath, resourceRegion);

const secretKey = resource_by_path.get('/'+ customer.toLowerCase() + '/' + env.toLowerCase() + '/' + 'Iatm_Token_Secret');

module.exports = {
    secretKey
};