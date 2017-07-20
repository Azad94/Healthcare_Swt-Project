<?php
/**
 * Written by Jan-Ole Petersen
 */
include "autoload.php";

/**
 * This class is for getting detailed information about a beacon object, updating a beacon object and deleting a beacon object
 */
class BeaconObjectDetails
{
    /**
     * API instances for communication with the REST server
     */
    private $beaconObjektApi_instance;
    private $beaconsApi_instance;
    private $locationsApi_instance;
    /**
     * id of the beacon object
     */
    private $beacon_object_id;

    /**
     * BeaconObjectDetails constructor.
     * @param $id id of the beacon object
     */
    public function __construct($id)
    {
        $this->beaconObjektApi_instance = new Swagger\Client\Api\BeaconObjectApi();
        $this->beaconsApi_instance = new \Swagger\Client\Api\BeaconApi();
        $this->locationsApi_instance = new \Swagger\Client\Api\LocationApi();
        $this->beacon_object_id = $id;
    }

    /**
     * Function for getting all information of a beacon object
     */
    public function getDetailInfo()
    {
        try {
            $response = $this->beaconObjektApi_instance->beaconObjectsBeaconObjectIdGet($this->beacon_object_id);
        } catch (Exception $e) {
            echo 'Exception: ', $e->getMessage(), PHP_EOL;
        }
        echo $response;
    }

    /**
     * Function for getting all information of a beacon object plus the editable information for this beacon object
     * @param $no_beacon_object should the beacon be part of a beacon object or should it be unused
     */
    public function getEditInfo($no_beacon_object)
    {
        try {
            $beaconObject = $this->beaconObjektApi_instance->beaconObjectsBeaconObjectIdGet($this->beacon_object_id);
            $locations = $this->locationsApi_instance->locationsGet();
            $beacons = $this->beaconsApi_instance->beaconsGet($no_beacon_object);
        } catch (Exception $e) {
            echo 'Exception when calling BeaconObjectApi->beaconObjectsBeaconObjectIdGet: ', $e->getMessage(), PHP_EOL;
        }
        $result['beaconObject'] = $beaconObject;
        $result['beacons'] = $beacons;
        $result['locations'] = $locations;
        echo json_encode(\Swagger\Client\ObjectSerializer::sanitizeForSerialization($result));
    }

    /**
     * This function is for updating a beacon object
     * @param $name name to update
     * @param $beaconId id of the beacon to update
     * @param $locationId id of the location to update
     */
    public function update($name, $beaconId, $locationId)
    {
        try{
            if(!empty($beaconId))
                $beacon = $this->beaconsApi_instance->beaconsBeaconIdGet($beaconId);
            else
                $beacon = null;

            if(!empty($locationId))
                $location = $this->locationsApi_instance->locationsLocationIdGet($locationId);
            else
                $location = null;

            $body = new \Swagger\Client\Model\BeaconObject();
            $body->setId($this->beacon_object_id);
            $body->setName($name);
            if($beacon != null) $body->setBeacon($beacon);
            if($location != null) $body->setLocation($location);

            $this->beaconObjektApi_instance->beaconObjectsBeaconObjectIdPut($this->beacon_object_id, $body);

        }
        catch (Exception $e)
        {
            echo 'Exception: ', $e->getMessage(), PHP_EOL;
        }

        echo "Updated";
    }

    /**
     * This function deletes a beacon object
     */
    public function delete()
    {
        try {
            $this->beaconObjektApi_instance->beaconObjectsBeaconObjectIdDelete($this->beacon_object_id);
        } catch (Exception $e) {
            echo 'Exception when calling BeaconObjectApi->beaconObjectsBeaconObjectIdDelete: ', $e->getMessage(), PHP_EOL;
        }

    }
}

$action = new BeaconObjectDetails($_POST['id']);

/**
 * Which method to call
 */
switch ($_POST['method'])
{
    case "get":
        $action->getDetailInfo();
        break;
    case "getput":
        $action->getEditInfo(true);
        break;
    case "put":
        $action->update($_POST['name'], $_POST['beaconUuid'], $_POST['locationId']);
        break;
    case "delete":
        $action->delete();
        break;
}