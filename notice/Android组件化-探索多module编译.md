# android组件化-探索多module独立编译


## 写在前面
17年华为智能家居项目开发中，为了提高开发效率，避免不必要的编译时间浪费(16G电脑编译半个小时)，就不得不使单个业务模块独立运行，我负责探索和预研。

也有出海版本二进制配置productFlvors，有相同的点，具体可以参考我下面两篇文章

* 1.[# 使用gradle的productFlavors实现Android项目多渠道打包](https://zhuanlan.zhihu.com/p/33722674)
* 2.[# Android组件化探索之Library productFlavors](https://zhuanlan.zhihu.com/p/33787611)

小项目基本用不到独立编译，但最近遇到一个中等的项目，有这个概念，一看知乎，好像我缺少这方面的总结，那如何实现呢？


## 一：问题背景

android组件化开发中，工程通常被拆分为多个module。开发中可以根据需要来选择```组件开发```或者```集成开发```。

[组件化的时候，会有一个App module（主module），多个业务module，一堆lib module。现在假如App module是App.module，有一个业务module叫login.module，还有一个lib module叫lib.module。组件化是一个项目解耦的过程，所以需要把每个业务module公用的功能抽离到lib.module中比如网络请求模块](https://zhuanlan.zhihu.com/p/33787611)

在build.gradle配置文件中可以看到，module主要有两种属性：
* 1.application属性，即该module为可独立运行的app程序
```css
	apply plugin: ‘com.android.application’
```
* 2.library属性，一般作为app编译运行依赖的库文件，不可独立运行
```css
	apply plugin: ‘com.android.library’
```
## 二： 组件开发和集成开发模式切换

在多个团队进行各自组件的开发时，业务组件应处于application属性，方便开发人员的独立开发、运行及调试。当组件开发完毕需要转为集成模式，业务组件应处于library属性，便于宿主app依赖各业务组件，构造一个具备完整功能app。  
为了便于各业务组件在两种属性之间切换，我们可以利用gradle构建工具，在项目根目录的gradle.properties中定义一个属性isBuildModule，true表示当前处于组件开发模式，false表示当前处于集成开发模式。其他业务组件的build.gradle中可以读取gradle.properties中定义的isBuildModule常量，根据需要在两种开发模式间切换。

### 1.在gradle.properties文件中创建一个变量isBuildModule，如下
```css
#isBuildModule 是否探索多module独立编译  
isBuildModule=true
```

### 2.在各业务组件的builde.gradle中读取isBuildModule，设置运行属性，(可以是原始的程序入口app module）。
```css
if (isBuildModule.toBoolean()) {  
    apply plugin: 'com.android.application'  
} else {  
    apply plugin: 'com.android.library'  
}
```
### 3.在各业务组件的builde.gradle中读取isBuildModule，设置修改源集

也就是修改引用，不同的AndroidManifest.xml资源文件
(图片1)
每个业务组件都会有各自的AndroidManifest.xml文件，其中配置了业务组件使用的四大组件、权限、Application等信息。在组件开发模式中，AndroidManifest.xml应该声明组件作为app独立运行所需要的全部属性，e.g.Application信息和桌面启动的Activity。在集成开发模式中，每个业务组件的AndroidManifest.xml都会合并到宿主app中，此时不需要声明Application和桌面启动的Activity等信息。综上，业务组件在切换开发模式的同时，需要切换对应的AndroidManifest.xml。  
可以通过创建两个AndroidManifest.xml文件位于不同的路径，并由gradle提供的sourceSets属性来配置不同开发模式使用的AndroidManifest文件路径，来解决上述的冲突问题。

```css
sourceSets {  
    main {  
        //jni库统一放在libs目录下 //            jniLibs.srcDir = ['libs']    
 if (isBuildModule.toBoolean()) {  
//单独运行    
 manifest.srcFile 'src/main/alone/AndroidManifest.xml'  
  } else {  
//合并到宿主中    
 manifest.srcFile 'src/main/AndroidManifest.xml'  
  resources {  
//正式版本时.剔除debug文件夹下的所有调式文件    
 exclude 'src/debug/*'  
  }  
        }  
    }  
}
```
首先是集成开发模式下的 AndroidManifest.xml，不能拥有自己的 Application 和 桌面启动的 Activity，也不能声明APP名称、图标等属性，只能声明自己业务组件独有的东西。示例如下：
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"  
  package="com.sunsta.livery">  
  
    <application  
  android:allowBackup="true"  
  android:icon="@mipmap/app_ic_launcher"  
  android:label="@string/app_name"  
  android:roundIcon="@mipmap/app_ic_launcher_round"  
  android:supportsRtl="true"  
  android:theme="@style/AppTheme" />  
</manifest>
```
其次是组件开发模式下的表单文件，和常见的app的AndroidManifest.xml一致：
```xml
<?xml version="1.0" encoding="utf-8"?>  
<manifest xmlns:android="http://schemas.android.com/apk/res/android"  
  package="com.sunsta.home">  
  
    <application>  
        <activity android:name="com.sunsta.home.ui.SplashActivity"  
  android:theme="@style/AppTheme.Start"  
  android:screenOrientation="portrait"  
  >  
            <intent-filter>  
                <action android:name="android.intent.action.MAIN" />  
  
                <category android:name="android.intent.category.LAUNCHER" />  
            </intent-filter>  
        </activity>  
        <activity android:name=".ui.MainActivity"  
  android:screenOrientation="portrait"  
  />  
        <activity android:name=".ui.GuideActivity"/>  
    </application>  
  
</manifest>
```
这样在每个Module中添加另一个独立编译时使用的AndroidManifest.xml，就可以保证独立编译时有启动的Activity等。

### 4.接下来修改applicationId，如下：
```css
defaultConfig {  
    if (isBuildModule.toBoolean()){//当为运行module时才有applicationId属性（可以做到不同module的app能在手机上共存）  
  applicationId "com.sunsta.livery"  
  }  
    minSdkVersion 21  
  targetSdkVersion 28  
  versionCode 1  
  versionName "1.0"  
  multiDexEnabled true  
}
```
到此为止大功告成，总体来说配置过程不是很麻烦。接下来只要修改gradle.properties文件中isBuildModule的值就可以自由切换了，

## 三：实际项目经验建议

工程中有多moudle时，每个moudle都有独自的build.gradle文件，可配置各自的编译信息。

当buildToolsVersion与其他的编译依赖版本号不一致时，可能出现编译问题或者警告什么你总之意想不到的错误，

为了避免上述繁琐的步骤，必须将各moudle的编译依赖信息统一配置，方便后续的更新维护

### 1. 统一各个module的compileSdkVerstion、targetSdkVersion、support-library等的版本号，如下config.gradle

```css
ext {  
    //android 开发版本  
  android = [  
            compileSdkVersion: 29,  
            buildToolsVersion: '29.0.1',  
            minSdkVersion : 21,  
            targetSdkVersion : 29,  
            applicationId : "com.mvvm.mvvmcomponent",  
            versionCode : 1,  
            versionName : "1.0",  
    ]  
  
    //version 版本控制  
  versions = [  
  
    ]  
  
    //androidx支持库配置  
  androidx = [  
            "androidx-appcompat" : "androidx.appcompat:appcompat:1.0.2",  
            "androidx.lifecycle" : "androidx.lifecycle:lifecycle-extensions:2.1.0",  
            "androidx.recyclerview" : "androidx.recyclerview:recyclerview:1.1.0",  
            "androidx.constraintlayout" : "androidx.constraintlayout:constraintlayout:1.1.3",  
            "androidx.cardview" : "androidx.cardview:cardview:1.0.0",  
            "android.material" : "com.google.android.material:material:1.0.0",  
            "androidx.navigation-fragment" : "androidx.navigation:navigation-fragment:2.1.0",  
            "androidx.navigation-ui:" : "androidx.navigation:navigation-ui:2.1.0",  
            "junit" : "junit:junit:4.12",  
            "androidx.test.ext-junit" : "androidx.test.ext:junit:1.1.0",  
            "androidx.test.espresso:espresso-core": "androidx.test.espresso:espresso-core:3.1.1",  
    ]
```
###  2.针对多模块独立编译统一一个全局的build.gradle，如下参考module.build.gradle文件中内容
```css
if (isBuildModule.toBoolean()) {  
    //作为独立App应用运行  
  apply plugin: 'com.android.application'  
} else {  
    //作为组件运行  
  apply plugin: 'com.android.library'  
}  
  
android {  
    compileSdkVersion rootProject.ext.android.compileSdkVersion  
    buildToolsVersion rootProject.ext.android.buildToolsVersion  
    defaultConfig {  
        minSdkVersion rootProject.ext.android.minSdkVersion  
        targetSdkVersion rootProject.ext.android.targetSdkVersion  
        versionCode rootProject.ext.android.versionCode  
        versionName rootProject.ext.android.versionName  
        consumerProguardFiles 'consumer-rules.pro'  
  testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"  
  
  javaCompileOptions {  
            annotationProcessorOptions {  
                arguments = [AROUTER_MODULE_NAME: project.getName()]  
            }  
        }  
  
    }  
    buildTypes {  
        debug {  
            minifyEnabled false  
  proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'  
  }  
        release {  
            minifyEnabled false  
  proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'  
  }  
    }  
    sourceSets {  
        main{  
            //jni库统一放在libs目录下  
//            jniLibs.srcDir = ['libs']  
  if (isBuildModule.toBoolean()){  
                //单独运行  
  manifest.srcFile 'src/main/alone/AndroidManifest.xml'  
  }  
            else {  
                //合并到宿主中  
  manifest.srcFile 'src/main/AndroidManifest.xml'  
  resources{  
                    //正式版本时.剔除debug文件夹下的所有调式文件  
  exclude 'src/debug/*'  
  }  
            }  
        }  
    }  
    //开启dataBinding  
  dataBinding{  
        enabled true  
  }  
  
    compileOptions {  
        sourceCompatibility = 1.8  
  targetCompatibility = 1.8  
  }  
}  
  
sourceSets {  
    main {  
        //jni库统一放在libs目录下  
//            jniLibs.srcDir = ['libs']  
  if (isBuildModule.toBoolean()) {  
            //单独运行  
  manifest.srcFile 'src/main/alone/AndroidManifest.xml'  
  } else {  
            //合并到宿主中  
  manifest.srcFile 'src/main/AndroidManifest.xml'  
  resources {  
                //正式版本时.剔除debug文件夹下的所有调式文件  
  exclude 'src/debug/*'  
  }  
        }  
    }  
}
```  
### 3.module统一引用
这样统一以后，在上【第二第2小节内容中】各业务组件(也就是子module，或程序入口app module)就可以修改为，如下我的module-main模块引用代码

```css
apply from: "../module.build.gradle"  
apply plugin: 'kotlin-android'  
apply plugin: 'kotlin-android-extensions'  
  
android {  
    defaultConfig {  
       if (isBuildModule.toBoolean()){  
           applicationId "com.sunsta.main"  
  }
```
这样的好处，除module-main模块以外，还有其它的比如module-login,module-video,module-home都可以这样统一处理，当然作为程序的主入口app module，在最后整个集成开发时修改为application属性。
```css
apply plugin: 'com.android.application'
```
## 最终编译结果
现在我的一个 project 下面有多个module，在Terminal 标签下输入

> ./gradlew :your_module_name:task_name

如：

> ./gradlew :app:assembleRelease
> ./gradlew :module-home:assembleDebug
> ./gradlew :module-video:build

或者Build点开不是有个Make Module，再或者android studio 右侧的 Gradle 标签，找到对应的任务，如：



---
以上便是android组件化-探索多module独立编译内容的记录，欢迎各位大佬对此提出可行性意见和建议，共同成长进步，如果有帮助到你，还请点个关注或赞，如果有其他问题，可以在评论下方留言，看到会回复.

请尊重劳动成果，注意文中[版权声明](https://zhuanlan.zhihu.com/p/80668416)，Android专栏不定时更新，**欢迎[点击关注我知乎](https://www.zhihu.com/people/qydq/activities)**。也可以同时关注[人工智能专栏](https://zhuanlan.zhihu.com/sstai)，本内容作者**sunst**，有问题请沟通qyddai@gmail.com

> 作者：sunst 发布日期：2020-03-27 20:45 修改日期：null
