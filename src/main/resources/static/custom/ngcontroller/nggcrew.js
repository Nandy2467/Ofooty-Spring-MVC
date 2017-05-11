/**
 * Angular controller for GCrew functions
 */

var app = angular.module('gcrewApp', []);

app.controller('gcrewCtlr',[ '$scope', 'getlocation', 'getnextdates', 'getavailmap' , 'changeAvailStatus', function($scope, getlocation, getnextdates, getavailmap, changeAvailStatus) {
	
	
	$scope.dosomething = function(){
		$scope.filldates();
	};
	
	
	/* method to change the status of the avial */
	$scope.actionTimeSlot = function(id, date, slot){
		changeAvailStatus.updateslotsatuts(id, date, slot, function(result){
			$scope.availMaps = result;
		});
	};
	
	
	/* method to get avail map*/
	$scope.availMap =  function(d){
		getavailmap.getavailmapbydate(d, function(result){
			$scope.availMaps = result;
		});
	};
	
	
	/* method to get next dates */
	$scope.filldates = function(){
		getnextdates.getNextDatesToSet(function(result){
			$scope.datevalues = result;
		});
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


/* service to get dates to set availability of ground */
app.service('getnextdates', [ '$http', function($http) {
	
	this.getNextDatesToSet = function(cb) {
		
		$http({
			url : 'next-dates',
			method : 'POST',
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);



/* service to get availability map for a date */
app.service('getavailmap', [ '$http', function($http) {
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	
	this.getavailmapbydate = function(d, cb) {
		var data = "date=" + d;
		$http({
			url : 'get-availmap',
			method : 'POST',
			data: data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);


/* service to get availability map for a date */
app.service('changeAvailStatus', [ '$http', function($http) {
	$http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
	
	this.updateslotsatuts = function(id, date, slot, cb) {
		var data = "id=" + id + "&date=" + date + "&slot=" + slot;
		$http({
			url : 'update-timeslot',
			method : 'POST',
			data: data
		}).then(function(response) {
			cb(response.data);
		}, function(failure) {
			// do nothing
		});
	};

} ]);



