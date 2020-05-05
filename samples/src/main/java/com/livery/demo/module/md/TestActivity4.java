package com.livery.demo.module.md;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.livery.demo.R;
import com.livery.demo.model.adapter.MainFragmentAdapter;
import com.sunsta.bear.layout.swipe.widget.DefaultItemDecoration;
import com.sunsta.bear.listener.AppBarStateChangeListener;
import com.sunsta.bear.view.AliActivity;

import java.util.Arrays;
import java.util.List;

public class TestActivity4 extends AliActivity {

    View mFLayout;

    TextView mTextView;
    RecyclerView recyclerView;
    private MainFragmentAdapter mainFragmentAdapter;
    protected AppBarLayout appBarLayout;
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    private LinearLayout playButton;
    private Toolbar toolbar;

    @Override
    public void initView() {
        setContentView(R.layout.md_activity_test3);
// ViewUtils.setImmersionStateMode(this);

        recyclerView = findViewById(R.id.recyclerview);
        appBarLayout = findViewById(R.id.app_bar);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        playButton = findViewById(R.id.playButton);
        toolbar = findViewById(R.id.toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
// layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置横向
        recyclerView.setLayoutManager(layoutManager);
        DefaultItemDecoration listItemDecoration = new DefaultItemDecoration(getResources().getColor(R.color.an_color_line), 80, 1);
        recyclerView.addItemDecoration(listItemDecoration);
        mainFragmentAdapter = new MainFragmentAdapter(this, createDataList());
        recyclerView.setAdapter(mainFragmentAdapter);

        appBarListener();
    }

    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.main_material_ux));
    }

    private void appBarListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
/* if (state === State.COLLAPSED) {
//下面的方法会出现监听不到的情况，所以这里变成折叠状态，最好再设置一次透明度
blankView.visibility = View.VISIBLE
}else{
blankView.visibility = View.GONE
}*/
                if (state == State.EXPANDED) {
                    collapsingToolbarLayout.setTitle("EXPANDED");//设置title为EXPANDED
                } else if (state == State.COLLAPSED) {
                    collapsingToolbarLayout.setTitle("");//设置title不显示
                    playButton.setVisibility(View.VISIBLE);//隐藏播放按钮
                    toolbar.setVisibility(View.VISIBLE);//隐藏播放按钮
                } else if (state == State.IDLE) {
                    playButton.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                    toolbar.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                    collapsingToolbarLayout.setTitle("IDLE");//设置title为INTERNEDIATE
                }

// if (state == AppBarStateChangeListener.State.COLLAPSED) {
// /*滑动到上面了*/
//// appBarLayout.setBackgroundColor(getResources().getColor(R.color.ColorWhite));
// fitStatusBar(false, true);//设置状态栏颜色为白色，lightMode=false为默认白色，lightMode=true为黑色
// } else {
// /*没有展开了*/
//// appBarLayout.setBackgroundColor(getResources().getColor(R.color.ColorBlack));
// fitStatusBar(true, true);//设置状态栏颜色为白色，lightMode=false为默认白色，lightMode=true为黑色
// }
            }
        });
    }
}
