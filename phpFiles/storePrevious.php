<?php
   if($_SERVER['REQUEST_METHOD']=='POST'){
 
   $json=json_decode($_POST['store_previous']);
   $c=$json->prv;
   $reg=$json->reg;
     require_once('connection.php');
	 
	 $sql="UPDATE user_details SET `Previous`='$c' WHERE Reg_no='$reg' ";
	 $q = mysqli_query($con,$sql);
	 
	 echo mysqli_error($con);
   }
?>
