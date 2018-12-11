package com.valai.school.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valai.school.R;
import com.valai.school.fragmentsAdmin.AttendanceFragmentAdmin;
import com.valai.school.fragmentsAdmin.HomeFragmentAdmin;
import com.valai.school.fragmentsAdmin.MessageFragmentAdmin;
import com.valai.school.interfaces.DrawerLocker;
import com.valai.school.interfaces.FragmentListnerAdmin;
import com.valai.school.modal.GetAssignmentSectionPOJO;
import com.valai.school.modal.GetCircularClassPOJO;
import com.valai.school.modal.GetCircularStaffPOJO;
import com.valai.school.modal.GetClassAttendancePOJO;
import com.valai.school.modal.SignInPOJO;
import com.valai.school.network.ApiClient;
import com.valai.school.network.RestClient;
import com.valai.school.prefs.AppPreferencesHelper;
import com.valai.school.receivers.NotificationReceiver;
import com.valai.school.services.NLService;
import com.valai.school.utils.AppConstants;
import com.valai.school.utils.CommonUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.valai.school.utils.AppConstants.DELAY_TIME_OUT;
import static com.valai.school.utils.AppConstants.REQUEST_PERMISSION_CODE_STORAGE;
import static com.valai.school.utils.AppConstants.ROOT_DOWNLOAD;
import static com.valai.school.utils.AppConstants.ROOT_GROUP_FOLDER;
import static com.valai.school.utils.AppConstants.ROOT_SCHOOL_PHOTO_FOLDER;
import static com.valai.school.utils.AppConstants.TAG_ATTENDANCE;
import static com.valai.school.utils.AppConstants.TAG_CALENDAR;
import static com.valai.school.utils.AppConstants.TAG_CHANGE_PASSWORD;
import static com.valai.school.utils.AppConstants.TAG_HOME;
import static com.valai.school.utils.AppConstants.TAG_LEAVE_APPLICATION;
import static com.valai.school.utils.AppConstants.TAG_LESSON_PLANER;
import static com.valai.school.utils.AppConstants.TAG_MARK_ENTRY;
import static com.valai.school.utils.AppConstants.TAG_MESSAGE;
import static com.valai.school.utils.AppConstants.TAG_PAY_SLIP;
import static com.valai.school.utils.AppConstants.TAG_REPORTCARD_ATTENDANCE;
import static com.valai.school.utils.AppConstants.TAG_STUDENT_DATA;
import static com.valai.school.utils.AppConstants.TAG_TIME_TABLE;
import static com.valai.school.utils.CommonUtils.writeAdminResponseBodyToDisk;

