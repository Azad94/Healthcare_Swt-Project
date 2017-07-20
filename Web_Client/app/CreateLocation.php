<?php
/**
 * HTML base code by Kim Huber, adjusted by Jan-Ole Petersen
 * PHP code by Jan-Ole Petersen
 */
include "autoload.php";
session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}

if(isset($_POST['submit']))
{
    $api_instance = new Swagger\Client\Api\LocationApi();
    $body = new \Swagger\Client\Model\Location($_POST); // \Swagger\Client\Model\Location |

    try {
        $api_instance->locationsPost($body);
        header("Location: ShowLocations.php");
        exit;
    } catch (Exception $e) {
        echo 'Exception when calling LocationApi->locationsPost: ', $e->getMessage(), PHP_EOL;
    }

}


$title = "Ort erstellen";
$scriptExpand = "'Orte'";

$script ="assets/js/emptyScript.js";
$content='
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            <div class="container">
                <form class="card card-signup" method="POST">
                    <div class="row">
                        <h2 class="text-center">Location erstellen</h2>
                    </div>
                    
                    <div class="row">
                    <p class="col-md-1 col-md-offset-1">Gebäude:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <div class="form-group label-floating">
                                <label class="control-label">Gebäude</label>
                                <input type="number" class="form-control" name="building">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                    <p class="col-md-1 col-md-offset-1">Stockwerk:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <div class="form-group label-floating">
                                <label class="control-label">Stockwerk</label>
                                <input type="number" class="form-control" name="floor">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                    <p class="col-md-1 col-md-offset-1">Raum:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <div class="form-group label-floating">
                                <label class="control-label">Raum</label>
                                <input type="number" class="form-control" name="room">
                            </div>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-2">
                            <button type="submit" name="submit" class="btn btn-info">Abschicken</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>';


include 'Template.php';
?>