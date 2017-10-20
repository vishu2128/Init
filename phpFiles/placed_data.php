<?php
$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
 
 
 $json = json_decode($_POST['placed_data']);
 
 $company = $json->company;
 foreach ( $json->results as $trend )
{
 //$name = $trend->name;
 $reg_no = $trend->reg_no;
// $branch = $trend->branch;
 $package = $trend->package;
 
 require_once('connection.php');
 

 
// insert query

$q ="UPDATE user_details SET `Package`='$package',`Company`='$company' WHERE Reg_no='$reg_no' ";;
  
    $r = mysqli_query($con,$q);
	
	  $qt="UPDATE registered_students SET `Status`='1' WHERE Reg_no='$reg_no' AND Company='$company'";
	  $f = mysqli_query($con,$qt);
}	
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