<?php
$response = array();

   if($_SERVER['REQUEST_METHOD']=='POST'){
 
   $json=json_decode($_POST['sendPrevious']);
   $c=$json->reg_no;
   require_once('connection.php');
	 
	$sql="SELECT * FROM user_details WHERE Reg_no='$c' ";
	$q = mysqli_query($con,$sql);

	 echo mysqli_error($con);
	 
	 while($row=mysqli_fetch_array($q,MYSQL_ASSOC))
	 {
				 $response["previous"]=$row["Previous"];
	 }
	 
	 echo mysqli_error($con);
	 if(mysqli_error($con)=="")
	 {
		 $response["status"]=1;
		  echo json_encode($response);
	 }
	 else
	 {
		 $response["status"]=0;
		  echo json_encode($response);
	 }
	
	}
	
?>
