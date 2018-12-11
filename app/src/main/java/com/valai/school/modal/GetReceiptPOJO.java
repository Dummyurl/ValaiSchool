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
public class GetReceiptPOJO {

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
            "receipt_Code",
            "receipt_Date",
            "receipt_Amount",
            "receipt_Mode",
            "cheque_Number",
            "cheque_Date",
            "dd_Number",
            "dd_Date",
            "reference_Code",
            "payment_Date",
            "bank_Name",
            "branch_Name",
            "student_Name"
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
        @JsonProperty("receipt_Code")
        private Integer receipt_Code;
        @JsonProperty("receipt_Date")
        private String receipt_Date;
        @JsonProperty("receipt_Amount")
        private Integer receipt_Amount;
        @JsonProperty("receipt_Mode")
        private Integer receipt_Mode;
        @JsonProperty("cheque_Number")
        private String cheque_Number;
        @JsonProperty("cheque_Date")
        private String cheque_Date;
        @JsonProperty("dd_Number")
        private String dd_Number;
        @JsonProperty("dd_Date")
        private String dd_Date;
        @JsonProperty("reference_Code")
        private String reference_Code;
        @JsonProperty("payment_Date")
        private String payment_Date;
        @JsonProperty("bank_Name")
        private String bank_Name;
        @JsonProperty("branch_Name")
        private String branch_Name;
        @JsonProperty("student_Name")
        private String student_Name;
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

        @JsonProperty("receipt_Code")
        public Integer getReceiptCode() {
            return receipt_Code;
        }

        @JsonProperty("receipt_Code")
        public void setReceiptCode(Integer receipt_Code) {
            this.receipt_Code = receipt_Code;
        }

        @JsonProperty("receipt_Date")
        public String getReceiptDate() {
            return receipt_Date;
        }

        @JsonProperty("receipt_Date")
        public void setReceiptDate(String receipt_Date) {
            this.receipt_Date = receipt_Date;
        }

        @JsonProperty("receipt_Amount")
        public Integer getReceiptAmount() {
            return receipt_Amount;
        }

        @JsonProperty("receipt_Amount")
        public void setReceiptAmount(Integer receipt_Amount) {
            this.receipt_Amount = receipt_Amount;
        }

        @JsonProperty("receipt_Mode")
        public Integer getReceiptMode() {
            return receipt_Mode;
        }

        @JsonProperty("receipt_Mode")
        public void setReceiptMode(Integer receipt_Mode) {
            this.receipt_Mode = receipt_Mode;
        }

        @JsonProperty("cheque_Number")
        public String getChequeNumber() {
            return cheque_Number;
        }

        @JsonProperty("cheque_Number")
        public void setChequeNumber(String cheque_Number) {
            this.cheque_Number = cheque_Number;
        }

        @JsonProperty("cheque_Date")
        public String getChequeDate() {
            return cheque_Date;
        }

        @JsonProperty("cheque_Date")
        public void setChequeDate(String cheque_Date) {
            this.cheque_Date = cheque_Date;
        }

        @JsonProperty("dd_Number")
        public String getDdNumber() {
            return dd_Number;
        }

        @JsonProperty("dd_Number")
        public void setDdNumber(String dd_Number) {
            this.dd_Number = dd_Number;
        }

        @JsonProperty("dd_Date")
        public String getDdDate() {
            return dd_Date;
        }

        @JsonProperty("dd_Date")
        public void setDdDate(String dd_Date) {
            this.dd_Date = dd_Date;
        }

        @JsonProperty("reference_Code")
        public String getReferenceCode() {
            return reference_Code;
        }

        @JsonProperty("reference_Code")
        public void setReferenceCode(String reference_Code) {
            this.reference_Code = reference_Code;
        }

        @JsonProperty("payment_Date")
        public String getPaymentDate() {
            return payment_Date;
        }

        @JsonProperty("payment_Date")
        public void setPaymentDate(String payment_Date) {
            this.payment_Date = payment_Date;
        }

        @JsonProperty("bank_Name")
        public String getBankName() {
            return bank_Name;
        }

        @JsonProperty("bank_Name")
        public void setBankName(String bank_Name) {
            this.bank_Name = bank_Name;
        }

        @JsonProperty("branch_Name")
        public String getBranchName() {
            return branch_Name;
        }

        @JsonProperty("branch_Name")
        public void setBranchName(String branch_Name) {
            this.branch_Name = branch_Name;
        }

        @JsonProperty("student_Name")
        public String getStudentName() {
            return student_Name;
        }

        @JsonProperty("student_Name")
        public void setStudentName(String student_Name) {
            this.student_Name = student_Name;
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