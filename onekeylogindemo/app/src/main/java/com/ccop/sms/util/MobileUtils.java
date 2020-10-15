package com.ccop.sms.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * @ProjectName: Okl Demo
 * @Package: com.ccop.sms.util
 * @ClassName: MobileUtils
 * @Description: java类作用描述
 * @Author: LH
 * @CreateDate: 2020/4/28 14:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/28 14:16
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MobileUtils {
    private static final String TAG = MobileUtils.class.getSimpleName();
    //获取电话的服务商或者状态
    public static void getSystemService(Context context){
        TelephonyManager localTelephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if(localTelephonyManager!=null) {
//            @SuppressLint("MissingPermission")
//            String deviceId = localTelephonyManager.getDeviceId();
//            Log.e(TAG,"deviceId:"+deviceId+",simState:"+simState);
            int simState=localTelephonyManager.getSimState();
            Log.e(TAG,"simState:"+simState);
        }

    }
    /**
    public static boolean getSystemService(Context context){
        TelephonyManager localTelephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        int i = null != localTelephonyManager ? localTelephonyManager.getSimState() : 0;
        
        boolean bool = true;
        switch (i){
            case 1:
                bool = false;
                break;
            case 0:
                bool = false;
        }
        return bool;
    }
     **/
}
