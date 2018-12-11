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

package com.valai.school.utils;

public final class AppConstants {
    // Splash Screen Timer Delay
    public static final int SPLASH_TIME_OUT = 3000;
    // Menu Items Fragments Timer Delay
    public static final int DELAY_TIME_OUT = 250;
    // Request API RESPONSE CODE
    public static final int RESPONSE_CODE = 200;

    // Request API RESPONSE CODE
    public static final String API_RESPONSE_CODE_WITH_DATA = "0";
    public static final String API_RESPONSE_CODE_NO_DATA = "501";

    public static final int REQUEST_PERMISSION_CODE_STORAGE = 101;
    public static final int REQUEST_PERMISSION_CODE_READ_PHONE_STATE = 102;
    public static final int REQUEST_PERMISSION_CODE_STORAGE_FOR_PROFILE = 103;
    public static final int REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS = 104;
    public static final int REQUEST_PERMISSION_RECORD_AUDIO = 105;
    public static final int REQUEST_CHOOSER = 1234;
    public static final int REQUEST_SELECT_PICTURE = 0x01;
    public static final int REQUEST_CAMERA = 102;
    public static final int REQUEST_VIDEO_CAPTURE = 105;
    public static final int REQUEST_PERMISSION_CODE_CAMERA = 1023;
    public static final int REQUEST_DOC_FILE = 1222;


    public static final int USER_TYPE_ID_FOR_ADMIN_SCHOOL = 1;
    public static final int USER_TYPE_ID_FOR_TEACHER_STAFF = 3;
    public static final int USER_TYPE_ID_FOR_PARENT = 6;

    public static final String TAG_HOME = "Home";
    public static final String TAG_DIARY = "Diary";
    public static final String TAG_MESSAGE = "Message";
    public static final String TAG_FEE = "Fee";
    public static final String TAG_ATTENDANCE = "Attendance";
    public static final String TAG_EXAM_SCHEDULE = "Exam Schedule";
    public static final String TAG_TRANSPORT = "Transport";
    public static final String TAG_HOLIDAY = "Holiday";
    public static final String TAG_PROFILE = "Profile";
    public static final String TAG_CHANGE_PASSWORD = "Change Password";

    public static final String TAG_CALENDAR = "Calendar";
    public static final String TAG_MARK_ENTRY = "Mark Entry";
    public static final String TAG_REPORTCARD_ATTENDANCE = "Report Card Attendance";
    public static final String TAG_STUDENT_DATA = "Student Data";
    public static final String TAG_TIME_TABLE = "Time Table";
    public static final String TAG_LESSON_PLANER = "Lesson Planner";
    public static final String TAG_PAY_SLIP = "Pay Slip";
    public static final String TAG_LEAVE_APPLICATION = "Leave Application";

    public static final String ORIGINNAME_PARENT = "Parent App";
    public static final String ORIGINNAME_ADMIN = "Admin App";
    public static final String ORIGINNAME_STAFF = "Staff App";

    // Preference Name
    public static final String PREF_NAME = "valai_school_pref";

    public static final String TRUE = "True";
    public static final String FALSE = "False";

    // Network Request API Root URL
    public static final String ROOT = "http://app.evalai.com/api/";
    public static final String ROOT_DOWNLOAD = "http://app.evalai.com/";
    public static final String ROOT_GROUP_FOLDER = "Group/";
    public static final String ROOT_REPORT_CARD_FOLDER = "ReportCard/";
    public static final String ROOT_REPORT_CARD = "/ReportCard";
    public static final String ROOT_SCHOOL_PHOTO_FOLDER = "/SchoolPhoto/";
    public static final String ROOT_STUDENT_PHOTO_FOLDER = "/StudPhoto/";
    public static final String ROOT_STAFF_PHOTO_FOLDER = "/StaffPhoto/";
    public static final String ROOT_SLASH = "/";
    public static final String FILE_ROOT_PATH_CIRCULAR = "/circularPath/";
    public static final String FILE_ROOT_PATH_ASSIGNMENT = "/assignmentPath/";
    public static final String FEE_COMMON_PATH = "FeeCommon/";
    public static final String FEE_RECEIPT_NAME = "FeeReceipt.pdf";
    public static final String PDF_EXTENTION = ".pdf";
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static final String MODE_USER_TYPE = "GET_USERTYPE";
    public static final String MODE_GET_PARENT_VIEW = "GET_PARENT_VIEW";
    public static final String MODE_GET_MESSAGE = "GET_MESSAGE";
    public static final String MODE_GET_PARENT_CIRCULAR = "PARENTMSG";
    public static final String MODE_GET_PARENT_ASSIGNMENT = "ASSIGNSUB";
    public static final String MODE_GET_EXAM_TYPE = "GETEXAMTYPE";
    public static final String MODE_GET_EXAM_REPORT = "EXAM_REPORT";
    public static final String MODE_TRANSPORT_REPORT = "TRANSPORT_REPORT";
    public static final String MODE_HOLIDAY_REPORT = "HOLIDAY_REPORT";
    public static final String MODE_GET_STUDENT_DETAILS = "GETSTUDENTDETAILS";
    public static final String MODE_READ = "READ";
    public static final String MODE_GET_REPORT_CARD = "GETREPORTCARD";
    public static final String MODE_GET_TERM_AND_EXAM = "GETTERMANDEXAM";
    public static final String MODE_GET = "GET";
    public static final String MODE_FEE_DETAIL = "FEE_DETAILED";
    public static final String MODE_RECEIPT_REGENERATE_LIST = "RECEIPT_REGENERATE_LIST";
    public static final String MODE_GET_APP = "GET_APP";
    public static final String MODE_INSERT_APP = "INSERT_APP";
    public static final String MODE_INSERT_STAFF = "INSERT_STAFF";
    public static final String MODE_CIRCULAR_GCM_APP_STAFF = "CIRCULAR_GCM_APP_STAFF";
    public static final String MODE_CIRCULAR_GCM_APP_STUD = "CIRCULAR_GCM_APP_STUD";
    public static final String MODE_ASSIGNMENT_GCM_APP = "ASSIGNMENT_GCM_APP";

    public static final String TOPIC_GLOBAL = "global";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String ORGID = "org_Id";
    public static final String ACADEMICID = "academic_Id";
    public static final String SECTIONID = "section_Id";
    public static final String CHAPTER_ID = "chapter_Id";
    public static final String SUB_CHAPTER_ID = "subChapter_Id";
    public static final String SUBJECT_ID = "subject_Id";
    public static final String CLASSID = "class_Id";
    public static final String STAFFID = "staff_Id";
    public static final String BRANCH_ID = "branch_Id";
    public static final String IS_ATTACHMENT = "is_attachment";
    public static final String ASSIGNMENT_DATE = "assignment_Date";
    public static final String LOGIN_ID = "login_Id";
    public static final String SUBMISSION_DATE = "submission_Date";
    public static final String MESSAGE_SUBJECT = "msg_Subject";
    public static final String MESSAGE = "message";
    public static final String FILE_NAME = "file_name";
    public static final String GEN_FILENAME = "genfile_name";
    public static final String GEN_FILENAME_ASSIGNMENT = "gen_filename";
    public static final String FILE_SIZE = "file_Size";
    public static final String CURRENTDATE = "cir_date";
    public static final String MODE = "mode";
    public static final String TOADDRESS = "to_address";
    public static final String PARENT_EMAIL = "Parent Email";
    public static final String STAFF_EMAIL = "Staff Email";

    private AppConstants() {
        // This utility class is not publicly instantiable
    }
}