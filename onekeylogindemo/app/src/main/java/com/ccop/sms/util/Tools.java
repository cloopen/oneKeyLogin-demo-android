package com.ccop.sms.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ProjectName: Okl Demo
 * @Package: com.ccop.sms.util
 * @ClassName: Tools
 * @Description: java类作用描述
 * @Author: LH
 * @CreateDate: 2020/3/11 16:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/11 16:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Tools {
    protected static String TAG = "Tools";
    public static String getSign(Context context){

        String packageName = context.getPackageName();
        Log.e(TAG, "pName:"+packageName);
        //String packageName = "com.ccop.sms";
        Signature[] signs = getRawSignature(context, packageName);
        if ((signs == null) || (signs.length == 0))
        {
            Log.e(TAG, "signs is null");
            return null;
        }
        Signature sign = signs[0];
        String signStr = encryptionMD5(sign.toByteArray());
        return signStr;
    }
    private static Signature[] getRawSignature(Context context, String packageName){
        if ((packageName == null) || (packageName.length() == 0))
        {
            //errout("getSignature, packageName is null");
            Log.e(TAG, "getSignature, packageName is null");
            return null;
        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "NameNotFoundException");
            e.printStackTrace();
        }
        return null;
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
}
