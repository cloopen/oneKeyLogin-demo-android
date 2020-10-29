package com.ccop.sms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ccop.sms.BuildConfig;
import com.ccop.sms.R;
import com.ccop.sms.listener.SMSVerificationListener;
import com.ccop.sms.util.ShareUtils;
import com.ccop.sms.util.Utils;
import com.ccop.sms.validate.Request;
import com.ccop.sms.validate.RequestCallback;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class SMSVerificationActivity extends AppCompatActivity {

    private Button initSmsBtn;
    private EditText smsVerification;
    private EditText phoneNumber;
    private int countDown;
    private Handler handler = new Handler();
    private String TAG = "SMSVerificationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_m_s_verification);
        phoneNumber = findViewById(R.id.phone_number);
        smsVerification = findViewById(R.id.sms_verification);
        initSmsBtn = findViewById(R.id.init_sms_btn);
        initSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkFlag= Utils.validataPhonNumb(phoneNumber.getText().toString());
                if(!checkFlag){
                    Toast.makeText(SMSVerificationActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                //此处开发者可自行配置（自己）服务器地址及参数逻辑进行后台验证码获取
//                smsValidate("BuildConfig.APP_ID", phoneNumber.getText().toString(), new SMSVerificationListener() {
//                    @Override
//                    public void oneSMSVerificationResult(final String code, final String msg) {
//                        Log.e(TAG,"validate sms,code:"+code + ",msg:"+msg);
//                            SMSVerificationActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if(code.equals("000000")){
//                                    //发送验证码
//                                    startCountDown();
//                                    Toast.makeText(SMSVerificationActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
//                                    }else {
//                                        Toast.makeText(SMSVerificationActivity.this, msg, Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                    }
//                });
            }
        });
    }
    public void smsValidate(final String appId, final String mobile, final SMSVerificationListener listener){
        Bundle values = new Bundle();
        values.putString("appId",appId);
        values.putString("mobile",mobile);
        Request.getInstance(this).smsValidate(values, new RequestCallback() {
            @Override
            public void onRequestComplete(String resultCode, String resultDes, JSONObject jsonObj) {
                Log.i("SMS发送结果", jsonObj.toString());
                listener.oneSMSVerificationResult(resultCode,jsonObj.toString());
            }
        });
    }
    public void returnBt(View view) {
        finish();
    }

    public void verifyBtn(View view) {
        String phone = phoneNumber.getText().toString();
        boolean checkFlag= Utils.validataPhonNumb(phoneNumber.getText().toString());
        if(!checkFlag){
            Toast.makeText(SMSVerificationActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        String sms = smsVerification.getText().toString();
        if (TextUtils.isEmpty(sms)){
            Toast.makeText(SMSVerificationActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
       String captcha = (String) ShareUtils.getParam(this,"captcha","");
       String mobile = (String) ShareUtils.getParam(this,"mobile","");
        if (TextUtils.isEmpty(captcha)||!captcha.equals(sms)){
            Toast.makeText(SMSVerificationActivity.this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mobile)||!mobile.equals(phone)){
            Toast.makeText(SMSVerificationActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        long smsTime = (long) ShareUtils.getParam(this,"sms_time",Long.parseLong("0"));
        int intervalTime = (int) ((System.currentTimeMillis() - smsTime)/1000);
        Log.d("验证码间隔",intervalTime+"");
        if (intervalTime>120){
            Toast.makeText(SMSVerificationActivity.this, "验证码超时,请重新发送", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
    private void startCountDown() {
        countDown = 60;
        initSmsBtn.setText(countDown + "秒");
        initSmsBtn.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (countDown <= 0) {
                    initSmsBtn.setEnabled(true);
                    initSmsBtn.setText("重新发送");
                    return;
                }
                countDown--;
                initSmsBtn.setText(countDown + "秒");
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        handler = null;
    }

}