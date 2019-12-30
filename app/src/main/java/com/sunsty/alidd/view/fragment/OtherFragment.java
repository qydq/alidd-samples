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

import com.ali.view.activity.AliwebViewActivity;
import com.ali.view.callback.OnItemClickListener;
import com.ali.view.swipelayout.widget.DefaultItemDecoration;
import com.sunsty.alidd.R;
import com.sunsty.alidd.model.adapter.RecyclerAdapter;
import com.sunsty.alidd.view.activity.AnimationGifActivity;
import com.sunsty.alidd.view.activity.ExpandableActivity;
import com.sunsty.alidd.view.activity.INATabLayoutActivity;
import com.sunsty.alidd.view.activity.TranslucentActivity;
import com.sunsty.alidd.view.activity.VideoActivity;
import com.sunsty.xmediac.MediacDecodeActivity;

import java.util.Arrays;
import java.util.List;

public class OtherFragment extends Fragment implements OnItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material_ux, container, false);
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
        return Arrays.asList(getResources().getStringArray(R.array.main_other));
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        switch (position) {
            case 0: {
                intent.setClass(getContext(), AliwebViewActivity.class);
                intent.putExtra("url", "https://github.com/qydq/alidd-samples");
                intent.putExtra("title", "Github官方alidd框架");
                startActivity(intent);
                break;
            }
            case 1: {
                startActivity(new Intent(getContext(), MediacDecodeActivity.class));
                break;
            }
            case 2: {
                startActivity(new Intent(getContext(), INATabLayoutActivity.class));
                break;
            }
            case 3: {
                startActivity(new Intent(getContext(), TranslucentActivity.class));
                break;
            }
            case 4: {
                startActivity(new Intent(getContext(), VideoActivity.class));
                break;
            }
            case 5: {
                startActivity(new Intent(getContext(), ExpandableActivity.class));
                break;
            }
        }
    }
}
