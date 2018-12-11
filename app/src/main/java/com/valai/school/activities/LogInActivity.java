package com.valai.school.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valai.school.R;
import com.valai.school.modal.FCMPOJO;
import com.valai.school.modal.GetUserTypePOJO;
import com.valai.school.modal.SignInPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.prefs.AppPreferencesHelper;
import com.valai.school.utils.AppConstants;
import com.valai.school.utils.KeyboardUtils;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.MODE_USER_TYPE;
import static com.valai.school.utils.AppConstants.REQUEST_PERMISSION_CODE_READ_PHONE_STATE;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.AppConstants.USER_TYPE_ID_FOR_ADMIN_SCHOOL;
import static com.valai.school.utils.AppConstants.USER_TYPE_ID_FOR_PARENT;
import static com.valai.school.utils.AppConstants.USER_TYPE_ID_FOR_TEACHER_STAFF;

public class LogInActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ActivityCompat.OnRequestPermissionsResultCallback {
    public static final String TAG = LogInActivity.class.getSimpleName();

    @BindView(R.id.edtUserName)
    EditText edtUserName;

    @BindView(R.id.edtPassword)
    EditText edtPassword;

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    @BindView(R.id.radioGroupLogInAs)
    RadioGroup radioGroupLogInAs;

    @BindView(R.id.radioBtnAdmin)
    RadioButton radioBtnAdmin;

    @BindView(R.id.radioBtnParent)
    RadioButton radioBtnParent;

    @BindView(R.id.radioBtnTeacher)
    RadioButton radioBtnTeacher;

    private AppPreferencesHelper appPreferencesHelper;
    private int userTypeId = 0;

    private TelephonyManager telephonyManager;
    private String deviceId;

