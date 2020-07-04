package com.livery.demo.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.livery.demo.R;
import com.sunsta.bear.immersion.RichTextView;
import com.sunsta.bear.listener.OnItemClickListener;

import java.util.List;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {
    private List<String> list;
    private Context context;

    public MainFragmentAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MainFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
                if (mItemClickListener != null)
                    mItemClickListener.onItemClick(holder.itemView, position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainFragmentAdapter.ViewHolder holder, int position) {
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
        if (position == 3) {
            RichTextView.setCircleText(holder.title, person, "红");
        } else if (position == 4) {
            RichTextView.setRoundText(holder.title, person, "圆圆的小可爱");
        } else if (position == 5) {
            RichTextView.setPictureRight(holder.title, R.drawable.pager_ic_news_black_24dp);
        } else if (position == 6) {
            RichTextView.setPictureLeft(holder.title, R.drawable.pager_ic_ondemand_video_black_24dp);
        }
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