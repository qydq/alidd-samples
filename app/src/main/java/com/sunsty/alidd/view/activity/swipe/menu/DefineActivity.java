package com.sunsty.alidd.view.activity.swipe.menu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.layout.swipe.SwipeMenuLayout;
import com.sunsty.alidd.R;
import com.sunsty.alidd.model.adapter.BaseAliAdapter;
import com.sunsty.alidd.view.activity.swipe.BaseActivity;

import java.util.List;

/**
 * 利用SwipeMenuLayout自定义菜单。
 */
public class DefineActivity extends BaseActivity {

    private SwipeMenuLayout mSwipeMenuLayout;

    @Override
    protected int getContentView() {
        return R.layout.activity_menu_define;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView.setAdapter(mAdapter);

        mSwipeMenuLayout = findViewById(R.id.swipe_layout);
        TextView btnLeft = findViewById(R.id.left_view);
        TextView btnRight = findViewById(R.id.right_view);

        btnLeft.setOnClickListener(xOnClickListener);
        btnRight.setOnClickListener(xOnClickListener);
    }

    @Override
    protected BaseAliAdapter createAdapter() {
        return new DefineAdapter(this);
    }

    /**
     * 就是这个适配器的Item的Layout需要处理，其实和CardView的方式一模一样。
     */
    private static class DefineAdapter extends BaseAliAdapter<ViewHolder> {

        DefineAdapter(Context context) {
            super(context);
        }

        @Override
        public void notifyDataSetChanged(List<String> dataList) {
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_define, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button mLeftBtn, mMiddleBtn, mRightBtn;

        ViewHolder(View itemView) {
            super(itemView);

            mLeftBtn = itemView.findViewById(R.id.left_view);
            mMiddleBtn = itemView.findViewById(R.id.btn_start);
            mRightBtn = itemView.findViewById(R.id.right_view);
            mLeftBtn.setOnClickListener(this);
            mMiddleBtn.setOnClickListener(this);
            mRightBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_view: {
                    Toast.makeText(v.getContext(), "我是第" + getAdapterPosition() + "个Item的左边的Button", Toast.LENGTH_SHORT)
                        .show();
                    break;
                }
                case R.id.btn_start: {
                    Toast.makeText(v.getContext(), "我是第" + getAdapterPosition() + "个Item的中间的Button", Toast.LENGTH_SHORT)
                        .show();
                    break;
                }
                case R.id.right_view: {
                    Toast.makeText(v.getContext(), "我是第" + getAdapterPosition() + "个Item的右边的Button", Toast.LENGTH_SHORT)
                        .show();
                    break;
                }
            }
        }
    }

    /**
     * 一般的点击事件。
     */
    private View.OnClickListener xOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.left_view) {
                mSwipeMenuLayout.smoothCloseMenu();// 关闭菜单。
                Toast.makeText(DefineActivity.this, "我是左面的", Toast.LENGTH_SHORT).show();
            } else if (v.getId() == R.id.right_view) {
                mSwipeMenuLayout.smoothCloseMenu();// 关闭菜单。
                Toast.makeText(DefineActivity.this, "我是右面的", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onItemClick(View itemView, int position) {
        Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected List<String> createDataList() {
        return null;
    }
}