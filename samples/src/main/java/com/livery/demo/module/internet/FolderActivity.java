package com.livery.demo.module.internet;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.livery.demo.R;
import com.livery.demo.module.md.BottomSheetBehaviorActivity;
import com.sunsta.bear.engine.picker.CityPickerView;
import com.sunsta.bear.faster.EasyPermission;
import com.sunsta.bear.faster.FileUtils;
import com.sunsta.bear.faster.LADialog;
import com.sunsta.bear.faster.LAStorageFile;
import com.sunsta.bear.faster.LAUi;
import com.sunsta.bear.faster.LaLog;
import com.sunsta.bear.faster.ToastUtils;
import com.sunsta.bear.layout.INABarrageView;
import com.sunsta.bear.layout.INAStartAnimationView;
import com.sunsta.bear.presenter.net.InternetClient;
import com.sunsta.bear.view.AliActivity;

import java.io.File;
import java.util.List;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;
import static com.livery.demo.module.internet.OrignalRetrofitActivity.Http_Full_Download_Url;

public class FolderActivity extends AliActivity implements EasyPermission.PermissionCallback {
    private RelativeLayout relativeLayout;
    private ConstraintLayout constainitLayout;

    private TextView textView;
    private String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private final int REQUEST_PERMISS = 2;
    private INABarrageView inaBarrageView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        setContentView(R.layout.activity_folder);
        relativeLayout = findViewById(R.id.relativeLayout);
        constainitLayout = findViewById(R.id.constainitLayout);
        textView = findViewById(R.id.textView);

// setBadBackgroundLayout();

        INAStartAnimationView animationView = new INAStartAnimationView(this);
        animationView.setImage(R.drawable.ic_color_camera);
        animationView.show(relativeLayout);
        LaLog.d("软键盘状态----------" + LAUi.getInstance().keyboardIsActive(FolderActivity.this));

        launchTimer(2000);

