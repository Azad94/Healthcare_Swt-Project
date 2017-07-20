# Swagger\Client\UserApi

All URIs are relative to *http://localhost:8080/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**usersGet**](UserApi.md#usersGet) | **GET** /users | Liste aller User
[**usersPost**](UserApi.md#usersPost) | **POST** /users | Erstelle einen neuen user
[**usersUserIdDelete**](UserApi.md#usersUserIdDelete) | **DELETE** /users/{userId} | löschen
[**usersUserIdGet**](UserApi.md#usersUserIdGet) | **GET** /users/{userId} | Erhalte informationen über einen User
[**usersUserIdPut**](UserApi.md#usersUserIdPut) | **PUT** /users/{userId} | Einen User bearbeiten


# **usersGet**
> \Swagger\Client\Model\User[] usersGet()

Liste aller User

Alle User

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\UserApi();

try {
    $result = $api_instance->usersGet();
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling UserApi->usersGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**\Swagger\Client\Model\User[]**](../Model/User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **usersPost**
> \Swagger\Client\Model\User usersPost($body)

Erstelle einen neuen user

Einen Neuen User erstellen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\UserApi();
$body = new \Swagger\Client\Model\User(); // \Swagger\Client\Model\User | 

try {
    $result = $api_instance->usersPost($body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling UserApi->usersPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\User**](../Model/\Swagger\Client\Model\User.md)|  |

### Return type

[**\Swagger\Client\Model\User**](../Model/User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **usersUserIdDelete**
> usersUserIdDelete($user_id)

löschen

löschen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\UserApi();
$user_id = "user_id_example"; // string | 

try {
    $api_instance->usersUserIdDelete($user_id);
} catch (Exception $e) {
    echo 'Exception when calling UserApi->usersUserIdDelete: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **user_id** | **string**|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **usersUserIdGet**
> \Swagger\Client\Model\User usersUserIdGet($user_id)

Erhalte informationen über einen User

Ein User

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\UserApi();
$user_id = "user_id_example"; // string | 

try {
    $result = $api_instance->usersUserIdGet($user_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling UserApi->usersUserIdGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **user_id** | **string**|  |

### Return type

[**\Swagger\Client\Model\User**](../Model/User.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **usersUserIdPut**
> usersUserIdPut($user_id, $body)

Einen User bearbeiten

Einen User bearbeiten

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\UserApi();
$user_id = "user_id_example"; // string | 
$body = new \Swagger\Client\Model\User(); // \Swagger\Client\Model\User | 

try {
    $api_instance->usersUserIdPut($user_id, $body);
} catch (Exception $e) {
    echo 'Exception when calling UserApi->usersUserIdPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **user_id** | **string**|  |
 **body** | [**\Swagger\Client\Model\User**](../Model/\Swagger\Client\Model\User.md)|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

