<?php
/**
 * Written by Jan-Ole Petersen
 */

/**
 * Load Model and Api
 */
include "autoload.php";

/**
 * Start session so the session cookie is usable
 */
session_start();

/**
 * This class is for getting detailed information about a Task, updating a Task and closing a Task
 */
class TaskDetails
{
    /**
     * API instances for communication with the REST server
     */
    private $taskApi_instance;
    private $subTaskApi_instance;
    private $locationApi_instance;
    private $beaconObjectApi_instance;
    private $roleApi_instance;
    /**
     * id of the Task
     */
    private $taskId;
    /**
     * type of the Task
     */
    private $taskType;

    /**
     * TaskDetails constructor.
     * @param $id id of the Task
     * @param $taskType type of the Task
     */
    public function __construct($id, $taskType)
    {
        $this->taskType = $taskType;
        $this->taskId = $id;
        $this->subTaskApi_instance = new \Swagger\Client\Api\SubTaskApi();
        $this->locationApi_instance = new \Swagger\Client\Api\LocationApi();
        $this->beaconObjectApi_instance = new \Swagger\Client\Api\BeaconObjectApi();
        $this->roleApi_instance = new \Swagger\Client\Api\RoleApi();
        switch ($this->taskType)
        {
            case "transportTask":
                $this->taskApi_instance = new \Swagger\Client\Api\TransportTaskApi();
                break;
            case "maintainanceTask":
                $this->taskApi_instance = new \Swagger\Client\Api\MaintainanceTaskApi();
                break;
            case "cleaningTask":
                $this->taskApi_instance = new \Swagger\Client\Api\CleaningTaskApi();
                break;
        }
    }

    /**
     * Function for getting all information of a Task
     */
    public function getDetailInfo()
    {
        try
        {
            switch ($this->taskType)
            {
                case "transportTask":

                    echo $this->taskApi_instance->transporttasksTaskidGet($this->taskId);
                    break;

                case "maintainanceTask":

                    echo $this->taskApi_instance->maintainancetasksTaskidGet($this->taskId);
                    break;

                case "cleaningTask":

                    echo $this->taskApi_instance->cleaningtasksTaskidGet($this->taskId);
                    break;
            }
        }
        catch (Exception $e)
        {
            echo $e->getMessage();
        }

    }

    /**
     * Function for getting all information of an Task plus the editable information for this Task
     */
    public function getEditInfo()
    {
        try
        {
            $beaconObjects = $this->beaconObjectApi_instance->beaconObjectsGet();
            $roles = $this->roleApi_instance->rolesGet();
            switch ($this->taskType)
            {
                case "transportTask":

                    $task = $this->taskApi_instance->transporttasksTaskidGet($this->taskId);
                    $locations = $this->locationApi_instance->locationsGet();
                    $response['task'] = $task;
                    $response['locations'] = $locations;
                    $response['roles'] = $roles;
                    $response['beaconObjects'] = $beaconObjects;
                    echo json_encode(\Swagger\Client\ObjectSerializer::sanitizeForSerialization($response));
                    break;

                case "maintainanceTask":

                    $task = $this->taskApi_instance->maintainancetasksTaskidGet($this->taskId);
                    $beaconObjects = $this->beaconObjectApi_instance->beaconObjectsGet();
                    $response['task'] = $task;
                    $response['roles'] = $roles;
                    $response['beaconObjects'] = $beaconObjects;
                    echo json_encode(\Swagger\Client\ObjectSerializer::sanitizeForSerialization($response));
                    break;

                case "cleaningTask":

                    $task = $this->taskApi_instance->cleaningtasksTaskidGet($this->taskId);
                    $beaconObjects = $this->beaconObjectApi_instance->beaconObjectsGet();
                    $response['task'] = $task;
                    $response['roles'] = $roles;
                    $response['beaconObjects'] = $beaconObjects;
                    echo json_encode(\Swagger\Client\ObjectSerializer::sanitizeForSerialization($response));
                    break;
            }
        }
        catch (Exception $e)
        {
            echo $e->getMessage();
        }
    }

