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
public class GetAdminCircularParentPOJO {

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
            "class_Name",
            "branch_Name",
            "section_Name",
            "subject",
            "cir_date",
            "file_name",
            "circular_Id",
            "genfile_name",
            "message"
    })
    public class Datum {

        @JsonProperty("class_Name")
        private String class_Name;
        @JsonProperty("branch_Name")
        private String branch_Name;
        @JsonProperty("section_Name")
        private String section_Name;
        @JsonProperty("subject")
        private String subject;
        @JsonProperty("cir_date")
        private String cir_date;
        @JsonProperty("file_name")
        private String file_name;
        @JsonProperty("circular_Id")
        private Integer circular_Id;
        @JsonProperty("genfile_name")
        private String genfile_name;
        @JsonProperty("message")
        private String message;
        private boolean isImageDownloaded;
        private boolean isPdfDownloaded;
        private boolean isDocDownloaded;
        private boolean isTxtDownloaded;
        private boolean isExcelFileDownloaded;
        private boolean isCsvFileDownloaded;
        private boolean isAudioVideoFileDownloaded;
        private boolean isGifFileDownloaded;

        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("class_Name")
        public String getClassName() {
            return class_Name;
        }

        @JsonProperty("class_Name")
        public void setClassName(String class_Name) {
            this.class_Name = class_Name;
        }

        @JsonProperty("branch_Name")
        public String getBranchName() {
            return branch_Name;
        }

        @JsonProperty("branch_Name")
        public void setBranchName(String branch_Name) {
            this.branch_Name = branch_Name;
        }

        @JsonProperty("section_Name")
        public String getSectionName() {
            return section_Name;
        }

        @JsonProperty("section_Name")
        public void setSectionName(String section_Name) {
            this.section_Name = section_Name;
        }

        @JsonProperty("subject")
        public String getSubject() {
            return subject;
        }

        @JsonProperty("subject")
        public void setSubject(String subject) {
            this.subject = subject;
        }

        @JsonProperty("cir_date")
        public String getCirDate() {
            return cir_date;
        }

        @JsonProperty("cir_date")
        public void setCirDate(String cir_date) {
            this.cir_date = cir_date;
        }

        @JsonProperty("file_name")
        public String getFileName() {
            return file_name;
        }

        @JsonProperty("file_name")
        public void setFileName(String file_name) {
            this.file_name = file_name;
        }

        @JsonProperty("circular_Id")
        public Integer getCircularId() {
            return circular_Id;
        }

        @JsonProperty("circular_Id")
        public void setCircularId(Integer circular_Id) {
            this.circular_Id = circular_Id;
        }

        @JsonProperty("genfile_name")
        public String getGenfileName() {
            return genfile_name;
        }

        @JsonProperty("genfile_name")
        public void setGenfileName(String genfile_name) {
            this.genfile_name = genfile_name;
        }

        @JsonProperty("message")
        public String getMessage() {
            return message;
        }

        @JsonProperty("message")
        public void setMessage(String message) {
            this.message = message;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        public boolean isImageDownloaded() {
            return isImageDownloaded;
        }

        public void setImageDownloaded(boolean imageDownloaded) {
            isImageDownloaded = imageDownloaded;
        }

        public boolean isPdfDownloaded() {
            return isPdfDownloaded;
        }

        public void setPdfDownloaded(boolean pdfDownloaded) {
            isPdfDownloaded = pdfDownloaded;
        }

        public boolean isDocDownloaded() {
            return isDocDownloaded;
        }

        public void setDocDownloaded(boolean docDownloaded) {
            isDocDownloaded = docDownloaded;
        }

        public boolean isTxtDownloaded() {
            return isTxtDownloaded;
        }

        public void setTxtDownloaded(boolean txtDownloaded) {
            isTxtDownloaded = txtDownloaded;
        }

        public boolean isExcelFileDownloaded() {
            return isExcelFileDownloaded;
        }

        public void setExcelFileDownloaded(boolean excelFileDownloaded) {
            isExcelFileDownloaded = excelFileDownloaded;
        }

        public boolean isCsvFileDownloaded() {
            return isCsvFileDownloaded;
        }

        public void setCsvFileDownloaded(boolean csvFileDownloaded) {
            isCsvFileDownloaded = csvFileDownloaded;
        }

        public boolean isAudioVideoDownloaded() {
            return isAudioVideoFileDownloaded;
        }

        public void setAudioVideoDownloaded(boolean isAudioVideoFileDownloaded) {
            this.isAudioVideoFileDownloaded = isAudioVideoFileDownloaded;
        }

        public boolean isGifFileDownloaded() {
            return isGifFileDownloaded;
        }

        public void setGifFileDownloaded(boolean isGifFileDownloaded) {
            this.isGifFileDownloaded = isGifFileDownloaded;
        }
    }
}