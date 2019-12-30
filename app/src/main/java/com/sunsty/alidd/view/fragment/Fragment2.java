package com.sunsty.alidd.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sunsty.alidd.R;

public class Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        TextView tvContent = view.findViewById(R.id.content);
        Bundle bundle = getArguments();
        if (null != bundle) {
            String content = bundle.getString("content");
            if (!TextUtils.isEmpty(content)) {
                tvContent.setText(content);
            }
        }
        return view;
    }
}
