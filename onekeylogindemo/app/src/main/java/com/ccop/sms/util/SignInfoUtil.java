package com.ccop.sms.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * author : Hv
 * e-mail : huowei@yuntongxun.com
 * date   : 2020/8/1517:56
 * desc   :
 * version: 1.0
 */
public class SignInfoUtil {

    /**
     * 获取app的签名信息
     * @param context
     * @return
     */
    public static String getSingInfo(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            byte[] signByteArray = sign.toByteArray();

            return md5(signByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5
     * @param data
     * @return
     */
    public static String md5(byte[] data){
        if(data == null || data.length == 0){
            return null;
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString().toUpperCase();
    }
}

