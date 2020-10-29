package com.ccop.sms.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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

    /**
     * 保存数据 , 所有的类型都适用
     *
     * @param key
     * @param value
     */
    public static void saveParam(Context context,String key, Object value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (value instanceof String) {
            // 保存String 类型
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            // 保存integer 类型
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            // 保存 boolean 类型
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            // 保存float类型
            editor.putFloat(key, (Float) value);
        } else if (value instanceof  Long) {
            // 保存long类型
            editor.putLong(key, (Long) value);
        } else {
            if (!(value instanceof Serializable)) {
                throw new IllegalArgumentException(value.getClass().getName() + " 必须实现Serializable接口!");
            }

            // 不是基本类型则是保存对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(value);
                String productBase64 = Base64.encodeToString(
                        baos.toByteArray(), Base64.DEFAULT);
                editor.putString(key, productBase64);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        editor.commit();
    }
    /**
     * 得到保存数据的方法，所有类型都适用
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context,String key, Object defaultObject) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        }
        return getObject(context,key);
    }


    public static Object getObject(Context context,String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);

        String wordBase64 = sharedPreferences.getString(key, "");
        byte[] base64 = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            ObjectInputStream bis = new ObjectInputStream(bais);
            Object object =  bis.readObject();
            return object;
        } catch (Exception e) {

        }
        return null;
    }
}
