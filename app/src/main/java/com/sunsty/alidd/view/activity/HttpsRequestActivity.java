package com.sunsty.alidd.view.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ali.presenter.callback.JustNetApi;
import com.ali.presenter.net.JustAsyncManager;
import com.ali.presenter.net.JustNetClient;
import com.ali.presenter.net.JustNetException;
import com.ali.take.Convert;
import com.ali.take.FileUtils;
import com.ali.take.Intents;
import com.ali.take.LaLog;
import com.bumptech.glide.Glide;
import com.sunsty.alidd.BuildConfig;
import com.sunsty.alidd.R;
import com.sunsty.alidd.take.NewUploadApi;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpsRequestActivity extends NewBaseActivity {
    private static final String TAG = "HttpsRequestActivity";
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
        setContentView(R.layout.activity_https_request);
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
//                startActivity(new Intent(BeforeSelectAlbumActivity.this, WebViewActivity.class));
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


    /*
     *通过sdk进行一个跳转*/
    public static final String WX_PACKAGE = "com.tencent.mm"; // 微信包名
    public static final String ZFB_PACKAGE = "com.eg.android.AlipayGphone"; // 支付宝包名

    /**
     * 是否安装微信 WXAPIFactory是微信官方sdk
     */
    public static boolean isInstallWx(Context context) {
//        return WXAPIFactory.createWXAPI(context, "wxb613184aa8f5718a").isWXAppInstalled();
        return Intents.isAppInstalled(WX_PACKAGE);
    }

    /**
     * 是否安装支付宝
     *
     * @return true 为已经安装
     */
    public boolean isInstallAli(Context context) {
        PackageManager manager = context.getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse("alipays://"));
        List<ResolveInfo> list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return null != list && list.size() > 0;
//        return AppUtils.isAppInstalled(ZFB_PACKAGE);
    }

    /**
     * 跳转微信
     *
     * @return 跳转成功返回 true
     */
    public static boolean appToWx() {
//    Intent lan = mContext.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
//    Intent intent = new Intent(Intent.ACTION_MAIN);
//    intent.addCategory(Intent.CATEGORY_LAUNCHER);
//    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    intent.setComponent(Objects.requireNonNull(lan).getComponent());
//    startActivity(intent);
        if (Intents.isAppInstalled(WX_PACKAGE)) {
            Intents.launchApp(WX_PACKAGE);
            return true;
        } else {
            return true;
        }
    }

    /**
     * 跳转支付宝
     *
     * @return 跳转成功返回 true
     */
    public boolean appToAli() {
        if (Intents.isAppInstalled(ZFB_PACKAGE)) {
            Intents.launchApp(ZFB_PACKAGE);
            return true;
        } else {
            return true;
        }
    }


    /*如果是mvp中的mode, 应该callback回掉回去*/
    private void canshuRequest(String phoneUmber, LoadDataCallback logcall) {
        Map<String, Object> map = new HashMap<>();
        map.put("phonenumber", phoneUmber);
        map.put("task", "GO_GCACTION");
        NewUploadApi api = JustNetClient.init().getRetrofit("https://www.wanandroid.com/banner/").create(NewUploadApi.class);

        new JustAsyncManager().add(api.getSmsCode(map).compose(Convert.io_main()).subscribe(result -> logcall.success("成功!"), throwable -> logcall.failure("稍后再试")));

    }

    public interface LoadDataCallback {
        void success(String msg);

        void failure(String msg);
    }

    /*上传普通图片的方法*/
    private void uploadImage(Uri uri) {
        //获取图片路径,项目开发中会有很多图片需要上传，一般从相册和相机里获取，并且保存到后台服务器时先定义名字，这样就不会发生图片替换（原因名字重复）
        String realFilePath = FileUtils.getInstance().getPath(this, uri);
        File mFile = new File(realFilePath);
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("image/*"), mFile);
        HashMap<String, RequestBody> map = new HashMap<>();
        NewUploadApi api = JustNetClient.init().getRetrofit("https://www.wanandroid.com/banner/").create(NewUploadApi.class);

        map.put("files" + "\"; filename=\"" + mFile.getName() + "", mRequestBody);
        // map.put("files" + "\"; filename=\"" + mFile1.getName() + "", mRequestBody1);
        new JustAsyncManager().add(api.uploadFiles(map).compose(Convert.io_main()).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody o) throws Exception {
                Glide.with(HttpsRequestActivity.this).load(mFile).into(natureIv);
                showToast("上传成功");
//                if (selectPosition == 0) {
//                    Glide.with(HttpsRequestActivity.this).load(mFile).into(natureIv);
//                    showToast("上传成功");
//                } else if (selectPosition == 1) {
//                    Glide.with(HttpsRequestActivity.this).load(mFile).into(natureIv2);
//                    showToast("上传成功");
//                }
            }
        }, new JustNetException() {
            @Override
            public void onError(int errorCode, String errorMsg) {
                showToast("网络异常，请重新上传");
            }
        }));
    }

    /*上传视频的方法*/
    private void uploadVideo(String filePath) {
        //构建要上传的文件
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        val requestFile = RequestBody.create(MediaType.parse("video/*"), file)
        MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
        Map<String, String> videoMap = new HashMap<>();
        videoMap.put("VERSION_NAME", BuildConfig.VERSION_NAME);
        videoMap.put("timestamp", System.currentTimeMillis() + "");
//        if (typeFrom) {
//            videoMap.put("type", "aboutPage");
//        } else {
//            videoMap.put("type", "aboutme");
//        }
        videoMap.put("md5", "mymd5");
        videoMap.put("videoName", "videoName");
        NewUploadApi api = JustNetClient.init().getRetrofit("https://www.wanandroid.com/banner/").create(NewUploadApi.class);

        /*第二个参数*/
        JSONObject jsonObject = new JSONObject(videoMap);
        LaLog.e(TAG + "----jsonObject=" + jsonObject.toString());
        MultipartBody.Part bodyParams = MultipartBody.Part.createFormData("requestModel", jsonObject.toString());

        addDispose(api.uploadMovieFiles(body, bodyParams)
                .compose(Convert.io_main())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        //todo
//                        handUploadResult();
                    }
                }, new JustNetException() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        showToast("网络异常，请重新上传");
                    }
                }));
    }
}