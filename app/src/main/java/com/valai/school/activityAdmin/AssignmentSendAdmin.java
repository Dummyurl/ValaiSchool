package com.valai.school.activityAdmin;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.valai.school.R;
import com.valai.school.activities.BaseActivity;
import com.valai.school.modal.GetChapterMasterPOJO;
import com.valai.school.modal.GetSubChapterMasterPOJO;
import com.valai.school.modal.GetSubjectMasterPOJO;
import com.valai.school.modal.SendAssignmentPOJO;
import com.valai.school.modal.SignInPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.prefs.AppPreferencesHelper;
import com.valai.school.utils.AlbumStorageDirFactory;
import com.valai.school.utils.AppConstants;
import com.valai.school.utils.BaseAlbumDirFactory;
import com.valai.school.utils.CommonUtils;
import com.valai.school.utils.FroyoAlbumDirFactory;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.ACADEMICID;
import static com.valai.school.utils.AppConstants.API_RESPONSE_CODE_WITH_DATA;
import static com.valai.school.utils.AppConstants.ASSIGNMENT_DATE;
import static com.valai.school.utils.AppConstants.CHAPTER_ID;
import static com.valai.school.utils.AppConstants.FILE_NAME;
import static com.valai.school.utils.AppConstants.FILE_SIZE;
import static com.valai.school.utils.AppConstants.GEN_FILENAME_ASSIGNMENT;
import static com.valai.school.utils.AppConstants.IS_ATTACHMENT;
import static com.valai.school.utils.AppConstants.LOGIN_ID;
import static com.valai.school.utils.AppConstants.MESSAGE;
import static com.valai.school.utils.AppConstants.MESSAGE_SUBJECT;
import static com.valai.school.utils.AppConstants.MODE_ASSIGNMENT_GCM_APP;
import static com.valai.school.utils.AppConstants.MODE_GET_APP;
import static com.valai.school.utils.AppConstants.ORGID;
import static com.valai.school.utils.AppConstants.REQUEST_CAMERA;
import static com.valai.school.utils.AppConstants.REQUEST_CHOOSER;
import static com.valai.school.utils.AppConstants.REQUEST_DOC_FILE;
import static com.valai.school.utils.AppConstants.REQUEST_PERMISSION_CODE_CAMERA;
import static com.valai.school.utils.AppConstants.REQUEST_PERMISSION_CODE_STORAGE;
import static com.valai.school.utils.AppConstants.REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS;
import static com.valai.school.utils.AppConstants.REQUEST_PERMISSION_RECORD_AUDIO;
import static com.valai.school.utils.AppConstants.REQUEST_VIDEO_CAPTURE;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.SECTIONID;
import static com.valai.school.utils.AppConstants.SUBJECT_ID;
import static com.valai.school.utils.AppConstants.SUBMISSION_DATE;
import static com.valai.school.utils.AppConstants.SUB_CHAPTER_ID;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.prepareFilePart;
import static com.valai.school.utils.CommonUtils.prepareStringPart;

public class AssignmentSendAdmin extends BaseActivity implements AdapterView.OnItemSelectedListener, ActivityCompat.OnRequestPermissionsResultCallback {
    public static final String TAG = AssignmentSendAdmin.class.getSimpleName();

    @BindView(R.id.text_sectionname)
    TextView textSectionName;

    @BindView(R.id.et_from_date)
    TextView et_from_date;

    @BindView(R.id.et_to_date)
    TextView et_to_date;

    @BindView(R.id.spinnersubject)
    Spinner spinnersubject;

    @BindView(R.id.spinnerchapter)
    Spinner spinnerchapter;

    @BindView(R.id.spinnersubchapter)
    Spinner spinnersubchapter;

    @BindView(R.id.et_title)
    EditText et_title;

    @BindView(R.id.et_essay)
    EditText et_essay;

    @BindView(R.id.btn_attachment)
    Button btn_attachment;

