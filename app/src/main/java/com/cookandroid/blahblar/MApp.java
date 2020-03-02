package com.cookandroid.blahblar;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

//import com.kakao.auth.KakaoSDK;

public class MApp extends Application {
    private Context currentContext;
    private static volatile MApp obj = null;
    private static volatile Activity currentActivity = null;


    private String userId;
    private String userName;
    private String userPhone;

    @Override
    public void onCreate() {
        super.onCreate();
        obj = this;
        //KakaoSDK.init(new KakaoSDKAdapter());
    }

    public Context getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public static MApp getGlobalApplicationContext() {
        return obj;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        MApp.currentActivity = currentActivity;
    }

}
