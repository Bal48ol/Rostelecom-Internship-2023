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
import org.openapitools.client.model.Student;

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
 * Grade
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-11-13T03:10:41.285607200+03:00[Europe/Moscow]")
public class Grade {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_STUDENT = "student";
  @SerializedName(SERIALIZED_NAME_STUDENT)
  private Student student;

  public static final String SERIALIZED_NAME_PHYSICS = "physics";
  @SerializedName(SERIALIZED_NAME_PHYSICS)
  private Integer physics;

  public static final String SERIALIZED_NAME_MATHEMATICS = "mathematics";
  @SerializedName(SERIALIZED_NAME_MATHEMATICS)
  private Integer mathematics;

  public static final String SERIALIZED_NAME_RUS = "rus";
  @SerializedName(SERIALIZED_NAME_RUS)
  private Integer rus;

  public static final String SERIALIZED_NAME_LITERATURE = "literature";
  @SerializedName(SERIALIZED_NAME_LITERATURE)
  private Integer literature;

  public static final String SERIALIZED_NAME_GEOMETRY = "geometry";
  @SerializedName(SERIALIZED_NAME_GEOMETRY)
  private Integer geometry;

  public static final String SERIALIZED_NAME_INFORMATICS = "informatics";
  @SerializedName(SERIALIZED_NAME_INFORMATICS)
  private Integer informatics;

  public Grade() {
  }

  public Grade id(Integer id) {
    
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public Grade student(Student student) {
    
    this.student = student;
    return this;
  }

   /**
   * Get student
   * @return student
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Student getStudent() {
    return student;
  }


  public void setStudent(Student student) {
    this.student = student;
  }


  public Grade physics(Integer physics) {
    
    this.physics = physics;
    return this;
  }

   /**
   * Get physics
   * @return physics
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getPhysics() {
    return physics;
  }


  public void setPhysics(Integer physics) {
    this.physics = physics;
  }


  public Grade mathematics(Integer mathematics) {
    
    this.mathematics = mathematics;
    return this;
  }

   /**
   * Get mathematics
   * @return mathematics
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getMathematics() {
    return mathematics;
  }


  public void setMathematics(Integer mathematics) {
    this.mathematics = mathematics;
  }


  public Grade rus(Integer rus) {
    
    this.rus = rus;
    return this;
  }

   /**
   * Get rus
   * @return rus
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getRus() {
    return rus;
  }


  public void setRus(Integer rus) {
    this.rus = rus;
  }


  public Grade literature(Integer literature) {
    
    this.literature = literature;
    return this;
  }

   /**
   * Get literature
   * @return literature
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getLiterature() {
    return literature;
  }


  public void setLiterature(Integer literature) {
    this.literature = literature;
  }


  public Grade geometry(Integer geometry) {
    
    this.geometry = geometry;
    return this;
  }

   /**
   * Get geometry
   * @return geometry
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getGeometry() {
    return geometry;
  }


  public void setGeometry(Integer geometry) {
    this.geometry = geometry;
  }


  public Grade informatics(Integer informatics) {
    
    this.informatics = informatics;
    return this;
  }

   /**
   * Get informatics
   * @return informatics
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getInformatics() {
    return informatics;
  }


  public void setInformatics(Integer informatics) {
    this.informatics = informatics;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Grade grade = (Grade) o;
    return Objects.equals(this.id, grade.id) &&
        Objects.equals(this.student, grade.student) &&
        Objects.equals(this.physics, grade.physics) &&
        Objects.equals(this.mathematics, grade.mathematics) &&
        Objects.equals(this.rus, grade.rus) &&
        Objects.equals(this.literature, grade.literature) &&
        Objects.equals(this.geometry, grade.geometry) &&
        Objects.equals(this.informatics, grade.informatics);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, student, physics, mathematics, rus, literature, geometry, informatics);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Grade {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    student: ").append(toIndentedString(student)).append("\n");
    sb.append("    physics: ").append(toIndentedString(physics)).append("\n");
    sb.append("    mathematics: ").append(toIndentedString(mathematics)).append("\n");
    sb.append("    rus: ").append(toIndentedString(rus)).append("\n");
    sb.append("    literature: ").append(toIndentedString(literature)).append("\n");
    sb.append("    geometry: ").append(toIndentedString(geometry)).append("\n");
    sb.append("    informatics: ").append(toIndentedString(informatics)).append("\n");
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
    openapiFields.add("id");
    openapiFields.add("student");
    openapiFields.add("physics");
    openapiFields.add("mathematics");
    openapiFields.add("rus");
    openapiFields.add("literature");
    openapiFields.add("geometry");
    openapiFields.add("informatics");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to Grade
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (Grade.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in Grade is not found in the empty JSON string", Grade.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Grade.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Grade` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
      // validate the optional field `student`
      if (jsonObj.get("student") != null && !jsonObj.get("student").isJsonNull()) {
        Student.validateJsonObject(jsonObj.getAsJsonObject("student"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Grade.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Grade' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Grade> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Grade.class));

       return (TypeAdapter<T>) new TypeAdapter<Grade>() {
           @Override
           public void write(JsonWriter out, Grade value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Grade read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Grade given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Grade
  * @throws IOException if the JSON string is invalid with respect to Grade
  */
  public static Grade fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Grade.class);
  }

 /**
  * Convert an instance of Grade to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

