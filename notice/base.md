
## 依赖个人私有库名

implementation ‘com.sunsta.livery:livery:1.1.2’



项目名 简聊（app暂定名） ==（聊天，视频，广告，收藏，朋友圈能力的软件，（朋友圈区别于微信朋友圈，点赞什么的），可集成游戏，


## 预研

阿里云对象存储可行性研究，在预研ok的情况下进行后面的操作

## 项目结构，

本项目旨在完善livery框架

和个人收益项目，

各模块必须严格独立，相关依赖性（可以配置到阿里云上面）进行控制，

原则上控制文件大小，包大小==

| module名        | 包名   |  介绍  |
| --------   | -----:  | :----:  |
| app     | com.anli.   |   项目主要启动，包含整个框架的入口（如整个社区，通知，我的，首页）     |
| angux   |   com.anli.null   |   包含引用的第三方库，（如livery,glide，re;当前项目所需独立的资源 |
| ali-home   |   com.anli.home   |  子模块首页 |
| ali-community   |   com.anli.community   |  子模块社区|
| ali-game   |   com.anli.game   |  子模块游戏|
| ali-media   |   com.anli.media   |  子模块媒体|
| ali-me   |   com.anli.me   |  子模块个人中心|


## 项目原型设计



## 依赖个人私有库名

implementation ‘com.sunsta.livery:livery:1.1.2’



项目名 简聊（app暂定名） ==（聊天，视频，广告，收藏，朋友圈能力的软件，（朋友圈区别于微信朋友圈，点赞什么的），可集成游戏，


（是否应该开发一款，音频内的软件， 如： 荔枝 ，不要电台）==聊天只有语音，

出去外在， 人最美的不就是声音么？Voice，（声音） ，voice


## 预研

阿里云对象存储可行性研究，在预研ok的情况下进行后面的操作

## 项目结构，

本项目旨在完善livery框架

和个人收益项目，

各模块必须严格独立，相关依赖性（可以配置到阿里云上面）进行控制，

原则上控制文件大小，包大小==

| module名        | 包名   |  介绍  |
| --------   | -----:  | :----:  |
| app     | com.anli.   |   项目主要启动，包含整个框架的入口（如整个社区，通知，我的，首页）     |
| angux   |   com.anli.null   |   包含引用的第三方库，（如livery,glide，re;当前项目所需独立的资源 |
| li-home   |   com.anli.home   |  子模块首页 |
| li-community   |   com.anli.community   |  子模块社区|
| li-game   |   com.anli.game   |  子模块游戏|
| li-media   |   com.anli.media   |  子模块媒体|
| li-user   |   com.anli.me   |  子模块个人中心(包含登录）|


## 项目原型设计
在angux模块中，统一管理，项目所需的资源文件

以user为列：
在angux中，user所需的图片命名：
xx_user_login_bg1.png
xx_user_login_bg2.png


## 需要具备的能力，

当前需要研究的方向是，rxfragment通讯问题,【再次拥抱rxjava,rxandroid（一）：基本内容】

一套好用的开发工具：

图片pictureselect
基础类库框架livery
事件跳转等arouter

### （框架需要具备的）
1.简单的锁状态栏能力。
2.对于点赞按钮，必须大气的设计。
3.夜间模式。
4.左滑动回退。
5.xxx需要考虑人工智能语音实时监听，翻译模块(比如用语音调用一个方法 ==）


### 使用功能性的能力。



=========================
## livery框架继续构建

implementation ‘com.sunsta.livery:livery:1.1.2’

implementation 'com.sunsta.voice.voice:1.0.0 #包含科大讯飞的sdk的依赖库

需要分开开发，因为还有demo部分要介绍如何使用的
### 项目livery

| 模块名        | 包名   |  介绍  |
| --------   | -----:  | :----:  |
| ala      | com.sunli.   |   包含ucrop和原livery     |
| alpic        |   com.sunli.   |   包含pictureselector   |
| m-user        |   com.sunli.   |   包含pictureselector   |
| app        |    com.sunli    |  项目主启动  |


## 技能点
我的一个 project 下面有多个 Module ，一开始不知道如何单独编译某个 Module , 现在记录下


./gradlew :your_module_name:task_name

> ./gradlew :app:assembleRelease




个人构建框架依赖：

implementation ‘com.sunsta.livery:livery:1.1.2’

1.picture-selector ： 包含在livery中。

2.

