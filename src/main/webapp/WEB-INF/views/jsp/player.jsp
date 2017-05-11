<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="custom/image/favicon.ico" type="image/x-icon">
<title>Ofooty</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="custom/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="custom/css/player.css">
<script type="text/javascript" src="custom/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="custom/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="custom/angular/angular.min.js"></script>
<script type="text/javascript" src="custom/angular/dirPagination.js"></script>
<script type="text/javascript" src="custom/ngcontroller/ngplayer.js"></script>
<script type="text/javascript" src="custom/js/scroll-spy.js"></script>
</head>
<body ng-app="playerApp" ng-controller="playerCtlr"  ng-init="dosomething()">
<nav id="myNavbar" class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myRightNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#player"><span class="tweak">O</span>footy</a>
			</div>
			<div>
				<div class="collapse navbar-collapse" id="myRightNavbar">
					<ul id="links-right" class="nav navbar-nav navbar-right">
						<li><a href="#teams">My Teams</a></li>
						<li><a href="/ofooty/logout">Logout</a></li>
					</ul>
				</div>
			</div>
		</div>
	</nav>
	
<!-- main content start -->
<div id="player" class="container">
<div class="wc-msg">Welcome Player  <span class="wc-msg-black"><c:if test="${not empty player}"> ${ player } </c:if></span></div>
	<div class="row player-box">
		<div class="col-sm-4">
		<div class="pro-pic">
			<img alt="Profile Pic" src="${propic}" style="height: 100px; width: 100px;">
		</div>
		<div class="controls">
			<form method="post" action="upload-pic" enctype="multipart/form-data">
				<input class="form-control" type="file" ng-model="file" name="file" accept="image/jpeg">
				<button type="submit" class="btn btn-default btn-block btn-custom" >Change</button>
				<sec:csrfInput/>
			</form>
		</div>	
		</div>
		<div class="col-sm-4">
		</div>	
	</div>
</div>
<br>
<br>

