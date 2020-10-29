package com.ccop.sms.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ccop.sms.util.StatusBarUtil;
import com.ccop.sms.util.Utils;
import com.ccop.sms.validate.Request;
import com.ccop.sms.validate.RequestCallback;
import com.cloopen.okl.sdk.OneKeyLoginHelper;
import com.cloopen.okl.sdk.listener.PhoneAuthListener;

import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ccop.sms.R;

import org.json.JSONException;
import org.json.JSONObject;


public class PhoneAuthActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG =PhoneAuthActivity.class.getSimpleName();
    private RelativeLayout btnPhoneAuth;
    private EditText editTextPhoneAuth;
    private ProgressBar loading;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        StatusBarUtil.setStatusBarTranslucent(this, true);
        context = this;
        initView();
        initListener();
    }


    private void initView() {
        btnPhoneAuth=findViewById(R.id.demo_authentication_button);
        editTextPhoneAuth=findViewById(R.id.edit_text_phone_auth);
        loading=findViewById(R.id.demo_phone_loading);
    }

    private void initListener() {
        btnPhoneAuth.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.demo_authentication_button:
                phoneValidate();
                break;
            default:
                break;
        }
    }

    private void phoneValidate(){
        Log.e(TAG,"Phone Validate");
        final String phone = editTextPhoneAuth.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(context, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean checkFlag= Utils.validataPhonNumb(phone);
        if(!checkFlag){
            Toast.makeText(context, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        btnPhoneAuth.setClickable(false);
        loading.setVisibility(View.VISIBLE);
        OneKeyLoginHelper.getInstance().phoneAuth(new PhoneAuthListener(){
            @Override
            public void phoneAuthResult(String code, final String data) {
                Log.e(TAG,"Token of phone auth,code:"+code + ",data:"+data);
                //开发者已成功对接SDK,获取token
                if(code.equals("000000")){
                    try {
                        JSONObject json = new JSONObject(data);
//                        int operator = json.optInt("operatorType");
                        OneKeyLoginHelper.getInstance().quitAuthActivity();
                        String token = json.optString("token");
                        Log.e(TAG,"Token is:"+token);
                        PhoneAuthActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnPhoneAuth.setClickable(true);
                                loading.setVisibility(View.GONE);
                                Toast.makeText(context, data, Toast.LENGTH_LONG).show();
                            }
                        });

                        //此处开发者可自行配置（自己）服务器地址及参数逻辑进行后台token校验  核对是否号码一致
//                        phoneValidate( BuildConfig.APP_ID, token,phone, new PhoneAuthListener() {
//                            @Override
//                            public void phoneAuthResult(String code, final String msg) {
//                                Log.e(TAG,"validate phone,code:"+code + ",msg:"+msg);
////                                btnPhoneAuth.setClickable(true);
////                                loading.setVisibility(View.GONE);
//                                if(code.equals("000000")){
//                                    try {
//                                        JSONObject json = new JSONObject(msg);
//                                        //0：一致 1：不一致 2：未知
//                                        int authResult=json.optInt("result");
//                                            OneKeyLoginHelper.getInstance().quitAuthActivity();
//                                            Intent intent = new Intent(context, AuthResultActivity.class);
//                                            intent.putExtra("authResult", authResult);
//                                            intent.putExtra("mobile", phone);
//                                            startActivity(intent);
//                                            finish();
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }else{
//                                    Log.e(TAG,"validate phone fail");
//                                    PhoneAuthActivity.this.runOnUiThread(new Runnable() {
//                                        public void run() {
//                                            btnPhoneAuth.setClickable(true);
//                                            loading.setVisibility(View.GONE);
//                                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//                                        }
//                                    });
//                                }
//
//                            }
//
//                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    PhoneAuthActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnPhoneAuth.setClickable(true);
                            loading.setVisibility(View.GONE);
                            Toast.makeText(context, data, Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
    }

    public void phoneValidate(final String appId, final String token,final String mobile,final PhoneAuthListener listener){
        Bundle values = new Bundle();
        values.putString("appId",appId);
        values.putString("token",token);
        values.putString("mobile",mobile);
        Request.getInstance(this).phoneValidate(values, new RequestCallback() {

            @Override
            public void onRequestComplete(String resultCode, String resultDes, JSONObject jsonObj) {
                Log.i("Token校验结果", jsonObj.toString());
                listener.phoneAuthResult(resultCode,jsonObj.toString());
            }
        });

    }

}
