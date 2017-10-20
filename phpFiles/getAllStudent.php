<?php

$response = array();

 if($_SERVER['REQUEST_METHOD']=='POST')
 {

 require_once 'connection.php';

   // $json  = json_decode($_POST['company_request']);
     


//session_start();
    
     $sql = "SELECT * FROM user_details";
     $r = mysqli_query($con,$sql);
 
 while ($row = mysqli_fetch_array($r, MYSQL_ASSOC)) {
				 $response["name"]=$row["Name"];
		 		 $response["reg"]=$row["Reg_no"];
			     $response["course"]=$row["Course"];
				 $response["branch"]=$row["Branch"];
				 $response["photo"]=$row["Photo"];
				 $response["email"]=$row["email"];
				 $response["phone"]=$row["contact_no"];
				 $response["credits"]=$row["tpo_credits"];
				 $response["placed"]=$row["check_intern"];
				 $response["ctc"]=$row["Package"];
				 $response["company"]=$row["Company"];
				 
 $ans[]=$response;
 }

 echo json_encode($ans);
 
 }


?>