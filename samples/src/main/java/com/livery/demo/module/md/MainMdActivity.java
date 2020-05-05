package com.livery.demo.module.md;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.livery.demo.R;
import com.livery.demo.module.md.utils.ViewUtils;
/*
 * (1).sunst提供：[an专栏]情景系列，material design 情景系列示例md
 *     如果有帮助到你，真诚的邀请你，关注知乎<a href="https://zhihu.com/people/qydq">Bgwan</a>
 *     2020年05月：目前需要集齐1000+个关注者，感谢🙏
 * */
public class MainMdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_md_activity);
//        ViewUtils.setImmersionStateMode(this);
    }

    public void style1(View view) {
        startActivity(new Intent(MainMdActivity.this, TestActivity1.class));
    }

    public void style2(View view) {
        startActivity(new Intent(MainMdActivity.this, TestActivity2.class));
    }

    public void style3(View view) {
        startActivity(new Intent(MainMdActivity.this, TestActivity3.class));

    }

    public void style4(View view) {
        startActivity(new Intent(MainMdActivity.this, TestActivity4.class));
    }

    public void style5(View view) {
        startActivity(new Intent(MainMdActivity.this, TestActivity5.class));
    }
}
