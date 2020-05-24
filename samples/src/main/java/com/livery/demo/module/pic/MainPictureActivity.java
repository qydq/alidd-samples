package com.livery.demo.module.pic;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.livery.demo.R;
import com.sunsta.bear.view.ParallaxActivity;

public class MainPictureActivity extends ParallaxActivity implements View.OnClickListener {
    private Button btn_activity, btn_fragment;

    @Override
    public void initView() {
        setContentView(R.layout.pic_activity_main);
        btn_activity = findViewById(R.id.btn_activity);
        btn_fragment = findViewById(R.id.btn_fragment);
        btn_activity.setOnClickListener(this);
        btn_fragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_activity:
                startActivity(new Intent(MainPictureActivity.this, PhotoActivity.class));
                break;
            case R.id.btn_fragment:
                startActivity(new Intent(MainPictureActivity.this, PhotoFragmentActivity.class));
                break;
            default:
                break;
        }
    }
}
