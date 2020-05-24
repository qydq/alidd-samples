package com.livery.demo.model.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.livery.demo.R;
import com.sunsta.bear.model.adapter.SmartRecyclerAdapter;

import java.util.List;

public class SwipeRecyclerAdapter extends SmartRecyclerAdapter<String, SwipeRecyclerAdapter.ViewHolder> {
    private List<String> mDataList;

    public SwipeRecyclerAdapter(Context context) {
        super(context);
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
        return new ViewHolder(getInflaterView(R.layout.item_swipe_recycleview, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitile);
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }
    }
}
