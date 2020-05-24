package com.livery.demo.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.livery.demo.R;
import com.livery.demo.model.adapter.MainFragmentAdapter;
import com.livery.demo.module.internet.FolderActivity;
import com.livery.demo.module.internet.InternetActivity;
import com.livery.demo.module.internet.OrignalRetrofitActivity;
import com.livery.demo.module.internet.UpDownloadActivity;
import com.livery.demo.module.pic.MainPictureActivity;
import com.livery.demo.view.activity.BeforeSelectAlbumActivity;
import com.livery.demo.view.activity.EsayPermissionActivity;
import com.livery.demo.module.internet.DownloadActivity;
import com.sunsta.bear.faster.ToastUtils;
import com.sunsta.bear.layout.swipe.widget.DefaultItemDecoration;
import com.sunsta.bear.listener.OnItemClickListener;

import java.util.Arrays;
import java.util.List;

public class SecondFragment extends Fragment implements OnItemClickListener {
    private RecyclerView recyclerView;
    private MainFragmentAdapter mainFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置横向
        recyclerView.setLayoutManager(layoutManager);
        DefaultItemDecoration listItemDecoration = new DefaultItemDecoration(getResources().getColor(R.color.an_color_line), 80, 1);
        recyclerView.addItemDecoration(listItemDecoration);
        mainFragmentAdapter = new MainFragmentAdapter(getContext(), createDataList());
        recyclerView.setAdapter(mainFragmentAdapter);
        mainFragmentAdapter.setItemClickListener(this);
        return view;
    }

    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.main_an_scene));
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                ToastUtils.s(getContext(), "livery1.1.11不再支持该请求，请使用下面的网络请求");
                break;
            case 1:
                startActivity(new Intent(getContext(), OrignalRetrofitActivity.class));
                break;
            case 2:
                startActivity(new Intent(getContext(), InternetActivity.class));
                break;
            case 3:
                startActivity(new Intent(getContext(), UpDownloadActivity.class));
                break;
            case 4:
                startActivity(new Intent(getContext(), DownloadActivity.class));
                break;
            case 5:
                startActivity(new Intent(getContext(), BeforeSelectAlbumActivity.class));
                break;
            case 6:
                startActivity(new Intent(getContext(), MainPictureActivity.class));
                break;
            case 7:
                startActivity(new Intent(getContext(), EsayPermissionActivity.class));
                break;
            case 8:
                startActivity(new Intent(getContext(), FolderActivity.class));
                break;
        }
    }
}