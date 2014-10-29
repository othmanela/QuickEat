<#include "./common/header.ftl">




	<div id="content">

		<div class="center-bloc" id="results">
			<span>Quick Eats is a web app for finding the restaurant where you can get a meal in the shortest amount of time.  To find the quickest restaurant, the web app factors in both how long it will take you to reach a restaurant based on your current location and the expected wait time for that restaurant based on the day of the week and the time of day.  After dining at restaurant, you can visit the Quick Eats website to enter how long you had to wait for service.  Additionally, restaurant owners can visit the Quick Eats website and enter their expected wait time for any day and hour of the week.  To find a place to eat, hit the search button and Quick Eats will use your current location to return an ordered list of the quickest places to grab a meal.</span> 
		</div> <!-- End center-bloc -->

		<div class="chosecity">
		</div> <!-- End chosecity -->

	</div> <!-- End content -->

  <script>
  function findRestaurants(){
  $.post("/cis330-gp16/quickeat",
    function(data){
      $("#results").html(data);
    }
  );
}
</script>

</body>

</html>