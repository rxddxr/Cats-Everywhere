<?php
//MYSQL QUERY
$con = mysql_connect("mysql.squashysquash.com","antaressql","password");
if (!$con)
  {
  die('Could not connect: ' .mysqlerror());
  }

mysql_select_db("catseverywhere", $con);
$result = mysql_query("SELECT path FROM photo");
echo json_encode($result);
mysql_close($con);
?>