package com.sunsty.alidd.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ali.view.dd.NestExpandableListView;
import com.sunsty.alidd.R;
import com.sunsty.alidd.model.adapter.ExpandableAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExpandableActivity extends AppCompatActivity {
    private static final String TAG = "HttpsRequestActivity";
    private TextView tvStatus;
    private NestExpandableListView nestExpandableListView;
    private ExpandableAdapter adapter;
    private List<String> group = new ArrayList<>();
    private List<String> item = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);
        tvStatus = findViewById(R.id.tvHttps);
        nestExpandableListView = findViewById(R.id.nestExpandableListView);
        group.add("sunst1");
        group.add("sunst2");

        item.add("more item1");
        item.add("more item2");

        adapter = new ExpandableAdapter(group, item, this);
        nestExpandableListView.setAdapter(adapter);
        nestExpandableListView.setGroupIndicator(null);
    }
}