<!-- player details -->
<div class="container-fluid">
	<div class="row player-box">	
	<!-- create team -->
		<div class="col-sm-6">
			<div class="create-box">
			<div class="create-box-head">Create A Team</div>
				<div class="controls">
						<form name="teamform" ng-submit="addTeam()" >
						<div class="error">
								<p>
								<span ng-cloak ng-show="teamform.teamname.$touched && teamform.teamname.$invalid">Team Name is required.</span>  
								<span ng-cloak ng-show="teamname.length > 0 && groundform.zip.$touched && groundform.zip.$invalid">Zip Code is required.</span> 
								<span ng-cloak ng-show="teamname.length > 0 && zip.length > 0 && teamform.city.$touched && teamform.city.$invalid">City is required.</span>
							    <span ng-cloak ng-show="teamname.length > 0 && zip.length > 0 && city.length > 0 && teamform.state.$touched && teamform.state.$invalid">State is required.</span> 
							    <span ng-cloak ng-show="teamname.length > 0 && zip.length > 0 && city.length > 0 && state.length > 0 && teamform.country.$touched && teamform.country.$invalid">Country is required.</span> 
							    <span ng-cloak ng-show="teamname.length > 0 && zip.length > 0 && city.length > 0 && state.length > 0 && country.length > 0 && zip.length != 5">Invalid Zip.</span> 
								<span>{{ zipmsg }}</span>
								<span>{{ invalidteamname }}</span>
								<span class="msg">{{ newteamcreated }}</span>
								</p>
							</div>
							<input class="form-control" type="text" name="teamname"
								placeholder="Team Name" ng-model="teamname" required ng-keyup="checkTeamName()" />  
								<input class="form-control input-medium bfh-phone" type="text"
								name="zip" placeholder="Zip Code" data-format="ddddd"
								ng-model="zip" required ng-keyup="getCityState()" />
								<input class="form-control" type="text" name="city"
								placeholder="City" ng-model="city" required /> 
								<input class="form-control" type="text" name="state"
								placeholder="State" ng-model="state" required /> 
								<input class="form-control" type="text" name="country"
								placeholder="Country" ng-model="country" required />
							<button type="submit" ng-disabled="teamform.$invalid || zip.length != 5 || invalidteamname.length > 0"
								class="btn btn-default btn-block btn-custom" >Create Team</button>
						</form>
					</div>
			</div>
		</div>
	<!-- create team end -->
	<!-- join team -->
		<div class="col-sm-6">
			<div class="create-box">
				<div class="create-box-head">Join A Team</div>
					<div class="controls">
						<form name="joinForm" ng-submit="searchTeams()">
						<div class="form-control-two" >
							<div class="row">
							<div class="col-sm-3">
							<span class="tweak">Search Type</span>
							</div>
							<div class="col-sm-3">
							<input type="radio" ng-model="searchkey" value="teamName">Team Name
							</div>
							<div class="col-sm-3">
							<input type="radio" ng-model="searchkey" value="address.city">City
							</div>
							<div class="col-sm-3">
							<input  type="radio" ng-model="searchkey" value="address.zip">Zip
							</div>
							</div>
						</div>
						<div class="error">
							<p>
							<span ng-cloak ng-show="joinForm.searchvalue.$touched && joinForm.searchvalue.$invalid">Search Key is required.</span>
							<span ng-cloak >{{ searcherror }}</span>  
							</p>
						</div>		
							<input class="form-control" type="text" ng-model="searchvalue" name="searchvalue" placeholder="Search Key" ng-keyup="clearsearcherror()" required>
							<button type="submit" ng-disabled="joinForm.$invalid || searchvalue.length < 0 || ( '' + searchkey).length  == 0"
								class="btn btn-default btn-block btn-custom" >Search</button>
						</form>
					</div>
					<div class="jtr-msg">
						<p>{{ jtrMessage }}</p>
					</div>
					<dir-pagination-controls boundary-links="true" on-page-change="pageChangeHandler(newPageNumber)" template-url="custom/angular/dirPagination.tpl.html"></dir-pagination-controls>
					<div class="row map-box" dir-paginate="ts in teams | itemsPerPage: pageSize" current-page="currentPage">
						<div class="col-xs-8 ts-box">
							<div class="ts-slot">
								<p><span class="glyphicon glyphicon-triangle-right"></span> {{ ts.teamName }}</p>
								<p><span class="glyphicon glyphicon-user"></span> {{ ts.manager }}</p>
								<p><span class="glyphicon glyphicon-home"></span>  {{ ts.address}}</p>
								<p><span class="glyphicon glyphicon-fullscreen"></span> {{ ts.teamSize }}</p>
							</div>
						</div>
						<div class="col-xs-4" >
							<div class="ts-decide" ng-mouseover="clrjtrmsg()" ng-click="goJoinTeam(ts.teamId)">
								Join Team
							</div>
						</div>
					</div>
			</div>
		</div>
	<!-- join team end -->	
	</div>
</div>
<!-- player details end -->

<div class="container-fluid">
	<div class="row">
		<div class="divide-line"></div>
	</div>
	</div>

<!-- My teams -->
<div id="teams" class="container-fluid">
		<div class="row">
				<div class="mng-grd">
					<p>My Teams</p>
				</div>
				<!-- New ground request start -->
					<div class="panel panel-default req-panel">
						<div class="panel-heading">
							<h3 class="panel-title">My Teams</h3>
						</div>
						<!-- angular controller to load data -->
						<div class="panel-body">
						<div class="row">
							<div class="col-sm-3 req-box" ng-repeat="p in players" >
								<div class="req-details">
								<form action="in-team" method="GET">
									<p><span class="tweak">Team Name:</span> {{ p.teamName }} </p>
									<p><span class="tweak">Team Manager:</span> {{ p.manager }} </p>
									<p><span class="tweak">Team Size:</span> {{ p.teamSize }} </p>
									<p><span class="tweak">Location:</span> {{ p.address }}</p>
									<input type="hidden" name="tid" value="{{ p.teamId }}">
									<p><input type="submit" value="View Team"></p>
									<sec:csrfInput/>
								</form>	
								</div>
								</div>
							</div>
						</div>
						</div>		
					</div>
				<!-- New ground request end -->
			</div>
<!-- My teams end -->

<!-- main content end -->


</body>
</html>