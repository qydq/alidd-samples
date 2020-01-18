package com.sunsty.alidd.view.activity;

import android.Manifest;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ali.view.ParallaxActivity;
import com.sunsty.alidd.R;
import com.sunsty.alidd.take.EasyPermission;

import java.util.List;

public class EsayPermissionActivity extends ParallaxActivity implements EasyPermission.PermissionCallback {

    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private boolean isHavePermission = true;
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission(){
        if(EasyPermission.hasPermissions(this, permissions)){
            isHavePermission = true;
        }else{
            EasyPermission.with(this)
                    .rationale("打开我的权限")
                    .addRequestCode(REQUEST_PERMISS)
                    .permissions(permissions)
                    .request();
        }
    }

    private final int REQUEST_CODE = 1;
    private final int REQUEST_PERMISS = 2;
    @Override
    public void initView() {
        setContentView(R.layout.activity_audio);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
        }
    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {
        isHavePermission = true;
    }

    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {
        showToast("没有相机权限");
    }
}
