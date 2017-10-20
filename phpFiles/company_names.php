<?php

$company = array();

 if($_SERVER['REQUEST_METHOD']=='POST')
 {

 require_once 'connection.php';

 $sql = "SELECT company_name FROM company_data";
 $r = mysqli_query($con,$sql);
 
 while ($row = mysqli_fetch_array($r, MYSQL_ASSOC)) {
    $company[] =  $row['company_name'];  
}   
  echo json_encode($company); 

}

?>