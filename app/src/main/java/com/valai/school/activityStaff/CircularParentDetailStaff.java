package com.valai.school.activityStaff;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.valai.school.activities.AdminActivity;
import com.valai.school.activities.BaseActivity;
import com.valai.school.activities.TeacherActivity;
import com.valai.school.adapter.CircularAdminParentAdapter;
import com.valai.school.interfaces.GetFileDownload;
import com.valai.school.modal.GetAdminCircularParentPOJO;
import com.valai.school.modal.ParentCircularModal;
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
import com.valai.school.utils.RecyclerItemClickListener;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
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
import static com.valai.school.utils.AppConstants.CLASSID;
import static com.valai.school.utils.AppConstants.CURRENTDATE;
import static com.valai.school.utils.AppConstants.FILE_NAME;
import static com.valai.school.utils.AppConstants.FILE_SIZE;
import static com.valai.school.utils.AppConstants.GEN_FILENAME;
import static com.valai.school.utils.AppConstants.MESSAGE;
import static com.valai.school.utils.AppConstants.MODE;
import static com.valai.school.utils.AppConstants.MODE_CIRCULAR_GCM_APP_STUD;
import static com.valai.school.utils.AppConstants.MODE_INSERT_APP;
import static com.valai.school.utils.AppConstants.ORGID;
import static com.valai.school.utils.AppConstants.PARENT_EMAIL;
import static com.valai.school.utils.AppConstants.REQUEST_CAMERA;
import static com.valai.school.utils.AppConstants.REQUEST_CHOOSER;
import static com.valai.school.utils.AppConstants.REQUEST_DOC_FILE;
import static com.valai.school.utils.AppConstants.REQUEST_PERMISSION_CODE_CAMERA;
import static com.valai.school.utils.AppConstants.REQUEST_PERMISSION_CODE_STORAGE;
import static com.valai.school.utils.AppConstants.REQUEST_PERMISSION_CODE_STORAGE_DOCUMENTS;
import static com.valai.school.utils.AppConstants.REQUEST_PERMISSION_RECORD_AUDIO;
import static com.valai.school.utils.AppConstants.REQUEST_VIDEO_CAPTURE;
import static com.valai.school.utils.AppConstants.RESPONSE_CODE;
import static com.valai.school.utils.AppConstants.TOADDRESS;
import static com.valai.school.utils.AppConstants.TRUE;
import static com.valai.school.utils.CommonUtils.prepareFilePart;
import static com.valai.school.utils.CommonUtils.prepareStringPart;

