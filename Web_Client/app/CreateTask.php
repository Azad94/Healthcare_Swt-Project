<?php
/**
 * HTML code by Kim Huber
 * PHP code by Jan-Ole Petersen
 */

/**
 * Load Model and Api
 */
include 'autoload.php';

/**
 * Start session and check if user is logged in
 */
session_start();
if(!isset($_SESSION['user'])){
    header("location:Login.php");
    exit;
}

/**
 * User name for the request body
 */
$user_name = new \Swagger\Client\Model\UserName([
        "user_name" => $_SESSION['user']->getName(),
        "id" => $_SESSION['user']->getId()
]);

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
        $result .= '<li value="' . $loc->getId() . '"><a href="#">' . $loc->getBuilding() . '.' . $loc->getFloor() . '.' . $loc->getRoom() . '</a></li>';
    }
    return $result;
}

/**
 * Function for populating drop down menu for beacon objects
 * @param $beaconObjects beacon objects to populate drow down
 * @param $type type of the beacon object
 * @return string HTML string of the drop down
 */
function populateObjectDropdown($beaconObjects, $type)
{
    $result="";

    foreach ($beaconObjects as $obj)
    {
        foreach ($type as $k)
        {
            if($obj->getBeaconObjectType() == $k)
            {
                $result .= '<li value="' . $obj->getId() . '"><a href="#">' . $obj->getName() . '</a></li>';
            }
        }
    }
    return $result;
}

/**
 * Function for creating a Task body
 * @param $body instance of TransportTask, MaintenanceTask or CleaningTask
 * @param $name name of the Task
 * @param $description comment for the Task
 * @param $beaconObject beacon object of the Task
 * @param $role role, which the task is addressing
 * @param $type type of the Task
 * @param $repeatInDays frequency of a repeating MaintenanceTask
 * @param $targetLocation target location of a TransportTask
 * @return mixed body of the Task
 */
function createTaskBody($body, $name, $description, $beaconObject, $role, $type, $repeatInDays, $targetLocation)
{
    $body->setName($name);
    $body->setCreator($_SESSION['user']);
    $body->setDescription($description);
    $date = new DateTime("now");
    $body->setCreationTime($date->format(DateTime::ATOM));
    $body->setBeaconObject($beaconObject);
    if($targetLocation != null) $body->setTargetLocation($targetLocation);
    $body->setRole($role);
    $body->setType($type);
    $body->setState(1);
    if($repeatInDays != null) $body->setRepeatTaskInDay($repeatInDays);

    return $body;
}

/**
 * Function for creating a SubTask body
 * @param $sub instance of SubTaskCheckbox or SubTaskSlider
 * @param $type type of the SubTask
 * @param $title title of the SubTask
 * @return mixed body of the SubTask
 */
function createSubTask($sub, $type, $title)
{
    $sub->setTitle($title);
    $sub->setType($type);
    return $sub;
}

/**
 * Api instances for communication with the REST-Server
 */
$beaconObjectsApi_instance = new Swagger\Client\Api\BeaconObjectApi();
$locationApi_instance = new Swagger\Client\Api\LocationApi();
$beaconObjectApi_instance = new Swagger\Client\Api\BeaconObjectApi();

try {
    /**
     * Get all beacon objects
     */
    $beaconObjects = $beaconObjectsApi_instance->beaconObjectsGet();
} catch (Exception $e) {
    echo 'Exception when calling BeaconObjectApi->beaconObjectsGet: ', $e->getMessage(), PHP_EOL;
}


try {
    /**
     * Get all locations and sort them in ascending order
     */
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
    echo 'Exception when calling LocationApi->locationsGet: ', $e->getMessage(), PHP_EOL;
}


/**
 * Get data of the form
 */
