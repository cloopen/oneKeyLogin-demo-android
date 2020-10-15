package com.ccop.sms.listener;

/**
 * @ProjectName: Okl Demo
 * @Package: com.ccop.sms.listener
 * @ClassName: OneKeyLoginListener
 * @Description: java类作用描述
 * @Author: LH
 * @CreateDate: 2020/3/18 20:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/18 20:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface OneKeyLoginListener {
    public void oneKeyLoginResult(String code,String msg);
}
