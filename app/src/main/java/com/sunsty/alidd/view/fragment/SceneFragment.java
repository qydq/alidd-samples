package com.sunsty.alidd.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.view.callback.OnItemClickListener;
import com.ali.view.swipelayout.widget.DefaultItemDecoration;
import com.sunsty.alidd.R;
import com.sunsty.alidd.model.adapter.RecyclerAdapter;
import com.sunsty.alidd.view.activity.JustHttpsActivity;

import java.util.Arrays;
import java.util.List;

public class SceneFragment extends Fragment implements OnItemClickListener {
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
        return Arrays.asList(getResources().getStringArray(R.array.main_an_scene));
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0: {
                startActivity(new Intent(getContext(), JustHttpsActivity.class));
                break;
            }
            case 1: {
//                startActivity(new Intent(getContext(), AnimationGifActivity.class));
                break;
            }
            case 2: {
//                startActivity(new Intent(getContext(), INATabLayoutActivity.class));
                break;
            }
            case 3: {
//                startActivity(new Intent(getContext(), TranslucentActivity.class));
                break;
            }
            case 4: {
//                startActivity(new Intent(getContext(), VideoNoFFmpegActivity.class));
                break;
            }
            case 5: {
//                startActivity(new Intent(getContext(), ExpandableActivity.class));
                break;
            }
        }
    }
}