package com.livery.demo.module.pager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.livery.demo.R;
import com.livery.demo.module.pager.adapter.NavigationPagerAdapter;
import com.livery.demo.module.pager.custom.SpecialTab;

import com.sunsta.livery.engine.NavigationController;
import com.sunsta.livery.engine.PageNavigationView;
import com.sunsta.livery.item.BaseTabItem;
import com.livery.demo.module.pager.custom.SpecialTabRound;

/**
 * Created by mjj on 2017/6/25
 */
public class SpecialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity_special);

        PageNavigationView tab = findViewById(R.id.tab);

        NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.pager_ic_restore_gray_24dp,R.drawable.pager_ic_restore_teal_24dp,"Recents"))
                .addItem(newItem(R.drawable.pager_ic_favorite_gray_24dp,R.drawable.pager_ic_favorite_teal_24dp,"Favorites"))
                .addItem(newRoundItem(R.drawable.pager_ic_nearby_gray_24dp,R.drawable.pager_ic_nearby_teal_24dp,"Nearby"))
                .addItem(newItem(R.drawable.pager_ic_favorite_gray_24dp,R.drawable.pager_ic_favorite_teal_24dp,"Favorites"))
                .addItem(newItem(R.drawable.pager_ic_restore_gray_24dp,R.drawable.pager_ic_restore_teal_24dp,"Recents"))
                .build();

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new NavigationPagerAdapter(getSupportFragmentManager(),navigationController.getItemCount()));

        //自动适配ViewPager页面切换
        navigationController.setupWithViewPager(viewPager);
    }

    /**
     * 正常tab
     */
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        SpecialTab mainTab = new SpecialTab(this);
        mainTab.initialize(drawable,checkedDrawable,text);
        mainTab.setTextDefaultColor(0xFF888888);
        mainTab.setTextCheckedColor(0xFF009688);
        return mainTab;
    }

    /**
     * 圆形tab
     */
    private BaseTabItem newRoundItem(int drawable, int checkedDrawable, String text){
        SpecialTabRound mainTab = new SpecialTabRound(this);
        mainTab.initialize(drawable,checkedDrawable,text);
        mainTab.setTextDefaultColor(0xFF888888);
        mainTab.setTextCheckedColor(0xFF009688);
        return mainTab;
    }

}
