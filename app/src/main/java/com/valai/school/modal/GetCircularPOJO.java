package com.valai.school.modal;


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
public class GetCircularPOJO {

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
            "academic_Id",
            "class_Id",
            "class_Name",
            "branch_Id",
            "branch_Name",
            "section_Id",
            "section_Name",
            "circular_Id",
            "subject",
            "cir_date",
            "file_name",
            "genfile_name",
            "read_flag",
            "star_flag",
            "message"
    })
    public static class Datum {

        @JsonProperty("academic_Id")
        private Integer academic_Id;
        @JsonProperty("class_Id")
        private Integer class_Id;
        @JsonProperty("class_Name")
        private String class_Name;
        @JsonProperty("branch_Id")
        private Integer branch_Id;
        @JsonProperty("branch_Name")
        private Object branch_Name;
        @JsonProperty("section_Id")
        private Integer section_Id;
        @JsonProperty("section_Name")
        private String section_Name;
        @JsonProperty("circular_Id")
        private Integer circular_Id;
        @JsonProperty("subject")
        private String subject;
        @JsonProperty("cir_date")
        private String cir_date;
        @JsonProperty("file_name")
        private String file_name;
        @JsonProperty("genfile_name")
        private String genfile_name;
        @JsonProperty("read_flag")
        private Integer read_flag;
        @JsonProperty("star_flag")
        private Integer star_flag;
        @JsonProperty("message")
        private String message;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();
        private boolean isImageDownloaded;
        private boolean isPdfDownloaded;
        private boolean isDocDownloaded;
        private boolean isTxtDownloaded;
        private boolean isExcelFileDownloaded;
        private boolean isCsvFileDownloaded;
        private boolean isAudioVideoFileDownloaded;
        private boolean isGifFileDownloaded;

        public boolean isCsvFileDownloaded() {
            return isCsvFileDownloaded;
        }

        public void setCsvFileDownloaded(boolean isCsvFileDownloaded) {
            this.isCsvFileDownloaded = isCsvFileDownloaded;
        }

        public boolean isExcelFileDownloaded() {
            return isExcelFileDownloaded;
        }

        public void setExcelFileDownloaded(boolean isExcelFileDownloaded) {
            this.isExcelFileDownloaded = isExcelFileDownloaded;
        }

        public boolean isDocDownloaded() {
            return isDocDownloaded;
        }

        public void setDocDownloaded(boolean isDocDownloaded) {
            this.isDocDownloaded = isDocDownloaded;
        }

        public boolean isTxtDownloaded() {
            return isTxtDownloaded;
        }

        public void setTxtDownloaded(boolean isTxtDownloaded) {
            this.isTxtDownloaded = isTxtDownloaded;
        }

        public boolean isImageDownloaded() {
            return isImageDownloaded;
        }

        public void setImageDownloaded(boolean isImageDownloaded) {
            this.isImageDownloaded = isImageDownloaded;
        }

        public boolean isPdfDownloaded() {
            return isPdfDownloaded;
        }

        public void setPdfDownloaded(boolean isPdfDownloaded) {
            this.isPdfDownloaded = isPdfDownloaded;
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

        @JsonProperty("branch_Name")
        public Object getBranchName() {
            return branch_Name;
        }

        @JsonProperty("branch_Name")
        public void setBranchName(Object branch_Name) {
            this.branch_Name = branch_Name;
        }

        @JsonProperty("section_Id")
        public Integer getSectionId() {
            return section_Id;
        }

        @JsonProperty("section_Id")
        public void setSectionId(Integer section_Id) {
            this.section_Id = section_Id;
        }

        @JsonProperty("section_Name")
        public String getSectionName() {
            return section_Name;
        }

        @JsonProperty("section_Name")
        public void setSectionName(String section_Name) {
            this.section_Name = section_Name;
        }

        @JsonProperty("circular_Id")
        public Integer getCircularId() {
            return circular_Id;
        }

        @JsonProperty("circular_Id")
        public void setCircularId(Integer circular_Id) {
            this.circular_Id = circular_Id;
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

        @JsonProperty("genfile_name")
        public String getGenfileName() {
            return genfile_name;
        }

        @JsonProperty("genfile_name")
        public void setGenfileName(String genfile_name) {
            this.genfile_name = genfile_name;
        }

        @JsonProperty("read_flag")
        public Integer getReadFlag() {
            return read_flag;
        }

        @JsonProperty("read_flag")
        public void setReadFlag(Integer read_flag) {
            this.read_flag = read_flag;
        }

        @JsonProperty("star_flag")
        public Integer getStarFlag() {
            return star_flag;
        }

        @JsonProperty("star_flag")
        public void setStarFlag(Integer star_flag) {
            this.star_flag = star_flag;
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

    }
}