package com.sunsty.alidd.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ali.model.entity.CropOptions;
import com.ali.model.entity.TResult;
import com.ali.take.LaLog;
import com.ali.take.photo.compress.CompressConfig;
import com.ali.take.photo.interfaces.TakePhoto;
import com.ali.view.activity.AliTakePhotoActivity;
import com.bumptech.glide.Glide;
import com.sunsty.alidd.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BeforeSelectAlbumActivity extends AliTakePhotoActivity {
    private Button btnSelectAlbum;
    private Button btnCamera;

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


    private void btnCamera() {
        imageUri = getImageCropUri();
        LaLog.d("sunst888---btnCamera : imageUri=" + imageUri);
        //拍照并裁剪
//        takePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions);//ok
        //仅仅拍照不裁剪
        takePhoto.onPickFromCapture(imageUri);
    }

    private void btnSelectAlbum() {
        /*
         * 配置参数设置
         * */
        int maxSize = Integer.parseInt("409600");//最大 压缩
        int width = Integer.parseInt("800");//宽
        int height = Integer.parseInt("800");//高
        CompressConfig config = new CompressConfig.Builder().setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(false)//拍照压缩后是否显示原图
                .create();
        takePhoto.onEnableCompress(config, false);//是否显示进度条


        takePhoto.onPickMultiple(3);   //3张图片
//        takePhoto.onPickFromGallery();//根据需求这里面放最大图片数 一张图片takePhoto.onPickFromGallery();
        imageUri = getImageCropUri();
        LaLog.d("sunst888---btnSelectAlbum : imageUri=" + imageUri);
//                //从相册中选取图片并裁剪
//                takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions);
//                //从相册中选取不裁剪
//                takePhoto.onPickFromGallery();
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

    @Override
    public void takeSuccess(TResult result) {
        LaLog.d("sunst888---takeSuccess : " + result.getImage().getOriginalPath());
        String iconPath = result.getImage().getOriginalPath();
        //Toast显示图片路径
        Toast.makeText(this, "imagePath:" + iconPath, Toast.LENGTH_SHORT).show();
        Glide.with(this).load(iconPath).into(ivPhoto);
    }
// 图片压缩失败:null is compress failures picturePath:null
    @Override
    public void takeFail(TResult result, String msg) {
        LaLog.d("sunst888---takeFail : " + msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_before_album);
        if (Build.VERSION.SDK_INT >= 23) {  //6.0才用动态申请相关权限
            initPermission();
        }
        //设置压缩、裁剪参数

        ////获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
        //设置压缩参数
        compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig, false);    //设置为需要压缩

        btnSelectAlbum = (Button) findViewById(R.id.btnSelectAlbum);
        btnCamera = (Button) findViewById(R.id.btnCamera);

        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        textView = (TextView) findViewById(R.id.tvTitle);

        btnCamera.setOnClickListener(v -> btnCamera());
        btnSelectAlbum.setOnClickListener(v -> btnSelectAlbum());
    }


    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
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
}