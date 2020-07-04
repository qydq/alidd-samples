package com.livery.demo.view.activity;

import android.content.Intent;
import android.widget.TextView;

import com.sunsta.bear.immersion.RichTextView;
import com.sunsta.bear.view.ParallaxActivity;
import com.sunsta.bear.view.activity.AliWebActivity;
import com.livery.demo.R;

public class AliParallaxActivity extends ParallaxActivity {
    @Override
    public void initView() {
        setContentView(R.layout.activity_parallax);
        TextView tvMark = findViewById(R.id.tvMark);
        tvMark.setOnClickListener(v -> gotoIntorduce());
        RichTextView.setUnderLineAndColor02(tvMark, "click：", "详细使用", R.color.ColorGreenyellow);
    }

    private void gotoIntorduce() {
        Intent intent = new Intent(this, AliWebActivity.class);
        intent.putExtra("url", "https://zhuanlan.zhihu.com/p/26089356");
        intent.putExtra("title", "Github官方alidd框架");
        startActivity(intent);
    }
}