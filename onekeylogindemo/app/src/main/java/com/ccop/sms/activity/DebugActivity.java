package com.ccop.sms.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ccop.sms.BuildConfig;
import com.ccop.sms.R;
import com.ccop.sms.listener.OneKeyLoginListener;
import com.ccop.sms.util.ConfigUtils;
import com.ccop.sms.util.StatusBarUtil;
import com.ccop.sms.util.StringFormat;
import com.ccop.sms.util.Tools;
import com.ccop.sms.util.Utils;
import com.cloopen.okl.sdk.OneKeyLoginHelper;
import com.cloopen.okl.sdk.config.AuthRegisterUIConfig;
import com.cloopen.okl.sdk.config.AuthUIConfig;
import com.cloopen.okl.sdk.listener.CustomInterface;
import com.cloopen.okl.sdk.listener.InitListener;
import com.cloopen.okl.sdk.listener.LoginResultListener;
import com.cloopen.okl.sdk.listener.OpenAuthListener;
import com.cloopen.okl.sdk.listener.PreLoginListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;

public class DebugActivity extends Activity implements View.OnClickListener{
    private static final String TAG =DebugActivity.class.getSimpleName();
    private Context context;
    private long startTime, loginTime, authTime, aunthDeltTime;
    private SharedPreferences sp;
    private TextView check_message_init, check_message;
    private String message1, message2, message3, message4, message5;
    private long time1, time2;
    private String deviceMessage;
    private Button sdk_init, sdk_pre, sdk_login;
    private ImageView loading_view;
    private static final int DEBUG = 1;
    public static String[] operatorArray = new String[]{"未知", "移动", "联通", "电信"};
    public static String[] networkArray = new String[]{"未知", "数据流量", "纯WiFi", "流量+WiFi"};

