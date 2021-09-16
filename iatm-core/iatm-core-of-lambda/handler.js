"use strict"
var jwt = require('jsonwebtoken');
var zlib = require("zlib");
var iamPolicy = require("./iam-policy");
const constant = require('./constants');

module.exports.handler  = async (event,context, callback) => {
mainfunction(event,context, callback);

    return {status: "done"}
}
 exports.handler =  function mainfunction(event, context, callback) {
	console.log("Lambda authorizer invoked");
	var tokenCompressed = event.headers.Authorization;

    if(tokenCompressed == undefined) {
        tokenCompressed = event.headers.authorization;
    }
    var buffer = Buffer.from(tokenCompressed, 'base64');
    var token = "13333";
    zlib.gunzip(buffer, (err, buffer) => { 
    if (err) return callback(err);
    token = buffer.toString('utf8');
      console.log("token is new........" + token); 
     console.log("token is........" + token);
    var httpMethod = event.httpMethod;
    console.log("http method is........" + httpMethod);
    var resourcePath = event.resource;
    console.log("resource is........" + resourcePath);
    var methodArn = event.methodArn;
    console.log("method Arn is........" + methodArn);
    var cipher = constant.secretKey;
    if(cipher == null){
       setTimeout(function() {}, 1000);
       cipher = constant.secretKey;       
	}  
	try{
		var decoded = jwt.verify(token, cipher);
		console.log("decode value is... " + decoded);
		var parsedPayload = JSON.parse(JSON.stringify(decoded));
		console.log('endpoints:' + parsedPayload.endpoints); 
		console.log('resourcePath:' + resourcePath); 
		console.log('httpMethod:' + httpMethod); 
		var endPoints = parsedPayload.endpoints;
		verifyEndPoint(endPoints, resourcePath, httpMethod, methodArn).then(function(iamPolicy){
			callback(null, iamPolicy);
		
    }).catch(function(err){
        callback("Unauthorized");
    });
	} catch(err) {
	     console.log('error11111111 .............'); 
        console.log("6666" + err); 
		callback("Unauthorized");
	};
    });

};

function verifyEndPoint(endPoints, resourcePath, httpMethod, methodArn) {
    
    console.log("Verifying End Points");
    return new Promise(function(resolve,reject){

        try{
            var flag = false;

            for(var i=0; i < endPoints.length; i++) {
                
                var endPoint = endPoints[i];
                console.log("api name ." + endPoint.apiName);
                 console.log("method name ." + endPoint.operationName);
                if(endPoint.apiName == resourcePath && endPoint.operationName == httpMethod) {
                    flag = true;
                    console.log("Found Permission.");
                    break;
                }
            }

            if(flag) {
                //Allow
                console.log("ALLOW POLICY to be generated.");
                iamPolicy.generateAllowPolicy("user", methodArn).then(function(authResponse){
                    console.log("Allow Policy --> ", authResponse);
                    resolve(authResponse);  
                });
            } else {            
                //Deny
                console.log("DENY POLICY to be generated.");
                iamPolicy.generateDenyPolicy("user", methodArn).then(function(authResponse){
                    console.log("Deny Policy --> ", JSON.stringify(authResponse));
                    reject(authResponse);                        
                });
            }} catch(err){
            console.log("Failed to authorize jwt token.", err);
            reject(err);
        };
    });
}

