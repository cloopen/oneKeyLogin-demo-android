package com.ccop.sms.listener;

/**
 * @ProjectName: Okl Demo
 * @Package: com.ccop.sms.listener
 * @ClassName: PhoneAuthListener
 * @Description: java类作用描述
 * @Author: LH
 * @CreateDate: 2020/3/22 22:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/22 22:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface PhoneAuthListener {
    public void phoneAuthResult(String code,String msg);
}
