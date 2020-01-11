package com.sunsty.alidd.view.activity.swipe.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sunsty.alidd.R;
import com.sunsty.alidd.view.activity.swipe.BaseActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 侧滑菜单的演示。
 */
public class MenuActivity extends BaseActivity {

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
                startActivity(new Intent(this, ListActivity.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, GridActivity.class));
                break;
            }
            case 2: {
                startActivity(new Intent(this, ViewTypeActivity.class));
                break;
            }
            case 3: {
                startActivity(new Intent(this, VerticalActivity.class));
                break;
            }
            case 4: {
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