package com.sunsty.alidd.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ali.model.entity.CropOptions;
import com.ali.model.entity.TResult;
import com.ali.presenter.net.JustNetClient;
import com.ali.take.photo.compress.CompressConfig;
import com.ali.take.photo.interfaces.TakePhoto;
import com.ali.view.activity.TakePhotoActivity;
import com.bumptech.glide.Glide;
import com.sunsty.alidd.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BackMainActivity extends TakePhotoActivity {
    private JustNetClient biniNetClient;
    private Button btnPhoto;
    private Button btnPhotos;
    private Button btnUpdate;
    private ImageView ivPhoto;
    private TextView textView;

    //动态权限
    String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    List<String> mPermissionList = new ArrayList<>();
    private final int mRequestCode = 100;//权限请求码

    //TakePhoto
    private TakePhoto takePhoto;
    private CropOptions cropOptions;    //裁剪参数
    private CompressConfig compressConfig;  //压缩参数
    private Uri imageUri;       //图片保存路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        if (Build.VERSION.SDK_INT >= 23) {  //6.0才用动态权限
            //申请相关权限
            initPermission();
        }
        initData();  //设置压缩、裁剪参数
        initView();
    }

    private void initData() {
        ////获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        //设置压缩参数
        compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig, false);    //设置为需要压缩

    }

    private void initPermission() {
        mPermissionList.clear();        //清空没有通过的权限
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }
        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        } else {
            //说明权限都已经通过，可以做你想做的事情去
        }
    }

    private void initView() {
        btnPhoto = findViewById(R.id.btnPhoto1);
        btnPhotos = findViewById(R.id.btnPhoto2);
        btnUpdate = findViewById(R.id.btnUpdate);
        ivPhoto = findViewById(R.id.ivPhoto);
        textView = findViewById(R.id.tvTitle);


        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("1111111", "btnPhoto");
                imageUri = getImageCropUri();
                //拍照并裁剪
//                takePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions);
                //仅仅拍照不裁剪
                takePhoto.onPickFromCapture(imageUri);
            }
        });

        btnPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("1111111", "btnPhotos");
                onClickk(getTakePhoto());
                imageUri = getImageCropUri();
//                //从相册中选取图片并裁剪
//                takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions);
//                //从相册中选取不裁剪
//                takePhoto.onPickFromGallery();
            }
        });

    }

    //从本地选取的第二种方法，可设置多张选择
    public void onClickk(TakePhoto takePhoto) {
        configCompress(takePhoto);
        takePhoto.onPickMultiple(3);   //3张图片
//        takePhoto.onPickFromGallery();//根据需求这里面放最大图片数 一张图片takePhoto.onPickFromGallery();
    }

    private void configCompress(TakePhoto takePhoto) {      //压缩配置
        int maxSize = Integer.parseInt("409600");//最大 压缩
        int width = Integer.parseInt("800");//宽
        int height = Integer.parseInt("800");//高
        CompressConfig config;
        config = new CompressConfig.Builder().setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(false)//拍照压缩后是否显示原图
                .create();
        takePhoto.onEnableCompress(config, false);//是否显示进度条
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Log.i("1111111", "takeSuccess : " + result.getImage().getOriginalPath());
//        Glide.with(this).load(new File(result.getImage().getOriginalPath())).into(ivPhoto);
        super.takeSuccess(result);
        String iconPath = result.getImage().getOriginalPath();
        //Toast显示图片路径
        Toast.makeText(this, "imagePath:" + iconPath, Toast.LENGTH_SHORT).show();
        //Google Glide库 用于加载图片资源
        Glide.with(this).load(iconPath).into(ivPhoto);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        Log.i("1111111", "takeFail : " + msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
//                showPermissionDialog();//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
            } else {
                //全部权限通过，可以进行下一步操作。。。

            }
        }
    }

    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }
}
