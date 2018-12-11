package com.valai.school.activityStaff;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valai.school.R;
import com.valai.school.activities.BaseActivity;
import com.valai.school.adapter.AttendanceSendClassAdapter;
import com.valai.school.modal.GetStudentNameDetailsPOJO;
import com.valai.school.modal.SendAssignmentPOJO;
import com.valai.school.modal.SignInPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.prefs.AppPreferencesHelper;
import com.valai.school.utils.AnimationItem;
import com.valai.school.utils.AppConstants;
import com.valai.school.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.ACADEMICID;
import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.BRANCH_ID;
import static com.valai.school.utils.AppConstants.CLASSID;
import static com.valai.school.utils.AppConstants.ORGID;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.SECTIONID;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;

public class AttendanceSendStaffActivity extends BaseActivity {
    public static final String TAG = AttendanceSendStaffActivity.class.getSimpleName();

    @BindView(R.id.tvClassName)
    TextView tvClassName;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    @BindView(R.id.tvDate)
    TextView tvDate;

    private List<SignInPOJO.Datum> listSignInRes;
    private List<GetStudentNameDetailsPOJO.Datum> studentDetailList;
    private HashMap<String, List<GetStudentNameDetailsPOJO.Datum>> hashMapList;
    private AnimationItem mSelectedItem;
    private String new_month;
    private String new_day;
    private Integer classId, branchId, sectionId;
    private AppPreferencesHelper appPreferencesHelper;
    private AttendanceSendClassAdapter adapter;
    private int pos = 0;

    @SuppressLint({"ObsoleteSdkInt", "SetTextI18n"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_send);
        ButterKnife.bind(this);
        tvDate.setText(getString(R.string.date) + ": " + CommonUtils.getCurrentDateForAttendance());
        tvClassName.setText(getIntent().getExtras().getString("sectionName", null));
        classId = getIntent().getExtras().getInt("classId", 0);
        branchId = getIntent().getExtras().getInt("branch_Id", 0);
        sectionId = getIntent().getExtras().getInt("section_Id", 0);

        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        Gson gson = new Gson();
        Type type = new TypeToken<List<SignInPOJO.Datum>>() {
        }.getType();
        listSignInRes = new ArrayList<>();
        listSignInRes = gson.fromJson(appPreferencesHelper.getParentSignInResponse(), type);

        AnimationItem[] mAnimationItems = getAnimationItems();
        mSelectedItem = mAnimationItems[1];
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        setAdapter();
        setStudentDetails(CommonUtils.getCurrentDate());
    }

