<?php

/**
 * HTML code by Kim Huber
 * PHP code by Jan-Ole Petersen
 *
 * This page is for logging in
 */

/**
 * Load Model and Api
 */
include "autoload.php";

/**
 * Start session and check if user is already logged in
 */
session_start();
if(isset($_SESSION['user'])){
    header("location:index.php");
    exit;
}

/**
 * Get content of the login form
 */
if(isset($_POST['submit'])) {

    /**
     * Call REST server with api and post the login data
     */
    $api_instance = new \Swagger\Client\Api\LoginApi();
    try {
        $user = $api_instance->loginPost(new \Swagger\Client\Model\Body($_POST));
    } catch (Exception $e) {
        echo 'Exception when calling UserApi->usersUserNameGet: ', $e->getMessage(), PHP_EOL;
    }


    /**
     * Check if the REST server returned a user and if the password matches
     */
    if ($user !== false && $_POST['password'] == $user->getPassword()) {
        $_SESSION['user'] = $user;
        if(isset($_SESSION['user']))
        {
            header("Location: index.php");
            exit;
        }
    }
    else
    {
        echo "Name oder Passwort war ungültig<br>";
    }

}

?>

<!doctype html>
<html lang="de">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

    <title>Login</title>

    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />

    <!--     Fonts and icons     -->
    <link rel="stylesheet" href="assets/font-awesome-4.7.0/css/font-awesome.min.css">

    <!-- CSS Files -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="assets/css/material-kit.css" rel="stylesheet"/>

</head>

<body class="signup-page">
<div class="wrapper">
    <div class="header header-filter">
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
                    <div class="card card-signup">
                        <form class="form" method="POST">
                            <p class="text-divider">Login</p>
                            <div class="content">
                                <div class="input-group">
										<span class="input-group-addon">
											<i class="fa fa-user"></i>
										</span>
                                    <div class="form-group label-floating">
                                        <label class="control-label">Name</label>
                                        <input type="text" class="form-control" name="name">
                                    </div>
                                </div>

                                <div class="input-group">
										<span class="input-group-addon">
											<i class="fa fa-unlock-alt "></i>
										</span>
                                    <div class="form-group label-floating">
                                        <label class="control-label">Password</label>
                                        <input type="password" class="form-control" name="password">
                                    </div>
                                </div>
                            </div>
                            <div class="footer text-center">
                                <button type="submit" name="submit" class="btn btn-info btn-round">Login</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


</body>
<!--   Core JS Files   -->
<script src="assets/js/jquery.min.js" type="text/javascript"></script>
<script src="assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="assets/js/material.min.js"></script>

<!-- Control Center for Material Kit: activating the ripples, parallax effects, scripts from the example pages etc -->
<script src="assets/js/material-kit.js" type="text/javascript"></script>

</html>
