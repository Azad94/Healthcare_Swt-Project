<?php
/*
 * HTML base code by Kim Huber, adjusted by Radoslaw Speier
 * PHP code by Radoslaw Speier
 */
include "autoload.php";

session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}
/* write a new prefernces entry with a new ip for rest service */
if(isset($_GET)){
    
    if(array_key_exists('host',$_GET)){
        file_put_contents("Preferences.json", json_encode($_GET,JSON_UNESCAPED_SLASHES));
        header("Location: index.php");
    }
}
$title = "Preferences";
$config = new Swagger\Client\Configuration();

$content = '<div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            <div class="container">
                <div class="card card-signup">
                    <div class="row">
                        <h2 class="col-md-offset-5">Einstellungen</h2>
                    </div>
                    <div class="row">
                        <h5 class="col-md-1 col-md-offset-1">Rest Server</h5>
                        <form>
                            <div class="form-group">
                                
                                <div class="col-lg-3 col-md-3 col-sm-3">
                                <label for="pwd">Host:</label>
                                    <input  type="text" name="host" class="form-control" value='.$config->getHost().'>
                                </div>
                                
                                <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-2">
                                <button type="submit" class="btn btn-info">Änderungen übernehmen</button>
                                </div>
                            </div>
                        </form>
                    </div>                  
                </div>
            </div>
        </div>
    </div>';
$script ="'assets/js/emptyScript.js'";
include 'Template.php';
?>