package com.sunsty.alidd.view.activity;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ali.take.LAUi;
import com.ali.view.ParallaxActivity;
import com.ali.view.recyclerview.recyclerview.AppBarStateChangeListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.sunsty.alidd.R;
import com.sunsty.alidd.view.fragment.LogicalFragment;
import com.sunsty.alidd.view.fragment.MaterialUxFragment;
import com.sunsty.alidd.view.fragment.OtherFragment;
import com.sunsty.alidd.view.fragment.SceneFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Alidd - Samples入口;google原生RecyclerView布局。
 * </p>
 * Created by Sunst on 2017/7/22.
 */
public class MainActivity extends ParallaxActivity {
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
        setContentView(R.layout.activity_main);
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

        nativeFragments.add(new MaterialUxFragment());
        nativeFragments.add(new SceneFragment());
        nativeFragments.add(new LogicalFragment());
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
                } else {
                    barTop = false;
                    /*没有展开了*/
//                    appBarLayout.setBackgroundColor(getResources().getColor(R.color.ColorBlack));
                }
            }
        });
    }
}