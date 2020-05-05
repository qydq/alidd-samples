package com.livery.demo.module.pager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.livery.demo.R;
import com.livery.demo.module.pager.adapter.NavigationPagerAdapter;
import com.livery.demo.module.pager.custom.OnlyIconItemView;
import com.livery.demo.module.pager.custom.TestRepeatTab;

import com.sunsta.livery.engine.NavigationController;
import com.sunsta.livery.engine.PageNavigationView;
import com.sunsta.livery.item.BaseTabItem;

public class Custom2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity_horizontal);

        PageNavigationView tab = findViewById(R.id.tab);

        NavigationController navigationController = tab.custom()
                .addItem(newItem_test(R.drawable.pager_ic_restore_gray_24dp, R.drawable.pager_ic_restore_teal_24dp))
                .addItem(newItem(R.drawable.pager_ic_favorite_gray_24dp, R.drawable.pager_ic_favorite_teal_24dp))
                .addItem(newItem(R.drawable.pager_ic_nearby_gray_24dp, R.drawable.pager_ic_nearby_teal_24dp))
                .build();

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new NavigationPagerAdapter(getSupportFragmentManager(), navigationController.getItemCount()));

        //自动适配ViewPager页面切换
        navigationController.setupWithViewPager(viewPager);
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable) {
        OnlyIconItemView onlyIconItemView = new OnlyIconItemView(this);
        onlyIconItemView.initialize(drawable, checkedDrawable);
        return onlyIconItemView;
    }

    //创建一个Item(测试重复点击的方法)
    private BaseTabItem newItem_test(int drawable, int checkedDrawable) {
        TestRepeatTab testRepeatTab = new TestRepeatTab(this);
        testRepeatTab.initialize(drawable, checkedDrawable);
        return testRepeatTab;
    }
}
