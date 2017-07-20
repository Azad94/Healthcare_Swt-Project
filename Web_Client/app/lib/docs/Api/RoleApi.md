# Swagger\Client\RoleApi

All URIs are relative to *http://localhost:8080/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**rolesGet**](RoleApi.md#rolesGet) | **GET** /roles | Alle rollen
[**rolesPost**](RoleApi.md#rolesPost) | **POST** /roles | Erstelle neue Role
[**rolesRoleIdDelete**](RoleApi.md#rolesRoleIdDelete) | **DELETE** /roles/{roleId} | löschen
[**rolesRoleIdGet**](RoleApi.md#rolesRoleIdGet) | **GET** /roles/{roleId} | Einzelne role
[**rolesRoleIdPut**](RoleApi.md#rolesRoleIdPut) | **PUT** /roles/{roleId} | Role bearbeiten


# **rolesGet**
> \Swagger\Client\Model\Role[] rolesGet()

Alle rollen

Alle rollen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\RoleApi();

try {
    $result = $api_instance->rolesGet();
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling RoleApi->rolesGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**\Swagger\Client\Model\Role[]**](../Model/Role.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **rolesPost**
> \Swagger\Client\Model\Role rolesPost($body)

Erstelle neue Role

Neue Role

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\RoleApi();
$body = new \Swagger\Client\Model\Role(); // \Swagger\Client\Model\Role | 

try {
    $result = $api_instance->rolesPost($body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling RoleApi->rolesPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**\Swagger\Client\Model\Role**](../Model/\Swagger\Client\Model\Role.md)|  |

### Return type

[**\Swagger\Client\Model\Role**](../Model/Role.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **rolesRoleIdDelete**
> rolesRoleIdDelete($role_id)

löschen

löschen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\RoleApi();
$role_id = "role_id_example"; // string | 

try {
    $api_instance->rolesRoleIdDelete($role_id);
} catch (Exception $e) {
    echo 'Exception when calling RoleApi->rolesRoleIdDelete: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **role_id** | **string**|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **rolesRoleIdGet**
> \Swagger\Client\Model\Role rolesRoleIdGet($role_id)

Einzelne role

Einzelne Role

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\RoleApi();
$role_id = "role_id_example"; // string | 

try {
    $result = $api_instance->rolesRoleIdGet($role_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling RoleApi->rolesRoleIdGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **role_id** | **string**|  |

### Return type

[**\Swagger\Client\Model\Role**](../Model/Role.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **rolesRoleIdPut**
> rolesRoleIdPut($role_id, $body)

Role bearbeiten

Role Bearbeiten

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\RoleApi();
$role_id = "role_id_example"; // string | 
$body = new \Swagger\Client\Model\Role(); // \Swagger\Client\Model\Role | 

try {
    $api_instance->rolesRoleIdPut($role_id, $body);
} catch (Exception $e) {
    echo 'Exception when calling RoleApi->rolesRoleIdPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **role_id** | **string**|  |
 **body** | [**\Swagger\Client\Model\Role**](../Model/\Swagger\Client\Model\Role.md)|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

