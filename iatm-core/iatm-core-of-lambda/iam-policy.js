function generateAllowPolicy(principalId, resource, methodArn) {
    return generatePolicy(principalId, 'Allow', resource);
}

function generateDenyPolicy(principalId, resource) {
    return generatePolicy(principalId, 'Deny', resource);    
}

// Help function to generate an IAM policy
var generatePolicy = function(principalId, effect, resource) {

    return new Promise(function(resolve,reject){
        // Required output:
        var authResponse = {};
        authResponse.principalId = principalId;
        if (effect && resource) {
        	console.log('effect:'+ effect + 'resource:'+ resource);
            var policyDocument = {};
            policyDocument.Version = '2012-10-17'; // default version
            policyDocument.Statement = [];
            var statementOne = {};
            statementOne.Action = 'execute-api:Invoke'; // default action
            statementOne.Effect = effect;
            statementOne.Resource = resource;
            policyDocument.Statement[0] = statementOne;
            authResponse.policyDocument = policyDocument;
        }
        resolve(authResponse);
    });
};

module.exports = {
    generateAllowPolicy, generateDenyPolicy
}