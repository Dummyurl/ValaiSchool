package com.valai.school.network;


import com.valai.school.modal.FCMPOJO;
import com.valai.school.modal.GetAcademicYearPOJO;
import com.valai.school.modal.GetAdminAssignmentPOJO;
import com.valai.school.modal.GetAdminCircularParentPOJO;
import com.valai.school.modal.GetAssignmentPOJO;
import com.valai.school.modal.GetAssignmentSectionPOJO;
import com.valai.school.modal.GetAttendancePOJO;
import com.valai.school.modal.GetChapterMasterPOJO;
import com.valai.school.modal.GetCircularClassPOJO;
import com.valai.school.modal.GetCircularPOJO;
import com.valai.school.modal.GetCircularStaffPOJO;
import com.valai.school.modal.GetClassAttendancePOJO;
import com.valai.school.modal.GetDiaryPOJO;
import com.valai.school.modal.GetEventDetailsPOJO;
import com.valai.school.modal.GetExamPOJO;
import com.valai.school.modal.GetExamSchedulePOJO;
import com.valai.school.modal.GetExamTypePOJO;
import com.valai.school.modal.GetFeeDetailPOJO;
import com.valai.school.modal.GetHolidaysPOJO;
import com.valai.school.modal.GetPrintReceiptUrlDownloadPOJO;
import com.valai.school.modal.GetPrintReceiptUrlPOJO;
import com.valai.school.modal.GetReceiptPOJO;
import com.valai.school.modal.GetReportCardPOJO;
import com.valai.school.modal.GetResultSchedulePOJO;
import com.valai.school.modal.GetResultTypePOJO;
import com.valai.school.modal.GetStudentDetailsPOJO;
import com.valai.school.modal.GetStudentNameDetailsPOJO;
import com.valai.school.modal.GetSubChapterMasterPOJO;
import com.valai.school.modal.GetSubjectMasterPOJO;
import com.valai.school.modal.GetTermExamIdPOJO;
import com.valai.school.modal.GetTermPOJO;
import com.valai.school.modal.GetTransportPOJO;
import com.valai.school.modal.GetUserTypePOJO;
import com.valai.school.modal.MonthAttendancePOJO;
import com.valai.school.modal.SendAssignmentPOJO;
import com.valai.school.modal.SignInPOJO;
import com.valai.school.modal.StudentListPOJO;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RestClient {

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

    @GET("./Parent/getuserType")
    Call<GetUserTypePOJO> getUserType(@Query("username") String userName,
                                      @Query("mode") String mode);

    @GET("./Parent/getAuthentication")
    Call<SignInPOJO> signIn(@Query("username") String userName,
                            @Query("userType") Integer userType,
                            @Query("password") String password);

    @GET("./Parent/getCalendarDetails")
    Call<GetDiaryPOJO> getDiaryForParent(@Query("org_Id") Integer orgId,
                                         @Query("academic_Id") Integer academicId,
                                         @Query("student_Id") Integer studentId,
                                         @Query("mode") String mode);

    @GET("./Parent/getMessage")
    Call<GetEventDetailsPOJO> getEventDetailsForParent(@Query("orgId") Integer orgId,
                                                       @Query("academicId") Integer academicId,
                                                       @Query("studentId") Integer studentId,
                                                       @Query("msgId") Integer msgId,
                                                       @Query("section_Id") Integer sectionId​,
                                                       @Query("mode") String mode);

    @GET("./Parent/getInbox")
    Call<GetCircularPOJO> getCircularForParent(@Query("orgId") Integer orgId,
                                               @Query("academicId") Integer academicId,
                                               @Query("classId") Integer classId,
                                               @Query("branchId") Integer branchId,
                                               @Query("sectionId") Integer sectionId,
                                               @Query("studentId") Integer studentId,
                                               @Query("mode") String mode);

    @GET("./Parent/getInboxAssignment")
    Call<GetAssignmentPOJO> getAssignmentForParent(@Query("orgId") Integer orgId,
                                                   @Query("academicId") Integer academicId,
                                                   @Query("classId") Integer classId,
                                                   @Query("branchId") Integer branchId,
                                                   @Query("sectionId") Integer sectionId,
                                                   @Query("studentId") Integer studentId,
                                                   @Query("mode") String mode);

    @GET("./Parent/attendanceReport")
    Call<GetAttendancePOJO> getAttendanceReportForParent(@Query("orgId") Integer orgId,
                                                         @Query("academic_Id") Integer academicId,
                                                         @Query("student_Id") Integer studentId);

    @GET("./Parent/attendanceReportDaily")
    Call<MonthAttendancePOJO> getMonthAttendanceReportForParent(@Query("orgId") Integer orgId,
                                                                @Query("academic_Id") Integer academicId,
                                                                @Query("student_Id") Integer studentId);


    @GET("./Parent/getFeeDetailedReport")
    Call<GetFeeDetailPOJO> getFeeReportCardForParent(@Query("orgId") Integer orgId,
                                                     @Query("academicId") Integer academicId,
                                                     @Query("classId") Integer classId,
                                                     @Query("sectionId") Integer sectionId,
                                                     @Query("studentId") Integer studentId,
                                                     @Query("mode") String mode);

    @GET("./Parent/getFeeRegenerateReceiptDetails")
    Call<GetReceiptPOJO> getFeeGeneratedReceipt(@Query("orgId") Integer orgId,
                                                @Query("academicId") Integer academicId,
                                                @Query("classId") Integer classId,
                                                @Query("sectionId") Integer sectionId,
                                                @Query("studentId") Integer studentId,
                                                @Query("receiptId") Integer receiptId,
                                                @Query("mode") String mode);

    @GET("./Parent/getPrintReceiptUrl")
    Call<GetPrintReceiptUrlPOJO> getPrintReceiptUrl(@Query("org_Id") Integer orgId);

    @GET("./")
    Call<GetPrintReceiptUrlDownloadPOJO> getReCreateReceiptUrl(@Query("orgId") Integer orgId,
                                                               @Query("academicId") Integer academicId,
                                                               @Query("classId") Integer classId,
                                                               @Query("sectionId") Integer sectionId,
                                                               @Query("studentId") Integer studentId,
                                                               @Query("receiptId") Integer receiptId,
                                                               @Query("userName") String userName);

    @GET("./Parent/getExamType")
    Call<GetExamTypePOJO> getExamType(@Query("org_Id") Integer orgId,
                                      @Query("academic_Id") Integer academicId,
                                      @Query("mode") String mode);

    @GET("./Parent/getStudentExamScheduleReport")
    Call<GetExamSchedulePOJO> getStudentExamScheduleReport(@Query("orgId") Integer orgId,
                                                           @Query("academicId") Integer academicId,
                                                           @Query("classId") Integer classId,
                                                           @Query("branchId") Integer branchId,
                                                           @Query("examId") Integer examId,
                                                           @Query("mode") String mode);

    @GET("./Parent/getReportCardGrid")
    Call<GetResultSchedulePOJO> getStudentExamResultReport(@Query("orgId") Integer orgId,
                                                           @Query("academicId") Integer academicId,
                                                           @Query("classId") Integer classId,
                                                           @Query("student") Integer student,
                                                           @Query("exam") Integer examId,
                                                           @Query("mode") String mode);


    @GET("./Parent/getReportCardGrid")
    Call<GetTermExamIdPOJO> getTermExamId(@Query("orgId") Integer orgId,
                                          @Query("academicId") Integer academicId,
                                          @Query("classId") Integer classId,
                                          @Query("student") Integer student,
                                          @Query("exam") Integer examId,
                                          @Query("mode") String mode);


    @GET("./Parent/getTransportReport")
    Call<GetTransportPOJO> getTransportReport(@Query("orgId") Integer orgId,
                                              @Query("academicId") Integer academicId,
                                              @Query("studentId") Integer studentId,
                                              @Query("mode") String mode);

    @GET("./Parent/getStudentHolidayReport")
    Call<GetHolidaysPOJO> getStudentHolidayReport(@Query("orgId") Integer orgId,
                                                  @Query("academicId") Integer academicId,
                                                  @Query("mode") String mode);

    @GET("./Parent/studentDetails")
    Call<GetStudentDetailsPOJO> getStudentProfile(@Query("orgId") Integer orgId,
                                                  @Query("academicId") Integer academicId,
                                                  @Query("studentId") Integer studentId,
                                                  @Query("mode") String mode);

    @GET("./Parent/getReportCardPublish")
    Call<GetResultTypePOJO> getReportType(@Query("orgId") Integer orgId,
                                          @Query("academicId") Integer academicId,
                                          @Query("classId") Integer classId,
                                          @Query("term") Integer term,
                                          @Query("mode") String mode);


    @GET("./Parent/getTotalMarksObtainedGrade")
    Call<RequestBody> getTotalMarksObtainedGrade(@Query("org_Id") Integer orgId,
                                                 @Query("academic_Id") Integer academicId,
                                                 @Query("totalMarkObtained") Integer totalMarkObtained​,
                                                 @Query("student_Id") Integer studentId);

    @GET("./Parent/getTerm")
    Call<GetTermPOJO> getTerm(@Query("orgId") Integer orgId,
                              @Query("academicId") Integer academicId,
                              @Query("classId") Integer classId);

    @GET("./Parent/getExam")
    Call<GetExamPOJO> getExam(@Query("orgId") Integer orgId,
                              @Query("academicId") Integer academicId,
                              @Query("classId") Integer classId,
                              @Query("termId") Integer termId,
                              @Query("catId") Integer catId​);

    @GET("./Parent/getAcademicYearMaster")
    Call<GetAcademicYearPOJO> getAcademicYearMaster(@Query("org_Id") Integer orgId,
                                                    @Query("academic_Id") Integer academicId,
                                                    @Query("mode") String mode);

    @GET("./ReportcardTemplate/getReportCard")
    Call<GetReportCardPOJO> getReportCard(@Query("org_Id") Integer orgId,
                                          @Query("academic_Id") Integer academicId,
                                          @Query("class_Id") Integer classId,
                                          @Query("section_Id") Integer sectionId,
                                          @Query("student_Id") Integer studentId,
                                          @Query("term_Id") Integer termId,
                                          @Query("exam_Id") Integer examId,
                                          @Query("term_Name") String termName,
                                          @Query("exam_Name") String examName,
                                          @Query("curren_Year") String currentYear);

    @GET("./Parent/parentLoginGetDetails")
    Call<StudentListPOJO> getStudentList(@Query("orgId") Integer orgId,
                                         @Query("academic_Id") Integer academicId,
                                         @Query("userName") String userName,
                                         @Query("userType_Id") Integer userType_Id);

    @GET("./parent/storeMobileDeviceIntoBank")
    Call<FCMPOJO> sendToken(@Query("orgId") Integer orgId,
                            @Query("parentId") Integer academicId,
                            @Query("originName") String classId,
                            @Query("fcmToken") String sectionId,
                            @Query("deviceId") String studentId,
                            @Query("userName") String username);

    @GET("./Admin/getClassDetails")
    Call<GetCircularClassPOJO> getCircularClassDetails(@Query("orgId") Integer orgId,
                                                       @Query("academic_Id") Integer academicId,
                                                       @Query("staff_id") Integer staffId,
                                                       @Query("user_Type_Id") Integer userTypeId,
                                                       @Query("user_Role_Id") Integer userRoleId);

    @GET("./Admin/getTeachersDetails")
    Call<GetCircularStaffPOJO> getCircularStaffDetails(@Query("orgId") Integer orgId);

    @GET("./Admin/getSectionDetails")
    Call<GetAssignmentSectionPOJO> getAssignmentSectionDetails(@Query("orgId") Integer orgId,
                                                               @Query("academicId") Integer academicId,
                                                               @Query("staffId") Integer staffId,
                                                               @Query("classId") Integer classId,
                                                               @Query("userRoleId") Integer userRoleId,
                                                               @Query("userTypeId") Integer userTypeId);

    @GET("./Admin/getSubjectMasterDetails")
    Call<GetSubjectMasterPOJO> getSubjectMasterDetails(@Query("org_Id") Integer orgId,
                                                       @Query("academic_Id") Integer academicId,
                                                       @Query("branch_Id") Integer branchId,
                                                       @Query("subject_Id") Integer subjectId,
                                                       @Query("class_Id") Integer classId,
                                                       @Query("mode") String mode);


    @GET("./Admin/getChapterMasterDetails")
    Call<GetChapterMasterPOJO> getChapterMasterDetails(@Query("org_Id") Integer orgId,
                                                       @Query("academic_Id") Integer academicId,
                                                       @Query("branch_Id") Integer branchId,
                                                       @Query("subject_Id") Integer subjectId,
                                                       @Query("class_Id") Integer classId,
                                                       @Query("mode") String mode);

    @GET("./Admin/getsubChapterMasterDetails")
    Call<GetSubChapterMasterPOJO> getSubChapterMasterDetails(@Query("org_Id") Integer orgId,
                                                             @Query("academic_Id") Integer academicId,
                                                             @Query("branch_Id") Integer branchId,
                                                             @Query("subject_Id") Integer subjectId,
                                                             @Query("class_Id") Integer classId,
                                                             @Query("chapter_Id") Integer chapterId,
                                                             @Query("mode") String mode);

    @POST("./Admin/insertAssignment")
    Call<SendAssignmentPOJO> sendAssignment(@Body RequestBody body);

    @POST("./Admin/insertCircular")
    Call<SendAssignmentPOJO> sendParentCircular(@Body RequestBody body);

    @GET("./Admin/getInboxAssignment")
    Call<GetAdminAssignmentPOJO> getAssignmentForAdmin(@Query("orgId") Integer orgId,
                                                       @Query("academicId") Integer academicId,
                                                       @Query("sectionId") Integer sectionId);

    @GET("./Admin/getCircularMessage")
    Call<GetAdminCircularParentPOJO> getCircularParentForAdmin(@Query("orgId") Integer orgId,
                                                               @Query("userId") String userId,
                                                               @Query("userTypeId") Integer userTypeId,
                                                               @Query("academicId") Integer academicId);

    @GET("./Admin/SendNotification")
    Call<SendAssignmentPOJO> adminSendNotification(@Query("orgId") Integer orgId,
                                                   @Query("academic_Id") Integer academicId,
                                                   @Query("class_Id") String classId,
                                                   @Query("staffId") String staffId,
                                                   @Query("section_Id") Integer sectionId,
                                                   @Query("message") String message,
                                                   @Query("mode") String mode);

    @Multipart
    @POST("./upload")
    Call<SendAssignmentPOJO> sendFileMultipart(@Part MultipartBody.Part filePath,
                                               @Part MultipartBody.Part fileBody);

    @GET("./Admin/getClass")
    Call<GetClassAttendancePOJO> getStaffClass(@Query("orgId") Integer orgId,
                                               @Query("staff_Id") Integer staffId,
                                               @Query("user_Type_Id") Integer userTypeId,
                                               @Query("user_Role_Id") Integer userRoleId,
                                               @Query("academic_Id") Integer academicId);

    @GET("./Admin/getSectionDetails")
    Call<GetClassAttendancePOJO> getSectionDetails(@Query("orgId") Integer orgId,
                                                   @Query("academicId") Integer academicId,
                                                   @Query("staffId") Integer staffId,
                                                   @Query("classId") Integer classId,
                                                   @Query("userRoleId") Integer userRoleId,
                                                   @Query("userTypeId") Integer userTypeId);

    @GET("./Admin/getStudentName")
    Call<GetStudentNameDetailsPOJO> getStudentDetails(@Query("orgId") Integer orgId,
                                                      @Query("section_Id") Integer sectionId,
                                                      @Query("academicId") Integer academicId,
                                                      @Query("dateOfAttendance") String dateOfAttendance);

    @POST("./Admin/iuStudentAttendance")
    Call<SendAssignmentPOJO> sendStudentDetailsClassWise(@Body RequestBody body);

    @POST("./Admin/AttendanceStudDet")
    Call<SendAssignmentPOJO> sendStudentDetails(@Body RequestBody body);

}