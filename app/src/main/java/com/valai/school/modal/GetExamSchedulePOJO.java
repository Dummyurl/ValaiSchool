package com.valai.school.modal;

/*
 * @author by Mohit Arora on 17/2/18.
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
public class GetExamSchedulePOJO implements Serializable{

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
            "exam_Id",
            "class_Id",
            "subject_Id",
            "schedule_Id",
            "branch_Id",
            "branch_Name",
            "exam_Name",
            "class_Name",
            "subject_Name",
            "exam_Date",
            "start_Time",
            "end_Time",
            "is_Active",
            "comments"
    })
    public class Datum implements Serializable{
        @JsonProperty("org_Id")
        private Integer org_Id;
        @JsonProperty("academic_Id")
        private Integer academic_Id;
        @JsonProperty("exam_Id")
        private Integer exam_Id;
        @JsonProperty("class_Id")
        private Integer class_Id;
        @JsonProperty("subject_Id")
        private Integer subject_Id;
        @JsonProperty("schedule_Id")
        private Integer schedule_Id;
        @JsonProperty("branch_Id")
        private Integer branch_Id;
        @JsonProperty("branch_Name")
        private String branch_Name;
        @JsonProperty("exam_Name")
        private String exam_Name;
        @JsonProperty("class_Name")
        private String class_Name;
        @JsonProperty("subject_Name")
        private String subject_Name;
        @JsonProperty("exam_Date")
        private String exam_Date;
        @JsonProperty("start_Time")
        private String start_Time;
        @JsonProperty("end_Time")
        private String end_Time;
        @JsonProperty("is_Active")
        private Integer is_Active;
        @JsonProperty("comments")
        private String comments;
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

        @JsonProperty("exam_Id")
        public Integer getExamId() {
            return exam_Id;
        }

        @JsonProperty("exam_Id")
        public void setExamId(Integer exam_Id) {
            this.exam_Id = exam_Id;
        }

        @JsonProperty("class_Id")
        public Integer getClassId() {
            return class_Id;
        }

        @JsonProperty("class_Id")
        public void setClassId(Integer class_Id) {
            this.class_Id = class_Id;
        }

        @JsonProperty("subject_Id")
        public Integer getSubjectId() {
            return subject_Id;
        }

        @JsonProperty("subject_Id")
        public void setSubjectId(Integer subject_Id) {
            this.subject_Id = subject_Id;
        }

        @JsonProperty("schedule_Id")
        public Integer getScheduleId() {
            return schedule_Id;
        }

        @JsonProperty("schedule_Id")
        public void setScheduleId(Integer schedule_Id) {
            this.schedule_Id = schedule_Id;
        }

        @JsonProperty("branch_Id")
        public Integer getBranchId() {
            return branch_Id;
        }

        @JsonProperty("branch_Id")
        public void setBranchId(Integer branch_Id) {
            this.branch_Id = branch_Id;
        }

        @JsonProperty("branch_Name")
        public String getBranchName() {
            return branch_Name;
        }

        @JsonProperty("branch_Name")
        public void setBranchName(String branch_Name) {
            this.branch_Name = branch_Name;
        }

        @JsonProperty("exam_Name")
        public String getExamName() {
            return exam_Name;
        }

        @JsonProperty("exam_Name")
        public void setExamName(String exam_Name) {
            this.exam_Name = exam_Name;
        }

        @JsonProperty("class_Name")
        public String getClassName() {
            return class_Name;
        }

        @JsonProperty("class_Name")
        public void setClassName(String class_Name) {
            this.class_Name = class_Name;
        }

        @JsonProperty("subject_Name")
        public String getSubjectName() {
            return subject_Name;
        }

        @JsonProperty("subject_Name")
        public void setSubjectName(String subject_Name) {
            this.subject_Name = subject_Name;
        }

        @JsonProperty("exam_Date")
        public String getExamDate() {
            return exam_Date;
        }

        @JsonProperty("exam_Date")
        public void setExamDate(String exam_Date) {
            this.exam_Date = exam_Date;
        }

        @JsonProperty("start_Time")
        public String getStartTime() {
            return start_Time;
        }

        @JsonProperty("start_Time")
        public void setStartTime(String start_Time) {
            this.start_Time = start_Time;
        }

        @JsonProperty("end_Time")
        public String getEndTime() {
            return end_Time;
        }

        @JsonProperty("end_Time")
        public void setEndTime(String end_Time) {
            this.end_Time = end_Time;
        }

        @JsonProperty("is_Active")
        public Integer getIsActive() {
            return is_Active;
        }

        @JsonProperty("is_Active")
        public void setIsActive(Integer is_Active) {
            this.is_Active = is_Active;
        }

        @JsonProperty("comments")
        public String getcomment() {
            return comments;
        }

        @JsonProperty("comments")
        public void setcomment(String comments) {
            this.comments = comments;
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