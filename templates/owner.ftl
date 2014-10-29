<#include "./common/header.ftl">

	
<style>

#page {
  display: none;
}

</style>

	<div id="content">

		<div class="center-bloc">
			
			<div class="form-gen">

				<form name="input" action="/cis330-gp16/owner" method="POST">

					<ul>
						<li>
							<label>Restaurant name</label>
							<input id="autocomplete" name="businessID" value="" placeholder=${businessID} disabled>
						</li>

						<#-- <li>
							<label>Date time</label>
							<input id="datepicker" name="DateTime" value="" placeholder="Insert a valid date">
						</li> -->

						<li>
							<label>Wait time</label>
							<input id="" name="WaitTime" value="" placeholder="Insert the wait time">
						</li>

							<input type="hidden"  name="businessID" value=${businessID}>

					</ul>

					<input href="" type="submit" style="float: left;" class="btn btn-primary">
				</form>

				<script>
				$(function() {
					$("#datepicker").datetimepicker();

				});

				$(function() {
					var availableTags = [
							"Chipotle (3811 Walnut St, Philadelphia PA 19104) **id:1**",
							"Chipotle (2018 Chestnut St, Philadelphia PA 19104) **id:2**",
							"GreekLady (2001 Walnut St, Philadelphia PA 19104) **id:3**",
							"Mad Mex (1002 Market St, Philadelphia PA 19104) **id:4**",
							"Bobby's Burger Palace (3800 Walnut St, Philadelphia PA 19104) **id:5**",
							"Jimmy John's (4302 Walnut St, Philadelphia PA 19104) **id:6**" ];

					$('#autocomplete').typeahead({
						source : availableTags
					});

				});
				</script>

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

			</div> <!-- End form-gen -->

		</div> <!-- End center-bloc -->

		<div class="chosecity">
		</div> <!-- End chosecity -->

	</div> <!-- End content -->

</body>

</html>