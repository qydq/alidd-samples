package com.sunsty.alidd.view.activity.swipe.move;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ali.view.swipelayout.touch.OnItemMoveListener;
import com.sunsty.alidd.model.adapter.BaseAliAdapter;
import com.sunsty.alidd.view.activity.swipe.DragTouchAdapter;

import java.util.Collections;

/**
 * 拖拽Item + 侧滑删除，默认侧滑删除只支持List形式。
 */
public class DragTouchListActivity extends BaseDragActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView.setLongPressDragEnabled(true); // 长按拖拽，默认关闭。
        mRecyclerView.setItemViewSwipeEnabled(true); // 滑动删除，默认关闭。
    }

    @Override
    protected OnItemMoveListener getItemMoveListener() {
        return onItemMoveListener;
    }

    @Override
    protected BaseAliAdapter createAdapter() {
        return new DragTouchAdapter(this, mRecyclerView);
    }

    /**
     * 监听拖拽和侧滑删除，更新UI和数据源。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            // 不同的ViewType不能拖拽换位置。
            if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) return false;

            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();

            Collections.swap(mDataList, fromPosition, toPosition);
            mAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;// 返回true表示处理了并可以换位置，返回false表示你没有处理并不能换位置。
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            int position = srcHolder.getAdapterPosition();
            mDataList.remove(position);
            mAdapter.notifyItemRemoved(position);
            Toast.makeText(DragTouchListActivity.this, "现在的第" + position + "条被删除。", Toast.LENGTH_SHORT).show();
        }

    };
}