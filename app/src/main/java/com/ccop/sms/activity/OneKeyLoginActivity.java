package com.ccop.sms.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ccop.sms.R;
import com.ccop.sms.listener.OneKeyLoginListener;
import com.ccop.sms.util.ConfigUtils;
import com.ccop.sms.util.StatusBarUtil;
import com.ccop.sms.util.Utils;
import com.cloopen.okl.sdk.BuildConfig;
import com.cloopen.okl.sdk.OneKeyLoginHelper;
import com.cloopen.okl.sdk.config.AuthUIConfig;
import com.cloopen.okl.sdk.listener.LoginResultListener;

import org.json.JSONObject;

import cn.com.chinatelecom.account.sdk.ui.AuthActivity;

/**
 * 一键登录
 */
public class OneKeyLoginActivity extends Activity implements View.OnClickListener {
    private static final String TAG =OneKeyLoginActivity.class.getSimpleName();
    //private Button btnLoginDebug,btnLoginFull,btnLoginPopup,btnPhoneValidate;
    private RelativeLayout btnLoginFull,btnLoginDialog;
    private Button btnLoginDebug;
    private ProgressBar loadingFull,loadingDialog;
    private Context context;
    private boolean isFullScreen = true; //是否全屏
    private RelativeLayout layoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_key_login);
        StatusBarUtil.setStatusBarTranslucent(this, false);
        context = this;
        initView();
        initListener();

    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();
        switch (viewId){
            case R.id.btn_login_full:
                isFullScreen = true;
                //全屏模式自定义
                loadingFull.setVisibility(View.VISIBLE);
                oneKeyLogin(ConfigUtils.getAuthVerticalUIConfig(context));
                //默认全屏 new AuthUIConfig.Builder().build()
//                OneKeyLoginHelper.getInstance().setAuthUIConfig(new AuthUIConfig.Builder().build());
                break;
            case R.id.btn_login_dialog:
                isFullScreen = false;
                //弹窗标准 自定义
                loadingDialog.setVisibility(View.VISIBLE);
                oneKeyLogin(ConfigUtils.getAuthDialogUIConfig(context));
                break;
            case R.id.btn_login_debug:
                startActivity(new Intent(context, DebugActivity.class));
                break;
            default:
                break;
        }
    }

    private void initView() {
        layoutBtn = findViewById(R.id.layout_btn);
        btnLoginFull = findViewById(R.id.btn_login_full);
        btnLoginDialog = findViewById(R.id.btn_login_dialog);
        btnLoginDebug=findViewById(R.id.btn_login_debug);
        loadingFull=findViewById(R.id.login_full_loading);
        loadingDialog=findViewById(R.id.login_dialog_loading);
    }

    private void initListener() {
        btnLoginFull.setOnClickListener(this);
        btnLoginDialog.setOnClickListener(this);
        btnLoginDebug.setOnClickListener(this);
    }

    private void oneKeyLogin(AuthUIConfig authUIConfig) {
        OneKeyLoginHelper.getInstance().setAuthUIConfig(authUIConfig);
        disableBtn();
        Log.e(TAG,"Open auth");
        OneKeyLoginHelper.getInstance().openAuth(context, new LoginResultListener() {
            //获取到token
            @Override
            public void loginResult(final String code, final String data) {
                Log.e(TAG,"login result,code:"+code + ",data:"+data);
                try {
                    if (code.equals("000000")) {//成功获取token
                        final JSONObject object = new JSONObject(data);
                        Log.d(TAG,"token="+object.optString("token"));
                        OneKeyLoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resetBtn();
                                Toast.makeText(context, "code:"+code + ",data:"+data, Toast.LENGTH_LONG).show();
                                OneKeyLoginHelper.getInstance().quitAuthActivity();
                                startActivity(new Intent(context, HomeActivity.class));
                            }
                        });

                        //此处开发者可自行配置服务器地址及参数逻辑进行后台校验
//                        Utils.tokenValidate(context, BuildConfig.APP_ID, object.optString("token"), new OneKeyLoginListener() {
//                            @Override
//                            public void oneKeyLoginResult(final String code, final String msg) {
//                                Log.e(TAG,"接收到验证结果"+code);
//
//                                    OneKeyLoginActivity.this.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if(code.equals("000000")) {
//                                                startActivity(new Intent(context, HomeActivity.class));
//                                                OneKeyLoginHelper.getInstance().quitAuthActivity();
//                                            }else{
//                                                 OneKeyLoginHelper.getInstance().quitAuthActivity();
//                                                 Toast.makeText(context, "code:"+code + ",data:"+data, Toast.LENGTH_LONG).show();
//                                        }
//                                            resetBtn();
//                                        }
//                                    });
//
//                            }
//                        });

                    } else if (code.equals("000003")){
                        Log.e(TAG,"响应异常");
                        OneKeyLoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resetBtn();
                                Toast.makeText(context, "code:"+code + ",data:"+data, Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        OneKeyLoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                OneKeyLoginHelper.getInstance().quitAuthActivity();
                                resetBtn();
                                Toast.makeText(context, "code:"+code + ",data:"+data, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void disableBtn(){
        btnLoginFull.setClickable(false);
        btnLoginDialog.setClickable(false);
        btnLoginDebug.setClickable(false);
    }
    private void resetBtn(){
        btnLoginFull.setClickable(true);
        btnLoginDialog.setClickable(true);
        btnLoginDebug.setClickable(true);
        loadingFull.setVisibility(View.GONE);
        loadingDialog.setVisibility(View.GONE);
    }
}
