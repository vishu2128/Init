<?php
     $response=array();
   
   
    if($_SERVER['REQUEST_METHOD']=='POST')
    {
	 	$json=json_decode($_POST['imageData']);
		
		$reg_no=$json->reg_no;
		
		 require_once('connection.php');
		$sql="SELECT * FROM user_details WHERE Reg_no='$reg_no' ";
		$q=mysqli_query($con,$sql);
		        $result = mysqli_fetch_array($q,MYSQL_ASSOC);
				
				if(mysqli_error=="")
				{
				  $response["status"]=1;
				  $response["image"]=base64_encode($result["Photo"]);
		  echo json_encode($response);
				}
				else
				{  $response["status"]=0;
		  echo json_encode($response);
				
				}
		
		
	}
	
	
?>