package com.valai.school.interfaces;

import android.support.v4.app.Fragment;

import com.valai.school.modal.GetAssignmentPOJO;
import com.valai.school.modal.GetAttendancePOJO;
import com.valai.school.modal.GetCircularPOJO;
import com.valai.school.modal.GetDiaryPOJO;
import com.valai.school.modal.GetExamSchedulePOJO;
import com.valai.school.modal.GetExamTypePOJO;
import com.valai.school.modal.GetFeeDetailPOJO;
import com.valai.school.modal.GetHolidaysPOJO;
import com.valai.school.modal.GetReceiptPOJO;
import com.valai.school.modal.GetResultSchedulePOJO;
import com.valai.school.modal.GetResultTypePOJO;
import com.valai.school.modal.GetStudentDetailsPOJO;
import com.valai.school.modal.MonthAttendancePOJO;
import com.valai.school.modal.SignInPOJO;
import com.valai.school.modal.StudentListPOJO;
import com.valai.school.modal.TransportModal;
import com.valai.school.prefs.AppPreferencesHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public interface FragmentListner {
    void setOnLoadHome(int navIndex, String tag);

    void setTopStudentDetails(String details);

    void setSchoolInfo(String orgName, int orgId, String orgLogo);

    AppPreferencesHelper getAppPreferenceHelper();

    void onFragmentDetach(String tag);

    void onFragmentAttach(Fragment fragment, String tag);

    List<SignInPOJO.Datum> getSignInResultList();

    void setStudentList(List<StudentListPOJO.Datum> studentList);

    List<StudentListPOJO.Datum> getStudentList();

    void getFileDownloadCall(String fileUrl, final String filename);

    void setFileSizeDownloaded(long downloadedFileSize, long totalFileSize);

    void setOutputFilePath(File outputFile);

    void setAssignmentList(HashMap<Integer, List<GetAssignmentPOJO.Datum>> assignmentList);

    HashMap<Integer, List<GetAssignmentPOJO.Datum>> getAssignmentList();

    void setCircularList(HashMap<Integer, List<GetCircularPOJO.Datum>> assignmentList);

    HashMap<Integer, List<GetCircularPOJO.Datum>> getCircularList();

    void setDiary(HashMap<Integer, GetDiaryPOJO.Data> hashMap);

    HashMap<Integer, GetDiaryPOJO.Data> getDiary();

    void setPaymentList(HashMap<Integer, List<GetFeeDetailPOJO.Datum>> paymentList);

    HashMap<Integer, List<GetFeeDetailPOJO.Datum>> getPaymentList();

    void setReceiptList(HashMap<Integer, List<GetReceiptPOJO.Datum>> paymentList);

    HashMap<Integer, List<GetReceiptPOJO.Datum>> getReceiptList();

    void setAttendanceList(HashMap<Integer, List<GetAttendancePOJO.Datum>> attendanceList);

    HashMap<Integer, List<GetAttendancePOJO.Datum>> getAttendanceList();

    void setMonthList(HashMap<Integer, List<MonthAttendancePOJO.Datum>> monthList);

    HashMap<Integer, List<MonthAttendancePOJO.Datum>> getMonthList();

    void setResultTypeList(HashMap<Integer, List<GetResultTypePOJO.Datum>> listResultType);

    HashMap<Integer, List<GetResultTypePOJO.Datum>> getResultTypeList();

    void setExamTypeList(HashMap<Integer, List<GetExamTypePOJO.Datum>> listExamType);

    HashMap<Integer, List<GetExamTypePOJO.Datum>> getExamTypeList();

    void setExamScheduleList(HashMap<String, List<GetExamSchedulePOJO.Datum>> listExamSchedule);

    HashMap<String, List<GetExamSchedulePOJO.Datum>> getExamScheduleList();

    void setResultList(HashMap<String, List<GetResultSchedulePOJO.Result>> listResult);

    HashMap<String, List<GetResultSchedulePOJO.Result>> getResultList();

    void setTransportList(HashMap<Integer, List<TransportModal>> transportList);

    HashMap<Integer, List<TransportModal>> getTransportList();

    void setHolidayList(HashMap<Integer, List<GetHolidaysPOJO.Datum>> holidaysList);

    HashMap<Integer, List<GetHolidaysPOJO.Datum>> getHolidayList();

    void setStudentProfile(HashMap<Integer, List<GetStudentDetailsPOJO.Datum>> listGetStudentDetails);

    HashMap<Integer, List<GetStudentDetailsPOJO.Datum>> getStudentProfile();

    void setResultScheduleData(HashMap<String, GetResultSchedulePOJO.Data> getResultScheduleData);

    HashMap<String, GetResultSchedulePOJO.Data> getResultScheduleData();
}