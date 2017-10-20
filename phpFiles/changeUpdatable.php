<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
$r=$_POST["updatable"];
include('connection.php');
echo $r;
$sql="UPDATE `user_details` SET `updatable` = '$r'";
$ry=mysqli_query($con,$sql);
echo mysqli_error($con);
}
?>