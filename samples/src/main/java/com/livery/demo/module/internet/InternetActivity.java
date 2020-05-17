package com.livery.demo.module.internet;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.livery.demo.R;
import com.livery.demo.model.ReplyGithubUserMode;
import com.livery.demo.model.ReplyMasterMenuInfoMode;
import com.livery.demo.model.TBaseResponseMode;
import com.livery.demo.net.ApiInternetImpl;
import com.livery.demo.net.LiveryInternetApi;
import com.sunsta.bear.faster.Convert;
import com.sunsta.bear.faster.LaPermissions;
import com.sunsta.bear.presenter.callback.ObserverStringCallback;
import com.sunsta.bear.presenter.net.InternetAsyncManager;
import com.sunsta.bear.presenter.net.InternetClient;
import com.sunsta.bear.presenter.net.InternetException;
import com.sunsta.bear.presenter.net.InternetObserver;
import com.sunsta.bear.view.AliActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;


public class InternetActivity extends AliActivity implements View.OnClickListener {
    private TextView tvContent;
    private Button btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnOther;
    private TextView tvZfb;
    private TextView tvWx;
    private ImageView natureIv;


    private static String BASE_URL = "https://gank.io/api/v2/";
    private static String Full_URL = "https://gank.io/api/v2/banners";
    private String printResult = "";
    private LocalBroadcastManager bManager;


