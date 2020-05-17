package com.livery.demo;

import androidx.viewpager.widget.ViewPager;

import com.livery.demo.model.adapter.MainAdapter;
import com.sunsta.bear.faster.LaLog;
import com.sunsta.bear.view.ParallaxActivity;
import com.sunsta.livery.engine.MaterialMode;
import com.sunsta.livery.engine.NavigationController;
import com.sunsta.livery.engine.PageNavigationView;
import com.sunsta.livery.listener.OnTabItemSelectedListener;

import static com.livery.demo.R.id.tab;


/*
 * (1).sunst提供：[an专栏]情景系列，livery框架示例主入口
 *     如果有帮助到你，真诚的邀请你，关注知乎<a href="https://zhihu.com/people/qydq">Bgwan</a>
 *     2020年05月：目前需要集齐1000+个关注者，感谢🙏
 * */
public class MainActivity extends ParallaxActivity {
    int[] navigationColors = {0xFF455A64, 0xFF00796B, 0xFF795548, 0xFF5B4947, 0xFFF57C00};
    //    int[] navigationColors = {0xFF009688, 0xFF009688, 0xFF009688, 0xFF009688, 0xFF009688};
    NavigationController mNavigationController;

    @Override
    public void initView() {
        setContentView(R.layout.activity_main_main);
        //标准注释1：设置状态栏颜色为白色，lightMode=false为默认白色，lightMode=true为黑色
        fitStatusBar(false, true);

        PageNavigationView pageBottomTabLayout = findViewById(tab);
        ViewPager viewPager = findViewById(R.id.viewPager);

        mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.pager_ic_book_black_24dp, "小红", navigationColors[0])
                .addItem(R.drawable.pager_ic_audiotrack_black_24dp, "livery", navigationColors[1])
                .addItem(R.drawable.pager_ic_ondemand_video_black_24dp, "Movies & TV", navigationColors[2])
                .addItem(R.drawable.pager_ic_news_black_24dp, "About", navigationColors[3])
                .setDefaultColor(0x89FFFFFF)//未选中状态的颜色
                .setMode(MaterialMode.CHANGE_BACKGROUND_COLOR | MaterialMode.HIDE_TEXT)//这里可以设置样式模式，总共可以组合出4种效果
                .build();

        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), mNavigationController.getItemCount()));

        //标准注释2：自动适配ViewPager页面切换
        mNavigationController.setupWithViewPager(viewPager);

        //标准注释3：设置Item选中事件的监听
        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                LaLog.i(TAG, "selected: " + index + " old: " + old);
                if (index == 1) {
                    fitStatusBar(true, true);
                } else {
                    fitStatusBar(false, true);
                }
            }

            @Override
            public void onRepeat(int index) {
                LaLog.i(TAG, "onRepeat selected: " + index);
            }
        });

        //设置消息圆点
//        mNavigationController.setMessageNumber(0, 69);
//        mNavigationController.setHasMessage(3, true);
        //禁止右滑
        setBackEnable(false);
    }

    //复写下面两个方法，退出时动画。
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.aim_common_left_in,
                R.anim.aim_common_right_out);
    }

    @Override
    public void onBackPressed() {
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            finish();
        }
    }
}