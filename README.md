<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**

- [alidd1.0.15](#alidd1015)
  - [情景能力# Ability](#%E6%83%85%E6%99%AF%E8%83%BD%E5%8A%9B-ability)
    - [主要能力](#%E4%B8%BB%E8%A6%81%E8%83%BD%E5%8A%9B)
    - [第二能力](#%E7%AC%AC%E4%BA%8C%E8%83%BD%E5%8A%9B)
  - [集成方式# Binaries](#%E9%9B%86%E6%88%90%E6%96%B9%E5%BC%8F-binaries)
  - [模块介绍# Details Module](#%E6%A8%A1%E5%9D%97%E4%BB%8B%E7%BB%8D-details-module)
    - [an情景系列（material-ux）](#an%E6%83%85%E6%99%AF%E7%B3%BB%E5%88%97material-ux)
    - [an情景系列（aN情景s）](#an%E6%83%85%E6%99%AF%E7%B3%BB%E5%88%97an%E6%83%85%E6%99%AFs)
      - [效果图（待上传](#%E6%95%88%E6%9E%9C%E5%9B%BE%E5%BE%85%E4%B8%8A%E4%BC%A0)
  - [混淆配置# proguard-rules](#%E6%B7%B7%E6%B7%86%E9%85%8D%E7%BD%AE-proguard-rules)
  - [常见错误# Easy Mistake](#%E5%B8%B8%E8%A7%81%E9%94%99%E8%AF%AF-easy-mistake)
    - [非常重要1：由于```alidd```基于```an-aw-base```，在版本```1.0.2```以后用```androidx```替换了所有的```support-v4，v7```等；如果你的项目已经包含了```v4，v7，```删除跟```v4，v7```的依赖，如不能删除，如下参考配置.](#%E9%9D%9E%E5%B8%B8%E9%87%8D%E8%A6%811%E7%94%B1%E4%BA%8Ealidd%E5%9F%BA%E4%BA%8Ean-aw-base%E5%9C%A8%E7%89%88%E6%9C%AC102%E4%BB%A5%E5%90%8E%E7%94%A8androidx%E6%9B%BF%E6%8D%A2%E4%BA%86%E6%89%80%E6%9C%89%E7%9A%84support-v4v7%E7%AD%89%E5%A6%82%E6%9E%9C%E4%BD%A0%E7%9A%84%E9%A1%B9%E7%9B%AE%E5%B7%B2%E7%BB%8F%E5%8C%85%E5%90%AB%E4%BA%86v4v7%E5%88%A0%E9%99%A4%E8%B7%9Fv4v7%E7%9A%84%E4%BE%9D%E8%B5%96%E5%A6%82%E4%B8%8D%E8%83%BD%E5%88%A0%E9%99%A4%E5%A6%82%E4%B8%8B%E5%8F%82%E8%80%83%E9%85%8D%E7%BD%AE)
    - [错误2: More than one file was found with OS independent path 'META-INF/rxjava.properties'](#%E9%94%99%E8%AF%AF2-more-than-one-file-was-found-with-os-independent-path-meta-infrxjavaproperties)
    - [错误3： Manifest merger failed : Attribute meta-data#android.support.FILE_PROVIDER_PATHS@](#%E9%94%99%E8%AF%AF3-manifest-merger-failed--attribute-meta-dataandroidsupportfile_provider_paths)
  - [版本日志# Version LOG](#%E7%89%88%E6%9C%AC%E6%97%A5%E5%BF%97-version-log)
    - [*1.0.1](#101)
    - [*1.0.2](#102)
    - [1.0.3](#103)
    - [1.0.4](#104)
    - [*1.0.5](#105)
    - [1.0.6](#106)
    - [*1.0.7](#107)
    - [1.0.8](#108)
    - [*1.0.9](#109)
    - [1.0.10](#1010)
    - [1.0.11](#1011)
    - [*1.0.12](#1012)
    - [*1.0.13](#1013)
    - [*1.0.14](#1014)
    - [1.0.15](#1015)
  - [其它说明](#%E5%85%B6%E5%AE%83%E8%AF%B4%E6%98%8E)
    - [1.关于自定义apk名说明](#1%E5%85%B3%E4%BA%8E%E8%87%AA%E5%AE%9A%E4%B9%89apk%E5%90%8D%E8%AF%B4%E6%98%8E)
    - [2.关于其它](#2%E5%85%B3%E4%BA%8E%E5%85%B6%E5%AE%83)
  - [致谢](#%E8%87%B4%E8%B0%A2)
  - [LICENSE](#license)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# alidd1.0.15
[![Apache-2.0](http://img.shields.io/badge/license-Apache2.0-brightgreen.svg?style=flat)](https://github.com/qydq/alidd-samples/blob/master/LICENSE)
[![Download](https://api.bintray.com/packages/qydq/maven/alidd/images/download.svg)](https://bintray.com/qydq/maven/alidd/_latestVersion)
[![Release Version](https://img.shields.io/badge/release-1.0.15-red.svg)](https://github.com/qydq/small-video-record/releases)
[![](https://img.shields.io/badge/Author-sunst-blue.svg)](https://www.zhihu.com/people/qydq)

一款针对Android平台下快速集成**便捷开发**框架**alidd**，```an情景```系列```alidd框架```部分基于原项目[an框架click](https://github.com/qydq/an-aw-base)（基础的an-base）仓库优化而来，其目1是为小团子fang升级一款音乐聊天软件```[安妮暂定]3.0版本```，现在开放出来，希望用得着的朋友点个star.
>20190609再次确定命名dd.JUST.
>alidd一直维护，周1-6工作，有bug提[issues]([https://github.com/qydq/alidd-samples/issues](https://github.com/qydq/alidd-samples/issues))（或在知乎上给我留言，**问题描述清楚**就行]，一般修复好周7当晚更新.

专注于物联网领域，世界的通信标准从今开始改变，手机也可以是路由器，成功于视频直播，标准并不一定是Http/s，也可以是Bluetooth.

[**我的知乎地址.**](https://www.zhihu.com/people/qydq/columns)  	   ...&#8194;&#8194;&#8194;&#8194;&#8194;&#8194;&#8194;[*API中文帮助文档.*](https://pan.baidu.com/s/1jA80j-mxCRDoTpHHiDruWw)
## 情景能力# Ability
```alidd```包含一些非常实用的能力和一些技巧， 用简洁友好的方式，助力便捷开发；以下列举当前支持的功能<br/>
**⚠️注意**
>版本间有差异，建议使用最新版本   .

### 主要能力
* **1.符合Google Material Design的基类，如：AliActivity、AliFragment、BasePresenter、BaseView==.**
* **2.两种夜间模式.**
* **3.网络1请求基于xutils3模块的封装，http实现XHttps.**
>i. HTTP实现利用了XHttps提供了post,get,upLoadFile,downLoadFile.==.<br/>
>ii.  可以使用注解功能，可以参考xUtils3开源项目.<br/>
>iii. 提供便捷XCallBack ,XParseResponse ,XProgressCallBack.==. <br/>
>iv.快速监听网络变化，网速.==.<br/>
* **4.INA系列控件相关.**
 * **5.拉```La```情景实用能力集，如MD5加密，数据校验，尺寸，图片处理，网络，模糊算法，更新软件==.**
 * **6.StrictMode API 禁权限便捷申请.**
 * 7...

### 第二能力
* **1.网络2请求JustNetClient基于Retrofit封装；RxJava，RxAndroid实现JustRxManager.**
*  **2.GIF图片更友好便捷使用，提供GifImageView可以更快的加载Gif，效率可对比之前INAPowerImageView.**
*  **3.Glide加载图片会出现抖动的修复，请使用JustImageLoader.**
* **4.拍照相册选择能力，集成PictureSelector，整合本地TakePhoto模块.**
* **5.视频，图片裁剪压缩ucrop；视频预览图MediaHelper.**
 * 6...

## 集成方式# Binaries
Usage Include this library in your project using gradle (thanks to [JitPack.io](https://github.com/jitpack-io)).  <br/>
在你项目（一般app module下）的build.gradle中添加（致谢JitPack和Jcenter）.
 ```Groovy
dependencies {
   implementation'com.sunst.alidd:alidd:1.0.15'
   //compile 'com.sunst.alidsy:alidsy:1.0.15'
}
————————————————
```

## 模块介绍# Details Module
```alidd-samples.apk```包含4个tab，```an情景```系列```alidd```框架的使用主要是```material-ux```和```aN情景s```，如

>```网络请求```类似该功能放置在```aN情景s```页  <br/>
>```NATabLayout```控件类符合material design在```material-ux```中，具体如图：（待补）

其它两个tab页是个人当前测试的一些逻辑性，功能性代码，如视频编解码，获取网络一些数据列表，这部分代码仅供参考，关于```alidd情景```更多的api可以查看帮助文档.
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
### an情景系列（aN情景s）
#### 效果图（待上传
## 混淆配置# proguard-rules
混淆规则一定要看：[**Android App代码混淆解决方案click**](https://zhuanlan.zhihu.com/p/34559807)
```BASH
#---------------------------4.(反射实体)个人指令区-qy晴雨（请关注知乎Bgwan）---------------------
# alidd 1.0.12
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

### 非常重要1：由于```alidd```基于```an-aw-base```，在版本```1.0.2```以后用```androidx```替换了所有的```support-v4，v7```等；如果你的项目已经包含了```v4，v7，```删除跟```v4，v7```的依赖，如不能删除，如下参考配置.

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
版本更新日志严格跟随代码提交内容，方便日后查阅相关记录.

### *1.0.1
   implementation'com.sunst:alidd:1.0.1'
alidd的初始版本，从an-aw-base重构而来，alidd框架1.0.1（及以下的版本）支持的android最低版本为，minSdkVersion=19

1.0.1包含的库以及版本信息
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
————————————————
```
### *1.0.2
   implementation'com.sunst:alidd:1.0.2'
本次主要是添加androidx，移除升级修复相关第三方库等内容.
**更新1：**
添加androidx 库，所有的控件使用均不支持support.v4,v7这样的包(包含如RecyclerView等等)，代替的是androidx最新的库.

如你的项目已经包含了，v4，v7 ，请如下配置（可参见常见错误1）.
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
**更新2：**
移除takePhoto模块依赖的编译库，因为一些第三方库长久不更新，会导致一些问题，严重的可能出现崩溃，（如multipleimageselect 该库的作者未更新，导致更新glide后，在android8.0 ,9.0上存在兼容性问题）.

且第三方包会带来```alidd```体积变大的风险，故移除大部分非必要的引用，并修复一些已知问题，下面为相关信息.

>1.移除multipleimageselect，代替的multipleimage模块
>com.darsh.multipleimageselect:multipleimageselect:1.0.4
>使用方法同（相同）
https://github.com/darsh2/MultipleImageSelect
>
>2.移除crop
>crop库也是比较老旧，这里针对takePhoto移除crop裁剪相关的库，故本版本不支>持裁剪（请知），但是提供了ucrop模块
>使用方法具体参见demo案例.
>
>3.移除advancedluban压缩
>advancedluban这个压缩是比较通用的压缩，这里advancedluban压缩里面包含supportv4等等，跟本次构建编译环境产生严重冲突，故移除，代替的compress模块.
>
>4.其它移除
>因升级retrofit，故移除原先网络请求RetrofitClient请求的接口api，故本版本不支持最开始的网络请求调用方法，需要自己另行封装，会在后面版本>推出强大功能的网络请求（包含异常处理部分，

**更新3：**
升级一些库的最新版本，1.0.2包含库版本信息
```Groovy
//（2）网络请求等模块
retrofit           : 'com.squareup.retrofit2:retrofit:2.6.2',//20190628
gson               : 'com.squareup.retrofit2:converter-gson:2.6.2',//20190628
rxjava2            : 'com.squareup.retrofit2:adapter-rxjava2:2.6.2',//20190628
xutils             : 'org.xutils:xutils:3.5.0',

//（3）其它模块
glide              : 'com.github.bumptech.glide:glide:4.10.0',//20190628
glidecompiler      : 'com.github.bumptech.glide:compiler:4.10.0',//20190628
————————————————
```
### 1.0.3
本次主要增加```Retrofit```支持的```网络2异步请求```情景，包含```rxJava2,rxAndroid2```；原有的```网络1请求xUtils3```保留，出于对```xUtils```作者的致敬，以及```xUtils```四大模块这么多年的习惯.  <br/>
**更新1：**
添加rxandroid2和rxjava2，用于支持网络2异步请求.
```Groovy
iorxjava2 : 'io.reactivex.rxjava2:rxjava:2.2.15',//20190703
iorxandroid2 : 'io.reactivex.rxjava2:rxandroid:2.1.1',//20190703
————————————————
```
**更新2：**
新增```网络2异步请求```http情景，提供JustNetClient对网络异步请求.
### 1.0.4
修复```网络2异步请求```-已知bug.
### *1.0.5
   implementation'com.sunst.alidd:alidd:1.0.1'
修复```网络2异步请求```-已知bug，支持JustRxManager对网络异步请求能力.
### 1.0.6
1.修复```网络2异步请求```-已知bug，主要是过滤器```null```异常处理. <br/>
2.开始支持GIF图片加持，添加Gif图片的显示加载回调，能够监听到Gif播放的次数OnGifListener==.  <br/>
3.支持GIF图片的GifImageView，并且提供GlideImageLoader完善glide加载gif存在的一些问题（如图片抖动）.  <br/>
4.```ina情景```INAStepProgressView进度条加持.
### *1.0.7
修复Gif不显示的已知bug，优化了GIF加载显示速度的问题，此处感谢```koral--/android-gif-drawable```.
### 1.0.8
1.新增INATableLayout，，此处感谢```AndroidKun/XTabLayout```.  <br/>
2.优化新增INAStepProgressView已知性能问题.  <br/>
3.修复一些已知问题.
### *1.0.9
本次版本为重大版本升级，涉及近400多个文件的变化，项目架构增```JUST模块```，优化```dd```，规范命名；其次涉及图片选择，并且结合takephoto模块，其它修复一些已知问题.  <br/>
**更新1：**
修复```网络请求JustNetClient```已知问题，优化了GIF加载GifImageView显示（```AppCompactImageView```），针对```1.0.8版本```添加了```INATableLayout```直接设置指示栏图标为drawable对象，且同时支持颜色设置.  <br/>
**更新2：**
重大升级拍照，系统相册选择模块，原有的takephoto模块重构，加持图片拍照能力集合，在pictureselector基础上增加选中的布局，整合takephoto.  <br/>
**更新3：**
提供预览本地网络视频能力```MediaHelper```，完善LA情景.
### 1.0.10
修复```网络2异步请求```-已知bug，提供变换能力；LA大写部分.
### 1.0.11
优化```UCrop```裁剪工具，防止文件冲突找不到ali情景系列包，重命名. 发布优化版本.
### *1.0.12
继承```1.0.12```版本之前的版本后apk会增加13.4M左右；当前优化后```1.0.12```版本，apk大小仅仅增加1.8M；后期不新增需求，目标控制在1M以内.
### *1.0.13
1.优化SwipeMenuRecyclerView，material design，名字变更为SwipeRecyclerView.<br/>
2.修复一些已知问题.
### *1.0.14
年前的一个版本，```1.0.14```，知乎中准备更新```alidd-samples```第一个参考，暂时以INATabLayout为例子引入alidd情景.<br/>
**更新1：**
修复PictureSelector中存在的问题，统一FileUtils处理文件.<br/>
**更新2：**
修复一些已知问题，INA系列增加inaexpandablelistview，修复一些获取网络视频第一帧图片时的报错问题，完善mediahelper，lastorge增加文件大小获取.
### 1.0.15
2020年前第一个对外的```正式版本```，**首次**在知乎中以```INATabLayout```为例推广本```an情景```系列```alidd框架 ```的使用，提供完整的```api帮助文档```，完整的```示例apk以及源代码```，完整的构建标准体系.
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