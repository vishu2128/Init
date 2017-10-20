<?php
$response1 = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

 $json = json_decode($_POST['companydata']);
 
 $company  = $json->name;
 $lnk = $json->lnk;
 $course = $json->courses;

 $ctc = $json->ctc;
$designation = $json->designation;

$e =$json->cpi;
$location = $json->location;
$arrival_date = $json->dateArrival;
$test_date = $json->dateTest;
$date_register = $json->dateReg; 
  require_once('connection.php');

  $q="SELECT * FROM company_data where company_name='$company'";
$result = mysqli_query($con,$q);

  if(mysqli_num_rows($result)!=0)
{
	$response["status"]=2;
	echo json_encode($response);
}
else{


  $sql="INSERT INTO `company_data` (
`company_name` ,
`company_link` ,
`course_allowed` ,
`ctc` ,
`eligibility` ,
`designation` ,
`location` ,
`date_of_arrival` ,
`date_onlineTest` ,
`date_registration`
)
VALUES (
'$company',  '$lnk',  '$course',  '$ctc',  '$e',  '$designation',  '$location', '$arrival_date', '$test_date', '$date_register'
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
}
?>

 
 