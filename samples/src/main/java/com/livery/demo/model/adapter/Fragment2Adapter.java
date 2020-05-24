package com.livery.demo.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.livery.demo.R;

import java.util.List;

public class Fragment2Adapter extends RecyclerView.Adapter<Fragment2Adapter.ViewHolder> {
    private Context mContext;
    private List<String> listItems;

    public Fragment2Adapter(Context context, List<String> listItems) {
        this.mContext = context;
        this.listItems = listItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_refresh_header_normal, parent, false);
        if (viewType == 0) {
            return new EmptyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_error, parent, false));
        }
        return new ViewHolder(v);
    }

    class EmptyViewHolder extends Fragment2Adapter.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView, 0);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (null != listItems && listItems.size() > 0) {
            String luckyInfo = listItems.get(position);
            holder.deliverStatus.setText(luckyInfo);
        }
    }

// @Override
// public int getItemCount() {
// return listItems == null ? 0 : listItems.size();
// }

    /**
     * 有数据的时候执行该方法（先执行）|| 没有数据的时候执行（先执行）
     */
    @Override
    public int getItemCount() {
// return listItems.size();
        if (null == listItems || listItems.size() < 1) {
            return 1;
        } else {
            return listItems.size();
        }
    }

    /**
     * 有数据的时候执行该方法（后执行），没有数据的时候（该方法不执行）如果要执行该方法必须把getItemCount的值为空或者小于1的情况变为1
     * 可以根据type的值来判断或者执行-加载相应的布局资源
     */
    @Override
    public int getItemViewType(int position) {
//根据传入adapter来判断是否有数据
        if (null == listItems || listItems.size() < 1) {
            return 0;
        }
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemLayout;
        TextView deliverStatus;
        /*错误*/
        ImageView ivError;

        public ViewHolder(View v) {
            super(v);
            deliverStatus = v.findViewById(R.id.tv_normal_refresh_header_status);
        }

        public ViewHolder(View v, int type) {
            super(v);
            ivError = v.findViewById(R.id.ivError);
            ivError.setImageResource(R.drawable.default_error_nodata);

        }
    }

    private ListClickListener mListener;

    public interface ListClickListener {
        void onListClick(int position, String luckyInfo);
    }

    public void setListClickListener(ListClickListener mListener) {
        this.mListener = mListener;
    }
}