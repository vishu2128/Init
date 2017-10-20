<?php
$response1 = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

 $json = json_decode($_POST['registerdata']);
 
 $company  = $json->name;
 $reg = $json->reg_no;
  require_once('connection.php');
	
	$q = "SELECT * FROM registered_students WHERE Reg_no='$reg' AND Company='$company'" ; 
	$r = mysqli_query($con,$q);
 
	$res = mysqli_fetch_array($r);
	$k=mysqli_num_rows($r);
	
	if($k==0)
	{
		$sql="INSERT INTO `registered_students` (
		`Reg_no` ,
		`Company`
		)
		VALUES (
		'$reg',  '$company'
		) ";
 
 
		$r = mysqli_query($con,$sql);
   
		if(mysqli_error($con)=="")
		{
			$response1["status"] = 1;
			echo json_encode($response1);
		}
		else{
			$response1["status"] = 0;
			echo json_encode($response1);
		}
	}
	
	else{
		$response1["status"] = -1;
			echo json_encode($response1);
	}

  
  
  
}
?>

 
 