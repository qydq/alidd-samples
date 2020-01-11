package com.sunsty.alidd.view.activity.swipe.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sunsty.alidd.R;
import com.sunsty.alidd.view.activity.swipe.BaseActivity;

import java.util.Arrays;
import java.util.List;


public class GroupActivity extends BaseActivity {

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
                startActivity(new Intent(this, LayoutActivity.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, MenuActivity.class));
                break;
            }
        }
    }

    @Override
    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.test_data));
    }

}