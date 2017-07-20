# Swagger\Client\CleaningTaskApi

All URIs are relative to *http://localhost:8080/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**beaconObjectsBeaconObjectIdCleaningtasksGet**](CleaningTaskApi.md#beaconObjectsBeaconObjectIdCleaningtasksGet) | **GET** /beaconObjects/{beaconObjectId}/cleaningtasks | Erhalte alle cleaningtasks zu diesem beaconObject
[**cleaningtasksAppPost**](CleaningTaskApi.md#cleaningtasksAppPost) | **POST** /cleaningtasksApp | Erhalte die ersten 20 offenen tasks
[**cleaningtasksAppSizePost**](CleaningTaskApi.md#cleaningtasksAppSizePost) | **POST** /cleaningtasksApp/size | Anzahl der Tasks
[**cleaningtasksGet**](CleaningTaskApi.md#cleaningtasksGet) | **GET** /cleaningtasks | Erhalte die ersten 20 offenen tasks
[**cleaningtasksPost**](CleaningTaskApi.md#cleaningtasksPost) | **POST** /cleaningtasks | Erstelle einen neuen Task
[**cleaningtasksPut**](CleaningTaskApi.md#cleaningtasksPut) | **PUT** /cleaningtasks | Eine reihe CleaningTask bearbeiten
[**cleaningtasksSizeGet**](CleaningTaskApi.md#cleaningtasksSizeGet) | **GET** /cleaningtasks/size | Anzahl der Tasks
[**cleaningtasksTaskidDelete**](CleaningTaskApi.md#cleaningtasksTaskidDelete) | **DELETE** /cleaningtasks/{taskid} | löschen
[**cleaningtasksTaskidGet**](CleaningTaskApi.md#cleaningtasksTaskidGet) | **GET** /cleaningtasks/{taskid} | Erhalte einen einzelnen Task
[**cleaningtasksTaskidPut**](CleaningTaskApi.md#cleaningtasksTaskidPut) | **PUT** /cleaningtasks/{taskid} | Update einen Task
[**locationsCleaningtasksGet**](CleaningTaskApi.md#locationsCleaningtasksGet) | **GET** /locations/cleaningtasks | Liefert alle CleaningTask zu einer örtlichen beschreibung


# **beaconObjectsBeaconObjectIdCleaningtasksGet**
> \Swagger\Client\Model\CleaningTask[] beaconObjectsBeaconObjectIdCleaningtasksGet($beacon_object_id)

Erhalte alle cleaningtasks zu diesem beaconObject



### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\CleaningTaskApi();
$beacon_object_id = 789; // int | 

try {
    $result = $api_instance->beaconObjectsBeaconObjectIdCleaningtasksGet($beacon_object_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling CleaningTaskApi->beaconObjectsBeaconObjectIdCleaningtasksGet: ', $e->getMessage(), PHP_EOL;
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

# **cleaningtasksAppPost**
> \Swagger\Client\Model\CleaningTask[] cleaningtasksAppPost($user_name, $limit, $before, $since, $open, $closed, $processing, $own, $building)

Erhalte die ersten 20 offenen tasks



### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\CleaningTaskApi();
$user_name = new \Swagger\Client\Model\UserName6(); // \Swagger\Client\Model\UserName6 | 
$limit = 20; // int | Wie viele Tasks sollen geliefert werden
$before = "now"; // string | Vor welchem Datum
$since = "1970-01-01"; // string | Seit Wann
$open = true; // bool | 
$closed = true; // bool | 
$processing = true; // bool | 
$own = false; // bool | Nur eigene tasks anzeigen?
$building = 56; // int | 

try {
    $result = $api_instance->cleaningtasksAppPost($user_name, $limit, $before, $since, $open, $closed, $processing, $own, $building);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling CleaningTaskApi->cleaningtasksAppPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **user_name** | [**\Swagger\Client\Model\UserName6**](../Model/\Swagger\Client\Model\UserName6.md)|  |
 **limit** | **int**| Wie viele Tasks sollen geliefert werden | [optional] [default to 20]
 **before** | **string**| Vor welchem Datum | [optional] [default to now]
 **since** | **string**| Seit Wann | [optional] [default to 1970-01-01]
 **open** | **bool**|  | [optional] [default to true]
 **closed** | **bool**|  | [optional] [default to true]
 **processing** | **bool**|  | [optional] [default to true]
 **own** | **bool**| Nur eigene tasks anzeigen? | [optional] [default to false]
 **building** | **int**|  | [optional]

### Return type

[**\Swagger\Client\Model\CleaningTask[]**](../Model/CleaningTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **cleaningtasksAppSizePost**
> int cleaningtasksAppSizePost($new_tasks, $user_name)

Anzahl der Tasks

Anzahl der Tasks

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\CleaningTaskApi();
$new_tasks = true; // bool | Nur die anzahl an neuen Tasks haben seit der letzten abfrage
$user_name = new \Swagger\Client\Model\UserName8(); // \Swagger\Client\Model\UserName8 | 

try {
    $result = $api_instance->cleaningtasksAppSizePost($new_tasks, $user_name);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling CleaningTaskApi->cleaningtasksAppSizePost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **new_tasks** | **bool**| Nur die anzahl an neuen Tasks haben seit der letzten abfrage |
 **user_name** | [**\Swagger\Client\Model\UserName8**](../Model/\Swagger\Client\Model\UserName8.md)|  |

### Return type

**int**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **cleaningtasksGet**
> \Swagger\Client\Model\CleaningTask[] cleaningtasksGet($user_name, $limit, $before, $since, $open, $closed, $processing, $own, $building)

Erhalte die ersten 20 offenen tasks



### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\CleaningTaskApi();
$user_name = new \Swagger\Client\Model\UserName7(); // \Swagger\Client\Model\UserName7 | 
$limit = 20; // int | Wie viele Tasks sollen geliefert werden
$before = "now"; // string | Vor welchem Datum
$since = "1970-01-01"; // string | Seit Wann
$open = true; // bool | 
$closed = true; // bool | 
$processing = true; // bool | 
$own = false; // bool | Nur eigene tasks anzeigen?
$building = 56; // int | 

try {
    $result = $api_instance->cleaningtasksGet($user_name, $limit, $before, $since, $open, $closed, $processing, $own, $building);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling CleaningTaskApi->cleaningtasksGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **user_name** | [**\Swagger\Client\Model\UserName7**](../Model/\Swagger\Client\Model\UserName7.md)|  |
 **limit** | **int**| Wie viele Tasks sollen geliefert werden | [optional] [default to 20]
 **before** | **string**| Vor welchem Datum | [optional] [default to now]
 **since** | **string**| Seit Wann | [optional] [default to 1970-01-01]
 **open** | **bool**|  | [optional] [default to true]
 **closed** | **bool**|  | [optional] [default to true]
 **processing** | **bool**|  | [optional] [default to true]
 **own** | **bool**| Nur eigene tasks anzeigen? | [optional] [default to false]
 **building** | **int**|  | [optional]

### Return type

[**\Swagger\Client\Model\CleaningTask[]**](../Model/CleaningTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **cleaningtasksPost**
> \Swagger\Client\Model\CleaningTask cleaningtasksPost($body)

Erstelle einen neuen Task

Task

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\CleaningTaskApi();
$body = new \Swagger\Client\Model\CleaningTask(); // \Swagger\Client\Model\CleaningTask | 

try {
    $result = $api_instance->cleaningtasksPost($body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling CleaningTaskApi->cleaningtasksPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\CleaningTask**](../Model/\Swagger\Client\Model\CleaningTask.md)|  |

### Return type

[**\Swagger\Client\Model\CleaningTask**](../Model/CleaningTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **cleaningtasksPut**
> cleaningtasksPut($body)

Eine reihe CleaningTask bearbeiten

Irgendwas

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\CleaningTaskApi();
$body = array(new CleaningTask()); // \Swagger\Client\Model\CleaningTask[] | 

try {
    $api_instance->cleaningtasksPut($body);
} catch (Exception $e) {
    echo 'Exception when calling CleaningTaskApi->cleaningtasksPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\CleaningTask[]**](../Model/CleaningTask.md)|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **cleaningtasksSizeGet**
> int cleaningtasksSizeGet($new_tasks, $user_name)

Anzahl der Tasks

Anzahl der Tasks

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\CleaningTaskApi();
$new_tasks = true; // bool | Nur die anzahl an neuen Tasks haben seit der letzten abfrage
$user_name = new \Swagger\Client\Model\UserName9(); // \Swagger\Client\Model\UserName9 | 

try {
    $result = $api_instance->cleaningtasksSizeGet($new_tasks, $user_name);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling CleaningTaskApi->cleaningtasksSizeGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **new_tasks** | **bool**| Nur die anzahl an neuen Tasks haben seit der letzten abfrage |
 **user_name** | [**\Swagger\Client\Model\UserName9**](../Model/\Swagger\Client\Model\UserName9.md)|  |

### Return type

**int**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **cleaningtasksTaskidDelete**
> cleaningtasksTaskidDelete($taskid)

löschen

löschen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\CleaningTaskApi();
$taskid = 789; // int | 

try {
    $api_instance->cleaningtasksTaskidDelete($taskid);
} catch (Exception $e) {
    echo 'Exception when calling CleaningTaskApi->cleaningtasksTaskidDelete: ', $e->getMessage(), PHP_EOL;
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

# **cleaningtasksTaskidGet**
> \Swagger\Client\Model\CleaningTask cleaningtasksTaskidGet($taskid)

Erhalte einen einzelnen Task

Einzelner Task

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\CleaningTaskApi();
$taskid = 789; // int | 

try {
    $result = $api_instance->cleaningtasksTaskidGet($taskid);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling CleaningTaskApi->cleaningtasksTaskidGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskid** | **int**|  |

### Return type

[**\Swagger\Client\Model\CleaningTask**](../Model/CleaningTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **cleaningtasksTaskidPut**
> cleaningtasksTaskidPut($taskid, $body)

Update einen Task

Eine einzelne Task neue Werte zuweisen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\CleaningTaskApi();
$taskid = 789; // int | 
$body = new \Swagger\Client\Model\CleaningTask(); // \Swagger\Client\Model\CleaningTask | 

try {
    $api_instance->cleaningtasksTaskidPut($taskid, $body);
} catch (Exception $e) {
    echo 'Exception when calling CleaningTaskApi->cleaningtasksTaskidPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **taskid** | **int**|  |
 **body** | [**\Swagger\Client\Model\CleaningTask**](../Model/\Swagger\Client\Model\CleaningTask.md)|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **locationsCleaningtasksGet**
> \Swagger\Client\Model\CleaningTask[] locationsCleaningtasksGet($floor, $building, $room)

Liefert alle CleaningTask zu einer örtlichen beschreibung

Was oben steht

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\CleaningTaskApi();
$floor = 56; // int | 
$building = 56; // int | 
$room = 56; // int | 

try {
    $result = $api_instance->locationsCleaningtasksGet($floor, $building, $room);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling CleaningTaskApi->locationsCleaningtasksGet: ', $e->getMessage(), PHP_EOL;
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

[**\Swagger\Client\Model\CleaningTask[]**](../Model/CleaningTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

