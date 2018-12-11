package com.valai.school.modal;

/*
 * @author by Mohit Arora on 13/2/18.
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
public class GetTransportPOJO {

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
            "pickup_routeNo",
            "pickup_driver",
            "pickup_drivermobile",
            "pickup_helper",
            "pickup_helpermobile",
            "pickupstop",
            "pickup_Time",
            "pickUp_Bus_No",
            "drop_routeNo",
            "drop_driver",
            "drop_drivermobile",
            "drop_helper",
            "drop_helpermobile",
            "dropstop",
            "drop_Time",
            "drop_Bus_No"
    })
    public class Datum {

        @JsonProperty("pickup_routeNo")
        private String pickup_routeNo;
        @JsonProperty("pickup_driver")
        private String pickup_driver;
        @JsonProperty("pickup_drivermobile")
        private String pickup_drivermobile;
        @JsonProperty("pickup_helper")
        private String pickup_helper;
        @JsonProperty("pickup_helpermobile")
        private String pickup_helpermobile;
        @JsonProperty("pickupstop")
        private String pickupstop;
        @JsonProperty("pickup_Time")
        private String pickup_Time;
        @JsonProperty("pickUp_Bus_No")
        private String pickUp_Bus_No;
        @JsonProperty("drop_routeNo")
        private String drop_routeNo;
        @JsonProperty("drop_driver")
        private String drop_driver;
        @JsonProperty("drop_drivermobile")
        private String drop_drivermobile;
        @JsonProperty("drop_helper")
        private String drop_helper;
        @JsonProperty("drop_helpermobile")
        private String drop_helpermobile;
        @JsonProperty("dropstop")
        private String dropstop;
        @JsonProperty("drop_Time")
        private String drop_Time;
        @JsonProperty("drop_Bus_No")
        private String drop_Bus_No;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("pickup_routeNo")
        public String getPickupRouteNo() {
            return pickup_routeNo;
        }

        @JsonProperty("pickup_routeNo")
        public void setPickupRouteNo(String pickup_routeNo) {
            this.pickup_routeNo = pickup_routeNo;
        }

        @JsonProperty("pickup_driver")
        public String getPickupDriver() {
            return pickup_driver;
        }

        @JsonProperty("pickup_driver")
        public void setPickupDriver(String pickup_driver) {
            this.pickup_driver = pickup_driver;
        }

        @JsonProperty("pickup_drivermobile")
        public String getPickupDrivermobile() {
            return pickup_drivermobile;
        }

        @JsonProperty("pickup_drivermobile")
        public void setPickupDrivermobile(String pickup_drivermobile) {
            this.pickup_drivermobile = pickup_drivermobile;
        }

        @JsonProperty("pickup_helper")
        public String getPickupHelper() {
            return pickup_helper;
        }

        @JsonProperty("pickup_helper")
        public void setPickupHelper(String pickup_helper) {
            this.pickup_helper = pickup_helper;
        }

        @JsonProperty("pickup_helpermobile")
        public String getPickupHelpermobile() {
            return pickup_helpermobile;
        }

        @JsonProperty("pickup_helpermobile")
        public void setPickupHelpermobile(String pickup_helpermobile) {
            this.pickup_helpermobile = pickup_helpermobile;
        }

        @JsonProperty("pickupstop")
        public String getPickupstop() {
            return pickupstop;
        }

        @JsonProperty("pickupstop")
        public void setPickupstop(String pickupstop) {
            this.pickupstop = pickupstop;
        }

        @JsonProperty("pickup_Time")
        public String getPickupTime() {
            return pickup_Time;
        }

        @JsonProperty("pickup_Time")
        public void setPickupTime(String pickup_Time) {
            this.pickup_Time = pickup_Time;
        }

        @JsonProperty("pickUp_Bus_No")
        public String getPickupBusNo() {
            return pickUp_Bus_No;
        }

        @JsonProperty("pickUp_Bus_No")
        public void setPickupBusNo(String pickUp_Bus_No) {
            this.pickUp_Bus_No = pickUp_Bus_No;
        }

        @JsonProperty("drop_routeNo")
        public String getDropRouteNo() {
            return drop_routeNo;
        }

        @JsonProperty("drop_routeNo")
        public void setDropRouteNo(String drop_routeNo) {
            this.drop_routeNo = drop_routeNo;
        }

        @JsonProperty("drop_driver")
        public String getDropDriver() {
            return drop_driver;
        }

        @JsonProperty("drop_driver")
        public void setDropDriver(String drop_driver) {
            this.drop_driver = drop_driver;
        }

        @JsonProperty("drop_drivermobile")
        public String getDropDrivermobile() {
            return drop_drivermobile;
        }

        @JsonProperty("drop_drivermobile")
        public void setDropDrivermobile(String drop_drivermobile) {
            this.drop_drivermobile = drop_drivermobile;
        }

        @JsonProperty("drop_helper")
        public String getDropHelper() {
            return drop_helper;
        }

        @JsonProperty("drop_helper")
        public void setDropHelper(String drop_helper) {
            this.drop_helper = drop_helper;
        }

        @JsonProperty("drop_helpermobile")
        public String getDropHelpermobile() {
            return drop_helpermobile;
        }

        @JsonProperty("drop_helpermobile")
        public void setDropHelpermobile(String drop_helpermobile) {
            this.drop_helpermobile = drop_helpermobile;
        }

        @JsonProperty("dropstop")
        public String getDropstop() {
            return dropstop;
        }

        @JsonProperty("dropstop")
        public void setDropstop(String dropstop) {
            this.dropstop = dropstop;
        }

        @JsonProperty("drop_Time")
        public String getDropTime() {
            return drop_Time;
        }

        @JsonProperty("drop_Time")
        public void setDropTime(String drop_Time) {
            this.drop_Time = drop_Time;
        }

        @JsonProperty("drop_Bus_No")
        public String getDropBusNo() {
            return drop_Bus_No;
        }

        @JsonProperty("drop_Bus_No")
        public void setDropBusNo(String drop_Bus_No) {
            this.drop_Bus_No = drop_Bus_No;
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