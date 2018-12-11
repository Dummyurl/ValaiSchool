package com.valai.school.modal;

import java.io.Serializable;

/**
 * @author by Mohit Arora on 5/4/18.
 */

public class ParentCircularModal implements Serializable {
    private String title;
    private Integer userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}