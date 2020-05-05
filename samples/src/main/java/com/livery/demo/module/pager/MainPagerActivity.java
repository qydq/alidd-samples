package com.livery.demo.module.pager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.livery.demo.R;

/*
 * (1).sunst提供：[an专栏]情景系列，导航栏navigation 情景系列示例pager
 *     如果有帮助到你，真诚的邀请你，关注知乎<a href="https://zhihu.com/people/qydq">Bgwan</a>
 *     2020年05月：目前需要集齐1000+个关注者，感谢🙏
 * */
public class MainPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_pager_activity);
    }

    public void toMaterialdesign(View view) {
        startActivity(new Intent(this, MaterialDesignActivity.class));
    }

    public void toCustom(View view) {
        startActivity(new Intent(this, CustomActivity.class));
    }

    public void toCustom2(View view) {
        startActivity(new Intent(this, Custom2Activity.class));
    }

    public void toBehavior(View view) {
        startActivity(new Intent(this, BehaviorActivity.class));
    }

    public void toHide(View view) {
        startActivity(new Intent(this, HideActivity.class));
    }

    public void toSpecial(View view) {
        startActivity(new Intent(this, SpecialActivity.class));
    }

    public void toVertical(View view) {
        startActivity(new Intent(this, VerticalActivity.class));
    }

    public void toCsutomVertical(View view) {
        startActivity(new Intent(this, VerticalCustomActivity.class));
    }

    public void toTestController(View view) {
        startActivity(new Intent(this, TestControllerActivity.class));
    }

    public void navigationArchitectureComponent(View view) {
        startActivity(new Intent(this, NavigationComponentActivity.class));
    }
}
