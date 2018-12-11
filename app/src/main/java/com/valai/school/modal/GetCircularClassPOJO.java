package com.valai.school.modal;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ResponseStatus",
        "ResponseCode",
        "ResponseMessage",
        "data"
})
public class GetCircularClassPOJO {

    @JsonProperty("ResponseStatus")
    private String ResponseStatus;
    @JsonProperty("ResponseCode")
    private String ResponseCode;
    @JsonProperty("ResponseMessage")
    private String ResponseMessage;
    @JsonProperty("data")
    private List<Datum> data = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ResponseStatus")
    public String getResponseStatus() {
        return ResponseStatus;
    }

    @JsonProperty("ResponseStatus")
    public void setResponseStatus(String ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    @JsonProperty("ResponseCode")
    public String getResponseCode() {
        return ResponseCode;
    }

    @JsonProperty("ResponseCode")
    public void setResponseCode(String ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    @JsonProperty("ResponseMessage")
    public String getResponseMessage() {
        return ResponseMessage;
    }

    @JsonProperty("ResponseMessage")
    public void setResponseMessage(String ResponseMessage) {
        this.ResponseMessage = ResponseMessage;
    }

    @JsonProperty("data")
    public List<Datum> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<Datum> data) {
        this.data = data;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "class_Id",
            "class_Name",
            "class_Description",
            "class_Order",
            "classType_Id",
            "isActive"
    })
    public class Datum {

        @JsonProperty("class_Id")
        private Integer class_Id;
        @JsonProperty("class_Name")
        private String class_Name;
        @JsonProperty("class_Description")
        private String class_Description;
        @JsonProperty("class_Order")
        private Integer class_Order;
        @JsonProperty("classType_Id")
        private Integer classType_Id;
        @JsonProperty("isActive")
        private Integer isActive;
        private boolean isSelected;
        private boolean isChecked;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("class_Id")
        public Integer getClassId() {
            return class_Id;
        }

        @JsonProperty("class_Id")
        public void setClassId(Integer class_Id) {
            this.class_Id = class_Id;
        }

        @JsonProperty("class_Name")
        public String getClassName() {
            return class_Name;
        }

        @JsonProperty("class_Name")
        public void setClassName(String class_Name) {
            this.class_Name = class_Name;
        }

        @JsonProperty("class_Description")
        public String getClassDescription() {
            return class_Description;
        }

        @JsonProperty("class_Description")
        public void setClassDescription(String class_Description) {
            this.class_Description = class_Description;
        }

        @JsonProperty("class_Order")
        public Integer getClassOrder() {
            return class_Order;
        }

        @JsonProperty("class_Order")
        public void setClassOrder(Integer class_Order) {
            this.class_Order = class_Order;
        }

        @JsonProperty("classType_Id")
        public Integer getClassTypeId() {
            return classType_Id;
        }

        @JsonProperty("classType_Id")
        public void setClassTypeId(Integer classType_Id) {
            this.classType_Id = classType_Id;
        }

        @JsonProperty("isActive")
        public Integer getIsActive() {
            return isActive;
        }

        @JsonProperty("isActive")
        public void setIsActive(Integer isActive) {
            this.isActive = isActive;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setChecked(boolean Checked) {
            isChecked = Checked;
        }

        public boolean isChecked() {
            return isChecked;
        }

    }
}