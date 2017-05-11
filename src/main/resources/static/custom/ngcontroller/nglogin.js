/**
 *1. Angular controller to check duplicate user name and mobile for user registration 
 *2. For Player and Ground Crew registration
 */

var app = angular.module('loginApp', []);

app.controller('loginCtlr', ['$scope','userNameCheck', 'userMobileCheck', 'registerPlayer', 'registerGCrew', function($scope, userNameCheck, userMobileCheck, registerPlayer, registerGCrew) {
	
	/* Method to clear success message for player */
	$scope.clearSuccessMsg = function(){
		$scope.newPlayerMessage = "";
		$scope.newGCrewMessage = "";
	};
	
	
	/* Method to check Player Username */
	$scope.validatePlayerUserName = function() {
			if( $scope.pusername != undefined && ($scope.pusername).length > 0){
					userNameCheck.isDuplicateUserName($scope.pusername, function(result) {	
						$scope.invlaidPlayerUserName = result;
					});
			}
	};
	
	/* Method to check GCrew Username */
	$scope.validateGCrewUserName = function() {
		if(($scope.gusername).length > 0){
				userNameCheck.isDuplicateUserName($scope.gusername, function(result) {	
					$scope.invlaidGCrewUserName = result;
				});
			}
	};
	
	
	/* Method to check Player mobile */
	$scope.validatePlayerMobile = function() {
		if((''+ $scope.pmobile).length == 10){
			userMobileCheck.isDuplicateMobile($scope.pmobile, function(result) {	
					$scope.invlaidPlayerMobile = result;
				});
			}
	};
	
	
	/* Method to check GCrew mobile */
	$scope.validateGCrewMobile = function() {
		if(('' + $scope.gmobile).length == 10){
			userMobileCheck.isDuplicateMobile($scope.gmobile, function(result) {	
					$scope.invlaidGCrewUserName = result;
				});
			}
	};
	
	
	/* Method to add player */
	$scope.addPlayer = function(){
		registerPlayer.savePlayer($scope.pfname, $scope.plname, $scope.pmobile, $scope.pusername, $scope.ppassword, function(result){
			$scope.pfname = "";
			$scope.plname = ""; 
			$scope.pmobile = ""; 
			$scope.pusername = ""; 
			$scope.ppassword = "";
			$scope.pcpassword = "";
			$scope.pregform.$setPristine();
			$scope.pregform.$setUntouched();
			$scope.newPlayerMessage = result;
		});
	};
	
	/* Method to add gcrew */
	$scope.addGCrew = function(){

		registerGCrew.saveGCrew($scope.gfname, $scope.glname, $scope.gmobile, $scope.gusername, $scope.gpassword, function(result){
			$scope.gfname = "";
			$scope.glname = ""; 
			$scope.gmobile = ""; 
			$scope.gusername = ""; 
			$scope.gpassword = "";
			$scope.gcpassword = "";
			$scope.gregform.$setPristine();
			$scope.gregform.$setUntouched();
			$scope.newGCrewMessage = result;
		});
	};
	
	
	}]);


/* service to check duplicate user name */
app.service('userNameCheck', [ '$http', function($http) {
	
	this.isDuplicateUserName = function(username, cb) {
		var data = {
			username : username
		}
		$http({
			url : 'username-check',
			method : 'POST',
			params : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			cb("Unable to validate Username now. Try later.");
		});
	};

} ]);

/* service to check duplicate mobile */
app.service('userMobileCheck', [ '$http', function($http) {
	this.isDuplicateMobile = function(mobile, cb) {
		var data = {
			mobile : mobile
		}
		$http({
			url : 'mobile-check',
			method : 'POST',
			params : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			cb("Unable to validate Mobile now. Try later.");
		});
	};

} ]);


/* service to register player  */
app.service('registerPlayer', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	
	this.savePlayer = function(firstname, lastname, mobile, username, password, cb) {
		var data = 'firstname=' + firstname + '&lastname=' + lastname + '&mobile='+ mobile 
					+ '&username=' +username + '&password=' + password;
		$http({
			url : 'register-player',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(reason){
			cb("Unable to process your request now. Try later.");
		});
		
	};

}]);

	/* service to register Ground Crew  */
	app.service('registerGCrew', [ '$http', function($http) {
		this.saveGCrew = function(firstname, lastname, mobile, username, password, cb) {
			var data = 'firstname=' + firstname + '&lastname=' + lastname + '&mobile='+ mobile 
			+ '&username=' +username + '&password=' + password;
			$http({
				url : 'register-gcrew',
				method : 'POST',
				data : data
			}).then(function(response) {
				cb(response.data);
			}, function(failure) {
				cb("Unable to process your request now. Try later.");
			});
		};

	
} ]);
