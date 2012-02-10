<!DOCTYPE html>
<% String contextPath = getServletContext().getContextPath(); %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>SocialPM</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<!-- Le styles -->
<link href="<%= contextPath %>/bootstrap/css/bootstrap.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 60px;
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}
</style>
<link href="<%= contextPath %>/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="<%= contextPath %>/favicon.ico">
<link rel="apple-touch-icon" href="<%= contextPath %>/favicon.ico">

<link rel="apple-touch-icon" sizes="72x72"
	href="<%= contextPath %>/images/apple-touch-icon-72x72.png">
<link rel="apple-touch-icon" sizes="114x114"
	href="<%= contextPath %>/images/apple-touch-icon-114x114.png">
    
    <script type="text/javascript" language="javascript" src="<%= contextPath %>/app/app.nocache.js"></script>    
</head>

<body>

	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</a> 
				
				<a class="brand" href="#">SocialPM</a>

				<div class="nav-collapse">
					<ul class="nav">
						<li class="active"><a href="#">Home</a></li>

						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>
					
					<p id="authentication" class="navbar-text pull-right">
					</p>
                    
				</div>
			</div>
		</div>
	</div>

	<div class="container-fluid">
		<div id="breadcrumbs" class="row-fluid"></div>    
        
		<div class="row-fluid">
			<div class="span3">
                <div id="sidenav"></div>
			</div>
			<div class="span9" id="content"></div>
		</div>

		<hr>

		<footer>
			<p>&copy; OCPsoft 2012</p>
		</footer>

	</div>

	<iframe src="javascript:''" id="__gwt_historyFrame"
		style="width: 0; height: 0; border: 0"></iframe>

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="<%= contextPath %>/js/jquery.min.js"></script>
	<script src="<%= contextPath %>/bootstrap/js/bootstrap-transition.js"></script>
	<script src="<%= contextPath %>/bootstrap/js/bootstrap-alert.js"></script>

	<script src="<%= contextPath %>/bootstrap/js/bootstrap-modal.js"></script>
	<script src="<%= contextPath %>/bootstrap/js/bootstrap-dropdown.js"></script>
	<script src="<%= contextPath %>/bootstrap/js/bootstrap-scrollspy.js"></script>
	<script src="<%= contextPath %>/bootstrap/js/bootstrap-tab.js"></script>
	<script src="<%= contextPath %>/bootstrap/js/bootstrap-tooltip.js"></script>
	<script src="<%= contextPath %>/bootstrap/js/bootstrap-popover.js"></script>

	<script src="<%= contextPath %>/bootstrap/js/bootstrap-button.js"></script>
	<script src="<%= contextPath %>/bootstrap/js/bootstrap-collapse.js"></script>
	<script src="<%= contextPath %>/bootstrap/js/bootstrap-carousel.js"></script>
	<script src="<%= contextPath %>/bootstrap/js/bootstrap-typeahead.js"></script>

</body>
</html>
