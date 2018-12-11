package com.valai.school.modal;

/*
 * @author by Mohit Arora on 14/3/18.
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
public class StudentListPOJO {

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
            "org_Name",
            "org_Logo",
            "student_Id",
            "login_Id",
            "role_Home_Page",
            "student_org_Id",
            "stud_Name",
            "class_Id",
            "section_Id",
            "class_Name",
            "branch_Id",
            "academic_Id",
            "student_Image"
    })
    public class Datum {

        @JsonProperty("org_Id")
        private Integer org_Id;
        @JsonProperty("org_Name")
        private String org_Name;
        @JsonProperty("org_Logo")
        private String org_Logo;
        @JsonProperty("student_Id")
        private Integer student_Id;
        @JsonProperty("login_Id")
        private Integer login_Id;
        @JsonProperty("role_Home_Page")
        private String role_Home_Page;
        @JsonProperty("student_org_Id")
        private String student_org_Id;
        @JsonProperty("stud_Name")
        private String stud_Name;
        @JsonProperty("class_Id")
        private Integer class_Id;
        @JsonProperty("section_Id")
        private Integer section_Id;
        @JsonProperty("class_Name")
        private String class_Name;
        @JsonProperty("branch_Id")
        private Integer branch_Id;
        @JsonProperty("academic_Id")
        private Integer academic_Id;
        @JsonProperty("student_Image")
        private String student_Image;
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

        @JsonProperty("org_Name")
        public String getOrgName() {
            return org_Name;
        }

        @JsonProperty("org_Name")
        public void setOrgName(String org_Name) {
            this.org_Name = org_Name;
        }

        @JsonProperty("org_Logo")
        public String getOrgLogo() {
            return org_Logo;
        }

        @JsonProperty("org_Logo")
        public void setOrgLogo(String org_Logo) {
            this.org_Logo = org_Logo;
        }

        @JsonProperty("student_Id")
        public Integer getStudentId() {
            return student_Id;
        }

        @JsonProperty("student_Id")
        public void setStudentId(Integer student_Id) {
            this.student_Id = student_Id;
        }

        @JsonProperty("login_Id")
        public Integer getLoginId() {
            return login_Id;
        }

        @JsonProperty("login_Id")
        public void setLoginId(Integer login_Id) {
            this.login_Id = login_Id;
        }

        @JsonProperty("role_Home_Page")
        public String getRoleHomePage() {
            return role_Home_Page;
        }

        @JsonProperty("role_Home_Page")
        public void setRoleHomePage(String role_Home_Page) {
            this.role_Home_Page = role_Home_Page;
        }

        @JsonProperty("student_org_Id")
        public String getStudentOrgId() {
            return student_org_Id;
        }

        @JsonProperty("student_org_Id")
        public void setStudentOrgId(String student_org_Id) {
            this.student_org_Id = student_org_Id;
        }

        @JsonProperty("stud_Name")
        public String getStudName() {
            return stud_Name;
        }

        @JsonProperty("stud_Name")
        public void setStudName(String stud_Name) {
            this.stud_Name = stud_Name;
        }

        @JsonProperty("class_Id")
        public Integer getClassId() {
            return class_Id;
        }

        @JsonProperty("class_Id")
        public void setClassId(Integer class_Id) {
            this.class_Id = class_Id;
        }

        @JsonProperty("section_Id")
        public Integer getSectionId() {
            return section_Id;
        }

        @JsonProperty("section_Id")
        public void setSectionId(Integer section_Id) {
            this.section_Id = section_Id;
        }

        @JsonProperty("class_Name")
        public String getClassName() {
            return class_Name;
        }

        @JsonProperty("class_Name")
        public void setClassName(String class_Name) {
            this.class_Name = class_Name;
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

        @JsonProperty("student_Image")
        public String getStudentImage() {
            return student_Image;
        }

        @JsonProperty("student_Image")
        public void setStudentImage(String student_Image) {
            this.student_Image = student_Image;
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
