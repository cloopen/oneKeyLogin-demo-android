package com.ccop.sms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ccop.sms.R;

public class OtherLoginActivity extends AppCompatActivity {

    private String otherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_login);
        TextView otherText = findViewById(R.id.other_text);
        otherName = getIntent().getStringExtra("other_name");
        if (!TextUtils.isEmpty(otherName)){
            otherText.setText(otherName);
        }
    }

    public void returnBt(View view) {
        finish();
    }
}