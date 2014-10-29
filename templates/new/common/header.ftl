<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1, maximum-scale=1" />
<meta name="viewport" content="width=device-width" />
	
	<title>${title}</title>
	
	
<!-- OLD HEADER -->

<link rel="stylesheet" type="text/css" href="css/reset.css" media="screen" /> <!-- Reset CSS -->
<link rel="stylesheet" type="text/css" href="style.css" media="screen" /> <!-- General CSS -->
<link type="text/css" rel="stylesheet" href="css/mobile.css" media="screen and (max-width: 640px)" /> <!-- Pour Appareils mobile -->

<!-- Fix Internet Explorer -->
<script type="text/javascript" src="js/selectivizr.js"></script>
<!--[if (gte IE 6)&(lte IE 8)]>
  <script type="text/javascript" src="js/selectivizr.js"></script>
<![endif]-->

<!-- BootStrap -->
<link href="js/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="js/bootstrap/js/bootstrap.min.js"></script>

<!-- Jquery 
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> -->

<!-- Jquery Include -->
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script src="https://raw.github.com/trentrichardson/jQuery-Timepicker-Addon/master/jquery-ui-timepicker-addon.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />
<script>
$(function() {
var availableTags = [
	"Chipotle (3811 Walnut St, Philadelphia PA 19104) **id:1**",
	"Chipotle (2018 Chestnut St, Philadelphia PA 19104) **id:2**",
	"GreekLady (2001 Walnut St, Philadelphia PA 19104) **id:3**",
	"Mad Mex (1002 Market St, Philadelphia PA 19104) **id:4**",
	"Bobby's Burger Palace (3800 Walnut St, Philadelphia PA 19104) **id:5**",
	"Jimmy John's (4302 Walnut St, Philadelphia PA 19104) **id:6**" ];
$( "#tags" ).autocomplete({
source: availableTags
});
});
</script>

<script>
$(function() {
	$("#datepicker").datetimepicker();

});

</script>

<!-- OLD HEADER -->

</head>

<body>

	<div id="header">

		<#include "./menu.ftl">
			
		</div> <!-- End header-foot1 -->

		<div id="page">

			<div class="box-user">

				<span class="span_log"><a href="">Already have a Account ? Login in</a></span>
				<span class="span_reg"><a href="">Register here</a></span>

			</div> <!-- End box-user -->

			<div class="clearfix"></div>

			<div class="headercontent">
				<img src="images/logo.png" alt="Quick eat">
				<div class="bg-header-map">
					<img src="images/map.png">
				</div> <!-- End bg-header-map -->
			</div> <!-- headercontent -->

		</div> <!-- End page -->


		<div class="clearfix"></div>
		
				<div class="header-foot2">

			<div class="footerbooking">
				<a href="/cis330-gp16/quickeat"><button class="btn btn-large btn-block btn-warning" type="button" >Find quick eat</button></a>
			</div> <!-- End footerbooking -->

		</div> <!-- End header-foot2 -->

	</div> <!-- End header -->
