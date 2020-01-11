package com.sunsty.alidd.view.activity.swipe.move;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.view.swipelayout.touch.OnItemMoveListener;
import com.sunsty.alidd.R;

import java.util.Collections;

/**
 * 拖拽Item + 侧滑删除，默认侧滑删除只支持List形式。
 */
public class DragSwipeListActivity extends BaseDragActivity {

    View mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHeaderView = getLayoutInflater().inflate(R.layout.layout_header_switch, mRecyclerView, false);
        mRecyclerView.addHeaderView(mHeaderView);

        SwitchCompat switchCompat = mHeaderView.findViewById(R.id.switch_compat);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 控制是否可以侧滑删除。
                mRecyclerView.setItemViewSwipeEnabled(isChecked);
            }
        });

        mRecyclerView.setLongPressDragEnabled(true); // 长按拖拽，默认关闭。
        mRecyclerView.setItemViewSwipeEnabled(false); // 滑动删除，默认关闭。
    }

    @Override
    protected OnItemMoveListener getItemMoveListener() {
        // 监听拖拽和侧滑删除，更新UI和数据源。
        return new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                // 不同的ViewType不能拖拽换位置。
                if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) return false;

                // 真实的Position：通过ViewHolder拿到的position都需要减掉HeadView的数量。
                int fromPosition = srcHolder.getAdapterPosition() - mRecyclerView.getHeaderCount();
                int toPosition = targetHolder.getAdapterPosition() - mRecyclerView.getHeaderCount();

                Collections.swap(mDataList, fromPosition, toPosition);
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;// 返回true表示处理了并可以换位置，返回false表示你没有处理并不能换位置。
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                int adapterPosition = srcHolder.getAdapterPosition();
                int position = adapterPosition - mRecyclerView.getHeaderCount();

                if (mRecyclerView.getHeaderCount() > 0 && adapterPosition == 0) { // HeaderView。
                    mRecyclerView.removeHeaderView(mHeaderView);
                    Toast.makeText(DragSwipeListActivity.this, "HeaderView被删除。", Toast.LENGTH_SHORT).show();
                } else { // 普通Item。
                    mDataList.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    Toast.makeText(DragSwipeListActivity.this, "现在的第" + position + "条被删除。", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}