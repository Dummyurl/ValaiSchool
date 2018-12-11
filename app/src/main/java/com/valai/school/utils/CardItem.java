package com.valai.school.utils;


public class CardItem {

    private String mTextResource;
    private String mTitleResource;
    private String image;
    private Integer orgId;
    private Integer academicId;

    public CardItem(String title, String text, String mImage, Integer orgId, Integer academicId) {
        mTitleResource = title;
        mTextResource = text;
        image = mImage;
        this.orgId = orgId;
        this.academicId = academicId;
    }

    public String getText() {
        return mTextResource;
    }

    public String getTitle() {
        return mTitleResource;
    }

    public String getImage() {
        return image;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public Integer getAcademicId() {
        return academicId;
    }
}
