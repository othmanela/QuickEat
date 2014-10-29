<!--<?php 
$_table_lien=explode('/',$_SERVER['PHP_SELF']);
$count_lien=count($_table_lien)-1; 
$_page=$_table_lien[$count_lien];

  switch($_page)
	{
			  case 'index.php':$styleindex="current";break;
			  case 'customer.php':$stylecustomer="current";break;
			  case 'owner.php':$styleowner="current";break;
	}
		   
?>

<div class="header-foot1">
<ul class="features">
	<li><a class="<?php echo $stylecustomer?>" href="customer.php"><h2>I am</h2> a customer</a></li>
	<li class="fe2"><a class="<?php echo $styleowner?>" href="owner.php"><h2>I am</h2> restaurant owner</a></li>
	<li class="fe3 last"><a class="<?php echo $styleindex?>" href="index.php"><h2>Find</h2> quick eat</a></li>
</ul>
-->

<div class="header-foot1">
<ul class="features">
	<li class="logo"><a class="index-link" id="index-logo" href="/cis330-gp16/index">Quick Eat</a></li>
	<li><a class="" id="customer-link" href="/cis330-gp16/customer"><h2>I am</h2> a customer</a></li>
	<li class="fe2"><a class="" id="owner-link" href="/cis330-gp16/owner"><h2>I am</h2> restaurant owner</a></li>
	<li class="fe3 last"><a class="" id="quickeat-link"href="/cis330-gp16/quickeat"><h2>Find</h2> quick eat</a></li>
</ul>

<script>
$("#${activeTab}-logo").attr("class", "current-logo");
</script>

<script>
$("#${activeTab}-link").attr("class", "current");
</script>