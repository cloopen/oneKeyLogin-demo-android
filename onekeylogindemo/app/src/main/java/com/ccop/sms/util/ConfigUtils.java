package com.ccop.sms.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.ccop.sms.R;
import com.ccop.sms.activity.OtherLoginActivity;
import com.ccop.sms.activity.SMSVerificationActivity;
import com.cloopen.okl.sdk.config.AuthUIConfig;
import com.cloopen.okl.sdk.listener.OtherLoginInterface;

/**
 * @ProjectName: Okl Demo
 * @Package: com.ccop.sms.util
 * @ClassName: ConfigUtils
 * @Description: java类作用描述
 * @Author: LH
 * @CreateDate: 2020/5/17 14:24
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/17 14:24
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @UpdateDate: 2020/9/11
 * @UpdateRemark: 统一授权页配置
 * @Version: 1.0.5
 *
 */
public class ConfigUtils {

    /**竖屏全屏展示
     * @param context
     * @return
     */
    public static AuthUIConfig getAuthVerticalUIConfig(final Context context){
        AuthUIConfig.Builder configBuilder=  new AuthUIConfig.Builder()
                //设置授权页是否使用弹框，不使用可不调用
                .setDialogTheme(false, 0, 0)
                //设置状态栏，导航栏颜色，状态栏内容颜色
                .setStatusBar(0xff000000, 0xffffffff,false)
                //设置标题栏样式
                .setAuthNavLayout(0xFFFFFFFF, 49) //ok
                //设置标题栏文字，字体大小，颜色   后面是
                .setAuthNavTextView("一键登录", 0xff000000, 17, false, "服务条款", 0xFF000000, 15)
                //设置标题栏返回按钮样式
                .setAuthNavReturnImgView("okl_left_return", 24, 24, 12)
                //设置协议条款标题栏
                .setWebReturnImgView("okl_left_return", 24, 24, 12)
                //设置logo样式
                .setLogoImgView("demo_logo", 230, 50,  80)
                //设置脱敏手机号码样式
                .setNumberView(0xFF3D424C, 24, 130)
                //设置免密服务提供文字样式、位置
                .setSloganView(0xFFA8A8A8, 10, 169)
                //设置登录按钮样式、位置
                .setLogBtnLayout("okl_btn_normal", 238, 36, 220)
                //设置登录按钮文字、字体大小、颜色
                .setLogBtnTextView("一键登录", 0xFFFFFFFF, 15)
                //设置登录按钮上的loading动画样式
                .setLogBtnLoadingView("unicom_load_dot_white", 20, 20, 12)
                //设置切换账号样式
                .setSwitchView("其他登陆方式", 0xFF74cb26, 17, false, 450, new OtherLoginInterface() {
                    @Override
                    public void onClick(View v) {
                        //设置点击事件
                        context.startActivity(new Intent(context, SMSVerificationActivity.class));
                    }
                })
                //设置隐私协议选择框样式
                .setCheckedSelectImgPath("ct_check_box_select",true)
                //设置自定义的服务条款
                .setPrivacyClauseText( "《应用自定义服务条款一》", "https://aim-mobileauth.yuntongxun.com/okl/okl.html")
                //设置隐私条款位置
                .setPrivacyLayout(30)
                //设置隐私条款文字颜色、字体大小
                .setPrivacyClauseView(0xFFA8A8A8, 0xFF74cb26, 10)
                //设置隐私协议未勾选时点击一键登录按钮提示
                .setPrivacyUnCheckedToastText("请同意服务条款")
                //设置第三方登录 不显示可以不设置默认不显示、点击事件QQ、微信、微博
                .setIsShowOtherLogin(true,new OtherOnClick(context,"QQ登录"),new OtherOnClick(context,"微信登录"),new OtherOnClick(context,"微博登录"))
                //新加配置登录Activity进入动画和退出动画 （不选则系统默认）
//                .setActivityTransition(R.anim.in_activity,R.anim.out_activity)
                ;
        return configBuilder.build();
    }

    /**
     * 自定义点击事件
     */
    static class OtherOnClick implements OtherLoginInterface {
        private Context context;
        private String otherStr;
        public OtherOnClick(Context context, String str) {
            this.context = context;
            this.otherStr = str;
        }

        @Override
        public void onClick(View v) {
                context.startActivity(new Intent(context, OtherLoginActivity.class).putExtra("other_name",otherStr));
            }
        }

    /**
     * 竖屏弹框展示
     * @param context
     * @return
     */
    public static AuthUIConfig getAuthDialogUIConfig(final Context context){
        AuthUIConfig.Builder configBuilder=  new AuthUIConfig.Builder()
                .setDialogTheme(true, 300, 350)
                .setStatusBar(0xffffff, 0xfff000, false)
                .setAuthNavLayout(0xFFFFFF, 49)
                .setAuthNavTextView("免密登录", 0xFF000000, 15, false, "服务条款", 0xFF000000, 17)
                .setAuthNavReturnImgView("ct_account_mini_auth_goback", 24, 24,12)
                .setWebReturnImgView("okl_left_return", 24, 24, 12)
                .setLogoImgView("demo_logo", 130, 25, 60)
                .setNumberView(0xFF3D424C, 24, 80)
                .setSwitchView("其他登陆方式", 0xFF74cb26, 14, false, 220, new OtherLoginInterface() {
                    @Override
                    public void onClick(View v) {
                        //设置点击事件
                        context.startActivity(new Intent(context, SMSVerificationActivity.class));
                    }
                })
                .setLogBtnLayout("unicom_one_login_btn_normal", 260, 42, 170)
                .setLogBtnTextView("一键登录", 0xFFFFFFFF, 15)
                .setLogBtnLoadingView("unicom_load_dot_white", 20, 20, 12)
                .setSloganView(0xFFA8A8A8, 10, 120)
                .setCheckedSelectImgPath("ct_check_box_select",true)
                .setPrivacyClauseText("《应用自定义服务条款一》", "https://aim-mobileauth.yuntongxun.com/okl/okl.html")
                .setPrivacyLayout(10)
                .setPrivacyClauseView(0xFFA8A8A8, 0xFF74cb26, 10)
                .setPrivacyUnCheckedToastText("请同意服务条款");
//                .setActivityTransition(R.anim.in_activity,R.anim.out_activity);
        return configBuilder.build();
    }
}
