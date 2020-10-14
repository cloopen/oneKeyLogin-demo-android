package com.ccop.sms.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @ProjectName: Okl Demo
 * @Package: com.ccop.sms.util
 * @ClassName: ShareUtils
 * @Description: java类作用描述
 * @Author: LH
 * @CreateDate: 2020/3/15 1:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/15 1:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ShareUtils {
    public static final String NAME = "config";

    //键 值
    public static void putBoolean(Context mContext, String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    //键 默认值
    public static boolean getBoolean(Context mContext, String key, boolean defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }
}
