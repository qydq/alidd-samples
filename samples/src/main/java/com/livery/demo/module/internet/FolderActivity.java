package com.livery.demo.module.internet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.livery.demo.MainActivity;
import com.livery.demo.R;
import com.sunsta.bear.AnConstants;
import com.sunsta.bear.engine.DownloadService;
import com.sunsta.bear.faster.FileUtils;
import com.sunsta.bear.faster.LADialog;
import com.sunsta.bear.faster.LAStorageFile;
import com.sunsta.bear.faster.LaLog;
import com.sunsta.bear.faster.MyDialog;
import com.sunsta.bear.faster.ToastUtils;
import com.sunsta.bear.layout.INAStartAnimationView;
import com.sunsta.bear.view.AliActivity;
import com.sunsta.bear.view.activity.AliWebActivity;

import java.io.File;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static com.livery.demo.module.internet.OrignalRetrofitActivity.Http_Full_Download_Url;

public class FolderActivity extends AliActivity {
    private RelativeLayout relativeLayout;

    @Override
    public void initView() {
        setContentView(R.layout.activity_folder);
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);

        Activity activity = FolderActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }

//        launchTimer(2000);

        if (null != relativeLayout) {
            INAStartAnimationView openingStartAnimation = new INAStartAnimationView(this);
            openingStartAnimation.setImage(R.drawable.ic_color_camera);
            openingStartAnimation.show(relativeLayout);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(new Intent(FolderActivity.this, PhotoActivity.class));
                    dialog2(v);
                }
            });
        }


        //        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ToastUtils.s(FolderActivity.this, "我是测试的数据，时间到");
//// showDialog();
//            }
//        }, 2000);


        int currentMode = AppCompatDelegate.getDefaultNightMode();
//        int currentNightMode = currentMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentMode == Configuration.UI_MODE_NIGHT_NO) {
            LaLog.d("当前为非夜间模式" + currentMode);
        } else {
            LaLog.d("当前为夜间模式" + currentMode);
        }
    }

    @Override
    public void onLaunchedTimer() {
        ToastUtils.s(FolderActivity.this, "我是测试的数据，时间到");
    }

    // 蒙层
    public void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.an_dialog_middle_pure_image);
        TextView textView = new TextView(this);
        textView.setText("getString(R.string.isFirstShopHint)");
        textView.setTextSize(18);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, 0, 0, 600);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialog.addContentView(textView, layoutParams);
        dialog.show();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (display.getWidth()); // 设置宽度
        lp.height = (display.getHeight());
        dialog.getWindow().setAttributes(lp);
// dialog.getWindow().setGravity(80);
    }

    //文件夹
    public void onClick1(View view) {
        String folder = FileUtils.getInstance().getPwdMovieFolder();
        File fouceTouchFile = LAStorageFile.INSTANCE.fouceTouchFile(folder, "sunst_test.mp4");
        String getAbsolutePath1 = getExternalFilesDir(null).getAbsolutePath();

        if (fouceTouchFile.exists()) {
            LaLog.d("创建1-文件创建成功");
        } else {
            LaLog.d("创建1-文件文件创建失败");
            createADirectory(getAbsolutePath1);//创建文件夹
        }
        File fouceTouchFile1 = LAStorageFile.INSTANCE.fouceTouchFile(folder, "sunst_test.mp4");
    }

    public void onClick2(View view) {
        String getAbsolutePath = getFilesDir().getAbsolutePath();
// String getAbsolutePath1 = getExternalFilesDir(null).getAbsolutePath();
        File f = new File(getAbsolutePath + File.separator + "abc" + File.separator, "test.txt");
        if (f.exists()) {
            LaLog.d("创建2-文件创建成功");
        } else {
            LaLog.d("创建2-文件文件创建失败" + getAbsolutePath);
            createAFile(getAbsolutePath);//创建文件
        }
    }

    public void onClick3(View view) {
//        RxPermissions rxPermissions = new RxPermissions(FolderActivity.this);
//        rxPermissions.requestEach(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
//                .subscribe(permission -> {
//                    if (permission.granted) {
//                        LaLog.d("创建2-文件创建成功11111111");
//
//                        return;
//                    }
//                    if (permission.shouldShowRequestPermissionRationale) {
//                        LaLog.d("创建2-文件创建成功222222222222");
//
//                        return;
//                    }
//                });

        File fouceTouchFile = LAStorageFile.INSTANCE.fouceTouchFile(FileUtils.getInstance().getPwdMovieFolder(), "test.mp4");
        LaLog.d("验证1-\nfouceTouchFile：path" + fouceTouchFile.getPath() + "\n fileName：" + fouceTouchFile.getName() + "\n fileAbsPath:" + fouceTouchFile.getAbsolutePath() + "\n");

        String getAbsolutePath = getFilesDir().getAbsolutePath();
        String getPath = getFilesDir().getPath();
        String getName = getFilesDir().getName();
        LaLog.d("验证2-\ngetFilesDir：getAbsolutePath =" + getAbsolutePath + "\n getPath：" + getPath + "\n getName:" + getName + "\n");

        File external = getExternalFilesDir(null);
        String getAbsolutePath1 = external.getAbsolutePath();
        String getPath1 = external.getPath();
        String getName1 = external.getName();
        LaLog.d("验证3-\ngetFilesDir：getAbsolutePath1 =" + getAbsolutePath1 + "\n getPath1：" + getPath1 + "\n getName1:" + getName1 + "\n");

        if (fouceTouchFile.exists()) {
            LaLog.d("验证-文件fouceTouchFile存在===");
        } else {
            LaLog.d("验证-文件fouceTouchFile不存在===");

            File newFile = new File(getAbsolutePath, fouceTouchFile.getName());
// File newFile = new File(FileUtils.getInstance().getPwdMovieFolder(), fouceTouchFile.getName());
// Make sure the Pictures&Movie directory exists.
            if (newFile.exists()) {
                LaLog.d("验证-新文件已存在");
            } else {
                LaLog.d(newFile.mkdirs() ? "验证-新文件创建成功" : "新文件创建失败");
            }
        }
    }

    private void showResult(int result) {
        switch (result) {
            case FileUtils.FLAG_SUCCESS:
                LaLog.d("result: create success");
                break;
            case FileUtils.FLAG_EXISTS:
                LaLog.d("result: already exist");
// downloadApk(fouceTouchFile);
                break;
            case FileUtils.FLAG_FAILED:
                LaLog.d("result: create failed");
                break;
        }
    }

    private void downloadApk(String fileName, String pwdPath) {
        Intent intent = new Intent(FolderActivity.this, DownloadService.class);
        intent.putExtra(AnConstants.EXTRA.REQUEST_URL, Http_Full_Download_Url);
        intent.putExtra(AnConstants.EXTRA.REQUEST_FILENAME, fileName);
        intent.putExtra(AnConstants.EXTRA.REQUEST_DIRPATH, pwdPath);
        startService(intent);
    }

    /**
     * 创建单个文件
     */
    public void createAFile(String filePath) {
        int result = FileUtils.CreateFile(filePath + "/demos/file/test.txt");
        showResult(result);
    }

    /**
     * 创建文件夹
     */
    public void createADirectory(String filePath) {
        int result = FileUtils.createDir(filePath + "demos/file/tmp/test");
        showResult(result);
    }

    public void onClick4(View view) {
        startActivity(new Intent(FolderActivity.this, MainActivity.class));
    }

    public void closeNight(View view) {
//        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
        setDayTheme();
    }

    public void openNight(View view) {
//        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        setNightTheme();
    }

    public void dialog3(View view) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LaLog.d("验证-onConfigurationChanged uiMode=" + newConfig.uiMode);

        int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        LaLog.d("验证-onConfigurationChanged currentNightMode=" + currentNightMode);

        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
