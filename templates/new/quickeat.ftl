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
			<#-- <span>Contrairement à une opinion répandue, le Lorem Ipsum n'est pas simplement du texte aléatoire. Il trouve ses racines dans une oeuvre de la littérature latine classique datant de 45 av</span> -->
		</div> <!-- End center-bloc -->

		<div class="chosecity">
		</div> <!-- End chosecity -->

	</div> <!-- End content -->

</body>

</html>