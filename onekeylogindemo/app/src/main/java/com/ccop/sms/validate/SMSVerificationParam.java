package com.ccop.sms.validate;

import com.ccop.sms.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author : Hv
 * e-mail : huowei@yuntongxun.com
 * date   : 2020/9/23 14:03
 * desc   :
 * version: 1.0
 */
public class SMSVerificationParam {
    private String appId;
    private String mobile;
    private String captcha;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }


    public JSONObject toJson() {

        JSONObject mainJson = new JSONObject();
        try {
            String captcha = (int)((Math.random()*9+1)*100000)+"";
            setCaptcha(captcha);
            mainJson.put("appId",appId);
            mainJson.put("captcha",captcha);
            mainJson.put("mobile",mobile);
            mainJson.put("random", Utils.getUUID());
            String sign= Utils.generateSig(mainJson);
            mainJson.put("sign",sign);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return mainJson;
    }
}
