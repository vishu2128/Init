<?php
$response = array();
//$response["status"]="done";
//echo  json_encode($response);

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	$json=json_decode($_POST['sendData']);
	

	$reg_no  = $json->reg_no;
	$password = $json->password;
	
	require_once('connection.php');
 
	$sql = "SELECT *FROM user_details WHERE Reg_no='$reg_no' " ; 
	$r = mysqli_query($con,$sql);
 
	$res = mysqli_fetch_array($r);
	$k=mysqli_num_rows($r);
	
	if($k==0)
	{
		// you reg no is not present in database
		$response["status"]=-1;
		echo json_encode($response);
	}
	else if($k>0)
	{
		if($res['password']!= $password)
		{
			$response["status"]=0;
			echo json_encode($response);
			//mismatch
		}
		else if($res['updated']==0)
		{
			$response["status"]=1;
			echo json_encode($response);
		}
		else
		{
			//already updated profile
			$response["status"]=2;
			$response["isTpo"] = $res['IsTpo'];
			echo json_encode($response);
		}
	}
}
?>