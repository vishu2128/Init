<?php
 echo "1";
ServerConfig();

$PdfUploadFolder = 'PhotoUpload/';
 
$ServerURL = 'http://192.168.43.25/'.$PdfUploadFolder;
 
if($_SERVER['REQUEST_METHOD']=='POST'){
 
 if(isset($_POST['name']) and isset($_FILES['jpg']['name'])){

 $con = mysqli_connect(HostName,HostUser,HostPass,DatabaseName);
 
 $PdfName = $_POST['name'];
 
 $PdfInfo = pathinfo($_FILES['jpg']['name']);
 
 $PdfFileExtension = $PdfInfo['extension'];
 
 $PdfFileURL = $ServerURL . $PdfName . '.' . $PdfFileExtension;
 
 $PdfFileFinalPath = $PdfUploadFolder . $PdfName . '.'. $PdfFileExtension;
 
 try{
 
 move_uploaded_file($_FILES['jpg']['tmp_name'],$PdfFileFinalPath);
 
 $InsertTableSQLQuery = "UPDATE user_details SET `Photo`='$PdfFileURL' WHERE Reg_no='$PdfName' ";
	
 
 mysqli_query($con,$InsertTableSQLQuery);

 echo "AA";
 echo mysqli_error($con);
 }catch(Exception $e){} 
 mysqli_close($con);
 
 }
}

function ServerConfig(){
 
define('HostName','localhost');
define('HostUser','root');
define('HostPass','');
define('DatabaseName','init');
 
}

function GenerateFileNameUsingID(){
 
 $con2 = mysqli_connect(HostName,HostUser,HostPass,DatabaseName);
 
 $GenerateFileSQL = "SELECT max(id) as id FROM user_details";
 
 $Holder = mysqli_fetch_array(mysqli_query($con2,$GenerateFileSQL));

 mysqli_close($con2);
 
 if($Holder['id']==null)
 {
 return 1;
 }
 else
 {
 return ++$Holder['id'];
 }
}

?>