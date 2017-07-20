<?php
/**
 * HTML base code by Kim Huber, adjusted by Jan-Ole Petersen
 * PHP code by Jan-Ole Petersen
 *
 * This page is for creating a new beacon object
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
 * Api instances for communication with the REST-Server
 */
$locApi_instance = new Swagger\Client\Api\LocationApi();
$beaApi_instance = new Swagger\Client\Api\BeaconApi();

try
{
    /**
     * Get all locations
     */
    $locations = $locApi_instance->locationsGet();

    /**
     * Sorting the locations by increasing building/floor/room number
     */
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
    // Get all beacons that are not part of a beacon object
    $no_beacon_object = true;
    $beacons = $beaApi_instance->beaconsGet($no_beacon_object);

}
catch (Exception $e)
{
    echo 'Exception: ', $e->getMessage(), PHP_EOL;
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
 * Function for populating drop down menu for beacons
 * @param $beacons beacons to populate drop down
 * @return string HTML string of the drop down
 */
function populateBeaconDropdown($beacons)
{
    $result="";
    foreach ($beacons as $beacon)
    {
        $result .= '<li value="' . $beacon->getUuid() . '" name="' . $beacon->getUuid() . '"><a href="#">' . $beacon->getUuid() . '</a></li>';
    }
    return $result;
}

/**
 * Get content of form
 */
if(isset($_POST['submit']))
{

    if(isset($_POST['name']) && !empty($_POST['name']) && isset($_POST['beaconInput']) && !empty($_POST['beaconInput']) && isset($_POST['locationInput']) && !empty($_POST['locationInput']) && isset($_POST['typeInput']) && !empty($_POST['typeInput']))
    {
        /**
         * Api instances for communication with the REST-Server
         */
        $beaObApi_instance = new Swagger\Client\Api\BeaconObjectApi();
        $locApi_instance = new Swagger\Client\Api\LocationApi();
        $beaApi_instance = new Swagger\Client\Api\BeaconApi();

        /**
         * Get data from inputs
         */
        $beacon_id = $_POST['beaconInput'];
        $location_id = $_POST['locationInput'];
        $type = $_POST['typeInput'];

        try
        {
            /**
             * Get the location and beacon for the new beacon object
             */
            $beacon = null;
            $location = null;
            $beacon = $beaApi_instance->beaconsBeaconIdGet($beacon_id);
            $location = $locApi_instance->locationsLocationIdGet($location_id);
        }
        catch (Exception $e)
        {
            echo 'Exception: ', $e->getMessage(), PHP_EOL;
        }

        /**
         * Set body for the new beacon object
         */
        if($beacon != null && $location != null)
        {
            $body = new \Swagger\Client\Model\BeaconObject();
            $body->setName($_POST['name']);
            $body->setBeacon($beacon);
            $body->setLocation($location);
            $body->setBeaconObjectType($type);

            /**
             * If a picture of the object is uploaded, add it
             */
            if(isset($_FILES['picture']['tmp_name']) && !empty($_FILES['picture']['tmp_name']))
            {
                $imageData = file_get_contents($_FILES['picture']['tmp_name']);
                $picture = new \Swagger\Client\Model\PictureOfObject();
                $picture->setPicture(base64_encode($imageData));
                $body->setPictureOfObject($picture);
            }

            try
            {
                /**
                 * Post new object and redirect to table of beacon objects
                 */
                $result = $beaObApi_instance->beaconObjectsPost($body);
                if(isset($result))
                    header("Location: ShowObjects.php");
            } catch (Exception $e) {
                echo 'Exception when calling BeaconObjectApi->beaconObjectsPost: ', $e->getMessage(), PHP_EOL;
            }
        }
        else
        {
            //reload
        }

    }
    else
    {
        //reload
    }
}

/**
 * Title of page
 */
$title = "Objekt erstellen";
/**
 * Which menu item to show
 */
$scriptExpand = "'Objekte'";
/**
 * Additional script for the page
 */
$script ="'assets/js/CreateObject.js'";
/**
 * HTML code of the template content
 */
$content = '
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            <div class="container">
                <form class="card card-signup" method="POST" enctype="multipart/form-data">
                    <div class="row">
                        <h2 class="text-center">Objekt anlegen</h2>
                    </div>
                    
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Name:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                            <input value="" placeholder="Name" type="text" class="form-control" name="name">
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Typ:</p>
                        <div class="col-md-3 col-md-offset-1 dropdown">
                            <a href="#" class="btn btn-simple dropdown-toggle" id="typ" data-toggle="dropdown">
                                Typ
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu type">
                                <li value="Standardbett"><a href="#">Standardbett</a></li>
                                <li value="Seniorenbett"><a href="#">Seniorenbett</a></li>
                                <li value="Kinderbett"><a href="#">Kinderbett</a></li>
                                <li value="Brandschutztür"><a href="#">Brandschutztür</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Bild:</p>
                        <div class="col-lg-2 col-md-2 col-sm-2">
                        
                            <input class="btn btn-info" value="" type="file" name="picture">
                            <span class="form-control-feedback">
			                    <i class="fa fa-check"></i>
		                    </span>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Beacon:</p>
                        <div class="col-md-3 col-md-offset-1 dropdown">
                            <a href="#" class="btn btn-simple dropdown-toggle" id="beac" data-toggle="dropdown">
                                Beacon
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu beacon">
                                '.populateBeaconDropdown($beacons).'
                            </ul>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-2">
                            <button type="submit" name="submit" class="btn btn-info">Objekt anlegen</button>
                        </div>
                    </div>
                    <div class="row">
                        <p class="col-md-1 col-md-offset-1">Ort:</p>
                        <div class="col-md-3 col-md-offset-1 dropdown">
                            <a href="#" class="btn btn-simple dropdown-toggle" id="loc" data-toggle="dropdown">
                                Ort
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu location">
                                '.populateLocationDropdown($locations).'
                            </ul>
                        </div>
                    </div>
                    <input type="hidden" name="beaconInput">
                    <input type="hidden" name="locationInput">
                    <input type="hidden" name="typeInput">
                    
                </form>
            </div>
        </div>
    </div>';


include 'Template.php';
?>