    private void setStudentDetails(String date) {
        studentDetailList = new ArrayList<>();
        hashMapList = getStudentDetailList();
        if (hashMapList == null) {
            hashMapList = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (listSignInRes != null && listSignInRes.size() > 0) {
                if (hashMapList != null && hashMapList.containsKey(date + "-" + sectionId)) {
                    clearList();
                    studentDetailList = hashMapList.get(date + "-" + sectionId);
                    if (studentDetailList != null && studentDetailList.size() > 0) {
                        setAdapter();
                    } else {
                        clearList();
                        setAdapter();
                        showMessage(getString(R.string.internet_not_available));
                    }
                } else {
                    clearList();
                    setAdapter();
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                clearList();
                setAdapter();
                showMessage(getString(R.string.internet_not_available));
            }
        } else if (listSignInRes != null && listSignInRes.size() > 0) {
            getStudentsAccToClass(date);
        }
    }

    private void getStudentsAccToClass(final String date) {
        hideKeyboard();
        showLoading();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetStudentNameDetailsPOJO> call = restClientAPI.getStudentDetails(listSignInRes.get(0).getOrgId(),
                sectionId, listSignInRes.get(0).getAcademicId(), date);

        call.enqueue(new Callback<GetStudentNameDetailsPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetStudentNameDetailsPOJO> call, @NonNull Response<GetStudentNameDetailsPOJO> response) {
                GetStudentNameDetailsPOJO getStudentNameDetailsPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getStudentNameDetailsPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getStudentNameDetailsPOJO.getResponseStatus().equals(TRUE)) {
                            clearList();
                            studentDetailList.addAll(getStudentNameDetailsPOJO.getData());
                            hashMapList.put(date + "-" + sectionId, studentDetailList);
                            setStudentDetailList(hashMapList);
                            setAdapter();
                        } else {
                            clearList();
                            setAdapter();
                            showMessage(getStudentNameDetailsPOJO.getResponseMessage());
                        }
                    } else {
                        clearList();
                        setAdapter();
                        showMessage(getStudentNameDetailsPOJO.getResponseMessage());
                    }
                } else {
                    clearList();
                    setAdapter();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetStudentNameDetailsPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                clearList();
                setAdapter();
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void clearList() {
        if (studentDetailList.size() > 0) {
            studentDetailList.clear();
        }
    }

    private void setAdapter() {
        if (studentDetailList != null && studentDetailList.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            adapter = new AttendanceSendClassAdapter(this, studentDetailList);
            recycler_view.setAdapter(adapter);
            runLayoutAnimation(recycler_view, mSelectedItem);
        } else {
            recycler_view.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.img_back)
    public void Back() {
        finish();
    }

    @OnClick(R.id.tvDate)
    public void FromDate() {
        showDatePickerDialog("From_Date");
    }

    @OnClick(R.id.btnPresentAll)
    public void presentAllClick() {
        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }

        if (studentDetailList != null && studentDetailList.size() == 0) {
            showMessage(getString(R.string.no_information_text));
            return;
        }

        for (int i = 0; i < studentDetailList.size(); i++) {
            studentDetailList.get(i).setIsPresent(1);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btnAbsentAll)
    public void absentAllClick() {

        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }

        if (studentDetailList != null && studentDetailList.size() == 0) {
            showMessage(getString(R.string.no_information_text));
            return;
        }

        for (int i = 0; i < studentDetailList.size(); i++) {
            studentDetailList.get(i).setIsPresent(2);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btnHalfDayAll)
    public void halfDayAllClick() {

        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }

        if (studentDetailList != null && studentDetailList.size() == 0) {
            showMessage(getString(R.string.no_information_text));
            return;
        }

        for (int i = 0; i < studentDetailList.size(); i++) {
            studentDetailList.get(i).setIsPresent(3);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.button_send)
    void onSend() {
        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }

        if (studentDetailList != null && studentDetailList.size() == 0) {
            showMessage(getString(R.string.no_information_text));
            return;
        }

        showLoading();
        makeJson();
    }

    private void makeJson() {
        Integer noOfPresent = 0;
        Integer noOfAbsent = 0;
        Integer noOfHalfDay = 0;
        for (int i = 0; i < studentDetailList.size(); i++) {
            if (studentDetailList.get(i).getIsPresent() == 1) {
                noOfPresent++;
            } else if (studentDetailList.get(i).getIsPresent() == 2) {
                noOfAbsent++;
            } else if (studentDetailList.get(i).getIsPresent() == 3) {
                noOfHalfDay++;
            }
        }

        JSONObject json = new JSONObject();
        try {
            json.put(ORGID, listSignInRes.get(0).getOrgId());
            json.put(ACADEMICID, listSignInRes.get(0).getAcademicId());
            json.put(CLASSID, classId);
            json.put(BRANCH_ID, branchId);
            json.put(SECTIONID, sectionId);
            json.put("dateOfAttend", CommonUtils.convertDateStringFormat5(tvDate.getText().toString().replace("Date:", "").trim()));
            json.put("total_No_Stud", studentDetailList.size());
            json.put("no_of_Present", noOfPresent);
            json.put("no_of_Absence", noOfAbsent);
            json.put("no_of_Halfdays", noOfHalfDay);
            json.put("IP_address", CommonUtils.getIpAddress(this));
            json.put("user_name", listSignInRes.get(0).getUserName());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sendStudentDetailsClassWiseCount(json.toString());
    }

    public void sendStudentDetailsClassWiseCount(String json) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Call<SendAssignmentPOJO> call = restClientAPI.sendStudentDetailsClassWise(body);

        call.enqueue(new Callback<SendAssignmentPOJO>() {
            @Override
            public void onResponse(@NonNull Call<SendAssignmentPOJO> call, @NonNull Response<SendAssignmentPOJO> response) {
                SendAssignmentPOJO sendAssignmentPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (sendAssignmentPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (sendAssignmentPOJO.getResponseStatus().equals(TRUE)) {
                            //showMessage(sendAssignmentPOJO.getResponseMessage());
                            if (studentDetailList != null && studentDetailList.size() > 0) {
                                makeJsonForDetails(studentDetailList.get(pos).getStudentId(), studentDetailList.get(pos).getIsPresent());
                            }

                        } else {
                            hideLoading();
                            showMessage(sendAssignmentPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        showMessage(sendAssignmentPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendAssignmentPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(t.getMessage());
            }
        });
    }

    private void makeJsonForDetails(Integer studentId, Integer isPresent) {
        JSONObject json = new JSONObject();
        try {
            json.put(ORGID, listSignInRes.get(0).getOrgId());
            json.put(ACADEMICID, listSignInRes.get(0).getAcademicId());
            json.put(CLASSID, classId);
            json.put(BRANCH_ID, branchId);
            json.put(SECTIONID, sectionId);
            json.put("dateOfAttd", CommonUtils.convertDateStringFormat5(tvDate.getText().toString().replace("Date:", "").trim()));
            json.put("student_Id", studentId);
            json.put("is_present", isPresent);
            json.put("IP_address", CommonUtils.getIpAddress(this));
            json.put("user_name", listSignInRes.get(0).getUserName());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sendStudentDetails(json.toString());
    }

    public void sendStudentDetails(String json) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Call<SendAssignmentPOJO> call = restClientAPI.sendStudentDetails(body);

        call.enqueue(new Callback<SendAssignmentPOJO>() {
            @Override
            public void onResponse(@NonNull Call<SendAssignmentPOJO> call, @NonNull Response<SendAssignmentPOJO> response) {
                SendAssignmentPOJO sendAssignmentPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (sendAssignmentPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (sendAssignmentPOJO.getResponseStatus().equals(TRUE)) {
                            if (pos == studentDetailList.size() - 1) {
                                hideLoading();
                                pos = 0;
                                showMessage(sendAssignmentPOJO.getResponseMessage());
                            } else {
                                pos++;
                                makeJsonForDetails(studentDetailList.get(pos).getStudentId(), studentDetailList.get(pos).getIsPresent());
                            }

                        } else {
                            hideLoading();
                            showMessage(sendAssignmentPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        showMessage(sendAssignmentPOJO.getResponseMessage());
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendAssignmentPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(t.getMessage());
            }
        });
    }

    public void showDatePickerDialog(final String tag) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Set day of month , month and year value in the edit text
                        if (monthOfYear + 1 < 10) {
                            new_month = "0" + String.valueOf(monthOfYear + 1);
                        } else {
                            new_month = String.valueOf(monthOfYear + 1);
                        }

                        if (dayOfMonth < 10) {
                            new_day = "0" + String.valueOf(dayOfMonth);
                        } else {
                            new_day = String.valueOf(dayOfMonth);
                        }

                        setStudentDetails(String.valueOf(year + "-" + new_month + "-" + new_day));
                        setDateWithTagName(tag, new_day + "-" + new_month + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void setDateWithTagName(String tag, String s) {
        if (tag.equals("From_Date")) {
            tvDate.setText(getString(R.string.date) + ": " + s);
        }
    }

    public void setStudentDetailList(HashMap<String, List<GetStudentNameDetailsPOJO.Datum>> assignmentAdminList) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<GetStudentNameDetailsPOJO.Datum>>>() {
        }.getType();
        String json = gson.toJson(assignmentAdminList, type);
        this.appPreferencesHelper.setStudentDetailsListResponse(json);
    }

    public HashMap<String, List<GetStudentNameDetailsPOJO.Datum>> getStudentDetailList() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<GetStudentNameDetailsPOJO.Datum>>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getStudentDetailsListResponse(), type);
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        super.onBackPressed();
    }
}