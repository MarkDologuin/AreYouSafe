
<?php
/* Set internal character encoding to UTF-8 */
mb_internal_encoding("UTF-8");
$X_loc = $_GET["x"];
$Y_loc = $_GET["y"];



$conn_string = "host=mgltr.root.sx port=5432 dbname=AreYouSafe user=team_blue password=safe123";
$dbconn = pg_pconnect($conn_string);

if(!$dbconn){
	print("ERROR_C");
	exit;
}


$query = "SELECT SUM(total_killed) as \"KILLED\" ,SUM(total_injured)as \"INJURED\", MAX(contrib_factor_1) as \"FACTOR1\", MAX(contrib_factor_2) as \"FACTOR2\" FROM \"ALL_INCIDENTS\" ";
$query .= "WHERE ST_Distance(geo_32118, ST_Transform(ST_GeomFromText('POINT(" . $X_loc . " " . $Y_loc . ")',4326), 32118)) <= 500 ";


$result = pg_query($query);
pg_close($dbconn);


if (!$result) {
	print("ERROR_Q");
  exit;
}

$arr = pg_fetch_all($result);


print_r(json_encode($arr[0], JSON_FORCE_OBJECT) );




 
?>
