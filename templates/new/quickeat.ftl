<#include "./common/header.ftl">


  <script>
  function findRestaurants(){
  $.post("/cis330-gp16/quickeat",
    function(data){
      $("#results").html(data);
    }
  );
  }

  findRestaurants();
</script>

	<div id="content">

		<div class="center-bloc" id="results">
			<#-- <span>Contrairement � une opinion r�pandue, le Lorem Ipsum n'est pas simplement du texte al�atoire. Il trouve ses racines dans une oeuvre de la litt�rature latine classique datant de 45 av</span> -->
		</div> <!-- End center-bloc -->

		<div class="chosecity">
		</div> <!-- End chosecity -->

	</div> <!-- End content -->

</body>

</html>