package com.valai.school.modal;

/**
 * @author by Mohit Arora on 1/3/18.
 */

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
public class GetAcademicYearPOJO {

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
            "academic_Yrs_from",
            "from_Date",
            "academic_Yrs_To",
            "to_Date",
            "is_Active",
            "created_Date",
            "modified_Date"
    })
    public class Datum {

        @JsonProperty("org_Id")
        private Integer org_Id;
        @JsonProperty("academic_Id")
        private Integer academic_Id;
        @JsonProperty("academic_Yrs_from")
        private Integer academic_Yrs_from;
        @JsonProperty("from_Date")
        private String from_Date;
        @JsonProperty("academic_Yrs_To")
        private Integer academic_Yrs_To;
        @JsonProperty("to_Date")
        private String to_Date;
        @JsonProperty("is_Active")
        private Integer is_Active;
        @JsonProperty("created_Date")
        private String created_Date;
        @JsonProperty("modified_Date")
        private String modified_Date;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("org_Id")
        public Integer getOrgId() {
            return org_Id;
        }

        @JsonProperty("org_Id")
        public void setOrgId(Integer orgId) {
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

        @JsonProperty("academic_Yrs_from")
        public Integer getAcademicYrsFrom() {
            return academic_Yrs_from;
        }

        @JsonProperty("academic_Yrs_from")
        public void setAcademicYrsFrom(Integer academic_Yrs_from) {
            this.academic_Yrs_from = academic_Yrs_from;
        }

        @JsonProperty("from_Date")
        public String getFromDate() {
            return from_Date;
        }

        @JsonProperty("from_Date")
        public void setFromDate(String from_Date) {
            this.from_Date = from_Date;
        }

        @JsonProperty("academic_Yrs_To")
        public Integer getAcademicYrsTo() {
            return academic_Yrs_To;
        }

        @JsonProperty("academic_Yrs_To")
        public void setAcademicYrsTo(Integer academic_Yrs_To) {
            this.academic_Yrs_To = academic_Yrs_To;
        }

        @JsonProperty("to_Date")
        public String getToDate() {
            return to_Date;
        }

        @JsonProperty("to_Date")
        public void setToDate(String to_Date) {
            this.to_Date = to_Date;
        }

        @JsonProperty("is_Active")
        public Integer getIsActive() {
            return is_Active;
        }

        @JsonProperty("is_Active")
        public void setIsActive(Integer is_Active) {
            this.is_Active = is_Active;
        }

        @JsonProperty("created_Date")
        public String getCreatedDate() {
            return created_Date;
        }

        @JsonProperty("created_Date")
        public void setCreatedDate(String created_Date) {
            this.created_Date = created_Date;
        }

        @JsonProperty("modified_Date")
        public String getModifiedDate() {
            return modified_Date;
        }

        @JsonProperty("modified_Date")
        public void setModifiedDate(String modified_Date) {
            this.modified_Date = modified_Date;
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


