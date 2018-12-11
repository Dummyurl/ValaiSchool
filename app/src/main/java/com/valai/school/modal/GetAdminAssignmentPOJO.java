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
public class GetAdminAssignmentPOJO {

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
            "section_Id",
            "login_Id",
            "assignment_Id",
            "subject",
            "is_attachment",
            "file_name",
            "gen_filename",
            "assignment_Date",
            "submission_Date",
            "class_Name",
            "branch_Name",
            "section_Name",
            "subject_Id",
            "chapter_Id",
            "message",
            "subChapter_Id"

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
        @JsonProperty("section_Id")
        private Integer section_Id;
        @JsonProperty("login_Id")
        private Integer login_Id;
        @JsonProperty("assignment_Id")
        private Integer assignment_Id;
        @JsonProperty("subject")
        private String subject;
        @JsonProperty("is_attachment")
        private Integer is_attachment;
        @JsonProperty("file_name")
        private String file_name;
        @JsonProperty("gen_filename")
        private String gen_filename;
        @JsonProperty("assignment_Date")
        private String assignment_Date;
        @JsonProperty("submission_Date")
        private String submission_Date;
        @JsonProperty("class_Name")
        private String class_Name;
        @JsonProperty("branch_Name")
        private String branch_Name;
        @JsonProperty("section_Name")
        private String section_Name;
        @JsonProperty("subject_Id")
        private Integer subject_Id;
        @JsonProperty("chapter_Id")
        private Integer chapter_Id;
        @JsonProperty("subChapter_Id")
        private Integer subChapter_Id;
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

        @JsonProperty("section_Id")
        public Integer getSectionId() {
            return section_Id;
        }

        @JsonProperty("section_Id")
        public void setSectionId(Integer section_Id) {
            this.section_Id = section_Id;
        }

        @JsonProperty("login_Id")
        public Integer getLoginId() {
            return login_Id;
        }

        @JsonProperty("login_Id")
        public void setLoginId(Integer login_Id) {
            this.login_Id = login_Id;
        }

        @JsonProperty("assignment_Id")
        public Integer getAssignmentId() {
            return assignment_Id;
        }

        @JsonProperty("assignment_Id")
        public void setAssignmentId(Integer assignment_Id) {
            this.assignment_Id = assignment_Id;
        }

        @JsonProperty("subject")
        public String getSubject() {
            return subject;
        }

        @JsonProperty("subject")
        public void setSubject(String subject) {
            this.subject = subject;
        }

        @JsonProperty("is_attachment")
        public Integer getIsAttachment() {
            return is_attachment;
        }

        @JsonProperty("is_attachment")
        public void setIsAttachment(Integer is_attachment) {
            this.is_attachment = is_attachment;
        }

        @JsonProperty("file_name")
        public String getFileName() {
            return file_name;
        }

        @JsonProperty("file_name")
        public void setFileName(String file_name) {
            this.file_name = file_name;
        }

        @JsonProperty("gen_filename")
        public String getGenFilename() {
            return gen_filename;
        }

        @JsonProperty("gen_filename")
        public void setGenFilename(String gen_filename) {
            this.gen_filename = gen_filename;
        }

        @JsonProperty("assignment_Date")
        public String getAssignmentDate() {
            return assignment_Date;
        }

        @JsonProperty("assignment_Date")
        public void setAssignmentDate(String assignment_Date) {
            this.assignment_Date = assignment_Date;
        }

        @JsonProperty("submission_Date")
        public String getSubmissionDate() {
            return submission_Date;
        }

        @JsonProperty("submission_Date")
        public void setSubmissionDate(String submission_Date) {
            this.submission_Date = submission_Date;
        }

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