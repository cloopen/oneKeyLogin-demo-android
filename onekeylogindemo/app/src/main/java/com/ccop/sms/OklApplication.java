package com.ccop.sms;

import android.app.Application;
import android.util.Log;

import com.cloopen.okl.sdk.OneKeyLoginHelper;
import com.cloopen.okl.sdk.listener.InitListener;

/**
 * @ProjectName: Okl Demo
 * @Package: com.ccop.sms
 * @ClassName: OklApplication
 * @Description: java类作用描述
 * @Author: LH
 * @CreateDate: 2020/3/16 11:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/16 11:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class OklApplication extends Application {
    String TAG = "OklApplication";
    private static OklApplication application;

    public static OklApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Log.e(TAG,"Init okl sdk");
        // SDK配置debug开关
        OneKeyLoginHelper.getInstance().setDebug(true);
        //SDK初始化（建议放在Application的onCreate方法中执行）
        OneKeyLoginHelper.getInstance().init(this, BuildConfig.APP_ID,new InitListener() {
            @Override
            public void initStatus(String code , String data) {
                Log.e(TAG,"初始化结果,code:"+code +",data:"+data);
            }
        });
    }

}
