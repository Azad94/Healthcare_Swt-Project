<?php
/**
 * BeaconObject
 *
 * PHP version 5
 *
 * @category Class
 * @package  Swagger\Client
 * @author   Swaagger Codegen team
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
 * Do not edit the class manually.
 */

namespace Swagger\Client\Model;

use \ArrayAccess;

/**
 * BeaconObject Class Doc Comment
 *
 * @category    Class
 * @package     Swagger\Client
 * @author      Swagger Codegen team
 * @link        https://github.com/swagger-api/swagger-codegen
 */
class BeaconObject implements ArrayAccess
{
    const DISCRIMINATOR = null;

    /**
      * The original name of the model.
      * @var string
      */
    protected static $swaggerModelName = 'BeaconObject';

    /**
      * Array of property to type mappings. Used for (de)serialization
      * @var string[]
      */
    protected static $swaggerTypes = [
        'id' => 'int',
        'name' => 'string',
        'picture_of_object' => '\Swagger\Client\Model\PictureOfObject',
        'beacon' => '\Swagger\Client\Model\Beacon',
        'location' => '\Swagger\Client\Model\Location',
        'state' => 'string',
        'beacon_object_type' => 'string'
    ];

    public static function swaggerTypes()
    {
        return self::$swaggerTypes;
    }

    /**
     * Array of attributes where the key is the local name, and the value is the original name
     * @var string[]
     */
    protected static $attributeMap = [
        'id' => 'id',
        'name' => 'name',
        'picture_of_object' => 'pictureOfObject',
        'beacon' => 'beacon',
        'location' => 'location',
        'state' => 'state',
        'beacon_object_type' => 'beaconObjectType'
    ];


    /**
     * Array of attributes to setter functions (for deserialization of responses)
     * @var string[]
     */
    protected static $setters = [
        'id' => 'setId',
        'name' => 'setName',
        'picture_of_object' => 'setPictureOfObject',
        'beacon' => 'setBeacon',
        'location' => 'setLocation',
        'state' => 'setState',
        'beacon_object_type' => 'setBeaconObjectType'
    ];


    /**
     * Array of attributes to getter functions (for serialization of requests)
     * @var string[]
     */
    protected static $getters = [
        'id' => 'getId',
        'name' => 'getName',
        'picture_of_object' => 'getPictureOfObject',
        'beacon' => 'getBeacon',
        'location' => 'getLocation',
        'state' => 'getState',
        'beacon_object_type' => 'getBeaconObjectType'
    ];

    public static function attributeMap()
    {
        return self::$attributeMap;
    }

    public static function setters()
    {
        return self::$setters;
    }

    public static function getters()
    {
        return self::$getters;
    }

    const BEACON_OBJECT_TYPE_STANDARDBETT = 'Standardbett';
    const BEACON_OBJECT_TYPE_SENIORENBETT = 'Seniorenbett';
    const BEACON_OBJECT_TYPE_KINDERBETT = 'Kinderbett';
    const BEACON_OBJECT_TYPE_BRANDSCHUTZTR = 'Brandschutztür';
    

    
    /**
     * Gets allowable values of the enum
     * @return string[]
     */
    public function getBeaconObjectTypeAllowableValues()
    {
        return [
            self::BEACON_OBJECT_TYPE_STANDARDBETT,
            self::BEACON_OBJECT_TYPE_SENIORENBETT,
            self::BEACON_OBJECT_TYPE_KINDERBETT,
            self::BEACON_OBJECT_TYPE_BRANDSCHUTZTR,
        ];
    }
    

    /**
     * Associative array for storing property values
     * @var mixed[]
     */
    protected $container = [];

    /**
     * Constructor
     * @param mixed[] $data Associated array of property values initializing the model
     */
    public function __construct(array $data = null)
    {
        $this->container['id'] = isset($data['id']) ? $data['id'] : null;
        $this->container['name'] = isset($data['name']) ? $data['name'] : null;
        $this->container['picture_of_object'] = isset($data['picture_of_object']) ? $data['picture_of_object'] : null;
        $this->container['beacon'] = isset($data['beacon']) ? $data['beacon'] : null;
        $this->container['location'] = isset($data['location']) ? $data['location'] : null;
        $this->container['state'] = isset($data['state']) ? $data['state'] : null;
        $this->container['beacon_object_type'] = isset($data['beacon_object_type']) ? $data['beacon_object_type'] : null;
    }

    /**
     * show all the invalid properties with reasons.
     *
     * @return array invalid properties with reasons
     */
    public function listInvalidProperties()
    {
        $invalid_properties = [];

        $allowed_values = ["Standardbett", "Seniorenbett", "Kinderbett", "Brandschutztür"];
        if (!in_array($this->container['beacon_object_type'], $allowed_values)) {
            $invalid_properties[] = "invalid value for 'beacon_object_type', must be one of 'Standardbett', 'Seniorenbett', 'Kinderbett', 'Brandschutztür'.";
        }

        return $invalid_properties;
    }

