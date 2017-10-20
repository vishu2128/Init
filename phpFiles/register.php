<?php


$response = array();

// check for required fields
 if($_SERVER['REQUEST_METHOD']=='POST')
 {

 require_once 'connection.php';

    $json  = json_decode($_POST['details']);
     
    $name = $json->name;
    $reg_no = $json->reg_no;
    $course = $json->course;
    $branch = $json->branch;
    $cpi = $json->cpi;
    $ten = $json->ten;
    $twe = $json->twe;
    $dob = $json->dob;
    $gender = $json->gender;
    $hostel = $json->hostel;
    $address = $json->address;
    $city = $json->city;
    $state = $json->state;
    $pass =$json->pass;
    $email = $json->email;
    $phone =$json->contact;

session_start();

$sql = "INSERT INTO  `init`.`user_details` (
`Name` ,
`Reg_no` ,
`Course` ,
`Branch` ,
`CPI` ,
`10thper` ,
`12thper` ,
`DOB` ,
`Gender` ,
`Hostel` ,
`Address` ,
`City` ,
`State` ,
`Reg_datetime` ,
`password` ,
`email` ,
`contact_no`,
`updated`
)
VALUES (
'$name', '$reg_no', '$course', '$branch', '$cpi', '$ten', '$twe','$dob', '$gender', '$hostel','$address', '$city', '$state', NOW(), '$pass', '$email', '$phone', '1'
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
?>