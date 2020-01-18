# 一：使用方法

//胜 ： just dependencies
implementation 'com.sunst.alidd:alidd:1.0.7'

//胜 ： just import like this
import com.ali.take.GlideImageLoader;

下一个版本：

justli 1.1.0
implementation 'com.sunsta.justli:justli:1.1.0'  ( 胜　)

ImageCompleteCallback

implementation 'com.sunsty.bear:bear:1.0.7'

implementation 'com.sunsty.bear:bear:1.0.7'

发现一个好用的库：demo大师 可以关注一下
http://www.demodashi.com/mydemo/buy.html

所有的禁止使用databind


#2020年成都地区专属系列架构（未开放）：
1.依赖关系
compile 'com.sunsta.livery:livery:1.1.0'

2.包名统一：
com.ali
model  =====  mode
module (save) ====modu==

3.项目结构：
ali ------
---uxalic(***资源文件，公共可用-utils,callback，dependence)
com.ali.very.LivUx,LivBitmap,LivSize,LivUxText,
com.ali.callback.OnItemClickListener

---livery(***提供对外，AndroidManifest.xml)
com.ali.AnApplication
com.ali.view.INASwipeLayout
com.ali.view.activity.PictureSelector
---picture(***module支持， =========or ==
even just empty
com.ali.module.PictureTask
com.ali.
---thpart(***后期可扩展，第三方支持)

---samples




## Debug版本apk大小统计对比
1.2019年12月31号：  apk大小：7.5M ===  DownloadSize :6.4  （仅仅包含alidd-samples INATabLayout为例，推广引入alidd首发-使用案例）   === 当前项目大小 180M
2.2020年01月02号：  apk大小13.4M  ==== DownloadSize : 12.2M  1.包含了视频2个情景系列 和 Gif加载动画，2.引入了几张大的资源gif图， 这是apk Size变大的原因   == 项目大小： 255M
3.2020年01月02号：  apk大小16M  ==== DownloadSize : 14.7M  1.包含了视频滤镜的module videobeauty  == 项目大小： 259M
4.
综上：以上项目需要优化，减小体积


someTips:

李&#8194;芳&#8194;芳：爱&8194
context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
gif

以后的标题都叫：（1）android利用alidd情景框架便捷实现夜间模式使用案列;
               （2）android利用alidd情景框架快速加载动画实现的套路;
               （3）android特别的ExpandableListView视觉效果（仿cardView）利用alidd便捷实现;

Git命令：git log --pretty=oneline 文件名
git log --pretty=oneline frameworks/base/packages/SystemUI/AndroidManifest.xml
34b7ac981a39193ca78e0d4673ce66515c23988d     [add a new feature to show the battery voltage value and level]     1.Support show the level of
c1d6879f098775f1d703dfcfe814c4d726be47ad [Create MT6753-6M AP source repository]
每一行最前面的那一长串数字就是每次提交形成的哈希值，接下来使用git show即可显示具体的某次的改动的修改。
it show 命令查看具体的修改详情了。如：
git show <git提交版本号> <文件名>
git show 34b7ac981a39193ca78e0d4673ce66515c23988d frameworks/base/packages/SystemUI/AndroidManifest.xml
注释：按N和Ctrl+N进行上下搜索结果切换

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

# 四：人脸识别demo

1.(Android离线身份证等图片文字识别)==https://www.jianshu.com/p/21d5442a8358?utm_campaign
https://github.com/itlwy/TextOcrExample

2.Android Ocr文字识别 身份证识别 实时扫描(对于python人脸识别）
https://blog.csdn.net/weixin_41632509/article/details/79799971

理论知识介绍：
https://blog.csdn.net/findhappy117/article/details/79367413

3.完整可用的二代身份证识别(需要获取key
https://github.com/XieZhiFa/IdCardOCR

4.二维码扫描的一个工具下载
https://github.com/banketree/Scan-Android.git



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
(16).统一修改系统的弹窗.,,修改同意的Dialog弹窗样式，，，，，，，  ===ok
(17)，视频模块的整合(很多内容，耗时比较久)
*(18总结性的链接地址总结)https://www.jianshu.com/p/4f82b058c8ec
(19).alidd情景系列，  完成国际版的翻译工作，进一步压缩集成体积(备注：尽快完成2-3天）.
（20）网络请求下载工具,完善alidd网络请求下载封装 一天之内搞定，并且准备发布alidd1.0.16版本）   ==ok
(21).检视ali所有的返回键控制情况，统一并处理为一直的效果(1天之内完成).
(22).网络模块需要紧急修复CLEARTEXT communication to download.fir.im not permitted by network security policy，9.0系统出现的网络安全访问问题  ==ok
(23).INATablayout，新增一些属性（右可见，右右可见， 左边可见，中间不可见，字体大小），修复material错误命名，back事件（暂留），考虑是否需要增加an_match_match.VBG----HBG. ==ok
(24).新增几个圆角，enable，并且再统一一下drawable中selector，修改并且完善AboutPage，随后发布1.0.19(1.0系列最后一个版本）
     目前新增了一个，但是未完成，细想drawable系列的选择有严重冲突的可能性，所以这里发布1.0.19版本以后，需要重构，下面列举（
        这里改的地方比较多，，，，

        a:对于选择器, 有一种是可以点击的改变点击效果， 有一种是不可以点击的时候的默认背景，那么这种在命名方面，(选择器，则移除drawable名名，)如

        (state_pressed=true : state_enabled=true (默认): state_enabled=false :

        in_selector.xml   ============

        in_selector_clear.xml   =========  对应 ：base_drawable_clear;;;base_drawable_clear_click;;;enable

        in_selector_ll.xml      =========  普通颜色，对应 ： an_color_ll_white;;;an_color_ll_select;;;an_color_ll_noselect;;;

        in_selector_ll_shape.xml      =========  资源文件，对应 ： an_color_ll_white;;;an_color_ll_select;;;an_color_ll_noselect;;;


        b:所有的图片，也就是png,jpg，都命名为 ,,base_drawable_xxx.png;; ic_drawable_xxx.webp;;ic_picture_xxx.png



     ）
(25).临时发现一个bug，设置夜间模式的主题，application中写错误了Settheme()，改为setDayniththem(),并且夜间模式，日间模式颜色需要调控(并且新增一个IntentUtils曾对跳转) ==ok
(26).增加两个通用的布局文件， （参考samples中，item_parent，item_child布局，并且定一个标准(周末完成） ==ok
(27).pictureseletor被迫更新版本号控制，（因为最低版本api=17  或者19 ,但是很多还是17的版本 ，， 到底要不要兼容，发布1.0.19 还是发布 1.1.0版本来被迫更新（ 资源文件是否进一步优化） 这些内容都需要完成。
(28).DataServer中 ，copyClipboad复制需要返回对应的结果状态.
(29).从1.0.19开始考虑是否提供aar的形式出来，（否定， 如果当有一天下载不了的时候再提供相应的版本出来，但是应该做好记录，对应低版本关系)
(30).集成该框架有很多错误的地方需要修改， 需要重新优化mainfest.xml (并且需要更新一份justli使用文档， 注意事项)如：

不能添加：useLibrary 'org.apache.http.legacy'
(31).完成文字选择颜色demo代码，（多个选择，后回家过年）,alidd框架考虑去掉AliApplication
(32).https://www.cnblogs.com/hh8888-log/p/10300972.html(下载文件含断点续传)

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