<?php
$response = array();
//$response["status"]="done";
//echo  json_encode($response);

if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $json = json_decode($_POST['tpo_reduce']);
 
 $reg_no  = $json->reg_no;
 $credits = (int)$json->credits;
 
 require_once('connection.php');
 
 $sql = "SELECT *FROM user_details WHERE Reg_no='$reg_no' " ;
 
  $r = mysqli_query($con,$sql);
 
 $res = mysqli_fetch_array($r,MYSQL_ASSOC);
 
 $red = $res["tpo_credits"];
 $up= $red - $credits;
 
  //update query
  $q="UPDATE user_details SET tpo_credits='$up' WHERE Reg_no='$reg_no' ";
  
    $r = mysqli_query($con,$q);
	
	if(mysqli_error($con)=="")
	{
		$response["status"]=1;
		echo json_encode($response);
	}
	else
	{
		echo mysqli_error($con);
		$response["status"]=0;
		echo json_encode($response);
	}
}
 ?>
 