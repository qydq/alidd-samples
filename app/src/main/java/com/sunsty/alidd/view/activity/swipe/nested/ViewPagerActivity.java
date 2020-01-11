package com.sunsty.alidd.view.activity.swipe.nested;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sunsty.alidd.R;
import com.sunsty.alidd.view.activity.swipe.MenuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 侧滑菜单和ViewPager嵌套，事先说好ViewPager的左右滑动会失效。
 */
public class ViewPagerActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pager_);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btn_one).setOnClickListener(mBtnClickListener);
        findViewById(R.id.btn_two).setOnClickListener(mBtnClickListener);
        findViewById(R.id.btn_three).setOnClickListener(mBtnClickListener);

        mViewPager = findViewById(R.id.view_pager_menu);
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mViewPager.setOffscreenPageLimit(2);

        List<Fragment> fragments = new ArrayList<>(3);
        fragments.add(Fragment.instantiate(this, MenuFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, MenuFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, MenuFragment.class.getName()));

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(pagerAdapter);

        mPageChangeListener.onPageSelected(0);
    }

    /**
     * Button点击监听。
     */
    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_one) {
                mViewPager.setCurrentItem(0, true);
            } else if (v.getId() == R.id.btn_two) {
                mViewPager.setCurrentItem(1, true);
            } else if (v.getId() == R.id.btn_three) {
                mViewPager.setCurrentItem(2, true);
            }
        }
    };

    private ViewPager.SimpleOnPageChangeListener mPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            mActionBar.setSubtitle("第" + position + "个");
            switch (position) {
                case 0: {
                    mViewPager.setBackgroundColor(ContextCompat.getColor(ViewPagerActivity.this, R.color.colorAccent));
                    break;
                }
                case 1: {
                    mViewPager.setBackgroundColor(ContextCompat.getColor(ViewPagerActivity.this, R.color.colorPrimary));
                    break;
                }
                case 2: {
                    mViewPager.setBackgroundColor(ContextCompat.getColor(ViewPagerActivity.this, R.color.btn_green_noraml));
                    break;
                }
            }
        }
    };

    private static class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public PagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
            super(fragmentManager);
            this.fragments = fragments;
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}