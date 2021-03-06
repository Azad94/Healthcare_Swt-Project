<?php
/**
 * SubTaskApiTest
 * PHP version 5
 *
 * @category Class
 * @package  Swagger\Client
 * @author   Swagger Codegen team
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
use PHPUnit\Framework\TestCase;
use \Datetime;
include '../../autoload.php';
/**
 * SubTaskApiTest Class Doc Comment
 *
 * @category Class
 * @package  Swagger\Client
 * @author   Swagger Codegen team
 * @link     https://github.com/swagger-api/swagger-codegen
 */
class SubTaskApiTest extends TestCase
{

    /**
     * Setup before running any test cases
     */
    public static function setUpBeforeClass()
    {
        
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
        /*** Beacon ****/
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
        
            list($beaconObject,$statusCode,$c) = $beaObApi_instance->beaconObjectsPostWithHttpInfo($BObody);
        
        /*$GLOBALS['userID']=$user['id'];
        $GLOBALS['locationID']=$location['id'];
        $GLOBALS['beaconObjectID']=$result['id'];
        
        
        */
        $maintenanceTaskApi_instance = new \Swagger\Client\Api\MaintainanceTaskApi();      
        /*$subTaskApi_instance = new \Swagger\Client\Api\SubTaskApi();
        $beaconObjectApi_instance = new \Swagger\Client\Api\BeaconObjectApi();
        $userApi_instance = new \Swagger\Client\Api\UserApi();*/

        $date = new DateTime();
      
        $body = new \Swagger\Client\Model\MaintainanceTask();
        
        $body->setCreator($user);
        $body->setDescription("SubTaskTest");
        $body->setLevel(1);
        $body->setCreationTime($date);
        $body->setState(1);
        $body->setBeaconObject($beaconObject);
        $body->setType(\Swagger\Client\Model\Task::TYPE_MAINTAINANCE_TASK);
        $body->setRole($user->getRole());
        $body->setName("ganz toll");
        $body->setPicture(null);
        $body->setSubTasks(null);
        $body->setRepeatTaskInDay(2);
        
        list($mTask,$statusCode,$c) = $maintenanceTaskApi_instance->maintainancetasksPostWithHttpInfo($body);
        

        $GLOBALS['id']= $mTask['id'];
                    
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
     * Test case for maintainancetasksMaintainancetaskIdSubtaskscheckboxPost
     *
     * Erstelle einen neuen SubTask.
     *
     */
    public function testMaintainancetasksMaintainancetaskIdSubtaskscheckboxPost()
    {
        $subTask_api = new \Swagger\Client\Api\SubTaskApi();
        $body = new \Swagger\Client\Model\SubTaskCheckbox();
        $body->setTitle("Sub 1");
        $body->setType(\Swagger\Client\Model\AbstractSubTask::TYPE_SUB_TASK_CHECKBOX);
        $body->setValue(true);
      
        list($result,$statusCode,$c)=$subTask_api->maintainancetasksMaintainancetaskIdSubtaskscheckboxPostWithHttpInfo($GLOBALS['id'], $body);
        $this->assertTrue($statusCode==200);
        return $result['id'];
                 
    }
    
    /**
     * Test case for maintainancetasksMaintainancetaskIdSubtaskscheckboxGet
     *
     * Erhalte alle subtasks zu einem MaintainanceTask.
     * @depends testMaintainancetasksMaintainancetaskIdSubtaskscheckboxPost
     */
    public function testMaintainancetasksMaintainancetaskIdSubtaskscheckboxGet($id)
    {
        $subTask_api = new \Swagger\Client\Api\SubTaskApi();
        list($result,$statusCode,$c)=$subTask_api->maintainancetasksMaintainancetaskIdSubtaskscheckboxGetWithHttpInfo($GLOBALS['id']);
        $this->assertTrue($statusCode==200);
        $this->assertTrue(count($result)>=1);
        $this->assertTrue($result[0]->getTitle()=="Sub 1");
        $this->assertTrue($result[0]->getType()==\Swagger\Client\Model\AbstractSubTask::TYPE_SUB_TASK_CHECKBOX);
        $this->assertTrue($result[0]->getValue()==true);
    }
     
    /**
     * Test case for maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdPost
     *
     * Subtask bearbeiten.
     *@depends testMaintainancetasksMaintainancetaskIdSubtaskscheckboxPost
     */
    public function testMaintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdPost($id)
    {
        $subTask_api = new \Swagger\Client\Api\SubTaskApi();
        $body = new \Swagger\Client\Model\SubTaskCheckbox();
        $body->setTitle("Sub 1.1");
        $body->setType(\Swagger\Client\Model\AbstractSubTask::TYPE_SUB_TASK_CHECKBOX);
        $body->setValue(false);
        try{
        list($result,$statusCode,$c) = $subTask_api->maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdPostWithHttpInfo($id, $GLOBALS['id'], $body);
            $this->assertTrue($statusCode==200);}
        catch(\Swagger\Client\ApiException $e){
            $this->assertTrue(false);
        }
    }
    
    /**
     * Test case for maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdDelete
     *
     * Lösche einen Subtask.
     *@depends testMaintainancetasksMaintainancetaskIdSubtaskscheckboxPost
     */
    public function testMaintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdDelete($id)
    {
        $subTask_api = new \Swagger\Client\Api\SubTaskApi();
        list($result,$statusCode,$c)=$subTask_api->maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdDeleteWithHttpInfo($id, $GLOBALS['id']);
        $this->assertTrue($statusCode==200);
        list($result,$statusCode,$c)=$subTask_api->maintainancetasksMaintainancetaskIdSubtaskscheckboxGetWithHttpInfo($GLOBALS['id']);
        $this->assertTrue($statusCode==200);
        foreach($result as $subTask){
            $this->assertTrue($result['id']!=$id);
        }
        
    }

    

   

    /**
     * Test case for maintainancetasksMaintainancetaskIdSubtaskssliderPost
     *
     * Erstelle einen neuen SubTask.
     *
     */
    public function testMaintainancetasksMaintainancetaskIdSubtaskssliderPost()
    {
        $subtask_api = new Api\SubTaskApi();
        $body = new Model\SubTaskSlider();
        $body->setSlider(3);
        $body->setTitle("Sub slider");
        $body->setType(\Swagger\Client\Model\AbstractSubTask::TYPE_SUB_TASK_SLIDER);
        list($result,$statusCode,$c)=$subtask_api->maintainancetasksMaintainancetaskIdSubtaskssliderPostWithHttpInfo($GLOBALS['id'], $body);
        $this->assertTrue($statusCode==200);
        //print_r($result);
        //$this->assertTrue($result->getSlider()==3);
        $this->assertTrue($result->getType()==\Swagger\Client\Model\AbstractSubTask::TYPE_SUB_TASK_SLIDER);
        $this->assertTrue($result->getTitle()=="Sub slider");
        return $result['id'];
        
    }
    
     /**
     * Test case for maintainancetasksMaintainancetaskIdSubtaskssliderGet
     *
     * Erhalte alle subtasks zu einem MaintainanceTask.
     * @depends testMaintainancetasksMaintainancetaskIdSubtaskssliderPost
     */
    public function testMaintainancetasksMaintainancetaskIdSubtaskssliderGet()
    {
        $subTask_api = new \Swagger\Client\Api\SubTaskApi();
        list($result,$statusCode,$c)=$subTask_api->maintainancetasksMaintainancetaskIdSubtaskssliderGetWithHttpInfo($GLOBALS['id']);
        $this->assertTrue($statusCode==200);
        $this->assertTrue(count($result)>=1);
        
        $this->assertTrue($result[0]->getTitle()=="Sub slider");
        $this->assertTrue($result[0]->getType()==\Swagger\Client\Model\AbstractSubTask::TYPE_SUB_TASK_SLIDER);
        //$this->assertTrue($result[0]->getSlider()==3);
    }

    
        /**
     * Test case for maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdPost
     *
     * Subtask bearbeiten.
     * @depends testMaintainancetasksMaintainancetaskIdSubtaskssliderPost
     *
     */
    public function testMaintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdPost($id)
    {
        $subTask_api = new \Swagger\Client\Api\SubTaskApi();
        list($subtask,$statusCode,$c)=$subTask_api->maintainancetasksMaintainancetaskIdSubtaskssliderGetWithHttpInfo($GLOBALS['id']);
        $this->assertTrue($statusCode==200);
        
        $subtask[0]->setTitle("edit");
        list($result,$statusCode,$c)=
                $subTask_api->maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdPost($id, $GLOBALS['id'],$subtask );
        $this->assertTrue($statusCode==200);
        //$this->assertTrue() 
    }

    /**
     * Test case for maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdDelete
     *
     * Lösche einen Subtask.
     * @depends testMaintainancetasksMaintainancetaskIdSubtaskssliderPost
     */
    public function testMaintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdDelete($id)
    {
        $subTask_api = new \Swagger\Client\Api\SubTaskApi();
        list($result,$statusCode,$c)=$subTask_api->maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdDeleteWithHttpInfo($id, $GLOBALS['id']);
        $this->assertTrue($statusCode==200);
        list($result,$statusCode,$c)=$subTask_api->maintainancetasksMaintainancetaskIdSubtaskssliderGetWithHttpInfo($GLOBALS['id']);
        $this->assertTrue($statusCode==200);
        foreach($result as $subTask){
            $this->assertTrue($result['id']!=$id);
        }
    }


}
