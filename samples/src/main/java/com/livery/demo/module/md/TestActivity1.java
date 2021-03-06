package com.livery.demo.module.md;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.livery.demo.view.fragment.Fragment1;
import com.sunsta.bear.faster.LAUi;
import com.sunsta.bear.listener.AppBarStateChangeListener;
import com.sunsta.bear.view.ParallaxActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.livery.demo.R;
import com.livery.demo.view.fragment.MainFragment;
import com.livery.demo.view.fragment.OtherFragment;
import com.livery.demo.view.fragment.SecondFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Alidd - Samples入口;google原生RecyclerView布局。
 * </p>
 * Created by Sunst on 2017/7/22.
 */
public class TestActivity1 extends ParallaxActivity {
    private String[] strings = new String[]{"material ux", "aN情景s", "逻辑相关", "其它"};
    private List<Fragment> nativeFragments = new ArrayList<>();
    private ViewPager nativeViewPager;

    protected Toolbar mToolbar;
    protected AppBarLayout appBarLayout;
    protected View toolbarInner;
    protected ActionBar mActionBar;

    protected boolean displayHomeAsUpEnabled() {
        return false;
    }

    @Override
    public void initView() {
        setContentView(R.layout.md_activity_test1);
        fitStatusBar(false, true);//设置状态栏颜色为白色，lightMode=false为默认白色，lightMode=true为黑色
        mToolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.appBarLayout);
        toolbarInner = findViewById(R.id.toolbarInner);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (displayHomeAsUpEnabled()) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        appBarListener();

        nativeViewPager = findViewById(R.id.natureViewPager);
        TabLayout natureTableLayout = findViewById(R.id.natureTableLayout);
        toolbarInner.findViewById(R.id.iv).setOnClickListener(v -> showToast("1.前两个模块属于alidd情景系列；2.后两个模块属于非alidd情景系列."));

        nativeFragments.add(new MainFragment());
        nativeFragments.add(new SecondFragment());
        nativeFragments.add(new Fragment1());
        nativeFragments.add(new OtherFragment());
        NatureAdapter fragmentAdater = new NatureAdapter(getSupportFragmentManager());
        nativeViewPager.setAdapter(fragmentAdater);
//        natureViewPager.setOffscreenPageLimit(4);
        natureTableLayout.setupWithViewPager(nativeViewPager);
        LAUi.getInstance().setIndicator(natureTableLayout, 0, 0);
        nativeViewPager.setOffscreenPageLimit(7);
    }

    public class NatureAdapter extends FragmentPagerAdapter {
        public NatureAdapter(FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return nativeFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return nativeFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }

    @Override
    public void onBackPressed() {
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.aim_common_left_in, R.anim.aim_common_right_out);
    }

    private boolean barTop = false;

    private void appBarListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                /*      if (state === State.COLLAPSED) {
                          //下面的方法会出现监听不到的情况，所以这里变成折叠状态，最好再设置一次透明度
                          blankView.visibility = View.VISIBLE
                      }else{
                          blankView.visibility = View.GONE
                      }*/
                if (state == AppBarStateChangeListener.State.COLLAPSED) {
                    barTop = true;
                    /*滑动到上面了*/
//                    appBarLayout.setBackgroundColor(getResources().getColor(R.color.ColorWhite));
                    fitStatusBar(false, true);//设置状态栏颜色为白色，lightMode=false为默认白色，lightMode=true为黑色
                } else {
                    barTop = false;
                    /*没有展开了*/
//                    appBarLayout.setBackgroundColor(getResources().getColor(R.color.ColorBlack));
                    fitStatusBar(true, true);//设置状态栏颜色为白色，lightMode=false为默认白色，lightMode=true为黑色
                }
            }
        });
    }
}