    /**
     * validate all the properties in the model
     * return true if all passed
     *
     * @return bool True if all properties are valid
     */
    public function valid()
    {

        $allowed_values = ["Standardbett", "Seniorenbett", "Kinderbett", "Brandschutztür"];
        if (!in_array($this->container['beacon_object_type'], $allowed_values)) {
            return false;
        }
        return true;
    }


    /**
     * Gets id
     * @return int
     */
    public function getId()
    {
        return $this->container['id'];
    }

    /**
     * Sets id
     * @param int $id
     * @return $this
     */
    public function setId($id)
    {
        $this->container['id'] = $id;

        return $this;
    }

    /**
     * Gets name
     * @return string
     */
    public function getName()
    {
        return $this->container['name'];
    }

    /**
     * Sets name
     * @param string $name
     * @return $this
     */
    public function setName($name)
    {
        $this->container['name'] = $name;

        return $this;
    }

    /**
     * Gets picture_of_object
     * @return \Swagger\Client\Model\PictureOfObject
     */
    public function getPictureOfObject()
    {
        return $this->container['picture_of_object'];
    }

    /**
     * Sets picture_of_object
     * @param \Swagger\Client\Model\PictureOfObject $picture_of_object
     * @return $this
     */
    public function setPictureOfObject($picture_of_object)
    {
        $this->container['picture_of_object'] = $picture_of_object;

        return $this;
    }

    /**
     * Gets beacon
     * @return \Swagger\Client\Model\Beacon
     */
    public function getBeacon()
    {
        return $this->container['beacon'];
    }

    /**
     * Sets beacon
     * @param \Swagger\Client\Model\Beacon $beacon
     * @return $this
     */
    public function setBeacon($beacon)
    {
        $this->container['beacon'] = $beacon;

        return $this;
    }

    /**
     * Gets location
     * @return \Swagger\Client\Model\Location
     */
    public function getLocation()
    {
        return $this->container['location'];
    }

    /**
     * Sets location
     * @param \Swagger\Client\Model\Location $location
     * @return $this
     */
    public function setLocation($location)
    {
        $this->container['location'] = $location;

        return $this;
    }

    /**
     * Gets state
     * @return string
     */
    public function getState()
    {
        return $this->container['state'];
    }

    /**
     * Sets state
     * @param string $state Kann benutzt werden um einen status zu verdeutlichen
     * @return $this
     */
    public function setState($state)
    {
        $this->container['state'] = $state;

        return $this;
    }

    /**
     * Gets beacon_object_type
     * @return string
     */
    public function getBeaconObjectType()
    {
        return $this->container['beacon_object_type'];
    }

    /**
     * Sets beacon_object_type
     * @param string $beacon_object_type
     * @return $this
     */
    public function setBeaconObjectType($beacon_object_type)
    {
        $allowed_values = array('Standardbett', 'Seniorenbett', 'Kinderbett', 'Brandschutztür');
        if (!is_null($beacon_object_type) && (!in_array($beacon_object_type, $allowed_values))) {
            throw new \InvalidArgumentException("Invalid value for 'beacon_object_type', must be one of 'Standardbett', 'Seniorenbett', 'Kinderbett', 'Brandschutztür'");
        }
        $this->container['beacon_object_type'] = $beacon_object_type;

        return $this;
    }
    /**
     * Returns true if offset exists. False otherwise.
     * @param  integer $offset Offset
     * @return boolean
     */
    public function offsetExists($offset)
    {
        return isset($this->container[$offset]);
    }

    /**
     * Gets offset.
     * @param  integer $offset Offset
     * @return mixed
     */
    public function offsetGet($offset)
    {
        return isset($this->container[$offset]) ? $this->container[$offset] : null;
    }

    /**
     * Sets value based on offset.
     * @param  integer $offset Offset
     * @param  mixed   $value  Value to be set
     * @return void
     */
    public function offsetSet($offset, $value)
    {
        if (is_null($offset)) {
            $this->container[] = $value;
        } else {
            $this->container[$offset] = $value;
        }
    }

    /**
     * Unsets offset.
     * @param  integer $offset Offset
     * @return void
     */
    public function offsetUnset($offset)
    {
        unset($this->container[$offset]);
    }

    /**
     * Gets the string presentation of the object
     * @return string
     */
    public function __toString()
    {
        if (defined('JSON_PRETTY_PRINT')) { // use JSON pretty print
            return json_encode(\Swagger\Client\ObjectSerializer::sanitizeForSerialization($this), JSON_PRETTY_PRINT);
        }

        return json_encode(\Swagger\Client\ObjectSerializer::sanitizeForSerialization($this));
    }
}


