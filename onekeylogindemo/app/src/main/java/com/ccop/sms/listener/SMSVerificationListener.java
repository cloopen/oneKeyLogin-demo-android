package com.ccop.sms.listener;

/**
 * author : Hv
 * e-mail : huowei@yuntongxun.com
 * date   : 2020/9/23 15:18
 * desc   :
 * version: 1.0
 */
public interface SMSVerificationListener {
     void oneSMSVerificationResult(String code, String msg);
}
