<#include "./common/header.ftl">


	<div id="content">

		<div class="center-bloc">
			
			<div class="form-gen bloc horizontal single">

				<form name="input" action="/cis330-gp16/customer" method="POST">

					<ul>
						<li>
							<label>Business ID</label>
							<input name="BusinessID" id="tags" placeholder="Inser your Business ID">
						</li>

						<li>
							<label>Date time</label>
							<input id="datepicker" name="DateTime" value="" placeholder="Inser a valid date">
						</li>

						<li>
							<label>Wait time</label>
							<input id="" name="WaitTime" value="" placeholder="Inser a valid date">
						</li>
					</ul>

					<a href="" style="float: left;" class="btn btn-primary">Submit</a>
					<span id="demo" class="formnote">Click the button to get your coordinates:</span>
					

				</form>

				<button onclick="getLocation()" style="float: left;" class="btn btn-success">Find nearby</button>
				<br/>

			<div class="span6">
				<ol id="nearbyRestaurants">
				</ol>
				<script>
					var x = document.getElementById("demo");
					function getLocation() {
						if (navigator.geolocation) {
							navigator.geolocation.getCurrentPosition(showPosition);

						} else {
							x.innerHTML = "Geolocation is not supported by this browser.";
						}
					}
					function showPosition(position) {
						x.innerHTML = "Latitude: " + position.coords.latitude
								+ "<br>Longitude: " + position.coords.longitude;
						$.getJSON('/cis330-gp16/nearby', {
							"latitude" : position.coords.latitude,
							"longitude" : position.coords.longitude
						}, function(data) {
							for ( var i = 0; i < data.length; i++) {
								$('#nearbyRestaurants').append(
										'<li>' + data[i] + '</li>');
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