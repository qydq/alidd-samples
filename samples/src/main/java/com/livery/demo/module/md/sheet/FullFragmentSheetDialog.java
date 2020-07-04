package com.livery.demo.module.md.sheet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.livery.demo.R;
import com.livery.demo.model.adapter.Fragment1Adapter;

import java.util.ArrayList;

public class FullFragmentSheetDialog extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;
    private ArrayList<String> list = new ArrayList<>();

    public FullFragmentSheetDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.md_fragment_sheet, null);
        initViews(view);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());

        return dialog;
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

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//全屏展开
    }
}