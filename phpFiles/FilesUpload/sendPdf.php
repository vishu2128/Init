<?php
require_once 'connection.php';

define('HostName','localhost');
define('HostUser','root');
define('HostPass','');
define('DatabaseName','init');
 
//connecting to the db
 $con = mysqli_connect(HostName,HostUser,HostPass,DatabaseName);
 
 $json=json_decode($_POST['get_pdf']);
 $c=$json->reg_no;
   
//sql query
$sql="SELECT * FROM user_details WHERE Reg_no='$c' ";
 
//getting result on execution the sql query
$result = mysqli_query($con,$sql);
 
//response array
$response = array();
 
$response['error'] = false;
 
$response['message'] = "PDfs fetched successfully.";
 
//traversing through all the rows
 
	 while($row=mysqli_fetch_array($result,MYSQL_ASSOC))
{
    $response['url'] = $row['Resume'];
    $response['name'] = $row['PdfName'];
}
 
echo json_encode($response);
?>

