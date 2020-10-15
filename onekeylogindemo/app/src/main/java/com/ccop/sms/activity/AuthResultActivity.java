package com.ccop.sms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccop.sms.R;
import com.ccop.sms.util.StatusBarUtil;

public class AuthResultActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG =AuthResultActivity.class.getSimpleName();
    private Context context;
    private RelativeLayout demoTryAgain;
    private TextView textViewAuth;
    private LinearLayout authenticationresult_success, authenticationresult_fail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_result);
        StatusBarUtil.setStatusBarTranslucent(this, true);
        context=this;
        initView();
        initListener();
        initEvent();
    }

    private void initEvent() {
        Intent intent = getIntent();
        int authResult = intent.getIntExtra("authResult",-1);
        String phone = intent.getStringExtra("phone");
        if (authResult==0) {

            textViewAuth.setText("手机号码与本机号一致");
            authenticationresult_success.setVisibility(View.VISIBLE);
        } else if (authResult==1) {
            textViewAuth.setText("手机号码与本机号不一致");
            authenticationresult_success.setVisibility(View.VISIBLE);
        } else {
            textViewAuth.setText("校验结果未知：code:"+authResult);

        }

    }

    private void initListener() {
        demoTryAgain.setOnClickListener(this);
    }

    private void initView() {
        authenticationresult_success = findViewById(R.id.demo_authenticationresult_success);
        authenticationresult_fail = findViewById(R.id.demo_authenticationresult_fail);
        demoTryAgain=findViewById(R.id.demo_try_again);
        textViewAuth = findViewById(R.id.demo_login_success);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.demo_try_again:
                startActivity(new Intent(context, MainActivity.class));
                break;
            default:
                break;
        }
    }
    public void callPhone(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:400-610-1019"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