if(isset($_POST['submit']))
{
    /**
     * Set default Task
     */
    if(isset($_POST['taskInput']) && !empty($_POST['taskInput']))
    {
        $task = $_POST['taskInput'];
    }
    else
    {
        $task = "trans";
    }

    /**
     * Get inputs
     */
    $name = $_POST['name'];
    $creator = $_SESSION['user'];
    echo $creator;
    $description = (isset($_POST['description']) && !empty($_POST['description'])) ? $_POST['description'] : "Kein Kommentar";
    $level = (isset($_POST['levelInput']) && !empty($_POST['levelInput'])) ? $_POST['levelInput'] : 1;
    $date = new DateTime();
    $creationTime = $date->format(DateTime::ATOM);



    if(isset($_POST['objectInput']) && !empty($_POST['objectInput']))
    {
        $beacon_object_id = $_POST['objectInput'];
    }
    elseif(isset($_POST['objectId']) && !empty($_POST['objectId']))
    {
        $beacon_object_id = $_POST['objectId'];
    }

    try
    {
        /**
         * Get selected beacon object
         */
        $beaconObject = $beaconObjectApi_instance->beaconObjectsBeaconObjectIdGet($beacon_object_id);
    }
    catch (Exception $e)
    {
        echo 'Exception when calling BeaconObjectApi->beaconObjectsBeaconObjectIdGet: ', $e->getMessage(), PHP_EOL;
    }


    /**
     * Switch operations for task type
     */
    switch ($task)
    {
        /**
         * Operations for TransportTask
         */
        case "trans":
            /**
             * Api instances for communication with the REST-Server
             */
            $transportTaskApi_instance = new Swagger\Client\Api\TransportTaskApi();
            $locationApi_instance = new Swagger\Client\Api\LocationApi();
            try
            {
                if(isset($_POST['locationInput']) && !empty($_POST['locationInput']))
                {
                    /**
                     * Get selected target location
                     */
                    $targetLocation = $locationApi_instance->locationsLocationIdGet($_POST['locationInput']);
                }
            }
            catch (Exception $e)
            {
                echo 'Exception when calling LocationApi->locationsLocationIdGet: ', $e->getMessage(), PHP_EOL;
            }

            /**
             * Create body for TransportTask
             */
            $body = createTaskBody(new \Swagger\Client\Model\TransportTask(), $name, $description, $beaconObject, $creator->getRole(), \Swagger\Client\Model\Task::TYPE_TRANSPORT_TASK, null, $targetLocation);
            try
            {
                /**
                 * Post TransportTask
                 */
                $result = $transportTaskApi_instance->transporttasksPost($body);
            }
            catch (Exception $e)
            {
                echo 'Exception when calling TransportTaskApi->transporttasksPost: ', $e->getMessage(), PHP_EOL;
            }
            break;

        /**
         * Operations for MaintenanceTask
         */
        case "main":
            /**
             * Count of SubTasks
             */
            $count = (int)$_POST['subtaskNumber'];
            /**
             * Set default MaintenanceTask
             */
            if(isset($_POST['mainKindInput']) && !empty($_POST['mainKindInput']))   //DEFAULTS
            {
                $task = $_POST['mainKindInput'];
            }
            else
            {
                $task = "bed";
            }

            /**
             * Switch operations for MaintenanceTask type
             */
            switch ($task)
            {
                /**
                 * Operations for MaintenanceTask with beds
                 */
                case "bed":

                    /**
                     * Get input of SubTasks
                     */
                    for($i = 0; $i <= $count; $i++)
                    {
                        if(isset($_POST["taskBedLabel$i"]))
                        {
                            $tasks['label'][$i] = $_POST["taskBedLabel$i"];
                            $tasks['type'][$i] = $_POST["optionsRadiosBed$i"];
                        }
                        elseif(isset($_POST["ownBedLabel$i"]))
                        {
                            $tasks['label'][$i] = $_POST["ownBedLabel$i"];
                            $tasks['type'][$i] = $_POST["optionsRadiosBed$i"];
                        }
                    }

                    /**
                     * Api instances for communication with the REST-Server
                     */
                    $maintenanceTaskApi_instance = new \Swagger\Client\Api\MaintainanceTaskApi();
                    $subTaskApi_instance = new \Swagger\Client\Api\SubTaskApi();

                    /**
                     * If standing Task, get frequency
                     */
                    if(isset($_POST['standing']))
                        $frequency = $_POST['freqInput'];
                    else
                        $frequency = null;

                    /**
                     * Create body for MaintenanceTask
                     */
                    $body = createTaskBody(new \Swagger\Client\Model\MaintainanceTask(), $name, $description, $beaconObject, $creator->getRole(), \Swagger\Client\Model\Task::TYPE_MAINTAINANCE_TASK, $frequency, null);

                    try{
                        /**
                         * Post MaintenanceTask
                         */
                        $result = $maintenanceTaskApi_instance->maintainancetasksPost($body);
                    }
                    catch (Exception $e)
                    {
                        echo $e->getMessage();
                    }
                    /**
                     * Create SubTasks of the MaintenanceTask
                     */
                    for($i = 0; $i <= $count; $i++)
                    {
                        if($tasks['type'][$i] == "slider")
                        {
                            $sub = createSubTask(new \Swagger\Client\Model\SubTaskSlider(), "SubTaskSlider", $tasks['label'][$i]);
                            try{
                                $subTaskApi_instance->maintainancetasksMaintainancetaskIdSubtaskssliderPost($result->getId(), $sub);
                            }
                            catch (Exception $e)
                            {
                                echo $e;
                            }
                        }
                        elseif ($tasks['type'][$i] == "checkBox")
                        {
                            $sub = createSubTask(new \Swagger\Client\Model\SubTaskCheckbox(), "SubTaskCheckbox", $tasks['label'][$i]);
                            try{
                                $subTaskApi_instance->maintainancetasksMaintainancetaskIdSubtaskscheckboxPost($result->getId(), $sub);
                            }
                            catch (Exception $e)
                            {
                                echo $e;
                            }
                        }
                    }

                    break;
                /**
                 * Operations for MaintenanceTask with doors
                 */
                case "door":

                    /**
                     * Get input of SubTasks
                     */
                    for($i = 0; $i <= $count; $i++)
                    {
                        if (isset($_POST["taskDoorLabel$i"])) {
                            $tasks['label'][$i] = $_POST["taskDoorLabel$i"];
                            $tasks['type'][$i] = $_POST["optionsRadiosDoor$i"];
                        } elseif (isset($_POST["ownBedLabel$i"])) {
                            echo "own";
                            $tasks['label'][$i] = $_POST["ownDoorLabel$i"];
                            $tasks['type'][$i] = $_POST["optionsRadiosDoor$i"];
                        }
                    }

                    /**
                     * Api instances for communication with the REST-Server
                     */
                    $maintenanceTaskApi_instance = new \Swagger\Client\Api\MaintainanceTaskApi();
                    $subTaskApi_instance = new \Swagger\Client\Api\SubTaskApi();

                    /**
                     * If standing Task, get frequency
                     */
                    if(isset($_POST['standing']))
                        $frequency = $_POST['freqInput'];
                    else
                        $frequency = null;

                    /**
                     * Create body for MaintenanceTask
                     */
                    $body = createTaskBody(new \Swagger\Client\Model\MaintainanceTask(), $name, $description, $beaconObject, $creator->getRole(), \Swagger\Client\Model\Task::TYPE_MAINTAINANCE_TASK, $frequency, null);

                    try{
                        /**
                         * Post MaintenanceTask
                         */
                        $result = $maintenanceTaskApi_instance->maintainancetasksPost($body);

                    }
                    catch (\Swagger\Client\ApiExceptionException $e)
                    {
                        echo $e->getMessage();
                    }
                    /**
                     * Create SubTasks of the MaintenanceTask
                     */
                    for($i = 0; $i <= $count; $i++)
                    {
                        if($tasks['type'][$i] == "slider")
                        {
                            $sub = createSubTask(new \Swagger\Client\Model\SubTaskSlider(), "SubTaskSlider", $tasks['label'][$i]);
                            try{
                                $subTaskApi_instance->maintainancetasksMaintainancetaskIdSubtaskssliderPost($result->getId(), $sub);
                            }
                            catch (Exception $e)
                            {
                                echo $e;
                            }
                        }
                        elseif ($tasks['type'][$i] == "checkBox")
                        {
                            $sub = createSubTask(new \Swagger\Client\Model\SubTaskSlider(), "SubTaskCheckbox", $tasks['label'][$i]);
                            try{
                                $subTaskApi_instance->maintainancetasksMaintainancetaskIdSubtaskscheckboxPost($result->getId(), $sub);
                            }
                            catch (\Swagger\Client\ApiExceptionException $e)
                            {
                                echo $e->getMessage();
                            }
                        }
                    }


                    break;
            }
            break;
        /**
         * Operations for CleaningTask
         */
        case "clean":
            /**
             * Api instance for communication with the REST server
             */
            $cleaningTaskApi_instance = new \Swagger\Client\Api\CleaningTaskApi();

            /**
             * Create body for CleaningTask
             */
            $body = createTaskBody(new \Swagger\Client\Model\CleaningTask(), $name, $description, $beaconObject, $creator->getRole(), \Swagger\Client\Model\Task::TYPE_CLEANING_TASK, null, null);
            try
            {
                /**
                 * Post CleaningTask
                 */
                $result = $cleaningTaskApi_instance->cleaningtasksPost($body);
            }
            catch (Exception $e)
            {
                $e->getMessage();
            }
            break;
    }
    /**
     * If post successful, redirect to TaskTable
     */
    if(isset($result) || $result != null)
    {
        sleep(1);
        header("Location: TaskTable.php");
        exit;
    }

}


