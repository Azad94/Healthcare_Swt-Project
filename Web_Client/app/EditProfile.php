<?php
/**
 *HTML base code by Kim Huber, adjusted by Jan-Ole Petersen
 * PHP code by Jan-Ole Petersen
 */

/**
 * Load Model and Api
 */
include "autoload.php";

/**
 * Start session and check if user is logged in
 */
session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}

$user = $_SESSION['user'];

/**
 * Get content of form and update user
 */
if(isset($_POST['save']))
{
    $userApi_instance = new \Swagger\Client\Api\UserApi();
    try
    {
        $editUser = $userApi_instance->usersUserIdGet($user->getId());
        if(isset($_POST['name']) && !empty($_POST['name']))
            $editUser->setName($_POST['name']);
        if(isset($_POST['email']) && !empty($_POST['email']))
            $editUser->setEmail($_POST['email']);
        if(isset($_POST['password']) && !empty($_POST['password']))
        {
            if(isset($_POST['confirm_password']) && !empty($_POST['confirm_password']))
            {
                if($_POST['password'] == $_POST['confirm_password'])
                    $editUser->setPassword($_POST['password']);
            }
        }
        $userApi_instance->usersUserIdPut($editUser->getId(), $editUser);
        $_SESSION['user'] = $editUser;
    }
    catch (Exception $e)
    {
        echo $e->getMessage();
    }

    /**
     *
     */
    header("Location: Profile.php");
    exit;

}
elseif (isset($_POST['cancel']))
{
    header("Location: Profile.php");
    exit;
}



/**
 * Title of the page
 */
$title = "Profil bearbeiten";
/**
 * Which menu item to show
 */
$scriptExpand = "'Nutzer'";
/**
 * Additional script for the page
 */
$script ="'assets/js/emptyScript.js'";
/**
 * HTML code of the template content
 */
$content ='
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            <div class="container">
                <form class="card card-signup" method="POST">
                    <div class="row">
                        <h2 class="text-center">Nutzerprofil</h2>
                    </div>
                    
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Name:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <input value="'.$user->getName().'" placeholder="Name" type="text" class="form-control" name="name">
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">E-Mail:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <input value="'.$user->getEmail().'" placeholder="E-Mail" type="email" class="form-control" name="email">
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Passwort:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <input placeholder="Passwort" type="password" class="form-control" name="password">
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Passwort wiederholen:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <input placeholder="Passwort wiederholen" type="password" class="form-control" name="confirm_password">
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-1 col-md-1 col-sm-1 col-md-offset-5">
                            <button type="submit" name="save" class="btn btn-info">Speichern</button>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-sm-offset-1">
                            <button type="submit" name="cancel" class="btn btn-info">Abbrechen</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>';

include 'Template.php';
?>