# Swagger\Client\BeaconObjektApi

All URIs are relative to *http://localhost:8080/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**beaconObjectsBeaconObjectIdMaintainancetasksGet**](BeaconObjektApi.md#beaconObjectsBeaconObjectIdMaintainancetasksGet) | **GET** /beaconObjects/{beaconObjectId}/maintainancetasks | Alle Maintainancetasks die zu diesem BeaconObjekt gehören


# **beaconObjectsBeaconObjectIdMaintainancetasksGet**
> \Swagger\Client\Model\MaintainanceTask[] beaconObjectsBeaconObjectIdMaintainancetasksGet($beacon_object_id)

Alle Maintainancetasks die zu diesem BeaconObjekt gehören

Alle Maintainancetasks die zu diesem BeaconObjekt gehören

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\BeaconObjektApi();
$beacon_object_id = 789; // int | 

try {
    $result = $api_instance->beaconObjectsBeaconObjectIdMaintainancetasksGet($beacon_object_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling BeaconObjektApi->beaconObjectsBeaconObjectIdMaintainancetasksGet: ', $e->getMessage(), PHP_EOL;
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

