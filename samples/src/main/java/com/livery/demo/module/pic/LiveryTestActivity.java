package com.livery.demo.module.pic;

import android.widget.TextView;

import com.livery.demo.R;
import com.sunsta.bear.view.ParallaxActivity;

public class LiveryTestActivity extends ParallaxActivity {
    private TextView mTextView;
    @Override
    public void initView() {
        setContentView(R.layout.item_aboutpage);

        mTextView = findViewById(R.id.description);

    }
}
