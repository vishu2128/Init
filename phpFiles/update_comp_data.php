<?php
$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $json = json_decode($_POST['updatedata']);
 
 $company  = $json->name;
 $lnk = $json->lnk;
 $course = $json->courses;

 $ctc = $json->ctc;
 $designation = $json->designation;

 $e =$json->eligibility;
 $location = $json->location;
 $arrival_date = $json->date_arrival;
 $test_date = $json->date_test;
 $date_register = $json->date_registration; 
 
 require_once('connection.php');
 
  //update query
  $q="UPDATE company_data SET `company_link`='$lnk', `course_allowed`='$course', `ctc`='$ctc', `eligibility`='$e', `designation`='$designation', `location`='$location', `date_of_arrival`='$arrival_date', `date_onlineTest`='$test_date', `date_registration`='$date_register' WHERE company_name='$company' ";
  
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