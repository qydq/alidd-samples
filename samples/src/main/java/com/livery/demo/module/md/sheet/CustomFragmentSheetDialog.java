package com.livery.demo.module.md.sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.livery.demo.R;
import com.livery.demo.model.adapter.Fragment1Adapter;

import java.util.ArrayList;

/*CustomFragmentDialog 最终显示的高度 决定于内容的高度*/
public class CustomFragmentSheetDialog extends BottomSheetDialogFragment {
    private ArrayList<String> list = new ArrayList<>();

    public CustomFragmentSheetDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.md_fragment_sheet, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        for (int i = 0; i < 100; i++) {
            list.add("条目" + i);
        }
        RecyclerView recyclerView = view.findViewById(R.id.dialog_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Fragment1Adapter adapter = new Fragment1Adapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
    }
}