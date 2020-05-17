package com.livery.demo.module.internet;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.livery.demo.MainActivity;
import com.livery.demo.R;
import com.livery.demo.take.PosterDialog;
import com.sunsta.bear.AnConstants;
import com.sunsta.bear.engine.DownloadService;
import com.sunsta.bear.faster.FileUtils;
import com.sunsta.bear.faster.LAStorageFile;
import com.sunsta.bear.faster.LaLog;
import com.sunsta.bear.faster.MyDialog;
import com.sunsta.bear.faster.ToastUtils;
import com.sunsta.bear.layout.INAStartAnimationView;

import java.io.File;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;
import static com.livery.demo.module.internet.OrignalRetrofitActivity.Http_Full_Download_Url;

public class FolderActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_folder);
        relativeLayout = findViewById(R.id.relativeLayout);
        INAStartAnimationView INAStartAnimationView = new INAStartAnimationView(this);
        INAStartAnimationView.setImage(R.drawable.ic_color_camera);
        INAStartAnimationView.show(relativeLayout);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtils.s(FolderActivity.this, "我是测试的数据，时间到");
// showDialog();
            }
        }, 2000);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FolderActivity.this, UpDownloadActivity.class));
            }
        });

        int currentMode = AppCompatDelegate.getDefaultNightMode();

// int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentMode == Configuration.UI_MODE_NIGHT_NO) {
            LaLog.d("当前为非夜间模式" + currentMode);
        } else {
            LaLog.d("当前为夜间模式" + currentMode);
        }
    }

//    @Override
//    public void initView() {
//        setContentView(R.layout.activity_folder);
//        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
//
//
//
//
//        Activity activity = FolderActivity.this;
//        while (activity.getParent() != null) {
//            activity = activity.getParent();
//        }
//
//        if (null != relativeLayout) {
//
//            OpeningStartAnimation openingStartAnimation = new OpeningStartAnimation(this);
//            openingStartAnimation.setImage(R.drawable.ic_color_camera);
//            openingStartAnimation.show(relativeLayout);
//
//            relativeLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(FolderActivity.this, MainPicActivity.class));
//                }
//            });
//        }
//    }

//    @Override
//    public void onAsyncTimer() {
//        // MyDialog myDialog = new MyDialog(activity);
//        ToastUtils.s(FolderActivity.this, "我是测试的数据，时间到");
//    }

    // 蒙层
    public void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.custom_dialog);
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
        lp.width = (int) (display.getWidth()); // 设置宽度
        lp.height = (int) (display.getHeight());
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
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
    }

    public void openNight(View view) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
// AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
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

    private PosterDialog posterDialog;

    public void dialog2(View view) {
        PosterDialog.PosterBuilder posterBuilder = new PosterDialog.PosterBuilder(FolderActivity.this, "https://ae01.alicdn.com/kf/U6de089ce45ff468a8f06c50e19ad7379N.jpg");
        posterDialog = posterBuilder.setOnPosterSelectListener(new PosterDialog.PosterBuilder.setOnPosterSelectListener() {
            @Override
            public void cancelClick() {
                posterDialog.dismiss();
            }

            @Override
            public void posterJump() {
                ToastUtils.s(FolderActivity.this, "点击提交");
            }
        }).create();
        posterDialog.show();
    }
}
