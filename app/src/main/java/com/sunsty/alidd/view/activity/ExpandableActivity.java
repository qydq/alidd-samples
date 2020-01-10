package com.sunsty.alidd.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ali.module.lib.tools.ToastUtils;
import com.ali.view.dd.NestExpandableListView;
import com.sunsty.alidd.R;
import com.sunsty.alidd.model.adapter.ExpandableAdapter;

public class ExpandableActivity extends AppCompatActivity {
    private static final String TAG = "HttpsRequestActivity";
    private NestExpandableListView nestExpandableListView;
    /*alidd有一个ExpandableAdpater不要导包错误*/
    private ExpandableAdapter adapter;

    private boolean isTurnOff = false;//tab间是否是互斥的

    //Model：定义的数据
    private String[] groups = {"齐", "楚", "燕", "韩", "赵", "魏", "秦"};

    //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}
    private String[][] childs = {{"匡章", "田单", "田朌", "田忌"}
            , {"项燕", "昭阳"}
            , {"乐毅"}
            , {"冯亭"}
            , {"李牧", "赵奢", "廉颇"}
            , {"庞涓", "乐羊", "吴起"}
            , {"秦孝公", "商鞅", "公孙衍", "冉侯", "白起", "王剪"}};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);
        TextView tvStatus = findViewById(R.id.tvStatus);
        nestExpandableListView = findViewById(R.id.nestExpandableListView);

        adapter = new ExpandableAdapter(this, groups, childs);
        nestExpandableListView.setAdapter(adapter);
        nestExpandableListView.setGroupIndicator(null);//去掉系统自带的展开图标


        //默认展开第一个数组
        nestExpandableListView.expandGroup(0);
        //关闭数组某个数组，可以通过该属性来实现全部展开和只展开一个列表功能
        //nestExpandableListView.collapseGroup(0);
        nestExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                ToastUtils.s(ExpandableActivity.this, groups[groupPosition]);
                tvStatus.setText(groups[groupPosition]);
                return false;
            }
        });
        //子视图的点击事件
        nestExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                ToastUtils.s(ExpandableActivity.this, childs[groupPosition][childPosition]);
                tvStatus.setText(childs[groupPosition][childPosition]);
                return true;
            }
        });
        //用于当组项折叠时的通知。
        nestExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ToastUtils.s(ExpandableActivity.this, "折叠了数据___" + groups[groupPosition]);
                tvStatus.setText("折叠了数据___" + groups[groupPosition]);

            }
        });
        //
        //用于当组项折叠时的通知。
        nestExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ToastUtils.s(ExpandableActivity.this, "展开了数据___" + groups[groupPosition]);
                tvStatus.setText("展开了数据___" + groups[groupPosition]);
                if (isTurnOff) {
                    if (adapter.getChildrenCount(groupPosition) >= 0) {
                        nestExpandableListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_NORMAL);
                    }
                    int adapterCount = adapter.getGroupCount();
                    int expandableListCount = nestExpandableListView.getExpandableListAdapter().getGroupCount();
                    tvStatus.setText("adapterCount=" + adapterCount + ":expandableListCount = " + expandableListCount);
                    for (int i = 0; i < adapterCount; i++) {
                        if (i != groupPosition) {
                            nestExpandableListView.collapseGroup(i);
                        }
                    }
                }
            }
        });

        //添加一个按钮，用于测试expandGroup(int groupPos, boolean animate)中的animate不同值的显示效果.
        Button expandBtn = findViewById(R.id.btnControl);
        expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nestExpandableListView.expandGroup(2, false);
                //展开时，是否执行滑动过程; 建议在测试时，选择展开后列表能够超出屏幕的，更容易看到两者不同
                //看源码，发现为true时执行了方法smoothScrollToPosition(position, boundPosition)
                //在默认情况下，点击group选项，系统也会执行上述smoothScrollToPosition方法.
            }
        });

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTurnOff) {
                    isTurnOff = true;
                } else {
                    isTurnOff = false;
                }
                tvStatus.setText(isTurnOff ? "已开启互斥点击" : "已关闭互斥点击");
            }
        });
    }
}