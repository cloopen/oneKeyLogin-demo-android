package com.ccop.sms.validate;

import com.ccop.sms.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ProjectName: Okl Demo
 * @Package: com.ccop.sms.validate
 * @ClassName: PhoneAuthParam
 * @Description: java类作用描述
 * @Author: LH
 * @CreateDate: 2020/3/19 19:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/19 19:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PhoneAuthParam {
    private String appId;
    private String token;
    private String mobile;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public JSONObject toJson() {

        JSONObject mainJson = new JSONObject();
        try {
            mainJson.put("appId",appId);
            mainJson.put("token",token);
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
