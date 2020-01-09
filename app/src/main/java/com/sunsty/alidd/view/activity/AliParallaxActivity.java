package com.sunsty.alidd.view.activity;

import android.content.Intent;
import android.widget.TextView;

import com.ali.take.LATextView;
import com.ali.view.ParallaxActivity;
import com.ali.view.activity.AliWebViewActivity;
import com.sunsty.alidd.R;

public class AliParallaxActivity extends ParallaxActivity {
    @Override
    public void initView() {
        setContentView(R.layout.activity_parallax);
        TextView tvMark = findViewById(R.id.tvMark);
        tvMark.setOnClickListener(v -> gotoIntorduce());
        LATextView.setUnderLineAndColor02(tvMark, "click：", "详细使用", R.color.ColorGreenyellow);
    }

    private void gotoIntorduce() {
        Intent intent = new Intent(this, AliWebViewActivity.class);
        intent.putExtra("url", "https://zhuanlan.zhihu.com/p/26089356");
        intent.putExtra("title", "Github官方alidd框架");
        startActivity(intent);
    }
}