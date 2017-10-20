<?php


$response2 = array();

// check for required fields
 if($_SERVER['REQUEST_METHOD']=='POST')
 {

 require_once 'connection.php';

    $json  = json_decode($_POST['tpo_request']);
     
    $name = $json->name;
    $reg_no = $json->reg_no;
  $message = $json->message;

//session_start();
    
     $sql = "INSERT INTO `tpo_request` (`Reg_no`,`Name`, `Message`)VALUES ('$reg_no','$name','$message')";
     $r = mysqli_query($con,$sql);
 
   $res = mysqli_fetch_array($r, MYSQL_ASSOC);
    $k=mysqli_num_rows($res);
if($k==0)
{
    $response2["status"] = 0;
    echo json_encode($response2);
}
else{
    $response2["status"] = 1;
    echo json_encode($response2);
}    
   
}
?>