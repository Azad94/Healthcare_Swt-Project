<?php
/**
 * HTML base code by Kim Huber, adjusted by Jan-Ole Petersen
 * PHP code by Jan-Ole Petersen
 *
 * This page is for configuring the filters for the task table
 */

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

/**
 * Get data from form
 */
if(isset($_POST['submit'])) {

    /**
     * Set defaults and store them in session cookie
     */
    $_SESSION['taskParams'] = [
        'open' => 0,
        'closed' => 0,
        'processing' => 0,
        'own' => 0,
        'maintenance' => 0,
        'transport' => 0,
        'cleaning' => 0
    ];

    /**
     * Set filter parameters
     */
    if(!empty($_POST['since'])) $since = $_POST['since']; else $since = 0;
    if(!empty($_POST['before'])) $before = $_POST['before']; else $before = 0;
    if(!empty($_POST['limit'])) $limit = $_POST['limit']; else $limit = 0;
    $_POST = array_fill_keys(array_keys($_POST), 1);
    $_SESSION['taskParams'] = array_replace($_SESSION['taskParams'], $_POST);
    $_SESSION['taskParams']['since'] = $since;
    $_SESSION['taskParams']['before'] = $before;
    $_SESSION['taskParams']['limit'] = $limit;
    header("Location: TaskTable.php");
    exit;
}

/**
 * Title of the page
 */
$title   = "Filter Aufträge";
/**
 * Which menu item to show
 */
$scriptExpand="'Aufträge'";
/**
 * Additional script for the page
 */
$script ="assets/js/emptyScript.js";

/**
 * HTML code of the template content
 */
$content = '<div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            <div class="container">
                <div class="card card-signup">
                    <div class="row">
                        <h2 class="col-md-offset-5">Filter wählen</h2>
                    </div>
                    <div class="row">
                        <h5 class="text-center">Welche Art von Aufträgen sollen angezeigt werden?</h5>
                    </div>
                    <form class="form" method="POST">
                        <div class="row">
                            <div class="col-lg-2 col-md-1 col-sm-1 col-md-offset-1">
                                <div class="checkbox">
                                    <label><input type="checkbox" value="" name="open">Offene Aufträge</label>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-2">
                                <div class="checkbox">
                                    <label><input type="checkbox" value="" name="closed">Geschlossene Aufträge</label>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-3">
                                <div class="checkbox">
                                    <label><input type="checkbox" value="" name="processing">Aufträge in Bearbeitung</label>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-1">
                                <div class="checkbox">
                                    <label><input type="checkbox" value="" name="own">Nur eigene Aufträge</label>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-2">
                                <div class="checkbox">
                                    <label><input type="checkbox" value="" name="maintenance">Wartungsaufträge</label>
                                </div>
                            </div>
                            <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-3">
                                <div class="checkbox">
                                    <label><input type="checkbox" value="" name="transport">Transportaufträge</label>
                                </div>
                            </div>
                            
                        </div>
                        <div class="row">
                            <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-1">
                                <div class="checkbox">
                                    <label><input type="checkbox" value="" name="cleaning">Reinigungsaufträge</label>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-3 col-md-2 col-sm-2 col-md-offset-2">
                                <input class="datepicker form-control" type="text" placeholder="Since" name="since" value=""/>

                                    <span class="add-on"><i class="icon-calendar"></i></span>
                            </div>
                            <div class="col-lg-3 col-md-2 col-sm-2 col-md-offset-2">
                                <input class="datepicker form-control" type="text" placeholder="Before" name="before" value=""/>

                                <span class="add-on"><i class="icon-calendar"></i></span>
                            </div>
                            <div class="col-lg-1 col-md-1 col-sm-1 col-md-offset-2">
                                <div class="form-group label-floating">
                                    <label class="control-label">Limit</label>
                                    <input type="number" class="form-control" name="limit">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-5">
                                <button class="btn btn-info" name="submit" type="submit">Aufträge anzeigen</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>';
        
        
 include 'Template.php';


?>