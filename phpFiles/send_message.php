<?php
$response1 = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

 $json = json_decode($_POST['message_details']);
 
 $reg  = $json->reg_no;
 $msg = $json->msg;
 require_once('connection.php');

  $sql="INSERT INTO `messages` (
`reg_no` ,
`Message`
)
VALUES (
'$reg',  '$msg'
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

 
 