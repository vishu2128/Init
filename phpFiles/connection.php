<?php
$host = "localhost";
$dbname = "init";
$username = "root";
$pass= "";

$con= mysqli_connect ($host,$username,$pass,$dbname);
  if(mysqli_connect_error())
  {
	die("Connection problem !! ".mysqli_connect_error() ); 
  }

?>