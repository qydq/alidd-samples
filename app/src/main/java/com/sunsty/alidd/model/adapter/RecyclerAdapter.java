package com.sunsty.alidd.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.view.callback.OnItemClickListener;
import com.sunsty.alidd.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<String> list;
    private Context context;

    public RecyclerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent, false);
//        View view = LinearLayout.inflate(parent.getContext(), R.ic_launcher_foreground.item_recycleview, null);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
//                int position = holder.getLayoutPosition();
                String person = list.get(position);
//                Toast.makeText(v.getContext(), person, Toast.LENGTH_SHORT).show();
                mItemClickListener.onItemClick(holder.itemView, position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                holder.tvBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.ColorRed));
                break;
            case 1:
                holder.tvBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.an_color_mark));
                break;
            case 2:
                holder.tvBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.ColorBlueviolet));
                break;
            default:
                holder.tvBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.ColorGray));
        }
        holder.tvBackground.setText(String.valueOf(position + 1));
        String person = list.get(position);
        holder.title.setText(person);
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
            tvBackground = itemView.findViewById(R.id.tvBackground);
        }
    }

    public void setItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private OnItemClickListener mItemClickListener;
}