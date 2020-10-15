package com.ccop.sms.validate;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.ccop.sms.BuildConfig;
import com.ccop.sms.util.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by haoxin on 2017/5/31.
 */

public class Request {
    private static final String TAG = "Request";
    private Context mContext;
    private static Request mInstance = null;

    protected Request() {
    }

    protected Request(Context context) {
        mContext = context;
    }

    /**
     * 使用单例模式
     */
    public static Request getInstance(Context context) {
        if (mInstance == null) {
            synchronized (Request.class) {
                if (mInstance == null) {
                    mInstance = new Request(context);
                }
            }
        }
        return mInstance;
    }

    public static String generateNonce32() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
    public static String getCurrentTime() {
        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sf.format(data);
    }

    public static String exChange(String str){
        StringBuffer sb = new StringBuffer();
        if(str!=null){
            for(int i=0;i<str.length();i++){
                char c = str.charAt(i);
                if(Character.isLowerCase(c)){
                    sb.append(Character.toUpperCase(c));
                }else{
                    sb.append((c));
                }
            }
        }

        return sb.toString();
    }

    private String SHA(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                    {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    private static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byte2hex(bytes);
            System.out.println(hash);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    public static String byte2hex(byte[] b)
    {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }


    public  void doCommonRequest(final String url, ValidateParam params, final RequestCallback callback) {
        Log.e(TAG, "request https url : " + url + ">>>>>>> PARAMS : " + params.toJson().toString());
        new HttpUtils().requestHttp(url, params.toJson().toString(),  new HttpUtils.Response() {
            @Override
            public void onSuccess(String result) {

                Log.e(TAG, "request success , url : " + url + ">>>>result : " + result);
                try {
                    JSONObject json = new JSONObject(result);



                    callback.onRequestComplete(json.optString("statusCode"), json.optString("statusMsg"), json);
                } catch (Exception e) {
                    e.printStackTrace();
                    onError("102223", "数据解析异常");
                }

            }

            @Override
            public void onError(String errorCode, String msg) {

                JSONObject object = new JSONObject();
                try {
                    object.put("resultCode", errorCode);
                    object.put("desc", msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "request failed , url : " + url
                        + ">>>>>errorMsg : " + object.toString());
                if (callback != null) {
                    callback.onRequestComplete(errorCode, msg, object);
                }
            }
        });
    }
    public  void doPhoneAuthCommonRequest(final String url, PhoneAuthParam params, final RequestCallback callback) {
        Log.e(TAG, "request https url : " + url + ">>>>>>> PARAMS : " + params.toJson().toString());
        new HttpUtils().requestHttp(url, params.toJson().toString(),  new HttpUtils.Response() {
            @Override
            public void onSuccess(String result) {

                Log.e(TAG, "request success , url : " + url + ">>>>result : " + result);
                try {
                    JSONObject json = new JSONObject(result);



                    callback.onRequestComplete(json.optString("statusCode"), json.optString("statusMsg"), json);
                } catch (Exception e) {
                    e.printStackTrace();
                    onError("102223", "数据解析异常");
                }

            }

            @Override
            public void onError(String errorCode, String msg) {

                JSONObject object = new JSONObject();
                try {
                    object.put("resultCode", errorCode);
                    object.put("desc", msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "request failed , url : " + url
                        + ">>>>>errorMsg : " + object.toString());
                if (callback != null) {
                    callback.onRequestComplete(errorCode, msg, object);
                }
            }
        });
    }
    //一键登录
    public void tokenValidate(Bundle values, RequestCallback callback) {

        ValidateParam param = new ValidateParam();
        param.setAppId(values.getString("appId"));
        param.setToken(values.getString("token"));

        String url = BuildConfig.SERVER_URL+Constant.HTTP_TOKEN_URL;
        doCommonRequest(url, param, callback);
    }
    //本机号码认证
    public void phoneValidate(Bundle values, RequestCallback callback) {

        PhoneAuthParam param = new PhoneAuthParam();
        param.setAppId(values.getString("appId"));
        param.setToken(values.getString("token"));
        param.setMobile(values.getString("mobile"));
        String url = BuildConfig.SERVER_URL+Constant.HTTP_PHONE_URL;
        doPhoneAuthCommonRequest(url, param, callback);
    }

}
