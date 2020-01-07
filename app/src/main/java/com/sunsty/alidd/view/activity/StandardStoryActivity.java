package com.sunsty.alidd.view.activity;

import android.graphics.Color;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.ali.take.LaLog;
import com.ali.view.ParallaxActivity;
import com.ali.view.callback.OnLikeListener;
import com.ali.view.dd.INALikeButton;
import com.sunsty.alidd.R;

public class StandardStoryActivity extends ParallaxActivity implements NestedScrollView.OnScrollChangeListener {
    Toolbar toolbar;
    NestedScrollView nestedScrollView;
    INALikeButton likeCollect;
    RelativeLayout rlTop;
    INALikeButton likeButton;
    WebView webView;
    View lineTop;

    @Override
    public void initView() {
        setContentView(R.layout.activity_story_detail);
        toolbar = findViewById(R.id.toolbar);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        likeCollect = findViewById(R.id.likeCollect);
        likeButton = findViewById(R.id.likeButton);
        webView = findViewById(R.id.webView);
        rlTop = findViewById(R.id.rlTop);
        lineTop = findViewById(R.id.lineTop);

//        initToolbar(toolbar, true);

        initToolbar(toolbar, "我的测试数据");

        nestedScrollView.setOnScrollChangeListener(this);

        likeCollect.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(INALikeButton var1) {
                showToast("1");
            }

            @Override
            public void unLiked(INALikeButton var1) {
                showToast("0");
            }
        });

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(INALikeButton var1) {
                showToast("1");
            }

            @Override
            public void unLiked(INALikeButton var1) {
                showToast("0");
            }
        });

        loadData();
    }

    private void initToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);//返回键可见
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        webView.loadDataWithBaseURL("", "在Retrofit2.0使用详解这篇文章中详细介绍了retrofit的用法。并且在retrofit中我们可以通过ResponseBody进行对文件的下载。但是在retrofit中并没有为我们提供显示下载进度的接口。在项目中，若是用户下载一个文件，无法实时给用户显示下载进度，这样用户的体验也是非常差的。那么下面就介绍一下在retrofit用于文件的下载如何实时跟踪下载进度。\n" +
                "\n" +
                "演示\n" +
                "\n" +
                "\n" +
                "Retrofit文件下载进度更新的实现\n" +
                "　　在retrofit2.0中他依赖于Okhttp，所以如果我们需要解决这个问题还需要从这个OKhttp来入手。在Okhttp中有一个依赖包Okio。Okio也是有square公司所开发，它是java.io和java.nio的补充，使用它更容易访问、存储和处理数据。在这里需要使用Okio中的Source类。在这里Source可以看做InputStream。对于Okio的详细使用在这里就不在介绍。下面来看一下具体实现。 \n" +
                "　　在这里我们首先写一个接口，用于监听下载的进度。对于文件的下载，我们需要知道下载的进度，文件的总大小，以及是否操作完成。于是有了下面这样一个接口。\n" +
                "————————————————\n" +
                "版权声明：本文为CSDN博主「无嘴小呆子」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。\n" +
                "原文链接：https://blog.csdn.net/ljd2038/article/details/51189334在上面ProgressResponseBody类中，我们计算已经读取文件的字节数，并且调用了ProgressListener接口。所以这个ProgressListener接口是在子线程中运行的。 \n" +
                "　　下面就来看一下是如何使用这个ProgressResponseBody。我们通过为OkhttpClient添加一个拦截器来使用我们自定义的ProgressResponseBody。并且在这里我们可以通过实现ProgressListener接口。来获取下载进度了。但是在这里依然存在一个问题，刚才说到这个ProgressListener接口运行在子线程中。也就是说在ProgressListener这个接口中我们无法进行ui操作。而我们获取文件下载的进度往往则是需要一个进度条进行ui显示。显然这并不是我们想要的结果。 \n" +
                "　　在这个时候我们就需要使用Handler了。我们可以通过Handler将子线程中的ProgressListener的数据发送到ui线程中进行处理。也就是说我们在ProgressListener接口中的操作只是将其参数通过Handler发送出去。很显然在上面的代码中我们通过ProgressHandler来发送消息。那么就来看一下具体操作。 \n" +
                "　　这里我们创建一个对象，用于存放ProgressListener中的参数。\n" +
                "————————————————\n" +
                "版权声明：本文为CSDN博主「无嘴小呆子」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。\n" +
                "原文链接：https://blog.csdn.net/ljd2038/article/details/51189334,上面的ProgressHandler他是一个抽象类。在这里我们需要通过Handler对象进行发送和处理消息。于是定义了两个抽象方法sendMessage和handleMessage。之后又定义了一个抽象方法onProgress来处理下载进度的显示，而这个onProgress则是我们需要在ui线程进行调用。最后创建了一个继承自Handler的ResponseHandler内部类。为了避免内存泄露我们使用static关键字。 \n" +
                "　　下面来创建一个DownloadProgressHandler类，他继承于ProgressHandler，用来发送和处理消息。\n" +
                "————————————————\n" +
                "版权声明：本文为CSDN博主「无嘴小呆子」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。\n" +
                "原文链接：https://blog.csdn.net/ljd2038/article/details/51189334", "text/html", "utf-8", null);

//        webView.loadData(Html.fromHtml(mod.getContent()).toString(), "text/html", "UTF-8");//not ok
//        webView.loadData(mod.getContent(), "text/html;charset=UTF-8", null);//ok3
//        webView.loadDataWithBaseURL(null, mod.getContent(), "text/html", "utf-8", null);//ok1
//        webView.loadDataWithBaseURL(null, mod.getContent(), "text/html", "gbk", null);//ok2
    }

    @Override
    public void onScrollChange(NestedScrollView v, int x, int y, int oldX, int oldY) {
        rlTop.scrollTo(x, -y / 2);
        if (y < maxHeight) {
            lineTop.setVisibility(View.GONE);
            toolbar.setAlpha(getAlphaForActionBar(y));
        } else {
            LaLog.d(TAG + "sunst---onScrollChange:y=" + y);
            int lastYy = lastY - y;
            LaLog.d(TAG + "sunst---onScrollChange:lastYy=" + lastYy);
            int yLastY = y - lastY;
            LaLog.d(TAG + "sunst---onScrollChange:yLastY=" + yLastY);

            if (lastYy > 20) {
                //上滑
                lastY = y;
                if (toolbar.getVisibility() == View.GONE) {
                    toolbar.setVisibility(View.VISIBLE);
                    lineTop.setVisibility(View.GONE);
                }
                toolbar.setAlpha(1f);
            } else if (yLastY > 20) {
                //下滑
                lastY = y;
                toolbar.setAlpha(0f);
                if (toolbar.getVisibility() == View.VISIBLE) {
                    toolbar.setVisibility(View.GONE);
                    lineTop.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    int maxHeight = 600;
    int lastY = 0;

    public float getAlphaForActionBar(int scrollY) {
        float minDist = 0;
        if (scrollY > maxHeight) {
            return 0f;
        } else if (scrollY < minDist) {
            return 0f;
        } else {
            float alpha = 1f - (1f / maxHeight * scrollY);
            return alpha;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
//        loadView.retryListener = null;
        nestedScrollView.setOnClickListener(null);

        likeButton.setOnLikeListener(null);
        likeCollect.setOnLikeListener(null);
    }
}
