<?php
$image_url = $_GET['image_path'];
if (!readfile($image_url))
   echo "Error loading file";
?>