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

import java.io.Serializable;
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
public class GetAttendancePOJO implements Serializable {

    @JsonProperty("ResponseStatus")
    private String ResponseStatus;
    @JsonProperty("ResponseCode")
    private String ResponseCode;
    @JsonProperty("ResponseMessage")
    private String ResponseMessage;
    @JsonProperty("data")
    private List<Datum> data = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

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
            "month_Name",
            "student_Id",
            "stud_Name",
            "Present",
            "Absent",
            "TotalDay"
    })
    public class Datum implements Serializable {

        @JsonProperty("month_Name")
        private String month_Name;
        @JsonProperty("student_Id")
        private Integer student_Id;
        @JsonProperty("stud_Name")
        private String stud_Name;
        @JsonProperty("Present")
        private Double Present;
        @JsonProperty("Absent")
        private Double Absent;
        @JsonProperty("TotalDay")
        private Double TotalDay;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("month_Name")
        public String getMonthName() {
            return month_Name;
        }

        @JsonProperty("month_Name")
        public void setMonthName(String month_Name) {
            this.month_Name = month_Name;
        }

        @JsonProperty("student_Id")
        public Integer getStudentId() {
            return student_Id;
        }

        @JsonProperty("student_Id")
        public void setStudentId(Integer student_Id) {
            this.student_Id = student_Id;
        }

        @JsonProperty("stud_Name")
        public String getStudName() {
            return stud_Name;
        }

        @JsonProperty("stud_Name")
        public void setStudName(String stud_Name) {
            this.stud_Name = stud_Name;
        }

        @JsonProperty("Present")
        public Double getPresent() {
            return Present;
        }

        @JsonProperty("Present")
        public void setPresent(Double Present) {
            this.Present = Present;
        }

        @JsonProperty("Absent")
        public Double getAbsent() {
            return Absent;
        }

        @JsonProperty("Absent")
        public void setAbsent(Double Absent) {
            this.Absent = Absent;
        }

        @JsonProperty("TotalDay")
        public Double getTotalDay() {
            return TotalDay;
        }

        @JsonProperty("TotalDay")
        public void setTotalDay(Double TotalDay) {
            this.TotalDay = TotalDay;
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