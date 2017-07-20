<?php
/**
 * BeaconApiTest
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
use Swagger\Client\Api\BeaconApi;
use PHPUnit\Framework\TestCase;

include '../../autoload.php';
/**
 * BeaconApiTest Class Doc Comment
 *
 * @category Class
 * @package  Swagger\Client
 * @author   Swagger Codegen team
 * @link     https://github.com/swagger-api/swagger-codegen
 */
class BeaconApiTest extends TestCase
{

    /**
     * Setup before running any test cases
     */
    public static function setUpBeforeClass()
    {

    }

    /**
     * Setup before running each test case
     */
    public function setUp()
    {
    
    $body = new \Swagger\Client\Model\Beacon(); 
    $body->setMajor(1);
    $body->setMinor(0);
    return $body;
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
     * Test case for beaconsPost
     *
     * Erstelle neuen Beacon als rückgabe gibt es den fertigen beacon mit uuid.
     *
     */
    public function testBeaconsPost()
    {
         $body = $this->setUp();
         $api_instance = new BeaconApi();
         try {$return = $api_instance->beaconsPost($body);
              
              $this->assertTrue($return['major']==1);
              $this->assertTrue($return['minor']==0);
              return $return['uuid'];
         }
         catch (\Swagger\Client\ApiException $e){
            //print $e->getCode();
         }
         
    }
    
    /**
     * Test case for beaconsGet
     *
     * Erhalte alle Beacons.
     *@depends testBeaconsPost
     */
    public function testBeaconsBeaconIdGet($uuid)
    {    
        $api_instance = new BeaconApi;
        try{ $result = $api_instance->beaconsBeaconIdGet($uuid);
            $this->assertTrue($result['major']==1);
            $this->assertTrue($result['minor']==0);
            
            return $uuid;
        } catch (\Swagger\Client\ApiException $ex) {
             //print $ex->getCode();
            
        }
        return $uuid;
    }

        /**
     * Test case for beaconsBeaconIdPut
     *
     * Einen Beacon bearbeiten.
     * @depends testBeaconsBeaconIdGet
     */
    public function testBeaconsBeaconIdPut($uuid)
    {
        $api_instance = new BeaconApi;
        $array = array('major'=> 0,'minor'=>3);
        $body = new Model\Beacon($array);
        try{ 
            list($a,$b,$c) = $api_instance->beaconsBeaconIdPutWithHttpInfo($uuid, $body);
            $this->assertTrue($b==200);
        } catch(\Swagger\Client\ApiException $ex){
            
        }
         return $uuid;     
    }
    
     /**
     * Test case for beaconsGet
     *
     * Erhalte alle Beacons.
     *@depends testBeaconsBeaconIdPut
     */
    public function testBeaconsBeaconIdGetAgain($uuid)
    {    
        $api_instance = new BeaconApi;
        try{ $result = $api_instance->beaconsBeaconIdGet($uuid);
            $this->assertTrue($result['major']==0);
            
            $this->assertTrue($result['minor']==3);
            
            return $uuid;
        } catch (\Swagger\Client\ApiException $ex) {
             //print $ex->getCode();
        }
        return $uuid;
    }
    
    
    /**
     * Test case for beaconsGet
     *
     * Erhalte alle Beacons.
     *@depends testBeaconsBeaconIdGetAgain
     */
    public function testBeaconsGet($uuid)
    {
        $api_instance = new BeaconApi;
        try{ $result = $api_instance->beaconsGet();
            $this->assertTrue(count($result)>=1);
        } catch (\Swagger\Client\ApiException $ex) {
             //print $ex->getCode();
        }
        return $uuid;
    }
    /**
     * Test case for beaconsBeaconIdDelete
     *
     * löschen.
     *@depends testBeaconsGet 
     */
    public function testBeaconsBeaconIdDelete($uuid)
    {
        $api_instance = new BeaconApi;
        try{ list($result,$statuscode,$c) = $api_instance->beaconsBeaconIdDeleteWithHttpInfo($uuid);
            $this->assertTrue($statuscode==200);
            $result = $api_instance->beaconsBeaconIdGet($uuid);
            //var_dump($result);
        } catch (\Swagger\Client\ApiException $ex) {
            $this->assertTrue($ex->getCode()==404);
        }
    }

}