/**
 * Title of the page
 */
$title = "Auftrag aufgeben";
/**
 * Additional script for the page
 */
$script = "'assets/js/CreateTask.js'";
/**
 * Which menu item to show
 */
$scriptExpand="'Aufträge'";
/**
 * HTML code of the template content
 */
$content = '<div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div id="pushobj">
            <div class="container">
                <form class="card card-signup" method="POST">
                    <div class="row">
                        <h2 class="col-md-offset-5">Auftrag aufgeben</h2>
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
                    <h5 class="col-md-1 col-md-offset-1">Auftragsart:       </h5>
                    <ul class="nav nav-pills nav-pills-primary col-md-9 col-md-offset-1 task" role="tablist">
                        <li class="active" value="trans">
                            <a href="#transport" role="tab" data-toggle="tab">
                                Transport
                            </a>
                        </li>
                        <li value="main">
                            <a href="#wartung" role="tab" data-toggle="tab">
                                Wartung
                            </a>
                        </li>
                        <li value="clean">
                            <a href="#reinigung" role="tab" data-toggle="tab">
                                Reinigung
                            </a>
                        </li>
                    </ul>

                    <div class="tab-content">
                        <div id="transport" class="tab-pane fade in active">
                            <div class="row"></div>
                            <div class="row">
                                <div class="col-md-3 col-md-offset-3 dropdown">
                                            <a href="#" class="btn btn-simple dropdown-toggle" data-toggle="dropdown">
                                                Objekt
                                                <b class="caret"></b>
                                            </a>
                                            <ul class="dropdown-menu object">
                                                '.populateObjectDropdown($beaconObjects, [\Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_STANDARDBETT, \Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_SENIORENBETT, \Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_KINDERBETT]).'
                                            </ul>
                                        </div>
                                <div class="col-md-3 col-md-offset-1 dropdown">
                                    <a href="#" class="btn btn-simple dropdown-toggle" data-toggle="dropdown">
                                        Ziel
                                        <b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu location">
                                        '.populateLocationDropdown($locations).'
                                    </ul>
                                </div>
                                
                            </div>

                            <div class="row">
                                <div class="col-md-3 col-md-offset-1 dropdown">
                                    <a href="#" class="btn btn-simple dropdown-toggle" data-toggle="dropdown">
                                        Priorität
                                        <b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu level">
                                        <li value="1"><a href="#">1</a></li>
                                        <li value="2"><a href="#">2</a></li>
                                        <li value="3"><a href="#">3</a></li>
                                    </ul>
                                </div>
                                
                            </div>
                        </div>
                        <div id="wartung" class="tab-pane fade">
                            <div class="row"></div>
                            <h5 class="col-md-1 col-md-offset-1">Wartungsart:       </h5>
                            <ul class="nav nav-pills nav-pills-primary col-md-9 col-md-offset-1 mainKind" role="tablist">
                                <li class="active" value="bed">
                                    <a href="#bett" role="tab" data-toggle="tab">
                                        Bett
                                    </a>
                                </li>
                                <li value="door">
                                    <a href="#brandtuer" role="tab" data-toggle="tab">
                                        Brandschutztür
                                    </a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div id="bett" class="tab-pane fade in active">                                 <!-- BETT -->
                                    <div class="row">
                                        <div class="col-md-3 col-md-offset-1 dropdown">
                                            <a href="#" class="btn btn-simple dropdown-toggle" data-toggle="dropdown">
                                                Objekt
                                                <b class="caret"></b>
                                            </a>
                                            <ul class="dropdown-menu object">
                                                '.populateObjectDropdown($beaconObjects, [\Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_STANDARDBETT, \Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_SENIORENBETT, \Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_KINDERBETT]).'
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-8 col-md-offset-2">
                                            <table class="table table-sm table-bordered table-hover" id="wartungTableBed">
                                                <thead>
                                                <tr>
                                                    <th>Beschriftung</th>
                                                    <th>Art</th>
                                                </tr>
                                                </thead>

                                                <tbody>
                                                <tr id="bedAddr0">
                                                    <td>
                                                        <div id="taskBed0" class="btn-group">
                                                            <a type="button" class="btn btn-default">Beschriftung</a>
                                                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                                                <span class="caret"></span>
                                                                <span class="sr-only">Toggle Dropdown</span>
                                                            </button>
                                                            <ul class="dropdown-menu" role="menu">                            <!-- HIER -->
                                                                <li value="Hebemotor funktioniert"><a>Hebemotor funktioniert</a></li>
                                                                <li value="Räder funktionieren"><a>Räder funktionieren</a></li>
                                                                <li>
                                                                    <div class="form-group">
                                                                        <input name="ownBedLabel0" type="text" placeholder="Eigene Beschriftung" class="form-control" />
                                                                    </div>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                        <input type="hidden" name="taskBedLabel0">
                                                    </td>
                                                    <td>
                                                        <fieldset class="form-group">
                                                            <div class="form-check">
                                                                <label class="form-check-label">
                                                                    <input type="radio" class="form-check-input" name="optionsRadiosBed0" value="checkBox">
                                                                    Checkbox
                                                                </label>
                                                            </div>
                                                            <div class="form-check">
                                                                <label class="form-check-label">
                                                                    <input type="radio" class="form-check-input" name="optionsRadiosBed0" value="slider">
                                                                    Slider
                                                                </label>
                                                            </div>
                                                        </fieldset>
                                                    </td>
                                                </tr>
                                                <tr id="bedAddr1"></tr>
                                                </tbody>
                                            </table>
                                            <a id="addRowBed" class="btn btn-default btn-just-icon pull-left"><i class="fa fa-plus"></i></a>
                                        </div>
                                    </div>
                                </div>
                                <div id="brandtuer" class="tab-pane fade in">
                                    <div class="row">
                                        <div class="col-md-3 col-md-offset-3 dropdown">
                                            <a href="#" class="btn btn-simple dropdown-toggle" data-toggle="dropdown">
                                                Objekt
                                                <b class="caret"></b>
                                            </a>
                                            <ul class="dropdown-menu object">
                                                '.populateObjectDropdown($beaconObjects, [\Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_BRANDSCHUTZTR]).'
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-8 col-md-offset-2">
                                                    <table class="table table-sm table-bordered table-hover" id="wartungTableDoor">
                                                        <thead>
                                                            <tr>
                                                                <th>Beschriftung</th>
                                                                <th>Art</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                        <tr id="doorAddr0">
                                                            <td>
                                                                <div id="taskDoor0" class="btn-group">
                                                                    <a type="button" class="btn btn-default">Beschriftung</a>
                                                                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                                                        <span class="caret"></span>
                                                                        <span class="sr-only">Toggle Dropdown</span>
                                                                    </button>
                                                                    <ul class="dropdown-menu" role="menu">
                                                                        <li value="Magnet funktioniert"><a>Magnet funktioniert</a></li>
                                                                        <li value="Scharniere"><a>Scharniere</a></li>
                                                                        <li value="Temperatur"><a>Temperatur</a></li>
                                                                        <li>
                                                                            <div class="form-group">
                                                                                <input type="text" name="ownDoorLabel0" placeholder="Eigene Beschriftung" class="form-control" />
                                                                            </div>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                                <input type="hidden" name="taskDoorLabel0">
                                                            </td>
                                                            <td>
                                                                <fieldset class="form-group">
                                                                    <div class="form-check">
                                                                        <label class="form-check-label">
                                                                            <input type="radio" class="form-check-input" name="optionsRadiosDoor0" value="checkBox">
                                                                            Checkbox
                                                                        </label>
                                                                    </div>
                                                                    <div class="form-check">
                                                                        <label class="form-check-label">
                                                                            <input type="radio" class="form-check-input" name="optionsRadiosDoor0" value="slider">
                                                                            Slider
                                                                        </label>
                                                                    </div>
                                                                </fieldset>
                                                            </td>
                                                        </tr>
                                                        <tr id="doorAddr1"></tr>
                                                        </tbody>
                                                    </table>
                                            <a id="addRowDoor" class="btn btn-default btn-just-icon pull-left"><i class="fa fa-plus"></i></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3 col-md-offset-1 dropdown">
                                    <a href="#" class="btn btn-simple dropdown-toggle" data-toggle="dropdown">
                                        Priorität
                                        <b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li value="1"><a href="#">1</a></li>
                                        <li value="2"><a href="#">2</a></li>
                                        <li value="3"><a href="#">3</a></li>
                                    </ul>
                                </div>
                                <div class="togglebutton col-md-1 col-md-offset-1">
                                    <label>
                                        <input id="toggleButton" type="checkbox" data-toggle="toggle" name="standing">
                                        Dauerauftrag
                                    </label>
                                </div>
                                <div id="dauerauftragZyklus" class="col-md-3 col-md-offset-1 dropdown" style="display: none">
                                    <a href="#" class="btn btn-simple dropdown-toggle" data-toggle="dropdown">
                                        Zyklus
                                        <b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu frequency">
                                        <li value="7"><a href="#">1 Woche</a></li>
                                        <li value="14"><a href="#">2 Wochen</a></li>
                                        <li value="30"><a href="#">1 Monat</a></li>
                                        <li value="60"><a href="#">2 Monate</a></li>
                                        <li value="180"><a href="#">6 Monate</a></li>
                                        <li value="365"><a href="#">1 Jahr</a></li>
                                        <li value="730"><a href="#">2 Jahre</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div id="reinigung" class="tab-pane fade">
                            <div class="row"></div>
                            <div class="row">
                                <div class="col-md-3 col-md-offset-1 dropdown">
                                    <a href="#" class="btn btn-simple dropdown-toggle" data-toggle="dropdown">
                                        Objekt
                                        <b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu object">
                                        '.populateObjectDropdown($beaconObjects, [\Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_STANDARDBETT, \Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_SENIORENBETT, \Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_KINDERBETT]).'
                                    </ul>
                                </div>
                               
                            </div>
                            <div class="row">
                                <div class="col-md-3 col-md-offset-1 dropdown">
                                    <a href="#" class="btn btn-simple dropdown-toggle" data-toggle="dropdown">
                                        Priorität
                                        <b class="caret"></b>
                                    </a>
                                    <ul class="dropdown-menu level">
                                        <li value="1"><a href="#">1</a></li>
                                        <li value="2"><a href="#">2</a></li>
                                        <li value="3"><a href="#">3</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 col-md-offset-1">
                                <textarea name="description" class="form-control" placeholder="Kommentar" rows="5"></textarea>
                            </div>
                            <div class="col-lg-2 col-md-2 col-sm-2 col-md-offset-2">
                                <button type="submit" name="submit" class="btn btn-info">Abschicken</button>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="taskInput">
                    <input type="hidden" name="objectInput">
                    <input type="hidden" name="locationInput">
                    <input type="hidden" name="levelInput">
                    <input type="hidden" name="mainKindInput">
                    <input type="hidden" name="subtaskNumber">
                    <input type="hidden" name="freqInput">
                    
                </form>
            </div>
        </div>
    </div>
'

    ;



include 'Template.php';

?>