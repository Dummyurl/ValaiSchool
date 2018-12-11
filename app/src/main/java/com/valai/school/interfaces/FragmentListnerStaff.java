package com.valai.school.interfaces;

import android.support.v4.app.Fragment;

import com.valai.school.modal.GetAssignmentSectionPOJO;
import com.valai.school.modal.GetCircularClassPOJO;
import com.valai.school.modal.GetCircularStaffPOJO;
import com.valai.school.modal.GetClassAttendancePOJO;
import com.valai.school.modal.SignInPOJO;
import com.valai.school.prefs.AppPreferencesHelper;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public interface FragmentListnerStaff extends Serializable {
    void setOnLoadHome(int navIndex, String tag);

    void setTopStudentDetails(String details);

    AppPreferencesHelper getAppPreferenceHelper();

    void onFragmentDetach(String tag);

    void onFragmentAttach(Fragment fragment, String tag);

    List<SignInPOJO.Datum> getSignInResultList();

    void getFileDownloadCall(String fileUrl, final String filename);

    void setFileSizeDownloaded(long downloadedFileSize, long totalFileSize);

    void setOutputFilePath(File outputFile);

    void setClassAttendanceList(List<GetClassAttendancePOJO.Datum> getClassAttendanceList);

    List<GetClassAttendancePOJO.Datum> getClassAttendanceList();

    void setCircularClassList(List<GetCircularClassPOJO.Datum> circularClassList);

    List<GetCircularClassPOJO.Datum> getCircularClassList();

    void setCircularStaffList(List<GetCircularStaffPOJO.Datum> circularStaffList);

    List<GetCircularStaffPOJO.Datum> getCircularStaffList();

    void setAssignmentAdminList(List<GetAssignmentSectionPOJO.Datum> assignmentAdminList);

    List<GetAssignmentSectionPOJO.Datum> getAssignmentAdminList();
}