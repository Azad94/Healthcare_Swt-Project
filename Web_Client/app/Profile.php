<?php
/**
 * Load Model and Api
 */
include "autoload.php";

/**
 * Start of session and checking if user is logged in
 */
session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}

$user = $_SESSION['user'];


if(isset($_POST['submit']))
{
    header("Location: EditProfile.php");
    exit;
}


/**
 * Title of the page
 */
$title = "Nutzerprofil";
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
                        <div class="col-lg-3 col-md-3 col-sm-3">
                            <p>'.$user->getName().'</p>
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">E-Mail:</p>
                        <div class="col-lg-3 col-md-3 col-sm-3">
                            <p>'.$user->getEmail().'</p>
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Passwort:</p>
                        <div class="col-lg-3 col-md-3 col-sm-3">
                            <p>'.$user->getPassword().'</p>
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                        
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Rolle:</p>
                        <div class="col-lg-3 col-md-3 col-sm-3">
                            <p>'.$user->getRole()->getName().'</p>
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                        <div class="col-lg-3 col-md-3 col-sm-3 col-md-offset-1">
                            <button type="submit" name="submit" class="btn btn-info">Profil bearbeiten</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>';

include 'Template.php';
?>