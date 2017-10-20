<?php
$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $json = json_decode($_POST['updatedata']);
    
	$name = $json->name;
    $reg_no = $json->reg_no;
    $dob = $json->dob;
    $gender = $json->gender;
    $hostel = $json->hostel;
    $address = $json->address;
    $city = $json->city;
    $state = $json->state;
    $email = $json->email;
    $phone =$json->phone;
 
 require_once('connection.php');
 
  //update query
  $q="UPDATE user_details SET `Name`='$name', `contact_no`='$phone', `DOB`='$dob', `Gender`='$gender', `Hostel`='$hostel', `Address`='$address', `City`='$city', `State`='$state', `email`='$email' WHERE Reg_no='$reg_no' ";
  
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