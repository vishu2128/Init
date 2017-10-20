<?php

$company = array();

 if($_SERVER['REQUEST_METHOD']=='POST')
 {
	 $json=json_decode($_POST['sendData']);
	

	$name  = $json->name;
	

 require_once 'connection.php';

	
     $sql = "SELECT * FROM user_details WHERE Company='$name' ";
     $r = mysqli_query($con,$sql);
 if(mysqli_num_rows($r)==0)
 {
	 echo json_encode($company);
 }
 else{
 while ($row = mysqli_fetch_array($r, MYSQL_ASSOC)) {
    $company["reg"]=  $row['Reg_no'];  
   $company["name"]=  $row['Name']; 
 $ans[]=$company;
 }
  echo json_encode($ans);
 }

 
 }


?>