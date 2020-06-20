package com.livery.demo.module.md;

import androidx.recyclerview.widget.RecyclerView;

import com.livery.demo.R;
import com.livery.demo.module.md.adapter.MdAdapter;
import com.sunsta.bear.view.AliActivity;

import java.util.Arrays;
import java.util.List;

public class TestActivity6 extends AliActivity {
    private RecyclerView recyclerView;

    @Override
    public void initView() {
        setContentView(R.layout.activity_parallel_image);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new MdAdapter(recyclerView, TestActivity6.this));
    }

    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.main_material_ux));
    }
}
