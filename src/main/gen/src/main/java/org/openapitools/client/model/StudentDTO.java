/*
 * Student's Grades API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 2.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openapitools.client.JSON;

/**
 * StudentDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-11-13T03:10:41.285607200+03:00[Europe/Moscow]")
public class StudentDTO {
  public static final String SERIALIZED_NAME_FAMILY = "family";
  @SerializedName(SERIALIZED_NAME_FAMILY)
  private String family;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_AGE = "age";
  @SerializedName(SERIALIZED_NAME_AGE)
  private Integer age;

  public static final String SERIALIZED_NAME_GROUP_ID = "groupId";
  @SerializedName(SERIALIZED_NAME_GROUP_ID)
  private Integer groupId;

  public static final String SERIALIZED_NAME_AVERAGE_GRADE = "averageGrade";
  @SerializedName(SERIALIZED_NAME_AVERAGE_GRADE)
  private BigDecimal averageGrade;

  public StudentDTO() {
  }

  public StudentDTO family(String family) {
    
    this.family = family;
    return this;
  }

   /**
   * Get family
   * @return family
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getFamily() {
    return family;
  }


  public void setFamily(String family) {
    this.family = family;
  }


  public StudentDTO name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public StudentDTO age(Integer age) {
    
    this.age = age;
    return this;
  }

   /**
   * Get age
   * @return age
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getAge() {
    return age;
  }


  public void setAge(Integer age) {
    this.age = age;
  }


  public StudentDTO groupId(Integer groupId) {
    
    this.groupId = groupId;
    return this;
  }

   /**
   * Get groupId
   * @return groupId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getGroupId() {
    return groupId;
  }


  public void setGroupId(Integer groupId) {
    this.groupId = groupId;
  }


  public StudentDTO averageGrade(BigDecimal averageGrade) {
    
    this.averageGrade = averageGrade;
    return this;
  }

   /**
   * Get averageGrade
   * @return averageGrade
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public BigDecimal getAverageGrade() {
    return averageGrade;
  }


  public void setAverageGrade(BigDecimal averageGrade) {
    this.averageGrade = averageGrade;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StudentDTO studentDTO = (StudentDTO) o;
    return Objects.equals(this.family, studentDTO.family) &&
        Objects.equals(this.name, studentDTO.name) &&
        Objects.equals(this.age, studentDTO.age) &&
        Objects.equals(this.groupId, studentDTO.groupId) &&
        Objects.equals(this.averageGrade, studentDTO.averageGrade);
  }

  @Override
  public int hashCode() {
    return Objects.hash(family, name, age, groupId, averageGrade);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StudentDTO {\n");
    sb.append("    family: ").append(toIndentedString(family)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    age: ").append(toIndentedString(age)).append("\n");
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
    sb.append("    averageGrade: ").append(toIndentedString(averageGrade)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("family");
    openapiFields.add("name");
    openapiFields.add("age");
    openapiFields.add("groupId");
    openapiFields.add("averageGrade");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to StudentDTO
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (StudentDTO.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in StudentDTO is not found in the empty JSON string", StudentDTO.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!StudentDTO.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `StudentDTO` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      if ((jsonObj.get("family") != null && !jsonObj.get("family").isJsonNull()) && !jsonObj.get("family").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `family` to be a primitive type in the JSON string but got `%s`", jsonObj.get("family").toString()));
      }
      if ((jsonObj.get("name") != null && !jsonObj.get("name").isJsonNull()) && !jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!StudentDTO.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'StudentDTO' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<StudentDTO> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(StudentDTO.class));

       return (TypeAdapter<T>) new TypeAdapter<StudentDTO>() {
           @Override
           public void write(JsonWriter out, StudentDTO value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public StudentDTO read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of StudentDTO given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of StudentDTO
  * @throws IOException if the JSON string is invalid with respect to StudentDTO
  */
  public static StudentDTO fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, StudentDTO.class);
  }

 /**
  * Convert an instance of StudentDTO to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

