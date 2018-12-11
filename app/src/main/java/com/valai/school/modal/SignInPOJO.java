package com.valai.school.modal;

/*
 * @author by Mohit Arora on 8/2/18.
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
public class SignInPOJO {

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
            "orgId",
            "academicId",
            "groupId",
            "userRoleId",
            "userTypeId",
            "loginId",
            "orgName",
            "orgLogo",
            "userName",
            "staffId",
            "parentId",
            "classId",
            "studentId",
            "sectionId",
            "subscription",
            "homePage",
            "markEntry",
            "branch_Id",
            "countryId"
    })
    public class Datum {

        @JsonProperty("orgId")
        private Integer orgId;
        @JsonProperty("academicId")
        private Integer academicId;
        @JsonProperty("groupId")
        private Integer groupId;
        @JsonProperty("userRoleId")
        private Integer userRoleId;
        @JsonProperty("userTypeId")
        private Integer userTypeId;
        @JsonProperty("loginId")
        private Integer loginId;
        @JsonProperty("orgName")
        private String orgName;
        @JsonProperty("orgLogo")
        private String orgLogo;
        @JsonProperty("userName")
        private String userName;
        @JsonProperty("staffId")
        private Integer staffId;
        @JsonProperty("parentId")
        private Integer parentId;
        @JsonProperty("classId")
        private Integer classId;
        @JsonProperty("studentId")
        private Integer studentId;
        @JsonProperty("sectionId")
        private Integer sectionId;
        @JsonProperty("subscription")
        private Integer subscription;
        @JsonProperty("homePage")
        private String homePage;
        @JsonProperty("markEntry")
        private Integer markEntry;
        @JsonProperty("branch_Id")
        private Integer branch_Id;
        @JsonProperty("countryId")
        private Integer countryId;
        @JsonProperty("staffImg")
        private String staffImg;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("orgId")
        public Integer getOrgId() {
            return orgId;
        }

        @JsonProperty("orgId")
        public void setOrgId(Integer orgId) {
            this.orgId = orgId;
        }

        @JsonProperty("academicId")
        public Integer getAcademicId() {
            return academicId;
        }

        @JsonProperty("academicId")
        public void setAcademicId(Integer academicId) {
            this.academicId = academicId;
        }

        @JsonProperty("groupId")
        public Integer getGroupId() {
            return groupId;
        }

        @JsonProperty("groupId")
        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        @JsonProperty("userRoleId")
        public Integer getUserRoleId() {
            return userRoleId;
        }

        @JsonProperty("userRoleId")
        public void setUserRoleId(Integer userRoleId) {
            this.userRoleId = userRoleId;
        }

        @JsonProperty("userTypeId")
        public Integer getUserTypeId() {
            return userTypeId;
        }

        @JsonProperty("userTypeId")
        public void setUserTypeId(Integer userTypeId) {
            this.userTypeId = userTypeId;
        }

        @JsonProperty("loginId")
        public Integer getLoginId() {
            return loginId;
        }

        @JsonProperty("loginId")
        public void setLoginId(Integer loginId) {
            this.loginId = loginId;
        }

        @JsonProperty("orgName")
        public String getOrgName() {
            return orgName;
        }

        @JsonProperty("orgName")
        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        @JsonProperty("orgLogo")
        public String getOrgLogo() {
            return orgLogo;
        }

        @JsonProperty("orgLogo")
        public void setOrgLogo(String orgLogo) {
            this.orgLogo = orgLogo;
        }

        @JsonProperty("userName")
        public String getUserName() {
            return userName;
        }

        @JsonProperty("userName")
        public void setUserName(String userName) {
            this.userName = userName;
        }

        @JsonProperty("staffId")
        public Integer getStaffId() {
            return staffId;
        }

        @JsonProperty("staffId")
        public void setStaffId(Integer staffId) {
            this.staffId = staffId;
        }

        @JsonProperty("parentId")
        public Integer getParentId() {
            return parentId;
        }

        @JsonProperty("parentId")
        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        @JsonProperty("classId")
        public Integer getClassId() {
            return classId;
        }

        @JsonProperty("classId")
        public void setClassId(Integer classId) {
            this.classId = classId;
        }

        @JsonProperty("studentId")
        public Integer getStudentId() {
            return studentId;
        }

        @JsonProperty("studentId")
        public void setStudentId(Integer studentId) {
            this.studentId = studentId;
        }

        @JsonProperty("sectionId")
        public Integer getSectionId() {
            return sectionId;
        }

        @JsonProperty("sectionId")
        public void setSectionId(Integer sectionId) {
            this.sectionId = sectionId;
        }

        @JsonProperty("subscription")
        public Integer getSubscription() {
            return subscription;
        }

        @JsonProperty("subscription")
        public void setSubscription(Integer subscription) {
            this.subscription = subscription;
        }

        @JsonProperty("homePage")
        public String getHomePage() {
            return homePage;
        }

        @JsonProperty("homePage")
        public void setHomePage(String homePage) {
            this.homePage = homePage;
        }

        @JsonProperty("markEntry")
        public Integer getMarkEntry() {
            return markEntry;
        }

        @JsonProperty("markEntry")
        public void setMarkEntry(Integer markEntry) {
            this.markEntry = markEntry;
        }

        @JsonProperty("branch_Id")
        public Integer getBranchId() {
            return branch_Id;
        }

        @JsonProperty("branch_Id")
        public void setBranchId(Integer branch_Id) {
            this.branch_Id = branch_Id;
        }

        @JsonProperty("countryId")
        public Integer getCountryId() {
            return countryId;
        }

        @JsonProperty("countryId")
        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

        @JsonProperty("staffImg")
        public String getStaffImg() {
            return staffImg;
        }

        @JsonProperty("staffImg")
        public void setStaffImg(String staffImg) {
            this.staffImg = staffImg;
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