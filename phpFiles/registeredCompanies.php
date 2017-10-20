<?php

$company = array();

 if($_SERVER['REQUEST_METHOD']=='POST')
 {
	 
 $json = json_decode($_POST['register_data']);
 $reg_no = $json->reg_no;

 require_once 'connection.php';

 $sql = "SELECT * FROM registered_students WHERE Reg_no='$reg_no'";
 $r = mysqli_query($con,$sql);
 
 while ($row = mysqli_fetch_array($r, MYSQL_ASSOC)) {
    $company[] =  $row['Company'];  
}   
  echo json_encode($company); 

}

?>