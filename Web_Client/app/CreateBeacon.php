<?php
/**
 * HTML base code by Kim Huber, adjusted by Radoslaw Speier
 * PHP code by Radoslaw Speier
 */
include "autoload.php";
session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}


$title = "Beacons erstellen";
$scriptExpand = "'Beacons'"; // needed for proper menu expansion after klick
$script ="assets/js/CreateBeacon.js"; //additional script content;


if(isset($_POST['submit']))
{

    $api_instance = new Swagger\Client\Api\BeaconApi;
    $body = new \Swagger\Client\Model\Beacon($_POST/*$array*/); // \Swagger\Client\Model\Location |

    try {
        $api_instance->beaconsPost($body);
        header("Location: ShowBeacons.php");
        exit;
    } catch (Exception $e) {
        echo 'Exception when calling LocationApi->locationsPost: ', $e->getMessage(), PHP_EOL;
    }

}


$content = '
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            <div class="container">
                <form class="card card-signup" method="POST">
                    <div class="row">
                        <h2 class="text-center">Beacon erstellen</h2>
                    </div>
                    
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Major/Rolle: </p>
                            <div class="col-lg-1 col-md-1 col-sm-1">
                                <input value=0 type="number" name="major" class="form-control" id="rolle">
                            </div>
                        <select class="btn btn-primary dropdown-toggle" id="majorselect">
                            <option value=0>Techniker</option>
                            <option value=1>Betten-Schieber</option>
                        </select>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Minor/Objekt: </p>
                            <div class="col-lg-1 col-md-1 col-sm-1">
                                <input value=0 type="number" name="minor" class="form-control" id="objekt">
                            </div>
                        <select class="btn btn-primary dropdown-toggle" id="minorselect">
                            <option value=0>Bett</option>
                            <option value=1>Tür</option>
                        </select>
                    </div>
                    <div class="row">
                    
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