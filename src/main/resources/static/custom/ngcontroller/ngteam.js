/**
 * Angular controller for in team activities
 */

var app = angular.module('teamApp', ['angularUtils.directives.dirPagination']);


app.controller('teamCtlr',[ '$scope', 'newPlayers', 'acceptJTR', 'rejectJTR', 'oldPlayerSer', 'removePlayerSer', 'pollSer', 'pollgetSer', 'pollUpSer', 'joinPollSer' ,function($scope, newPlayers, acceptJTR, rejectJTR, oldPlayerSer, removePlayerSer, pollSer, pollgetSer, pollUpSer, joinPollSer) {
	
	var teamId = "";
	
	/* clear new poll msg */
	$scope.clearNewPollMsg = function(){
		$scope.newpollcreated = "";
	};
	
	/* clear response message */
	$scope.clearnprMsg = function(){
		$scope.nprMsg = "";
	};
	
	$scope.clearrpMsg= function(){
		$scope.rpMsg = "";
	}
	
	
	/*pagination */
	/* pagination */
	$scope.currentPage = 1;
	$scope.pageSize = 3;
	
	
	
	/* initialize Page */
	$scope.doIntializePage = function(tid){
		// get new players
		$scope.getNewPlayerRequests(tid);
		
		//get old players
		$scope.getOldPlayers(tid);
		
		// set team Id
		teamId = tid;
		
		//get active polls
		$scope.activePolls(tid);
		
	};
	
	/* create poll */
	$scope.createpoll = function(){
		pollSer.createPollRequest(teamId, $scope.reqmsg,function(result){
			$scope.newpollcreated = result;
			$scope.doIntializePage(teamId);
			$scope.reqmsg = "";
			$scope.pollform.$setPristine();
			$scope.pollform.$setUntouched();
		});
	};
	
	/* Initialize for player */
	$scope.doIntializePlayerPart = function(tid){
		teamId = tid;
		$scope.activePolls(tid);
	}
	
	
	/* get active polls */
	$scope.activePolls = function(tid){
		
		pollgetSer.getAllActivePoll(tid, function(result){
			$scope.activepolls = result;
		});
	};
	
	/* get players in poll */
	
	/* update polls -- close polls */
	$scope.closePoll = function(pollid){
		pollUpSer.updatePollRequest( teamId ,pollid, function(result){
			// do nothing 
			console.log(result);
			$scope.doIntializePage(teamId);
		});
	};
	
	
	/* function to join poll*/
	$scope.joinPoll = function(pollid){
		joinPollSer.joinActivePoll(pollid, function(result){
			console.log(result);
			$scope.doIntializePlayerPart(teamId);
		});
	};
	
	
	/* function to remove player*/
	$scope.removePlayer = function(pid){
		removePlayerSer.removeOldPlayersFun(teamId, pid, function(result){
			$scope.rpMsg = result;
			$scope.getOldPlayers(teamId);
		});
	};
	
	
	/* function to get old players */
	$scope.getOldPlayers = function(tid){
		oldPlayerSer.getOldPlayersFun(tid, function(result){
			$scope.oldplayers = result;
		});
	};
	
	
	/* function to get new players request*/
	$scope.getNewPlayerRequests = function(tid){
		newPlayers.newPlayersRequests(tid, function(result){
			$scope.newplayers = result;
		});
	};
	
	/* function to approve new player request*/
	$scope.approveJTR = function(reqId){
		acceptJTR.acceptNewPlayerJTR(reqId, function(result){
			$scope.nprMsg = result;
			$scope.getNewPlayerRequests(teamId);
			$scope.getOldPlayers(teamId);
		});
	};
	
	/* function to reject new player request*/
	$scope.declineJTR = function(reqId){
		rejectJTR.rejectNewPlayerJTR(reqId, function(result){
			$scope.nprMsg = result;
			$scope.getNewPlayerRequests(teamId);
			$scope.getOldPlayers(teamId);
		});
	};
	
	
}]);




/* service to get new player request for the team */
app.service('newPlayers', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.newPlayersRequests = function(tid, cb) {
		var data = "teamid=" + tid; 
		$http({
			url : 'get-joinrequests',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);


/* service to accept new player request for the team */
app.service('acceptJTR', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.acceptNewPlayerJTR = function(reqId, cb) {
		var data = "reqid=" + reqId; 
		$http({
			url : 'accept-joinrequests',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);


/* service to reject new player request for the team */
app.service('rejectJTR', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.rejectNewPlayerJTR = function(reqId, cb) {
		var data = "reqid=" + reqId; 
		$http({
			url : 'reject-joinrequests',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);



/* service to get old player in team */
app.service('oldPlayerSer', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.getOldPlayersFun = function(tid, cb) {
		var data = "teamid=" + tid; 
		$http({
			url : 'get-oldplayers',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);


/* service to remove old player in team */
app.service('removePlayerSer', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.removeOldPlayersFun = function(tid, pid, cb) {
		var data = "teamid=" + tid +"&pid=" + pid; 
		$http({
			url : 'remove-oldplayer',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);



/* service create poll */
app.service('pollSer', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.createPollRequest = function(tid, reqmsg, cb) {
		var data = "teamid=" + tid + "&reqmsg=" + reqmsg; 
		$http({
			url : 'create-poll',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);


/* service to update poll */
app.service('pollUpSer', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.updatePollRequest = function(tid ,pollid, cb) {
		var data = "teamid=" + tid + "&mrid=" + pollid; 
		$http({
			url : 'close-poll',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);


/* service to get all poll */
app.service('pollgetSer', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.getAllActivePoll = function(tid, cb) {
		var data = "teamid=" + tid; 
		$http({
			url : 'get-activepolls',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);


/* service to get all poll */
app.service('joinPollSer', [ '$http', function($http) {
	
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	this.joinActivePoll = function(pollid, cb) {
		var data = "pollid=" + pollid; 
		$http({
			url : 'join-activepolls',
			method : 'POST',
			data : data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);





