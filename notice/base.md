
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