    @BindView(R.id.btnClose)
    Button btnClose;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.rlFilesAttached)
    RelativeLayout rlFilesAttached;

    @BindView(R.id.btn_send)
    Button btn_send;

    @BindView(R.id.floating_action_button)
    FloatingActionButton floating_action_button;

    @BindView(R.id.recordingTime)
    TextView recordingTime;

    @BindView(R.id.reveal_items)
    LinearLayout mRevealView;

    @BindView(R.id.menu1)
    LinearLayout menu1;

    @BindView(R.id.menu2)
    LinearLayout menu2;

    @BindView(R.id.menu3)
    LinearLayout menu3;

    @BindView(R.id.menu4)
    LinearLayout menu4;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private List<SignInPOJO.Datum> listSignInRes;
    private ArrayList<GetSubjectMasterPOJO.Datum> listSubjectType;
    private ArrayList<GetChapterMasterPOJO.Datum> listChapterType;
    private ArrayList<GetSubChapterMasterPOJO.Datum> listSubChapterType;
    private String new_month;
    private String new_day;
    private boolean hidden = true;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private String imagePath = "";
    private MediaRecorder mediaRecorder;
    private String AudioSavePathInDevice = null;
    private Random random;
    private String fileName;
    private String fileExtension;
    private int fileSize;
    private boolean isAttachment = false;
    private int generateRandomNo;
    private Integer sectionId, classId;
    private File outputFile;
    private boolean isMediaRecorded = false;
    private CountDownTimer t;
    private int cnt = 0;
    private Integer subjectId = 0;
    private Integer chapterId = 0;
    private Integer subChapterId = 0;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignmentsend);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }

        mRevealView.setVisibility(View.GONE);
        floating_action_button.hide();
        recordingTime.setVisibility(View.GONE);
        initializeTimerTask();
        textSectionName.setText(getIntent().getExtras().getString("sectionName", ""));
        sectionId = getIntent().getExtras().getInt("sectionId", 0);
        classId = getIntent().getExtras().getInt("classId", 0);

        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        Gson gson = new Gson();
        Type type = new TypeToken<List<SignInPOJO.Datum>>() {
        }.getType();
        listSignInRes = new ArrayList<>();
        listSubjectType = new ArrayList<>();
        listChapterType = new ArrayList<>();
        listSubChapterType = new ArrayList<>();
        listSignInRes = gson.fromJson(appPreferencesHelper.getParentSignInResponse(), type);
        if (listSignInRes != null && listSignInRes.size() > 0) {
            getSubjectMasterDetails();
        }

        spinnersubject.setOnItemSelectedListener(this);
        spinnerchapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = i - 1;
                if (pos == -1) {
                    return;
                }
                subjectId = listChapterType.get(pos).getSubjectId();
                Log.e("subjectIdN", "subjectIdN>>" + subjectId);
                chapterId = listChapterType.get(pos).getChapterId();
                Log.e("chapterId", "chapterId>>" + chapterId);
                getSubChapterMasterDetails(subjectId, chapterId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnersubchapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = i - 1;
                if (pos == -1) {
                    return;
                }
                subjectId = listSubChapterType.get(pos).getSubjectId();
                Log.e("subjectIdC", "subjectIdC>>" + subjectId);
                chapterId = listSubChapterType.get(pos).getChapterId();
                Log.e("chapterIdC", "chapterIdC>>" + chapterId);
                subChapterId = listSubChapterType.get(pos).getSubChapterId();
                Log.e("subChapterIdC", "subChapterIdC>>" + subChapterId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setDateWithTagName("Current_Date", CommonUtils.getCurrentDate());
        random = new Random();
        generateRandomNo = getRandomNumber();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        subjectId = listSubjectType.get(i).getSubjectId();
        Log.e("subjectId", "subjectId>>" + subjectId);
        getChapterMasterDetails(listSubjectType.get(i).getSubjectId());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @OnClick(R.id.img_back)
    public void Back(View view) {
        hideKeyboard();
        if (!hidden) {
            hideRevealView();
            return;
        }

        if (isMediaRecorded) {
            showMessage(getString(R.string.recording_in_progress));
            return;
        }
        finish();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
    }

    @OnClick(R.id.et_from_date)
    public void FromDate(View view) {
        showDatePickerDialog("From_Date");
    }

    @OnClick(R.id.et_to_date)
    public void ToDate(View view) {
        showDatePickerDialog("To_Date");
    }

    @OnClick(R.id.btn_attachment)
    public void onAttachClick(View view) {
        if (isMediaRecorded) {
            showMessage(getString(R.string.recording_in_progress));
            return;
        }
        int cx = (mRevealView.getRight() - 200);
        int cy = mRevealView.getBottom();
        int radius = mRevealView.getWidth();

        setScrollFocus();

        //Below Android LOLIPOP Version
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SupportAnimator animator = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(600);

            SupportAnimator animator_reverse = animator.reverse();

            if (hidden) {
                mRevealView.setVisibility(View.VISIBLE);
                animator.start();
                hidden = false;
                setItemsAnimations();
            } else {
                animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;
                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });
                animator_reverse.start();
            }
        }
        // Android LOLIPOP And ABOVE Version
        else {
            if (hidden) {
                Animator anim = android.view.ViewAnimationUtils.
                        createCircularReveal(mRevealView, cx, cy, 0, radius);
                mRevealView.setVisibility(View.VISIBLE);
                anim.start();
                hidden = false;
                setItemsAnimations();
            } else {
                Animator anim = android.view.ViewAnimationUtils.
                        createCircularReveal(mRevealView, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;
                    }
                });
                anim.start();
            }
        }
    }

    @SuppressLint("InlinedApi")
    @OnClick(R.id.menu1)
    void onDocButtonClick() {
        hideRevealView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS);
        } else {
            new MaterialFilePicker()
                    .withActivity(this)
                    .withRequestCode(REQUEST_DOC_FILE)
                    .withHiddenFiles(true)
                    .withTitle("Sample title")
                    .start();
        }
    }

    @OnClick(R.id.menu2)
    void onCameraButtonClick() {
        hideRevealView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_CAMERA);
        } else {
            if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                showMessage(getString(R.string.camera_not_support));
                return;
            }
            showCameraDialog();
        }
    }

    @OnClick(R.id.menu3)
    void onGalleryButtonClick() {
        hideRevealView();
        pickFromGallery();
    }

    @OnClick(R.id.menu4)
    void onRecordButtonClick() {
        hideRevealView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_RECORD_AUDIO);
        } else {
            isMediaRecorded = true;
            showMessage(getString(R.string.recording_started));
            floating_action_button.show();
            recordingTime.setVisibility(View.VISIBLE);

            File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + File.separator
                    + getString(R.string.app_name) + File.separator + getString(R.string.app_name_audio) + File.separator);
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            AudioSavePathInDevice = new File(myDir, getString(R.string.aud_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + ".mp3").getPath();
            MediaRecorderReady();
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                startTimer();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                recordingTime.setText(null);
                recordingTime.setVisibility(View.GONE);
                stopTimer();
            } catch (IOException e) {
                e.printStackTrace();
                recordingTime.setText(null);
                recordingTime.setVisibility(View.GONE);
                stopTimer();
            }
        }
    }

    @OnClick(R.id.floating_action_button)
    void onPauseRecord() {
        floating_action_button.hide();
        mediaRecorder.stop();
        stopTimer();
        recordingTime.setText(null);
        recordingTime.setVisibility(View.GONE);
        isMediaRecorded = false;
        if (AudioSavePathInDevice != null) {
            //getFilePath(audioSavePathInDevice);
            isAttachment = true;

            outputFile = new File(AudioSavePathInDevice);
            fileExtension = outputFile.getAbsolutePath().substring(outputFile.getAbsolutePath().lastIndexOf("."));
            fileSize = Integer.parseInt(String.valueOf(outputFile.length() / 1024));
            fileName = AudioSavePathInDevice.substring(AudioSavePathInDevice.lastIndexOf("/") + 1);

            if (fileSize <= 10000) {
                // MP3 file
                rlFilesAttached.setVisibility(View.VISIBLE);
                imageView.setImageResource(0);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                // MP3 document
                Glide.with(this)
                        .load(R.drawable.ic_movie_player)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                        .into(imageView);
            } else {
                isAttachment = false;
                showMessage(getString(R.string.fileSizeMessage));
            }
        }
    }

    @OnClick(R.id.btnClose)
    public void onCloseClick(View view) {
        isAttachment = false;
        rlFilesAttached.setVisibility(View.GONE);
        imageView.setImageResource(0);
    }

    @OnClick(R.id.btn_send)
    void onSend() {
//        Log.e("subjectId","subjectId>>"+subjectId);
//        Log.e("chapterId","chapterId>>"+chapterId);
//        Log.e("subChapterId","subChapterId>>"+subChapterId);

        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }

        if (isMediaRecorded) {
            showMessage(getString(R.string.recording_in_progress));
            return;
        }

        if (et_from_date.getText().toString().equals("")) {
            showMessage(getString(R.string.enter_from_date));
            return;
        }

        if (et_to_date.getText().toString().equals("")) {
            showMessage(getString(R.string.enter_to_date));
            return;
        }

        if (spinnersubject.getSelectedItem() == null) {
            showMessage(getString(R.string.enter_subject));
            return;
        }

        if (spinnersubject.getSelectedItem().toString().equals("")) {
            showMessage(getString(R.string.enter_subject));
            return;
        }

        if (et_title.getText().toString().equals("")) {
            showMessage(getString(R.string.enter_title));
            return;
        }

        showLoading();
        makeJson();
    }

    private void makeJson() {
        JSONObject json = new JSONObject();
        try {
            json.put(ORGID, listSignInRes.get(0).getOrgId());
            json.put(ACADEMICID, listSignInRes.get(0).getAcademicId());
            json.put(SECTIONID, String.valueOf(sectionId));

            json.put(SUBJECT_ID, String.valueOf(subjectId));
            json.put(CHAPTER_ID, String.valueOf(chapterId));
            json.put(SUB_CHAPTER_ID, String.valueOf(subChapterId));

            if (isAttachment) {
                json.put(IS_ATTACHMENT, "1");
            } else {
                json.put(IS_ATTACHMENT, "0");
            }

            json.put(ASSIGNMENT_DATE, et_from_date.getText().toString());
            json.put(LOGIN_ID, listSignInRes.get(0).getLoginId());
            json.put(SUBMISSION_DATE, et_to_date.getText().toString());
            json.put(MESSAGE_SUBJECT, spinnersubject.getSelectedItem().toString());
            json.put(MESSAGE, et_essay.getText().toString());
            if (isAttachment) {
                json.put(FILE_NAME, fileName.trim());
            } else {
                json.put(FILE_NAME, "");
            }

            if (isAttachment) {
                json.put(GEN_FILENAME_ASSIGNMENT, fileName.trim());
            } else {
                json.put(GEN_FILENAME_ASSIGNMENT, "");
            }

            if (isAttachment) {
                json.put(FILE_SIZE, String.valueOf(fileSize));
            } else {
                json.put(FILE_SIZE, "");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (isAttachment) {
            sendParentCircularFile(json.toString());
        } else {
            sendAssignment(json.toString());
        }
    }

    public void getSubjectMasterDetails() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetSubjectMasterPOJO> call = restClientAPI.getSubjectMasterDetails(
                listSignInRes.get(0).getOrgId(),
                listSignInRes.get(0).getAcademicId(),
                listSignInRes.get(0).getBranchId(),
                0, classId, MODE_GET_APP);

        call.enqueue(new Callback<GetSubjectMasterPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetSubjectMasterPOJO> call, @NonNull Response<GetSubjectMasterPOJO> response) {
                GetSubjectMasterPOJO getSubjectMasterPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getSubjectMasterPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getSubjectMasterPOJO.getResponseStatus().equals(TRUE)) {
                            hideLoading();
                            listSubjectType.addAll(getSubjectMasterPOJO.getData());
                            setSubjectSpinnerItems();
                            getChapterMasterDetails(getSubjectMasterPOJO.getData().get(0).getSubjectId());
                        } else {
                            if (listSubjectType != null && listSubjectType.size() > 0) {
                                listSubjectType.clear();
                            }
                            setSubjectSpinnerItems();
                            hideLoading();
                        }
                    } else {
                        if (listSubjectType != null && listSubjectType.size() > 0) {
                            listSubjectType.clear();
                        }
                        setSubjectSpinnerItems();
                        hideLoading();
                    }
                } else {
                    if (listSubjectType != null && listSubjectType.size() > 0) {
                        listSubjectType.clear();
                    }
                    setSubjectSpinnerItems();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetSubjectMasterPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                if (listSubjectType != null && listSubjectType.size() > 0) {
                    listSubjectType.clear();
                }
                setSubjectSpinnerItems();
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setSubjectSpinnerItems() {
        subjectId = 0;
        chapterId = 0;
        subChapterId = 0;
        List<String> list = new ArrayList<>();
        if (listSubjectType != null && listSubjectType.size() > 0) {
            for (int i = 0; i < listSubjectType.size(); i++) {
                list.add(listSubjectType.get(i).getSubjectName());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersubject.setAdapter(dataAdapter);
    }

    public void getChapterMasterDetails(final Integer subject_id) {
        hideKeyboard();
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetChapterMasterPOJO> call = restClientAPI.getChapterMasterDetails(
                listSignInRes.get(0).getOrgId(),
                listSignInRes.get(0).getAcademicId(),
                listSignInRes.get(0).getBranchId(),
                subject_id, classId, MODE_GET_APP);

        call.enqueue(new Callback<GetChapterMasterPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetChapterMasterPOJO> call, @NonNull Response<GetChapterMasterPOJO> response) {
                GetChapterMasterPOJO getChapterMasterPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getChapterMasterPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getChapterMasterPOJO.getResponseStatus().equals(TRUE)) {
                            if (listChapterType.size() > 0) {
                                listChapterType.clear();
                            }
                            listChapterType.addAll(getChapterMasterPOJO.getData());
                            setChapterSpinnerItems();
                            getSubChapterMasterDetails(subject_id,
                                    getChapterMasterPOJO.getData().get(0).getChapterId());
                        } else {
                            if (listChapterType != null && listChapterType.size() > 0) {
                                listChapterType.clear();
                            }
                            setChapterSpinnerItems();
                            if (listSubChapterType != null && listSubChapterType.size() > 0) {
                                listSubChapterType.clear();
                            }
                            setSubChapterSpinnerItems();
                            hideLoading();
                        }
                    } else {
                        if (listChapterType != null && listChapterType.size() > 0) {
                            listChapterType.clear();
                        }
                        setChapterSpinnerItems();
                        if (listSubChapterType != null && listSubChapterType.size() > 0) {
                            listSubChapterType.clear();
                        }
                        setSubChapterSpinnerItems();
                        hideLoading();
                    }
                } else {
                    if (listChapterType != null && listChapterType.size() > 0) {
                        listChapterType.clear();
                    }
                    setChapterSpinnerItems();
                    if (listSubChapterType != null && listSubChapterType.size() > 0) {
                        listSubChapterType.clear();
                    }
                    setSubChapterSpinnerItems();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetChapterMasterPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                if (listChapterType != null && listChapterType.size() > 0) {
                    listChapterType.clear();
                }
                setChapterSpinnerItems();
                if (listSubChapterType != null && listSubChapterType.size() > 0) {
                    listSubChapterType.clear();
                }
                setSubChapterSpinnerItems();
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setChapterSpinnerItems() {
        chapterId = 0;
        subChapterId = 0;
        List<String> list = new ArrayList<>();
        if (listChapterType != null && listChapterType.size() > 0) {
            list.add(getString(R.string.select_chapter));
            for (int i = 0; i < listChapterType.size(); i++) {
                list.add(listChapterType.get(i).getChapterName());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerchapter.setAdapter(dataAdapter);
    }

    public void getSubChapterMasterDetails(Integer subject_id, Integer chapter_id) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetSubChapterMasterPOJO> call = restClientAPI.getSubChapterMasterDetails(
                listSignInRes.get(0).getOrgId(),
                listSignInRes.get(0).getAcademicId(),
                listSignInRes.get(0).getBranchId(), subject_id,
                classId, chapter_id, MODE_GET_APP);

        call.enqueue(new Callback<GetSubChapterMasterPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetSubChapterMasterPOJO> call, @NonNull Response<GetSubChapterMasterPOJO> response) {
                GetSubChapterMasterPOJO getSubChapterMasterPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (getSubChapterMasterPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getSubChapterMasterPOJO.getResponseStatus().equals(TRUE)) {
                            hideLoading();
                            if (listSubChapterType.size() > 0) {
                                listSubChapterType.clear();
                            }
                            listSubChapterType.addAll(getSubChapterMasterPOJO.getData());
                            setSubChapterSpinnerItems();
                        } else {
                            if (listSubChapterType != null && listSubChapterType.size() > 0) {
                                listSubChapterType.clear();
                            }
                            setSubChapterSpinnerItems();
                            hideLoading();
                        }
                    } else {
                        if (listSubChapterType != null && listSubChapterType.size() > 0) {
                            listSubChapterType.clear();
                        }
                        setSubChapterSpinnerItems();
                        hideLoading();
                    }
                } else {
                    if (listSubChapterType != null && listSubChapterType.size() > 0) {
                        listSubChapterType.clear();
                    }
                    setSubChapterSpinnerItems();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetSubChapterMasterPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                if (listSubChapterType != null && listSubChapterType.size() > 0) {
                    listSubChapterType.clear();
                }
                setSubChapterSpinnerItems();
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setSubChapterSpinnerItems() {
        subChapterId = 0;
        List<String> list = new ArrayList<>();
        if (listSubChapterType != null && listSubChapterType.size() > 0) {
            list.add(getString(R.string.select_sub_chapter));
            for (int i = 0; i < listSubChapterType.size(); i++) {
                list.add(listSubChapterType.get(i).getSubChapterName());
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnersubchapter.setAdapter(dataAdapter);
    }

    public void sendAssignment(String json) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Call<SendAssignmentPOJO> call = restClientAPI.sendAssignment(body);

        call.enqueue(new Callback<SendAssignmentPOJO>() {
            @Override
            public void onResponse(@NonNull Call<SendAssignmentPOJO> call, @NonNull Response<SendAssignmentPOJO> response) {
                SendAssignmentPOJO sendAssignmentPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (sendAssignmentPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (sendAssignmentPOJO.getResponseStatus().equals(TRUE)) {
                            hideLoading();
                            showMessage(sendAssignmentPOJO.getResponseMessage());
                            sendNotification();
                            et_essay.setText("");
                            hideLoading();
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            hideLoading();
                        }
                    } else {
                        hideLoading();
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendAssignmentPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
            }
        });
    }

    public void sendParentCircularFile(final String json) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        MultipartBody.Part fileBody = prepareFilePart("file", outputFile);
        MultipartBody.Part fileBody2 = prepareStringPart("filepath", "/Group/" + listSignInRes.get(0).getOrgId() + "/assignmentPath");
        Call<SendAssignmentPOJO> call = restClientAPI.sendFileMultipart(fileBody2, fileBody);

        call.enqueue(new Callback<SendAssignmentPOJO>() {
            @Override
            public void onResponse(@NonNull Call<SendAssignmentPOJO> call, @NonNull Response<SendAssignmentPOJO> response) {
                SendAssignmentPOJO responseBody = response.body();
                int code = response.code();
                Log.e(TAG, "File Response Code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (responseBody.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (responseBody.getResponseStatus().equals(TRUE)) {
                            Log.e(TAG, "File Response Message>>>>" + responseBody.getResponseMessage());
                            sendAssignment(json);
                        } else {
                            showMessage(responseBody.getResponseMessage());
                            hideLoading();
                        }
                    } else {
                        showMessage(responseBody.getResponseMessage());
                        hideLoading();
                    }
                } else {
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendAssignmentPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void sendNotification() {
        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<SendAssignmentPOJO> call = restClientAPI.adminSendNotification(
                listSignInRes.get(0).getOrgId(),
                listSignInRes.get(0).getAcademicId(),
                String.valueOf(classId),
                String.valueOf(0),
                sectionId,
                et_essay.getText().toString(), MODE_ASSIGNMENT_GCM_APP);

        call.enqueue(new Callback<SendAssignmentPOJO>() {
            @Override
            public void onResponse(@NonNull Call<SendAssignmentPOJO> call, @NonNull Response<SendAssignmentPOJO> response) {
                SendAssignmentPOJO sendAssignmentPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code Notification>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (sendAssignmentPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (sendAssignmentPOJO.getResponseStatus().equals(TRUE)) {
                            Log.e(TAG, "code MessageNoti>>>>" + sendAssignmentPOJO.getResponseMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendAssignmentPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                showMessage(getString(R.string.internet_not_available));
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

                        setDateWithTagName(tag, year + "-"
                                + new_month + "-" + new_day);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void setDateWithTagName(String tag, String s) {
        if (tag.equals("From_Date")) {
            et_from_date.setText(s);
        }

        if (tag.equals("To_Date")) {
            et_to_date.setText(s);
        }

        if (tag.equals("Current_Date")) {
            et_from_date.setText(s);
            et_to_date.setText(s);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CHOOSER:
                    final Uri uri = data.getData();
                    if (uri != null) {
                        String path = FileUtils.getPath(this, uri);
                        getFilePath(path);
                    }
                    break;

                case REQUEST_DOC_FILE:
                    String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
                    if (path != null) {
                        getFilePath(path);
                    }
                    break;

                case UCrop.REQUEST_CROP:
                    final Uri resultUri = UCrop.getOutput(data);
                    isAttachment = true;
                    String newPath = FileUtils.getPath(this, resultUri);
                    outputFile = new File(newPath);
                    fileExtension = outputFile.getAbsolutePath().substring(outputFile.getAbsolutePath().lastIndexOf("."));
                    fileSize = Integer.parseInt(String.valueOf(outputFile.length() / 1024));
                    fileName = newPath.substring(newPath.lastIndexOf("/") + 1);
                    setScrollFocus();
                    if (fileSize <= 10000) {
                        rlFilesAttached.setVisibility(View.VISIBLE);
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        Glide.with(this)
                                .load(resultUri)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                .into(imageView);
                    } else {
                        isAttachment = false;
                        showMessage(getString(R.string.fileSizeMessage));
                    }
                    break;

                case REQUEST_CAMERA:
                    getFilePath(imagePath);
                    break;

                case REQUEST_VIDEO_CAPTURE:
                    Uri videoUri = data.getData();
                    if (videoUri != null) {
                        String videoPath = FileUtils.getPath(this, videoUri);
                        outputFile = new File(videoPath);
                        fileExtension = outputFile.getAbsolutePath().substring(outputFile.getAbsolutePath().lastIndexOf("."));
                        fileSize = Integer.parseInt(String.valueOf(outputFile.length() / 1024));
                        setScrollFocus();

                        if (fileSize <= 25000) {
                            isAttachment = true;
                            outputFile = CommonUtils.copyOrMoveFile(this, outputFile, getString(R.string.app_name_video), getString(R.string.vid_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + fileExtension);
                            fileName = outputFile.getPath().substring(outputFile.getPath().lastIndexOf("/") + 1);
                            // MP4 file
                            rlFilesAttached.setVisibility(View.VISIBLE);
                            imageView.setImageResource(0);
                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            // MP4 document
                            Glide.with(this)
                                    .load(R.drawable.ic_movie_player)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                                    .into(imageView);
                        } else {
                            isAttachment = false;
                            showMessage(getString(R.string.fileSizeMessage));
                        }
                    }
                    break;
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
        }
    }

    private void getFilePath(String path) {
        if (path != null && FileUtils.isLocal(path)) {
            outputFile = new File(path);
            fileExtension = outputFile.getAbsolutePath().substring(outputFile.getAbsolutePath().lastIndexOf("."));
            fileSize = Integer.parseInt(String.valueOf(outputFile.length() / 1024));
            //fileName = path.substring(path.lastIndexOf("/") + 1);
            if (fileSize <= 10000) {
                Uri uri = Uri.fromFile(outputFile);

                if (outputFile.toString().contains(".doc") || outputFile.toString().contains(".docx")) {
                    isAttachment = true;
                    outputFile = CommonUtils.copyOrMoveFile(this, outputFile, getString(R.string.app_name_docs), getString(R.string.doc_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + fileExtension);
                    fileName = outputFile.getPath().substring(outputFile.getPath().lastIndexOf("/") + 1);

                    rlFilesAttached.setVisibility(View.VISIBLE);
                    imageView.setImageResource(0);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    // Word document
                    Glide.with(this)
                            .load(R.drawable.ic_doc)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(imageView);

                } else if (outputFile.toString().contains(".pdf")) {
                    isAttachment = true;
                    outputFile = CommonUtils.copyOrMoveFile(this, outputFile, getString(R.string.app_name_docs), getString(R.string.doc_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + fileExtension);
                    fileName = outputFile.getPath().substring(outputFile.getPath().lastIndexOf("/") + 1);

                    // PDF file
                    rlFilesAttached.setVisibility(View.VISIBLE);
                    imageView.setImageResource(0);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    // Word document
                    Glide.with(this)
                            .load(R.drawable.ic_pdf)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(imageView);

                } else if (outputFile.toString().contains(".xls") || outputFile.toString().contains(".xlsx")) {
                    isAttachment = true;
                    outputFile = CommonUtils.copyOrMoveFile(this, outputFile, getString(R.string.app_name_docs), getString(R.string.doc_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + fileExtension);
                    fileName = outputFile.getPath().substring(outputFile.getPath().lastIndexOf("/") + 1);

                    // Excel file
                    rlFilesAttached.setVisibility(View.VISIBLE);
                    imageView.setImageResource(0);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    // Word document
                    Glide.with(this)
                            .load(R.drawable.ic_excel)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(imageView);

                } else if (outputFile.toString().contains(".jpg")) {

                    // JPG file
                    cropImage(uri, getString(R.string.img_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + ".jpg");

                } else if (outputFile.toString().contains(".jpeg")) {

                    // JPG file
                    cropImage(uri, getString(R.string.img_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + ".jpeg");

                } else if (outputFile.toString().contains(".png")) {

                    // JPG file
                    cropImage(uri, getString(R.string.img_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + ".png");

                } else if (outputFile.toString().contains(".txt")) {
                    isAttachment = true;
                    outputFile = CommonUtils.copyOrMoveFile(this, outputFile, getString(R.string.app_name_docs), getString(R.string.txt_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + fileExtension);
                    fileName = outputFile.getPath().substring(outputFile.getPath().lastIndexOf("/") + 1);

                    // Text file
                    rlFilesAttached.setVisibility(View.VISIBLE);
                    imageView.setImageResource(0);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    // Word document
                    Glide.with(this)
                            .load(R.drawable.ic_text)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(imageView);

                } else if (outputFile.toString().contains(".csv")) {
                    isAttachment = true;
                    outputFile = CommonUtils.copyOrMoveFile(this, outputFile, getString(R.string.app_name_docs), getString(R.string.doc_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + fileExtension);
                    fileName = outputFile.getPath().substring(outputFile.getPath().lastIndexOf("/") + 1);

                    // CSV file
                    rlFilesAttached.setVisibility(View.VISIBLE);
                    imageView.setImageResource(0);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    // Word document
                    Glide.with(this)
                            .load(R.drawable.ic_csv)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(imageView);

                } else if (outputFile.toString().contains(".mp4") || outputFile.toString().contains(".3gp")
                        || outputFile.toString().contains(".avi") || outputFile.toString().contains(".flv")
                        || outputFile.toString().contains(".mov") || outputFile.toString().contains(".m4a")) {

                    isAttachment = true;
                    outputFile = CommonUtils.copyOrMoveFile(this, outputFile, getString(R.string.app_name_video), getString(R.string.vid_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + fileExtension);
                    fileName = outputFile.getPath().substring(outputFile.getPath().lastIndexOf("/") + 1);

                    // MP4 file
                    rlFilesAttached.setVisibility(View.VISIBLE);
                    imageView.setImageResource(0);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    // MP4 document
                    Glide.with(this)
                            .load(R.drawable.ic_movie_player)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(imageView);
                } else if (outputFile.toString().contains(".mp3") || outputFile.toString().contains(".wmv")
                        || outputFile.toString().contains(".ogg")) {

                    isAttachment = true;
                    outputFile = CommonUtils.copyOrMoveFile(this, outputFile, getString(R.string.app_name_audio), getString(R.string.aud_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + fileExtension);
                    fileName = outputFile.getPath().substring(outputFile.getPath().lastIndexOf("/") + 1);

                    // MP4 file
                    rlFilesAttached.setVisibility(View.VISIBLE);
                    imageView.setImageResource(0);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    // MP4 document
                    Glide.with(this)
                            .load(R.drawable.ic_movie_player)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(imageView);
                } else if (outputFile.toString().contains(".gif")) {

                    isAttachment = true;
                    outputFile = CommonUtils.copyOrMoveFile(this, outputFile, getString(R.string.app_name_gif), getString(R.string.gif_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + fileExtension);
                    fileName = outputFile.getPath().substring(outputFile.getPath().lastIndexOf("/") + 1);

                    // MP4 file
                    rlFilesAttached.setVisibility(View.VISIBLE);
                    imageView.setImageResource(0);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    // MP4 document
                    Glide.with(this)
                            .load(R.drawable.gif)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                            .into(imageView);
                } else {
                    isAttachment = false;
                    showMessage(getString(R.string.format_not_supported));
                }
            } else {
                isAttachment = false;
                showMessage(getString(R.string.fileSizeMessage));
            }
        } else {
            isAttachment = false;
            showMessage(getString(R.string.fileNotSelected));
        }
    }

    public void cropImage(Uri sourceUri, String fileName) {
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                + File.separator
                + getString(R.string.app_name) + File.separator + getString(R.string.app_name_images) + File.separator);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        File outputFile = new File(myDir, fileName);

        UCrop.of(sourceUri, Uri.fromFile(outputFile))
                .start(this);
    }

    private void hideRevealView() {
        if (mRevealView.getVisibility() == View.VISIBLE) {
            mRevealView.setVisibility(View.GONE);
            hidden = true;
        }
    }

    private void setItemsAnimations() {
        Animation popUp1 = AnimationUtils.loadAnimation(this, R.anim.popup);
        Animation popUp2 = AnimationUtils.loadAnimation(this, R.anim.popup);
        Animation popUp3 = AnimationUtils.loadAnimation(this, R.anim.popup);
        Animation popUp4 = AnimationUtils.loadAnimation(this, R.anim.popup);

        popUp1.setStartOffset(50);
        popUp2.setStartOffset(100);
        popUp3.setStartOffset(150);
        popUp4.setStartOffset(200);

        menu4.startAnimation(popUp1);
        menu3.startAnimation(popUp2);
        menu2.startAnimation(popUp3);
        menu1.startAnimation(popUp4);
    }

    private void setScrollFocus() {
        View lastChild = scrollView.getChildAt(scrollView.getChildCount() - 1);
        int bottom = lastChild.getBottom() + scrollView.getPaddingBottom();
        int sy = scrollView.getScrollY();
        int sh = scrollView.getHeight();
        int delta = bottom - (sy + sh);

        scrollView.smoothScrollBy(0, delta);
    }

    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE_STORAGE);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_CHOOSER);
        }
    }

    private void showCameraDialog() {
        if (!hidden) {
            hideRevealView();
        }
        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_camera);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        Button btnTakePicture = dialog.findViewById(R.id.btnTakePicture);
        Button btnTakeVideo = dialog.findViewById(R.id.btnTakeVideo);

        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                cameraIntent();
            }
        });

        btnTakeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                cameraIntentVideo();
            }
        });
        dialog.show();
    }

    public void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = null;
        try {
            file = new File(setUpPhotoFile().getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (intent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    private void cameraIntentVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private File setUpPhotoFile() throws IOException {
        File f = createImageFile();
        imagePath = f.getAbsolutePath();
        return f;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String JPEG_FILE_PREFIX = "IMG_";
        String imageFileName = JPEG_FILE_PREFIX + listSignInRes.get(0).getOrgId() + "_" + CommonUtils.getTimeStamp();
        File albumF = getAlbumDir();
        String JPEG_FILE_SUFFIX = ".jpg";
        return File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
    }

    @Nullable
    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            showMessage("External storage is not mounted READ/WRITE.");
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    public String getAlbumName() {
        return getString(R.string.app_name);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    Log.e(TAG, "Storage Permission Not Granted");
                    showMessage(getString(R.string.allow_storage_permission));
                }

                break;
            }

            case REQUEST_PERMISSION_CODE_CAMERA: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onCameraButtonClick();
                } else {
                    Log.e(TAG, "Storage Permission Not Granted");
                    showMessage(getString(R.string.allow_storage_permission));
                }

                break;
            }

            case REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onDocButtonClick();
                } else {
                    Log.e(TAG, "Storage Permission Not Granted");
                    showMessage(getString(R.string.allow_storage_permission));
                }

                break;
            }

            case REQUEST_PERMISSION_RECORD_AUDIO: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onRecordButtonClick();
                } else {
                    Log.e(TAG, "Storage Permission Not Granted");
                    showMessage(getString(R.string.allow_record_permission));
                }

                break;
            }
        }
    }

    public String CreateRandomAudioFileName(int string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int i = 0;
        while (i < string) {
            String randomAudioFileName = "ABCDEFGHIJKLMNOP";
            stringBuilder.append(randomAudioFileName.
                    charAt(random.nextInt(randomAudioFileName.length())));

            i++;
        }
        return stringBuilder.toString();
    }

    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    private int getRandomNumber() {
        return 10000000 + random.nextInt(90000000);
    }

    public void initializeTimerTask() {
        t = new CountDownTimer(Long.MAX_VALUE, 1000) {

            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onTick(long millisUntilFinished) {

                cnt++;
                String time = Integer.valueOf(cnt).toString();

                long millis = cnt;
                int seconds = (int) (millis / 60);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                recordingTime.setText(String.format("%02d:%02d", seconds, millis));
            }

            @Override
            public void onFinish() {
                cnt = 0;
                initializeTimerTask();
            }
        };
    }

    public void startTimer() {
        t.start();
    }

    @SuppressLint("SetTextI18n")
    public void stopTimer() {
        recordingTime.setText("00:00");
        t.cancel();
        t.onFinish();
    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        if (!hidden) {
            hideRevealView();
            return;
        }

        if (isMediaRecorded) {
            showMessage(getString(R.string.recording_in_progress));
            return;
        }
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top);
    }
}