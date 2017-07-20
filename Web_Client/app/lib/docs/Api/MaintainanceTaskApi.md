# Swagger\Client\MaintainanceTaskApi

All URIs are relative to *http://localhost:8080/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**beaconObjectsBeaconObjectIdMaintainancetasksGet**](MaintainanceTaskApi.md#beaconObjectsBeaconObjectIdMaintainancetasksGet) | **GET** /beaconObjects/{beaconObjectId}/maintainancetasks | Alle Maintainancetasks die zu diesem BeaconObjekt gehören
[**locationsMaintainancetasksGet**](MaintainanceTaskApi.md#locationsMaintainancetasksGet) | **GET** /locations/maintainancetasks | Liefert alle MaintainanceTasks zu einer örtlichen beschreibung
[**maintainancetasksAppPost**](MaintainanceTaskApi.md#maintainancetasksAppPost) | **POST** /maintainancetasksApp | Erhalte die ersten 20 offenen tasks
[**maintainancetasksAppSizePost**](MaintainanceTaskApi.md#maintainancetasksAppSizePost) | **POST** /maintainancetasksApp/size | Anzahl der Tasks
[**maintainancetasksGet**](MaintainanceTaskApi.md#maintainancetasksGet) | **GET** /maintainancetasks | Erhalte die ersten 20 offenen tasks
[**maintainancetasksPost**](MaintainanceTaskApi.md#maintainancetasksPost) | **POST** /maintainancetasks | Erstelle einen neuen Task
[**maintainancetasksSizeGet**](MaintainanceTaskApi.md#maintainancetasksSizeGet) | **GET** /maintainancetasks/size | Anzahl der Tasks
[**maintainancetasksTaskidDelete**](MaintainanceTaskApi.md#maintainancetasksTaskidDelete) | **DELETE** /maintainancetasks/{taskid} | löschen
[**maintainancetasksTaskidGet**](MaintainanceTaskApi.md#maintainancetasksTaskidGet) | **GET** /maintainancetasks/{taskid} | Erhalte einen einzelnen Task
[**maintainancetasksTaskidPut**](MaintainanceTaskApi.md#maintainancetasksTaskidPut) | **PUT** /maintainancetasks/{taskid} | Update einen Task


# **beaconObjectsBeaconObjectIdMaintainancetasksGet**
> \Swagger\Client\Model\MaintainanceTask[] beaconObjectsBeaconObjectIdMaintainancetasksGet($beacon_object_id)

Alle Maintainancetasks die zu diesem BeaconObjekt gehören

Alle Maintainancetasks die zu diesem BeaconObjekt gehören

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\MaintainanceTaskApi();
$beacon_object_id = 789; // int | 

try {
    $result = $api_instance->beaconObjectsBeaconObjectIdMaintainancetasksGet($beacon_object_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling MaintainanceTaskApi->beaconObjectsBeaconObjectIdMaintainancetasksGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **beacon_object_id** | **int**|  |

### Return type

[**\Swagger\Client\Model\MaintainanceTask[]**](../Model/MaintainanceTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **locationsMaintainancetasksGet**
> \Swagger\Client\Model\MaintainanceTask[] locationsMaintainancetasksGet($floor, $building, $room)

Liefert alle MaintainanceTasks zu einer örtlichen beschreibung

Was oben steht

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\MaintainanceTaskApi();
$floor = 56; // int | 
$building = 56; // int | 
$room = 56; // int | 

try {
    $result = $api_instance->locationsMaintainancetasksGet($floor, $building, $room);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling MaintainanceTaskApi->locationsMaintainancetasksGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **floor** | **int**|  | [optional]
 **building** | **int**|  | [optional]
 **room** | **int**|  | [optional]

### Return type

[**\Swagger\Client\Model\MaintainanceTask[]**](../Model/MaintainanceTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksAppPost**
> \Swagger\Client\Model\MaintainanceTask[] maintainancetasksAppPost($user_name, $limit, $before, $since, $open, $closed, $processing, $own, $building)

Erhalte die ersten 20 offenen tasks



### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\MaintainanceTaskApi();
$user_name = new \Swagger\Client\Model\UserName2(); // \Swagger\Client\Model\UserName2 | 
$limit = 20; // int | Wie viele Tasks sollen geliefert werden
$before = "now"; // string | Vor welchem Datum
$since = "1970-01-01"; // string | Seit Wann
$open = true; // bool | 
$closed = true; // bool | 
$processing = true; // bool | 
$own = false; // bool | Nur eigene tasks anzeigen?
$building = 56; // int | 

try {
    $result = $api_instance->maintainancetasksAppPost($user_name, $limit, $before, $since, $open, $closed, $processing, $own, $building);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling MaintainanceTaskApi->maintainancetasksAppPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **user_name** | [**\Swagger\Client\Model\UserName2**](../Model/\Swagger\Client\Model\UserName2.md)|  |
 **limit** | **int**| Wie viele Tasks sollen geliefert werden | [optional] [default to 20]
 **before** | **string**| Vor welchem Datum | [optional] [default to now]
 **since** | **string**| Seit Wann | [optional] [default to 1970-01-01]
 **open** | **bool**|  | [optional] [default to true]
 **closed** | **bool**|  | [optional] [default to true]
 **processing** | **bool**|  | [optional] [default to true]
 **own** | **bool**| Nur eigene tasks anzeigen? | [optional] [default to false]
 **building** | **int**|  | [optional]

### Return type

[**\Swagger\Client\Model\MaintainanceTask[]**](../Model/MaintainanceTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksAppSizePost**
> int maintainancetasksAppSizePost($new_tasks, $user_name)

Anzahl der Tasks

Anzahl der Tasks

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\MaintainanceTaskApi();
$new_tasks = true; // bool | Nur die anzahl an neuen Tasks haben seit der letzten abfrage
$user_name = new \Swagger\Client\Model\UserName4(); // \Swagger\Client\Model\UserName4 | 

try {
    $result = $api_instance->maintainancetasksAppSizePost($new_tasks, $user_name);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling MaintainanceTaskApi->maintainancetasksAppSizePost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **new_tasks** | **bool**| Nur die anzahl an neuen Tasks haben seit der letzten abfrage |
 **user_name** | [**\Swagger\Client\Model\UserName4**](../Model/\Swagger\Client\Model\UserName4.md)|  |

### Return type

**int**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksGet**
> \Swagger\Client\Model\MaintainanceTask[] maintainancetasksGet($user_name, $limit, $before, $since, $open, $closed, $processing, $own, $building)

Erhalte die ersten 20 offenen tasks



### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\MaintainanceTaskApi();
$user_name = new \Swagger\Client\Model\UserName3(); // \Swagger\Client\Model\UserName3 | 
$limit = 20; // int | Wie viele Tasks sollen geliefert werden
$before = "now"; // string | Vor welchem Datum
$since = "1970-01-01"; // string | Seit Wann
$open = true; // bool | 
$closed = true; // bool | 
$processing = true; // bool | 
$own = false; // bool | Nur eigene tasks anzeigen?
$building = 56; // int | 

try {
    $result = $api_instance->maintainancetasksGet($user_name, $limit, $before, $since, $open, $closed, $processing, $own, $building);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling MaintainanceTaskApi->maintainancetasksGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **user_name** | [**\Swagger\Client\Model\UserName3**](../Model/\Swagger\Client\Model\UserName3.md)|  |
 **limit** | **int**| Wie viele Tasks sollen geliefert werden | [optional] [default to 20]
 **before** | **string**| Vor welchem Datum | [optional] [default to now]
 **since** | **string**| Seit Wann | [optional] [default to 1970-01-01]
 **open** | **bool**|  | [optional] [default to true]
 **closed** | **bool**|  | [optional] [default to true]
 **processing** | **bool**|  | [optional] [default to true]
 **own** | **bool**| Nur eigene tasks anzeigen? | [optional] [default to false]
 **building** | **int**|  | [optional]

### Return type

[**\Swagger\Client\Model\MaintainanceTask[]**](../Model/MaintainanceTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksPost**
> \Swagger\Client\Model\MaintainanceTask maintainancetasksPost($body)

Erstelle einen neuen Task

Task

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\MaintainanceTaskApi();
$body = new \Swagger\Client\Model\MaintainanceTask(); // \Swagger\Client\Model\MaintainanceTask | 

try {
    $result = $api_instance->maintainancetasksPost($body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling MaintainanceTaskApi->maintainancetasksPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\MaintainanceTask**](../Model/\Swagger\Client\Model\MaintainanceTask.md)|  |

### Return type

[**\Swagger\Client\Model\MaintainanceTask**](../Model/MaintainanceTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksSizeGet**
> int maintainancetasksSizeGet($new_tasks, $user_name)

Anzahl der Tasks

Anzahl der Tasks

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\MaintainanceTaskApi();
$new_tasks = true; // bool | Nur die anzahl an neuen Tasks haben seit der letzten abfrage
$user_name = new \Swagger\Client\Model\UserName5(); // \Swagger\Client\Model\UserName5 | 

try {
    $result = $api_instance->maintainancetasksSizeGet($new_tasks, $user_name);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling MaintainanceTaskApi->maintainancetasksSizeGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **new_tasks** | **bool**| Nur die anzahl an neuen Tasks haben seit der letzten abfrage |
 **user_name** | [**\Swagger\Client\Model\UserName5**](../Model/\Swagger\Client\Model\UserName5.md)|  |

### Return type

**int**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksTaskidDelete**
> maintainancetasksTaskidDelete($taskid)

löschen

löschen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\MaintainanceTaskApi();
$taskid = 789; // int | 

try {
    $api_instance->maintainancetasksTaskidDelete($taskid);
} catch (Exception $e) {
    echo 'Exception when calling MaintainanceTaskApi->maintainancetasksTaskidDelete: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskid** | **int**|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksTaskidGet**
> \Swagger\Client\Model\MaintainanceTask maintainancetasksTaskidGet($taskid)

Erhalte einen einzelnen Task

Einzelner Task

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\MaintainanceTaskApi();
$taskid = 789; // int | 

try {
    $result = $api_instance->maintainancetasksTaskidGet($taskid);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling MaintainanceTaskApi->maintainancetasksTaskidGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskid** | **int**|  |

### Return type

[**\Swagger\Client\Model\MaintainanceTask**](../Model/MaintainanceTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksTaskidPut**
> maintainancetasksTaskidPut($taskid, $body)

Update einen Task

Eine einzelne Task neue Werte zuweisen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\MaintainanceTaskApi();
$taskid = 789; // int | 
$body = new \Swagger\Client\Model\MaintainanceTask(); // \Swagger\Client\Model\MaintainanceTask | 

try {
    $api_instance->maintainancetasksTaskidPut($taskid, $body);
} catch (Exception $e) {
    echo 'Exception when calling MaintainanceTaskApi->maintainancetasksTaskidPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskid** | **int**|  |
 **body** | [**\Swagger\Client\Model\MaintainanceTask**](../Model/\Swagger\Client\Model\MaintainanceTask.md)|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

