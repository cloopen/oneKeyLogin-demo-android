package com.ccop.sms.validate;

import com.ccop.sms.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ProjectName: Okl Demo
 * @Package: com.ccop.sms.validate
 * @ClassName: ValidateParam
 * @Description: java类作用描述
 * @Author: LH
 * @CreateDate: 2020/2/18 19:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/18 19:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ValidateParam {
    private String appId;
    private String token;

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

    public JSONObject toJson() {

        JSONObject mainJson = new JSONObject();
        try {
            mainJson.put("appId",appId);
            mainJson.put("token",token);
            mainJson.put("random", Utils.getUUID());
            String sign= Utils.generateSig(mainJson);
            mainJson.put("sign",sign);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return mainJson;
    }
}
