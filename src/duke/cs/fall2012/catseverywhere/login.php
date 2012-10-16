<?php
$mail = $_REQUEST['email'];
$pw = $_REQUEST['password'];

$con = mysql_connect("mysql.squashysquash.com","antaressql","password");
if (!$con)
  {
  die('Could not connect: ' .mysqlerror());
  }

mysql_select_db("catseverywhere", $con);

$result = mysql_query("SELECT * FROM user WHERE user.email = ('$mail') AND user.password = ('$pw')");

if (mysql_num_rows($result) == 1) {
    echo "true";
}
else {
    echo "false";
}

mysql_close($con);
?>