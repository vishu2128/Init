<?php

include('connection.php');
$response=array();

$sql="SELECT * FROM user_details";
$r=mysqli_query($con,$sql);
echo mysqli_error($con);

$c=mysqli_fetch_array($r);

$response["updatable"]=$c["updatable"];
echo json_encode($response);
?>