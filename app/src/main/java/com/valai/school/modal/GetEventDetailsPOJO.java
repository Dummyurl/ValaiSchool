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
public class GetEventDetailsPOJO {

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
            "title",
            "titlePopup",
            "msgId",
            "description",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile"
    })
    public class Datum {

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("msgId")
        private Integer msgId;
        @JsonProperty("description")
        private String description;
        @JsonProperty("start")
        private String start;
        @JsonProperty("end")
        private String end;
        @JsonProperty("allDay")
        private Integer allDay;
        @JsonProperty("attachment")
        private String attachment;
        @JsonProperty("genFile")
        private String genFile;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("title")
        public String getTitle() {
            return title;
        }

        @JsonProperty("title")
        public void setTitle(String title) {
            this.title = title;
        }

        @JsonProperty("titlePopup")
        public String getTitlePopup() {
            return titlePopup;
        }

        @JsonProperty("titlePopup")
        public void setTitlePopup(String titlePopup) {
            this.titlePopup = titlePopup;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
        }

        @JsonProperty("description")
        public String getDescription() {
            return description;
        }

        @JsonProperty("description")
        public void setDescription(String description) {
            this.description = description;
        }

        @JsonProperty("start")
        public String getStart() {
            return start;
        }

        @JsonProperty("start")
        public void setStart(String start) {
            this.start = start;
        }

        @JsonProperty("end")
        public String getEnd() {
            return end;
        }

        @JsonProperty("end")
        public void setEnd(String end) {
            this.end = end;
        }

        @JsonProperty("allDay")
        public Integer getAllDay() {
            return allDay;
        }

        @JsonProperty("allDay")
        public void setAllDay(Integer allDay) {
            this.allDay = allDay;
        }

        @JsonProperty("attachment")
        public String getAttachment() {
            return attachment;
        }

        @JsonProperty("attachment")
        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        @JsonProperty("genFile")
        public String getGenFile() {
            return genFile;
        }

        @JsonProperty("genFile")
        public void setGenFile(String genFile) {
            this.genFile = genFile;
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