package com.sunsty.alidd.view.activity.picture;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ali.module.lib.config.PictureConfig;
import com.ali.module.lib.config.PictureMimeType;
import com.ali.module.lib.permissions.PermissionChecker;
import com.ali.module.lib.tools.PictureFileUtils;
import com.sunsty.alidd.R;

public class PhotoFragmentActivity extends AppCompatActivity {
    private PhotoFragment fragment;
    public final static String FC_TAG = "picture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simp);
        // 在部分低端手机，调用单独拍照时内存不足时会导致activity被回收，所以不重复创建fragment
        if (savedInstanceState == null) {
            // 添加显示第一个fragment
            fragment = new PhotoFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.tab_content, fragment, FC_TAG).show(fragment).commit();
        } else {
            fragment = (PhotoFragment) getSupportFragmentManager().findFragmentByTag(FC_TAG);
        }
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限

        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PictureFileUtils.deleteCacheDirFile(PhotoFragmentActivity.this, PictureMimeType.ofImage());
        } else {
            PermissionChecker.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE:
                // 存储权限
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        PictureFileUtils.deleteCacheDirFile(PhotoFragmentActivity.this, PictureMimeType.ofImage());
                    } else {
                        Toast.makeText(PhotoFragmentActivity.this,
                                getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
