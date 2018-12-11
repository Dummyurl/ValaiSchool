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

import java.io.Serializable;
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
public class GetDiaryPOJO implements Serializable{

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
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
    })
    public class Data implements Serializable{

        @JsonProperty("Jan")
        private List<Jan> Jan = null;
        @JsonProperty("Feb")
        private List<Feb> Feb = null;
        @JsonProperty("Mar")
        private List<Mar> Mar = null;
        @JsonProperty("Apr")
        private List<Apr> Apr = null;
        @JsonProperty("May")
        private List<May> May = null;
        @JsonProperty("Jun")
        private List<Jun> Jun = null;
        @JsonProperty("Jul")
        private List<Jul> Jul = null;
        @JsonProperty("Aug")
        private List<Aug> Aug = null;
        @JsonProperty("Sep")
        private List<Sep> Sep = null;
        @JsonProperty("Oct")
        private List<Oct> Oct = null;
        @JsonProperty("Nov")
        private List<Nov> Nov = null;
        @JsonProperty("Dec")
        private List<Dec> Dec = null;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("Jan")
        public List<Jan> getJan() {
            return Jan;
        }

        @JsonProperty("Jan")
        public void setJan(List<Jan> Jan) {
            this.Jan = Jan;
        }

        @JsonProperty("Feb")
        public List<Feb> getFeb() {
            return Feb;
        }

        @JsonProperty("Feb")
        public void setFeb(List<Feb> Feb) {
            this.Feb = Feb;
        }

        @JsonProperty("Mar")
        public List<Mar> getMar() {
            return Mar;
        }

        @JsonProperty("Mar")
        public void setMar(List<Mar> Mar) {
            this.Mar = Mar;
        }

        @JsonProperty("Apr")
        public List<Apr> getApr() {
            return Apr;
        }

        @JsonProperty("Apr")
        public void setApr(List<Apr> Apr) {
            this.Apr = Apr;
        }

        @JsonProperty("May")
        public List<May> getMay() {
            return May;
        }

        @JsonProperty("May")
        public void setMay(List<May> May) {
            this.May = May;
        }

        @JsonProperty("Jun")
        public List<Jun> getJun() {
            return Jun;
        }

        @JsonProperty("Jun")
        public void setJun(List<Jun> Jun) {
            this.Jun = Jun;
        }

        @JsonProperty("Jul")
        public List<Jul> getJul() {
            return Jul;
        }

        @JsonProperty("Jul")
        public void setJul(List<Jul> Jul) {
            this.Jul = Jul;
        }

        @JsonProperty("Aug")
        public List<Aug> getAug() {
            return Aug;
        }

        @JsonProperty("Aug")
        public void setAug(List<Aug> Aug) {
            this.Aug = Aug;
        }

        @JsonProperty("Sep")
        public List<Sep> getSep() {
            return Sep;
        }

        @JsonProperty("Sep")
        public void setSep(List<Sep> Sep) {
            this.Sep = Sep;
        }

        @JsonProperty("Oct")
        public List<Oct> getOct() {
            return Oct;
        }

        @JsonProperty("Oct")
        public void setOct(List<Oct> Oct) {
            this.Oct = Oct;
        }

        @JsonProperty("Nov")
        public List<Nov> getNov() {
            return Nov;
        }

        @JsonProperty("Nov")
        public void setNov(List<Nov> Nov) {
            this.Nov = Nov;
        }

        @JsonProperty("Dec")
        public List<Dec> getDec() {
            return Dec;
        }

        @JsonProperty("Dec")
        public void setDec(List<Dec> Dec) {
            this.Dec = Dec;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class Apr implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class Aug implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class Dec implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class Feb implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class Jan implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class Jul implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class Jun implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class Mar implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class May implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class Nov implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class Oct implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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
            "title",
            "titlePopup",
            "color",
            "msgId",
            "start",
            "end",
            "allDay",
            "attachment",
            "genFile",
            "is_Holiday"
    })
    public class Sep implements Serializable{

        @JsonProperty("title")
        private String title;
        @JsonProperty("titlePopup")
        private String titlePopup;
        @JsonProperty("color")
        private String color;
        @JsonProperty("msgId")
        private Integer msgId;
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
        @JsonProperty("is_Holiday")
        private Integer is_Holiday;
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

        @JsonProperty("color")
        public String getColor() {
            return color;
        }

        @JsonProperty("color")
        public void setColor(String color) {
            this.color = color;
        }

        @JsonProperty("msgId")
        public Integer getMsgId() {
            return msgId;
        }

        @JsonProperty("msgId")
        public void setMsgId(Integer msgId) {
            this.msgId = msgId;
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

        @JsonProperty("is_Holiday")
        public Integer getIsHoliday() {
            return is_Holiday;
        }

        @JsonProperty("is_Holiday")
        public void setIsHoliday(Integer is_Holiday) {
            this.is_Holiday = is_Holiday;
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

