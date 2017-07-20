<?php
/**
 * HTML code by Kim Huber
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

/**
 * Function for populating drop down menu for locations
 * @param $locations locations to populate drop down
 * @return string HTML string of the drop down
 */
function populateLocationDropdown($locations)
{
    $result="";
    foreach ($locations as $loc)
    {
        $result .= '<li value="' . $loc->getId() . '" name="' . $loc->getBuilding() . '.' . $loc->getFloor() . '.' . $loc->getRoom() . '"><a href="#">' . $loc->getBuilding() . '.' . $loc->getFloor() . '.' . $loc->getRoom() . '</a></li>';
    }
    return $result;
}

/**
 * API instances
 */
$serviceApi_instance = new \Swagger\Client\Api\ServiceApi();
$locationApi_instance = new Swagger\Client\Api\LocationApi();
/**
 * Get all locations and sort them in ascending order
 */
try {
    $locations = $locationApi_instance->locationsGet();
    usort($locations, function($a, $b) {
        $ret = $a->getBuilding() - $b->getBuilding();
        if ($ret == 0) {
            $ret = $a->getFloor() - $b->getFloor();
            if ($ret == 0) {
                $ret = $a->getRoom() - $b->getRoom();
            }
        }
        return $ret;
    });

} catch (Exception $e) {
    echo $e->getMessage();
}

/**
 * Get data of the form
 */
if(isset($_POST['submit']))
{
    if(isset($_POST['bedInput']) && !empty($_POST['bedInput']))
    {
        $bed = $_POST['bedInput'];
    }
    else
    {
        $bed = "standard";
    }

    switch($bed)
    {
        case "standard":
            $callObjects = \Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_STANDARDBETT;
            break;
        case "senior":
            $callObjects = \Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_SENIORENBETT;
            break;
        case "child":
            $callObjects = \Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_KINDERBETT;
            break;
    }

    $quantity = $_POST['quantity'];
    $locationId = $_POST['locationInput'];
    $body = new \Swagger\Client\Model\Service();
    $body->setCallObjects($callObjects);
    $body->setQuantity($quantity);

    /**
     * Send request for a service
     */
    try{
        $location = $locationApi_instance->locationsLocationIdGet($locationId);
        $body->setTargetLocation($location);
        $task = $serviceApi_instance->serviceGet($body);
        header("Location: TaskTable.php");
    }
    catch (Exception $e)
    {
        echo $e->getMessage();
    }


}
/**
 * Title of the page
 */
$title = "Betten anfordern";
/**
 * Which menu item to show
 */
$scriptExpand = "'Service anfordern'";
/**
 * Additional script for the page
 */
$script ="'assets/js/RequestBed.js'";
/**
 * HTML code of the template content
 */
$content = '
    
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            <div class="container">
                <form class="card card-signup" method="POST">
                    <div class="row">
                        <h2 class="col-md-offset-5">Bett anfordern</h2>
                    </div>
                    <div class="row">
                        <h5 class="col-md-1 col-md-offset-1">Bett Art:</h5>
                        <ul class="nav nav-pills nav-pills-primary bed" role="tablist">
                            <li class="active" value="standard">
                                <a href="#dashboard" role="tab" data-toggle="tab">
                                    Standartbett
                                </a>
                            </li>
                            <li value="senior">
                                <a href="#schedule" role="tab" data-toggle="tab">
                                    Seniorenbett
                                </a>
                            </li>
                            <li value="child">
                                <a href="#tasks" role="tab" data-toggle="tab">
                                    Kinderbett
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="row">
                        <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-2">
                            <div class="form-group label-floating">
                                <label class="control-label">Anzahl</label>
                                <input value="" type="number" min="0" class="form-control" name="quantity">
                                <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                            </div>
                        </div>
                        <div class="col-md-3 col-md-offset-1 dropdown">
                            <a href="#" class="btn btn-simple dropdown-toggle" id="loc" data-toggle="dropdown">
                                Ziel
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu location">
                                '.populateLocationDropdown($locations).'
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        
                        <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-2">
                            <button type="submit" name="submit" class="btn btn-info">Anfordern</button>
                        </div>
                    </div>
                    <input type="hidden" name="bedInput">
                    <input type="hidden" name="locationInput">
                </form>
            </div>
        </div>
    </div>';


include 'Template.php';
?>