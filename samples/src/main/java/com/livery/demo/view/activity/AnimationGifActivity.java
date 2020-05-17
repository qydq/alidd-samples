package com.livery.demo.view.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;

import com.sunsta.bear.engine.gif.GifDrawable;
import com.sunsta.bear.engine.gif.GifImageView;
import com.sunsta.bear.faster.ToastUtils;
import com.bumptech.glide.Glide;
import com.livery.demo.R;

import java.io.IOException;

public class AnimationGifActivity extends AppCompatActivity {
    private ImageView ivKit1, ivKit2, ivKit3, ivKit4, ivKit5, ivKit6, ivKit7, ivKit8, originalImageView;

    private GifImageView gifImageView, aliGifImageView1, aliGifImageView2, aliGifImageView3, aliGifImageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_gif);
        originalImageView = findViewById(R.id.originalImageView);

        ivKit1 = findViewById(R.id.ivKit1);
        ivKit2 = findViewById(R.id.ivKit2);
        ivKit3 = findViewById(R.id.ivKit3);
        ivKit4 = findViewById(R.id.ivKit4);
        ivKit5 = findViewById(R.id.ivKit5);
        ivKit6 = findViewById(R.id.ivKit6);
        ivKit7 = findViewById(R.id.ivKit7);
        ivKit8 = findViewById(R.id.ivKit8);
        gifImageView = findViewById(R.id.gifImageView);
        aliGifImageView1 = findViewById(R.id.aliGifImageView1);
        aliGifImageView2 = findViewById(R.id.aliGifImageView2);
        aliGifImageView3 = findViewById(R.id.aliGifImageView3);
        aliGifImageView4 = findViewById(R.id.aliGifImageView4);

        loadKitGifWithGlide();

        loadOrinalAnmiation();
//        loadHttpImage();

        gifImageView.setOnClickListener(v -> ToastUtils.s(AnimationGifActivity.this, "909KB大Gif动图本地加载"));

        loadAliGif();

        loadHttpImage();
    }

    private void loadAliGif() {
        //resource (drawable or raw)
        try {
            GifDrawable gifFromResource3 = new GifDrawable(getResources(), R.drawable.drawable_gif3);
            aliGifImageView3.setImageResource(R.drawable.drawable_gif5);
            aliGifImageView4.setImageResource(R.drawable.drawable_gif6);

            GifDrawable gifFromResource4 = new GifDrawable(getResources(), R.drawable.drawable_gif4);

            final MediaController mc = new MediaController(this);
            mc.setMediaPlayer((GifDrawable) aliGifImageView3.getDrawable());
            mc.setAnchorView(aliGifImageView3);
            aliGifImageView3.setOnClickListener(v -> mc.show());

            aliGifImageView1.setBackground(gifFromResource3);
            aliGifImageView2.setImageDrawable(gifFromResource4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 原生AnimationDrawable加载帧动画
     * Author ：sunst
     * link : https://zhihu.com/people/qydq
     */
    private void loadOrinalAnmiation() {
        //获取背景，并将其强转成AnimationDrawable
        AnimationDrawable animationDrawable = (AnimationDrawable) originalImageView.getBackground();
        if (animationDrawable != null) {
            animationDrawable.stop();
            //判断是否在运行
            if (!animationDrawable.isRunning()) {
                //开启帧动画
                animationDrawable.start();
            }
        }

        originalImageView.setOnClickListener(v -> {
            if (animationDrawable != null) {
                animationDrawable.stop();
                //判断是否在运行
                if (!animationDrawable.isRunning()) {
                    //开启帧动画
                    animationDrawable.start();
                }
            }
        });

        ivKit4.setOnClickListener(v -> {
            if (animationDrawable != null) {
                animationDrawable.setVisible(true, true);//设置当前动画的第一帧，然后停止，可能联想之类的手机不支持
//                    animationDrawable.selectDrawable(0);      //选择当前动画的第一帧，然后停止
                animationDrawable.stop();
//                imageView.clearAnimation();
            }
        });
    }

    /**
     * Glide加载动画设置： 总结效率不是那么好，容易掉帧
     * Author ：sunst
     * link : https://zhihu.com/people/qydq
     */
    private void loadKitGifWithGlide() {
        Glide.with(this).load(R.drawable.drawable_gif_kit1).into(ivKit1);
        Glide.with(this).load(R.drawable.drawable_gif_kit2).into(ivKit2);
        Glide.with(this).load(R.drawable.drawable_gif_kit3).into(ivKit3);
        Glide.with(this).load(R.drawable.drawable_gif_kit4).into(ivKit4);
        Glide.with(this).load(R.drawable.drawable_gif_kit5).into(ivKit5);
        Glide.with(this).load(R.drawable.drawable_gif_kit6).into(ivKit6);
        Glide.with(this).load(R.drawable.drawable_gif_kit7).into(ivKit7);
        Glide.with(this).load(R.drawable.drawable_gif_kit8).into(ivKit8);
    }

    private void loadHttpImage() {
        String gifHttpUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1578367575&di=ee76023b9d5791ff9bc044f810044a8b&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201707%2F08%2F20170708153157_YMBFU.gif";
//        GlideEngine.getInstance().loadImage(this, gifHttpUrl, R.drawable.drawable_gif1, R.drawable.drawable_gif1, gifImageView);

//        LAUi.getInstance().flushLoadGif(this, gifHttpUrl, gifImageView);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LAUi.getInstance().stopLoadGif();
    }
}