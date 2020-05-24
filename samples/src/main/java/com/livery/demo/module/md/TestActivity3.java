package com.livery.demo.module.md;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;
import com.livery.demo.R;
import com.livery.demo.module.md.utils.ViewUtils;

public class TestActivity3 extends AppCompatActivity {

    View mFLayout;

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.md_activity_test3);
        ViewUtils.setImmersionStateMode(this);
//ViewUtils.addStatuHeight(findViewById(R.id.fl_layout),this);
        AppBarLayout mAppBarLayout = findViewById(R.id.appbar);
        mFLayout = findViewById(R.id.fl_layout);
        mTextView = findViewById(R.id.tv_info);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());

//第一种
                int toolbarHeight = appBarLayout.getTotalScrollRange();

                int dy = Math.abs(verticalOffset);

                if (dy <= toolbarHeight) {
                    float scale = (float) dy / toolbarHeight;
                    float alpha = scale * 255;
                    mFLayout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));

                    mTextView.setText("setBackgroundColor(Color.argb((int) " + (int) alpha + ", 255, 255, 255))\n" + "mFLayout.setAlpha(" + percent + ")");
                }

//第二种

// mFLayout.setAlpha(percent);

            }
        });

    }
}