package com.ccop.sms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ccop.sms.R;
import com.ccop.sms.util.StatusBarUtil;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG =HomeActivity.class.getSimpleName();
    private Context context;
    private Button btnReturnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        StatusBarUtil.setStatusBarTranslucent(this, true);
        context=this;
        initView();
        initListener();
    }

    private void initListener() {
        btnReturnMain.setOnClickListener(this);
    }

    private void initView() {
        btnReturnMain=findViewById(R.id.btn_return_main);

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.btn_return_main:

                finish();
                break;
            default:
                break;
        }
    }
}
