package com.livery.demo.module.swipe.move;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.livery.demo.R;
import com.livery.demo.module.swipe.BaseActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Item拖拽和侧滑删除演示。
 */
public class MoveActivity extends BaseActivity {

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
                startActivity(new Intent(this, DragSwipeListActivity.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, DragGridActivity.class));
                break;
            }
            case 2: {
                startActivity(new Intent(this, DragTouchListActivity.class));
                break;
            }
            case 3: {
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