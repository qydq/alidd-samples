package com.sunsty.alidd.view.activity.swipe.nested;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sunsty.alidd.R;
import com.sunsty.alidd.view.activity.swipe.BaseActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 几种嵌套使用的演示。
 */
public class NestedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged(mDataList);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        switch (position) {
            case 0: {
                startActivity(new Intent(this, CardViewActivity.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, DrawerActivity.class));
                break;
            }
            case 2: {
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            }
        }
    }

    @Override
    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.test_data));
    }
}