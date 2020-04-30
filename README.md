

# android快速集成基础框架 - Livery```1.1.11```

[![Apache-2.0](http://img.shields.io/badge/license-Apache2.0-brightgreen.svg?style=flat)](https://github.com/qydq/alidd-sample/blob/master/LICENSE)
[![Download](https://api.bintray.com/packages/qydq/maven/livery/images/download.svg)](https://bintray.com/qydq/maven/livery/_latestVersion)
[![JCenter](https://img.shields.io/badge/%20JCenter%20-1.1.11-5bc0de.svg)](https://bintray.com/qydq/maven/livery/_latestVersion)
![@sunst](https://avatars3.githubusercontent.com/u/20716264?s=460&u=ec068ee954f943483fbf1516803dcd5b77520ad3&v=4)

[![MinSdk](https://img.shields.io/badge/%20MinSdk%20-%2021%2B%20-f0ad4e.svg)](https://android-arsenal.com/api?level=21)
[![Release Version](https://img.shields.io/badge/release-1.1.11-red.svg)](https://github.com/qydq/small-video-record/releases)
[![](https://img.shields.io/badge/Author-sunst-blue.svg)](https://www.zhihu.com/people/qydq)

[***中文API帮助文档下载.***](https://pan.baidu.com/s/1jA80j-mxCRDoTpHHiDruWw) 一款针对Android平台下快速集成**便捷开发**框架**livery**，```an情景```系列```livery框架```部分基于原项目[an框架](https://github.com/qydq/an-aw-base)（基础的an-base）仓库优化而来，其目1是为小团子fang升级一款音乐聊天软件```[安妮暂定]3.0版本```，现在开放出来，希望用得着的朋友点个star.
>20190609再次确定命名image*Internet.
>livery一直维护，周1-5工作，有bug提[issues]([https://github.com/qydq/livery-samples/issues](https://github.com/qydq/livery-sample/issues))（或在知乎上给我留言，**问题描述清楚**就行]，一般修复好周7当晚更新.

专注于物联网领域，世界的通信标准从今开始改变，手机也可以是路由器，成功于视频直播，标准并不一定是Http/s，也可以是Bluetooth.

[**我的唯一知乎地址.**](https://www.zhihu.com/people/qydq/columns)      &#8194;&#8194;&#8194;&#8194;&#8194;&#8194;&#8194;（感谢关注🙏）
## 情景能力# Ability
```livery```一路走来经历了很多版本，现在是一个非常成熟的稳定版本，它包含一些非常实用的能力和一些技巧， 用简洁友好的方式，助力便捷开发；以下列举当前支持的**部分**功能<br/>
**⚠️注意**
>`1.1.10`之前的版本差异较大，建议使用最新版本   .    [(点击这里查看老版本日志记录)](https://github.com/qydq/old/tree/release-1.1.9)

### 1：主要能力
- [x] 符合Google Material Design的基类，如：AliActivity、AliFragment、BasePresenter、BaseView==.
- [x] 两种夜间模式.
- [x] 网络1请求基于xutils3模块的封装，http实现XHttps.** （⚠️因控制大小移除xutils，替代为Retrofit+RxJava）
- [x] INA系列控件相关.
- [x] 拉```La```情景实用能力集，如MD5加密，数据校验，尺寸，图片处理，网络，模糊算法，更新软件==.
- [x] StrictMode API 禁权限便捷申请.
### 2：最新版本能力
- [x] 网络2请求InternetClient基于Retrofit封装；RxJava，RxAndroid实现InternetAsyncManager.
- [x] GIF图片更友好便捷使用，提供GifImageView可以更快的加载Gif，效率可对比之前INAPowerImageView.
- [x] Glide加载图片会出现抖动的修复，请使用LaImageLoader.
- [x] 拍照相册选择能力，集成[PictureSelector](https://github.com/LuckSiege/_PictureSelector_)；整合原TakePhoto模块，视频预览图MediaHelper.
- [ ] 正在开发ing...智能语音唤醒监听能力（世界上最美的就是声音Voice ）.
## 集成方式# Binaries
第1步骤：Usage Include this library in your project using gradle (thanks to [JitPack.io](https://github.com/jitpack-io)).  <br/>
在你项目（一般app module下）的build.gradle中添加（致谢JitPack和Jcenter）.
 ```Groovy
dependencies {
   implementation'com.sunsta.livery:livery:1.1.11'
}
```
第2步骤（可选）：如果使用`网络2请求`，在你的`XxxApplication`中继承`AnApplication`，在`然后onCreate()`方法中添加初始化网络的操作，如下参考.
```java
public class AliddApplication extends AnApplication {
  @Override
  public void onCreate() {
      super.onCreate();
      InternetClient.getInstance().initialze("BASE_URL");
}
```
说明：`BASE_URL`是符合Retrofit的网络请求地址，如：`https://github.com/qydq/`，需要以`/`结尾，最后把`XxxApplication`添加到`AndroidManifest.xml`中.
## 模块介绍# Details Module
```livery-samples.apk```为提供的安装包，包含4个tab，```an情景```系列```livery```框架的使用主要是```material-ux```和```scene mode```，如

>```网络请求```类似该功能放置在```scene mode```页  <br/>
>```NATabLayout```控件类符合material design在```material-ux```中，具体如图：（待补）

其它两个tab页是个人当前测试的一些逻辑性，功能性代码，如视频编解码，获取网络一些数据列表，这部分代码仅供参考，关于```livery情景```更多的api可以查看帮助文档.
![](https://github.com/qydq/alidd-samples/blob/master/screen/api_doc.png?raw=true)

下面是部份```Details Module```的介绍，更多内容可以从本人知乎an情景专栏中获取.
### an情景系列（material-ux）
1.Gif图片加载方法
```XML
<com.ali.view.dd.gif.GifImageView
    android:id="@+id/gif_iamge_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:src="@mipmap/gif"/>
————————————————
```
循环播放
GifImageView默认播放一次就停止了，我们可以通过GifImageView获取GifDrawable，然后再通过GifDrawable设置循环播放的次数，或者设置无限循环播放
```JAVA
GifImageView gifImageView = findViewById(R.id.gif_iamge_view);
GifDrawable gifDrawable = (GifDrawable) gifImageView.getDrawable();
gifDrawable.setLoopCount(5); //设置具体播放次数
gifDrawable.setLoopCount(0); //设置无限循环播放
————————————————
```
### an情景系列（scene-mode）
#### 效果图（待上传
## 混淆配置# proguard-rules
混淆规则一定要看：[**Android App代码混淆解决方案click**](https://zhuanlan.zhihu.com/p/34559807)
```BASH
#---------------------------4.(反射实体)个人指令区-qy晴雨（请关注知乎Bgwan）---------------------
# livery 1.0.12
-keep class com.ali.view.activity.**{*;}
-keep class androidx.support.widget.helper.**{*;}
-keep class com.ali.**{*;}
-keep class com.ali.view.**{*;}
-keep class com.ali.view.widget.**{*;}
-keep class com.ali.view.activity.**{*;}
-dontwarn com.ali.**

-keep class com.ali.module.lib.** { *; }
-dontwarn com.ali.module.ucrop**
-keep class com.ali.module.ucrop** { *; }
-keep interface com.ali.module.ucrop** { *; }

#内部内
-keepclasseswithmembers class com.ali.AnConstants$URL {
 <methods>;}
-keepclasseswithmembers class com.ali.AnConstants$KEY {
 <methods>;}

-keep public class com.ali.view.dd.gif.GifIOException{<init>(int);}
-keep class com.ali.view.dd.gif.GifInfoHandle{<init>(long,int,int,int);}
```
## 常见错误# Easy Mistake

./gradlew processDebugManifest --stacktrace

### 非常重要1：由于```livery```基于```an-aw-base```，在版本```1.1.x```以后用```androidx```替换了所有的```support-v4，v7```等；如果你的项目已经包含了```v4，v7，```删除跟```v4，v7```的依赖，如不能删除，如下参考配置.

项目的根目录的build.gradle中添加，这样就可以忽略support相关的包引用问题
```Groovy
 configurations {
     all*.exclude group :'com.android.support',module:'support-compat'
     all*.exclude group :'com.android.support',module:'support-v4'
     all*.exclude group :'com.android.support',module:'support-annotations'
     all*.exclude group :'com.android.support',module:'support-fragment'
     all*.exclude group :'com.android.support',module:'support-core-utils'
     all*.exclude group :'com.android.support',module:'support-core-ui'
 }
————————————————
```
或参考：
```Groovy
implementation('me.imid.swipebacklayout.lib:library:1.1.0') {
    exclude group: 'com.android.support'
}
————————————————
```

### 错误2: More than one file was found with OS independent path 'META-INF/rxjava.properties'
这是rxJava冲突，在app目录的build.gradle下添加
```Groovy
 packagingOptions {
   exclude 'META-INF/rxjava.properties'
 }
 ————————————————
```
### 错误3： Manifest merger failed : Attribute meta-data#android.support.FILE_PROVIDER_PATHS@
这是File_provider冲突，修改AndroidManifest.xml文件.[参考](https://blog.csdn.net/lbqcsdn/article/details/84795775)
```XML
<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="${applicationId}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/gdt_file_path" />
</provider>

<provider
    android:name=".utils.BuglyFileProvider"
    android:authorities="${applicationId}.fileProvider"
    android:exported="false"
    android:grantUriPermissions="true"
    tools:replace="name,authorities,exported,grantUriPermissions">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/bugly_file_paths"
                tools:replace="name,resource" />
</provider>
————————————————
```
## 版本日志# Version LOG

[**an-aw-base0.x.x版本log.**](https://github.com/qydq/an-aw-base/releases)

**⚠️注意**
>版本更新日志严格跟随代码提交内容，方便日后查阅相关记录，为控制字数，这里只记录1.0.x版本日志（[1.0.20之前版本想了解的可以点击这里查看](https://github.com/qydq/samples)）和1.1.8之后的重要版本记录

### 1.0.x版本总述
livery的初始版本，从an-aw-base重构而来，livery框架1.0.x（及以下的版本）支持的android最低版本为，minSdkVersion=19，总共发布了20个实际版本，具体依赖方法如下：
```Groovy
implementation 'com.sunsta:livery:1.0.x'
```

1.0.x包含包含了support系列的库
```Groovy
appcompat          : 'com.android.support:appcompat-v7:27.0.2',
constraint         : 'com.android.support.constraint:constraint-layout:1.0.2',
design             : 'com.android.support:design:27.0.2',
recyclerview       : 'com.android.support:recyclerview-v7:27.0.2',

retrofit           : 'com.squareup.retrofit2:retrofit:2.3.0',
gson               : 'com.squareup.retrofit2:converter-gson:2.1.0',
rxjava2            : 'com.squareup.retrofit2:adapter-rxjava2:2.3.0',
okhttp             : 'com.squareup.okhttp3:okhttp:3.8.1',
glide              : 'com.github.bumptech.glide:glide:4.3.1',

xutils             : 'org.xutils:xutils:3.5.0',
glidecompiler      : 'com.github.bumptech.glide:compiler:4.3.1',
multipleimageselect: 'com.darsh.multipleimageselect:multipleimageselect:1.0.4',
crop               : 'com.soundcloud.android.crop:lib_crop:1.0.0',
advancedluban      : 'me.shaohui.advancedluban:library:1.3.2',
nineoldandroids    : 'com.nineoldandroids:library:2.4.0',
```
### 1.1.8*
1.1.x版本主要是添加androidx，移除升级修复减小体积，相关第三方库bug，完善稳定网络2请求，修改最低支持版本为21，也就是说livery在以后不再支持android5.0以下的手机.

所有的控件使用均不支持support.v4,v7这样的包(包含如RecyclerView等等)，代替的是androidx最新的库

优化和移除takePhoto模块依赖的编译库，因为一些第三方库长久不更新，会导致一些问题，严重的可能出现崩溃，（如multipleimageselect 该库的作者未更新，导致更新glide后，在android8.0 ,9.0上存在兼容性问题）.

### 1.1.10*
引入androidx.camera的测试版本，（此版本存在兼容性问题，如需引用androidx.camera特性，需要依赖1.1.1版本:并同时修改minSdkVersion=21）优化并且统一框架中的资源命名规范问题，涉及到字符串，颜色资源，属性定义，布局文件，类文件标准命名等等.
```
api "androidx.concurrent:concurrent-futures:1.0.0-rc01"
api "androidx.camera:camera-lifecycle:1.0.0-alpha01"
api "androidx.camera:camera-core:1.0.0-alpha08"
api "androidx.camera:camera-camera2:1.0.0-alpha05"
```
## 其它说明
### 1.关于自定义apk名说明
```Groovy
#---------------------------3.(自定义apk)个人其它说明区-sunst（请关注知乎Bgwan）---------------------
// 便利所有的Variants，all是迭代遍历操作符，相当于for
applicationVariants.all { variant ->// 遍历得出所有的variant
    variant.outputs.all {// 遍历所有的输出类型，一般是debug和replease
        // 定义apk的名字，拼接variant的版本号
        def apkName = "app_${variant.versionName}"
        // 判断是否为空
        if (!variant.flavorName.isEmpty()) {
            apkName += "_${variant.flavorName}"
        }
        // 赋值属性
        String time = new Date().format("_YYYYMMddHH")
        if (variant.buildType.name.equals("release")){
            outputFileName = apkName + "_Replease" + time + ".apk"
        }else {
            outputFileName = apkName + "_Debug" +time + ".apk"
        }

    }
}
```
### 2.关于其它

## 致谢
非常感谢以下前辈的开源精神，当代互联网的发展离不开前辈们的分享，也非常感谢github优秀的代码管理平台.

https://github.com/darsh2/MultipleImageSelect  <br/>
compile 'com.github.darsh2:MultipleImageSelect:v0.0.4'

https://github.com/LuckSiege/PictureSelector  <br/>
implementation 'com.github.LuckSiege.PictureSelector:picture_library:v2.3.7'

https://github.com/crazycodeboy/TakePhoto  <br/>
compile 'com.jph.takephoto:takephoto_library:4.1.0'

https://github.com/ReactiveX/RxAndroid  <br/>
implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'

https://github.com/ReactiveX/RxJava  <br/>
implementation 'io.reactivex.rxjava2:rxjava:2.2.15'

retrofit : 'com.squareup.retrofit2:retrofit:2.6.2',//20191128  <br/>
gson : 'com.squareup.retrofit2:converter-gson:2.6.2',//20191128  <br/>
rxjava2 : 'com.squareup.retrofit2:adapter-rxjava2:2.6.2',//20191128

https://github.com/bumptech/glide<br/>
implementation 'com.github.bumptech.glide:glide:4.10.0'<br/>
annotationProcessor 'com.github.bumptech.glide:compiler:4.10.0'

## LICENSE
```java
/*
 * Copyright (C) 2016 The Android Developer sunst
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```