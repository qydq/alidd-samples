package com.sunsty.alidd.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ali.take.LAUi;
import com.ali.view.ParallaxActivity;
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
    protected ActionBar mActionBar;

    protected boolean displayHomeAsUpEnabled() {
        return false;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (displayHomeAsUpEnabled()) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        nativeViewPager = findViewById(R.id.natureViewPager);
        TabLayout natureTableLayout = findViewById(R.id.natureTableLayout);

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
}