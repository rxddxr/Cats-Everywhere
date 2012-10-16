<?php
//UPLOAD TO SERVER
$target_path1 = "images/";
/* Add the original filename to our target path.
Result is "uploads/filename.extension" */
$target_path1 = $target_path1 . basename( $_FILES['uploadedFile']['name']);
echo $target_path1;
if(move_uploaded_file($_FILES['uploadedFile']['tmp_name'], $target_path1)) {
    echo "The first file ".  basename( $_FILES['uploadedfile']['name']).
    " has been uploaded.";
} else{
    echo "There was an error uploading the file, please try again!";
    echo "filename: " .  basename( $_FILES['uploadedfile']['name']);
    echo "target_path: " .$target_path1;
}
$user = $_REQUEST['user'];
echo "n String Parameter send from client side : " . $user;

//ADD TO DB
//get db fields via POST
$id = $_REQUEST['id'];
echo $id;
//$owner = $_REQUEST['owner'];
//$locaton = $_REQUEST['location'];
//$keywords = $_REQUEST['keywords'];

//MYSQL
$con = mysql_connect("mysql.squashysquash.com","antaressql","password");
if (!$con)
  {
  die('Could not connect: ' .mysqlerror());
  }

mysql_select_db("catseverywhere", $con);
mysql_query("INSERT INTO photo (id, path) VALUES ('$id', '$target_path1')");
mysql_close($con);
?>