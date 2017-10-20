<?php

include('connection.php');
$response=array();

$sql="SELECT * FROM company_data";
$r=mysqli_query($con,$sql);
echo mysqli_error($con);


while($row=mysqli_fetch_array($r,MYSQL_ASSOC))
{
	
	$com_name=$row["company_name"];
	$response["Company_name"]=$com_name;
	$ans=array();
	$s=array();
	 include('connection.php');
	$q="SELECT * FROM user_details WHERE `Company`='$com_name' ";
     $rt=mysqli_query($con,$q);
	
	 echo mysqli_error($con);
	  while($ro=mysqli_fetch_array($rt,MYSQL_ASSOC))
	  { //echo $ro["Name"];
		  $s["name"]=$ro["Name"];
		 $s["branch"]=$ro["Branch"];
		  $s["reg_no"]=$ro["Reg_no"];
		 // echo json_encode($s);
		  $ans[]=$s;
	  }
	  $response["student"]=$ans;
	  
	  $stud[]=$response;
	  
}


echo json_encode($stud);
?>