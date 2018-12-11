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
public class GetSubChapterMasterPOJO {

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
            "class_Id",
            "branch_Id",
            "subject_Id",
            "chapter_Id",
            "subChapter_Id",
            "subChapter_Name",
            "subChapter_Order",
            "from_Date",
            "to_Date",
            "duration",
            "No_of_days",
            "is_Active",
            "created_Date",
            "modified_Date",
            "user_name",
            "IP_address",
            "last_updated_timestamp"
    })
    public class Datum {

        @JsonProperty("org_Id")
        private Integer org_Id;
        @JsonProperty("academic_Id")
        private Integer academic_Id;
        @JsonProperty("class_Id")
        private Integer class_Id;
        @JsonProperty("branch_Id")
        private Integer branch_Id;
        @JsonProperty("subject_Id")
        private Integer subject_Id;
        @JsonProperty("chapter_Id")
        private Integer chapter_Id;
        @JsonProperty("subChapter_Id")
        private Integer subChapter_Id;
        @JsonProperty("subChapter_Name")
        private String subChapter_Name;
        @JsonProperty("subChapter_Order")
        private Integer subChapter_Order;
        @JsonProperty("from_Date")
        private String from_Date;
        @JsonProperty("to_Date")
        private String to_Date;
        @JsonProperty("duration")
        private Integer duration;
        @JsonProperty("No_of_days")
        private Integer No_of_days;
        @JsonProperty("is_Active")
        private Integer is_Active;
        @JsonProperty("created_Date")
        private String created_Date;
        @JsonProperty("modified_Date")
        private String modified_Date;
        @JsonProperty("user_name")
        private String user_name;
        @JsonProperty("IP_address")
        private String IP_address;
        @JsonProperty("last_updated_timestamp")
        private String last_updated_timestamp;
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

        @JsonProperty("class_Id")
        public Integer getClassId() {
            return class_Id;
        }

        @JsonProperty("class_Id")
        public void setClassId(Integer class_Id) {
            this.class_Id = class_Id;
        }

        @JsonProperty("branch_Id")
        public Integer getBranchId() {
            return branch_Id;
        }

        @JsonProperty("branch_Id")
        public void setBranchId(Integer branch_Id) {
            this.branch_Id = branch_Id;
        }

        @JsonProperty("subject_Id")
        public Integer getSubjectId() {
            return subject_Id;
        }

        @JsonProperty("subject_Id")
        public void setSubjectId(Integer subject_Id) {
            this.subject_Id = subject_Id;
        }

        @JsonProperty("chapter_Id")
        public Integer getChapterId() {
            return chapter_Id;
        }

        @JsonProperty("chapter_Id")
        public void setChapterId(Integer chapter_Id) {
            this.chapter_Id = chapter_Id;
        }

        @JsonProperty("subChapter_Id")
        public Integer getSubChapterId() {
            return subChapter_Id;
        }

        @JsonProperty("subChapter_Id")
        public void setSubChapterId(Integer subChapter_Id) {
            this.subChapter_Id = subChapter_Id;
        }

        @JsonProperty("subChapter_Name")
        public String getSubChapterName() {
            return subChapter_Name;
        }

        @JsonProperty("subChapter_Name")
        public void setSubChapterName(String subChapter_Name) {
            this.subChapter_Name = subChapter_Name;
        }

        @JsonProperty("subChapter_Order")
        public Integer getSubChapterOrder() {
            return subChapter_Order;
        }

        @JsonProperty("subChapter_Order")
        public void setSubChapterOrder(Integer subChapter_Order) {
            this.subChapter_Order = subChapter_Order;
        }

        @JsonProperty("from_Date")
        public String getFromDate() {
            return from_Date;
        }

        @JsonProperty("from_Date")
        public void setFromDate(String from_Date) {
            this.from_Date = from_Date;
        }

        @JsonProperty("to_Date")
        public String getToDate() {
            return to_Date;
        }

        @JsonProperty("to_Date")
        public void setToDate(String to_Date) {
            this.to_Date = to_Date;
        }

        @JsonProperty("duration")
        public Integer getDuration() {
            return duration;
        }

        @JsonProperty("duration")
        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        @JsonProperty("No_of_days")
        public Integer getNoOfDays() {
            return No_of_days;
        }

        @JsonProperty("No_of_days")
        public void setNoOfDays(Integer No_of_days) {
            this.No_of_days = No_of_days;
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

        @JsonProperty("user_name")
        public String getUserName() {
            return user_name;
        }

        @JsonProperty("user_name")
        public void setUserName(String user_name) {
            this.user_name = user_name;
        }

        @JsonProperty("IP_address")
        public String getIPAddress() {
            return IP_address;
        }

        @JsonProperty("IP_address")
        public void setIPAddress(String IP_address) {
            this.IP_address = IP_address;
        }

        @JsonProperty("last_updated_timestamp")
        public String getLastUpdatedTimestamp() {
            return last_updated_timestamp;
        }

        @JsonProperty("last_updated_timestamp")
        public void setLastUpdatedTimestamp(String last_updated_timestamp) {
            this.last_updated_timestamp = last_updated_timestamp;
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