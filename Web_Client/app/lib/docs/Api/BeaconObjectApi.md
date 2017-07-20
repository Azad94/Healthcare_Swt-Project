# Swagger\Client\BeaconObjectApi

All URIs are relative to *http://localhost:8080/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**beaconObjectsBeaconObjectIdCleaningtasksGet**](BeaconObjectApi.md#beaconObjectsBeaconObjectIdCleaningtasksGet) | **GET** /beaconObjects/{beaconObjectId}/cleaningtasks | Erhalte alle cleaningtasks zu diesem beaconObject
[**beaconObjectsBeaconObjectIdDelete**](BeaconObjectApi.md#beaconObjectsBeaconObjectIdDelete) | **DELETE** /beaconObjects/{beaconObjectId} | löschen
[**beaconObjectsBeaconObjectIdGet**](BeaconObjectApi.md#beaconObjectsBeaconObjectIdGet) | **GET** /beaconObjects/{beaconObjectId} | Einzelnes Beacon Object
[**beaconObjectsBeaconObjectIdPut**](BeaconObjectApi.md#beaconObjectsBeaconObjectIdPut) | **PUT** /beaconObjects/{beaconObjectId} | Bearbeiten
[**beaconObjectsBeaconObjectIdTransporttasksGet**](BeaconObjectApi.md#beaconObjectsBeaconObjectIdTransporttasksGet) | **GET** /beaconObjects/{beaconObjectId}/transporttasks | Erhalte alle Transporttasks die zu diesem beaconObject gehören
[**beaconObjectsGet**](BeaconObjectApi.md#beaconObjectsGet) | **GET** /beaconObjects | Alle Beaconobjekte
[**beaconObjectsPost**](BeaconObjectApi.md#beaconObjectsPost) | **POST** /beaconObjects | Erstellen


# **beaconObjectsBeaconObjectIdCleaningtasksGet**
> \Swagger\Client\Model\CleaningTask[] beaconObjectsBeaconObjectIdCleaningtasksGet($beacon_object_id)

Erhalte alle cleaningtasks zu diesem beaconObject



### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconObjectApi();
$beacon_object_id = 789; // int | 

try {
    $result = $api_instance->beaconObjectsBeaconObjectIdCleaningtasksGet($beacon_object_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling BeaconObjectApi->beaconObjectsBeaconObjectIdCleaningtasksGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **beacon_object_id** | **int**|  |

### Return type

[**\Swagger\Client\Model\CleaningTask[]**](../Model/CleaningTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **beaconObjectsBeaconObjectIdDelete**
> beaconObjectsBeaconObjectIdDelete($beacon_object_id)

löschen

löschen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconObjectApi();
$beacon_object_id = 789; // int | 

try {
    $api_instance->beaconObjectsBeaconObjectIdDelete($beacon_object_id);
} catch (Exception $e) {
    echo 'Exception when calling BeaconObjectApi->beaconObjectsBeaconObjectIdDelete: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **beacon_object_id** | **int**|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **beaconObjectsBeaconObjectIdGet**
> \Swagger\Client\Model\BeaconObject beaconObjectsBeaconObjectIdGet($beacon_object_id)

Einzelnes Beacon Object

Ein einzelnes BeaconOjbekt

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconObjectApi();
$beacon_object_id = 789; // int | 

try {
    $result = $api_instance->beaconObjectsBeaconObjectIdGet($beacon_object_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling BeaconObjectApi->beaconObjectsBeaconObjectIdGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **beacon_object_id** | **int**|  |

### Return type

[**\Swagger\Client\Model\BeaconObject**](../Model/BeaconObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **beaconObjectsBeaconObjectIdPut**
> beaconObjectsBeaconObjectIdPut($beacon_object_id, $body)

Bearbeiten

Einzelnes BeaconObjekt bearbeiten

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconObjectApi();
$beacon_object_id = 789; // int | 
$body = new \Swagger\Client\Model\BeaconObject(); // \Swagger\Client\Model\BeaconObject | 

try {
    $api_instance->beaconObjectsBeaconObjectIdPut($beacon_object_id, $body);
} catch (Exception $e) {
    echo 'Exception when calling BeaconObjectApi->beaconObjectsBeaconObjectIdPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **beacon_object_id** | **int**|  |
 **body** | [**\Swagger\Client\Model\BeaconObject**](../Model/\Swagger\Client\Model\BeaconObject.md)|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **beaconObjectsBeaconObjectIdTransporttasksGet**
> \Swagger\Client\Model\TransportTask[] beaconObjectsBeaconObjectIdTransporttasksGet($beacon_object_id)

Erhalte alle Transporttasks die zu diesem beaconObject gehören



### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconObjectApi();
$beacon_object_id = 789; // int | 

try {
    $result = $api_instance->beaconObjectsBeaconObjectIdTransporttasksGet($beacon_object_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling BeaconObjectApi->beaconObjectsBeaconObjectIdTransporttasksGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **beacon_object_id** | **int**|  |

### Return type

[**\Swagger\Client\Model\TransportTask[]**](../Model/TransportTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **beaconObjectsGet**
> \Swagger\Client\Model\BeaconObject[] beaconObjectsGet()

Alle Beaconobjekte

Alle Beaconobjekte

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconObjectApi();

try {
    $result = $api_instance->beaconObjectsGet();
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling BeaconObjectApi->beaconObjectsGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**\Swagger\Client\Model\BeaconObject[]**](../Model/BeaconObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **beaconObjectsPost**
> \Swagger\Client\Model\BeaconObject beaconObjectsPost($body)

Erstellen

BeaconObjekt erstellen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconObjectApi();
$body = new \Swagger\Client\Model\BeaconObject(); // \Swagger\Client\Model\BeaconObject | 

try {
    $result = $api_instance->beaconObjectsPost($body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling BeaconObjectApi->beaconObjectsPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\BeaconObject**](../Model/\Swagger\Client\Model\BeaconObject.md)|  |

### Return type

[**\Swagger\Client\Model\BeaconObject**](../Model/BeaconObject.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

