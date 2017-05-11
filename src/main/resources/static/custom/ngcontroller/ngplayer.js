/**
 * Angular controller for GCrew functions
 */

var app = angular.module('playerApp', ['angularUtils.directives.dirPagination']);

app.controller('playerCtlr',[ '$scope', 'getlocation', 'teamNameCheck', 'createTeam', 'getTeams', 'searchTeam', 'joinTeam' ,function($scope, getlocation, teamNameCheck, createTeam, getTeams, searchTeam, joinTeam) {
	
	
	/* clear jtrMessage */
	
	$scope.clrjtrmsg = function(){
		$scope.jtrMessage = "";
	};
	
	/*Join team request */
	$scope.goJoinTeam = function(teamid){
		joinTeam.sendJoinRequest(teamid, function(result){
			$scope.jtrMessage = result;
		});
	};
	
	
	
	/* pagination */
	$scope.currentPage = 1;
	$scope.pageSize = 1;
	
	
	/* clear error message for search */
	
	$scope.clearsearcherror = function(){
		$scope.searcherror = "";
		$scope.jtrMessage = "";
		$scope.teams = "";
	};
	
	/* function to search team */
	$scope.searchTeams = function(){
		searchTeam.teamSearchByKey($scope.searchkey, $scope.searchvalue, function(result){
			if(result == ""){
				$scope.searcherror = "No matches found for the search";
			} else if(result == 'fail'){
				$scope.searcherror = "Unable to process the results now. Try later.";
			} else {
				$scope.teams = result;
				$scope.searchvalue = "";
				$scope.joinForm.$setPristine();
				$scope.joinForm.$setUntouched();
			}
		});
	};
	
	
	/* ng-init function*/
	$scope.dosomething = function(){
		$scope.getMyTeams();
	};
	
	/* Method to get my teams */
	
	$scope.getMyTeams = function(){
		getTeams.getTeamsByPlayer(function(result){
			$scope.players = result;
		});
	};
	
	/* method to create team */
	$scope.addTeam = function(){
		createTeam.addCreateTeam($scope.teamname, $scope.zip, $scope.city, $scope.state, $scope.country, function(result){
			$scope.newteamcreated = result;
			$scope.teamname = ""; 
			$scope.zip = ""; 
			$scope.city= ""; 
			$scope.state = ""; 
			$scope.country = "";
			$scope.teamform.$setPristine();
			$scope.teamform.$setUntouched();
			$scope.getMyTeams();
		});
	};
	
	
	/* method to check duplicate team name*/
	$scope.checkTeamName = function(){
		$scope.newteamcreated = "";
		if((''+ $scope.teamname).length > 0){
			teamNameCheck.isDuplicateTeamName($scope.teamname, function(result){
				$scope.invalidteamname = result;
			});
		}
	};
	
	
	/* method to get location details */
	$scope.getCityState = function() {
		if(( ''+ $scope.zip).length == 5){
		getlocation.getDetailsByZip( $scope.zip, function(result){
				if(result != 'fail'){
					$scope.city = result.city;
					$scope.state = result.state;
					$scope.country = result.country;
					$scope.zipmsg = "";
					if((result.error).length > 0){
						$scope.zipmsg = "Invlaid Zip Code";
					}
				} else {
					$scope.city = "";
					$scope.state = "";
					$scope.country = "";
					$scope.zipmsg = "";
				}
			});
		} else {
			$scope.city = "";
			$scope.state = "";
			$scope.country = "";
			$scope.zipmsg = "";
		}
	};
	
}]);


/* service to get city state  and country */
app.service('getlocation', [ '$http', function($http) {
	
	this.getDetailsByZip = function(zip, cb) {
		var qUrl = 'http://ziptasticapi.com/'+ zip;
		
		$http({
			url : qUrl,
			method : 'GET',
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			cb("fail");
		});
	};

} ]);


/* service to check duplicate team name */
app.service('teamNameCheck', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.isDuplicateTeamName = function(teamname, cb) {
		var data = "teamname=" + teamname; 
		$http({
			url : 'teamname-check',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			cb("Unable to validate team name now. Try later.");
		});
	};

} ]);


/* service to check duplicate team name */
app.service('createTeam', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.addCreateTeam = function(teamname, zip, city, state, country, cb) {
		var data = "teamname=" + teamname + "&zip="+ zip + "&city="+city + "&state=" + state + "&country=" + country; 
		$http({
			url : 'create-team',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			cb("Unable to create team now. Try later.");
		});
	};

} ]);


/* service to check duplicate team name */
app.service('getTeams', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.getTeamsByPlayer = function(cb) { 
		$http({
			url : 'get-myteams',
			method : 'POST'
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);


/* service to search teams */
app.service('searchTeam', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.teamSearchByKey = function(searchkey, searchval, cb) {
		var data = "key=" + searchkey + "&val="+ searchval; 
		$http({
			url : 'search-team',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			cb("fail");
		});
	};

} ]);


/* service to join team request */
app.service('joinTeam', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.sendJoinRequest = function(teamid, cb) {
		var data = "teamid=" + teamid; 
		$http({
			url : 'join-team',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			cb("Unable to process your request. Try later.");
		});
	};

} ]);




