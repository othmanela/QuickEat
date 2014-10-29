<#include "./common/header.ftl">

	
<style>

#page {
  display: none;
}

#thanks {
color:blue;
font-size:20px;
}


</style>

	<div id="content">

		<div class="center-bloc">
			
			<div class="form-gen bloc horizontal single">

				<form name="input" action="/cis330-gp16/customer" method="POST">

					<ul>
						<#if thankYou == 1>
							<p id="thanks">Thank you for your input!</p>
						</#if>
						<li>
							<label>Restaurant</label>
							<input name="BusinessID" id="tags" placeholder="Insert your Restaurant name">
						</li>

						<li>
							<label>Date time</label>
							<input id="datepicker" name="DateTime" value="" placeholder="Insert a valid date">
						</li>

						<li>
							<label>Wait time</label>
							<input id="" name="WaitTime" value="" placeholder="Insert wait time">
						</li>
					</ul>

					<input href="" type="submit" style="float: left;" class="btn btn-primary">
					<!--<span id="demo" class="formnote">Click the button to get your coordinates:</span>-->
					

				</form>

				<br/><br/>
				<button onclick="getLocation()" style="float: left;" class="btn btn-success">Find nearby</button>
				<br/>

			<div class="span6">
				<br>
				<ol id="nearbyRestaurants">
				</ol>
				<script>
					//<var x = document.getElementById("demo");
					function getLocation() {
						if (navigator.geolocation) {
							navigator.geolocation.getCurrentPosition(showPosition);

						} else {
							x.innerHTML = "Geolocation is not supported by this browser.";
						}
					}
					function showPosition(position) {
						//x.innerHTML = "Latitude: " + position.coords.latitude + "<br>Longitude: " + position.coords.longitude;
						$.getJSON('/cis330-gp16/nearby', {
							"latitude" : position.coords.latitude,
							"longitude" : position.coords.longitude
						}, function(data) {
							$('#nearbyRestaurants').empty();
							for ( var i = 0; i < data.length; i++) {
								$('#nearbyRestaurants').append(
										'<li style="text-align:left">' + data[i] + '</li>');
							}
						})
					}
				</script>
			</div>

			</div> <!-- End form-gen -->

		</div> <!-- End center-bloc -->

		<div class="chosecity">
		</div> <!-- End chosecity -->

	</div> <!-- End content -->

</body>

</html>