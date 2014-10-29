<#include "./common/header.ftl">




<style>

#page {
  display: none;
}

#loadingMessage {
color:blue;
text-align:center;
font-size:20px;
margin-top: 2cm;
}

</style>

<#-- <p id="demo"/> -->

<script>

    $( function(){
      // var x = document.getElementById("demo");
      function getLocationAndPost() {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(showPositionAndPost);
        } else {
          console.log("GEO NOT WORKING");
          // x.innerHTML = "Geolocation is not supported by this browser.";
        }
      }
      function showPositionAndPost(position) {
        // alert("Latitude: " + position.coords.latitude + "<br>Longitude: " + position.coords.longitude);
        $.post('/cis330-gp16/quickeat', {
          "latitude" : position.coords.latitude,
          "longitude" : position.coords.longitude
        }, function(data) {
          $("#loadingMessage").remove();
          $("#results").html(data);
        });
      }
      getLocationAndPost();
    }

    );

    function hideBelow3(){
      var rows = $(".rating").parent();
      for (var i=0;i<rows.length;i++)
      { 
        var curr = rows[i];
        // console.log($(curr).children(".rating").text()>=3);
        if ($(curr).children(".rating").text()<3.5){
          $(curr).hide();
        } else{
        }
      }
    }

    function showAllRatings(){
      $("tr").show();
    }


 </script>


	<div id="content">

    

    <button onClick="hideBelow3()">Hide Low Ratings</button>
    <button onClick="showAllRatings()">Show All Ratings</button>

    <p id="loadingMessage">Finding results based on your location...</p>

		<div class="center-bloc">
      <div id="results"></div>
			<#-- <span>Contrairement à une opinion répandue, le Lorem Ipsum n'est pas simplement du texte aléatoire. Il trouve ses racines dans une oeuvre de la littérature latine classique datant de 45 av</span> -->
 <#--      <table class="table table-striped">
        <thead>
          <tr>
            <th>Company</th>
            <th>Location</th>
            <th>Actif</th>
          </tr>
        </thead>

        <tbody>
        <tr>
          <td>Dharma Initiative</td>
          <td>Honolulu USA</td>
          <td>Yes</td>
        </tr>
        <tr>
          <td>Ajira Airways</td>
          <td>Ajira Airways</td>
          <td>No</td>
        </tr>
        <tr>
          <td>Clas Ohlson</td>
          <td>Stockholm Sweden</td>
          <td>No</td>
        </tr>
        <tr>
          <td>Zwoty Galery</td>
          <td>Warzsawa Poland</td>
          <td>No</td>
        </tr>
              </tbody>
      </table>
		</div> <!-- End center-bloc --> 


		<div class="chosecity">
		</div> <!-- End chosecity -->

	</div> <!-- End content -->

</body>

</html>