    /**
     * This function is for updating a Task
     * @param $description description to update
     * @param $level level to update
     * @param $beaconObjectId id of the beacon to update
     * @param $roleId id of the role to update
     * @param $targetLocationId id of the target location to update
     * @param $frequency frequency to update
     */
    public function update($description, $level, $beaconObjectId, $roleId, $targetLocationId, $frequency)
    {
        echo $frequency;
        try{

            if($roleId != '')
                $role = $this->roleApi_instance->rolesRoleIdGet($roleId);
            else
                $role = null;
            if($beaconObjectId != '')
                $beaconObject = $this->beaconObjectApi_instance->beaconObjectsBeaconObjectIdGet($beaconObjectId);
            else $beaconObject = null;


            switch ($this->taskType)
            {
                case "transportTask":
                    if($targetLocationId != '')
                        $location = $this->locationApi_instance->locationsLocationIdGet($targetLocationId);
                    else
                        $location = null;
                    $body = $this->taskApi_instance->transporttasksTaskidGet($this->taskId);
                    if($location != null) $body->setTargetLocation($location);
                    break;

                case "maintainanceTask":
                    $body = $this->taskApi_instance->maintainancetasksTaskidGet($this->taskId);
                    if($frequency != '') $body->setRepeatTaskInDay($frequency);
                    break;

                case "cleaningTask":
                    $body = $this->taskApi_instance->cleaningtasksTaskidGet($this->taskId);
                    break;
            }



            $body->setId($this->taskId);
            $body->setEditor($_SESSION['user']);
            if($description != '')$body->setDescription($description);
            if($level != '') $body->setLevel($level);
            if($role != null) $body->setRole($role);
            if($beaconObject != null) $body->setBeaconObject($beaconObject);
            if($frequency != '') $body->setRepeatTaskInDay($frequency);


            switch ($this->taskType)
            {
                case "transportTask":
                    $this->taskApi_instance->transporttasksTaskidPut($this->taskId, $body);
                    break;

                case "maintainanceTask":
                    $this->taskApi_instance->maintainancetasksTaskidPut($this->taskId, $body);
                    break;

                case "cleaningTask":
                    $this->taskApi_instance->cleaningtasksTaskidPut($this->taskId, $body);
                    break;
            }

        }
        catch (Exception $e)
        {
            echo 'Exception: ', $e->getMessage(), PHP_EOL;
        }

        $response = "success";
    }

    /**
     * This function closes a Task
     */
    function closeTask()
    {
        try
        {
            switch ($this->taskType)
            {
                case "transportTask":
                    $body = $this->taskApi_instance->transporttasksTaskidGet($this->taskId);
                    $body->setState(3);
                    $this->taskApi_instance->transporttasksTaskidPut($this->taskId, $body);
                    break;

                case "maintainanceTask":
                    $body = $this->taskApi_instance->maintainancetasksTaskidGet($this->taskId);
                    $body->setState(3);
                    echo $this->taskApi_instance->maintainancetasksTaskidPut($this->taskId, $body);
                    break;

                case "cleaningTask":
                    $body = $this->taskApi_instance->cleaningtasksTaskidGet($this->taskId);
                    $body->setState(3);
                    $this->taskApi_instance->cleaningtasksTaskidPut($this->taskId, $body);
                    break;
            }

        }
        catch (Exception $e)
        {
            $e->getMessage();
        }
    }
}

$action = new TaskDetails($_POST['id'], $_POST['type']);

/**
 * Which method to call
 */
switch ($_POST['method'])
{
    case "get":
        $action->getDetailInfo();
        break;
    case "getput":
        $action->getEditInfo();
        break;
    case "put":
        $action->update($_POST['description'], $_POST['level'], $_POST['beaconObjectId'], $_POST['roleId'], (isset($_POST['targetLocationId'])) ? $_POST['targetLocationId'] : '', (isset($_POST['frequency'])) ? $_POST['frequency'] : '');
        break;
    case "close":
        $action->closeTask();
        break;
}