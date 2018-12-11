/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.valai.school.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_TOP_TITLE_POSITION = "PREF_KEY_TOP_TITLE_POSITION";

    private static final String PREF_KEY_USER_TYPE = "PREF_KEY_USER_TYPE";

    private static final String PREF_KEY_PARENT_SIGN_IN_RESPONSE = "PREF_KEY_PARENT_SIGN_IN_RESPONSE";

    private static final String PREF_KEY_STUDENT_LIST_RESPONSE = "PREF_KEY_STUDENT_LIST_RESPONSE";

    private static final String PREF_KEY_TOKEN = "PREF_KEY_TOKEN";

    private static final String PREF_KEY_ASSIGNMENT_LIST_RESPONSE = "PREF_KEY_ASSIGNMENT_LIST_RESPONSE";

    private static final String PREF_KEY_CIRCULAR_LIST_RESPONSE = "PREF_KEY_CIRCULAR_LIST_RESPONSE";

    private static final String PREF_KEY_DIARY_RESPONSE = "PREF_KEY_DIARY_RESPONSE";

    private static final String PREF_KEY_PAYMENT_RESPONSE = "PREF_KEY_PAYMENT_RESPONSE";

    private static final String PREF_KEY_RECEIPT_RESPONSE = "PREF_KEY_RECEIPT_RESPONSE";

    private static final String PREF_KEY_ATTENDANCE_RESPONSE = "PREF_KEY_ATTENDANCE_RESPONSE";

    private static final String PREF_KEY_MONTH_RESPONSE = "PREF_KEY_MONTH_RESPONSE";

    private static final String PREF_KEY_EXAM_TYPE_RESPONSE = "PREF_KEY_EXAM_TYPE_RESPONSE";

    private static final String PREF_KEY_RESULT_TYPE_RESPONSE = "PREF_KEY_RESULT_TYPE_RESPONSE";

    private static final String PREF_KEY_EXAM_SCHEDULE_RESPONSE = "PREF_KEY_EXAM_SCHEDULE_RESPONSE";

    private static final String PREF_KEY_RESULT_LIST_RESPONSE = "PREF_KEY_RESULT_LIST_RESPONSE";

    private static final String PREF_KEY_TRANSPORT_RESPONSE = "PREF_KEY_TRANSPORT_RESPONSE";

    private static final String PREF_KEY_HOLIDAY_RESPONSE = "PREF_KEY_HOLIDAY_RESPONSE";

    private static final String PREF_KEY_STUDENT_PROFILE_RESPONSE = "PREF_KEY_STUDENT_PROFILE_RESPONSE";

    private static final String PREF_KEY_RESULT_SCHEDULE_RESPONSE = "PREF_KEY_RESULT_SCHEDULE_RESPONSE";

    private static final String PREF_KEY_IS_INTENT_START = "PREF_KEY_IS_INTENT_START";

    private static final String PREF_KEY_CIRCULAR_CLASS_LIST = "PREF_KEY_CIRCULAR_CLASS_LIST";

    private static final String PREF_KEY_CIRCULAR_STAFF_LIST = "PREF_KEY_CIRCULAR_STAFF_LIST";

    private static final String PREF_KEY_ASSIGNMENT_ADMIN_LIST = "PREF_KEY_ASSIGNMENT_ADMIN_LIST";

    private static final String PREF_KEY_ASSIGNMENT_ADMIN_DETAIL_LIST = "PREF_KEY_ASSIGNMENT_ADMIN_DETAIL_LIST";

    private static final String PREF_KEY_CIRCULAR_ADMIN_LIST = "PREF_KEY_CIRCULAR_ADMIN_LIST";

    private static final String PREF_KEY_CIRCULAR_ADMIN_STAFF_LIST = "PREF_KEY_CIRCULAR_ADMIN_STAFF_LIST";

    private static final String PREF_KEY_GET_CLASS_ATTENDANCE_LIST = "PREF_KEY_GET_CLASS_ATTENDANCE_LIST";

    private static final String PREF_KEY_GET_STUDENT_NAME_LIST = "PREF_KEY_GET_STUDENT_NAME_LIST";

    private final SharedPreferences mPrefs;

    public AppPreferencesHelper(Context context, String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void logOutFromPref() {
        //For Parent Preference
        mPrefs.edit().putInt(PREF_KEY_TOP_TITLE_POSITION, -1).apply();
        mPrefs.edit().putInt(PREF_KEY_USER_TYPE, 0).apply();
        mPrefs.edit().putString(PREF_KEY_PARENT_SIGN_IN_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_STUDENT_LIST_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_ASSIGNMENT_LIST_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_CIRCULAR_LIST_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_DIARY_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_PAYMENT_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_RECEIPT_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_ATTENDANCE_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_MONTH_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_EXAM_TYPE_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_RESULT_TYPE_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_EXAM_SCHEDULE_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_RESULT_LIST_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_TRANSPORT_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_HOLIDAY_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_STUDENT_PROFILE_RESPONSE, null).apply();
        mPrefs.edit().putString(PREF_KEY_RESULT_SCHEDULE_RESPONSE, null).apply();
        mPrefs.edit().putBoolean(PREF_KEY_IS_INTENT_START, false).apply();
        //For Admin Preference
        mPrefs.edit().putString(PREF_KEY_CIRCULAR_CLASS_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_CIRCULAR_STAFF_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_ASSIGNMENT_ADMIN_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_ASSIGNMENT_ADMIN_DETAIL_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_CIRCULAR_ADMIN_LIST, null).apply();
        //For Staff Preference
        mPrefs.edit().putString(PREF_KEY_GET_CLASS_ATTENDANCE_LIST, null).apply();
        mPrefs.edit().putString(PREF_KEY_GET_STUDENT_NAME_LIST, null).apply();
    }

    @Override
    public Integer getTopTitlePosition() {
        return mPrefs.getInt(PREF_KEY_TOP_TITLE_POSITION, -1);
    }

    @Override
    public void setTopTitlePosition(Integer userId) {
        mPrefs.edit().putInt(PREF_KEY_TOP_TITLE_POSITION, userId).apply();
    }

    @Override
    public int getUserType() {
        return mPrefs.getInt(PREF_KEY_USER_TYPE, 0);
    }

    @Override
    public void setUserType(int userTypeId) {
        mPrefs.edit().putInt(PREF_KEY_USER_TYPE, userTypeId).apply();
    }

    @Override
    public String getParentSignInResponse() {
        return mPrefs.getString(PREF_KEY_PARENT_SIGN_IN_RESPONSE, null);
    }

    @Override
    public void setParentSignInResponse(String signInResponse) {
        mPrefs.edit().putString(PREF_KEY_PARENT_SIGN_IN_RESPONSE, signInResponse).apply();
    }

    @Override
    public String getStudentListResponse() {
        return mPrefs.getString(PREF_KEY_STUDENT_LIST_RESPONSE, null);
    }

    @Override
    public void setStudentListResponse(String studentListResponse) {
        mPrefs.edit().putString(PREF_KEY_STUDENT_LIST_RESPONSE, studentListResponse).apply();
    }

    @Override
    public String getToken() {
        return mPrefs.getString(PREF_KEY_TOKEN, null);
    }

    @Override
    public void setToken(String token) {
        mPrefs.edit().putString(PREF_KEY_TOKEN, token).apply();
    }

    @Override
    public String getAssignmentListResponse() {
        return mPrefs.getString(PREF_KEY_ASSIGNMENT_LIST_RESPONSE, null);
    }

    @Override
    public void setAssignmentListResponse(String assignmentListResponse) {
        mPrefs.edit().putString(PREF_KEY_ASSIGNMENT_LIST_RESPONSE, assignmentListResponse).apply();
    }

    @Override
    public String getCircularListResponse() {
        return mPrefs.getString(PREF_KEY_CIRCULAR_LIST_RESPONSE, null);
    }

    @Override
    public void setCircularListResponse(String circularListResponse) {
        mPrefs.edit().putString(PREF_KEY_CIRCULAR_LIST_RESPONSE, circularListResponse).apply();
    }

    @Override
    public String getDiaryResponse() {
        return mPrefs.getString(PREF_KEY_DIARY_RESPONSE, null);
    }

    @Override
    public void setDiaryResponse(String diaryResponse) {
        mPrefs.edit().putString(PREF_KEY_DIARY_RESPONSE, diaryResponse).apply();
    }

    @Override
    public String getPaymentResponse() {
        return mPrefs.getString(PREF_KEY_PAYMENT_RESPONSE, null);
    }

    @Override
    public void setPaymentResponse(String paymentResponse) {
        mPrefs.edit().putString(PREF_KEY_PAYMENT_RESPONSE, paymentResponse).apply();
    }

    @Override
    public String getReceiptResponse() {
        return mPrefs.getString(PREF_KEY_RECEIPT_RESPONSE, null);
    }

    @Override
    public void setReceiptResponse(String receiptResponse) {
        mPrefs.edit().putString(PREF_KEY_RECEIPT_RESPONSE, receiptResponse).apply();
    }

    @Override
    public String getAttendanceResponse() {
        return mPrefs.getString(PREF_KEY_ATTENDANCE_RESPONSE, null);
    }

    @Override
    public void setAttendanceResponse(String attendanceResponse) {
        mPrefs.edit().putString(PREF_KEY_ATTENDANCE_RESPONSE, attendanceResponse).apply();
    }

    @Override
    public String getMonthResponse() {
        return mPrefs.getString(PREF_KEY_MONTH_RESPONSE, null);
    }

    @Override
    public void setMonthResponse(String monthResponse) {
        mPrefs.edit().putString(PREF_KEY_MONTH_RESPONSE, monthResponse).apply();
    }

    @Override
    public String getExamTypeResponse() {
        return mPrefs.getString(PREF_KEY_EXAM_TYPE_RESPONSE, null);
    }

    @Override
    public void setExamTypeResponse(String examTypeResponse) {
        mPrefs.edit().putString(PREF_KEY_EXAM_TYPE_RESPONSE, examTypeResponse).apply();
    }

    @Override
    public String getResultTypeResponse() {
        return mPrefs.getString(PREF_KEY_RESULT_TYPE_RESPONSE, null);
    }

    @Override
    public void setResultTypeResponse(String resultTypeResponse) {
        mPrefs.edit().putString(PREF_KEY_RESULT_TYPE_RESPONSE, resultTypeResponse).apply();
    }

    @Override
    public String getExamScheduleResponse() {
        return mPrefs.getString(PREF_KEY_EXAM_SCHEDULE_RESPONSE, null);
    }

    @Override
    public void setExamScheduleResponse(String examScheduleResponse) {
        mPrefs.edit().putString(PREF_KEY_EXAM_SCHEDULE_RESPONSE, examScheduleResponse).apply();
    }

    @Override
    public String getResultListResponse() {
        return mPrefs.getString(PREF_KEY_RESULT_LIST_RESPONSE, null);
    }

    @Override
    public void setResultListResponse(String resultListResponse) {
        mPrefs.edit().putString(PREF_KEY_RESULT_LIST_RESPONSE, resultListResponse).apply();
    }

    @Override
    public String getTransportResponse() {
        return mPrefs.getString(PREF_KEY_TRANSPORT_RESPONSE, null);
    }

    @Override
    public void setTransportResponse(String transportResponse) {
        mPrefs.edit().putString(PREF_KEY_TRANSPORT_RESPONSE, transportResponse).apply();
    }

    @Override
    public String getHolidayListResponse() {
        return mPrefs.getString(PREF_KEY_HOLIDAY_RESPONSE, null);
    }

    @Override
    public void setHolidayListResponse(String holydayListResponse) {
        mPrefs.edit().putString(PREF_KEY_HOLIDAY_RESPONSE, holydayListResponse).apply();
    }

    @Override
    public String getStudentProfileResponse() {
        return mPrefs.getString(PREF_KEY_STUDENT_PROFILE_RESPONSE, null);
    }

    @Override
    public void setStudentProfileResponse(String studentProfileResponse) {
        mPrefs.edit().putString(PREF_KEY_STUDENT_PROFILE_RESPONSE, studentProfileResponse).apply();
    }

    @Override
    public String getResultScheduleResponse() {
        return mPrefs.getString(PREF_KEY_RESULT_SCHEDULE_RESPONSE, null);
    }

    @Override
    public void setResultScheduleResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_RESULT_SCHEDULE_RESPONSE, json).apply();
    }

    @Override
    public void isStartIntent(boolean isStart) {
        mPrefs.edit().putBoolean(PREF_KEY_IS_INTENT_START, isStart).apply();
    }

    @Override
    public boolean getStartIntentResponse() {
        return mPrefs.getBoolean(PREF_KEY_IS_INTENT_START, false);
    }

    @Override
    public void setCircularClassListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_CIRCULAR_CLASS_LIST, json).apply();
    }

    @Override
    public String getCircularClassListResponse() {
        return mPrefs.getString(PREF_KEY_CIRCULAR_CLASS_LIST, null);
    }

    @Override
    public void setCircularStaffListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_CIRCULAR_STAFF_LIST, json).apply();
    }

    @Override
    public String getCircularStaffListResponse() {
        return mPrefs.getString(PREF_KEY_CIRCULAR_STAFF_LIST, null);
    }

    @Override
    public void setAssignmentAdminListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_ASSIGNMENT_ADMIN_LIST, json).apply();
    }

    @Override
    public String getAssignmentAdminListResponse() {
        return mPrefs.getString(PREF_KEY_ASSIGNMENT_ADMIN_LIST, null);
    }

    @Override
    public void setAssignmentAdminDetailListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_ASSIGNMENT_ADMIN_DETAIL_LIST, json).apply();
    }

    @Override
    public String getAssignmentAdminDetailListResponse() {
        return mPrefs.getString(PREF_KEY_ASSIGNMENT_ADMIN_DETAIL_LIST, null);
    }

    @Override
    public void setCircularAdminListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_CIRCULAR_ADMIN_LIST, json).apply();
    }

    @Override
    public String getCircularAdminListResponse() {
        return mPrefs.getString(PREF_KEY_CIRCULAR_ADMIN_LIST, null);
    }

    @Override
    public void setCircularAdminStaffListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_CIRCULAR_ADMIN_STAFF_LIST, json).apply();
    }

    @Override
    public String getCircularAdminStaffListResponse() {
        return mPrefs.getString(PREF_KEY_CIRCULAR_ADMIN_STAFF_LIST, null);
    }

    @Override
    public void setClassAttendanceListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_GET_CLASS_ATTENDANCE_LIST, json).apply();
    }

    @Override
    public String getClassAttendanceListResponse() {
        return mPrefs.getString(PREF_KEY_GET_CLASS_ATTENDANCE_LIST, null);
    }

    @Override
    public void setStudentDetailsListResponse(String json) {
        mPrefs.edit().putString(PREF_KEY_GET_STUDENT_NAME_LIST, json).apply();
    }

    @Override
    public String getStudentDetailsListResponse() {
        return mPrefs.getString(PREF_KEY_GET_STUDENT_NAME_LIST, null);
    }
}