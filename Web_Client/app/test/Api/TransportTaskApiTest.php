<?php
/**
 * TransportTaskApiTest
 * PHP version 5
 *
 * @category Class
 * @package  Swagger\Client
 * @author   Swagger Codegen team & Radoslaw Speier
 * @link     https://github.com/swagger-api/swagger-codegen
 */

/**
 * Healthcare App
 *
 * Beschreibung der Rest-Schnitstelle der Healthcare API
 *
 * OpenAPI spec version: 1.0.0
 * 
 * Generated by: https://github.com/swagger-api/swagger-codegen.git
 *
 */

/**
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen
 * Please update the test case below to test the endpoint.
 */

namespace Swagger\Client;

use \Swagger\Client\Configuration;
use \Swagger\Client\ApiClient;
use \Swagger\Client\ApiException;
use \Swagger\Client\ObjectSerializer;
use DateTime;
use PHPUnit\Framework\TestCase;

include '../../autoload.php';
/**
 * TransportTaskApiTest Class Doc Comment
 *
 * @category Class
 * @package  Swagger\Client
 * @author   Swagger Codegen team
 * @link     https://github.com/swagger-api/swagger-codegen
 */
class TransportTaskApiTest extends TestCase
{

    /**
     * Setup before running any test cases
     */
    public static function setUpBeforeClass()
    {
        
        
        //creator editor role beaconobject anlegen
        
        /***Role***/
        $roleApiInstance = new \Swagger\Client\Api\RoleApi();
        $roleBody = new \Swagger\Client\Model\Role();
        $roleBody->setName("Verwalter");
        $role = $roleApiInstance->rolesPost($roleBody);
        /****USER*****/
        $userApiInstance = new \Swagger\Client\Api\UserApi();
        $userBody = new \Swagger\Client\Model\User();
        $userBody->setEmail("lol@lo.de");
        $userBody->setName("test");
        $userBody->setPassword("1234");
        $date = new DateTime();
        $userBody->setLastUpdate($date->format(DateTime::ATOM));
        //$userBody->setLastUpdate(null);
        $userBody->setRole($role);
        try{
            $user = $userApiInstance->usersPost($userBody);
            
        } catch(ApiException $e){
            echo $e->getTraceAsString();
        }
        /**** LOCATION ***/
        $body = new \Swagger\Client\Model\Location(); 
        $body->setBuilding(1);
        $body->setFloor(1);
        $body->setRoom(1);
        /*** BeaconObject ****/
        $body2 = new \Swagger\Client\Model\Beacon();
        $body2->setMajor(1);
        $body2->setMinor(0);
        $api_instanceLocation = new Api\LocationApi();
        $api_instanceBeacon = new Api\BeaconApi();
        
            list($location,$statusCode,$c) = $api_instanceLocation->locationsPostWithHttpInfo($body);
            list($beacon,$statusCode2,$c2) = $api_instanceBeacon->beaconsPostWithHttpInfo($body2);  
       
        $BObody = new \Swagger\Client\Model\BeaconObject();
        $beaObApi_instance = new \Swagger\Client\Api\BeaconObjectApi();
        
   
        $BObody->setLocation($location);
        $BObody->setBeacon($beacon);
        $BObody->setName("TestBetten");
        $BObody->setBeaconObjectType(\Swagger\Client\Model\BeaconObject::BEACON_OBJECT_TYPE_SENIORENBETT);
        $BObody->setPictureOfObject(null);
        $BObody->setState(1);
        
        list($result,$statusCode,$c) = $beaObApi_instance->beaconObjectsPostWithHttpInfo($BObody);
        
        $GLOBALS['user']=$user;
        $GLOBALS['location']=$location;
        $GLOBALS['beaconObject']=$result;
        
        /** Target Locaion **/
        $body = new \Swagger\Client\Model\Location(); 
        $body->setBuilding(1);
        $body->setFloor(2);
        $body->setRoom(3);
        list($targetLocation,$statusCode,$c) = $api_instanceLocation->locationsPostWithHttpInfo($body);
        $GLOBALS['targetLocation']=$targetLocation;
        $date = new DateTime();
        $GLOBALS['mockDate']=$date->format(DateTime::ATOM);
        /***Role Schieber***/
        $roleApiInstance = new \Swagger\Client\Api\RoleApi();
        $roleBody = new \Swagger\Client\Model\Role();
        $roleBody->setName("Schieber");
        $role = $roleApiInstance->rolesPost($roleBody);
        $GLOBALS['schieber']=$role;
        
    }

    /**
     * Setup before running each test case
     */
    public function setUp()
    {

    }

    /**
     * Clean up after running each test case
     */
    public function tearDown()
    {

    }

    /**
     * Clean up after running all test cases
     */
    public static function tearDownAfterClass()
    {

    }
    /**
     * Test case for transporttasksPost
     *
     * Erstelle einen neuen Task.
     *
     */
    public function testTransporttasksPost()
    {
        $transportTaskApi = new \Swagger\Client\Api\TransportTaskApi();
        
        $body = new \Swagger\Client\Model\TransportTask();
        $body->setCreator($GLOBALS['user']);
        $body->setBeaconObject($GLOBALS['beaconObject']);
        $body->setTargetLocation($GLOBALS['targetLocation']);
        $body->setDescription("Eine tolle Aufgabe");
        $body->setLevel(3);
        $body->setCreationTime($GLOBALS['mockDate']);
        $body->setState(2);
        $body->setRole($GLOBALS['schieber']);
        $body->setName("Task");
        $body->setType(\Swagger\Client\Model\TransportTask::TYPE_TRANSPORT_TASK);
        
        list($result,$statusCode,$c)=$transportTaskApi->transporttasksPostWithHttpInfo($body);
        $this->assertTrue($statusCode==200);
        
        return $result['id'];
    }
    
