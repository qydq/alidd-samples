package com.livery.demo.module.md;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.livery.demo.R;
import com.livery.demo.view.fragment.Fragment1;
import com.livery.demo.view.fragment.Fragment2;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.SmartUtil;
import com.sunsta.bear.faster.LAUi;
import com.sunsta.bear.immersion.StatusBarUtils;
import com.sunsta.bear.view.AliActivity;

import java.util.ArrayList;
import java.util.List;

public class TestActivity5 extends AliActivity implements View.OnClickListener {
    private String[] strings = new String[]{"列表1", "列表2"};
    private List<Fragment> nativeFragments = new ArrayList<>();
    private ViewPager nativeViewPager;

    protected Toolbar mToolbar;
    protected AppBarLayout appBarLayout;
    protected View toolbarInner;
    protected ActionBar mActionBar;

    protected RelativeLayout rlContent1;
    protected RelativeLayout rlContent2;
    private TextView tvTitle, tvJoin, tvFan, tvFanNumber, tvPost, tvPostNumber, tvTitle2;
    private ImageView ivHead, ivBack1, ivBack2;
    protected FloatingActionButton fab;

    private int mOffset = 0;
    private int mScrollY = 0;

    protected boolean displayHomeAsUpEnabled() {
        return false;
    }

    @Override
    public void initView() {
        setContentView(R.layout.md_activity_test4);
        fitStatusBar(false, true);//设置状态栏颜色为白色，lightMode=false为默认白色，lightMode=true为黑色
        mToolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.appBarLayout);
        toolbarInner = findViewById(R.id.toolbarInner);

        rlContent1 = findViewById(R.id.rlContent1);
        rlContent2 = findViewById(R.id.rlContent2);
        fab = findViewById(R.id.fab);
        tvTitle = findViewById(R.id.tvTitle1);
        tvTitle2 = findViewById(R.id.tvTitle2);
        tvJoin = findViewById(R.id.tvJoin);
        tvFan = findViewById(R.id.tvFan);
        tvFanNumber = findViewById(R.id.tvFanNumber);
        tvPost = findViewById(R.id.tvPost);
        tvPostNumber = findViewById(R.id.tvPostNumber);
        ivBack1 = findViewById(R.id.ivBack1);
        ivBack2 = findViewById(R.id.ivBack2);
        ivHead = findViewById(R.id.ivHead);

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (displayHomeAsUpEnabled()) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        appBarListener();

        /*初始化布局事件*/

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//状态栏透明和间距处理
        StatusBarUtils.immersive(this);
        StatusBarUtils.setPaddingSmart(this, toolbar);
        StatusBarUtils.setMargin(this, findViewById(R.id.header));

        final View parallax = findViewById(R.id.parallax);
        final View buttonBar = findViewById(R.id.buttonBarLayout);
        final NestedScrollView scrollView = findViewById(R.id.scrollView);
        final RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);

        findViewById(R.id.tvJoin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "点击了tvJoin", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.fmc_center_dynamic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "点击了fabu", Toast.LENGTH_SHORT).show();
            }
        });

        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(3000);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mOffset = offset / 2;
                parallax.setTranslationY(mOffset - mScrollY);
                toolbar.setAlpha(1 - Math.min(percent, 1));
            }
// @Override
// public void onHeaderPulling(@NonNull RefreshHeader header, float percent, int offset, int bottomHeight, int maxDragHeight) {
// mOffset = offset / 2;
// parallax.setTranslationY(mOffset - mScrollY);
// toolbar.setAlpha(1 - Math.min(percent, 1));
// }
// @Override
// public void onHeaderReleasing(@NonNull RefreshHeader header, float percent, int offset, int bottomHeight, int maxDragHeight) {
// mOffset = offset / 2;
// parallax.setTranslationY(mOffset - mScrollY);
// toolbar.setAlpha(1 - Math.min(percent, 1));
// }
        });
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = SmartUtil.dp2px(170);
            private int color = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    buttonBar.setAlpha(1f * mScrollY / h);
                    toolbar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                    parallax.setTranslationY(mOffset - mScrollY);
                }
                lastScrollY = scrollY;
            }
        });
        buttonBar.setAlpha(0);
        toolbar.setBackgroundColor(0);

        nativeViewPager = findViewById(R.id.natureViewPager);
        TabLayout natureTableLayout = findViewById(R.id.natureTableLayout);
