<?php
$ans= array();
$response = array();

   if($_SERVER['REQUEST_METHOD']=='POST'){
 
   $json=json_decode($_POST['sendReg']);
   $r=$json->reg_no;
 
 
     require_once('connection.php');
	 
	 $sql="SELECT * FROM messages where reg_no='$r' ";
	 $q = mysqli_query($con,$sql);

	 echo mysqli_error($con);
	 
	 $t=mysqli_num_rows($q);
	 $i=0;
	 //echo $t;
	 $row=mysqli_fetch_array($q,MYSQL_ASSOC);
	 
	// print_r($row);
	 while($t>0){
		 
	 
		        
		 		 $response["uid"]=$row["UID"];
			     $response["message"]=$row["Message"];
				 $response["response"]=$row["Response"];
				$ans[]=$response;
				  $row=mysqli_fetch_array($q,MYSQL_ASSOC);
				  
				  $t--;
	 }
	// $t--;
	// $i++;
	 
	 
	 echo mysqli_error($con);
	 if(mysqli_error($con)=="")
	 {
//$response["status"]=1;
//$ans[]=$response;
		  echo json_encode($ans);
	 }
	 else
	 {
		 //$response["status"]=0;
		// $ans[]=$response;
		  echo json_encode($ans);
	 }
	
	}
	
?>