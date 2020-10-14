package com.ccop.sms.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ccop.sms.R;
import com.ccop.sms.util.MobileUtils;
import com.ccop.sms.util.PermissionConstants;
import com.ccop.sms.util.PermissionUtils;
import com.ccop.sms.util.StatusBarUtil;

import androidx.annotation.NonNull;

/**
 * 主页
 */
public class MainActivity extends Activity implements View.OnClickListener{
    private static final String TAG =MainActivity.class.getSimpleName();
    //private Button btnLoginDebug,btnLoginFull,btnLoginPopup,btnPhoneValidate;
    private Button btnOneKeyLogin,btnPhoneValidate;
    private Context context;
    private boolean mHasPermission;
    private PermissionUtils permissionUtils;
    private View myViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG,"Create MainActivity");
        context = this;
        StatusBarUtil.setStatusBarTranslucent(this, false);
        initView();
        initListener();
        initPermission();
    }
    @SuppressLint("WrongConstant")
    private void initPermission() {
        permissionUtils = PermissionUtils.permission(PermissionConstants.STORAGE,PermissionConstants.PHONE);
        permissionUtils.callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        mHasPermission = true;
                    }

                    @Override
                    public void onDenied() {
                        mHasPermission = false;
                    }
                }).request(this);
    }
    @Override
    public void onClick(View view) {
        this.myViews = view;
        if(!mHasPermission){
            Toast.makeText(context, "请先授予权限", Toast.LENGTH_SHORT).show();
            initPermission();
            return;
        }
        int viewId = view.getId();
        switch (viewId){
            case R.id.btn_one_key_login:
                MobileUtils.getSystemService(context);
                //一键登录
                startActivity(new Intent(context, OneKeyLoginActivity.class));
                break;
            case R.id.btn_phone_validate:
                startActivity(new Intent(context, PhoneAuthActivity.class));
                break;
            default:
                break;
        }
    }


    private void initView(){
        //btnLoginDebug=findViewById(R.id.btn_login_debug);
        btnOneKeyLogin = findViewById(R.id.btn_one_key_login);;
        btnPhoneValidate=findViewById(R.id.btn_phone_validate);
    }
    private void initListener(){
//        btnLoginDebug.setOnClickListener(this);
        btnOneKeyLogin.setOnClickListener(this);
        btnPhoneValidate.setOnClickListener(this);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permissionUtils!=null&&requestCode==1) {
            permissionUtils.onRequestPermissionsResult(this);
            if (myViews != null){
                onClick(myViews);
            }
        }
    }
}