    @NonNull
    public static Intent getStartIntent(Context context) {
        return new Intent(context, LogInActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        radioGroupLogInAs.setOnCheckedChangeListener(this);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);

        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
        }

    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    @OnClick(R.id.btnSignIn)
    public void onSignInBtnClick() {

        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }
        if (btnSignIn.getText().equals(getString(R.string.nextBtnText))) {
            if (edtUserName.getText().length() == 0) {
                showMessage(getString(R.string.enter_user_name));
                return;
            }

            getUserType();

        } else if (btnSignIn.getText().equals(getString(R.string.signInBtnText))) {
            if (edtUserName.getText().length() == 0) {
                showMessage(getString(R.string.enter_user_name));
                return;
            }
            if (edtPassword.getText().length() == 0) {
                showMessage(getString(R.string.enter_password));
                return;
            }
            if (!isPermissionsGranted(Manifest.permission.READ_PHONE_STATE)) {
                requestPermission(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PERMISSION_CODE_READ_PHONE_STATE);
                return;
            }
            deviceId = telephonyManager.getDeviceId();
            signInRequestCall(userTypeId);
        }
    }

    // Open Activities
    public void openActivities() {
        Log.e("userTypeId", "userTypeId>>" + userTypeId);
        if (userTypeId == USER_TYPE_ID_FOR_ADMIN_SCHOOL) {
            appPreferencesHelper.setUserType(userTypeId);
            openAdminActivity();
        } else if (userTypeId == USER_TYPE_ID_FOR_TEACHER_STAFF) {
            appPreferencesHelper.setUserType(userTypeId);
            openTeacherActivity();
        } else if (userTypeId == USER_TYPE_ID_FOR_PARENT) {
            appPreferencesHelper.setUserType(userTypeId);
            openParentActivity();
        }
    }

    // Open Admin Activity
    private void openAdminActivity() {
        Intent intent = AdminActivity.getStartIntent(LogInActivity.this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    // Open Parent Activity
    private void openParentActivity() {
        Intent intent = ParentsActivity.getStartIntent(LogInActivity.this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    // Open Teacher Activity
    private void openTeacherActivity() {
        Intent intent = TeacherActivity.getStartIntent(LogInActivity.this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.radioBtnAdmin) {
            userTypeId = USER_TYPE_ID_FOR_ADMIN_SCHOOL;
        } else if (checkedId == R.id.radioBtnTeacher) {
            userTypeId = USER_TYPE_ID_FOR_TEACHER_STAFF;
        } else if (checkedId == R.id.radioBtnParent) {
            userTypeId = USER_TYPE_ID_FOR_PARENT;
        }
    }

    // Request Rest Client API Call For Get User Type
    public void getUserType() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetUserTypePOJO> call = restClientAPI.getUserType(edtUserName.getText().toString(),
                MODE_USER_TYPE);
        call.enqueue(new Callback<GetUserTypePOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetUserTypePOJO> call, @NonNull Response<GetUserTypePOJO> response) {
                GetUserTypePOJO userTypePOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (userTypePOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (userTypePOJO.getResponseStatus().equals(TRUE)) {
                            if (userTypePOJO.getData() != null && userTypePOJO.getData().size() > 0) {

                                if (userTypePOJO.getData().size() == 1) {
                                    userTypeId = userTypePOJO.getData().get(0).getUserType();
                                    radioGroupLogInAs.setVisibility(View.GONE);
                                }

                                if (userTypePOJO.getData().size() == 2) {
                                    radioGroupLogInAs.setVisibility(View.VISIBLE);
                                    if (userTypePOJO.getData().get(0).getUserType() == USER_TYPE_ID_FOR_ADMIN_SCHOOL
                                            && userTypePOJO.getData().get(1).getUserType() == USER_TYPE_ID_FOR_PARENT) {

                                        userTypeId = userTypePOJO.getData().get(1).getUserType();
                                        radioBtnAdmin.setVisibility(View.VISIBLE);
                                        radioBtnParent.setVisibility(View.VISIBLE);
                                        radioBtnTeacher.setVisibility(View.GONE);
                                        radioBtnParent.setChecked(true);

                                    } else if (userTypePOJO.getData().get(0).getUserType() == USER_TYPE_ID_FOR_TEACHER_STAFF
                                            && userTypePOJO.getData().get(1).getUserType() == USER_TYPE_ID_FOR_PARENT) {

                                        userTypeId = userTypePOJO.getData().get(1).getUserType();
                                        radioBtnAdmin.setVisibility(View.GONE);
                                        radioBtnParent.setVisibility(View.VISIBLE);
                                        radioBtnTeacher.setVisibility(View.VISIBLE);
                                        radioBtnParent.setChecked(true);

                                    } else if (userTypePOJO.getData().get(0).getUserType() == USER_TYPE_ID_FOR_ADMIN_SCHOOL
                                            && userTypePOJO.getData().get(1).getUserType() == USER_TYPE_ID_FOR_TEACHER_STAFF) {

                                        userTypeId = userTypePOJO.getData().get(1).getUserType();
                                        radioBtnAdmin.setVisibility(View.VISIBLE);
                                        radioBtnParent.setVisibility(View.GONE);
                                        radioBtnTeacher.setVisibility(View.VISIBLE);
                                        radioBtnAdmin.setChecked(true);
                                    }
                                }

                                if (userTypePOJO.getData().size() == 3) {
                                    radioGroupLogInAs.setVisibility(View.VISIBLE);
                                    if (userTypePOJO.getData().get(0).getUserType() == USER_TYPE_ID_FOR_ADMIN_SCHOOL
                                            && userTypePOJO.getData().get(1).getUserType() == USER_TYPE_ID_FOR_TEACHER_STAFF
                                            && userTypePOJO.getData().get(2).getUserType() == USER_TYPE_ID_FOR_PARENT) {

                                        userTypeId = userTypePOJO.getData().get(2).getUserType();
                                        radioBtnAdmin.setVisibility(View.VISIBLE);
                                        radioBtnTeacher.setVisibility(View.VISIBLE);
                                        radioBtnParent.setVisibility(View.VISIBLE);
                                        radioBtnParent.setChecked(true);
                                    }
                                }

                                btnSignIn.setText(getString(R.string.signInBtnText));
                                edtPassword.setVisibility(View.VISIBLE);
                                edtPassword.requestFocus();
                                edtUserName.setEnabled(false);
                                KeyboardUtils.showSoftInput(edtPassword, LogInActivity.this);

                            } else {
                                showMessage(userTypePOJO.getResponseMessage());
                            }
                        } else {
                            showMessage(userTypePOJO.getResponseMessage());
                        }
                    } else {
                        showMessage(userTypePOJO.getResponseMessage());
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetUserTypePOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    // Request Rest Client API Call For SignIn
    public void signInRequestCall(final int userTypeId) {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<SignInPOJO> call = restClientAPI.signIn(edtUserName.getText().toString(),
                userTypeId, edtPassword.getText().toString());
        call.enqueue(new Callback<SignInPOJO>() {
            @Override
            public void onResponse(@NonNull Call<SignInPOJO> call, @NonNull Response<SignInPOJO> response) {
                SignInPOJO signInPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    //hideLoading();
                    if (signInPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (signInPOJO.getResponseStatus().equals(TRUE)) {
                            if (signInPOJO.getData().get(0).getLoginId() != 0) {
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<SignInPOJO.Datum>>() {
                                }.getType();
                                String json = gson.toJson(signInPOJO.getData(), type);
                                appPreferencesHelper.setParentSignInResponse(json);

                                String token = appPreferencesHelper.getToken();

                                sendToken(signInPOJO.getData().get(0).getOrgId(),
                                        signInPOJO.getData().get(0).getParentId(), token,
                                        signInPOJO.getData().get(0).getUserName(), userTypeId);

                            } else {
                                hideLoading();
                                showMessage(signInPOJO.getResponseMessage());
                            }

                        } else {
                            hideLoading();
                            showMessage(signInPOJO.getResponseMessage());
                        }
                    } else {
                        hideLoading();
                        showMessage(signInPOJO.getResponseMessage());
                    }

                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignInPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void sendToken(int org_id, int parent_id, String token, String username, Integer userTypeId) {
        //showLoading();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();
        String originName = null;
        if (userTypeId == USER_TYPE_ID_FOR_ADMIN_SCHOOL) {
            originName = AppConstants.ORIGINNAME_ADMIN;
        } else if (userTypeId == USER_TYPE_ID_FOR_TEACHER_STAFF) {
            originName = AppConstants.ORIGINNAME_STAFF;
        } else if (userTypeId == USER_TYPE_ID_FOR_PARENT) {
            originName = AppConstants.ORIGINNAME_PARENT;
        }

        Call<FCMPOJO> call = restClientAPI.sendToken(org_id, parent_id, originName, token, deviceId, username);
        call.enqueue(new Callback<FCMPOJO>() {
            @Override
            public void onResponse(@NonNull Call<FCMPOJO> call, @NonNull Response<FCMPOJO> response) {
                FCMPOJO fcmpojo = response.body();
                int code = response.code();
                Log.e(TAG, "code send Token>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (fcmpojo.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (fcmpojo.getResponseStatus().equals(TRUE)) {
                            openActivities();
                        }
                    }
                } else {
                    hideLoading();
                    openActivities();
                }
            }

            @Override
            public void onFailure(@NonNull Call<FCMPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                openActivities();
                hideLoading();
            }
        });
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE_READ_PHONE_STATE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onSignInBtnClick();
                    break;
                } else {
                    showMessage(getString(R.string.give_permission_text));
                }
            }
        }
    }
}