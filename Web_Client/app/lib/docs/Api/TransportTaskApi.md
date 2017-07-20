# Swagger\Client\TransportTaskApi

All URIs are relative to *http://localhost:8080/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**beaconObjectsBeaconObjectIdTransporttasksGet**](TransportTaskApi.md#beaconObjectsBeaconObjectIdTransporttasksGet) | **GET** /beaconObjects/{beaconObjectId}/transporttasks | Erhalte alle Transporttasks die zu diesem beaconObject gehören
[**locationsTransporttasksGet**](TransportTaskApi.md#locationsTransporttasksGet) | **GET** /locations/transporttasks | Liefert alle TransportTask zu einer örtlichen beschreibung
[**maintainancetasksPut**](TransportTaskApi.md#maintainancetasksPut) | **PUT** /maintainancetasks | Eine reihe MaintainanceTask bearbeiten
[**transporttasksAppPost**](TransportTaskApi.md#transporttasksAppPost) | **POST** /transporttasksApp | Erhalte die ersten 20 offenen tasks
[**transporttasksAppSizePost**](TransportTaskApi.md#transporttasksAppSizePost) | **POST** /transporttasksApp/size | Anzahl der Tasks
[**transporttasksGet**](TransportTaskApi.md#transporttasksGet) | **GET** /transporttasks | Erhalte die ersten 20 offenen tasks
[**transporttasksPost**](TransportTaskApi.md#transporttasksPost) | **POST** /transporttasks | Erstelle einen neuen Task
[**transporttasksPut**](TransportTaskApi.md#transporttasksPut) | **PUT** /transporttasks | Eine reihe Transporttasks bearbeiten
[**transporttasksSizeGet**](TransportTaskApi.md#transporttasksSizeGet) | **GET** /transporttasks/size | Anzahl der Tasks
[**transporttasksTaskidDelete**](TransportTaskApi.md#transporttasksTaskidDelete) | **DELETE** /transporttasks/{taskid} | Löscht dieses Objekt
[**transporttasksTaskidGet**](TransportTaskApi.md#transporttasksTaskidGet) | **GET** /transporttasks/{taskid} | Erhalte einen einzelnen Task
[**transporttasksTaskidPut**](TransportTaskApi.md#transporttasksTaskidPut) | **PUT** /transporttasks/{taskid} | Update einen Task


# **beaconObjectsBeaconObjectIdTransporttasksGet**
> \Swagger\Client\Model\TransportTask[] beaconObjectsBeaconObjectIdTransporttasksGet($beacon_object_id)

Erhalte alle Transporttasks die zu diesem beaconObject gehören



### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$beacon_object_id = 789; // int | 

try {
    $result = $api_instance->beaconObjectsBeaconObjectIdTransporttasksGet($beacon_object_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->beaconObjectsBeaconObjectIdTransporttasksGet: ', $e->getMessage(), PHP_EOL;
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

# **locationsTransporttasksGet**
> \Swagger\Client\Model\TransportTask[] locationsTransporttasksGet($floor, $building, $room)

Liefert alle TransportTask zu einer örtlichen beschreibung

Was oben steht

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$floor = 56; // int | 
$building = 56; // int | 
$room = 56; // int | 

try {
    $result = $api_instance->locationsTransporttasksGet($floor, $building, $room);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->locationsTransporttasksGet: ', $e->getMessage(), PHP_EOL;
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

[**\Swagger\Client\Model\TransportTask[]**](../Model/TransportTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksPut**
> maintainancetasksPut($body)

Eine reihe MaintainanceTask bearbeiten

Irgendwas

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$body = array(new MaintainanceTask()); // \Swagger\Client\Model\MaintainanceTask[] | 

try {
    $api_instance->maintainancetasksPut($body);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->maintainancetasksPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\MaintainanceTask[]**](../Model/MaintainanceTask.md)|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **transporttasksAppPost**
> \Swagger\Client\Model\TransportTask[] transporttasksAppPost($body, $limit, $before, $since, $open, $closed, $processing, $own, $building)

Erhalte die ersten 20 offenen tasks



### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$body = new \Swagger\Client\Model\Body1(); // \Swagger\Client\Model\Body1 | 
$limit = 20; // int | Wie viele Tasks sollen geliefert werden
$before = new \DateTime(); // \DateTime | Vor welchem Datum
$since = new \DateTime(); // \DateTime | Seit Wann
$open = true; // bool | 
$closed = true; // bool | 
$processing = true; // bool | 
$own = false; // bool | Nur eigene tasks anzeigen?
$building = 56; // int | 

try {
    $result = $api_instance->transporttasksAppPost($body, $limit, $before, $since, $open, $closed, $processing, $own, $building);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->transporttasksAppPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\Body1**](../Model/\Swagger\Client\Model\Body1.md)|  |
 **limit** | **int**| Wie viele Tasks sollen geliefert werden | [optional] [default to 20]
 **before** | **\DateTime**| Vor welchem Datum | [optional]
 **since** | **\DateTime**| Seit Wann | [optional]
 **open** | **bool**|  | [optional] [default to true]
 **closed** | **bool**|  | [optional] [default to true]
 **processing** | **bool**|  | [optional] [default to true]
 **own** | **bool**| Nur eigene tasks anzeigen? | [optional] [default to false]
 **building** | **int**|  | [optional]

### Return type

[**\Swagger\Client\Model\TransportTask[]**](../Model/TransportTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **transporttasksAppSizePost**
> int transporttasksAppSizePost($new_tasks, $user_name)

Anzahl der Tasks

Anzahl der Tasks

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$new_tasks = true; // bool | Nur die anzahl an neuen Tasks haben seit der letzten abfrage
$user_name = new \Swagger\Client\Model\UserName(); // \Swagger\Client\Model\UserName | 

try {
    $result = $api_instance->transporttasksAppSizePost($new_tasks, $user_name);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->transporttasksAppSizePost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **new_tasks** | **bool**| Nur die anzahl an neuen Tasks haben seit der letzten abfrage |
 **user_name** | [**\Swagger\Client\Model\UserName**](../Model/\Swagger\Client\Model\UserName.md)|  |

### Return type

**int**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **transporttasksGet**
> \Swagger\Client\Model\TransportTask[] transporttasksGet($body, $limit, $before, $since, $open, $closed, $processing, $own, $building)

Erhalte die ersten 20 offenen tasks



### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$body = new \Swagger\Client\Model\Body2(); // \Swagger\Client\Model\Body2 | 
$limit = 20; // int | Wie viele Tasks sollen geliefert werden
$before = "now"; // string | Vor welchem Datum
$since = "1970-01-01"; // string | Seit Wann
$open = true; // bool | 
$closed = true; // bool | 
$processing = true; // bool | 
$own = false; // bool | Nur eigene tasks anzeigen?
$building = 56; // int | 

try {
    $result = $api_instance->transporttasksGet($body, $limit, $before, $since, $open, $closed, $processing, $own, $building);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->transporttasksGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\Body2**](../Model/\Swagger\Client\Model\Body2.md)|  |
 **limit** | **int**| Wie viele Tasks sollen geliefert werden | [optional] [default to 20]
 **before** | **string**| Vor welchem Datum | [optional] [default to now]
 **since** | **string**| Seit Wann | [optional] [default to 1970-01-01]
 **open** | **bool**|  | [optional] [default to true]
 **closed** | **bool**|  | [optional] [default to true]
 **processing** | **bool**|  | [optional] [default to true]
 **own** | **bool**| Nur eigene tasks anzeigen? | [optional] [default to false]
 **building** | **int**|  | [optional]

### Return type

[**\Swagger\Client\Model\TransportTask[]**](../Model/TransportTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **transporttasksPost**
> \Swagger\Client\Model\TransportTask transporttasksPost($body)

Erstelle einen neuen Task

Task

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$body = new \Swagger\Client\Model\TransportTask(); // \Swagger\Client\Model\TransportTask | 

try {
    $result = $api_instance->transporttasksPost($body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->transporttasksPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\TransportTask**](../Model/\Swagger\Client\Model\TransportTask.md)|  |

### Return type

[**\Swagger\Client\Model\TransportTask**](../Model/TransportTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **transporttasksPut**
> transporttasksPut($body)

Eine reihe Transporttasks bearbeiten

Irgendwas

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$body = array(new TransportTask()); // \Swagger\Client\Model\TransportTask[] | 

try {
    $api_instance->transporttasksPut($body);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->transporttasksPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\TransportTask[]**](../Model/TransportTask.md)|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **transporttasksSizeGet**
> int transporttasksSizeGet($new_tasks, $user_name)

Anzahl der Tasks

Anzahl der Tasks

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$new_tasks = true; // bool | Nur die anzahl an neuen Tasks haben seit der letzten abfrage
$user_name = new \Swagger\Client\Model\UserName1(); // \Swagger\Client\Model\UserName1 | 

try {
    $result = $api_instance->transporttasksSizeGet($new_tasks, $user_name);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->transporttasksSizeGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **new_tasks** | **bool**| Nur die anzahl an neuen Tasks haben seit der letzten abfrage |
 **user_name** | [**\Swagger\Client\Model\UserName1**](../Model/\Swagger\Client\Model\UserName1.md)|  |

### Return type

**int**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **transporttasksTaskidDelete**
> transporttasksTaskidDelete($taskid)

Löscht dieses Objekt

Dieses Objekt löschen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$taskid = 789; // int | 

try {
    $api_instance->transporttasksTaskidDelete($taskid);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->transporttasksTaskidDelete: ', $e->getMessage(), PHP_EOL;
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

# **transporttasksTaskidGet**
> \Swagger\Client\Model\TransportTask transporttasksTaskidGet($taskid)

Erhalte einen einzelnen Task

Einzelner Task

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$taskid = 789; // int | 

try {
    $result = $api_instance->transporttasksTaskidGet($taskid);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->transporttasksTaskidGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskid** | **int**|  |

### Return type

[**\Swagger\Client\Model\TransportTask**](../Model/TransportTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **transporttasksTaskidPut**
> transporttasksTaskidPut($taskid, $body)

Update einen Task

Eine einzelne Task neue Werte zuweisen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\TransportTaskApi();
$taskid = 789; // int | 
$body = new \Swagger\Client\Model\TransportTask(); // \Swagger\Client\Model\TransportTask | 

try {
    $api_instance->transporttasksTaskidPut($taskid, $body);
} catch (Exception $e) {
    echo 'Exception when calling TransportTaskApi->transporttasksTaskidPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskid** | **int**|  |
 **body** | [**\Swagger\Client\Model\TransportTask**](../Model/\Swagger\Client\Model\TransportTask.md)|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

