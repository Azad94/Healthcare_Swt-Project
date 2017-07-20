# Swagger\Client\BeaconApi

All URIs are relative to *http://localhost:8080/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**beaconsBeaconIdDelete**](BeaconApi.md#beaconsBeaconIdDelete) | **DELETE** /beacons/{beaconId} | löschen
[**beaconsBeaconIdGet**](BeaconApi.md#beaconsBeaconIdGet) | **GET** /beacons/{beaconId} | Einen bestimmten Beacon erhalten
[**beaconsBeaconIdPut**](BeaconApi.md#beaconsBeaconIdPut) | **PUT** /beacons/{beaconId} | Einen Beacon bearbeiten
[**beaconsGet**](BeaconApi.md#beaconsGet) | **GET** /beacons | Erhalte alle Beacons
[**beaconsPost**](BeaconApi.md#beaconsPost) | **POST** /beacons | Erstelle neuen Beacon als rückgabe gibt es den fertigen beacon mit uuid


# **beaconsBeaconIdDelete**
> beaconsBeaconIdDelete($beacon_id)

löschen

löschen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconApi();
$beacon_id = "beacon_id_example"; // string | 

try {
    $api_instance->beaconsBeaconIdDelete($beacon_id);
} catch (Exception $e) {
    echo 'Exception when calling BeaconApi->beaconsBeaconIdDelete: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **beacon_id** | **string**|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **beaconsBeaconIdGet**
> \Swagger\Client\Model\Beacon beaconsBeaconIdGet($beacon_id)

Einen bestimmten Beacon erhalten

Einen Bestimmten beacon erhalten

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconApi();
$beacon_id = "beacon_id_example"; // string | 

try {
    $result = $api_instance->beaconsBeaconIdGet($beacon_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling BeaconApi->beaconsBeaconIdGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **beacon_id** | **string**|  |

### Return type

[**\Swagger\Client\Model\Beacon**](../Model/Beacon.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **beaconsBeaconIdPut**
> beaconsBeaconIdPut($beacon_id, $body)

Einen Beacon bearbeiten

Einene Beacon bearbeiten

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconApi();
$beacon_id = "beacon_id_example"; // string | 
$body = new \Swagger\Client\Model\Beacon(); // \Swagger\Client\Model\Beacon | 

try {
    $api_instance->beaconsBeaconIdPut($beacon_id, $body);
} catch (Exception $e) {
    echo 'Exception when calling BeaconApi->beaconsBeaconIdPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **beacon_id** | **string**|  |
 **body** | [**\Swagger\Client\Model\Beacon**](../Model/\Swagger\Client\Model\Beacon.md)|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **beaconsGet**
> \Swagger\Client\Model\Beacon[] beaconsGet($no_beacon_object)

Erhalte alle Beacons

Alle Beacons

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconApi();
$no_beacon_object = false; // bool | Liefert alle beacons die an keinem BeaconObject befestigt sind

try {
    $result = $api_instance->beaconsGet($no_beacon_object);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling BeaconApi->beaconsGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **no_beacon_object** | **bool**| Liefert alle beacons die an keinem BeaconObject befestigt sind | [optional] [default to false]

### Return type

[**\Swagger\Client\Model\Beacon[]**](../Model/Beacon.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **beaconsPost**
> \Swagger\Client\Model\Beacon beaconsPost($body)

Erstelle neuen Beacon als rückgabe gibt es den fertigen beacon mit uuid

Neuen Beacon erstelen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconApi();
$body = new \Swagger\Client\Model\Body3(); // \Swagger\Client\Model\Body3 | 

try {
    $result = $api_instance->beaconsPost($body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling BeaconApi->beaconsPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\Body3**](../Model/\Swagger\Client\Model\Body3.md)|  |

### Return type

[**\Swagger\Client\Model\Beacon**](../Model/Beacon.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

