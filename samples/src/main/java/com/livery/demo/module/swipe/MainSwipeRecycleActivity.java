package com.livery.demo.module.swipe;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.livery.demo.view.activity.ExpandableActivity;
import com.sunsta.bear.faster.DataService;
import com.sunsta.bear.view.AliActivity;
import com.sunsta.bear.listener.OnItemClickListener;
import com.sunsta.bear.layout.swipe.SwipeRecyclerView;
import com.sunsta.bear.layout.swipe.widget.DefaultItemDecoration;
import com.livery.demo.R;
import com.sunsta.bear.model.adapter.SmartRecyclerAdapter;
import com.livery.demo.model.adapter.SwipeRecyclerAdapter;
import com.livery.demo.module.swipe.group.GroupActivity;
import com.livery.demo.module.swipe.group.MenuActivity;
import com.livery.demo.module.swipe.header.HeaderViewActivity;
import com.livery.demo.module.swipe.load.RefreshLoadActivity;
import com.livery.demo.module.swipe.move.MoveActivity;
import com.livery.demo.module.swipe.nested.NestedActivity;

import java.util.Arrays;
import java.util.List;

public class MainSwipeRecycleActivity extends AliActivity implements OnItemClickListener {
    protected Toolbar mToolbar;
    protected ActionBar mActionBar;
    protected SwipeRecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView.ItemDecoration mItemDecoration;

    private SmartRecyclerAdapter mAdapter;
    protected List<String> mDataList;

    @Override
    public void initView() {
        setContentView(R.layout.main_swipe_recycler_activity);
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

    protected SmartRecyclerAdapter createAdapter() {
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