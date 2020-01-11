package com.sunsty.alidd.view.activity.swipe.header;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ali.view.callback.OnItemClickListener;
import com.ali.view.swipelayout.SwipeRecyclerView;
import com.ali.view.swipelayout.widget.DefaultItemDecoration;
import com.sunsty.alidd.R;
import com.sunsty.alidd.model.adapter.SwipeRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class HeaderViewActivity extends AppCompatActivity implements OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiperecycler);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        SwipeRecyclerView recyclerView = findViewById(R.id.swipeRecyclerView);
        recyclerView.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.multiple_image_select_divider)));

        // HeaderView。
        View headerView = getLayoutInflater().inflate(R.layout.layout_header, recyclerView, false);
        headerView.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "HeaderView", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addHeaderView(headerView);

        // FooterView。
        View footerView = getLayoutInflater().inflate(R.layout.layout_footer, recyclerView, false);
        footerView.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "FooterView", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addFooterView(footerView);

        SwipeRecyclerAdapter mainAdapter = new SwipeRecyclerAdapter(this);
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.notifyDataSetChanged(createDataList());
    }

    protected List<String> createDataList() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add("第" + i + "个Item");
        }
        return strings;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}