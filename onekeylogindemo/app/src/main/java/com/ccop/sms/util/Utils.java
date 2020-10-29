package com.ccop.sms.util;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.ccop.sms.listener.OneKeyLoginListener;
import com.ccop.sms.validate.Request;
import com.ccop.sms.validate.RequestCallback;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ProjectName: Okl Demo
 * @Package: com.ccop.sms.util
 * @ClassName: Utils
 * @Description: java类作用描述
 * @Author: LH
 * @CreateDate: 2020/3/19 17:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/19 17:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Utils {
    public static boolean checkPhoneNum(String phone) {
        if (!phone.matches("^[1][0-9]{10}$")) {
            return false;
        } else {
            //合规
            return true;
        }
    }
    public static boolean validataPhonNumb(String phone) {
        String telRegex = "[1][3456789]\\d{9}";
        Pattern pattern = Pattern.compile(telRegex);
        Matcher matcher = pattern.matcher(phone);
        boolean b = matcher.matches();
        if (!b) {
            return false;
        }
        return true;
    }

    public static void tokenValidate(Context context, final String appId, final String token, final OneKeyLoginListener listener){
        Bundle values = new Bundle();
        values.putString("appId",appId);
        values.putString("token",token);
        Request.getInstance(context).tokenValidate(values, new RequestCallback() {

            @Override
            public void onRequestComplete(String resultCode, String resultDes, JSONObject jsonObj) {
                Log.i("Token校验结果：", jsonObj.toString());
                listener.oneKeyLoginResult(resultCode,jsonObj.toString());
            }
        });

    }
    public static String getUUID() {
        String str = UUID.randomUUID().toString();
        str = str.replace("-", "");
        return str;
    }
    public static String generateSig(JSONObject jsonObject) {
        TreeMap localTreeMap = new TreeMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String var5 = "";

        while(iterator.hasNext()) {
            key = iterator.next().toString();
            localTreeMap.put(key, jsonObject.optString(key));
        }

        StringBuilder localStringBuilder = new StringBuilder();
        Iterator it = localTreeMap.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)it.next();
            localStringBuilder.append((String)entry.getKey()).append((String)entry.getValue());
        }
        String str= localStringBuilder.toString();
        Log.d("Utils","str:"+str);
        return encryptionMD5(str);
    }
    public static String encryptionMD5(String paramString) {
        byte[] byteStr;
        try {
            byteStr = paramString.getBytes("UTF-8");
        } catch (Exception ex) {
            return null;
        }
        return encryptionMD5(byteStr);

    }
    public static String encryptionMD5(byte[] byteStr) {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            byte[] byteArray = messageDigest.digest();
//            return Base64.encodeToString(byteArray,Base64.NO_WRAP);
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5StrBuff.toString();
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static boolean mOrientation(Context context) {
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            //横屏
            return false;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            return true;
        }
        return true;
    }
}
