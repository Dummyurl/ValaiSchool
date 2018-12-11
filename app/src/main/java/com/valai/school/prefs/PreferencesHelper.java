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

/**
 * @author by Mohit Arora on 15/9/17.
 */

public interface PreferencesHelper {

    Integer getTopTitlePosition();

    void setTopTitlePosition(Integer position);

    int getUserType();

    void setUserType(int userType);

    String getParentSignInResponse();

    void setParentSignInResponse(String signInResponse);

    String getStudentListResponse();

    void setStudentListResponse(String signInResponse);

    void logOutFromPref();

    String getToken();

    void setToken(String signInResponse);

    String getAssignmentListResponse();

    void setAssignmentListResponse(String assignmentListResponse);

    String getCircularListResponse();

    void setCircularListResponse(String circularListResponse);

    String getDiaryResponse();

    void setDiaryResponse(String diaryResponse);

    String getPaymentResponse();

    void setPaymentResponse(String paymentResponse);

    String getReceiptResponse();

    void setReceiptResponse(String receiptResponse);

    String getAttendanceResponse();

    void setAttendanceResponse(String attendanceResponse);

    String getMonthResponse();

    void setMonthResponse(String monthResponse);

    String getExamTypeResponse();

    void setExamTypeResponse(String examTypeResponse);

    String getResultTypeResponse();

    void setResultTypeResponse(String resultTypeResponse);

    String getExamScheduleResponse();

    void setExamScheduleResponse(String examScheduleResponse);

    String getResultListResponse();

    void setResultListResponse(String resultListResponse);

    String getTransportResponse();

    void setTransportResponse(String transportResponse);

    String getHolidayListResponse();

    void setHolidayListResponse(String holydayListResponse);

    String getStudentProfileResponse();

    void setStudentProfileResponse(String studentProfileResponse);

    void setResultScheduleResponse(String json);

    String getResultScheduleResponse();

    void isStartIntent(boolean isStart);

    boolean getStartIntentResponse();

    void setCircularClassListResponse(String json);

    String getCircularClassListResponse();

    void setCircularStaffListResponse(String json);

    String getCircularStaffListResponse();

    void setAssignmentAdminListResponse(String json);

    String getAssignmentAdminListResponse();

    void setAssignmentAdminDetailListResponse(String json);

    String getAssignmentAdminDetailListResponse();

    void setCircularAdminListResponse(String json);

    String getCircularAdminListResponse();

    void setCircularAdminStaffListResponse(String json);

    String getCircularAdminStaffListResponse();

    void setClassAttendanceListResponse(String json);

    String getClassAttendanceListResponse();

    void setStudentDetailsListResponse(String json);

    String getStudentDetailsListResponse();
}