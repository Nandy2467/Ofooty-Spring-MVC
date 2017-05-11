<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="custom/image/favicon.ico" type="image/x-icon">
<title>Ofooty</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="custom/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="custom/css/team.css">
<script type="text/javascript" src="custom/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="custom/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="custom/angular/angular.min.js"></script>
<script type="text/javascript" src="custom/angular/dirPagination.js"></script>
<script type="text/javascript" src="custom/ngcontroller/ngteam.js"></script>
<script type="text/javascript" src="custom/js/scroll-spy.js"></script>
</head>
<body>
<body ng-app="teamApp" ng-controller="teamCtlr">
<nav id="myNavbar" class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myRightNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<c:if test="${isManager}">
					<a class="navbar-brand" href="#manager"><span class="tweak">O</span>footy</a>
				</c:if>
				<c:if test="${empty isManager}">
					<a class="navbar-brand" href="#player"><span class="tweak">O</span>footy</a>
				</c:if>
			</div>
			<div>
				<div class="collapse navbar-collapse" id="myRightNavbar">
					<ul id="links-right" class="nav navbar-nav navbar-right">
						<c:if test="${isManager}">
						<li><a href="#manager">Manager</a></li>
						</c:if>
						<li><a href="#player">Feed</a></li>
						<li><a href="/ofooty/logout">Logout</a></li>
					</ul>
				</div>
			</div>
		</div>
	</nav>
	
	
<!-- main content start -->

<c:if test="${isManager}">
<div id="manager" class="container" ng-init="doIntializePage(${ team.teamId })">
<div class="wc-msg">Welcome Player  <span class="wc-msg-black"> ${ player.firstName } ${ player.lastName }</span></div>
	<div class="row player-box">
		<div class="col-sm-6">
			<div class="create-box">
				<div class="create-box-head">New Player Request</div>
				<div class="npr-msg"><p ng-cloak ng-mouseover="clearnprMsg()" >{{ nprMsg }}</p></div>
					 <dir-pagination-controls boundary-links="true" on-page-change="pageChangeHandler(newPageNumber)" template-url="custom/angular/dirPagination.tpl.html"></dir-pagination-controls>
					<div class="row map-box" ng-cloak dir-paginate="np in newplayers | itemsPerPage: pageSize" current-page="currentPage">
						<div class="col-xs-8 ts-box">
							<div class="ts-slot">
								<p><span><img ng-src="{{ 'propic/PLAYER' + np.sentBy.actorId + '.jpg' }}" onerror="this.src != 'propic/PLAYER.jpg'; this.src='propic/PLAYER.jpg';" class="img-circle" style="height: 50px; width: 50px" > </span></p>
								<p><span class="glyphicon glyphicon-user"></span> {{ np.sentBy.firstName }} {{ np.sentBy.lastName }} <span class="glyphicon glyphicon-phone"></span> {{ np.sentBy.mobile }}</p>
							</div>
						</div>
						<div class="col-xs-4" >
							<div class="ts-decide" ng-click="approveJTR(np.requestId)">
								Accept
							</div>
							<br>
							<div class="ts-decide" ng-click="declineJTR(np.requestId)">
								Decline
							</div>
						</div>
					</div>
			</div>
		</div>
		
		<!-- remove player -->
		<div class="col-sm-6">
			<div class="create-box">
				<div class="create-box-head">Manage Players</div>
				<div class="rp-msg"><p ng-cloak ng-mouseover="clearrpMsg()" >{{ rpMsg }}</p></div>
					<dir-pagination-controls boundary-links="true" on-page-change="pageChangeHandler(newPageNumber)" template-url="custom/angular/dirPagination.tpl.html"></dir-pagination-controls>
					<div class="row map-box" ng-cloak dir-paginate="op in oldplayers | itemsPerPage: pageSize" current-page="currentPage">
						<div class="col-xs-8 ts-box">
							<div class="ts-slot">
								<p><span><img ng-src="{{ 'propic/PLAYER' + op.actorId + '.jpg' }}" onerror="this.src != 'propic/PLAYER.jpg'; this.src='propic/PLAYER.jpg';" class="img-circle" style="height: 50px; width: 50px" > </span></p>
								<p><span class="glyphicon glyphicon-user"></span> {{ op.firstName }} {{ op.lastName }} <span class="glyphicon glyphicon-phone"></span> {{ op.mobile }}</p>
							</div>
						</div>
						<div class="col-xs-4" >
							<div class="ts-decide" ng-click="removePlayer(op.actorId)">
								Remove Player
							</div>
						</div>
					</div>
			</div>
		</div>
		
	</div>
	
	<!-- Match Request  and book ground link -->
	<div class="row player-box">
		<div class="create-box-head">Manage Player Availability</div>
		<div class="col-sm-6">
		<div class="create-box">
		<div class="create-box-head">Create Poll</div>
		<div class="controls">
			<form name="pollform" ng-submit="createpoll()" >
			<div class="error">
					<p>
					<span ng-cloak ng-show="pollform.reqmsg.$touched && pollform.reqmsg.$invalid">Request Message is Required.</span>  
					<span class="msg">{{ newpollcreated }}</span>
					</p>
				</div>
				<input class="form-control" type="text" name="reqmsg"
					placeholder="Poll Message" ng-model="reqmsg" required ng-keyup="clearNewPollMsg()" />  
				<button type="submit" ng-disabled="pollform.$invalid"
					class="btn btn-default btn-block btn-custom" >Post Poll</button>
			</form>
		</div>
	</div>
	</div>
	
	<div class="col-sm-6">
		<div class="create-box">
				<div class="create-box-head">Manage Polls</div>
					 <dir-pagination-controls boundary-links="true" on-page-change="pageChangeHandler(newPageNumber)" template-url="custom/angular/dirPagination.tpl.html"></dir-pagination-controls>
					<div class="row map-box" ng-cloak dir-paginate="ap in activepolls | itemsPerPage: pageSize" current-page="currentPage">
						<div class="col-xs-8 ts-box">
							<div class="ts-slot">
								<p><span class="glyphicon glyphicon-tasks"></span> {{ ap.matchRequest.reqMessage }}</p>
								<p>Player Count: {{ ap.playerCount }} {{ ap.playersList }}</p>
							</div>
						</div>
						<div class="col-xs-4" >
							<div class="ts-decide" ng-click="closePoll(ap.matchRequest.mrId)">
								Close Poll
							</div>
							<br>
							<div class="ts-decide"  data-toggle="modal" data-target="#myModal">
								View Players
							</div>
							
						</div>
						
						<!-- player Modal -->
						<div class="modal fade" id="myModal" role="dialog">
    						<div class="modal-dialog">
    						<div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal">&times;</button>
						        <h4 class="modal-title">Players List</h4>
						      </div>
						      <div class="modal-body">
						        <div ng-repeat="p in ap.matchRequest.playersList">
						        	<p><span class="glyphicon glyphicon-user"></span> {{ p.firstName }} {{ p.lastName }}</p>
						        </div>
						      </div>
    						</div>
    						</div>
    					</div>
						
					</div>
			</div>
	</div>
	</div>
