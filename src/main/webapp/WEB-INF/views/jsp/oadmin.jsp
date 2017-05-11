<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="custom/image/favicon.ico" type="image/x-icon">
<title>Ofooty</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="custom/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="custom/css/oadmin.css">
<script type="text/javascript" src="custom/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="custom/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="custom/angular/angular.min.js"></script>
<script type="text/javascript" src="custom/ngcontroller/ngoadmin.js"></script>
<script type="text/javascript" src="custom/js/scroll-spy.js"></script>
</head>
<body ng-app="oadminApp" ng-controller="oadminCtlr" ng-init="doinitialize()">
	<!-- nav start -->
	<nav id="myNavbar" class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myRightNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#dashboard"><span class="tweak">O</span>footy</a>
			</div>
			<div>
				<div class="collapse navbar-collapse" id="myRightNavbar">
					<ul id="links-right" class="nav navbar-nav navbar-right">
						<li><a href="#dashboard">Dashboard</a></li>
						<li><a href="#grounds">Manage Grounds</a></li>
						<li><a href="/ofooty/logout">Logout</a></li>
					</ul>
				</div>
			</div>
		</div>
	</nav>
	<!-- nav end -->

	<!-- main content -->
	<!-- dashboard section start -->
	<div id="dashboard" class="container-fluid">
	<div class="row">
	<div class="wlc-msg"><p>Welcome OAdmin <span class="wc-msg-black"><c:if test="${not empty oadmin}"> ${ oadmin } </c:if></span></p><br> </div>
	<div class="key-indicate">
					<p>Key Facts:</p>
		</div>
		<div class="col-sm-4 feature">
			<div class="refresh"><span class="glyphicon glyphicon-refresh" ng-click="updatecount()"></span></div>
    	    <div class="count"><i>{{ playercount }}</i></div>
            <h3>Player Count</h3>
            <div class="title_border"></div>
            <p>The total number of players in the system.</p>
		</div>
        <div class="col-sm-4 feature">
            <div class="refresh"><span class="glyphicon glyphicon-refresh" ng-click="updatecount()"></span></div>
            <div class="count"><i>{{ teamcount }}</i></div>
            <h3>Team Count</h3>
            <div class="title_border"></div>
            <p>The total number of teams in the system.</p>
		</div>
        <div class="col-sm-4 feature">
	        <div class="refresh"><span class="glyphicon glyphicon-refresh" ng-click="updatecount()"></span></div>
            <div class="count"><i>{{ groundcount }}</i></div>
            <h3>Ground Count</h3>
            <div class="title_border"></div>
            <p>The total number of grounds in the system.</p>
		</div> 
	</div>
	</div>
	<!-- dashboard section end -->
	
	<!-- Manage ground start -->
		<div id="grounds" class="container-fluid">
			<div class="row">
				<div class="mng-grd">
					<p>Ground Management</p>
				</div>
				<!-- New ground request start -->
					<div class="panel panel-default req-panel">
						<div class="panel-heading">
							<h3 class="panel-title">New Ground Request</h3>
						</div>
						<!-- angular controller to load data -->
						<div class="panel-body">
						<div class="row approve-msg" ng-show="(('' + approveMsg).length > 0)" > 
						<div class="col-xs-10">{{ approveMsg }}</div>
						<div class="col-xs-2"><span ng-click="clearApproveMsg()">x</span></div>
						</div>
						<div class="row">
							<div class="col-sm-3 req-box" ng-repeat="g in records" >
								<div class="req-details">
									<p><span class="tweak">Ground :</span> {{ g.ground.groundName }}</p>
									<p><span class="tweak">Ground Crew:</span> {{ g.firstName }} {{ g.lastName }}</p>
									<p><span class="tweak">GCrew Mobile:</span> {{ g.mobile }}</p>
									<p><span class="tweak">Location:</span> <span>{{ g.ground.address.street }}</span><span>, </span><span>{{ g.ground.address.city }}</span><span>, </span><span>{{ g.ground.address.state }}</span><span> - </span>{{ g.ground.address.zip }}<span></span><span>, </span><span>{{ g.ground.address.country }}</span> </p>
									<div class="row req-decide">
									<div class="col-xs-6">
									</div>
										<div class="col-xs-6">
											<div class="req-decide-btn approve" ng-click="approveGround(g.ground.groundId)">Approve</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						</div>		
					</div>
				<!-- New ground request end -->
			</div>
		</div>
	<!-- Manage ground ends -->	
	
	<!-- main content end -->
</body>
</html>