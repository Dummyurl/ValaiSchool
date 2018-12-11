package com.valai.school.modal;

/*
 * @author by Mohit Arora on 19/2/18.
 */

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ResponseStatus",
        "ResponseCode",
        "ResponseMessage",
        "data"
})
public class GetHolidaysPOJO {

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
            "org_Id",
            "academic_Id",
            "holiday_Id",
            "holiday_Code",
            "holiday_Name",
            "holiday_Description",
            "holiday_Start",
            "holiday_End",
            "is_Active"
    })
    public class Datum {

        @JsonProperty("org_Id")
        private Integer org_Id;
        @JsonProperty("academic_Id")
        private Integer academic_Id;
        @JsonProperty("holiday_Id")
        private Integer holiday_Id;
        @JsonProperty("holiday_Code")
        private String holiday_Code;
        @JsonProperty("holiday_Name")
        private String holiday_Name;
        @JsonProperty("holiday_Description")
        private String holiday_Description;
        @JsonProperty("holiday_Start")
        private String holiday_Start;
        @JsonProperty("holiday_End")
        private String holiday_End;
        @JsonProperty("is_Active")
        private Integer is_Active;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("org_Id")
        public Integer getOrgId() {
            return org_Id;
        }

        @JsonProperty("org_Id")
        public void setOrgId(Integer org_Id) {
            this.org_Id = org_Id;
        }

        @JsonProperty("academic_Id")
        public Integer getAcademicId() {
            return academic_Id;
        }

        @JsonProperty("academic_Id")
        public void setAcademicId(Integer academic_Id) {
            this.academic_Id = academic_Id;
        }

        @JsonProperty("holiday_Id")
        public Integer getHolidayId() {
            return holiday_Id;
        }

        @JsonProperty("holiday_Id")
        public void setHolidayId(Integer holiday_Id) {
            this.holiday_Id = holiday_Id;
        }

        @JsonProperty("holiday_Code")
        public String getHolidayCode() {
            return holiday_Code;
        }

        @JsonProperty("holiday_Code")
        public void setHolidayCode(String holiday_Code) {
            this.holiday_Code = holiday_Code;
        }

        @JsonProperty("holiday_Name")
        public String getHolidayName() {
            return holiday_Name;
        }

        @JsonProperty("holiday_Name")
        public void setHolidayName(String holiday_Name) {
            this.holiday_Name = holiday_Name;
        }

        @JsonProperty("holiday_Description")
        public String getHolidayDescription() {
            return holiday_Description;
        }

        @JsonProperty("holiday_Description")
        public void setHolidayDescription(String holiday_Description) {
            this.holiday_Description = holiday_Description;
        }

        @JsonProperty("holiday_Start")
        public String getHolidayStart() {
            return holiday_Start;
        }

        @JsonProperty("holiday_Start")
        public void setHolidayStart(String holiday_Start) {
            this.holiday_Start = holiday_Start;
        }

        @JsonProperty("holiday_End")
        public String getHolidayEnd() {
            return holiday_End;
        }

        @JsonProperty("holiday_End")
        public void setHolidayEnd(String holiday_End) {
            this.holiday_End = holiday_End;
        }

        @JsonProperty("is_Active")
        public Integer getIsActive() {
            return is_Active;
        }

        @JsonProperty("is_Active")
        public void setIsActive(Integer is_Active) {
            this.is_Active = is_Active;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }
    }
}