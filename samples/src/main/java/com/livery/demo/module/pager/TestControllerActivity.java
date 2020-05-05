package com.livery.demo.module.pager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.livery.demo.R;
import com.livery.demo.module.pager.adapter.NavigationPagerAdapter;
import com.sunsta.bear.layout.NoTouchViewPager;

import java.util.ArrayList;
import java.util.List;

import com.sunsta.livery.engine.NavigationController;
import com.sunsta.livery.engine.PageNavigationView;

/**
 * 测试导航栏控制类
 */
public class TestControllerActivity extends AppCompatActivity {

    private final int[] COLORS = {0xFF455A64, 0xFF00796B, 0xFF795548, 0xFF5B4947, 0xFFF57C00};

    private TextInputEditText mEdtIndex;
    private Button mBtnAddMessageNumber;
    private Button mBtnSubtraMessageNumber;
    private Button mBtnRemoveItem;
    private NoTouchViewPager mViewPager;
    private PageNavigationView mTab;
    private Button mBtnAddItem;

    private NavigationController mNavigationController;

    private final List<Integer> mMessageNumberList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity_test_controller);

        initView();

        initNavigation();

        initEvent();

    }

    private void initView() {
        mEdtIndex = findViewById(R.id.edt_index);
        mBtnAddMessageNumber = findViewById(R.id.btn_addMessageNumber);
        mBtnSubtraMessageNumber = findViewById(R.id.btn_subtraMessageNumber);
        mBtnRemoveItem = findViewById(R.id.btn_remove_item);
        mBtnAddItem = findViewById(R.id.btn_add_item);
        mViewPager = findViewById(R.id.viewPager);
        mTab = findViewById(R.id.tab);
    }

    private void initNavigation() {
        mNavigationController = mTab.material()
                .addItem(R.drawable.pager_ic_ondemand_video_black_24dp, "Movies & TV", COLORS[0])
                .addItem(R.drawable.pager_ic_audiotrack_black_24dp, "Music", COLORS[1])
                .addItem(R.drawable.pager_ic_book_black_24dp, "Books", COLORS[2])
                .addItem(R.drawable.pager_ic_news_black_24dp, "Newsstand", COLORS[3])
                .enableAnimateLayoutChanges()
                .build();

        NavigationPagerAdapter pagerAdapter = new NavigationPagerAdapter(getSupportFragmentManager(), Math.max(5, mNavigationController.getItemCount()));
        mViewPager.setAdapter(pagerAdapter);

        mNavigationController.setupWithViewPager(mViewPager);

        // 初始化消息数字为0
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            mMessageNumberList.add(0);
        }
    }

    private void initEvent() {
        // +
        mBtnAddMessageNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEdtIndex.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    showToast("input index");
                    return;
                }
                int index = Integer.parseInt(text);
                if (index >= 0 && index < mMessageNumberList.size()) {
                    mMessageNumberList.set(index, mMessageNumberList.get(index) + 1);
                    mNavigationController.setMessageNumber(index, mMessageNumberList.get(index));
                }
            }
        });

        // -
        mBtnSubtraMessageNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEdtIndex.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    showToast("input index");
                    return;
                }
                int index = Integer.parseInt(text);
                if (index >= 0 && index < mMessageNumberList.size()) {
                    mMessageNumberList.set(index, Math.max(0, mMessageNumberList.get(index) - 1));
                    mNavigationController.setMessageNumber(index, mMessageNumberList.get(index));
                }
            }
        });

        // 移除导航项
        mBtnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEdtIndex.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    showToast("input index");
                    return;
                }
                int index = Integer.parseInt(text);
                if (mNavigationController.removeItem(index)) {
                    mMessageNumberList.remove(index);
                } else {
                    showToast("移除失败(指定项不存在或者已被选中)");
                }
            }
        });

        // 添加导航项
        mBtnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEdtIndex.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    showToast("input index");
                    return;
                }

                if (mNavigationController.getItemCount() < 5) {
                    int index = Integer.parseInt(text);
                    mMessageNumberList.add(index, 0);
                    Drawable drawable = ContextCompat.getDrawable(TestControllerActivity.this, R.drawable.pager_ic_favorite_gray_24dp);
                    mNavigationController.addMaterialItem(index, drawable, drawable, "NEW", 0xFF880000);
                } else {
                    showToast("材料设计模式下，导航栏数量不要超过5个");
                }
            }
        });

    }

    private void showToast(String text) {
        Toast.makeText(TestControllerActivity.this, text, Toast.LENGTH_SHORT).show();
    }

}
