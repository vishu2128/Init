<?php
include('connection.php');

$response = array();

// check for required fields
 if($_SERVER['REQUEST_METHOD']=='POST')
 {
 $json  = json_decode($_POST['sendData']);
 
 $reg_no=$json->reg_no;
  $pass=$json->password;
  
  $sql="UPDATE `user_details` SET `password` = '$pass' WHERE `Reg_no` = '$reg_no'"; 
   $r=mysqli_query($con,$sql);
   echo mysqli_error($con);
   if(mysqli_error($con) == "")
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