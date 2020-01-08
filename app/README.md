# 一：使用方法

//胜 ： just dependencies
implementation 'com.sunst.alidd:alidd:1.0.7'

//胜 ： just import like this
import com.ali.take.GlideImageLoader;


## Debug版本apk大小统计对比
1.2019年12月31号：  apk大小：7.5M ===  DownloadSize :6.4  （仅仅包含alidd-samples INATabLayout为例，推广引入alidd首发-使用案例）   === 当前项目大小 180M
2.2020年01月02号：  apk大小13.4M  ==== DownloadSize : 12.2M  1.包含了视频2个情景系列 和 Gif加载动画，2.引入了几张大的资源gif图， 这是apk Size变大的原因   == 项目大小： 255M
3.2020年01月02号：  apk大小16M  ==== DownloadSize : 14.7M  1.包含了视频滤镜的module videobeauty  == 项目大小： 259M
4.
综上：以上项目需要优化，减小体积


someTips:

李&#8194;芳&#8194;芳：爱&8194
context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)



# 二：视频类参考：

1.android-gif是播放Gif图片。
2.FFmpegTest是视频压缩水印处理工具===底层C语言。
https://www.jianshu.com/p/d158e8c95ec5
https://www.jianshu.com/p/9c6caa9979e3
3.mediaCodecDemo是视频加入时间水印的demo
https://blog.csdn.net/u013147734/article/details/52401213
https://gitee.com/dxtx100/mediaCodecDemo
4.PictureSelector是图片选择框架的
5.YuanMusicPlay是一款音乐播放软件
6.small-video-record是视频拍摄压缩效率高效手段
https://github.com/mabeijianxi/small-video-record/blob/master/document/README_CH.md
7.包含命令参数１
https://www.jianshu.com/p/f7b0f5bab864
8.包含命令参数２，并且统计压缩效率对比
https://www.jianshu.com/p/4f82b058c8ec　（含有其它干货）
9.非常好用的gihub上文件操作工具
https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/README-CN.md
10.视频情景1（截屏，select video , cut ,scale mixaudio
https://github.com/tangpeng/VideoCompressor
https://github.com/tangpeng/FFmpegDemo
11.获取视频缩略图，(非常高效率的获取缩略图https://github.com/deepsadness/MediaMetadataRetrieverWrapper）


# 三：网络以及其它模块
1. 网络请求参考链接
Retrofit解析及文件上传下载（前后台详细实现）:https://www.jianshu.com/p/1300fd8de07d
解决Retrofit文件下载进度显示问题:https://blog.csdn.net/ljd2038/article/details/51189334
Retrofit+Rxjava 下载文件(带进度显示):https://blog.csdn.net/a1018875550/article/details/51832700
【Android】RxJava + Retrofit完成网络请求:https://segmentfault.com/a/1190000018253015
【Android】Retrofit 2.0 的使用一、概述二、原理:https://cloud.tencent.com/developer/article/1119020
【Android】Retrofit网络请求参数注解，@Path、@Query、@QueryMap...:https://www.jianshu.com/p/7687365aa946
Android中Retrofit 2.0直接使用JSON进行数据交互:https://www.jb51.net/article/121947.htm
https://bbs.csdn.net/topics/392380893?page=1
Android Retrofit详解:https://www.jianshu.com/p/865e9ae667a0
Retrofit打印请求地址以及返回的数据内容:https://blog.csdn.net/weixin_43115440/article/details/83306515


##20191218新增技术

其它：（美颜效果）
1.　美颜效果的视频录制参考
文章介绍：
https://blog.csdn.net/qqchenjian318/article/details/78274901
源码地址：
https://github.com/qqchenjian318/VideoEditor-For-Android


美颜，变换涂鸦加贴纸的效果
2.https://github.com/uestccokey/EZFilter



## 个人开发计划排版

(1.)封装webactivity加入带标签的内容加载
(2).电话号码安全显示,==ok
(3).复制的工具类==ok
(4).字体 颜色标记工具类==ok
(5).封装网络请求的类（考虑多种异常，多种数据接口解析，当然文件下载，图片下载，断点续传等等要考虑）
##网络请求api ,以call ,observa开头==ok
(6).ScrollView 网上顶上去
(7).修改glide,MultiImageSelector框架版本,(重点)==ok
(8).Readme模块更新说明：致谢，当前Glide版本号，MultiImageSelector版本号，RxJava，Retrofit版本号情况。====ok
(9).重构TakeCamear===ok
(10).LaBitmap(保存）应该给一个File对象的返回值===ok
(11).优化资源 文件，减少依赖的大小====ok
（12）。权限的，视频的，压缩的，录屏的，播放的，
（13）。ExpandalbleLayout  ==== ok
（14）。activity跳转的工具
(15)。文件获取 第一帧图片需要try =catch  == ok
(16).统一修改系统的弹窗.,,修改同意的Dialog弹窗样式，，，，，，，
(17)，视频模块的整合(很多内容，耗时比较久)
*(18总结性的链接地址总结)https://www.jianshu.com/p/4f82b058c8ec
(19).alidd情景系列，  完成国际版的翻译工作，进一步压缩集成体积(备注：尽快完成2-3天）.
（20）网络请求下载工具,完善alidd网络请求下载封装 一天之内搞定，并且准备发布alidd1.0.16版本）
(21).检视ali所有的返回键控制情况，统一并处理为一直的效果(1天之内完成).
(22).网络模块需要紧急修复CLEARTEXT communication to download.fir.im not permitted by network security policy，9.0系统出现的网络安全访问问题

## 临时新增视频(功能点非常多和杂乱)编解码模块（非alidd-框架系列==属于其它模块）整合.

###########
2020年01月03日备注：
该模块可能后面有时间，写篇博客，发现这些无论拿出哪个点都得写半天，我最近也对opengles十分感兴趣，以后有时间详细分析一下. 请关注个人知乎，如果看到：https://zhihu.com/people/qydq
###########

1.视频录制： （含截屏工具类）
（1）.摄像头视频录制。（前置摄像，后置摄像头，滤镜）。
（2）.手机屏幕录屏录制。 =====20191231号已完成改模块功能

手机屏幕录制已经封装成（xrecord模块) 可以直接参考视频情景1使用
https://blog.csdn.net/yellowcath/article/details/81985810 （主要，有一个可以本地播放视频的内嵌的播放器）
https://github.com/wuxiaoqiang625/VideoCompress
https://github.com/Tourenathan-G5organisation/SiliCompressor
https://www.jianshu.com/p/4f82b058c8ec（上面已有，这里总结性使用，含FFMpeg命令）

2. 视频滤镜：
（1）.普通视频滤镜。
美颜效果的视频录制参考，当前应先集成该模块的美颜滤镜效果2020年01月

(注意申请相机权限camera)
（2）.奇特效果滤镜。

3. 视频加水印：
https://www.jianshu.com/p/4cf8403cd9fa  这是OpenGl视频加水印
https://github.com/skateboard1991/OpenGlCamera
（1）.动态水印。
（2）.静态水印。
（3）.水印加贴纸。
（4）.加时间戳水印（ 可选） 。

(4)===001:
时间戳1）录制的画面没有声音
https://github.com/Zihao-Wu/YuvWater
文章可以参考：
https://blog.csdn.net/u010521645/article/details/85166237

(4)===002 (2016年的一个视频动态时间水印，视频有可能黄色变成了蓝色）
博客介绍：https://blog.csdn.net/u013147734/article/details/52401213
源码：https://gitee.com/dxtx100/mediaCodecDemo