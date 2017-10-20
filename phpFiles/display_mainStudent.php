<?php
$response = array();

   if($_SERVER['REQUEST_METHOD']=='POST'){
 
   $json=json_decode($_POST['display_student']);
   $c=$json->reg_no;
     require_once('connection.php');
	 
	 $sql="SELECT * FROM user_details WHERE Reg_no='$c' ";
	 $q = mysqli_query($con,$sql);

	 echo mysqli_error($con);
	 
	 while($row=mysqli_fetch_array($q,MYSQL_ASSOC))
	 {
				 $response["name"]=$row["Name"];
		 		 $response["reg_no"]=$row["Reg_no"];
			     $response["course"]=$row["Course"];
				 $response["branch"]=$row["Branch"];
				 $response["photo"]=$row["Photo"];
				 $response["tpo_credits"]=$row["tpo_credits"];
				 $response["placed"]=$row["check_intern"];
				 $response["package"]=$row["Package"];
				 $response["company"]=$row["Company"];
				 $response["cpi"]=$row["CPI"];
				 
	 }
	 
	 echo mysqli_error($con);
	 if(mysqli_error($con)=="")
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
