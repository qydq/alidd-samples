package com.sunsty.alidd.view.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ali.presenter.net.JustAsyncManager;
import com.ali.presenter.net.JustNetApi;
import com.ali.presenter.net.JustNetClient;
import com.ali.take.Convert;
import com.ali.take.LaLog;
import com.ali.view.ParallaxActivity;
import com.sunsty.alidd.R;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JustHttpsActivity extends ParallaxActivity {
    private static final String TAG = "JustHttpsActivity";
    private TextView tvHttps;
    private TextView tvZfb;
    private TextView tvWx;
    private ImageView natureIv;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            tvHttps.setText(msg.obj.toString());
            return false;
        }
    });

    /**
     * 检测是否安装支付宝
     *
     * @param context
     * @return
     */
    public boolean isAliPayInstalled() {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(getPackageManager());
        return componentName != null;
    }

    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public boolean isWeixinAvilible() {
        final PackageManager packageManager = getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_https);
        tvHttps = findViewById(R.id.tvHttps);
        tvZfb = findViewById(R.id.tvZfb);
        natureIv = findViewById(R.id.natureIv);
        tvWx = findViewById(R.id.tvWx);

        tvHttps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                retrofitRequest();//ok

//                turnBiniNet();//ok

                justNetRequest();//ok

            }
        });

        tvWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showToast(isWeixinAvilible() ? "安装了微信" : "未安装微信");
            }
        });
        tvZfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(isAliPayInstalled() ? "安装了支付宝" : "未安装支付宝");
            }
        });

    }

    @SuppressWarnings("unchecked")
    private void justNetRequest() {
        JustAsyncManager justManager = new JustAsyncManager();

//        ((BiniNetApi)JustNet.getInstance().just("https://baidu.com/",BiniNetApi.class)).observableGet();
//        ((BiniNetApi)JustNet.getInstance().just(BiniNetApi.class)).observableGet();
//
//        JustNet.getInstance().justWith("https://192.168.0.11").observableGet("json");
//        JustNet.getInstance().justWith().observableGet("json");

        justManager.add(((JustNetApi<ResponseBody>) JustNetClient.init().just("https://www.wanandroid.com/banner/", JustNetApi.class))
                .observableGet("json")
                .compose(Convert.io_main())
                .subscribe(this::hasData));
//                startActivity(new Intent(BackMainActivity.this, WebViewActivity.class));
    }

    public void hasData(ResponseBody responseBody) {
        LaLog.d("respon :data " + responseBody.toString());
        try {
            String justData = Objects.requireNonNull(responseBody).string();
            LaLog.d(TAG + "：justData = " + justData);
            if (!TextUtils.isEmpty(justData)) {
                tvHttps.setText(justData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 原生Retrofit网络请求
     */
    private void retrofitRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl("https://www.wanandroid.com/banner/")
                .build();
        JustNetApi api = retrofit.create(JustNetApi.class);
        Call<ResponseBody> call = api.tGetWith("json");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //成功返回数据后在这里处理，使用response.body();获取得到的结果

                if (response.isSuccessful()) {
                    ResponseBody news = response.body();
                    try {
                        String justData = Objects.requireNonNull(news).string();
                        LaLog.d(TAG + "：justData = " + justData);
                        if (!TextUtils.isEmpty(justData)) {
                            Message message = mHandler.obtainMessage();
                            message.obj = justData;
                            mHandler.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //请求失败在这里处理
                LaLog.d(TAG + "：Just error " + call.toString());
            }
        });
    }

    /**
     * 验证ok2
     */
    private void turnBiniNet() {
        JustNetApi api = JustNetClient.init().getRetrofit("https://www.wanandroid.com/banner/").create(JustNetApi.class);

        Call<ResponseBody> call = api.callHttpGetWith("json");
        if (call != null) {
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //成功返回数据后在这里处理，使用response.body();获取得到的结果
                    ResponseBody news = response.body();
                    try {
                        String justData = Objects.requireNonNull(news).string();
                        LaLog.d(TAG + "：justData = " + justData);
                        if (!TextUtils.isEmpty(justData)) {
                            Message message = mHandler.obtainMessage();
                            message.obj = justData;
                            mHandler.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //请求失败在这里处理
                    LaLog.d(TAG + "call error " + call.toString());
                }
            });
        }
    }
}