public class CircularParentDetailStaff extends BaseActivity implements GetFileDownload, ActivityCompat.OnRequestPermissionsResultCallback {
    public static final String TAG = CircularParentDetailStaff.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.text_sectionname)
    TextView text_sectionname;

    @BindView(R.id.edittext_chatbox)
    EditText edittext_chatbox;

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

    @BindView(R.id.btnClose)
    Button btnClose;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.rlFilesAttached)
    RelativeLayout rlFilesAttached;

    @BindView(R.id.floating_action_button)
    FloatingActionButton floating_action_button;

    @BindView(R.id.recordingTime)
    TextView recordingTime;

    private GetFileDownload fileDownload;
    private boolean hidden = true;
    private String imagePath = "";
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private String fileName;
    private String fileExtension;
    private int fileSize;
    private String audioSavePathInDevice = null;
    private MediaRecorder mediaRecorder;
    private Random random;
    private int generateRandom_no;
    private AppPreferencesHelper appPreferencesHelper;
    private String classId = "";
    private List<SignInPOJO.Datum> listSignInRes;
    private List<GetAdminCircularParentPOJO.Datum> getAdminCircularPOJOArrayList;
    private HashMap<String, List<GetAdminCircularParentPOJO.Datum>> hashMapList;
    private boolean isAttachment = false;
    private File outputFile;
    private boolean isMediaRecorded = false;
    private CountDownTimer t;
    private int cnt = 0;

    @SuppressLint({"SetTextI18n", "ObsoleteSdkInt"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circular_parent_detail_admin);
        ButterKnife.bind(this);
        getAdminCircularPOJOArrayList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
        mRevealView.setVisibility(View.GONE);
        floating_action_button.hide();
        recordingTime.setVisibility(View.GONE);
        initializeTimerTask();
        ArrayList<ParentCircularModal> myList = (ArrayList<ParentCircularModal>) getIntent().getSerializableExtra("myList");

        for (int i = 0; i < myList.size(); i++) {
            if (i == 0) {
                classId = String.valueOf(myList.get(i).getUserId()).trim();
                text_sectionname.setText(text_sectionname.getText() + myList.get(i).getTitle());
            } else {
                classId = classId + "," + String.valueOf(myList.get(i).getUserId()).trim();
                text_sectionname.setText(text_sectionname.getText() + ", " + myList.get(i).getTitle());
            }
        }

        fileDownload = this;
        recycler_view.setHasFixedSize(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        mLayoutManager.setReverseLayout(true);
        setAdapter();

        random = new Random();
        generateRandom_no = getRandomNumber();

        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        Gson gson = new Gson();
        Type type = new TypeToken<List<SignInPOJO.Datum>>() {
        }.getType();
        listSignInRes = new ArrayList<>();
        listSignInRes = gson.fromJson(appPreferencesHelper.getParentSignInResponse(), type);

        hashMapList = getCircularAdminList();
        if (hashMapList == null) {
            hashMapList = new HashMap<>();
        }

        if (!isNetworkConnected()) {
            if (listSignInRes != null && listSignInRes.size() > 0) {
                if (hashMapList != null && hashMapList.size() > 0 && hashMapList.containsKey(classId)) {
                    getAdminCircularPOJOArrayList = hashMapList.get(classId);
                    if (getAdminCircularPOJOArrayList != null && getAdminCircularPOJOArrayList.size() > 0) {
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
            getCircularRequestCall();
        }

        recycler_view.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //setFocusOnEditText(true);
                if (!hidden) {
                    hideRevealView();
                }
            }
        });

        //setRecycleItemTouchListener();
    }

    private void setRecycleItemTouchListener() {
        recycler_view.addOnItemTouchListener(new RecyclerItemClickListener(this,
                recycler_view, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                if (!hidden) {
                    hideRevealView();
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {
                if (!hidden) {
                    hideRevealView();
                }
            }
        }));
    }

    @OnClick(R.id.img_back)
    void onBackButtonClick() {
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
    }

    @OnClick(R.id.btnClose)
    public void onCloseClick(View view) {
        if (!hidden) {
            hideRevealView();
        }
        isAttachment = false;
        rlFilesAttached.setVisibility(View.GONE);
        imageView.setImageResource(0);
    }

    @OnClick(R.id.button_ChatBox_Send)
    void onSendButtonClick() {
        if (!hidden) {
            hideRevealView();
        }

        if (!isNetworkConnected()) {
            showMessage(getString(R.string.internet_not_available));
            return;
        }

        if (isMediaRecorded) {
            showMessage(getString(R.string.recording_in_progress));
            return;
        }

        if (edittext_chatbox.getText().toString().equals("")) {
            showMessage(getString(R.string.enter_message_text));
            return;
        }

        rlFilesAttached.setVisibility(View.GONE);
        imageView.setImageResource(0);
        recycler_view.scrollToPosition(0);
        showLoading();
        makeJson();
    }

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
            audioSavePathInDevice = new File(myDir, getString(R.string.aud_) + "-" + listSignInRes.get(0).getOrgId() + "-" + CommonUtils.getTimeStamp() + ".mp3").getPath();
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

        if (audioSavePathInDevice != null) {
            //getFilePath(audioSavePathInDevice);
            isAttachment = true;

            outputFile = new File(audioSavePathInDevice);
            fileExtension = outputFile.getAbsolutePath().substring(outputFile.getAbsolutePath().lastIndexOf("."));
            fileSize = Integer.parseInt(String.valueOf(outputFile.length() / 1024));
            fileName = audioSavePathInDevice.substring(audioSavePathInDevice.lastIndexOf("/") + 1);

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

    @OnClick(R.id.button_chatBox_Attach)
    void onAttachClick() {
        if (isMediaRecorded) {
            showMessage(getString(R.string.recording_in_progress));
            return;
        }
        int cx = (mRevealView.getRight() - 200);
        int cy = mRevealView.getBottom();
        int radius = mRevealView.getWidth();

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

    public void getCircularRequestCall() {
        hideKeyboard();
        showLoading();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        Call<GetAdminCircularParentPOJO> call = restClientAPI.getCircularParentForAdmin(
                listSignInRes.get(0).getOrgId(),
                classId, 6, listSignInRes.get(0).getAcademicId());

        call.enqueue(new Callback<GetAdminCircularParentPOJO>() {
            @Override
            public void onResponse(@NonNull Call<GetAdminCircularParentPOJO> call, @NonNull Response<GetAdminCircularParentPOJO> response) {
                GetAdminCircularParentPOJO getAdminCircularParentPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code>>>>" + code);
                if (code == RESPONSE_CODE) {
                    hideLoading();
                    if (getAdminCircularParentPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (getAdminCircularParentPOJO.getResponseStatus().equals(TRUE)) {
                            for (int i = 0; i < getAdminCircularParentPOJO.getData().size(); i++) {

                                String directoryName = "";

                                if (getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".doc")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".docx")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".xls")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".xlsx")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".pdf")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".txt")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".csv")) {

                                    // Word document
                                    directoryName = getString(R.string.app_name_docs);

                                } else if (getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".jpg")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".jpeg")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".png")) {

                                    // JPG,JPEG,PNG file
                                    directoryName = getString(R.string.app_name_images);

                                } else if (getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".gif")) {

                                    // GIF file
                                    directoryName = getString(R.string.app_name_gif);

                                } else if (getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".mp4")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".3gp")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".avi")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".flv")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".mov")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".m4a")) {

                                    // Video file
                                    directoryName = getString(R.string.app_name_video);

                                } else if (getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".mp3")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".wmv")
                                        || getAdminCircularParentPOJO.getData().get(i).getFileName().contains(".ogg")) {

                                    // Audio Video file
                                    directoryName = getString(R.string.app_name_audio);

                                }

                                File outputFile;
                                if (!directoryName.equals("")) {
                                    outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                                            + File.separator
                                            + getString(R.string.app_name) + File.separator + directoryName + File.separator,
                                            getAdminCircularParentPOJO.getData().get(i).getFileName());
                                } else {
                                    outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                                            + File.separator
                                            + getString(R.string.app_name) + File.separator,
                                            getAdminCircularParentPOJO.getData().get(i).getFileName());
                                }

                                if (outputFile.exists()) {
                                    if (outputFile.toString().contains(".doc") || outputFile.toString().contains(".docx")) {

                                        // Word document
                                        getAdminCircularParentPOJO.getData().get(i).setDocDownloaded(true);

                                    } else if (outputFile.toString().contains(".pdf")) {

                                        // PDF file
                                        getAdminCircularParentPOJO.getData().get(i).setPdfDownloaded(true);

                                    } else if (outputFile.toString().contains(".xls") || outputFile.toString().contains(".xlsx")) {

                                        // Excel file
                                        getAdminCircularParentPOJO.getData().get(i).setExcelFileDownloaded(true);

                                    } else if (outputFile.toString().contains(".jpg")) {

                                        // JPG file
                                        getAdminCircularParentPOJO.getData().get(i).setImageDownloaded(true);

                                    } else if (outputFile.toString().contains(".jpeg")) {

                                        // JPEG file
                                        getAdminCircularParentPOJO.getData().get(i).setImageDownloaded(true);

                                    } else if (outputFile.toString().contains(".png")) {

                                        // PNG file
                                        getAdminCircularParentPOJO.getData().get(i).setImageDownloaded(true);

                                    } else if (outputFile.toString().contains(".txt")) {

                                        // Text file
                                        getAdminCircularParentPOJO.getData().get(i).setTxtDownloaded(true);

                                    } else if (outputFile.toString().contains(".csv")) {

                                        // CSV file
                                        getAdminCircularParentPOJO.getData().get(i).setCsvFileDownloaded(true);

                                    } else if (outputFile.toString().contains(".gif")) {

                                        // GIF file
                                        getAdminCircularParentPOJO.getData().get(i).setGifFileDownloaded(true);

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
                                        getAdminCircularParentPOJO.getData().get(i).setAudioVideoDownloaded(true);

                                    } else {
                                        getAdminCircularParentPOJO.getData().get(i).setImageDownloaded(false);
                                        getAdminCircularParentPOJO.getData().get(i).setPdfDownloaded(false);
                                        getAdminCircularParentPOJO.getData().get(i).setDocDownloaded(false);
                                        getAdminCircularParentPOJO.getData().get(i).setTxtDownloaded(false);
                                        getAdminCircularParentPOJO.getData().get(i).setExcelFileDownloaded(false);
                                        getAdminCircularParentPOJO.getData().get(i).setCsvFileDownloaded(false);
                                        getAdminCircularParentPOJO.getData().get(i).setAudioVideoDownloaded(false);
                                        getAdminCircularParentPOJO.getData().get(i).setGifFileDownloaded(false);
                                    }

                                } else {
                                    getAdminCircularParentPOJO.getData().get(i).setImageDownloaded(false);
                                    getAdminCircularParentPOJO.getData().get(i).setPdfDownloaded(false);
                                    getAdminCircularParentPOJO.getData().get(i).setDocDownloaded(false);
                                    getAdminCircularParentPOJO.getData().get(i).setTxtDownloaded(false);
                                    getAdminCircularParentPOJO.getData().get(i).setExcelFileDownloaded(false);
                                    getAdminCircularParentPOJO.getData().get(i).setCsvFileDownloaded(false);
                                    getAdminCircularParentPOJO.getData().get(i).setAudioVideoDownloaded(false);
                                    getAdminCircularParentPOJO.getData().get(i).setGifFileDownloaded(false);
                                }
                            }
                            if (getAdminCircularPOJOArrayList.size() > 0) {
                                getAdminCircularPOJOArrayList.clear();
                            }
                            getAdminCircularPOJOArrayList.addAll(getAdminCircularParentPOJO.getData());
                            //Collections.reverse(getAdminCircularPOJOArrayList);
                            hashMapList.put(classId, getAdminCircularPOJOArrayList);
                            setCircularAdminList(hashMapList);
                            setAdapter();
                        } else {
                            setAdapter();
                            showMessage(getAdminCircularParentPOJO.getResponseMessage());
                        }
                    } else {
                        setAdapter();
                        showMessage(getAdminCircularParentPOJO.getResponseMessage());
                    }
                } else {
                    setAdapter();
                    hideLoading();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetAdminCircularParentPOJO> call, @NonNull Throwable t) {
                Log.e(TAG, "Throwable>>>>" + t.getMessage());
                setAdapter();
                hideLoading();
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    private void setAdapter() {
        if (getAdminCircularPOJOArrayList != null && getAdminCircularPOJOArrayList.size() > 0) {
            recycler_view.setVisibility(View.VISIBLE);
            CircularAdminParentAdapter circularAdminParentAdapter = new CircularAdminParentAdapter(this, getAdminCircularPOJOArrayList, listSignInRes, fileDownload);
            recycler_view.setAdapter(circularAdminParentAdapter);
            recycler_view.scrollToPosition(0);

        } else {
            recycler_view.setVisibility(View.GONE);
        }
    }

    private void makeJson() {
        JSONObject json = new JSONObject();
        try {
            json.put(ORGID, listSignInRes.get(0).getOrgId());
            json.put(ACADEMICID, listSignInRes.get(0).getAcademicId());
            json.put(CLASSID, classId);
            String body;
            if (edittext_chatbox.getText().toString().startsWith("http://")
                    || edittext_chatbox.getText().toString().startsWith("https://")) {
                body = "<a href=\"" + edittext_chatbox.getText().toString() + "\">" + edittext_chatbox.getText().toString() + "</a>";

            } else {
                body = edittext_chatbox.getText().toString();
            }
            json.put(MESSAGE, body);

            if (isAttachment) {
                json.put(FILE_NAME, fileName.trim());
            } else {
                json.put(FILE_NAME, "");
            }

            if (isAttachment) {
                json.put(GEN_FILENAME, fileName.trim());
            } else {
                json.put(GEN_FILENAME, "");
            }

            if (isAttachment) {
                json.put(FILE_SIZE, String.valueOf(fileSize));
            } else {
                json.put(FILE_SIZE, "");
            }

            json.put(CURRENTDATE, CommonUtils.getCurrentDate());
            json.put(TOADDRESS, PARENT_EMAIL);
            json.put(MODE, MODE_INSERT_APP);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (isAttachment) {
            sendParentCircularFile(json.toString());
        } else {
            sendParentCircular(json.toString());
        }
    }

    public void sendParentCircular(String json) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Call<SendAssignmentPOJO> call = restClientAPI.sendParentCircular(body);

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
                            getCircularRequestCall();
                            sendNotification();
                            edittext_chatbox.setText("");
                            hideLoading();
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
                showMessage(getString(R.string.internet_not_available));
            }
        });
    }

    public void sendParentCircularFile(final String json) {
        hideKeyboard();

        ApiClient apiClient = new ApiClient();
        RestClient restClientAPI = apiClient.getClient();

        MultipartBody.Part fileBody = prepareFilePart("file", outputFile);
        MultipartBody.Part fileBody2 = prepareStringPart("filepath", "/Group/" + listSignInRes.get(0).getOrgId() + "/circularPath");

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
                            sendParentCircular(json);
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
                classId,
                String.valueOf(0),
                0,
                edittext_chatbox.getText().toString(),
                MODE_CIRCULAR_GCM_APP_STUD);

        call.enqueue(new Callback<SendAssignmentPOJO>() {
            @Override
            public void onResponse(@NonNull Call<SendAssignmentPOJO> call, @NonNull Response<SendAssignmentPOJO> response) {
                SendAssignmentPOJO sendAssignmentPOJO = response.body();
                int code = response.code();
                Log.e(TAG, "code Notification>>>>" + code);
                if (code == RESPONSE_CODE) {
                    if (sendAssignmentPOJO.getResponseCode().equals(API_RESPONSE_CODE_WITH_DATA)) {
                        if (sendAssignmentPOJO.getResponseStatus().equals(TRUE)) {
                            Log.e(TAG, "code Message Noti>>>>" + sendAssignmentPOJO.getResponseMessage());
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

    @Override
    public void getFileDownloadCall(String url, String fileName) {
        TeacherActivity.fragmentListner.getFileDownloadCall(url, fileName);
    }

    @Override
    public boolean isInterNetConnected() {
        return isNetworkConnected();
    }

    @Override
    public void showMessage() {
        showMessage(getString(R.string.internet_not_available));
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

        UCrop.of(sourceUri, Uri.fromFile(outputFile)).start(this);
    }

    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(audioSavePathInDevice);
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

    public void setCircularAdminList(HashMap<String, List<GetAdminCircularParentPOJO.Datum>> assignmentAdminList) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<GetAdminCircularParentPOJO.Datum>>>() {
        }.getType();
        String json = gson.toJson(assignmentAdminList, type);
        this.appPreferencesHelper.setCircularAdminListResponse(json);
    }

    public HashMap<String, List<GetAdminCircularParentPOJO.Datum>> getCircularAdminList() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<GetAdminCircularParentPOJO.Datum>>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getCircularAdminListResponse(), type);
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
    }
}