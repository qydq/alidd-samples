package com.livery.demo.module.md;

import android.os.SystemClock;
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
import com.sunsta.bear.entity.Barrage;
import com.sunsta.bear.layout.INABarrageView;
import com.sunsta.bear.layout.swipe.widget.DefaultItemDecoration;
import com.sunsta.bear.listener.AppBarStateChangeListener;
import com.sunsta.bear.listener.OnItemClickListener;
import com.sunsta.bear.model.adapter.BarrageDataAdapter;
import com.sunsta.bear.view.AliActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TestActivity4 extends AliActivity {
    View mFLayout;
    TextView mTextView;
    RecyclerView recyclerView;
    private MainFragmentAdapter mainFragmentAdapter;
    protected AppBarLayout appBarLayout;
    protected INABarrageView inaBarrageView;
    protected BarrageDataAdapter barrageDataAdapter;
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    private LinearLayout playButton;
    private Toolbar toolbar;

    @Override
    public void initView() {
        setContentView(R.layout.md_activity_test4);
// ViewUtils.setImmersionStateMode(this);

        recyclerView = findViewById(R.id.recyclerview);
        appBarLayout = findViewById(R.id.app_bar);
        inaBarrageView = findViewById(R.id.inaBarrageView);
        barrageDataAdapter = inaBarrageView.obtainBarrageAdapter();

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

        mainFragmentAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });


        timer = new Timer();
        timer.schedule(new AsyncAddTask(), 0, 200);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inaBarrageView.pause();
            }
        });

    }

    private volatile boolean isStop = false;
    Timer timer = new Timer();
    private int num = 0;

    private void addTextBarrage() {
        Barrage barrage = new Barrage(BarrageDataAdapter.BarrageType.TEXT);
        barrage.setContent("本人知乎：bgwan，欢迎关注" + (num++));
        barrage.setAction0Drawable(R.drawable.base_bg_pressed);
        barrageDataAdapter.addBarrage(barrage);
    }

    private void addImageTextBarrage() {
        Barrage barrage = new Barrage(BarrageDataAdapter.BarrageType.IMAGE_TEXT);
        barrage.setPrimaryIvId(R.drawable.hong);
        barrage.setAction0Drawable(R.drawable.base_bg_pressed);

        barrage.setContent("显示图片类别的弹幕");
        barrageDataAdapter.addBarrage(barrage);
    }

    class AsyncAddTask extends TimerTask {
        @Override
        public void run() {
            for (int i = 0; i < 200; ++i) {
                if (isStop) {
                    return;
                }
                SystemClock.sleep(100);
                final int finalI = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (finalI % 2 == 0) {
                            addTextBarrage();
                        } else {
                            addImageTextBarrage();
                        }
                    }
                });
            }
        }
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
