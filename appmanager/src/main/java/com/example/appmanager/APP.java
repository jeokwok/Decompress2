package com.example.appmanager;

import android.graphics.drawable.Drawable;

/**
 * Created by 60411 on 2019/2/16.
 */

public class APP {
    private Drawable mIcon;
    private String mAppName;
    private String mActivityName;
    private String mPackageName;
    private String mVersionCode;
    private String mVersionName;

    public String getVersionName() {
        return mVersionName;
    }

    public void setVersionName(String versionName) {
        mVersionName = versionName;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable icon) {
        mIcon = icon;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }

    public String getActivityName() {
        return mActivityName;
    }

    public void setActivityName(String activityName) {
        mActivityName = activityName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public String getVersionCode() {
        return mVersionCode;
    }

    public void setVersionCode(String versionCode) {
        mVersionCode = versionCode;
    }


}