        getInaBarlayout().setOnRightLlClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
            }
        });

        int milli = 10000;
    }

    private void startXXX(View view) {
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        ObjectAnimator tanslationX = new ObjectAnimator().ofFloat(view, "translationX", widthPixels - view.getWidth(), -widthPixels);
        tanslationX.setDuration(1000);
        tanslationX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        tanslationX.setInterpolator(new LinearInterpolator());
        tanslationX.start();
    }

    @Override
    public void onLaunchedTimer() {
        showToast("我是测试的数据，时间到11111111");
    }

    //文件夹
    public void onClick1(View view) {
        String folder = FileUtils.getInstance().getPwdMovieFolder();
        File fouceTouchFile = LAStorageFile.INSTANCE.fouceTouchFile(folder, "sunst_test.mp4");
        if (fouceTouchFile.exists()) {
            LaLog.d("创建1-文件创建成功");
        } else {
            LaLog.d("创建1-foucheTouchFile不存在，使用getExternalFilesDir创建文件夹...");
            LaLog.d(fouceTouchFile.mkdirs() ? "创建1-新文件创建成功" : "新文件1创建失败");
            String getAbsolutePath1 = getExternalFilesDir(null).getAbsolutePath();
            createADirectory(getAbsolutePath1 + File.separator);//创建文件夹
        }
    }

    public void onClick2(View view) {
        String getAbsolutePath = getFilesDir().getAbsolutePath();
        String folder = FileUtils.getInstance().getPwdMovieFolder();

        File touchFile = new File(getAbsolutePath + folder, "test.txt");
        if (touchFile.exists()) {
            LaLog.d("创建2-文件创建成功");
        } else {
            LaLog.d("创建2-touchFile不存在，使用getFilesDir创建文件...");
            createAFile(getAbsolutePath);//创建文件
        }
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
        int result = FileUtils.createDir(filePath + "damon/file/tmp/test");
        showResult(result);
    }

    /**
     * 创建结果
     */
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

    /**
     * 打印信息，并且申请权限
     */
    public void onClick3(View view) {
        File fouceTouchFile = LAStorageFile.INSTANCE.fouceTouchFile(FileUtils.getInstance().getPwdMovieFolder(), "test.mp4");
        LaLog.d("验证1-\npath = " + fouceTouchFile.getPath() + "\nname：" + fouceTouchFile.getName() + "\nAbsPath:" + fouceTouchFile.getAbsolutePath() + "\n");
        if (fouceTouchFile.exists()) {
            LaLog.d("验证1-文件fouceTouchFile存在===");
        } else {
            LaLog.d("验证1-文件fouceTouchFile不存在===");
            File fouceFile = new File(fouceTouchFile.getAbsolutePath());
// File newFile = new File(FileUtils.getInstance().getPwdMovieFolder(), fouceTouchFile.getName());
// Make sure the Pictures&Movie directory exists.
            if (fouceFile.exists()) {
                LaLog.d("验证1-新文件已存在");
            } else {
                LaLog.d(fouceFile.mkdirs() ? "验证1-新文件创建成功" : "新文件1创建失败");
            }
        }

        String getAbsolutePath = getFilesDir().getAbsolutePath();
        String getPath = getFilesDir().getPath();
        String getName = getFilesDir().getName();
        LaLog.d("\n-------------------------------------------");
        LaLog.d("验证2-\npath =" + getPath + "\nname：" + getName + "\nAbsPath:" + getAbsolutePath + "\n");

        File external = getExternalFilesDir(null);
        String getAbsolutePath1 = external.getAbsolutePath();
        String getPath1 = external.getPath();
        String getName1 = external.getName();
        LaLog.d("\n-------------------------------------------");
        LaLog.d("验证3-\npath =" + getPath1 + "\nname：" + getName1 + "\nAbsPath:" + getAbsolutePath1 + "\n");

        requestPermission();
    }

    /**
     * standard ：an情景系列livery框架requestPermission打开权限
     * Author ：sunst
     * link : https://zhihu.com/people/qydq
     */
    private void requestPermission() {
// if (EasyPermission.hasPermissions(this, permissions)) {
// LaLog.d("requestPermission" + "已经有了权限");
// showToast("已经有了权限");
// } else {
// EasyPermission.with(this)
// .rationale("打开我的权限")
// .addRequestCode(REQUEST_PERMISS)
// .permissions(permissions)
// .request();
// }

        EasyPermission.with(this)
                .rationale("打开我的权限")
                .addRequestCode(REQUEST_PERMISS)
                .permissions(permissions)
                .request();
    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {
        showToast("给予了权限");
        LaLog.d("requestPermission" + "给予了权限");
    }

    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {
        showToast("没有相机权限");
        LaLog.d("requestPermission" + "没有相机权限");
    }

    public void closeNight(View view) {
// setDayTheme();
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
    }

    public void openNight(View view) {
// setNightTheme();
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
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
        LADialog.INSTANCE.attach(1, LADialog.STYLE.middle_pure_image, FolderActivity.this);
        LADialog.INSTANCE.setImageUrl("https://ae01.alicdn.com/kf/U6de089ce45ff468a8f06c50e19ad7379N.jpg");

        LADialog.INSTANCE.attach(2, LADialog.STYLE.middle_pure_image, FolderActivity.this);
        LADialog.INSTANCE.setImageUrl("https://gank.io/images/ce66aa74d78f49919085b2b2808ecc50");
        LADialog.INSTANCE.launch();

// MyDialog dialog = new MyDialog(MainActivity.this);
// dialog.setListData(null);
// dialog.show();
    }

    public void dialog2(View view) {
        ToastUtils.s(FolderActivity.this, "点击提交dialog2");

    }

    public void jump(View view) {
// startActivity(new Intent(MainActivity.this, TestActivity.class));
        startActivity(new Intent(FolderActivity.this, BottomSheetBehaviorActivity.class));
    }

    private View getRootView() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

    // 蒙层
    public void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.TransparentTheme);
        TextView textView = new TextView(this);
        dialog.setCancelable(true);
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

    public void download(View view) {
        downloadApk("wodexiaotuanzi.mp4", "/sunsta/more/");
    }

    private void downloadApk(String fileName, String pwdPath) {
        InternetClient.getInstance().downloadApkInService(Http_Full_Download_Url, fileName, pwdPath);
    }

    public void openSoft(View view) {
        openSoftKeyboard(FolderActivity.this, view);
    }
    /**
     * 打开软键盘通用
     * @param activity 上下文Activity
     */
    public void openSoftKeyboard(@NonNull Activity activity, View view) {
        LAUi.getInstance().openSoftKeyboard(FolderActivity.this, view);

    }

    public void closeSoft(View view) {
        LAUi.getInstance().hideSoftKeyboard(FolderActivity.this);
    }

    public void danMu(View view) {
        CityPickerView cityPickerView = new CityPickerView(this);
        cityPickerView.show();
    }
}