    public static final String MESSAGE_PROGRESS = "message_progress";
    private LaPermissions inaPermissions;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            tvContent.setText(msg.obj.toString());
            return false;
        }
    });


    @Override
    public void initView() {
        setContentView(R.layout.internet_activity);
        inaPermissions = new LaPermissions(InternetActivity.this);
        tvContent = findViewById(R.id.tvContent);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnOther = findViewById(R.id.btnOther);

        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnOther.setOnClickListener(this);

        tvZfb = findViewById(R.id.tvZfb);
        natureIv = findViewById(R.id.natureIv);
        tvWx = findViewById(R.id.tvWx);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn2:
                click2();
                break;
            case R.id.btn3:
                click3();
                break;
            case R.id.btn4:
                click4();
                break;
            case R.id.btn5:
                click5();
                break;
            case R.id.btn6:
                click6();
                break;
            case R.id.btn7:
                click7();
                break;
            case R.id.btn8:
                click8();
                break;
            case R.id.btn9:
                click9();
                break;
            case R.id.btnOther:
                defaultClick();
                break;
            default:
                defaultClick();
                break;
        }
    }

    /**
     * (2)最简单的通过写ApiInternetImpl去请求网络，这里以获取知乎专栏数据为例
     * <p>
     * 获取的是转换以后的json数据
     * (备注：这里获取的数据为Unicode,未转换可以参考click1中的转换)
     */
    public void click2() {
        Map<String, Object> params = new HashMap<>();
        params.put("token", "getToken('tokenKey','default_token')");
        params.put("terminal", "ANDROID");
        params.put("appType", "test");
        params.put("model", Build.BRAND + "," + Build.MODEL);
        ApiInternetImpl apiImpl = new ApiInternetImpl();
        apiImpl.get_observable_Zhihu_Columns(new InternetObserver(new ObserverStringCallback() {
            @Override
            public void onSuccess(String result) {
                printResult = btn2.getText().toString() + "\n请求成功:\n" + result;
                tvContent.setText(printResult);
            }

            @Override
            public void onFault(String error) {
                printResult = btn2.getText().toString() + "请求数据失败2:error=\n" + error;
                tvContent.setText(printResult);
            }
        }), params);
    }

    /**
     * (3)使用最简单的RxManger管理工具类InternetAsyncManager，这里用：https://gank.io/api/v2/
     * <p>
     * 获取的是原始的ResponseBody
     */
    public void click3() {
        LiveryInternetApi api = InternetClient.getInstance().getRetrofit(BASE_URL).create(LiveryInternetApi.class);
        InternetAsyncManager apiManager = new InternetAsyncManager();
        apiManager.add(api.get_observable_GankBanners().compose(Convert.io_main())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody rb) {
                        try {
                            String result = Objects.requireNonNull(rb).string();
                            if (!TextUtils.isEmpty(result)) {
                                printResult = btn3.getText().toString() + "\n请求成功:\n" + result;
                                tvContent.setText(printResult);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new InternetException() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        printResult = btn3.getText().toString() + "请求数据失败2:errorMsg=\n" + errorMsg + "errorCode=" + errorMsg;
                        tvContent.setText(printResult);
                    }
                }));
    }

    /**
     * (4),使用，因为需要转换为bean，这里使用这个接口，数据类似这样简单一点
     * {"code":"success","msg":"成功^_^","data":{"masterMenuInfo":[{"collectionId":-1,"menuName":"推荐","position":1},
     * {"collectionId":-1,"menuName":"电竞","position":2}]}}
     * https://www.fire18.tv/live/api/homepage/master/init_master_submenu
     */
    public void click4() {
        /*
         * 自己定义可扩展的LogginInterceptor
         * */
// NewUploadApi api = InternetClient.init(new LoggingInterceptor()).getRetrofit(BASE_URL).create(NewUploadApi.class);
        InternetAsyncManager apiManager = new InternetAsyncManager();
        LiveryInternetApi api = InternetClient.getInstance().getRetrofit("https://www.fire18.tv/live/api/homepage/").create(LiveryInternetApi.class);
        apiManager.add(api.get_observable_master_submenu().compose(Convert.io_main())
                .subscribe(new Consumer<TBaseResponseMode<ReplyMasterMenuInfoMode>>() {
                    @Override
                    public void accept(TBaseResponseMode<ReplyMasterMenuInfoMode> mod) {
                        if (mod != null) {
                            ReplyMasterMenuInfoMode rb = mod.getData();
                            if (rb != null) {
                                printResult = btn4.getText().toString() + "\n请求成功:\n" + rb.toString();
                                tvContent.setText(printResult);
                            }
                        } else {
                            showToast("成功，但数据为空");
                        }
                    }
                }, new InternetException() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        printResult = btn4.getText().toString() + "请求数据失败4:errorMsg=\n" + errorMsg + "errorCode=" + errorMsg;
                        tvContent.setText(printResult);
                    }
                }));
    }

    /**
     * (5)addDispose 获取未转换后的数据
     * <p>
     * https://www.wanandroid.com/article/list/1/json
     */
    private void click5() {
        LiveryInternetApi api = InternetClient.getInstance().getRetrofit("https://www.wanandroid.com/").create(LiveryInternetApi.class);
        addDispose(api.get_observable_json()
                .compose(Convert.io_main())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody rb) throws Exception {
                        String result = Objects.requireNonNull(rb).string();
                        if (!TextUtils.isEmpty(result)) {
                            printResult = btn5.getText().toString() + "\n请求成功:\n" + result;
                            tvContent.setText(printResult);
                        }
                    }
                }, new InternetException() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        printResult = btn5.getText().toString() + "请求数据失败5:errorMsg=\n" + errorMsg + "errorCode=" + errorMsg;
                        tvContent.setText(printResult);
                    }
                }));
    }

    /**
     * (6).addDispose 获取返回的数据为经过工厂转换后的实体类,这里使用这个接口，数据类似这样简单一点
     * <p>
     * * {"code":"success","msg":"成功^_^","data":{"masterMenuInfo":[{"collectionId":-1,"menuName":"推荐","position":1},
     * * {"collectionId":-1,"menuName":"电竞","position":2}]}}
     * * https://www.fire18.tv/live/api/homepage/master/init_master_submenu
     * 是属于框架提供的能力
     * <p>
     */
    private void click6() {
        LiveryInternetApi api = InternetClient.getInstance().getRetrofit("https://www.fire18.tv/live/api/homepage/").create(LiveryInternetApi.class);
        addDispose(api.get_observable_master_submenu()
                .compose(Convert.io_main())
                .subscribe(new Consumer<TBaseResponseMode<ReplyMasterMenuInfoMode>>() {
                    @Override
                    public void accept(TBaseResponseMode<ReplyMasterMenuInfoMode> mod) throws Exception {
                        if (mod != null) {
                            ReplyMasterMenuInfoMode rb = mod.getData();
                            if (rb != null) {
                                printResult = btn6.getText().toString() + "\n请求成功:\n" + rb.toString();
                                tvContent.setText(printResult);
                            }
                        } else {
                            showToast("成功，但数据为空");
                        }
                    }
                }, new InternetException() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        printResult = btn6.getText().toString() + "请求数据失败6:errorMsg=\n" + errorMsg + "errorCode=" + errorMsg;
                        tvContent.setText(printResult);
                    }
                }));
    }

    private void click7() {
        String url = "https://www.wanandroid.com/banner/";
/**
 * （1）.请求1成功ok
 * */
        InternetAsyncManager apiManager = new InternetAsyncManager();
// apiManager.add(((BaseNetApi<ResponseBody>) InternetClient.getInstance().api(url, BaseNetApi.class))
// .observableGet("json")
// .compose(Convert.io_main())
// .subscribe(this::hasData));

        apiManager.add(InternetClient.getInstance().obtainBaseApi(url)
                .observableGet("json")
                .compose(Convert.io_main())
                .subscribe(this::hasData));
    }

    private void hasData(Object o) {
        showToast("成功：" + o.toString());

    }

    private void hasData(ResponseBody responseBody) {
        showToast("成功：" + responseBody.toString());
    }

    /**
     * (带请求参数)addDispose获取原始ResponseBody
     * <p>
     * https://www.wanandroid.com/article/list/1/json
     */
    private void click8() {
        LiveryInternetApi api = InternetClient.getInstance().getRetrofit("https://www.wanandroid.com/").create(LiveryInternetApi.class);
        addDispose(api.get_observable_json_inparam(1, "json")
                .compose(Convert.io_main())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody rb) throws Exception {
                        String result = Objects.requireNonNull(rb).string();
                        if (!TextUtils.isEmpty(result)) {
                            printResult = btn8.getText().toString() + "\n请求成功:\n" + result;
                            tvContent.setText(printResult);
                        }
                    }
                }, new InternetException() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        printResult = btn8.getText().toString() + "请求数据失败8:errorMsg=\n" + errorMsg + "errorCode=" + errorMsg;
                        tvContent.setText(printResult);
                    }
                }));
    }

    /**
     * 获取直播推流地址(本情景唯一个Post请求)
     * <p>
     * https://www.fire18.tv/live/api/play/recommend/query_recommend_list
     */
    private void click9() {
        int uid = 39010;
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);
        LiveryInternetApi api = InternetClient.getInstance().getRetrofit("https://www.fire18.tv/live/").create(LiveryInternetApi.class);
        addDispose(api.post_observable_videoPullUrl(params)
                .compose(Convert.io_main())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody rb) throws Exception {
                        String result = Objects.requireNonNull(rb).string();
                        if (!TextUtils.isEmpty(result)) {
                            printResult = btn9.getText().toString() + "\n请求成功:\n" + result;
                            tvContent.setText(printResult);
                        }
                    }
                }, new InternetException() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        printResult = btn9.getText().toString() + "请求数据失败9:errorMsg=\n" + errorMsg + "errorCode=" + errorMsg;
                        tvContent.setText(printResult);
                    }
                }));
    }

    /**
     * https://api.github.com/user/repos?page=3&per_page=100
     * <p>https://api.github.com/user/repos?page=1&per_page=50
     * (带请求参数?)addDispose获取ReplyMode
     */
    private void defaultClick() {
        LiveryInternetApi api = InternetClient.getInstance().getRetrofit("https://api.github.com/user/").create(LiveryInternetApi.class);
        addDispose(api.get_observable_github_user("1", "50")
                .compose(Convert.io_main())
                .subscribe(new Consumer<ReplyGithubUserMode>() {
                    @Override
                    public void accept(ReplyGithubUserMode rm) throws Exception {
                        if (rm != null) {
                            printResult = btnOther.getText().toString() + "\n请求成功:\n" + rm.toString();
                            tvContent.setText(printResult);
                        } else {
                            showToast("成功，但数据为空");
                        }
                    }
                }, new InternetException() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        printResult = btnOther.getText().toString() + "请求数据失败other:errorMsg=\n" + errorMsg + "errorCode=" + errorMsg;
                        tvContent.setText(printResult);
                    }
                }));
    }
}