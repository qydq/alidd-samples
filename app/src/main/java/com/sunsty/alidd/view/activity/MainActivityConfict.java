package com.sunsty.alidd.view.activity;

//import com.sunsty.alidd.model.adapter.MainAdapter;
//import com.yanzhenjie.recyclerview.OnItemClickListener;
//import com.yanzhenjie.recyclerview.SwipeRecyclerView;


/**
 * <p>
 * Alidd - Samples入口。
 * </p>
 * Created by Sunst on 2017/7/22.
 */
public class MainActivityConfict  {
//    extends SuperActivity implements OnItemClickListener
//    protected Toolbar mToolbar;
//    protected ActionBar mActionBar;
//    protected SwipeRecyclerView mRecyclerView;
//    protected RecyclerView.LayoutManager mLayoutManager;
//    protected RecyclerView.ItemDecoration mItemDecoration;
//
//    protected BaseAdapter mAdapter;
//    protected List<String> mDataList;
//
//    @Override
//    public void initView() {
//        setContentView(R.ic_launcher_foreground.activity_mediac_decode);
//        mToolbar = findViewById(R.id.toolbar);
//        mRecyclerView = findViewById(R.id.swipeRecyclerView);
//        setSupportActionBar(mToolbar);
//        mActionBar = getSupportActionBar();
//        if (displayHomeAsUpEnabled()) {
//            mActionBar.setDisplayHomeAsUpEnabled(true);
//        }
//
//        mLayoutManager = createLayoutManager();
//        mItemDecoration = createItemDecoration();
//        mDataList = createDataList();
//        mAdapter = createAdapter();
//
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(mItemDecoration);
//        mRecyclerView.setOnItemClickListener(this);
//
//        mRecyclerView.setAdapter(mAdapter);
//
//        mAdapter.notifyDataSetChanged(mDataList);
//    }
//
//
//    protected boolean displayHomeAsUpEnabled() {
//        return false;
//    }
//
//    protected List<String> createDataList() {
//        return Arrays.asList(getResources().getStringArray(R.array.main_item));
//    }
//
//    protected RecyclerView.LayoutManager createLayoutManager() {
//        return new LinearLayoutManager(this);
//    }
//
//    protected RecyclerView.ItemDecoration createItemDecoration() {
//        return new DefaultItemDecoration(ContextCompat.getColor(this, R.color.an_color_line));
//    }
//
//    protected BaseAdapter createAdapter() {
//        return new MainAdapter(this);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return true;
//    }
//
//    @Override
//    public void onItemClick(View view, int adapterPosition) {
//        showToast("第" + adapterPosition + "个");
//        switch (adapterPosition) {
//            case 0: {
//                startActivity(new Intent(this, AnimationGifActivity.class));
//                break;
//            }
//            case 1: {
//                startActivity(new Intent(this, HttpsRequestActivity.class));
//                break;
//            }
//            case 2: {
//                startActivity(new Intent(this, INATabLayoutActivity.class));
//                break;
//            }
//            case 3: {
//                startActivity(new Intent(this, TranslucentActivity.class));
//                break;
//            }
//            case 4: {
//                startActivity(new Intent(this, VideoNoFFmpegActivity.class));
//                break;
//            }
//            case 5: {
//                startActivity(new Intent(this, ExpandableActivity.class));
//                break;
//            }
//        }
//    }
}