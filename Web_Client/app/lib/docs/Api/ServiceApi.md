# Swagger\Client\ServiceApi

All URIs are relative to *http://localhost:8080/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**serviceGet**](ServiceApi.md#serviceGet) | **GET** /service | Erhalte einen task und erstelle ihn für eine Service anfrage


# **serviceGet**
> \Swagger\Client\Model\TransportTask[] serviceGet($body)

Erhalte einen task und erstelle ihn für eine Service anfrage

Duch einen service wird ein Task erstellt anhand des Service objekts wird entschieden um was für einen TAsk es sich handelt

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\ServiceApi();
$body = new \Swagger\Client\Model\Service(); // \Swagger\Client\Model\Service | 

try {
    $result = $api_instance->serviceGet($body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling ServiceApi->serviceGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\Service**](../Model/\Swagger\Client\Model\Service.md)|  |

### Return type

[**\Swagger\Client\Model\TransportTask[]**](../Model/TransportTask.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

