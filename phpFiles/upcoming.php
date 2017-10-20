<?php

$company = array();

 if($_SERVER['REQUEST_METHOD']=='POST')
 {

 require_once 'connection.php';

   // $json  = json_decode($_POST['company_request']);
     


//session_start();
    
     $sql = "SELECT * FROM company_data";
     $r = mysqli_query($con,$sql);
 
 while ($row = mysqli_fetch_array($r, MYSQL_ASSOC)) {
    $company["name"]=  $row['company_name'];  
   $company["date"]=  $row['date_registration']; 
 $ans[]=$company;
 }

 echo json_encode($ans);
 
 }


?>