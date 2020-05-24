package com.livery.demo.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.livery.demo.R;
import com.sunsta.bear.listener.OnItemClickListener;

import java.util.List;

public class Fragment1Adapter extends RecyclerView.Adapter<Fragment1Adapter.ViewHolder> {
    private List<String> list;
    private Context context;

    public Fragment1Adapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment1Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.base_recyclerview_header, parent, false);
// View view = LinearLayout.inflate(parent.getContext(), R.ic_launcher_foreground.item_recycleview, null);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
// int position = holder.getLayoutPosition();
                String person = list.get(position);
                Toast.makeText(v.getContext(), person, Toast.LENGTH_SHORT).show();
// mItemClickListener.onItemClick(holder.itemView, position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Fragment1Adapter.ViewHolder holder, int position) {
// holder.tvBackground.setText(String.valueOf(position + 1));
        String person = list.get(position);
// holder.title.setText(person);
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView tvBackground;
        View itemView;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            title = itemView.findViewById(R.id.tvTitile);
// tvBackground = itemView.findViewById(R.id.tvBackground);
        }
    }

    public void setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private OnItemClickListener mItemClickListener;
}