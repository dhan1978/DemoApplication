'use strict';
const awsParamStore = require('aws-param-store');
var HashMap = require('hashmap');

var multiple_params_by_path = new HashMap();
var single_params_store = new HashMap();
var multiple_params_store = new HashMap();
// Get all parameters from path 
function getParametersFromSystemManagerByPath(resourcePath, resourceRegion) {
    console.log('in the getParameterFromSystemManager function')
    try {
        let parameters = awsParamStore.getParametersByPathSync(resourcePath, { WithDecryption: true, region: resourceRegion });
        for (const i of parameters) {
            multiple_params_by_path.set(i.Name, i.Value);
        }
        console.log("SSM Map size = " + multiple_params_by_path.size);
    } catch (error) {
        console.log("resourcePath :" + resourcePath + "  resourceRegion :" + resourceRegion + "-->" + error);
        error;
    }

    return multiple_params_by_path;
}
// get single parameter from absolute path
function getParameterFromSystemManager(resourcePath, resourceRegion) {
    try {
        let results = awsParamStore.getParameterSync(resourcePath, { WithDecryption: true, region: resourceRegion });
        single_params_store.set(results.Name, results.Value);
        console.log("Name = " + results.Name + "value-->" + results.Value);
    } catch (error) {
        console.log("resourcePath :" + resourcePath + "  resourceRegion :" + resourceRegion + "-->" + error);
        error;
    }

    return single_params_store;
}
// Get Multiple parameters from absolute file path
function getParametersFromSystemManager(resourcePath, resourceRegion) {
    try {
        let parameters = awsParamStore.getParametersSync(resourcePath, { WithDecryption: true, region: resourceRegion });
        for (const i of parameters.Parameters) {
            multiple_params_store.set(i.Name, i.Value);
        }
        console.log("SSM Map size = " + multiple_params_store.size);
    } catch (error) {
        console.log("resourcePath :" + resourcePath + "  resourceRegion :" + resourceRegion + "-->" + error);
        error;
    }

    return multiple_params_store;
}

module.exports = {
    getParametersFromSystemManagerByPath,
    getParameterFromSystemManager,
    getParametersFromSystemManager
};