    /**
     * Test case for beaconObjectsBeaconObjectIdTransporttasksGet
     *
     * Erhalte alle Transporttasks die zu diesem beaconObject gehören.
     *@depends testTransporttasksPost
     */
    public function testBeaconObjectsBeaconObjectIdTransporttasksGet($id)
    {
         $transportTaskApi = new \Swagger\Client\Api\TransportTaskApi();
         list($result,$statusCode,$c)=$transportTaskApi->beaconObjectsBeaconObjectIdTransporttasksGetWithHttpInfo($GLOBALS['beaconObject']['id']);
         $this->assertTrue($statusCode==200);
         //print_r($result);
         $this->assertTrue($result[0]->getDescription()=="Eine tolle Aufgabe");
    }

    /**
     * Test case for locationsTransporttasksGet
     *
     * Liefert alle TransportTask zu einer örtlichen beschreibung.
     *
     */
    public function testLocationsTransporttasksGet()
    {
        $transportTaskApi = new \Swagger\Client\Api\TransportTaskApi();
        list($result,$statusCode,$c)=$transportTaskApi->locationsTransporttasksGetWithHttpInfo(1, 1, 1);
        
    }




    /**
     * Test case for transporttasksGet
     *
     * Erhalte die ersten 20 offenen tasks.
     *
     */
    public function testTransporttasksGet()
    {
        $transportTaskApi = new \Swagger\Client\Api\TransportTaskApi();
        list($result,$statusCode,$c)=$transportTaskApi->transporttasksGetWithHttpInfo($GLOBALS['user'], 100, null, null, null, null, null, null, null);
        $this->assertTrue($statusCode==200);
        $this->assertTrue(count($result)>=1);
        foreach($result as $task){
            $this->assertTrue($task->getType()==\Swagger\Client\Model\TransportTask::TYPE_TRANSPORT_TASK);
        }
        
        
    }

    



    /**
     * Test case for transporttasksTaskidDelete
     *
     * Löscht dieses Objekt.
     *
     */
    public function testTransporttasksTaskidDelete()
    {
      /*not part of api anymore*/  
      $this->assertTrue(true);
    }

    /**
     * Test case for transporttasksTaskidGet
     *
     * Erhalte einen einzelnen Task.
     * @depends testTransporttasksPost
     */
    public function testTransporttasksTaskidGet($id)
    {
         $transportTaskApi = new \Swagger\Client\Api\TransportTaskApi();
         $transportTask = $transportTaskApi->transporttasksTaskidGet($id);
         //$this->assertTrue($transportTask->getCreationTime()==$GLOBALS['mockDate']);
         $this->assertTrue($transportTask->getDescription()=="Eine tolle Aufgabe");
         $this->assertTrue($transportTask->getLevel()==3);
         $this->assertTrue($transportTask->getState()==2);
         $this->assertTrue($transportTask->getRole()->getName()=="Schieber");
         $this->assertTrue($transportTask->getDescription()=="Eine tolle Aufgabe");
         $this->assertTrue($transportTask->getName()=="Task");
         $this->assertTrue($transportTask->getCreator()->getName()=="test");
         $this->assertTrue($transportTask->getTargetLocation()->getBuilding()==1);
         $this->assertTrue($transportTask->getTargetLocation()->getFloor()==2);
         $this->assertTrue($transportTask->getTargetLocation()->getRoom()==3);
         $this->assertTrue($transportTask->getBeaconObject()->getName()=="TestBetten");
         
    }

    /**
     * Test case for transporttasksTaskidPut
     *
     * Update einen Task.
     *@depends testTransporttasksPost
     */
    public function testTransporttasksTaskidPut($id)
    {
       /** new Target Location **/
       $api_instanceLocation = new Api\LocationApi();
       $body = new \Swagger\Client\Model\Location(); 
       $body->setBuilding(4);
       $body->setFloor(5);
       $body->setRoom(6);
       list($targetLocation,$statusCode,$c) = $api_instanceLocation->locationsPostWithHttpInfo($body);
   
       $transportTaskApi = new \Swagger\Client\Api\TransportTaskApi();
       $transportTask = $transportTaskApi->transporttasksTaskidGet($id);
       $transportTask->setDescription("neuer Name");
       $transportTask->setTargetLocation($targetLocation);
       $transportTaskApi->transporttasksTaskidPut($id, $transportTask);
       
       $transportTaskAfterPut = $transportTaskApi->transporttasksTaskidGet($id);
       
       $this->assertTrue($transportTaskAfterPut->getDescription()=="neuer Name");
       $this->assertTrue($transportTaskAfterPut->getTargetLocation()->getBuilding()==4);
       $this->assertTrue($transportTaskAfterPut->getTargetLocation()->getFloor()==5);
       $this->assertTrue($transportTaskAfterPut->getTargetLocation()->getRoom()==6);
    }

}