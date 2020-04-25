package com.sunsty.alidd.view.activity;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.faster.DataService;
import com.ali.view.AliActivity;
import com.ali.callback.OnItemClickListener;
import com.ali.layout.swipe.SwipeRecyclerView;
import com.ali.layout.swipe.widget.DefaultItemDecoration;
import com.sunsty.alidd.R;
import com.sunsty.alidd.model.adapter.BaseAliAdapter;
import com.sunsty.alidd.model.adapter.SwipeRecyclerAdapter;
import com.sunsty.alidd.view.activity.swipe.group.GroupActivity;
import com.sunsty.alidd.view.activity.swipe.group.MenuActivity;
import com.sunsty.alidd.view.activity.swipe.header.HeaderViewActivity;
import com.sunsty.alidd.view.activity.swipe.load.RefreshLoadActivity;
import com.sunsty.alidd.view.activity.swipe.move.MoveActivity;
import com.sunsty.alidd.view.activity.swipe.nested.NestedActivity;

import java.util.Arrays;
import java.util.List;

public class SwipeRecycleActivity extends AliActivity implements OnItemClickListener {
    protected Toolbar mToolbar;
    protected ActionBar mActionBar;
    protected SwipeRecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView.ItemDecoration mItemDecoration;

    private BaseAliAdapter mAdapter;
    protected List<String> mDataList;

    @Override
    public void initView() {
        setContentView(R.layout.activity_swiperecycler);
        mToolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.swipeRecyclerView);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (displayHomeAsUpEnabled()) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        mLayoutManager = createLayoutManager();
        mItemDecoration = createItemDecoration();
        mDataList = createDataList();
        mAdapter = createAdapter();

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setOnItemClickListener(this);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged(mDataList);
    }


    protected boolean displayHomeAsUpEnabled() {
        return false;
    }

    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.test_data));
    }

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new DefaultItemDecoration(ContextCompat.getColor(this, R.color.an_color_line), 10, 0);
    }

    protected BaseAliAdapter createAdapter() {
        return new SwipeRecyclerAdapter(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onItemClick(View view, int adapterPosition) {
        DataService.getInstance().copyClipboad(this, createDataList().get(adapterPosition));
        showToast("已复制：" + createDataList().get(adapterPosition));
        switch (adapterPosition) {
            case 0:
                startActivity(new Intent(this, MenuActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, MoveActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, HeaderViewActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, RefreshLoadActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, NestedActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, ExpandableActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, GroupActivity.class));
                break;
        }
    }
}