    private String resultString;
    private ResultDialog resultDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        StatusBarUtil.setStatusBarTranslucent(this, true);
        context=this;
        initView();
        sp = this.getSharedPreferences("auth_result", MODE_PRIVATE);
    }
    private void initView() {
        sdk_init = findViewById(R.id.demo_sdk_init);
        sdk_pre = findViewById(R.id.demo_sdk_pre);
        sdk_login = findViewById(R.id.demo_sdk_login);
        loading_view = findViewById(R.id.demo_loading_view);
        Glide.with(getApplicationContext()).load(R.drawable.demo_loading).into(loading_view);
        loading_view.setVisibility(View.GONE);
        sdk_init.setOnClickListener(this);
        sdk_pre.setOnClickListener(this);
        sdk_login.setOnClickListener(this);
        check_message_init = findViewById(R.id.demo_check_message_init);
        check_message = findViewById(R.id.demo_check_message);
        deviceMessage = "手机机型：" + Build.BRAND + android.os.Build.MODEL + "\n" + "android系统版本：" + android.os.Build.VERSION.RELEASE;
        check_message.setText(deviceMessage);
    }


    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.demo_sdk_init:
                initOklSdk();
                break;
            case R.id.demo_sdk_pre:
                preGetNum();
                break;
            case R.id.demo_sdk_login:
                openAuth();
                break;
        }
    }

    private void getAppSign() {
        String sign=Tools.getSign(context);
        Log.e(TAG,"App sign is "+sign);
        resultDialog.setResult(sign);
    }

    private void initOklSdk() {
        cleanData();
        time1 = System.currentTimeMillis();
        OneKeyLoginHelper.getInstance().setDebug(true);
        OneKeyLoginHelper.getInstance().init(context, BuildConfig.APP_ID,new InitListener() {
            @Override
            public void initStatus(String code , String data) {
                Log.e(TAG,"InitStatus,code:"+code +",data:"+data);
                long costTime = System.currentTimeMillis() - time1;
                message1 = "初始化步骤：" + "\n" + "开始时间:" + getCurrentTime() + "\n" + "耗时:" + costTime + "ms" + "\n" + "日志:" + "code=" + code + "\n" + "result=" + data + "\n";
                //check_message_init.setText("初始化步骤：" + "\n" + "开始时间");
                //处理完成后给handler发送消息
                Message msg = new Message();
                msg.what= DEBUG;
                msg.obj = message1;
                mHandler.sendMessage(msg);
            }
        });

    }

    private void preGetNum() {
        Log.e(TAG,"Pre get num");
        cleanData();
        time2 = System.currentTimeMillis();
        //预选号
        OneKeyLoginHelper.getInstance().reqPreLogin(10000,new PreLoginListener() {
            @Override
            public void preLoginStatus(String code, String data) {
                Log.e(TAG,"preLoginStatus,code:"+code +",data:"+data);
                long costTime = System.currentTimeMillis() - time2;
                message2 = "预取号步骤：" + "\n" + "开始时间:" + getCurrentTime() + "\n" + "耗时:" + costTime + "ms" + "\n" + "日志:" + "code=" + code +"\n"+ "result=" + data + "\n";
                Message msg = new Message();
                msg.what= DEBUG;
                msg.obj = message2;
                mHandler.sendMessage(msg);
            }
        });
    }
    private void openAuth() {
        Log.e(TAG,"Open auth");

        OneKeyLoginHelper.getInstance().setAuthUIConfig(ConfigUtils.getAuthVerticalUIConfig(context));
        OneKeyLoginHelper.getInstance().openAuth(context,new LoginResultListener() {
            //获取到token
            @Override
            public void loginResult(final String code, final String data) {
                Log.e(TAG,"login result,code:"+code + ",data:"+data);
                try {

                    if (code.equals("000000")) {//成功获取token
                        final JSONObject object = new JSONObject(data);
                        Log.d(TAG,"token="+object.optString("token"));
                        DebugActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "code:"+code + ",data:"+data, Toast.LENGTH_LONG).show();
                                OneKeyLoginHelper.getInstance().quitAuthActivity();
                                startActivity(new Intent(context, HomeActivity.class));
                            }
                        });
                        //此处开发者可自行配置服务器地址及参数逻辑进行后台校验
//                        Utils.tokenValidate(context,BuildConfig.APP_ID, object.optString("token"), new OneKeyLoginListener() {
//                            @Override
//                            public void oneKeyLoginResult(String code, final String msg) {
//                                Log.e(TAG,"接收到验证结果");
//                                if(code.equals("000000")) {
//                                    startActivity(new Intent(context, HomeActivity.class));
//                                    OneKeyLoginHelper.getInstance().quitAuthActivity();
//                                }else{
//                                    DebugActivity.this.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            OneKeyLoginHelper.getInstance().quitAuthActivity();
//                                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//                                        }
//                                    });
//                                }
////                                finish();
//                            }
//                        });
                    } else if (code.equals("000003")){
                        Log.e(TAG,"响应异常");
                        DebugActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, data, Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        DebugActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                OneKeyLoginHelper.getInstance().quitAuthActivity();
                                Toast.makeText(context, data, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void customView(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.custom_login_new, null);
        RelativeLayout.LayoutParams layoutParamsOther = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsOther.setMargins(0, 0, 0, Utils.dip2px(context, 150));
        layoutParamsOther.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        customView.setLayoutParams(layoutParamsOther);
        customView.findViewById(R.id.login_wechat_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        customView.findViewById(R.id.login_weibo_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        customView.findViewById(R.id.login_sms_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        AuthRegisterUIConfig authRegisterUIConfig =new AuthRegisterUIConfig.Builder()
                .setRootViewId(AuthRegisterUIConfig.RootViewId.ROOT_VIEW_ID_BODY)
                .setView(customView).setCustomInterface(new CustomInterface() {
                    @Override
                    public void onClick(Context context) {
                        Log.e(TAG,"click authRegisterUIConfig");
                    }
                }).build();
        OneKeyLoginHelper.getInstance().addCustomViewConfig("title_btn",authRegisterUIConfig);
    }

    Handler mHandler = new MyHandler(this);
    private static class MyHandler extends Handler {
        private WeakReference<DebugActivity> referenceActivity;
        private MyHandler(DebugActivity activity){
            referenceActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DebugActivity debugActivity = referenceActivity.get();
            if (debugActivity != null && !debugActivity.isFinishing()) {
                switch (msg.what) {
                    case DEBUG:
                        referenceActivity.get().check_message_init.setText((String)msg.obj);
                        break;
                    default:
                        break;
                }
            }
        }

    }

    private void getNetAndOprate() {

            Log.e(TAG, "getNetAndOprate");
            JSONObject jsonObject = OneKeyLoginHelper.getInstance().getNetworkType();
            int operator, net;

            try {
                resultString = jsonObject.toString();
                Log.e(TAG, "btn_quick_login_network,resultString:" + resultString);

                operator = Integer.parseInt(jsonObject.getString("operatorType"));
                net = Integer.parseInt(jsonObject.getString("networkType"));

                jsonObject.put("operatorType", operatorArray[operator]);
                jsonObject.put("networkType", networkArray[net]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultString = jsonObject.toString();
            Log.e(TAG, "btn_quick_login_network,new mResultString:" + resultString);
            resultDialog.setResult(StringFormat.logcatFormat(resultString));

    }
    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new java.util.Date());
    }

    private void cleanData() {
        check_message_init.setText("");
        check_message.setText("");
        message1 = "";
        message2 = "";
        message3 = "";
        message4 = "";

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != loading_view) {
            loading_view.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != loading_view) {
            loading_view.setVisibility(View.GONE);
        }
    }
}
