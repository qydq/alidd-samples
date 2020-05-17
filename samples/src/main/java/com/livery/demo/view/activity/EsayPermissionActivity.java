package com.livery.demo.view.activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.sunsta.bear.faster.LATextView;
import com.sunsta.bear.view.ParallaxActivity;
import com.livery.demo.R;
import com.sunsta.bear.faster.EasyPermission;

import java.util.List;

public class EsayPermissionActivity extends ParallaxActivity implements EasyPermission.PermissionCallback {
    private TextView colortx;
    private boolean isHavePermission = true;

    private String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private final int REQUEST_CODE = 1;
    private final int REQUEST_PERMISS = 2;

    @Override
    public void initView() {
        setContentView(R.layout.activity_esaypermission);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
        }
        colortx = findViewById(R.id.colortx);
        changeTxtColor();
    }

    private void changeTxtColor() {
        LATextView.setUnderLineAndColor02(colortx, "《平台协议》", "《隐私政策》", R.color.ColorAliceblue);
        SpannableStringBuilder style = new SpannableStringBuilder();
        style.append("请确认已阅读并同意《平台协议》、《隐私政策》，未注册时将自动注册账号");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                showToast("点击了==平台协议");
            }
        };

        ClickableSpan clickableSpan02 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                showToast("点击了==隐私政策");
            }
        };

        style.setSpan(clickableSpan, 9, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(clickableSpan02, 16, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#3399FF"));
        ForegroundColorSpan foregroundColorSpan02 = new ForegroundColorSpan(Color.parseColor("#3399FF"));
        style.setSpan(foregroundColorSpan, 9, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(foregroundColorSpan02, 16, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        colortx.setMovementMethod(LinkMovementMethod.getInstance());
        colortx.setText(style);
    }

    /**
     * standard ：an情景系列livery框架requestPermission打开权限
     * Author ：sunst
     * link : https://zhihu.com/people/qydq
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        if (EasyPermission.hasPermissions(this, permissions)) {
            isHavePermission = true;
        } else {
            EasyPermission.with(this)
                    .rationale("打开我的权限")
                    .addRequestCode(REQUEST_PERMISS)
                    .permissions(permissions)
                    .request();
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
