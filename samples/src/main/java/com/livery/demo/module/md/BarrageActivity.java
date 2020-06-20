package com.livery.demo.module.md;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.livery.demo.R;
import com.sunsta.bear.entity.Barrage;
import com.sunsta.bear.faster.LaLog;
import com.sunsta.bear.faster.ToastUtils;
import com.sunsta.bear.model.adapter.BarrageDataAdapter;
import com.sunsta.bear.layout.INABarrageView;
import com.sunsta.bear.view.AliActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class BarrageActivity extends AliActivity {
    private static final String TAG = "BarrageActivity";

    private Context mContext;
    private FrameLayout mBBFrame;
    private Button mAddByTimerBtn;

    private BarrageDataAdapter mBarrageAdapter;
    private INABarrageView inaBarrageView;


    @Override
    public void initView() {
        setContentView(R.layout.md_activity_barrage);
        mContext = this;
        init();
//        inaBarrageView.setData(loadData1());
//        BarrageBean rb2 = new BarrageBean(1, R.drawable.base_image_music_play, "Jack ma", "妈妈问我为什么跪着听歌");
//        inaBarrageView.startBarrage(rb2);
    }

    private List loadData1() {
        ArrayList<Barrage> datas = new ArrayList<>();
        datas.add(new Barrage(0, "sunsta", "状态:<b><font color=\"#FF3D49\">" + 50000 / 1000 + "</font></b>秒"));
        datas.add(new Barrage(1, "Jack ma", "妈妈问我为什么跪着听歌"));
        return datas;
    }

    private void init() {
        inaBarrageView = (INABarrageView) findViewById(R.id.barrageView);
        mBBFrame = (FrameLayout) findViewById(R.id.bbFrame);
        mAddByTimerBtn = (Button) findViewById(R.id.addByTimerBtn);
        mAddByTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBarrageByTimer();
            }
        });

        findViewById(R.id.dumpBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inaBarrageView.dumpMemory();
            }
        });
        findViewById(R.id.pauseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inaBarrageView.pause();
            }
        });
        findViewById(R.id.resumeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inaBarrageView.resume();
            }
        });
        findViewById(R.id.clearBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inaBarrageView.clear();
            }
        });
        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTextBarrage();
            }
        });
        findViewById(R.id.addPriorityBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.addImageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImageTextBarrage();
            }
        });
        findViewById(R.id.addBBBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBBBarrage();
            }
        });

        mBarrageAdapter = inaBarrageView.obtainBarrageAdapter();
        inaBarrageView.setIdleListener(new INABarrageView.CBarrageViewListener() {
            @Override
            public void onIdle(long idleTimeMs, INABarrageView view) {
                LaLog.d("我是计算延迟时间，idleTimeMs = " + idleTimeMs + "view=" + view.toString());
            }
        });
        mBarrageAdapter.setItemBarrageClickListener(new BarrageDataAdapter.OnItemBarrageClickListener() {
            @Override
            public void onItemClick(Barrage barrage) {
                ToastUtils.s(BarrageActivity.this, "点击:" + barrage.getType());
            }
        });

//        inaBarrageView.setRowHeight(100);
    }

    private int num = 0;

    private void addTextBarrage() {
        Barrage barrage = new Barrage(BarrageDataAdapter.BarrageType.TEXT);
        barrage.setContent("这是一条普通弹幕" + (num++));
//        barrage.setAction0Drawable(R.drawable.base_bg_pressed);
        mBarrageAdapter.addBarrage(barrage);
    }

    private void addImageTextBarrage() {
        Barrage barrage = new Barrage(BarrageDataAdapter.BarrageType.IMAGE_TEXT);
        barrage.setPrimaryIvId(R.drawable.hong);
//        barrage.setAction0Drawable(R.drawable.base_bg_pressed);

        barrage.setContent("图文");
        mBarrageAdapter.addBarrage(barrage);
    }

    private void addBBBarrage() {
        mBBFrame.setVisibility(View.VISIBLE);

        CountDownTimer timer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                // note memory leak
                Barrage barrage = new Barrage(BarrageDataAdapter.BarrageType.IMAGE_TEXT);
                barrage.setContent("霸屏");
                mBBFrame.setVisibility(View.GONE);
//                mBarrageAdapter.addRowBarrage(barrage);
//                mBarrageAdapter.addBarrageToRow(2, barrage);
                mBarrageAdapter.addBarrage(barrage);
            }
        };
        timer.start();
    }

    private void addBarrageByTimer() {
        Boolean b = (Boolean) mAddByTimerBtn.getTag();
        timer.cancel();
        if (b == null || !b) {
            mAddByTimerBtn.setText("暂停定时器");
            timer = new Timer();
            timer.schedule(new AsyncAddTask(), 0, 200);
            mAddByTimerBtn.setTag(true);
            isStop = false;
        } else {
            mAddByTimerBtn.setText("启动定时器");
            mAddByTimerBtn.setTag(false);
            isStop = true;
        }
    }

    private volatile boolean isStop = false;
    Timer timer = new Timer();

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
}