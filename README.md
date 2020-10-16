# 容联云一键登录Android_Demo
**此项目为容联云一键登录功能的体验Demo，其中包含 号码一键登录 和 号码验证功能。**
- [工程目录](./onekeylogindemo)为容联云一键登录Demo
- [工程目录](./sdk)为sdk及开发说明文档存放处

### ⼀. 概述

**容联云一键登录SDK 为移动应用提供完善的三网（移动、联通、电信）一键登录功能开发框架，屏蔽其内部复杂细节，对外提供较为简洁的 API 接口，方便第三方应用快速集成一键登录功能。**


<font color=#ff00 size=3>**注意事项:**</font>

- <font color=#80002f size=2>网络取号时候请务必开启手机流量</font>

  <font color=#80002f size=2>1.电信只支持4G网络取号</font>

  <font color=#80002f size=2>2.移动, 联通支持4G, 3G, 2G网络取号但在非4G网络情况下容易取号失败</font>

- <font color=#80002f size=2>针对双卡双待手机只取当前流量卡号</font>

   

### 二. Demo快速体验

- **克隆/下载本项目到本地，将Demo导入开发工具编译；**
- **配置修改build.gradle：**

      1.配置签名
```
    defaultConfig {
        //将build里面的applicationId换成对应的测试包名
        applicationId "xxxxxxx`"
        minSdkVersion 14
        targetSdkVersion 29
        versionCode 3
        versionName "1.0.6"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
```
    2.配置签名
```
    //配置相应的签名(密钥与容联注册时提供的请保持一致)
    signingConfigs {
        release {
            storeFile file('C:\\xxx.keystore')
            storePassword '******'
            keyAlias = 'xxx'
            keyPassword '******'
        }
        debug {
            storeFile file('C:\\xxx.keystore')
            storePassword '******'
            keyAlias = 'xxx'
            keyPassword '******'
        }
    }
 ```
     3.配置appId
 ```
    //配置相应的appId(appId与容联注册时提供的请保持一致)
    buildTypes {
        release {
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.release
            buildConfigField 'String', 'APP_ID', '"******************************"'
        }
    }
```

<font color=#ff00 size=3>**注：**</font>

- <font color=#ff00 size=3>建议使用Android Studio2.3及以上版本。</font>
- <font color=#ff00 size=3>确保已在容联服务端开通并申请了一键登录服务，获取到了相应的appID。</font>
- <font color=#ff00 size=3>替换 **build.gradle(app)** 文件中 **APP_ID** 的值为申请的appID，并确保此appID和应用包名相对应。</font>

### 接入及查看SDK配置、接口说明、授权界面设计、常见问题及返回码等

### 详见 [说明文档](./sdk/onekeyLogin-Android1.0.6.pdf)


