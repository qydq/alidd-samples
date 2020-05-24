package com.livery.demo.module.pic;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.livery.demo.R;
import com.sunsta.bear.config.PictureConfig;
import com.sunsta.bear.config.PictureMimeType;
import com.sunsta.livery.tools.PictureFileUtils;

public class PhotoFragmentActivity extends AppCompatActivity {
    private PhotoFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pic_activity_photo_fragment);
        // 在部分低端手机，调用单独拍照时内存不足时会导致activity被回收，所以不重复创建fragment
        if (savedInstanceState == null) {
            // 添加显示第一个fragment
            fragment = new PhotoFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.tab_content, fragment,
                    PictureConfig.EXTRA_FC_TAG).show(fragment)
                    .commit();
        } else {
            fragment = (PhotoFragment) getSupportFragmentManager()
                    .findFragmentByTag(PictureConfig.EXTRA_FC_TAG);
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
