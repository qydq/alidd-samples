package com.sunsty.alidd.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.module.lib.tools.ToastUtils;
import com.ali.take.LADialog;
import com.ali.view.callback.OnItemClickListener;
import com.ali.view.swipelayout.widget.DefaultItemDecoration;
import com.sunsty.alidd.R;
import com.sunsty.alidd.model.adapter.RecyclerAdapter;

import java.util.Arrays;
import java.util.List;

public class LogicalFragment extends Fragment implements OnItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alidd, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置横向
        recyclerView.setLayoutManager(layoutManager);
        DefaultItemDecoration listItemDecoration = new DefaultItemDecoration(getResources().getColor(R.color.an_color_line), 80, 1);
        recyclerView.addItemDecoration(listItemDecoration);
        recyclerAdapter = new RecyclerAdapter(getContext(), createDataList());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setItemClickListener(this);
        return view;
    }

    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.main_logical));
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                ToastUtils.s("我的测试对话框" + position, R.drawable.ic_drawable_copy_fav);
                break;
            case 1:
                ToastUtils.s("我的测试对话框" + position, R.drawable.ic_drawable_mail);
                break;
            case 2:
                ToastUtils.s("我的测试对话框" + position);
                break;
            case 3:
                ToastUtils.s(getContext(), "我的测试对话框" + position);
                break;
            case 4:
                ToastUtils.sc(getContext(), "我的测试对话框" + position);
                break;
            case 5:
                LADialog.INSTANCE.createDialog(getActivity()).show();
                break;
            case 6:
                LADialog.INSTANCE.createDialog(getActivity(), false).show();
            default:
                break;
        }
    }
}
