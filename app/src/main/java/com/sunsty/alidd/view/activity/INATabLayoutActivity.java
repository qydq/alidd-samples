package com.sunsty.alidd.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ali.take.LAUi;
import com.ali.take.LaLog;
import com.ali.view.ParallaxActivity;
import com.ali.view.dd.INATabLayout;
import com.google.android.material.tabs.TabLayout;
import com.sunsty.alidd.R;
import com.sunsty.alidd.model.adapter.FragmentAdapter;
import com.sunsty.alidd.view.fragment.Fragment1;
import com.sunsty.alidd.view.fragment.Fragment2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class INATabLayoutActivity extends ParallaxActivity {
    private ViewPager aliViewPager, originalViewPager;
    private List<Fragment> aliFragments = new ArrayList<>();
    private List<Fragment> originalFragments = new ArrayList<>();

    private String[] originalTabArray = new String[]{"coverFirst", "coverSecond", "凤鸣九天的凤", "sunst", "Get", "Post", "Object", "星垂四野"};

    @Override
    public void initView() {
        setContentView(R.layout.activity_inatablayout);
        aliViewPager = findViewById(R.id.aliViewPager);
        originalViewPager = findViewById(R.id.originalViewPager);
        initViewPager();
        initNatureTabLayout();
    }

    /**
     * ：alidd系列INATabLayout
     * Author ：sunst
     * link : https://zhihu.com/people/qydq
     */
    private void initViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("精选");
        titles.add("热点");
        titles.add("凤鸣喜欢的");
        titles.add("剧集");
        titles.add("sunst");
        titles.add("电影");
        titles.add("material");
        titles.add("综艺");
        titles.add("高清");
        titles.add("四川");
        titles.add("动漫");
        titles.add("直播");
        titles.add("漫画");
        titles.add("体育");
        for (int i = 0; i < titles.size(); i++) {
            Fragment1 fragment1 = new Fragment1();
            Bundle sendBundle = new Bundle();
            sendBundle.putString("content", "alidd支持INATabLayout:position=" + i);
            fragment1.setArguments(sendBundle);
            aliFragments.add(fragment1);
        }
        FragmentAdapter adatper = new FragmentAdapter(getSupportFragmentManager(), aliFragments, titles);
        aliViewPager.setAdapter(adatper);
        aliViewPager.setOffscreenPageLimit(4);
        INATabLayout tabLayout = findViewById(R.id.anTablayout);
//        tabLayout.settabDisplayNum(6);
        /*
         *  给INATabLayout设置适配器，将TabLayout和ViewPager关联起来。
         * */
        tabLayout.setupWithViewPager(aliViewPager);
        /*
         *  默认选中position =2的数据（可选）
         * */
        Objects.requireNonNull(tabLayout.getTabAt(2)).select();
        /*
         * 初始全部选中（可选）
         * */
//        tabLayout.setSelected(true);
        /*
         *  监听器(可选）
         * */
        tabLayout.addOnTabSelectedListener(new INATabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(INATabLayout.Tab tab) {
                LaLog.d(TAG + "=====onTabSelectedx:" + tab.getText());
            }

            @Override
            public void onTabUnselected(INATabLayout.Tab tab) {
                LaLog.d(TAG + "=====onTabUnselectedx:" + tab.getText());
            }

            @Override
            public void onTabReselected(INATabLayout.Tab tab) {
                LaLog.d(TAG + "=====onTabReselectedx:" + tab.getText());
            }
        });
    }

    /**
     * Google原生TabLayout设置
     * Author ：sunst
     * link : https://zhihu.com/people/qydq
     */
    private void initNatureTabLayout() {
        TabLayout natureTableLayout = findViewById(R.id.natureTableLayout);
        for (int i = 0; i < originalTabArray.length; i++) {
            Fragment2 fragment2 = new Fragment2();
            Bundle sendBundle = new Bundle();
            sendBundle.putString("content", "Google原生TabLayout:position=" + i);
            fragment2.setArguments(sendBundle);
            originalFragments.add(fragment2);
        }

        OriginalAdapter fragmentAdater = new OriginalAdapter(getSupportFragmentManager());
        originalViewPager.setAdapter(fragmentAdater);
//        originalViewPager.setOffscreenPageLimit(4);
        natureTableLayout.setupWithViewPager(originalViewPager);
        LAUi.getInstance().setIndicator(natureTableLayout, 0, 0);

        originalViewPager.setOffscreenPageLimit(7);

        natureTableLayout.addTab(natureTableLayout.newTab().setText("个性推荐").setIcon(R.drawable.ic_drawable_share_wxcircle));
        natureTableLayout.addTab(natureTableLayout.newTab().setText("歌单"));
        natureTableLayout.addTab(natureTableLayout.newTab().setText("主播电台"));
        natureTableLayout.addTab(natureTableLayout.newTab().setText("排行榜"));


        /*
         *  设置natureTableLayout全部不选中（可选）
         * */
//        for (int i = 0; i < titles.size(); i++) {
//            natureTableLayout.addTab(natureTableLayout.newTab(), false);
//        }

        /*
         *  覆盖第一个tab显示热点两个字（可选）
         * */
//        natureTableLayout.setSelectedTabIndicator(R.drawable.ailli_drawable_indicator);
        Objects.requireNonNull(natureTableLayout.getTabAt(0)).setCustomView(getview("热点", R.drawable.drawable_gif4));
        Objects.requireNonNull(natureTableLayout.getTabAt(1)).setCustomView(getview("成都地铁覆盖", R.drawable.ic_drawable_copy_fav));

        /*
         *  监听器(可选）
         * */
        natureTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LaLog.d(TAG + "=====onTabSelected:" + tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LaLog.d(TAG + "=====onTabUnselected:" + tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                LaLog.d(TAG + "=====onTabReselected:" + tab.getText());
            }
        });

        originalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LaLog.d(TAG + "=====onTabUnselected: position = " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private View getview(String title, int icon) {
        View view = LayoutInflater.from(this).inflate(R.layout.view_item_normal_listview, null);
        TextView tv = view.findViewById(R.id.tvTitile);
        ImageView iv = view.findViewById(R.id.iv_choose);

//        LAImageLoader.getInstance().loadImage(this, icon, iv);
        tv.setText(title);
//        iv.setImageResource(icon);
        iv.setBackgroundResource(icon);
        return view;
    }

    /**
     * 原生最简单的适配器
     * Author ：sunst
     * link : https://zhihu.com/people/qydq
     */
    public class OriginalAdapter extends FragmentPagerAdapter {
        OriginalAdapter(FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return originalFragments.size();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return originalFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return originalTabArray[position];
        }
    }
}