package com.sunsty.alidd.view.activity.swipe;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.view.swipelayout.SwipeRecyclerView;
import com.sunsty.alidd.R;
import com.sunsty.alidd.model.adapter.BaseAliAdapter;

import java.util.List;

/**
 * 触摸即开始拖拽。
 */
public class DragTouchAdapter extends BaseAliAdapter<DragTouchAdapter.ViewHolder> {

    private SwipeRecyclerView mMenuRecyclerView;
    private List<String> mDataList;

    public DragTouchAdapter(Context context, SwipeRecyclerView menuRecyclerView) {
        super(context);
        this.mMenuRecyclerView = menuRecyclerView;
    }

    @Override
    public void notifyDataSetChanged(List<String> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(getInflater().inflate(R.layout.item_drag_touch, parent, false));
        viewHolder.mMenuRecyclerView = mMenuRecyclerView;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        TextView tvTitle;
        SwipeRecyclerView mMenuRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            itemView.findViewById(R.id.iv_touch).setOnTouchListener(this);
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    mMenuRecyclerView.startDrag(this);
                    break;
                }
            }
            return false;
        }
    }

}