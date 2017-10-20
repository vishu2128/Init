<?php
$response = array();

   if($_SERVER['REQUEST_METHOD']=='POST'){
 
   $json=json_decode($_POST['display_company']);
   $c=$json->name;
     require_once('connection.php');
	 
	 $sql="SELECT * FROM company_data WHERE company_name='$c' ";
	 $q = mysqli_query($con,$sql);

	 echo mysqli_error($con);
	 
	 while($row=mysqli_fetch_array($q,MYSQL_ASSOC))
	 {
		 $response["company_name"]=$row["company_name"];
		 $response["lnk"]=$row["company_link"];
		 $response["courses"]=$row["course_allowed"];
		 $response["ctc"]=$row["ctc"];
		 
		 $response["eligibility"]=$row["eligibility"];
		 $response["designation"]=$row["designation"];
		 $response["location"]=$row["location"];
		 $response["date_arrival"]=$row["date_of_arrival"];
		 $response["date_test"]=$row["date_onlineTest"];
		 $response["date_registration"]=$row["date_registration"];
		 
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
