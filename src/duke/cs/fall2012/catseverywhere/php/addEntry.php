<?php
$mail = $_REQUEST['email'];
echo $mail;
$pw = $_REQUEST['password'];
$first = $_REQUEST['name'];

$con = mysql_connect("mysql.squashysquash.com","antaressql","password");
if (!$con)
  {
  die('Could not connect: ' .mysqlerror());
  }

mysql_select_db("catseverywhere", $con);

mysql_query("INSERT INTO user (email, password, name) VALUES ('$mail', '$pw', '$first')");

mysql_close($con);
?>