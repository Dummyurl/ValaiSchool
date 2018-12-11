package com.valai.school.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.valai.school.R;
import com.valai.school.prefs.AppPreferencesHelper;
import com.valai.school.utils.AppConstants;
import com.valai.school.utils.NotificationUtils;

import static com.valai.school.utils.AppConstants.USER_TYPE_ID_FOR_ADMIN_SCHOOL;
import static com.valai.school.utils.AppConstants.USER_TYPE_ID_FOR_PARENT;
import static com.valai.school.utils.AppConstants.USER_TYPE_ID_FOR_TEACHER_STAFF;

/**
 * @author by Mohit Arora on 24/1/18.
 */
public class SplashActivity extends BaseActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();
    private int userTypeId = 0;
    private AppPreferencesHelper appPreferencesHelper;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appPreferencesHelper = new AppPreferencesHelper(this, AppConstants.PREF_NAME);
        userTypeId = appPreferencesHelper.getUserType();

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(AppConstants.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(AppConstants.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    Log.e("Message", "From Push>>" + message);
                }
            }
        };

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openActivities();
            }
        }, AppConstants.SPLASH_TIME_OUT);
    }

    public void openActivities() {
        if (userTypeId != 0) {
            if (userTypeId == USER_TYPE_ID_FOR_ADMIN_SCHOOL) {
                appPreferencesHelper.setUserType(userTypeId);
                openAdminActivity();
            } else if (userTypeId == USER_TYPE_ID_FOR_TEACHER_STAFF) {
                appPreferencesHelper.setUserType(userTypeId);
                openTeacherActivity();
            } else if (userTypeId == USER_TYPE_ID_FOR_PARENT) {
                appPreferencesHelper.setUserType(userTypeId);
                openParentActivity();
            } else {
                openLogInActivity();
            }
        } else {
            openLogInActivity();
        }
    }

    public void openLogInActivity() {
        Intent intent = LogInActivity.getStartIntent(SplashActivity.this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void openAdminActivity() {
        Intent intent = AdminActivity.getStartIntent(SplashActivity.this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void openParentActivity() {
        Intent intent = ParentsActivity.getStartIntent(SplashActivity.this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void openTeacherActivity() {
        Intent intent = TeacherActivity.getStartIntent(SplashActivity.this);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}