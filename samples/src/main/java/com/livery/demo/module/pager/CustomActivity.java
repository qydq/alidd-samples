package com.livery.demo.module.pager;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.livery.demo.R;
import com.livery.demo.module.pager.adapter.NavigationPagerAdapter;

import com.sunsta.livery.engine.NavigationController;
import com.sunsta.livery.engine.PageNavigationView;
import com.sunsta.livery.item.BaseTabItem;
import com.sunsta.livery.item.NormalItemView;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity_horizontal);

        PageNavigationView tab = findViewById(R.id.tab);

        NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.pager_ic_restore_gray_24dp, R.drawable.pager_ic_restore_teal_24dp, "Recents"))
                .addItem(newItem(R.drawable.pager_ic_favorite_gray_24dp, R.drawable.pager_ic_favorite_teal_24dp, "Favorites"))
                .addItem(newItem(R.drawable.pager_ic_nearby_gray_24dp, R.drawable.pager_ic_nearby_teal_24dp, "Nearby"))
                .build();

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new NavigationPagerAdapter(getSupportFragmentManager(), navigationController.getItemCount()));

        //自动适配ViewPager页面切换
        navigationController.setupWithViewPager(viewPager);

        //设置消息数
        navigationController.setMessageNumber(1, 8);

        //设置显示小圆点
        navigationController.setHasMessage(0, true);
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF009688);
        return normalItemView;
    }
}