public class AdminActivity extends BaseActivity implements DrawerLocker, FragmentListnerAdmin, ActivityCompat.OnRequestPermissionsResultCallback {
    public static final String TAG = AdminActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @BindView(R.id.nav_view)
    NavigationView nav_view;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.toolbar_student_details)
    TextView toolbar_student_details;

    @BindView(R.id.admin_image)
    ImageView admin_image;
    private String fileUrl;
    private String fileName;
    private int navItemIndex = 0;
    private String CURRENT_TAG = TAG_MESSAGE;
    private String[] activityTitles;
    private Handler mHandler;
    private AppPreferencesHelper appPreferencesHelper;
    private List<SignInPOJO.Datum> listSignInRes;
    public static FragmentListnerAdmin fragmentListner;
    private File outputFile;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotifyManager = null;
    private NotificationReceiver nReceiver;
    private int notificationId = 1;

    @NonNull
    public static Intent getStartIntent(Context context) {
        return new Intent(context, AdminActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        fragmentListner = this;
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        View navHeader = nav_view.getHeaderView(0);
        TextView txtName = navHeader.findViewById(R.id.name);
        final ImageView schoolLogo = navHeader.findViewById(R.id.img_logo);
        activityTitles = getResources().getStringArray(R.array.nav_item_admin_titles);
        admin_image.setVisibility(View.GONE);
        toolbar_student_details.setVisibility(View.GONE);
        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Gson gson = new Gson();
        Type type = new TypeToken<List<SignInPOJO.Datum>>() {
        }.getType();
        listSignInRes = new ArrayList<>();
        listSignInRes = gson.fromJson(appPreferencesHelper.getParentSignInResponse(), type);
        if (listSignInRes != null && listSignInRes.size() > 0) {
            if (listSignInRes.get(0).getOrgName() != null && !listSignInRes.get(0).getOrgName().equals("")) {
                txtName.setText(listSignInRes.get(0).getOrgName());
            } else {
                txtName.setText(getString(R.string.app_name));
            }

            if (listSignInRes.get(0).getOrgLogo() != null && !listSignInRes.get(0).getOrgLogo().equals("")) {
                Glide.with(this)
                        .load(ROOT_DOWNLOAD + ROOT_GROUP_FOLDER + listSignInRes.get(0).getOrgId() + ROOT_SCHOOL_PHOTO_FOLDER + listSignInRes.get(0).getOrgLogo())
                        //.error(R.mipmap.student_icon)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                        //.placeholder(R.mipmap.student_icon)
                        .into(schoolLogo);
            }
        }

        // Initializing Navigation Menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_MESSAGE;
            loadHomeFragment();
        }
    }

    /*
    * Returns respected fragment that user
    * selected from navigation menu
    */
    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer_layout.closeDrawers();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        mHandler.postDelayed(mPendingRunnable, DELAY_TIME_OUT);
        drawer_layout.closeDrawers();
        invalidateOptionsMenu();
    }

    @Nullable
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
//            case 0:
//                // HomeFragment
//                return new HomeFragmentAdmin();
            case 0:
                // DiaryFragment
                return new MessageFragmentAdmin();
            case 1:
                // MessageFragment
                return new AttendanceFragmentAdmin();
            case 3:
                // FeeFragment
                //return new FeeFragment();
            case 4:
                // AttendanceFragment
                //return new AttendanceFragment();
            case 5:
                // ExamFragment
                //return new ExamFragment();
            case 6:
                // TransportFragment
                //return new TransportFragment();
            case 7:
                // HolidayFragment
                //return new HolidayFragment();
            case 8:
                // ProfileFragment
                //return new ProfileFragment();
            case 9:
                // Change Password Fragment
                //return new ChangePasswordFragment();
            case 10:
                // Change Password Fragment
                //return new ChangePasswordFragment();
            default:
                // By Default HomeFragment
                return new MessageFragmentAdmin();
        }
    }

    private void setToolbarTitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar_title.setText(activityTitles[navItemIndex]);
        }
    }

    private void selectNavMenu() {
        nav_view.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
//                    case R.id.nav_home:
//                        navItemIndex = 0;
//                        CURRENT_TAG = TAG_HOME;
//                        break;
                    case R.id.nav_message:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_MESSAGE;
                      break;
                    case R.id.nav_attendance:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_ATTENDANCE;
                        break;
//                    case R.id.nav_mark_entry:
//                        navItemIndex = 2;
//                        CURRENT_TAG = TAG_MARK_ENTRY;
//                        break;
//                    case R.id.nav_report_card_attendance:
//                        navItemIndex = 3;
//                        CURRENT_TAG = TAG_REPORTCARD_ATTENDANCE;
//                        break;
//                    case R.id.nav_student_data:
//                        navItemIndex = 4;
//                        CURRENT_TAG = TAG_STUDENT_DATA;
//                        break;
//                    case R.id.nav_time_table:
//                        navItemIndex = 5;
//                        CURRENT_TAG = TAG_TIME_TABLE;
//                        break;
//                    case R.id.nav_lesson_planner:
//                        navItemIndex = 6;
//                        CURRENT_TAG = TAG_LESSON_PLANER;
//                        break;
//                    case R.id.nav_pay_slip:
//                        navItemIndex = 7;
//                        CURRENT_TAG = TAG_PAY_SLIP;
//                        break;
//                    case R.id.nav_leave_application:
//                        navItemIndex = 8;
//                        CURRENT_TAG = TAG_LEAVE_APPLICATION;
//                        break;
//                    case R.id.nav_change_password:
//                        navItemIndex = 9;
//                        CURRENT_TAG = TAG_CHANGE_PASSWORD;
//                        break;
                    case R.id.nav_logout:
                        appPreferencesHelper.logOutFromPref();
                        Intent intent = LogInActivity.getStartIntent(AdminActivity.this);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        navItemIndex = 0;
                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                loadHomeFragment();
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer_layout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer_layout.setDrawerLockMode(lockMode);
    }

    @Override
    public void setOnLoadHome(int itemIndex, String tag) {
        navItemIndex = itemIndex;
        CURRENT_TAG = tag;
        loadHomeFragment();
    }

    @Override
    public void setTopStudentDetails(String details) {
        toolbar_student_details.setText(details);
    }

    @Override
    public AppPreferencesHelper getAppPreferenceHelper() {
        return appPreferencesHelper;
    }

    @Override
    public void onFragmentAttach(final Fragment fragment, final String tag) {
        //this.tag = tag;
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, tag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        mHandler.postDelayed(mPendingRunnable, DELAY_TIME_OUT);
    }

    @Override
    public void onFragmentDetach(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager.beginTransaction().disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right).remove(fragment)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getFileDownloadCall(fileUrl, fileName);
                } else {
                    Log.e(TAG, "Storage Permission Not Granted");
                    showMessage(getString(R.string.allow_storage_permission));
                }

                break;
            }
        }
    }

    @Override
    public List<SignInPOJO.Datum> getSignInResultList() {
        return listSignInRes;
    }

    @SuppressLint({"InlinedApi", "StaticFieldLeak"})
    @Override
    public void getFileDownloadCall(final String fileUrls, final String fileNames) {
        this.fileName = fileNames;
        this.fileUrl = fileUrls;
        Boolean isPermission = isPermissionsGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!isPermission) {
            this.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CODE_STORAGE);
            return;
        }

        hideKeyboard();

        if (!fileUrls.equals("")) {

            ApiClient apiClient = new ApiClient();
            RestClient restClientAPI = apiClient.getClient();

            initializeNotificationBuilder();

            Call<ResponseBody> call = restClientAPI.downloadFileWithDynamicUrlAsync(fileUrls);
            call.enqueue(new Callback<ResponseBody>() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        new AsyncTask<Void, Boolean, Boolean>() {
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                            }

                            @Override
                            protected Boolean doInBackground(Void... booleans) {
                                String directoryName = "";

                                if (fileNames.contains(".doc") || fileNames.contains(".docx")
                                        || fileNames.contains(".xls") || fileNames.contains(".xlsx")
                                        || fileNames.contains(".pdf") || fileNames.contains(".txt")
                                        || fileNames.contains(".csv")) {

                                    // Word document
                                    directoryName = getString(R.string.app_name_docs);

                                } else if (fileNames.contains(".jpg") || fileNames.contains(".jpeg") || fileNames.contains(".png")) {

                                    // JPG,JPEG,PNG file
                                    directoryName = getString(R.string.app_name_images);

                                } else if (fileNames.contains(".gif")) {

                                    // GIF file
                                    directoryName = getString(R.string.app_name_gif);

                                } else if (fileNames.contains(".mp4") || fileNames.contains(".3gp")
                                        || fileNames.contains(".avi") || fileNames.contains(".flv")
                                        || fileNames.contains(".mov") || fileNames.contains(".m4a")) {

                                    // Video file
                                    directoryName = getString(R.string.app_name_video);

                                } else if (fileNames.contains(".mp3") || fileNames.contains(".wmv")
                                        || fileNames.contains(".ogg")) {

                                    // Audio Video file
                                    directoryName = getString(R.string.app_name_audio);

                                }
                                return writeAdminResponseBodyToDisk(AdminActivity.this, response.body(), directoryName, fileNames, fragmentListner);
                            }

                            @Override
                            protected void onPostExecute(Boolean aBoolean) {
                                super.onPostExecute(aBoolean);
                                Log.e("Download", "File>>" + aBoolean);
                                //Log.e("Download", "FileName>>" + fileNames);
                                if (!aBoolean) {
                                    showMessage("Error in File Downloading");
                                    mBuilder.setContentTitle("Error.");
                                    mBuilder.setContentText("Downloading Error!")
                                            // Removes the progress bar
                                            .setProgress(0, 0, false);
                                } else {
                                    CommonUtils.openFile(AdminActivity.this, outputFile);
                                    mBuilder.setContentTitle("File Downloaded.");
                                    mBuilder.setContentText("Download complete!")
                                            // Removes the progress bar
                                            .setProgress(0, 0, false);
                                }

                                mNotifyManager.notify(notificationId, mBuilder.build());
                                fileName = null;
                                fileUrl = null;

                                try {
                                    if (nReceiver != null) {
                                        unregisterReceiver(nReceiver);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                //hideLoading();
                            }

                        }.execute();
                    } else {
                        //hideLoading();
                        Log.d(TAG, "server contact failed");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Log.e(TAG, "error");
                    //hideLoading();
                }
            });

        } else {
            String directoryName = "";

            if (fileNames.contains(".doc") || fileNames.contains(".docx")
                    || fileNames.contains(".xls") || fileNames.contains(".xlsx")
                    || fileNames.contains(".pdf") || fileNames.contains(".txt")
                    || fileNames.contains(".csv")) {

                // Word document
                directoryName = getString(R.string.app_name_docs);

            } else if (fileNames.contains(".jpg") || fileNames.contains(".jpeg") || fileNames.contains(".png")) {

                // JPG,JPEG,PNG file
                directoryName = getString(R.string.app_name_images);

            } else if (fileNames.contains(".gif")) {

                // GIF file
                directoryName = getString(R.string.app_name_gif);

            } else if (fileNames.contains(".mp4") || fileNames.contains(".3gp")
                    || fileNames.contains(".avi") || fileNames.contains(".flv")
                    || fileNames.contains(".mov") || fileNames.contains(".m4a")) {

                // Video file
                directoryName = getString(R.string.app_name_video);

            } else if (fileNames.contains(".mp3") || fileNames.contains(".wmv")
                    || fileNames.contains(".ogg")) {

                // Audio Video file
                directoryName = getString(R.string.app_name_audio);

            }

            if (!directoryName.equals("")) {
                //Log.e("Download", "FileNameElse>>" + fileNames);
                outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                        + File.separator
                        + getString(R.string.app_name) + File.separator + directoryName + File.separator, fileNames);
            } else {
                //Log.e("Download", "FileNameElse>>" + fileNames);
                outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                        + File.separator
                        + getString(R.string.app_name) + File.separator, fileNames);
            }

            //outputFile = new File(myDir, fileNames);
            if (outputFile.exists()) {
                //Log.e("outputFile", "isExists>>");
                CommonUtils.openFile(AdminActivity.this, outputFile);
            }
        }
    }

    private void initializeNotificationBuilder() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Downloading...")
                .setContentText("Download in progress")
                .setSmallIcon(R.mipmap.ic_launcher);
        // Start a lengthy operation in a background thread
        mBuilder.setProgress(0, 0, true);
        mNotifyManager.notify(notificationId, mBuilder.build());
        mBuilder.setAutoCancel(true);
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(NLService.NOT_TAG);
        registerReceiver(nReceiver, filter);
    }

    @Override
    public void setFileSizeDownloaded(long fileSizeDownloaded, long totalFileSize) {
        mBuilder.setContentText("Downloaded (" + fileSizeDownloaded + "/" + totalFileSize);
        mBuilder.setProgress(Integer.parseInt(String.valueOf(totalFileSize)),
                Integer.parseInt(String.valueOf(fileSizeDownloaded)), false);
        // Displays the progress bar for the first time.
        mNotifyManager.notify(notificationId, mBuilder.build());
    }

    @Override
    public void setOutputFilePath(File outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void setClassAttendanceList(List<GetClassAttendancePOJO.Datum> getClassAttendanceList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetClassAttendancePOJO.Datum>>() {
        }.getType();
        String json = gson.toJson(getClassAttendanceList, type);
        this.appPreferencesHelper.setClassAttendanceListResponse(json);
    }

    @Override
    public List<GetClassAttendancePOJO.Datum> getClassAttendanceList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetClassAttendancePOJO.Datum>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getClassAttendanceListResponse(), type);
    }

    @Override
    public void setCircularClassList(List<GetCircularClassPOJO.Datum> circularClassList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetCircularClassPOJO.Datum>>() {
        }.getType();
        String json = gson.toJson(circularClassList, type);
        this.appPreferencesHelper.setCircularClassListResponse(json);
    }

    @Override
    public List<GetCircularClassPOJO.Datum> getCircularClassList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetCircularClassPOJO.Datum>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getCircularClassListResponse(), type);
    }

    @Override
    public void setCircularStaffList(List<GetCircularStaffPOJO.Datum> circularStaffList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetCircularStaffPOJO.Datum>>() {
        }.getType();
        String json = gson.toJson(circularStaffList, type);
        this.appPreferencesHelper.setCircularStaffListResponse(json);
    }

    @Override
    public List<GetCircularStaffPOJO.Datum> getCircularStaffList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetCircularStaffPOJO.Datum>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getCircularStaffListResponse(), type);
    }

    @Override
    public void setAssignmentAdminList(List<GetAssignmentSectionPOJO.Datum> assignmentAdminList) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetAssignmentSectionPOJO.Datum>>() {
        }.getType();
        String json = gson.toJson(assignmentAdminList, type);
        this.appPreferencesHelper.setAssignmentAdminListResponse(json);
    }

    @Override
    public List<GetAssignmentSectionPOJO.Datum> getAssignmentAdminList() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<GetAssignmentSectionPOJO.Datum>>() {
        }.getType();
        return gson.fromJson(appPreferencesHelper.getAssignmentAdminListResponse(), type);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        // checking if user is on other navigation menu
        // rather than home
        if (navItemIndex != 0) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_MESSAGE;
            loadHomeFragment();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (mNotifyManager != null) {
            mNotifyManager.cancelAll();
        }
        super.onDestroy();
    }
}