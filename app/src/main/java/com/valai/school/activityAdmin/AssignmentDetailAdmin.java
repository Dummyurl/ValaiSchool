package com.valai.school.activityAdmin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valai.school.R;
import com.valai.school.activities.AdminActivity;
import com.valai.school.activities.BaseActivity;
import com.valai.school.adapter.AdminAssignmentAdapter;
import com.valai.school.interfaces.GetFileDownload;
import com.valai.school.modal.GetAdminAssignmentPOJO;
import com.valai.school.modal.SignInPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.prefs.AppPreferencesHelper;
import com.valai.school.utils.AnimationItem;
import com.valai.school.utils.AppConstants;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.getAnimationItems;
import static com.valai.school.utils.CommonUtils.runLayoutAnimation;

public class AssignmentDetailAdmin extends BaseActivity implements GetFileDownload {
    public static final String TAG = AssignmentDetailAdmin.class.getSimpleName();

    @BindView(R.id.text_sectionname)
    TextView textSectionName;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.tvNotFound)
    TextView tvNotFound;

    private AnimationItem mSelectedItem;
    private GetFileDownload fileDownload;
    private List<SignInPOJO.Datum> listSignInRes;
    private List<GetAdminAssignmentPOJO.Datum> getAdminAssignmentArrayList;
    private HashMap<Integer, List<GetAdminAssignmentPOJO.Datum>> hashMapList;
    private AppPreferencesHelper appPreferencesHelper;
    private Integer sectionId;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);
        ButterKnife.bind(this);
        getAdminAssignmentArrayList = new ArrayList<>();
        textSectionName.setText(getIntent().getExtras().getString("sectionName", ""));
        sectionId = getIntent().getExtras().getInt("sectionId", 0);

        fileDownload = this;

        AnimationItem[] mAnimationItems = getAnimationItems();
        mSelectedItem = mAnimationItems[1];
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        setAdapter();

        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        Gson gson = new Gson();
        Type type = new TypeToken<List<SignInPOJO.Datum>>() {
        }.getType();
        listSignInRes = new ArrayList<>();
        listSignInRes = gson.fromJson(appPreferencesHelper.getParentSignInResponse(), type);

        hashMapList = getAssignmentAdminDetailList();
        if (hashMapList == null) {
            hashMapList = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (listSignInRes != null && listSignInRes.size() > 0) {
                if (hashMapList != null && hashMapList.containsKey(sectionId)) {
                    getAdminAssignmentArrayList = hashMapList.get(sectionId);
                    if (getAdminAssignmentArrayList != null && getAdminAssignmentArrayList.size() > 0) {
                        setAdapter();
                    } else {
                        showMessage(getString(R.string.internet_not_available));
                    }
                } else {
                    showMessage(getString(R.string.internet_not_available));
                }
            } else {
                showMessage(getString(R.string.internet_not_available));
            }
        } else if (listSignInRes != null && listSignInRes.size() > 0) {
            getAssignmentRequestCall();
        }
    }

    @OnClick(R.id.img_back)
    public void Back(View view) {
        finish();
    }

    @OnClick(R.id.button_send)
    public void Send(View view) {
        Intent i = new Intent(this, AssignmentSendAdmin.class);
        i.putExtra("sectionName", getIntent().getExtras().getString("sectionName", ""));
        i.putExtra("sectionId", sectionId);
        i.putExtra("classId", getIntent().getExtras().getInt("classId", 0));
        startActivityForResult(i, 200);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
    }

    public void getAssignmentRequestCall() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetAdminAssignmentPOJO> call = restClientAPI.getAssignmentForAdmin(
                listSignInRes.get(0).getOrgId(),
                listSignInRes.get(0).getAcademicId(),
                sectionId);
        call.enqueue(new Callback<GetAdminAssignmentPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetAdminAssignmentPOJO> call, @NonNull Response<GetAdminAssignmentPOJO> response) {
                GetAdminAssignmentPOJO getAdminAssignmentPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getAdminAssignmentPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getAdminAssignmentPOJO.getResponseStatus().equals(TRUE)) {
                            hideLoading();
                            for (int i = 0; i < getAdminAssignmentPOJO.getData().size(); i++) {

                                String directoryName = "";

                                if (getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".doc")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".docx")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".xls")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".xlsx")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".pdf")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".txt")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".csv")) {

                                    // Word document
                                    directoryName = getString(R.string.app_name_docs);

                                } else if (getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".jpg")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".jpeg")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".png")) {

                                    // JPG,JPEG,PNG file
                                    directoryName = getString(R.string.app_name_images);

                                } else if (getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".gif")) {

                                    // GIF file
                                    directoryName = getString(R.string.app_name_gif);

                                } else if (getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".mp4")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".3gp")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".avi")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".flv")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".mov")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".m4a")) {

                                    // Video file
                                    directoryName = getString(R.string.app_name_video);

                                } else if (getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".mp3")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".wmv")
                                        || getAdminAssignmentPOJO.getData().get(i).getFileName().contains(".ogg")) {

                                    // Audio Video file
                                    directoryName = getString(R.string.app_name_audio);

                                }

                                File outputFile;
                                if (!directoryName.equals("")) {
                                    outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                                            + File.separator
                                            + getString(R.string.app_name) + File.separator + directoryName + File.separator, getAdminAssignmentPOJO.getData().get(i).getFileName());
                                } else {
                                    outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                                            + File.separator
                                            + getString(R.string.app_name) + File.separator, getAdminAssignmentPOJO.getData().get(i).getFileName());
                                }

                                if (outputFile.exists()) {
                                    if (outputFile.toString().contains(".doc") || outputFile.toString().contains(".docx")) {

                                        // Word document
                                        getAdminAssignmentPOJO.getData().get(i).setDocDownloaded(true);

                                    } else if (outputFile.toString().contains(".pdf")) {

                                        // PDF file
                                        getAdminAssignmentPOJO.getData().get(i).setPdfDownloaded(true);

                                    } else if (outputFile.toString().contains(".xls") || outputFile.toString().contains(".xlsx")) {

                                        // Excel file
                                        getAdminAssignmentPOJO.getData().get(i).setExcelFileDownloaded(true);

                                    } else if (outputFile.toString().contains(".jpg")) {

                                        // JPG file
                                        getAdminAssignmentPOJO.getData().get(i).setImageDownloaded(true);

                                    } else if (outputFile.toString().contains(".jpeg")) {

                                        // JPEG file
                                        getAdminAssignmentPOJO.getData().get(i).setImageDownloaded(true);

                                    } else if (outputFile.toString().contains(".png")) {

                                        // PNG file
                                        getAdminAssignmentPOJO.getData().get(i).setImageDownloaded(true);

                                    } else if (outputFile.toString().contains(".txt")) {

                                        // Text file
                                        getAdminAssignmentPOJO.getData().get(i).setTxtDownloaded(true);

                                    } else if (outputFile.toString().contains(".csv")) {

                                        // CSV file
                                        getAdminAssignmentPOJO.getData().get(i).setCsvFileDownloaded(true);

                                    } else if (outputFile.toString().contains(".gif")) {

                                        // GIF file
                                        getAdminAssignmentPOJO.getData().get(i).setGifFileDownloaded(true);

                                    } else if (outputFile.toString().contains(".mp4")
                                            || outputFile.toString().contains(".3gp")
                                            || outputFile.toString().contains(".avi")
                                            || outputFile.toString().contains(".flv")
                                            || outputFile.toString().contains(".mov")
                                            || outputFile.toString().contains(".m4a")
                                            || outputFile.toString().contains(".mp3")
                                            || outputFile.toString().contains(".wmv")
                                            || outputFile.toString().contains(".ogg")) {

                                        // Audio Video file
                                        getAdminAssignmentPOJO.getData().get(i).setAudioVideoDownloaded(true);

                                    } else {
                                        getAdminAssignmentPOJO.getData().get(i).setImageDownloaded(false);
                                        getAdminAssignmentPOJO.getData().get(i).setPdfDownloaded(false);
                                        getAdminAssignmentPOJO.getData().get(i).setDocDownloaded(false);
                                        getAdminAssignmentPOJO.getData().get(i).setTxtDownloaded(false);
                                        getAdminAssignmentPOJO.getData().get(i).setExcelFileDownloaded(false);
                                        getAdminAssignmentPOJO.getData().get(i).setCsvFileDownloaded(false);
                                        getAdminAssignmentPOJO.getData().get(i).setAudioVideoDownloaded(false);
                                        getAdminAssignmentPOJO.getData().get(i).setGifFileDownloaded(false);
                                    }

                                } else {
                                    getAdminAssignmentPOJO.getData().get(i).setImageDownloaded(false);
                                    getAdminAssignmentPOJO.getData().get(i).setPdfDownloaded(false);
                                    getAdminAssignmentPOJO.getData().get(i).setDocDownloaded(false);
                                    getAdminAssignmentPOJO.getData().get(i).setTxtDownloaded(false);
                                    getAdminAssignmentPOJO.getData().get(i).setExcelFileDownloaded(false);
                                    getAdminAssignmentPOJO.getData().get(i).setCsvFileDownloaded(false);
                                    getAdminAssignmentPOJO.getData().get(i).setAudioVideoDownloaded(false);
                                    getAdminAssignmentPOJO.getData().get(i).setGifFileDownloaded(false);
                                }
                            }
                            getAdminAssignmentArrayList.addAll(getAdminAssignmentPOJO.getData());
                            hashMapList.put(sectionId, getAdminAssignmentArrayList);
                            setAssignmentAdminDetailList(hashMapList);
                            setAdapter();
                        } else {
                            setAdapter();
                            hideLoading();
                            //showMessage(getAssignmentPOJO.getResponseMessage());
                        }
                    } else {
                        setAdapter();
                        hideLoading();
                        //showMessage(getAssignmentPOJO.getResponseMessage());
                    }
                } else {
                    setAdapter();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAdminAssignmentPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setAdapter();
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setAdapter() {
        if (getAdminAssignmentArrayList != null && getAdminAssignmentArrayList.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            tvNotFound.setVisibility(View.GONE);
            AdminAssignmentAdapter adminAssignmentAdapter = new AdminAssignmentAdapter(this, getAdminAssignmentArrayList, listSignInRes, fileDownload);
            recycler_view.setAdapter(adminAssignmentAdapter);
            runLayoutAnimation(recycler_view, mSelectedItem);
        } else {
            recycler_view.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getFileDownloadCall(String url, String fileName) {
        AdminActivity.fragmentListner.getFileDownloadCall(url, fileName);
    }

    @Override
    public boolean isInterNetConnected() {
        return isNetworkConnected();
    }

    @Override
    public void showMessage() {
        showMessage(getString(R.string.internet_not_available));
    }

    public void setAssignmentAdminDetailList(HashMap<Integer, List<GetAdminAssignmentPOJO.Datum>> assignmentAdminList) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<GetAdminAssignmentPOJO.Datum>>>() {
        }.getType();
        String json = gson.toJson(assignmentAdminList, type);
        this.appPreferencesHelper.setAssignmentAdminDetailListResponse(json);
    }

    public HashMap<Integer, List<GetAdminAssignmentPOJO.Datum>> getAssignmentAdminDetailList() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<GetAdminAssignmentPOJO.Datum>>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getAssignmentAdminDetailListResponse(), type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            recreate();
        }
    }
}