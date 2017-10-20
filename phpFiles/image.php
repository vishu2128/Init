<?php
   $response=array();
   
   
    if($_SERVER['REQUEST_METHOD']=='POST')
    {
	
	//$json=json_decode($_POST['image_data']);
	//$img=($json->image);
    //$reg_no = $json->reg_no;
	$response["status"]=$_POST['image_data'];
	echo json_encode($response);
	$img = $_POST['image_data'];
	echo $img;
      
	require_once 'connection.php';  //connection establishment
 
    /*$sql = "INSERT INTO user_details('Photo') VALUES('$img') WHERE Reg_no='$reg_no' ";  //insert query
        $r = mysqli_query($con,$sql);
        $result = mysqli_fetch_array($r);
        header('content-type: image/jpeg');
       echo base64_encode($img);
	  if(mysqli_error($con)=="")
	  {
		  $response["status"]=1;
		  echo json_encode($response);
	  }
	  else
	  {
		  $response["status"]=0;
		  echo json_encode($response);
	  }*/
 
	}
 
 ?>