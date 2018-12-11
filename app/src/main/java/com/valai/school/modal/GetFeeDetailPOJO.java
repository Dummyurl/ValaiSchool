package com.valai.school.modal;

/*
 * @author by Mohit Arora on 12/2/18.
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
public class GetFeeDetailPOJO {

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
            "section_Id",
            "student_Id",
            "category_Id",
            "subCategory_Id",
            "duration_Id",
            "type_Id",
            "month_Id",
            "structure_Id",
            "class_Name",
            "section_Name",
            "student_Name",
            "category_Name",
            "subCategory_Name",
            "duration_Name",
            "type_Name",
            "receipt_Amount",
            "structure_Amount",
            "discount_Amount",
            "balance_Amount"
    })
    public class Datum {

        @JsonProperty("org_Id")
        private Integer org_Id;
        @JsonProperty("academic_Id")
        private Integer academic_Id;
        @JsonProperty("class_Id")
        private Integer class_Id;
        @JsonProperty("section_Id")
        private Integer section_Id;
        @JsonProperty("student_Id")
        private Integer student_Id;
        @JsonProperty("category_Id")
        private Integer category_Id;
        @JsonProperty("subCategory_Id")
        private Integer subCategory_Id;
        @JsonProperty("duration_Id")
        private Integer duration_Id;
        @JsonProperty("type_Id")
        private Integer type_Id;
        @JsonProperty("month_Id")
        private Integer month_Id;
        @JsonProperty("structure_Id")
        private Integer structure_Id;
        @JsonProperty("class_Name")
        private String class_Name;
        @JsonProperty("section_Name")
        private String section_Name;
        @JsonProperty("student_Name")
        private String student_Name;
        @JsonProperty("category_Name")
        private String category_Name;
        @JsonProperty("subCategory_Name")
        private String subCategory_Name;
        @JsonProperty("duration_Name")
        private String duration_Name;
        @JsonProperty("type_Name")
        private String type_Name;
        @JsonProperty("receipt_Amount")
        private Integer receipt_Amount;
        @JsonProperty("structure_Amount")
        private Integer structure_Amount;
        @JsonProperty("discount_Amount")
        private Integer discount_Amount;
        @JsonProperty("balance_Amount")
        private Integer balance_Amount;
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

        @JsonProperty("section_Id")
        public Integer getSectionId() {
            return section_Id;
        }

        @JsonProperty("section_Id")
        public void setSectionId(Integer section_Id) {
            this.section_Id = section_Id;
        }

        @JsonProperty("student_Id")
        public Integer getStudentId() {
            return student_Id;
        }

        @JsonProperty("student_Id")
        public void setStudentId(Integer student_Id) {
            this.student_Id = student_Id;
        }

        @JsonProperty("category_Id")
        public Integer getCategoryId() {
            return category_Id;
        }

        @JsonProperty("category_Id")
        public void setCategoryId(Integer category_Id) {
            this.category_Id = category_Id;
        }

        @JsonProperty("subCategory_Id")
        public Integer getSubCategoryId() {
            return subCategory_Id;
        }

        @JsonProperty("subCategory_Id")
        public void setSubCategoryId(Integer subCategory_Id) {
            this.subCategory_Id = subCategory_Id;
        }

        @JsonProperty("duration_Id")
        public Integer getDurationId() {
            return duration_Id;
        }

        @JsonProperty("duration_Id")
        public void setDurationId(Integer duration_Id) {
            this.duration_Id = duration_Id;
        }

        @JsonProperty("type_Id")
        public Integer getTypeId() {
            return type_Id;
        }

        @JsonProperty("type_Id")
        public void setTypeId(Integer type_Id) {
            this.type_Id = type_Id;
        }

        @JsonProperty("month_Id")
        public Integer getMonthId() {
            return month_Id;
        }

        @JsonProperty("month_Id")
        public void setMonthId(Integer month_Id) {
            this.month_Id = month_Id;
        }

        @JsonProperty("structure_Id")
        public Integer getStructureId() {
            return structure_Id;
        }

        @JsonProperty("structure_Id")
        public void setStructureId(Integer structure_Id) {
            this.structure_Id = structure_Id;
        }

        @JsonProperty("class_Name")
        public String getClassName() {
            return class_Name;
        }

        @JsonProperty("class_Name")
        public void setClassName(String class_Name) {
            this.class_Name = class_Name;
        }

        @JsonProperty("section_Name")
        public String getSectionName() {
            return section_Name;
        }

        @JsonProperty("section_Name")
        public void setSectionName(String section_Name) {
            this.section_Name = section_Name;
        }

        @JsonProperty("student_Name")
        public String getStudentName() {
            return student_Name;
        }

        @JsonProperty("student_Name")
        public void setStudentName(String student_Name) {
            this.student_Name = student_Name;
        }

        @JsonProperty("category_Name")
        public String getCategoryName() {
            return category_Name;
        }

        @JsonProperty("category_Name")
        public void setCategoryName(String category_Name) {
            this.category_Name = category_Name;
        }

        @JsonProperty("subCategory_Name")
        public String getSubCategoryName() {
            return subCategory_Name;
        }

        @JsonProperty("subCategory_Name")
        public void setSubCategoryName(String subCategory_Name) {
            this.subCategory_Name = subCategory_Name;
        }

        @JsonProperty("duration_Name")
        public String getDurationName() {
            return duration_Name;
        }

        @JsonProperty("duration_Name")
        public void setDurationName(String duration_Name) {
            this.duration_Name = duration_Name;
        }

        @JsonProperty("type_Name")
        public String getTypeName() {
            return type_Name;
        }

        @JsonProperty("type_Name")
        public void setTypeName(String type_Name) {
            this.type_Name = type_Name;
        }

        @JsonProperty("receipt_Amount")
        public Integer getReceiptAmount() {
            return receipt_Amount;
        }

        @JsonProperty("receipt_Amount")
        public void setReceiptAmount(Integer receipt_Amount) {
            this.receipt_Amount = receipt_Amount;
        }

        @JsonProperty("structure_Amount")
        public Integer getStructureAmount() {
            return structure_Amount;
        }

        @JsonProperty("structure_Amount")
        public void setStructureAmount(Integer structure_Amount) {
            this.structure_Amount = structure_Amount;
        }

        @JsonProperty("discount_Amount")
        public Integer getDiscountAmount() {
            return discount_Amount;
        }

        @JsonProperty("discount_Amount")
        public void setDiscountAmount(Integer discount_Amount) {
            this.discount_Amount = discount_Amount;
        }

        @JsonProperty("balance_Amount")
        public Integer getBalanceAmount() {
            return balance_Amount;
        }

        @JsonProperty("balance_Amount")
        public void setBalanceAmount(Integer balance_Amount) {
            this.balance_Amount = balance_Amount;
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