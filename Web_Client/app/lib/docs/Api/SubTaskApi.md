# Swagger\Client\SubTaskApi

All URIs are relative to *http://localhost:8080/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**maintainancetasksMaintainancetaskIdSubtaskscheckboxGet**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtaskscheckboxGet) | **GET** /maintainancetasks/{maintainancetaskId}/subtaskscheckbox | Erhalte alle subtasks zu einem MaintainanceTask
[**maintainancetasksMaintainancetaskIdSubtaskscheckboxPost**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtaskscheckboxPost) | **POST** /maintainancetasks/{maintainancetaskId}/subtaskscheckbox | Erstelle einen neuen SubTask
[**maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdDelete**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdDelete) | **DELETE** /maintainancetasks/{maintainancetaskId}/subtaskscheckbox/{subtaskId} | Lösche einen Subtask
[**maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdPut**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdPut) | **PUT** /maintainancetasks/{maintainancetaskId}/subtaskscheckbox/{subtaskId} | Subtask bearbeiten
[**maintainancetasksMaintainancetaskIdSubtasksimageGet**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtasksimageGet) | **GET** /maintainancetasks/{maintainancetaskId}/subtasksimage | Erhalte alle subtasks zu einem MaintainanceTask
[**maintainancetasksMaintainancetaskIdSubtasksimagePost**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtasksimagePost) | **POST** /maintainancetasks/{maintainancetaskId}/subtasksimage | Erstelle einen neuen SubTask
[**maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdDelete**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdDelete) | **DELETE** /maintainancetasks/{maintainancetaskId}/subtasksimage/{subtaskId} | Lösche einen Subtask
[**maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdPut**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdPut) | **PUT** /maintainancetasks/{maintainancetaskId}/subtasksimage/{subtaskId} | Subtask bearbeiten
[**maintainancetasksMaintainancetaskIdSubtaskssliderGet**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtaskssliderGet) | **GET** /maintainancetasks/{maintainancetaskId}/subtasksslider | Erhalte alle subtasks zu einem MaintainanceTask
[**maintainancetasksMaintainancetaskIdSubtaskssliderPost**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtaskssliderPost) | **POST** /maintainancetasks/{maintainancetaskId}/subtasksslider | Erstelle einen neuen SubTask
[**maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdDelete**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdDelete) | **DELETE** /maintainancetasks/{maintainancetaskId}/subtasksslider/{subtaskId} | Lösche einen Subtask
[**maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdPut**](SubTaskApi.md#maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdPut) | **PUT** /maintainancetasks/{maintainancetaskId}/subtasksslider/{subtaskId} | Subtask bearbeiten


# **maintainancetasksMaintainancetaskIdSubtaskscheckboxGet**
> \Swagger\Client\Model\SubTaskCheckbox[] maintainancetasksMaintainancetaskIdSubtaskscheckboxGet($maintainancetask_id)

Erhalte alle subtasks zu einem MaintainanceTask

Erhale alle subtasks

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$maintainancetask_id = 789; // int | 

try {
    $result = $api_instance->maintainancetasksMaintainancetaskIdSubtaskscheckboxGet($maintainancetask_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtaskscheckboxGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **maintainancetask_id** | **int**|  |

### Return type

[**\Swagger\Client\Model\SubTaskCheckbox[]**](../Model/SubTaskCheckbox.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksMaintainancetaskIdSubtaskscheckboxPost**
> \Swagger\Client\Model\SubTaskCheckbox maintainancetasksMaintainancetaskIdSubtaskscheckboxPost($maintainancetask_id, $body)

Erstelle einen neuen SubTask

Einen subtask zu einem MaintainanceTask erstellen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$maintainancetask_id = 789; // int | 
$body = new \Swagger\Client\Model\SubTaskCheckbox(); // \Swagger\Client\Model\SubTaskCheckbox | 

try {
    $result = $api_instance->maintainancetasksMaintainancetaskIdSubtaskscheckboxPost($maintainancetask_id, $body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtaskscheckboxPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **maintainancetask_id** | **int**|  |
 **body** | [**\Swagger\Client\Model\SubTaskCheckbox**](../Model/\Swagger\Client\Model\SubTaskCheckbox.md)|  |

### Return type

[**\Swagger\Client\Model\SubTaskCheckbox**](../Model/SubTaskCheckbox.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdDelete**
> maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdDelete($subtask_id, $maintainancetask_id)

Lösche einen Subtask

Einen Subtask endgültig löschen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$subtask_id = 789; // int | 
$maintainancetask_id = 789; // int | 

try {
    $api_instance->maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdDelete($subtask_id, $maintainancetask_id);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdDelete: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subtask_id** | **int**|  |
 **maintainancetask_id** | **int**|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdPut**
> \Swagger\Client\Model\SubTaskCheckbox maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdPut($subtask_id, $maintainancetask_id, $body)

Subtask bearbeiten

SubTask bearbeiten

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$subtask_id = 789; // int | 
$maintainancetask_id = 789; // int | 
$body = new \Swagger\Client\Model\AbstractSubTask(); // \Swagger\Client\Model\AbstractSubTask | 

try {
    $result = $api_instance->maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdPut($subtask_id, $maintainancetask_id, $body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtaskscheckboxSubtaskIdPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subtask_id** | **int**|  |
 **maintainancetask_id** | **int**|  |
 **body** | [**\Swagger\Client\Model\AbstractSubTask**](../Model/\Swagger\Client\Model\AbstractSubTask.md)|  |

### Return type

[**\Swagger\Client\Model\SubTaskCheckbox**](../Model/SubTaskCheckbox.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksMaintainancetaskIdSubtasksimageGet**
> \Swagger\Client\Model\SubTaskImage[] maintainancetasksMaintainancetaskIdSubtasksimageGet($maintainancetask_id)

Erhalte alle subtasks zu einem MaintainanceTask

Erhale alle subtasks

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$maintainancetask_id = 789; // int | 

try {
    $result = $api_instance->maintainancetasksMaintainancetaskIdSubtasksimageGet($maintainancetask_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtasksimageGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **maintainancetask_id** | **int**|  |

### Return type

[**\Swagger\Client\Model\SubTaskImage[]**](../Model/SubTaskImage.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksMaintainancetaskIdSubtasksimagePost**
> \Swagger\Client\Model\SubTaskImage maintainancetasksMaintainancetaskIdSubtasksimagePost($maintainancetask_id, $body)

Erstelle einen neuen SubTask

Einen subtask zu einem MaintainanceTask erstellen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$maintainancetask_id = 789; // int | 
$body = new \Swagger\Client\Model\SubTaskImage(); // \Swagger\Client\Model\SubTaskImage | 

try {
    $result = $api_instance->maintainancetasksMaintainancetaskIdSubtasksimagePost($maintainancetask_id, $body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtasksimagePost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **maintainancetask_id** | **int**|  |
 **body** | [**\Swagger\Client\Model\SubTaskImage**](../Model/\Swagger\Client\Model\SubTaskImage.md)|  |

### Return type

[**\Swagger\Client\Model\SubTaskImage**](../Model/SubTaskImage.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdDelete**
> maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdDelete($subtask_id, $maintainancetask_id)

Lösche einen Subtask

Einen Subtask endgültig löschen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$subtask_id = 789; // int | 
$maintainancetask_id = 789; // int | 

try {
    $api_instance->maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdDelete($subtask_id, $maintainancetask_id);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdDelete: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subtask_id** | **int**|  |
 **maintainancetask_id** | **int**|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdPut**
> \Swagger\Client\Model\SubTaskImage maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdPut($subtask_id, $maintainancetask_id, $body)

Subtask bearbeiten

SubTask bearbeiten

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$subtask_id = 789; // int | 
$maintainancetask_id = 789; // int | 
$body = new \Swagger\Client\Model\SubTaskImage(); // \Swagger\Client\Model\SubTaskImage | 

try {
    $result = $api_instance->maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdPut($subtask_id, $maintainancetask_id, $body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtasksimageSubtaskIdPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subtask_id** | **int**|  |
 **maintainancetask_id** | **int**|  |
 **body** | [**\Swagger\Client\Model\SubTaskImage**](../Model/\Swagger\Client\Model\SubTaskImage.md)|  |

### Return type

[**\Swagger\Client\Model\SubTaskImage**](../Model/SubTaskImage.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksMaintainancetaskIdSubtaskssliderGet**
> \Swagger\Client\Model\SubTaskSlider[] maintainancetasksMaintainancetaskIdSubtaskssliderGet($maintainancetask_id)

Erhalte alle subtasks zu einem MaintainanceTask

Erhale alle subtasks

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$maintainancetask_id = 789; // int | 

try {
    $result = $api_instance->maintainancetasksMaintainancetaskIdSubtaskssliderGet($maintainancetask_id);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtaskssliderGet: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **maintainancetask_id** | **int**|  |

### Return type

[**\Swagger\Client\Model\SubTaskSlider[]**](../Model/SubTaskSlider.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksMaintainancetaskIdSubtaskssliderPost**
> \Swagger\Client\Model\SubTaskSlider maintainancetasksMaintainancetaskIdSubtaskssliderPost($maintainancetask_id, $body)

Erstelle einen neuen SubTask

Einen subtask zu einem MaintainanceTask erstellen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$maintainancetask_id = 789; // int | 
$body = new \Swagger\Client\Model\SubTaskSlider(); // \Swagger\Client\Model\SubTaskSlider | 

try {
    $result = $api_instance->maintainancetasksMaintainancetaskIdSubtaskssliderPost($maintainancetask_id, $body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtaskssliderPost: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **maintainancetask_id** | **int**|  |
 **body** | [**\Swagger\Client\Model\SubTaskSlider**](../Model/\Swagger\Client\Model\SubTaskSlider.md)|  |

### Return type

[**\Swagger\Client\Model\SubTaskSlider**](../Model/SubTaskSlider.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdDelete**
> maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdDelete($subtask_id, $maintainancetask_id)

Lösche einen Subtask

Einen Subtask endgültig löschen

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$subtask_id = 789; // int | 
$maintainancetask_id = 789; // int | 

try {
    $api_instance->maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdDelete($subtask_id, $maintainancetask_id);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdDelete: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subtask_id** | **int**|  |
 **maintainancetask_id** | **int**|  |

### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

# **maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdPut**
> \Swagger\Client\Model\SubTaskSlider maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdPut($subtask_id, $maintainancetask_id, $body)

Subtask bearbeiten

SubTask bearbeiten

### Example
```php
<?php
require_once(__DIR__ . '/vendor/autoload.php');

$api_instance = new Swagger\Client\Api\SubTaskApi();
$subtask_id = 789; // int | 
$maintainancetask_id = 789; // int | 
$body = new \Swagger\Client\Model\SubTaskSlider(); // \Swagger\Client\Model\SubTaskSlider | 

try {
    $result = $api_instance->maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdPut($subtask_id, $maintainancetask_id, $body);
    print_r($result);
} catch (Exception $e) {
    echo 'Exception when calling SubTaskApi->maintainancetasksMaintainancetaskIdSubtaskssliderSubtaskIdPut: ', $e->getMessage(), PHP_EOL;
}
?>
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subtask_id** | **int**|  |
 **maintainancetask_id** | **int**|  |
 **body** | [**\Swagger\Client\Model\SubTaskSlider**](../Model/\Swagger\Client\Model\SubTaskSlider.md)|  |

### Return type

[**\Swagger\Client\Model\SubTaskSlider**](../Model/SubTaskSlider.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../../README.md#documentation-for-api-endpoints) [[Back to Model list]](../../README.md#documentation-for-models) [[Back to README]](../../README.md)

