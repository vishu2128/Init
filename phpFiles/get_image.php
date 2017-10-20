<?php
$response = array();

   if($_SERVER['REQUEST_METHOD']=='POST'){
 
	//echo $_POST['updatedata'];
   $json=json_decode($_POST['updatedata']);
   //echo $json;
   
   $c=$json->reg_no;
   
     require_once('connection.php');
	 
	 $sql="SELECT * FROM user_details WHERE Reg_no='$c' ";
	 $q = mysqli_query($con,$sql);

	 echo mysqli_error($con);
	 
	 while($row=mysqli_fetch_array($q,MYSQL_ASSOC))
	 {
				 $response["image"]=base64_encode($row["Photo"]);
				 //echo "7";
				 
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
