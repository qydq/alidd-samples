package com.sunsty.alidd.view.activity.swipe.load;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sunsty.alidd.R;
import com.sunsty.alidd.view.activity.swipe.BaseActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 加载更多的两个演示。
 */
public class RefreshLoadActivity extends BaseActivity {

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
                startActivity(new Intent(this, DefaultActivity.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, DefineActivity.class));
                break;
            }
        }
    }

    @Override
    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.test_data));
    }
}