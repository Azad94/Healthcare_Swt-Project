<?php
/**
 * Written by Kim Huber
 */
include "autoload.php";
session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}
$title="Start";

$link1= "'RequestBed.php'";
$link2= "'CreateTask.php'";
$link3= "'TaskTable.php'";
$link4= "'Profile.php'";
$scriptExpand = "'" . $_SESSION['user']->getRole()->getName() . "'";
$script ="'assets/js/emptyScript.js'";
/**
 * HTML code of the template content
 */
$content =
'
    <div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            <div class="container">
                <div class="row">
                    <div class="col-md-4">
                        <button onclick="location.href='.$link1.';" class="btn btn-primary btn-round btn-block">
                            <i style="font-size: 80px" class="fa fa-bed"></i>
                            <h3>Bett anfordern</h3>
                        </button>
                    </div>
                    <div class="col-md-offset-1 col-md-4">
                        <button onClick="location.href='.$link2.';" class="btn btn-primary btn-round btn-block">
                            <i style="font-size: 80px" class="fa fa-tasks"></i>
                            <h3>Auftrag erstellen</h3>
                        </button>
                    </div>
                </div>
                <div class="row" style="margin-top: 50px">
                    <div class="col-md-4">
                        <button onclick="location.href='.$link3.';" class="btn btn-primary btn-round btn-block">
                            <i style="font-size: 80px" class="fa fa-list"></i>
                            <h3>Aufträge anzeigen</h3>
                        </button>
                    </div>
                    <div class="col-md-offset-1 col-md-4">
                        <button onClick="location.href='.$link4.';" class="btn btn-primary btn-round btn-block">
                            <i style="font-size: 80px" class="fa fa-user"></i>
                            <h3>Profil</h3>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
 ';
$skriptExpand="''";

include 'Template.php';
?>