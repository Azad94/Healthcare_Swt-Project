<?php
/**
 * This destroys the session and logs out the user, redirecting to the login page
 * Written by Jan-Ole Petersen
 */
session_start();
session_unset();
session_destroy();

header("location:Login.php");
exit();

?>
