<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="custom/image/favicon.ico" type="image/x-icon">
<title>Ofooty</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="custom/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="custom/css/gcrew.css">
<script type="text/javascript" src="custom/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="custom/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="custom/bootstrap/js/bootstrap-formhelpers-phone.js"></script>
<script type="text/javascript" src="custom/angular/angular.min.js"></script>
<script type="text/javascript" src="custom/ngcontroller/nggcrew.js"></script>
<script type="text/javascript" src="custom/js/scroll-spy.js"></script>
</head>
<body ng-app="gcrewApp" ng-controller="gcrewCtlr">
<nav id="myNavbar" class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myRightNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#myground"><span class="tweak">O</span>footy</a>
			</div>
			<div>
				<div class="collapse navbar-collapse" id="myRightNavbar">
					<ul id="links-right" class="nav navbar-nav navbar-right">
						<li><a href="#myground">My Ground</a></li>
						<c:if test="${ground.isEnabled}">
						<li><a href="#availability">Availability</a></li>
						</c:if>
						<li><a href="/ofooty/logout">Logout</a></li>
					</ul>
				</div>
			</div>
		</div>
	</nav>
	
	<!-- main content start -->
	<div id="myground" class="container-fluid">
		<div class="row">
			<div class="wc-msg">Welcome Ground Crew<span class="wc-msg-black"><c:if test="${not empty gcrew}"> ${ gcrew } </c:if></span></div>
			
			<br>
			<br>
			<div class="ground-box">
					<div class="gbox-head">My Ground Details</div>
				<c:if test="${empty ground}">
					<div class="add-ground">
						<p><span>Add Ground Details</span></p>
					</div>
					<div class="controls">
						<form name="groundform" action="add-ground" method="post" >
						<div class="error">
								<p>
								<span ng-cloak ng-show="groundform.grdname.$touched && groundform.grdname.$invalid">Ground Name is required.</span> 
								<span ng-cloak ng-show="grdname.length > 0 && groundform.street.$touched && groundform.street.$invalid">Street Line is required.</span> 
								<span ng-cloak ng-show="grdname.length > 0 && street.length > 0 && groundform.zip.$touched && groundform.zip.$invalid">Zip Code is required.</span> 
								<span ng-cloak ng-show="grdname.length > 0 && street.length > 0 && zip.length > 0 && groundform.city.$touched && groundform.city.$invalid">City is required.</span>
							    <span ng-cloak ng-show="grdname.length > 0 && street.length > 0 && zip.length > 0 && city.length > 0 && groundform.state.$touched && groundform.state.$invalid">State is required.</span> 
							    <span ng-cloak ng-show="grdname.length > 0 && street.length > 0 && zip.length > 0 && city.length > 0 && state.length > 0 && groundform.country.$touched && groundform.country.$invalid">Country is required.</span> 
							    <span ng-cloak ng-show="grdname.length > 0 && street.length > 0 && zip.length > 0 && city.length > 0 && state.length > 0 && country.length > 0 && zip.length != 5">Invalid Zip.</span> 
								<span>{{ zipmsg }}</span>
								</p>
							</div>
							<input class="form-control" type="text" name="grdname"
								placeholder="Ground Name" ng-model="grdname" required /> 
								<input class="form-control" type="text" name="street"
								placeholder="Street Line" ng-model="street" required /> 
								<input class="form-control input-medium bfh-phone" type="text"
								name="zip" placeholder="Zip Code" data-format="ddddd"
								ng-model="zip" required ng-keyup="getCityState()" />
								<input class="form-control" type="text" name="city"
								placeholder="City" ng-model="city" required /> 
								<input class="form-control" type="text" name="state"
								placeholder="State" ng-model="state" required /> 
								<input class="form-control" type="text" name="country"
								placeholder="Country" ng-model="country" required />
							<button type="submit" ng-disabled="groundform.$invalid || zip.length != 5"
								class="btn btn-default btn-block btn-custom" >Register</button>
								<sec:csrfInput/>
						</form>
					</div>
				</c:if>
				
				<c:if test="${not empty ground}">
					<div class="ground-details">
					<div class="grd-det-box">
						<p><span class="tweak">Ground: </span> ${ ground.groundName }</p>
						<p><span class="tweak">Ground Status: </span><c:choose><c:when test="${ ground.isEnabled }">Enabled</c:when><c:otherwise>Disabled</c:otherwise></c:choose></p>
						<p><span class="tweak">Ground Crew: </span> ${ ground.gcrew.firstName } ${ ground.gcrew.lastName } </p>
						<p><span class="tweak">Location: </span><span>${ ground.address.street }</span>, 
						<span>${ ground.address.city }</span>, <span>${ ground.address.state }</span>
						- <span>${ ground.address.zip }</span>, <span> ${ ground.address.country }</span>. 						
						</p>
					</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	
	<div class="container-fluid">
	<div class="row">
		<div class="divide-line"></div>
	</div>
	</div>
	
	<c:if test="${ground.isEnabled}">
	<!-- ground availability -->
	<div id="availability" class="container-fluid" ng-init="dosomething()">
		<div class="row">
			<div class="ground-box">
				<div class="gbox-head">Ground Availability</div>
				<!-- date -->
				<div class="col-sm-4">
				<div class="sd-box">
					<div class="sd-box-head">
						Select Date
					</div>
					<div ng-repeat="d in datevalues"class="sd-box-val">
						<p ng-click="availMap(d)"><span class="glyphicon glyphicon-calendar"></span>   {{ d }}</p>
					</div>
				</div>
			</div>
			<!-- map -->
			<div class="col-sm-8">
				<div class="sd-box">
				<div class="sd-box-head">
						Availability Map : <span> {{ availMaps.date }} </span>
					</div>
					<div class="row map-box" ng-repeat="ts in availMaps.timeSlots">
						<div class="col-xs-8 ts-box">
							<div class="ts-slot" ng-switch="ts.slot">
								<p ng-switch-when="ONE"><span class="glyphicon glyphicon-time"></span>  6:00 AM - 7:00 AM</p>
								<p ng-switch-when="TWO"><span class="glyphicon glyphicon-time"></span>  7:00 AM - 8:00 AM</p>
								<p ng-switch-when="THREE"><span class="glyphicon glyphicon-time"></span>  8:00 AM - 9:00 AM</p>
								<p ng-switch-when="FOUR"><span class="glyphicon glyphicon-time"></span>  9:00 AM - 10:00 AM</p>
								<p ng-switch-when="FIVE"><span class="glyphicon glyphicon-time"></span>  10:00 AM - 11:00 AM</p>
								<p ng-switch-when="SIX"><span class="glyphicon glyphicon-time"></span>  11:00 AM - 12:00 PM</p>
								<p ng-switch-when="SEVEN"><span class="glyphicon glyphicon-time"></span>  12:00 PM - 1:00 PM</p>
								<p ng-switch-when="EIGHT"><span class="glyphicon glyphicon-time"></span>  1:00 PM - 2:00 PM</p>
								<p ng-switch-when="NINE"><span class="glyphicon glyphicon-time"></span>  2:00 PM - 3:00 PM</p>
								<p ng-switch-when="TEN"><span class="glyphicon glyphicon-time"></span>  3:00 PM - 4:00 PM</p>
								<p ng-switch-when="ELEVEN"><span class="glyphicon glyphicon-time"></span>  4:00 PM - 5:00 PM</p>
								<p ng-switch-when="TWELVE"><span class="glyphicon glyphicon-time"></span>  5:00 PM - 6:00 PM</p>
								<p ng-switch-when="THIRTEEN"><span class="glyphicon glyphicon-time"></span>  6:00 PM - 7:00 PM</p>
								<p ng-switch-when="FOURTEEN"><span class="glyphicon glyphicon-time"></span>  7:00 PM - 8:00 PM</p>
								<p ng-switch-when="FIFTEEN"><span class="glyphicon glyphicon-time"></span>  8:00 PM - 9:00 PM</p>
								<p ng-switch-when="SIXTEEN"><span class="glyphicon glyphicon-time"></span>  9:00 PM - 10:00 PM</p>
								<p><span class="glyphicon glyphicon-time"></span> {{ ts.status}}</p>
							</div>
						</div>
						<div class="col-xs-4" >
						<div ng-show="ts.status=='OPEN' || ts.status=='CLOSED'">
							<div class="ts-decide" ng-click="actionTimeSlot(availMaps.id, availMaps.date, ts.slot)">
								Change Status
							</div>
						</div>
						</div>
					</div>
				</div>
			</div>
			
			</div>
		</div>
	</div>
	<!-- ground availability end -->
	</c:if>
	<!-- main content end -->
</body>
</html>