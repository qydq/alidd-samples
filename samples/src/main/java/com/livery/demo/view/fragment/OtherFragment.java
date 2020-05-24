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

import com.sunsta.bear.faster.LADialog;
import com.sunsta.bear.faster.MyDialog;
import com.sunsta.bear.faster.ToastUtils;
import com.sunsta.bear.layout.swipe.widget.DefaultItemDecoration;
import com.sunsta.bear.view.activity.AliWebActivity;
import com.sunsta.bear.listener.OnItemClickListener;
import com.example.cj.videoeditor.activity.VideoBeatyMainActivity;
import com.livery.demo.R;
import com.livery.demo.model.adapter.MainFragmentAdapter;
import com.livery.demo.view.activity.TranslucentActivity;
import com.livery.demo.view.activity.VideoNoFFmpegActivity;
import com.sunsty.xmediac.FFmpegRenderActivity;

import java.util.Arrays;
import java.util.List;

public class OtherFragment extends Fragment implements OnItemClickListener {
    private RecyclerView recyclerView;
    private MainFragmentAdapter mainFragmentAdapter;

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
        mainFragmentAdapter = new MainFragmentAdapter(getContext(), createDataList());
        recyclerView.setAdapter(mainFragmentAdapter);
        mainFragmentAdapter.setItemClickListener(this);
        return view;
    }

    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.main_other));
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(getContext(), AliWebActivity.class);
                intent.putExtra("url", "https://github.com/qydq/alidd-samples");
                intent.putExtra("title", "Github官方alidd框架");
                startActivity(intent);
                break;
            case 1:
                intent.setClass(getContext(), AliWebActivity.class);
                intent.putExtra("url", "https://zhihu.com/people/qydq");
                intent.putExtra("title", "知乎Bgwan（点击关注）");
                startActivity(intent);
                break;
            case 2:
                startActivity(new Intent(getContext(), VideoNoFFmpegActivity.class));
                break;
            case 3:
                startActivity(new Intent(getContext(), FFmpegRenderActivity.class));
                break;
            case 4:
                //todo :视频情景3：sunst
                intent.setClass(getContext(), VideoBeatyMainActivity.class);
                startActivity(intent);
                break;
            case 5:
                startActivity(new Intent(getContext(), TranslucentActivity.class));
                break;
            case 6:
                startActivity(new Intent(getContext(), TranslucentActivity.class));
                break;
            case 7:
                MyDialog myDialog = new MyDialog(getContext(), R.style.an_dialog_middle_pure_image, LADialog.STYLE.middle_pure_image);
                myDialog.setImageUrl("https://ae01.alicdn.com/kf/U6de089ce45ff468a8f06c50e19ad7379N.jpg");
////        myDialog.setImageUrl("http://i.imagseur.com/uploads/gifs/gif_10-05-2015/9349392.gif");
////        myDialog.setImageUrl("http://i.imagseur.com/uploads/gifs/gif_16-05-2015/49737-beautiful-asian-banged-hard.gif");
                myDialog.show();
                break;
            case 8:
                ToastUtils.showSelfToast("视频播放功能正在开发中...." + position, R.drawable.ic_color_copy_fav);
                break;
            case 9:
                ToastUtils.showSelfToast("app只能语音唤醒功能正在开发中...." + position, R.drawable.ic_white_mail);
                break;
            case 10:
                ToastUtils.showSelfToast("这位同学，敢不敢点开我知乎，关注一波呀，求关注呀" + position);
                break;
            case 11:
                ToastUtils.sc(getContext(), "关注知乎Bgwan解锁更多姿势1" + position);
                break;
            case 12:
                ToastUtils.s(getContext(), "关注知乎Bgwan解锁更多姿势2" + position);
                break;

        }
    }
}
