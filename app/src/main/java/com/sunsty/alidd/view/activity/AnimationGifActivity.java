package com.sunsty.alidd.view.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ali.take.LAImageLoader;
import com.ali.view.dd.INATabLayout;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.sunsty.alidd.R;
import com.sunsty.alidd.model.adapter.FragmentAdapter;
import com.sunsty.alidd.view.fragment.Fragment1;
import com.sunsty.alidd.view.fragment.Fragment2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnimationGifActivity extends AppCompatActivity {
    private ImageView iv1, iv2, iv3, iv4, imageView;
    private ViewPager aliViewPager, natureViewPager;
    private List<Fragment> aliFragments = new ArrayList<>();
    private List<Fragment> natureFragments = new ArrayList<>();
    private String[] strings = new String[]{"孙顺涛", "凤鸣九天", "sunst", "Get", "Post", "Object"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LaUi.getInstance().stopLoadGif();
        setContentView(R.layout.activity_animation_gif);

        imageView = findViewById(R.id.ivImageView);

        iv1 = findViewById(R.id.iv1);
        iv2 = findViewById(R.id.iv2);
        iv3 = findViewById(R.id.iv3);
        iv4 = findViewById(R.id.iv4);
        aliViewPager = findViewById(R.id.aliViewPager);
        natureViewPager = findViewById(R.id.natureViewPager);

        Glide.with(this).load(R.drawable.drawable_gif_home).into(iv1);
        Glide.with(this).load(R.drawable.drawable_2222).into(iv2);
        Glide.with(this).load(R.drawable.drawable_gif3).into(iv3);
        Glide.with(this).load(R.drawable.drawable_gif_me).into(iv4);

        //获取背景，并将其强转成AnimationDrawable
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();

        if (animationDrawable != null) {
            animationDrawable.stop();
            //判断是否在运行
            if (!animationDrawable.isRunning()) {
                //开启帧动画
                animationDrawable.start();
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationDrawable != null) {
                    animationDrawable.stop();
                    //判断是否在运行
                    if (!animationDrawable.isRunning()) {
                        //开启帧动画
                        animationDrawable.start();
                    }
                }
            }
        });


        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationDrawable != null) {
                    animationDrawable.setVisible(true, true);//设置当前动画的第一帧，然后停止，可能联想之类的手机不支持
//                    animationDrawable.selectDrawable(0);      //选择当前动画的第一帧，然后停止
                    animationDrawable.stop();
//                imageView.clearAnimation();
                }
            }
        });

        initViewPager();

        initNatureTabLayout();

    }

    /**
     * 方案一：原生设置
     */
    private void initNatureTabLayout() {
        TabLayout natureTableLayout = findViewById(R.id.natureTableLayout);
        natureFragments.add(new Fragment1());
        natureFragments.add(new Fragment2());
        natureFragments.add(new Fragment1());
        natureFragments.add(new Fragment2());
        natureFragments.add(new Fragment1());
        natureFragments.add(new Fragment2());

        NatureAdapter fragmentAdater = new NatureAdapter(getSupportFragmentManager());
        natureViewPager.setAdapter(fragmentAdater);
//        natureViewPager.setOffscreenPageLimit(4);
        natureTableLayout.setupWithViewPager(natureViewPager);
        setIndicator(natureTableLayout, 0, 0);


        natureViewPager.setOffscreenPageLimit(7);

        natureTableLayout.addTab(natureTableLayout.newTab().setText("个性推荐").setIcon(R.drawable.drawable_gif2));
        natureTableLayout.addTab(natureTableLayout.newTab().setText("歌单"));
        natureTableLayout.addTab(natureTableLayout.newTab().setText("主播电台"));
        natureTableLayout.addTab(natureTableLayout.newTab().setText("排行榜"));


        /**设置natureTableLayout全部不选中*/
//        for (int i = 0; i < titles.size(); i++) {
//            natureTableLayout.addTab(natureTableLayout.newTab(), false);
//        }


        /**覆盖第一个tab显示*/
//        natureTableLayout.setSelectedTabIndicator(R.drawable.ailli_drawable_indicator);
        Objects.requireNonNull(natureTableLayout.getTabAt(0)).setCustomView(getview("热点", R.drawable.drawable_gif2));


        /**监听器*/
        natureTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("TAG", "BackMainActivity=====onTabSelected:" + tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("TAG", "BackMainActivity=====onTabUnselected:" + tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("TAG", "BackMainActivity=====onTabReselected:" + tab.getText());
            }
        });

        natureViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("TAG", "BackMainActivity=====onPageSelected: position=" + position);
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

        LAImageLoader.getInstance().loadImage(this, icon, iv);
        tv.setText(title);
        iv.setImageResource(icon);
        return view;
    }

    /**
     * 方案二：ali系列TabLayout
     */
    private void initViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("孙顺涛");
        titles.add("李凤鸣");
        titles.add("中国");
        titles.add("成都");
        titles.add("四川");
        titles.add("我爱你");
        titles.add("永远");
        titles.add("真不知道写啥了");
        for (int i = 0; i < titles.size(); i++) {
            if (i % 2 == 0) {
                aliFragments.add(new Fragment2());
            } else {
                aliFragments.add(new Fragment1());
            }
        }
        FragmentAdapter adatper = new FragmentAdapter(getSupportFragmentManager(), aliFragments, titles);
        aliViewPager.setAdapter(adatper);
        aliViewPager.setOffscreenPageLimit(4);
        //将TabLayout和ViewPager关联起来。
        INATabLayout tabLayout = findViewById(R.id.anTablayout);
        //给TabLayout设置适配器
        tabLayout.setupWithViewPager(aliViewPager);
        /**默认选中position =2的数据*/
        tabLayout.getTabAt(2).select();

        /**
         * 初始全部选中
         * */
//        tabLayout.setSelected(true);


        /**监听器*/
        tabLayout.addOnTabSelectedListener(new INATabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(INATabLayout.Tab tab) {
                Log.d("TAG", "BackMainActivity=====onTabSelectedx:" + tab.getText());

            }

            @Override
            public void onTabUnselected(INATabLayout.Tab tab) {
                Log.d("TAG", "BackMainActivity=====onTabUnselectedx:" + tab.getText());

            }

            @Override
            public void onTabReselected(INATabLayout.Tab tab) {
                Log.d("TAG", "BackMainActivity=====onTabReselectedx:" + tab.getText());
            }
        });
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        try {
            Field tabStrip = tabs.getClass().getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout llTab = null;
            llTab = (LinearLayout) tabStrip.get(tabs);
            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void shiping(View view) {
        Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, TranslucentActivity.class));
    }

    public class NatureAdapter extends FragmentPagerAdapter {
        public NatureAdapter(FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return natureFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return natureFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }
}