<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" href="custom/image/favicon.ico" type="image/x-icon">
<title>Ofooty</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="custom/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="custom/css/log-in.css">
<script type="text/javascript" src="custom/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="custom/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="custom/angular/angular.min.js"></script>
<script type="text/javascript" src="custom/ngcontroller/nglogin.js"></script>
<script type="text/javascript" src="custom/bootstrap/js/bootstrap-formhelpers-phone.js"></script>
<script type="text/javascript" src="custom/js/scroll-spy.js"></script>	
</head>
<body ng-app="loginApp" ng-controller="loginCtlr">
	<!-- nav start -->
	<nav id="myNavbar" class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myRightNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#page-top"><span class="tweak">O</span>footy</a>
			</div>
			<div>
				<div class="collapse navbar-collapse" id="myRightNavbar">
					<ul id="links-right" class="nav navbar-nav navbar-right">
						<li><a href="#player">Player</a></li>
						<li><a href="#ground-crew">Ground Crew</a></li>
					</ul>
				</div>
			</div>
		</div>
	</nav>
	<!-- nav end -->

	<!-- main content -->
	<!-- page-top (log-in form with ofooty background image) section start -->
	<div id="page-top" class="container-fluid">
		<div class="row">
			<div class="col-sm-8">
				<div id="head-box">
					<div class="container-fluid text-center">
						<h3>
							<span class="tweak">O</span>footy
						</h3>
						<p>exclusively for soccer teams playing 5 vs 5</p>
						<br> <br>
						<p>Game On!</p>
						<br> <br> <br> <br>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div id="login-box">
					<div class="logo">
						<h1 class="logo-caption">
							<span class="tweak">L</span>ogin
						</h1>
					</div>
					<div class="controls">
						<form name="loginform" action="log-in" method="post">
							<div class="error">
								<p>
									<span ng-cloak
										ng-show="loginform.username.$touched && loginform.username.$invalid">Username
										is required.</span> <span ng-cloak
										ng-show="username.length > 0 && loginform.password.$touched && loginform.password.$invalid">Password
										is required.</span>
								</p>
								<c:if test="${not empty param.error}">
								<div class="alert alert-danger alert-dismissable">
								<a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
								<p>Invalid Credentials.</p>
								</div>
								</c:if>
							</div>
							<input class="form-control" type="text" name="username"
								placeholder="Username" ng-model="username" required /> <input
								class="form-control" type="password" name="password"
								placeholder="Password" ng-model="password" required />
							<button type="submit" ng-disabled="logform.$invalid"
								class="btn btn-default btn-block btn-custom">Login</button>
							<sec:csrfInput/>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- page-top section end -->

	<!-- player section start -->
	<div id="player" class="container-fluid">
		<div class="row">
			<div class="col-sm-12 col-md-6">
				<div id="phead-box">
					<!-- Heading lines start -->
					<div class="container-fluid text-center">
						<h3>
							<span class="tweak">P</span>layer
						</h3>
						<br>
						<p>Ready to play?</p>
						<p>Register as player.</p>
						<p>Join a team.</p>
						<p>Create a team and become a manager.</p>
						<br>
					</div>
				</div>
				<!-- Heading lines end -->
			</div>
			<div class="col-sm-12 col-md-6">
				<div id="register-box">
					<!-- register player box start -->
					<div class="logo">
						<h1 class="logo-caption">
							<span class="tweak">Player </span>Register
						</h1>
					</div>
					<div class="controls">
						<form name="pregform" ng-submit="addPlayer()" >
							<div class="error">
								<p>
									<span ng-cloak
										ng-show="pregform.pfname.$touched && pregform.pfname.$invalid">First
										Name is required.</span> <span ng-cloak
										ng-show="pfname.length > 0 && pregform.plname.$touched && pregform.plname.$invalid">Last
										Name is required.</span> <span ng-cloak
										ng-show="pfname.length > 0 && plname.length > 0 && pregform.pmobile.$touched && pregform.pmobile.$invalid">Mobile
										Number is required.</span> <span ng-cloak
										ng-show="pfname.length > 0 && plname.length > 0 && pmobile.length > 0 && pregform.pusername.$touched && pregform.pusername.$invalid">Username
										is required.</span> <span ng-cloak
										ng-show="pfname.length > 0 && plname.length > 0 && pmobile.length > 0 && pusername.length > 0 && pregform.ppassword.$touched && pregform.ppassword.$invalid">Password
										is required.</span> <span ng-cloak
										ng-show="pfname.length > 0 && plname.length > 0 && pmobile.length > 0 && pusername.length > 0 && ppassword.length > 0 && pregform.pcpassword.$touched && pregform.pcpassword.$invalid">Confirm
										Password is required.</span> <span ng-cloak
										ng-show="pfname.length > 0 && plname.length > 0 && pmobile.length > 0 && pusername.length > 0 && ppassword.length > 0 && pcpassword.length > 0 && pmobile.length != 10">Invalid
										mobile number.</span> <span ng-cloak
										ng-show="pfname.length > 0 && plname.length > 0 && pmobile.length > 0 && pusername.length > 0 && ppassword.length > 0 && pcpassword.length > 0 && ppassword != pcpassword">Password
										doesn't match.</span>
										<span>{{ invlaidPlayerUserName }}</span>
										<span>{{ invlaidPlayerMobile }}</span>
										<span class="msg">{{ newPlayerMessage }}</span>
								</p>
							</div>
							<input class="form-control" type="text" name="pfname"
								placeholder="First Name" ng-model="pfname" required ng-keyup="clearSuccessMsg()" /> <input
								class="form-control" type="text" name="plname"
								placeholder="Last Name" ng-model="plname" required /> <input
								class="form-control input-medium bfh-phone" type="text"
								name="pmobile" placeholder="mobile" data-format="dddddddddd"
								ng-model="pmobile" required ng-keyup="validatePlayerMobile()" /> <br> <input
								class="form-control" type="text" name="pusername"
								placeholder="Username" ng-model="pusername" required ng-keyup="validatePlayerUserName()" /> <input
								class="form-control" type="password" name="ppassword"
								placeholder="Password" ng-model="ppassword" required /> <input
								class="form-control" type="password" name="pcpassword"
								placeholder="Confirm Password" ng-model="pcpassword" required />
							<button type="submit"
								ng-disabled="pregform.$invalid || pmobile.length != 10 || ppassword != pcpassword || invlaidPlayerUserName.length > 0 || invlaidPlayerMobile.length > 0"
								class="btn btn-default btn-block btn-custom" >Register</button>
						</form>
					</div>
				</div>
				<!-- register player box end -->
			</div>
		</div>
	</div>
	<!-- player section end -->

	<!-- ground-crew section start -->
	<div id="ground-crew" class="container-fluid">
		<div class="row">
			<div class="col-sm-12 col-md-6">
				<div id="phead-box">
					<!-- Heading lines start -->
					<div class="container-fluid text-center">
						<h3>
							<span class="tweak-g">G</span>round<span class="tweak-g">
								C</span>rew
						</h3>
						<br>
						<p>Are you a ground owner?</p>
						<p>Register yourself.</p>
						<p>Can register your ground.</p>
						<p>Update your ground availability.</p>
						<br>
					</div>
				</div>
				<!-- Heading lines end -->
			</div>
			<div class="col-sm-12 col-md-6">
				<div id="register-box">
					<!-- register ground crew box start -->
					<div class="logo">
						<h1 class="logo-caption">
							<span class="tweak">Ground Crew </span>Register
						</h1>
					</div>
					<div class="controls">
						<form name="gregform" ng-submit="addGCrew()">
							<div class="error">
								<p>
									<span ng-cloak
										ng-show="gregform.gfname.$touched && gregform.gfname.$invalid">First
										Name is required.</span> <span ng-cloak
										ng-show="gfname.length > 0 && gregform.glname.$touched && gregform.glname.$invalid">Last
										Name is required.</span> <span ng-cloak
										ng-show="gfname.length > 0 && glname.length > 0 && gregform.gmobile.$touched && gregform.gmobile.$invalid">Mobile
										Number is required.</span> <span ng-cloak
										ng-show="gfname.length > 0 && glname.length > 0 && gmobile.length > 0 && gregform.gusername.$touched && gregform.gusername.$invalid">Username
										is required.</span> <span ng-cloak
										ng-show="gfname.length > 0 && glname.length > 0 && gmobile.length > 0 && gusername.length > 0 && gregform.gpassword.$touched && gregform.gpassword.$invalid">Password
										is required.</span> <span ng-cloak
										ng-show="gfname.length > 0 && glname.length > 0 && gmobile.length > 0 && gusername.length > 0 && gpassword.length > 0 && gregform.gcpassword.$touched && gregform.gcpassword.$invalid">Confirm
										Password is required.</span> <span ng-cloak
										ng-show="gfname.length > 0 && glname.length > 0 && gmobile.length > 0 && gusername.length > 0 && gpassword.length > 0 && gcpassword.length > 0 && gmobile.length != 10">Invalid
										mobile number.</span> <span ng-cloak
										ng-show="gfname.length > 0 && glname.length > 0 && gmobile.length > 0 && gusername.length > 0 && gpassword.length > 0 && gcpassword.length > 0 && gpassword != gcpassword">Password
										doesn't match.</span>
										<span>{{ invlaidGCrewUserName }}</span>
										<span>{{ invlaidGCrewMobile }}</span>
										<span class="msg">{{ newGCrewMessage }}</span>
								</p>
							</div>
							<input class="form-control" type="text" name="gfname"
								placeholder="First Name" ng-model="gfname" required ng-keyup="clearSuccessMsg()" /> <input
								class="form-control" type="text" name="glname"
								placeholder="Last Name" ng-model="glname" required /> <input
								class="form-control input-medium bfh-phone" type="text"
								name="gmobile" placeholder="mobile" data-format="dddddddddd"
								ng-model="gmobile" required  ng-keyup="validateGCrewMobile()"/> <br> <input
								class="form-control" type="text" name="gusername"
								placeholder="Username" ng-model="gusername" required ng-keyup="validateGCrewUserName()" /> <input
								class="form-control" type="password" name="gpassword"
								placeholder="Password" ng-model="gpassword" required /> <input
								class="form-control" type="password" name="gcpassword"
								placeholder="Confirm Password" ng-model="gcpassword" required />
							<button type="submit"
								ng-disabled="gregform.$invalid || gmobile.length != 10 || gpassword != gcpassword || invlaidGCrewUserName.length > 0 || invlaidGCrewMobile.length > 0"
								class="btn btn-default btn-block btn-custom">Register</button>
						</form>
					</div>
				</div>
				<!-- register ground crew box end -->
			</div>
		</div>
	</div>
	<!-- ground-crew section end -->

	<!-- main content end -->
</body>
</html>