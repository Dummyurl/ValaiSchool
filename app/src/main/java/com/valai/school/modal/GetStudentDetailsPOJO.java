package com.valai.school.modal;

/**
 * @author by Mohit Arora on 12/2/18.
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
public class GetStudentDetailsPOJO implements Serializable {

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
            "studentName",
            "photo",
            "className",
            "sectionName",
            "photo1",
            "admission_No",
            "mob_No",
            "date_Of_Birth",
            "blood_Group",
            "email_Id",
            "father_Name",
            "father_Mob_No",
            "mother_Name",
            "mother_Mobile",
            "guardian_Name",
            "guardian_Mobile",
            "father_Email_Id",
            "mother_Email_Id",
            "guardian_Email_Id",
            "medium_Name",
            "Current_Address",
            "Permnt_Address",
            "height",
            "weight",
            "alrg_egg",
            "alrg_fish",
            "alrg_milk",
            "alrg_peanut",
            "alrg_shellfish",
            "alrg_soy",
            "alrg_treenut",
            "alrg_wheat",
            "alrg_dtpvaccine",
            "alrg_poliovaccine",
            "alrg_mealsvaccine",
            "alrg_mumpsvaccine",
            "alrg_rubellavaccine",
            "alrg_varicellavaccine",
            "alrg_hibvaccine",
            "ASalrg_pneumococcalconjugate"
    })
    public class Datum implements Serializable {

        @JsonProperty("studentName")
        private String studentName;
        @JsonProperty("photo")
        private String photo;
        @JsonProperty("className")
        private String className;
        @JsonProperty("sectionName")
        private String sectionName;
        @JsonProperty("photo1")
        private String photo1;
        @JsonProperty("admission_No")
        private String admission_No;
        @JsonProperty("mob_No")
        private String mob_No;
        @JsonProperty("date_Of_Birth")
        private String date_Of_Birth;
        @JsonProperty("blood_Group")
        private String blood_Group;
        @JsonProperty("email_Id")
        private String email_Id;
        @JsonProperty("father_Name")
        private String father_Name;
        @JsonProperty("father_Mob_No")
        private String father_Mob_No;
        @JsonProperty("mother_Name")
        private String mother_Name;
        @JsonProperty("mother_Mobile")
        private String mother_Mobile;
        @JsonProperty("guardian_Name")
        private String guardian_Name;
        @JsonProperty("guardian_Mobile")
        private String guardian_Mobile;
        @JsonProperty("father_Email_Id")
        private String father_Email_Id;
        @JsonProperty("mother_Email_Id")
        private String mother_Email_Id;
        @JsonProperty("guardian_Email_Id")
        private String guardian_Email_Id;
        @JsonProperty("medium_Name")
        private String medium_Name;
        @JsonProperty("Current_Address")
        private Object Current_Address;
        @JsonProperty("Permnt_Address")
        private String Permnt_Address;
        @JsonProperty("height")
        private Object height;
        @JsonProperty("weight")
        private Object weight;
        @JsonProperty("alrg_egg")
        private Integer alrg_egg;
        @JsonProperty("alrg_fish")
        private Integer alrg_fish;
        @JsonProperty("alrg_milk")
        private Integer alrg_milk;
        @JsonProperty("alrg_peanut")
        private Integer alrg_peanut;
        @JsonProperty("alrg_shellfish")
        private Integer alrg_shellfish;
        @JsonProperty("alrg_soy")
        private Integer alrg_soy;
        @JsonProperty("alrg_treenut")
        private Integer alrg_treenut;
        @JsonProperty("alrg_wheat")
        private Integer alrg_wheat;
        @JsonProperty("alrg_dtpvaccine")
        private Integer alrg_dtpvaccine;
        @JsonProperty("alrg_poliovaccine")
        private Integer alrg_poliovaccine;
        @JsonProperty("alrg_mealsvaccine")
        private Integer alrg_mealsvaccine;
        @JsonProperty("alrg_mumpsvaccine")
        private Integer alrg_mumpsvaccine;
        @JsonProperty("alrg_rubellavaccine")
        private Integer alrg_rubellavaccine;
        @JsonProperty("alrg_varicellavaccine")
        private Integer alrg_varicellavaccine;
        @JsonProperty("alrg_hibvaccine")
        private Integer alrg_hibvaccine;
        @JsonProperty("ASalrg_pneumococcalconjugate")
        private Integer ASalrg_pneumococcalconjugate;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("studentName")
        public String getStudentName() {
            return studentName;
        }

        @JsonProperty("studentName")
        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        @JsonProperty("photo")
        public String getPhoto() {
            return photo;
        }

        @JsonProperty("photo")
        public void setPhoto(String photo) {
            this.photo = photo;
        }

        @JsonProperty("className")
        public String getClassName() {
            return className;
        }

        @JsonProperty("className")
        public void setClassName(String className) {
            this.className = className;
        }

        @JsonProperty("sectionName")
        public String getSectionName() {
            return sectionName;
        }

        @JsonProperty("sectionName")
        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        @JsonProperty("photo1")
        public String getPhoto1() {
            return photo1;
        }

        @JsonProperty("photo1")
        public void setPhoto1(String photo1) {
            this.photo1 = photo1;
        }

        @JsonProperty("admission_No")
        public String getAdmissionNo() {
            return admission_No;
        }

        @JsonProperty("admission_No")
        public void setAdmissionNo(String admission_No) {
            this.admission_No = admission_No;
        }

        @JsonProperty("mob_No")
        public String getMobNo() {
            return mob_No;
        }

        @JsonProperty("mob_No")
        public void setMobNo(String mob_No) {
            this.mob_No = mob_No;
        }

        @JsonProperty("date_Of_Birth")
        public String getDateOfBirth() {
            return date_Of_Birth;
        }

        @JsonProperty("date_Of_Birth")
        public void setDateOfBirth(String date_Of_Birth) {
            this.date_Of_Birth = date_Of_Birth;
        }

        @JsonProperty("blood_Group")
        public String getBloodGroup() {
            return blood_Group;
        }

        @JsonProperty("blood_Group")
        public void setBloodGroup(String blood_Group) {
            this.blood_Group = blood_Group;
        }

        @JsonProperty("email_Id")
        public String getEmailId() {
            return email_Id;
        }

        @JsonProperty("email_Id")
        public void setEmailId(String email_Id) {
            this.email_Id = email_Id;
        }

        @JsonProperty("father_Name")
        public String getFatherName() {
            return father_Name;
        }

        @JsonProperty("father_Name")
        public void setFatherName(String father_Name) {
            this.father_Name = father_Name;
        }

        @JsonProperty("father_Mob_No")
        public String getFatherMobNo() {
            return father_Mob_No;
        }

        @JsonProperty("father_Mob_No")
        public void setFatherMobNo(String father_Mob_No) {
            this.father_Mob_No = father_Mob_No;
        }

        @JsonProperty("mother_Name")
        public String getMotherName() {
            return mother_Name;
        }

        @JsonProperty("mother_Name")
        public void setMotherName(String mother_Name) {
            this.mother_Name = mother_Name;
        }

        @JsonProperty("mother_Mobile")
        public String getMotherMobile() {
            return mother_Mobile;
        }

        @JsonProperty("mother_Mobile")
        public void setMotherMobile(String mother_Mobile) {
            this.mother_Mobile = mother_Mobile;
        }

        @JsonProperty("guardian_Name")
        public String getGuardianName() {
            return guardian_Name;
        }

        @JsonProperty("guardian_Name")
        public void setGuardianName(String guardian_Name) {
            this.guardian_Name = guardian_Name;
        }

        @JsonProperty("guardian_Mobile")
        public String getGuardianMobile() {
            return guardian_Mobile;
        }

        @JsonProperty("guardian_Mobile")
        public void setGuardianMobile(String guardian_Mobile) {
            this.guardian_Mobile = guardian_Mobile;
        }

        @JsonProperty("father_Email_Id")
        public String getFatherEmailId() {
            return father_Email_Id;
        }

        @JsonProperty("father_Email_Id")
        public void setFatherEmailId(String father_Email_Id) {
            this.father_Email_Id = father_Email_Id;
        }

        @JsonProperty("mother_Email_Id")
        public String getMotherEmailId() {
            return mother_Email_Id;
        }

        @JsonProperty("mother_Email_Id")
        public void setMotherEmailId(String mother_Email_Id) {
            this.mother_Email_Id = mother_Email_Id;
        }

        @JsonProperty("guardian_Email_Id")
        public String getGuardianEmailId() {
            return guardian_Email_Id;
        }

        @JsonProperty("guardian_Email_Id")
        public void setGuardianEmailId(String guardian_Email_Id) {
            this.guardian_Email_Id = guardian_Email_Id;
        }

        @JsonProperty("medium_Name")
        public String getMediumName() {
            return medium_Name;
        }

        @JsonProperty("medium_Name")
        public void setMediumName(String medium_Name) {
            this.medium_Name = medium_Name;
        }

        @JsonProperty("Current_Address")
        public Object getCurrentAddress() {
            return Current_Address;
        }

        @JsonProperty("Current_Address")
        public void setCurrentAddress(Object Current_Address) {
            this.Current_Address = Current_Address;
        }

        @JsonProperty("Permnt_Address")
        public String getPermntAddress() {
            return Permnt_Address;
        }

        @JsonProperty("Permnt_Address")
        public void setPermntAddress(String Permnt_Address) {
            this.Permnt_Address = Permnt_Address;
        }

        @JsonProperty("height")
        public Object getHeight() {
            return height;
        }

        @JsonProperty("height")
        public void setHeight(Object height) {
            this.height = height;
        }

        @JsonProperty("weight")
        public Object getWeight() {
            return weight;
        }

        @JsonProperty("weight")
        public void setWeight(Object weight) {
            this.weight = weight;
        }

        @JsonProperty("alrg_egg")
        public Integer getAlrgEgg() {
            return alrg_egg;
        }

        @JsonProperty("alrg_egg")
        public void setAlrgEgg(Integer alrg_egg) {
            this.alrg_egg = alrg_egg;
        }

        @JsonProperty("alrg_fish")
        public Integer getAlrgFish() {
            return alrg_fish;
        }

        @JsonProperty("alrg_fish")
        public void setAlrgFish(Integer alrg_fish) {
            this.alrg_fish = alrg_fish;
        }

        @JsonProperty("alrg_milk")
        public Integer getAlrgMilk() {
            return alrg_milk;
        }

        @JsonProperty("alrg_milk")
        public void setAlrgMilk(Integer alrg_milk) {
            this.alrg_milk = alrg_milk;
        }

        @JsonProperty("alrg_peanut")
        public Integer getAlrgPeanut() {
            return alrg_peanut;
        }

        @JsonProperty("alrg_peanut")
        public void setAlrgPeanut(Integer alrg_peanut) {
            this.alrg_peanut = alrg_peanut;
        }

        @JsonProperty("alrg_shellfish")
        public Integer getAlrgShellfish() {
            return alrg_shellfish;
        }

        @JsonProperty("alrg_shellfish")
        public void setAlrgShellfish(Integer alrg_shellfish) {
            this.alrg_shellfish = alrg_shellfish;
        }

        @JsonProperty("alrg_soy")
        public Integer getAlrgSoy() {
            return alrg_soy;
        }

        @JsonProperty("alrg_soy")
        public void setAlrgSoy(Integer alrg_soy) {
            this.alrg_soy = alrg_soy;
        }

        @JsonProperty("alrg_treenut")
        public Integer getAlrgTreenut() {
            return alrg_treenut;
        }

        @JsonProperty("alrg_treenut")
        public void setAlrgTreenut(Integer alrg_treenut) {
            this.alrg_treenut = alrg_treenut;
        }

        @JsonProperty("alrg_wheat")
        public Integer getAlrgWheat() {
            return alrg_wheat;
        }

        @JsonProperty("alrg_wheat")
        public void setAlrgWheat(Integer alrg_wheat) {
            this.alrg_wheat = alrg_wheat;
        }

        @JsonProperty("alrg_dtpvaccine")
        public Integer getAlrgDtpvaccine() {
            return alrg_dtpvaccine;
        }

        @JsonProperty("alrg_dtpvaccine")
        public void setAlrgDtpvaccine(Integer alrg_dtpvaccine) {
            this.alrg_dtpvaccine = alrg_dtpvaccine;
        }

        @JsonProperty("alrg_poliovaccine")
        public Integer getAlrgPoliovaccine() {
            return alrg_poliovaccine;
        }

        @JsonProperty("alrg_poliovaccine")
        public void setAlrgPoliovaccine(Integer alrg_poliovaccine) {
            this.alrg_poliovaccine = alrg_poliovaccine;
        }

        @JsonProperty("alrg_mealsvaccine")
        public Integer getAlrgMealsvaccine() {
            return alrg_mealsvaccine;
        }

        @JsonProperty("alrg_mealsvaccine")
        public void setAlrgMealsvaccine(Integer alrg_mealsvaccine) {
            this.alrg_mealsvaccine = alrg_mealsvaccine;
        }

        @JsonProperty("alrg_mumpsvaccine")
        public Integer getAlrgMumpsvaccine() {
            return alrg_mumpsvaccine;
        }

        @JsonProperty("alrg_mumpsvaccine")
        public void setAlrgMumpsvaccine(Integer alrg_mumpsvaccine) {
            this.alrg_mumpsvaccine = alrg_mumpsvaccine;
        }

        @JsonProperty("alrg_rubellavaccine")
        public Integer getAlrgRubellavaccine() {
            return alrg_rubellavaccine;
        }

        @JsonProperty("alrg_rubellavaccine")
        public void setAlrgRubellavaccine(Integer alrg_rubellavaccine) {
            this.alrg_rubellavaccine = alrg_rubellavaccine;
        }

        @JsonProperty("alrg_varicellavaccine")
        public Integer getAlrgVaricellavaccine() {
            return alrg_varicellavaccine;
        }

        @JsonProperty("alrg_varicellavaccine")
        public void setAlrgVaricellavaccine(Integer alrg_varicellavaccine) {
            this.alrg_varicellavaccine = alrg_varicellavaccine;
        }

        @JsonProperty("alrg_hibvaccine")
        public Integer getAlrgHibvaccine() {
            return alrg_hibvaccine;
        }

        @JsonProperty("alrg_hibvaccine")
        public void setAlrgHibvaccine(Integer alrg_hibvaccine) {
            this.alrg_hibvaccine = alrg_hibvaccine;
        }

        @JsonProperty("ASalrg_pneumococcalconjugate")
        public Integer getASalrgPneumococcalconjugate() {
            return ASalrg_pneumococcalconjugate;
        }

        @JsonProperty("ASalrg_pneumococcalconjugate")
        public void setASalrgPneumococcalconjugate(Integer ASalrg_pneumococcalconjugate) {
            this.ASalrg_pneumococcalconjugate = ASalrg_pneumococcalconjugate;
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