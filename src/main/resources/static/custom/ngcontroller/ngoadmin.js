/**
 * angular controller - oadmin activities 
 */

var app = angular.module('oadminApp', []);

app.controller('oadminCtlr',[ '$scope', 'keyfacts','newgrounds', 'enablegrounds', function($scope, keyfacts, newgrounds, enablegrounds) {
	
	
	$scope.clearApproveMsg = function(){
		$scope.approveMsg = "";
	};
	
	$scope.doinitialize = function() {
		$scope.updatecount();
		$scope.groundRequests();
	}
	
	$scope.updatecount = function(){
		
		keyfacts.isUpdatedKeyFacts(function(result){
			$scope.playercount = result.playerCount;
			$scope.teamcount = result.teamCount;
			$scope.groundcount = result.groundCount;
		});
	};
	
	$scope.groundRequests = function(){
		newgrounds.getNewGrounds(function(result){
			$scope.records = result;
		}) ;
	};
	
	$scope.approveGround = function(groundId){
		
		enablegrounds.enableNewGrounds(groundId, function(result){
			$scope.approveMsg = result;
			$scope.groundRequests();
		});
	};
	
}]);


/* service to get key facts for oadmin */
app.service('keyfacts', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	
	this.isUpdatedKeyFacts = function(cb) {
		$http({
			url : 'keyfacts-update',
			method : 'POST',
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothings
		});
	};

} ]);


/* service to new ground requests */
app.service('newgrounds', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	
	this.getNewGrounds = function(cb) {
		$http({
			url : 'get-newgrounds',
			method : 'POST',
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothings
		});
	};

} ]);


/* service to approve new ground requests */
app.service('enablegrounds', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	
	this.enableNewGrounds = function(groundId,cb) {
		var data = 'gid=' + groundId;
		$http({
			url : 'enable-newgrounds',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			cb("Unable to process your resquest now. Try later.")
		});
	};

} ]);



