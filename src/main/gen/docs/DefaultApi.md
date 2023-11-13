# DefaultApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addStudentPost**](DefaultApi.md#addStudentPost) | **POST** /add/student | Добавление студента |
| [**deleteStudentIdDelete**](DefaultApi.md#deleteStudentIdDelete) | **DELETE** /delete/student/{id} | Удаление студента по ID |
| [**getAverageGradesGroupIdGet**](DefaultApi.md#getAverageGradesGroupIdGet) | **GET** /get/average_grades/{groupId} | Получение средних оценок каждого ученика в указанном классе |
| [**getStudentGradesIdGet**](DefaultApi.md#getStudentGradesIdGet) | **GET** /get/student/grades/{id} | Получение данных студента по ID |
| [**updateGradeStudentIdSubjectPut**](DefaultApi.md#updateGradeStudentIdSubjectPut) | **PUT** /update/grade/{studentId}/{subject} | Редактирование оценки студента по определенному предмету |


<a name="addStudentPost"></a>
# **addStudentPost**
> Student addStudentPost(lastName, firstName, age, groupId)

Добавление студента

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String lastName = "lastName_example"; // String | Фамилия студента
    String firstName = "firstName_example"; // String | Имя студента
    Integer age = 56; // Integer | Возраст студента
    Integer groupId = 56; // Integer | ID класса студента
    try {
      Student result = apiInstance.addStudentPost(lastName, firstName, age, groupId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#addStudentPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **lastName** | **String**| Фамилия студента | |
| **firstName** | **String**| Имя студента | |
| **age** | **Integer**| Возраст студента | |
| **groupId** | **Integer**| ID класса студента | |

### Return type

[**Student**](Student.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Успешный запрос |  -  |

<a name="deleteStudentIdDelete"></a>
# **deleteStudentIdDelete**
> String deleteStudentIdDelete(id)

Удаление студента по ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | ID студента
    try {
      String result = apiInstance.deleteStudentIdDelete(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#deleteStudentIdDelete");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**| ID студента | |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Успешный запрос |  -  |
| **404** | Студент не найден |  -  |

<a name="getAverageGradesGroupIdGet"></a>
# **getAverageGradesGroupIdGet**
> List&lt;StudentDTO&gt; getAverageGradesGroupIdGet(groupId)

Получение средних оценок каждого ученика в указанном классе

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer groupId = 56; // Integer | ID класса
    try {
      List<StudentDTO> result = apiInstance.getAverageGradesGroupIdGet(groupId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getAverageGradesGroupIdGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **groupId** | **Integer**| ID класса | |

### Return type

[**List&lt;StudentDTO&gt;**](StudentDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Успешный запрос |  -  |
| **404** | Ученики не найдены |  -  |

<a name="getStudentGradesIdGet"></a>
# **getStudentGradesIdGet**
> Grade getStudentGradesIdGet(id)

Получение данных студента по ID

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer id = 56; // Integer | ID студента
    try {
      Grade result = apiInstance.getStudentGradesIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#getStudentGradesIdGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **Integer**| ID студента | |

### Return type

[**Grade**](Grade.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Успешный запрос |  -  |
| **404** | Студент не найден |  -  |

<a name="updateGradeStudentIdSubjectPut"></a>
# **updateGradeStudentIdSubjectPut**
> String updateGradeStudentIdSubjectPut(studentId, subject, newGrade)

Редактирование оценки студента по определенному предмету

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Integer studentId = 56; // Integer | ID студента
    String subject = "subject_example"; // String | Предмет
    Integer newGrade = 56; // Integer | Новая оценка
    try {
      String result = apiInstance.updateGradeStudentIdSubjectPut(studentId, subject, newGrade);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#updateGradeStudentIdSubjectPut");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **studentId** | **Integer**| ID студента | |
| **subject** | **String**| Предмет | |
| **newGrade** | **Integer**| Новая оценка | |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Успешный запрос |  -  |
| **400** | Некорректный запрос |  -  |
| **404** | Студент не найден |  -  |