// toolbarInner.findViewById(R.id.iv).setOnClickListener(v -> showToast("1.前两个模块属于alidd情景系列；2.后两个模块属于非alidd情景系列."));

// mToolbar.setNavigationIcon(R.drawable.base_drawable_backarrow_click);

        nativeFragments.add(new Fragment1());
        nativeFragments.add(new Fragment2());
        NatureAdapter fragmentAdater = new NatureAdapter(getSupportFragmentManager());
        nativeViewPager.setAdapter(fragmentAdater);
// natureViewPager.setOffscreenPageLimit(4);
        natureTableLayout.setupWithViewPager(nativeViewPager);
        LAUi.getInstance().setIndicator(natureTableLayout, 0, 0);
        nativeViewPager.setOffscreenPageLimit(7);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTitle1:
            case R.id.tvTitle:
                showToast("点击了标题：" + tvTitle.getText().toString());
                break;
            case R.id.tvJoin:
                showToast("点击了加入");
                break;
            case R.id.tvFan:
            case R.id.tvFanNumber:
                showToast("点击了粉丝");
                break;
            case R.id.tvPost:
            case R.id.tvPostNumber:
                showToast("点击了帖子");
                break;
            case R.id.ivHead:
                showToast("点击了头像");
                break;
            case R.id.ivBack1:
            case R.id.ivBack2:
                showToast("点击了返回");
                break;
            case R.id.fab:
                showToast("点击了FloatingActionButton");
                break;
        }
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
// fab.setOnClickListener(this);
// tvTitle.setOnClickListener(this);
// tvJoin.setOnClickListener(this);
// tvFan.setOnClickListener(this);
// tvFanNumber.setOnClickListener(this);
// tvPost.setOnClickListener(this);
// tvPostNumber.setOnClickListener(this);
// ivHead.setOnClickListener(this);
// tvTitle.setOnClickListener(this);
// tvTitle2.setOnClickListener(this);
// ivBack1.setOnClickListener(this);
// ivBack2.setOnClickListener(this);
// appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
// @Override
// public void onStateChanged(AppBarLayout appBarLayout, State state) {
// /* if (state === State.COLLAPSED) {
// //下面的方法会出现监听不到的情况，所以这里变成折叠状态，最好再设置一次透明度
// blankView.visibility = View.VISIBLE
// }else{
// blankView.visibility = View.GONE
// }*/
// if (state == State.COLLAPSED) {
//// AnimationUtils.showAndHiddenAnimation(rlContent2, AnimationUtils.AnimationState.STATE_SHOW, 500);
//// rlContent1.setVisibility(View.GONE);
//// rlContent2.setVisibility(View.VISIBLE);
//// barTop = true;
// /*滑动到上面了*/
//// appBarLayout.setBackgroundColor(getResources().getColor(R.color.ColorWhite));
// fitStatusBar(true, true);//设置状态栏颜色为白色，lightMode=false为默认白色，lightMode=true为黑色
// } else {
//// rlContent1.setVisibility(View.VISIBLE);
//// rlContent2.setVisibility(View.GONE);
// barTop = false;
// /*没有展开了*/
//// appBarLayout.setBackgroundColor(getResources().getColor(R.color.ColorBlack));
// fitStatusBar(false, true);//设置状态栏颜色为白色，lightMode=false为默认白色，lightMode=true为黑色
// }
// }
// });
    }
}