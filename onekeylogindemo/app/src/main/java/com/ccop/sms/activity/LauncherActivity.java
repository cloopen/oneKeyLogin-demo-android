package com.ccop.sms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ccop.sms.R;
import com.ccop.sms.util.PermissionConstants;
import com.ccop.sms.util.PermissionUtils;
import com.ccop.sms.util.ShareUtils;
import com.ccop.sms.util.StatusBarUtil;

public class LauncherActivity extends AppCompatActivity {
    private static final String TAG =LauncherActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        StatusBarUtil.setStatusBarTranslucent(this, true);
        Log.e(TAG,"Create LauncherActivity");
        initView();

    }
    @SuppressLint("WrongConstant")
    private void initPermission() {
        PermissionUtils.permission(PermissionConstants.STORAGE,PermissionConstants.PHONE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                    }

                    @Override
                    public void onDenied() {
                    }
                }).request(this);
    }
    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(HANDLER_SPLASH, 2000);
    }

    /**
     * 1.延时2000ms
     * 2.判断程序是否第一次运行
     * 3.Activity全屏主题
     */
    //闪屏业延时
    private static final int HANDLER_SPLASH = 1001;
    //判断程序是否是第一次运行
    private static final String SHARE_IS_FIRST = "isFirst";


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_SPLASH:
                    //判断程序是否是第一次运行
                    if (isFirst()) {
                        Log.e(TAG,"Program first run");
                        //第一次运行进入导航页
                        startActivity(new Intent(LauncherActivity.this, GuideActivity.class));
                        initPermission();
                    } else {
                        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                    }
                    finish();
                    break;
                default:
                    break;
            }
            return false;
        }

    });

    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst = (boolean) ShareUtils.getParam(this, SHARE_IS_FIRST, true);
        if (isFirst) {
            ShareUtils.saveParam(this, SHARE_IS_FIRST, false);
            //是第一次运行
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
