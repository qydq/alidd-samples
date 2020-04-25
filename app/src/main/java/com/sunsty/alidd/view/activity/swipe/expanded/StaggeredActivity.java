/*
 * Copyright 2019 Zhenjie Yan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunsty.alidd.view.activity.swipe.expanded;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ali.callback.OnItemClickListener;
import com.ali.layout.swipe.SwipeRecyclerView;
import com.ali.layout.swipe.widget.DefaultItemDecoration;
import com.sunsty.alidd.R;
import com.sunsty.alidd.view.activity.swipe.BaseActivity;
import com.sunsty.alidd.view.activity.swipe.ExpandedAdapter;
import com.sunsty.alidd.view.activity.swipe.expanded.entity.Group;
import com.sunsty.alidd.view.activity.swipe.expanded.entity.GroupMember;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class StaggeredActivity extends BaseActivity {

    private SwipeRecyclerView mRecyclerView;
    private ExpandedAdapter mAdapter;
    private List<Group> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiperecycler);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.swipeRecyclerView);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.multiple_image_select_divider)));
        mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                // 根据原position判断该item是否是parent item
                if (mAdapter.isParentItem(position)) {
                    // 换取parent position
                    int parentPosition = mAdapter.parentItemPosition(position);

                    // 判断parent是否打开了二级菜单
                    if (mAdapter.isExpanded(parentPosition)) {
                        mDataList.get(parentPosition).setExpanded(false);
                        mAdapter.notifyParentChanged(parentPosition);

                        // 关闭该parent下的二级菜单
                        mAdapter.collapseParent(parentPosition);
                    } else {
                        mDataList.get(parentPosition).setExpanded(true);
                        mAdapter.notifyParentChanged(parentPosition);

                        // 打开该parent下的二级菜单
                        mAdapter.expandParent(parentPosition);
                    }
                } else {
                    // 换取parent position
                    int parentPosition = mAdapter.parentItemPosition(position);
                    // 换取child position
                    int childPosition = mAdapter.childItemPosition(position);
                    String message = String.format("我是%1$d爸爸的%2$d儿子", parentPosition, childPosition);
                    Toast.makeText(StaggeredActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        mAdapter = new ExpandedAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        refresh();
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Group group = new Group();
            group.setName("我是爸爸，我的号是 " + i);
            group.setMemberList(new ArrayList<GroupMember>());
            for (int j = 0; j < 10; j++) {
                GroupMember member = new GroupMember();
                member.setName("我是儿子，我的号是 " + j);
                group.getMemberList().add(member);
            }
            mDataList.add(group);
        }

        mAdapter.setGroupList(mDataList);
        mAdapter.notifyDataSetChanged();
    }
}