</div>

<div class="container-fluid">
	<div class="row">
		<div class="divide-line"></div>
	</div>
	</div>
</c:if>
<!-- manager over -->



<div id="player" class="container" ng-init="doIntializePlayerPart(${ team.teamId })">
<c:if test="${empty isManager}">
<div class="wc-msg">Welcome Player  <span class="wc-msg-black"> ${ player.firstName } ${ player.lastName }</span></div>
</c:if>
	<div class="row player-box">
		<div class="col-sm-6">
			<div class="create-box">
				<div class="create-box-head">Active Poll Requests</div>
					<dir-pagination-controls boundary-links="true" on-page-change="pageChangeHandler(newPageNumber)" template-url="custom/angular/dirPagination.tpl.html"></dir-pagination-controls>
					<div class="row map-box" ng-cloak dir-paginate="ap in activepolls | itemsPerPage: pageSize" current-page="currentPage">
						<div class="col-xs-8 ts-box">
							<div class="ts-slot">
								<p><span class="glyphicon glyphicon-tasks"></span> {{ ap.matchRequest.reqMessage }}</p>
								<p>Player Count: {{ ap.playerCount }}</p>
							</div>
						</div>
						<div class="col-xs-4" >
							<div class="ts-decide" ng-click="joinPoll(ap.matchRequest.mrId)">
								Join In
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<!-- <div class="col-sm-6">
			<div class="create-box">
				<div class="create-box-head">Upcoming Events</div>
					<dir-pagination-controls boundary-links="true" on-page-change="pageChangeHandler(newPageNumber)" template-url="custom/angular/dirPagination.tpl.html"></dir-pagination-controls>
					<div class="row map-box" ng-cloak dir-paginate="ap in activepolls | itemsPerPage: pageSize" current-page="currentPage">
						<div class="col-xs-8 ts-box">
							<div class="ts-slot">
								<p><span class="glyphicon glyphicon-tasks"></span> {{ ap.matchRequest.reqMessage }}</p>
								<p>Player Count: {{ ap.playerCount }}</p>
							</div>
						</div>
						<div class="col-xs-4" >
							<div class="ts-decide" ng-click="joinPoll(ap.matchRequest.mrId)">
								Join In
							</div>
						</div>
					</div>
				</div>
			</div> -->
			</div>
			
			<!-- display allteam players -->
			<div class="row player-box">
			<div class="create-box">
				<div class="create-box-head">Team Members List</div>
				<c:forEach var="item" items="${team.players}">
				<div class="col-sm-3">
					<div class="row map-box">
						<div class="ts-box">
							<div class="ts-slot">
								<p><span><img src="propic/PLAYER${ item.actorId }.jpg" onerror="this.src != 'propic/PLAYER.jpg'; this.src='propic/PLAYER.jpg';" class="img-circle" style="height: 50px; width: 50px" > </span></p>
								<p><span class="glyphicon glyphicon-user"></span> ${ item.firstName }  ${ item.lastName } <span class="glyphicon glyphicon-phone"></span> ${ item.mobile }</p>
							</div>
						</div>
					</div>
					</div>
				</c:forEach>
			</div>	
		</div>	
</div>

</body>
</html>