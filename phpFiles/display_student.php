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
				 $response["cpi"]=$row["CPI"];
				 $response["ten"]=$row["10thper"];
				 $response["twe"]=$row["12thper"];
				 $response["dob"]=$row["DOB"];
				 $response["gender"]=$row["Gender"];
				 $response["hostel"]=$row["Hostel"];
				 $response["address"]=$row["Address"];
				 
				 $response["city"]=$row["City"];
				 $response["state"]=$row["State"];
				 $response["resume"]=$row["Resume"];
				 $response["photo"]=$row["Photo"];
				 $response["email"]=$row["email"];
				 $response["contact_no"]=$row["contact_no"];
				 $response["tpo_credits"]=$row["tpo_credits"];
				 $response["placed"]=$row["check_intern"];
				 $response["package"]=$row["Package"];
				 $response["company"]=$row["Company"];
				 $response["updatable"] = $row["updatable"];
				 //echo "*";
				 
	 }
	 
	 echo mysqli_error($con);
	 if(mysqli_error($con)=="")
	 {
		 //echo "*";
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
