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
public class GetSubjectMasterPOJO {

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
            "SINO",
            "org_Id",
            "branch_Id",
            "academic_Id",
            "class_Id",
            "classType_Id",
            "subject_Id",
            "subject_Name",
            "sub_GroupId",
            "isFormativeAssm",
            "isMandatory",
            "subject_Order",
            "CGPA_Subject",
            "is_Active",
            "examtype_flag",
            "Theorymaxmark",
            "Practicalmaxmark"
    })
    public class Datum {

        @JsonProperty("SINO")
        private Integer sINO;
        @JsonProperty("org_Id")
        private Integer org_Id;
        @JsonProperty("branch_Id")
        private Integer branch_Id;
        @JsonProperty("academic_Id")
        private Integer academic_Id;
        @JsonProperty("class_Id")
        private Integer class_Id;
        @JsonProperty("classType_Id")
        private Integer classType_Id;
        @JsonProperty("subject_Id")
        private Integer subject_Id;
        @JsonProperty("subject_Name")
        private String subject_Name;
        @JsonProperty("sub_GroupId")
        private Integer sub_GroupId;
        @JsonProperty("isFormativeAssm")
        private Integer isFormativeAssm;
        @JsonProperty("isMandatory")
        private Integer isMandatory;
        @JsonProperty("subject_Order")
        private Integer subject_Order;
        @JsonProperty("CGPA_Subject")
        private Integer CGPA_Subject;
        @JsonProperty("is_Active")
        private Integer is_Active;
        @JsonProperty("examtype_flag")
        private Integer examtype_flag;
        @JsonProperty("Theorymaxmark")
        private Integer theorymaxmark;
        @JsonProperty("Practicalmaxmark")
        private Integer practicalmaxmark;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("SINO")
        public Integer getSINO() {
            return sINO;
        }

        @JsonProperty("SINO")
        public void setSINO(Integer sINO) {
            this.sINO = sINO;
        }

        @JsonProperty("org_Id")
        public Integer getOrgId() {
            return org_Id;
        }

        @JsonProperty("org_Id")
        public void setOrgId(Integer org_Id) {
            this.org_Id = org_Id;
        }

        @JsonProperty("branch_Id")
        public Integer getBranchId() {
            return branch_Id;
        }

        @JsonProperty("branch_Id")
        public void setBranchId(Integer branch_Id) {
            this.branch_Id = branch_Id;
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

        @JsonProperty("classType_Id")
        public Integer getClassTypeId() {
            return classType_Id;
        }

        @JsonProperty("classType_Id")
        public void setClassTypeId(Integer classType_Id) {
            this.classType_Id = classType_Id;
        }

        @JsonProperty("subject_Id")
        public Integer getSubjectId() {
            return subject_Id;
        }

        @JsonProperty("subject_Id")
        public void setSubjectId(Integer subject_Id) {
            this.subject_Id = subject_Id;
        }

        @JsonProperty("subject_Name")
        public String getSubjectName() {
            return subject_Name;
        }

        @JsonProperty("subject_Name")
        public void setSubjectName(String subject_Name) {
            this.subject_Name = subject_Name;
        }

        @JsonProperty("sub_GroupId")
        public Integer getSubGroupId() {
            return sub_GroupId;
        }

        @JsonProperty("sub_GroupId")
        public void setSubGroupId(Integer sub_GroupId) {
            this.sub_GroupId = sub_GroupId;
        }

        @JsonProperty("isFormativeAssm")
        public Integer getIsFormativeAssm() {
            return isFormativeAssm;
        }

        @JsonProperty("isFormativeAssm")
        public void setIsFormativeAssm(Integer isFormativeAssm) {
            this.isFormativeAssm = isFormativeAssm;
        }

        @JsonProperty("isMandatory")
        public Integer getIsMandatory() {
            return isMandatory;
        }

        @JsonProperty("isMandatory")
        public void setIsMandatory(Integer isMandatory) {
            this.isMandatory = isMandatory;
        }

        @JsonProperty("subject_Order")
        public Integer getSubjectOrder() {
            return subject_Order;
        }

        @JsonProperty("subject_Order")
        public void setSubjectOrder(Integer subject_Order) {
            this.subject_Order = subject_Order;
        }

        @JsonProperty("CGPA_Subject")
        public Integer getCGPASubject() {
            return CGPA_Subject;
        }

        @JsonProperty("CGPA_Subject")
        public void setCGPASubject(Integer CGPA_Subject) {
            this.CGPA_Subject = CGPA_Subject;
        }

        @JsonProperty("is_Active")
        public Integer getIsActive() {
            return is_Active;
        }

        @JsonProperty("is_Active")
        public void setIsActive(Integer is_Active) {
            this.is_Active = is_Active;
        }

        @JsonProperty("examtype_flag")
        public Integer getExamtypeFlag() {
            return examtype_flag;
        }

        @JsonProperty("examtype_flag")
        public void setExamtypeFlag(Integer examtype_flag) {
            this.examtype_flag = examtype_flag;
        }

        @JsonProperty("Theorymaxmark")
        public Integer getTheorymaxmark() {
            return theorymaxmark;
        }

        @JsonProperty("Theorymaxmark")
        public void setTheorymaxmark(Integer theorymaxmark) {
            this.theorymaxmark = theorymaxmark;
        }

        @JsonProperty("Practicalmaxmark")
        public Integer getPracticalmaxmark() {
            return practicalmaxmark;
        }

        @JsonProperty("Practicalmaxmark")
        public void setPracticalmaxmark(Integer practicalmaxmark) {
            this.practicalmaxmark = practicalmaxmark;
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