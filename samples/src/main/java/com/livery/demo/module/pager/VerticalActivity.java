package com.livery.demo.module.pager;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.livery.demo.R;
import com.livery.demo.module.pager.adapter.NavigationPagerAdapter;

import com.sunsta.livery.engine.NavigationController;
import com.sunsta.livery.engine.PageNavigationView;
import com.sunsta.livery.listener.OnTabItemSelectedListener;

import static com.livery.demo.R.id.tab;

/**
 * Created by mjj on 2017/8/3
 */
public class VerticalActivity extends AppCompatActivity {

    int[] testColors = {0xFF455A64, 0xFF00796B, 0xFF795548, 0xFF5B4947, 0xFFF57C00};

    NavigationController mNavigationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity_vertical);

        PageNavigationView pageBottomTabLayout = findViewById(tab);

        mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.pager_ic_ondemand_video_black_24dp, "Movies & TV", testColors[0])
                .addItem(R.drawable.pager_ic_audiotrack_black_24dp, "Music", testColors[1])
                .addItem(R.drawable.pager_ic_book_black_24dp, "Books", testColors[2])
                .addItem(R.drawable.pager_ic_news_black_24dp, "Newsstand", testColors[3])
                .enableVerticalLayout()//使用垂直布局
                .build();

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new NavigationPagerAdapter(getSupportFragmentManager(), mNavigationController.getItemCount()));

        //自动适配ViewPager页面切换
        mNavigationController.setupWithViewPager(viewPager);

        //也可以设置Item选中事件的监听
        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Log.i("asd", "selected: " + index + " old: " + old);
            }

            @Override
            public void onRepeat(int index) {
                Log.i("asd", "onRepeat selected: " + index);
            }
        });

        //设置消息圆点
        mNavigationController.setMessageNumber(0, 8);
        mNavigationController.setHasMessage(3, true);
    }
}
