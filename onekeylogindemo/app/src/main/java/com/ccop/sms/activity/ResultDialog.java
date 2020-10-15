package com.ccop.sms.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ccop.sms.R;

public class ResultDialog extends Dialog {
    private TextView okBtn;
    private TextView resultTextView;

    public ResultDialog(@NonNull Context context) {
        super(context);
        onCreate();
    }

    public ResultDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        onCreate();
    }

    protected ResultDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        onCreate();
    }
    public void setResult(String result) {
        this.resultTextView.setText(result);
        this.show();
    }
    private void onCreate() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_result);
        okBtn = (TextView) findViewById(R.id.dialog_button);
        resultTextView = (TextView) findViewById(R.id.dialog_result);
        setTitle("提示");

        okBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ResultDialog.this.dismiss();
            }
        });
    }
}
