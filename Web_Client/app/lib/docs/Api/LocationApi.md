# Swagger\Client\LocationApi

All URIs are relative to *http://localhost:8080/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**locationsCleaningtasksGet**](LocationApi.md#locationsCleaningtasksGet) | **GET** /locations/cleaningtasks | Liefert alle CleaningTask zu einer örtlichen beschreibung
[**locationsGet**](LocationApi.md#locationsGet) | **GET** /locations | Alle Lokationen
[**locationsLocationIdDelete**](LocationApi.md#locationsLocationIdDelete) | **DELETE** /locations/{locationId} | löschen
[**locationsLocationIdGet**](LocationApi.md#locationsLocationIdGet) | **GET** /locations/{locationId} | Erhalte eine Lokation
[**locationsLocationIdPut**](LocationApi.md#locationsLocationIdPut) | **PUT** /locations/{locationId} | Eine Lokation bearbeiten
[**locationsPost**](LocationApi.md#locationsPost) | **POST** /locations | Erstelle eine neue Location
[**locationsTransporttasksGet**](LocationApi.md#locationsTransporttasksGet) | **GET** /locations/transporttasks | Liefert alle TransportTask zu einer örtlichen beschreibung


# **locationsCleaningtasksGet**
> \Swagger\Client\Model\CleaningTask[] locationsCleaningtasksGet($floor, $building, $room)

Liefert alle CleaningTask zu einer örtlichen beschreibung

Was oben steht

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\LocationApi();
$floor = 56; // int | 
$building = 56; // int | 
$room = 56; // int | 

try {
    $result = $api_instance->locationsCleaningtasksGet($floor, $building, $room);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling LocationApi->locationsCleaningtasksGet: ', $e->getMessage(), PHP_EOL;
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

# **locationsGet**
> \Swagger\Client\Model\Location[] locationsGet($floor, $building, $room)

Alle Lokationen

Alle Lokationen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\LocationApi();
$floor = 56; // int | Liefert alle locations mit diesem floor
$building = 56; // int | Liefert alle location mit diesem Building
$room = 56; // int | Alle Location mit diesem Raum

try {
    $result = $api_instance->locationsGet($floor, $building, $room);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling LocationApi->locationsGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **floor** | **int**| Liefert alle locations mit diesem floor | [optional]
 **building** | **int**| Liefert alle location mit diesem Building | [optional]
 **room** | **int**| Alle Location mit diesem Raum | [optional]

### Return type

[**\Swagger\Client\Model\Location[]**](../Model/Location.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **locationsLocationIdDelete**
> locationsLocationIdDelete($location_id)

löschen

löschen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\LocationApi();
$location_id = 789; // int | 

try {
    $api_instance->locationsLocationIdDelete($location_id);
} catch (Exception $e) {
    echo 'Exception when calling LocationApi->locationsLocationIdDelete: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location_id** | **int**|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **locationsLocationIdGet**
> \Swagger\Client\Model\Location locationsLocationIdGet($location_id)

Erhalte eine Lokation

Eine Lokation

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\LocationApi();
$location_id = 789; // int | 

try {
    $result = $api_instance->locationsLocationIdGet($location_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling LocationApi->locationsLocationIdGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location_id** | **int**|  |

### Return type

[**\Swagger\Client\Model\Location**](../Model/Location.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **locationsLocationIdPut**
> locationsLocationIdPut($location_id, $body)

Eine Lokation bearbeiten

Eine Lokation bearbeiten

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\LocationApi();
$location_id = 789; // int | 
$body = new \Swagger\Client\Model\Location(); // \Swagger\Client\Model\Location | 

try {
    $api_instance->locationsLocationIdPut($location_id, $body);
} catch (Exception $e) {
    echo 'Exception when calling LocationApi->locationsLocationIdPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **location_id** | **int**|  |
 **body** | [**\Swagger\Client\Model\Location**](../Model/\Swagger\Client\Model\Location.md)|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **locationsPost**
> \Swagger\Client\Model\Location locationsPost($body)

Erstelle eine neue Location

Neue Location

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\LocationApi();
$body = new \Swagger\Client\Model\Location(); // \Swagger\Client\Model\Location | 

try {
    $result = $api_instance->locationsPost($body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling LocationApi->locationsPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\Location**](../Model/\Swagger\Client\Model\Location.md)|  |

### Return type

[**\Swagger\Client\Model\Location**](../Model/Location.md)

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

$api_instance = new Swagger\Client\Api\LocationApi();
$floor = 56; // int | 
$building = 56; // int | 
$room = 56; // int | 

try {
    $result = $api_instance->locationsTransporttasksGet($floor, $building, $room);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling LocationApi->locationsTransporttasksGet: ', $e->getMessage(), PHP_EOL;
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

