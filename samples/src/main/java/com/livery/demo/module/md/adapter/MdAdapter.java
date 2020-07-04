package com.livery.demo.module.md.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.livery.demo.R;
import com.livery.demo.controller.GlideImageController;
import com.sunsta.bear.engine.LocalImageController;
import com.sunsta.bear.engine.ResImageController;
import com.sunsta.bear.layout.INAParallelImageView;

import java.io.File;

public class MdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerView recyclerView;
    private Context context;
    private String pathPrefix = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;


    public MdAdapter(RecyclerView recyclerView, Context context) {
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_image, parent, false);
            return new ImageViewViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 5:
                ImageViewViewHolder holder1 = (ImageViewViewHolder) holder;
                holder1.parallaxImageView.bindRecyclerView(recyclerView);
                holder1.parallaxImageView.setController(new ResImageController(context, R.drawable.girl));
                holder1.tvTitle.setText("加载资源图:R.mipmap.girl");
                break;
            case 10:
                ImageViewViewHolder holder2 = (ImageViewViewHolder) holder;
                String imagePath = pathPrefix + "a0.jpg";
                holder2.parallaxImageView.bindRecyclerView(recyclerView);
                holder2.parallaxImageView.setController(new LocalImageController(imagePath));
                holder2.tvTitle.setText("加载本地图: /sdcard/a0.jpg");
                break;
            case 15:
                ImageViewViewHolder holder3 = (ImageViewViewHolder) holder;
                String imageUrl = "https://www.privacypic.com/images/2020/05/21/ulaUNj.jpg";
                holder3.parallaxImageView.bindRecyclerView(recyclerView);
                holder3.parallaxImageView.setController(new GlideImageController(context, imageUrl));
                holder3.tvTitle.setText("Glide加载网络图: http://gitstar.com.cn:8000/static/img/1.jpg");
                break;
            case 20:
                ImageViewViewHolder holder4 = (ImageViewViewHolder) holder;
                String imageUrl1 = "http://gitstar.com.cn:8000/static/img/6.jpg";
                holder4.parallaxImageView.bindRecyclerView(recyclerView);
                holder4.parallaxImageView.setController(new GlideImageController(context, imageUrl1));
                holder4.tvTitle.setText("Glide加载网络图: http://gitstar.com.cn:8000/static/img/6.jpg");
                break;
            default:
                ((MyViewHolder) holder).tvTitle.setText(position + "position");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 25;
    }

    @Override
    public int getItemViewType(int position) {
        return position != 0 && position % 5 == 0 ? 1 : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        TextView tvTitle;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            tvTitle = view.findViewById(R.id.tvTitle);
        }

    }

    class ImageViewViewHolder extends RecyclerView.ViewHolder {
        View view;
        INAParallelImageView parallaxImageView;
        TextView tvTitle;

        ImageViewViewHolder(View view) {
            super(view);
            this.view = view;
            parallaxImageView = view.findViewById(R.id.parallaxImageView);
            tvTitle = view.findViewById(R.id.tvTitle);
        }
    }
}