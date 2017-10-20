<?php
$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $json = json_decode($_POST['updatedata']);
    
	$branch = $json->branch;
    $course = $json->course;
    $cpi = $json->cpi;
    $ten = $json->ten;
    $twe = $json->twe;
	$reg_no = $json->reg_no;
	
 require_once('connection.php');
 
  //update query
  $q="UPDATE user_details SET `Course`='$course', `Branch`='$branch', `CPI`='$cpi', `10thper`='$ten', `12thper`='$twe' WHERE Reg_no='$reg_no' ";
  
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