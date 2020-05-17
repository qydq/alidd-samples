package com.livery.demo.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.livery.demo.R;
import com.livery.demo.model.adapter.MainFragmentAdapter;
import com.livery.demo.module.md.MainMdActivity;
import com.livery.demo.module.pager.MainPagerActivity;
import com.livery.demo.view.activity.AliParallaxActivity;
import com.livery.demo.view.activity.AnimationGifActivity;
import com.livery.demo.view.activity.DefaultRefActivity;
import com.livery.demo.view.activity.ExpandableActivity;
import com.livery.demo.view.activity.INATabLayoutActivity;
import com.livery.demo.view.activity.StandardStoryActivity;
import com.livery.demo.module.swipe.MainSwipeRecycleActivity;
import com.sunsta.bear.faster.ToastUtils;
import com.sunsta.bear.listener.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

/*
 * (1).sunst提供：[an专栏]情景系列，livery框架示例主入口Fragment
 *     如果有帮助到你，真诚的邀请你，关注知乎<a href="https://zhihu.com/people/qydq">Bgwan</a>
 *     2020年05月：目前需要集齐1000+个关注者，感谢🙏
 * */
public class MainFragment extends Fragment implements OnItemClickListener {
    private RecyclerView recyclerView;
    private MainFragmentAdapter mainFragmentAdapter;
    private View mFLayout;
    private EditText editText;
    private ImageView ivBackground;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        mFLayout = view.findViewById(R.id.fl_layout);
        editText = view.findViewById(R.id.editText);
        ivBackground = view.findViewById(R.id.ivBackground);
        AppBarLayout mAppBarLayout = view.findViewById(R.id.appbar);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置横向
        recyclerView.setLayoutManager(layoutManager);
//        DefaultItemDecoration listItemDecoration = new DefaultItemDecoration(getResources().getColor(R.color.an_color_line), 80, 0.1);
//        recyclerView.addItemDecoration(listItemDecoration);
        mainFragmentAdapter = new MainFragmentAdapter(getContext(), createDataList());
        recyclerView.setAdapter(mainFragmentAdapter);
        mainFragmentAdapter.setItemClickListener(this);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                //标准注释1：第一种写法
//                int toolbarHeight = appBarLayout.getTotalScrollRange();
//                int dy = Math.abs(verticalOffset);
//                if (dy <= toolbarHeight) {
//                    float scale = (float) dy / toolbarHeight;
//                    float alpha = scale * 255;
//                    mFLayout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
//                    editText.setText("alpha=" + (int) alpha + "," + "percent" + percent);
//                    if ((int) alpha > 100 && alpha <= 255) {
//                        StatusBarUtils.fitStatusBar(getActivity(), true, true);
//                    }
//                    if (alpha == 0 || alpha < 20) {
//                        StatusBarUtils.fitStatusBar(getActivity(), false, true);
//                    }
//                }
                //标准注释2：第二种写法
                mFLayout.setAlpha(percent);
            }
        });
        ivBackground.setOnClickListener(view1 -> ToastUtils.showSelfLongToast("本页面视频播放功能，正在开发种,ing，敬请期待."));
        return view;
    }

    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.main_material_ux));
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getContext(), INATabLayoutActivity.class));
                break;
            case 1:
                startActivity(new Intent(getContext(), MainMdActivity.class));
                break;
            case 2:
                startActivity(new Intent(getContext(), MainPagerActivity.class));
                break;
            case 3:
                startActivity(new Intent(getContext(), AnimationGifActivity.class));
                break;
            case 4:
                startActivity(new Intent(getContext(), StandardStoryActivity.class));
                break;
            case 5:
                startActivity(new Intent(getContext(), AliParallaxActivity.class));
                break;
            case 6:
                startActivity(new Intent(getContext(), ExpandableActivity.class));
                break;
            case 7:
                startActivity(new Intent(getContext(), MainSwipeRecycleActivity.class));
                break;
            case 8:
                startActivity(new Intent(getContext(), DefaultRefActivity.class));
                break;
            default:
                ToastUtils.s(getContext(), createDataList().get(position));
                break;
        }
    }
}