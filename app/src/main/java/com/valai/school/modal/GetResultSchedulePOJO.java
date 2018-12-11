package com.valai.school.modal;

/**
 * @author by Mohit Arora on 1/3/18.
 */

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
public class GetResultSchedulePOJO {

    @JsonProperty("ResponseStatus")
    private String ResponseStatus;
    @JsonProperty("ResponseCode")
    private String ResponseCode;
    @JsonProperty("ResponseMessage")
    private String ResponseMessage;
    @JsonProperty("data")
    private Data data;
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
    public Data getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Data data) {
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
            "result",
            "totalMark_Attain",
            "totalMax_Mark",
            "percentage_obtained",
            "overall_Grade"
    })
    public class Data {

        @JsonProperty("result")
        private List<Result> result = null;
        @JsonProperty("totalMark_Attain")
        private String totalMark_Attain;
        @JsonProperty("totalMax_Mark")
        private String totalMax_Mark;
        @JsonProperty("percentage_obtained")
        private String percentage_obtained;
        @JsonProperty("overall_Grade")
        private String overall_Grade;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("result")
        public List<Result> getResult() {
            return result;
        }

        @JsonProperty("result")
        public void setResult(List<Result> result) {
            this.result = result;
        }

        @JsonProperty("totalMark_Attain")
        public String getTotalMarkAttain() {
            return totalMark_Attain;
        }

        @JsonProperty("totalMark_Attain")
        public void setTotalMarkAttain(String totalMark_Attain) {
            this.totalMark_Attain = totalMark_Attain;
        }

        @JsonProperty("totalMax_Mark")
        public String getTotalMaxMark() {
            return totalMax_Mark;
        }

        @JsonProperty("totalMax_Mark")
        public void setTotalMaxMark(String totalMax_Mark) {
            this.totalMax_Mark = totalMax_Mark;
        }

        @JsonProperty("percentage_obtained")
        public String getPercentageObtained() {
            return percentage_obtained;
        }

        @JsonProperty("percentage_obtained")
        public void setPercentageObtained(String percentage_obtained) {
            this.percentage_obtained = percentage_obtained;
        }

        @JsonProperty("overall_Grade")
        public String getOverallGrade() {
            return overall_Grade;
        }

        @JsonProperty("overall_Grade")
        public void setOverallGrade(String overall_Grade) {
            this.overall_Grade = overall_Grade;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "subject_Name",
            "max_Mark",
            "mark_Attain",
            "grade_Name"
    })
    public class Result {

        @JsonProperty("subject_Name")
        private String subject_Name;
        @JsonProperty("max_Mark")
        private Double max_Mark;
        @JsonProperty("mark_Attain")
        private Double mark_Attain;
        @JsonProperty("grade_Name")
        private String grade_Name;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("subject_Name")
        public String getSubjectName() {
            return subject_Name;
        }

        @JsonProperty("subject_Name")
        public void setSubjectName(String subject_Name) {
            this.subject_Name = subject_Name;
        }

        @JsonProperty("max_Mark")
        public Double getMaxMark() {
            return max_Mark;
        }

        @JsonProperty("max_Mark")
        public void setMaxMark(Double max_Mark) {
            this.max_Mark = max_Mark;
        }

        @JsonProperty("mark_Attain")
        public Double getMarkAttain() {
            return mark_Attain;
        }

        @JsonProperty("mark_Attain")
        public void setMarkAttain(Double mark_Attain) {
            this.mark_Attain = mark_Attain;
        }

        @JsonProperty("grade_Name")
        public String getGradeName() {
            return grade_Name;
        }

        @JsonProperty("grade_Name")
        public void setGradeName(String grade_Name) {
            this.grade_Name = grade_Name;
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