// 夜间模式未启用，我们正在使用浅色主题
            LaLog.d("验证-UI_MODE_NIGHT_NO");
        }
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
// 夜间模式启用，我们使用的是深色主题
            LaLog.d("验证-UI_MODE_NIGHT_YES");
        }

    }

    public void dialog1(View view) {

        Activity activity = FolderActivity.this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        MyDialog myDialog = new MyDialog(activity);
// Dialog myDialog = LADialog.INSTANCE.newDialog(FolderActivity.this);
        myDialog.setListData(null);
        myDialog.setCancelable(false);
        myDialog.show();
    }

    public void dialog2(View view) {
//        MyDialog myDialog = new MyDialog(FolderActivity.this, R.style.an_dialog_middle_pure_image, LADialog.STYLE.middle_pure_image);
//        myDialog.setImageUrl("https://ae01.alicdn.com/kf/U6de089ce45ff468a8f06c50e19ad7379N.jpg");
////        myDialog.setImageUrl("http://i.imagseur.com/uploads/gifs/gif_10-05-2015/9349392.gif");
////        myDialog.setImageUrl("http://i.imagseur.com/uploads/gifs/gif_16-05-2015/49737-beautiful-asian-banged-hard.gif");
//        myDialog.show();

        //一般来说需要定义一个显示对话框级别的顺序， 不能定义的话，则自己+1操作
        LADialog.INSTANCE.attach(1, LADialog.STYLE.lodding_animation, FolderActivity.this);
//        LADialog.INSTANCE.setImageUrl("http://i.imagseur.com/uploads/gifs/gif_10-05-2015/9349392.gif");

//        LADialog.INSTANCE.attach(2, LADialog.STYLE.middle_download_center, FolderActivity.this);
//        LADialog.INSTANCE.setImageUrl("http://i.imagseur.com/uploads/gifs/gif_10-05-2015/9349392.gif");

//        LADialog.INSTANCE.attach(3, LADialog.STYLE.middle_pure_image, FolderActivity.this);
//        LADialog.INSTANCE.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showToast("我是个人测试");
//                gotoIntorduce();
//            }
//        });
//        LADialog.INSTANCE.setImageUrl("http://p7.urlpic.club/pic1893/upload/image/20190220/22008351726.jpg");
//
//        LADialog.INSTANCE.attach(4, LADialog.STYLE.middle_pure_image, FolderActivity.this);
//        LADialog.INSTANCE.setImageUrl("http://i.imagseur.com/uploads/gifs/gif_16-05-2015/49737-beautiful-asian-banged-hard.gif");
//
//        LADialog.INSTANCE.attach(5, LADialog.STYLE.fullscreen_dowoload_bottom, FolderActivity.this);
//        LADialog.INSTANCE.setImageUrl("https://ae01.alicdn.com/kf/U6de089ce45ff468a8f06c50e19ad7379N.jpg");
        LADialog.INSTANCE.launch();
    }

    private void gotoIntorduce() {
        Intent intent = new Intent(this, AliWebActivity.class);
        intent.putExtra("url", "https://zhuanlan.zhihu.com/p/26089356");
        intent.putExtra("title", "Github官方alidd框架");
        startActivity(intent);
    }
}
