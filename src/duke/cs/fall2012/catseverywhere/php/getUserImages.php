<?php
//MYSQL QUERY
$con = mysql_connect("mysql.squashysquash.com","antaressql","password");
if (!$con)
  {
  die('Could not connect: ' .mysqlerror());
  }

$user = mysql_real_escape_string($_REQUEST['user']);
mysql_select_db("catseverywhere", $con);
$result = mysql_query("SELECT path FROM photo WHERE owner = '$user'");

while ($info = mysql_fetch_array($result)) {
    $content[] = $info;
}
$count = count($content);
$result=array();
for($i=0;$i<$count;$i++)
{
    $result[]=$content[$i]['path'];
}

echo json_encode($result);

//while ($row = mysql_fetch_array($result)) {
//    echo $row["path"];  
//}
mysql_close($con);
?>