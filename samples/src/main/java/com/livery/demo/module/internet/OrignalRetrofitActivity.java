package com.livery.demo.module.internet;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
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
import com.livery.demo.net.LiveryInternetApi;
import com.sunsta.bear.AnConstants;
import com.sunsta.bear.engine.DownloadService;
import com.sunsta.bear.engine.GlideEngine;
import com.sunsta.bear.faster.DataService;
import com.sunsta.bear.faster.LaLog;
import com.sunsta.bear.model.entity.Download;
import com.sunsta.bear.presenter.BaseInternetApi;
import com.sunsta.bear.presenter.net.InternetClient;
import com.sunsta.bear.view.AliActivity;
import com.sunsta.livery.faster.FasterIntents;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OrignalRetrofitActivity extends AliActivity implements View.OnClickListener, DownloadService.Callback {
    private TextView tvContent;
    private Button btn1, btn2;
    private TextView tvZfb;
    private TextView tvWx;
    private ImageView ivPrimary;


    private static String BASE_URL = "https://gank.io/api/v2/";
    private static String Full_URL = "https://gank.io/api/v2/banners";
    private String printResult = "";
    private LocalBroadcastManager bManager;


    public static final String MESSAGE_PROGRESS = "message_progress";
    // public static final String Http_Full_Download_Url = "https://www.zhibo18.live//upload/20200416/5e97519c6b061.mp4";
// public static final String Http_Full_Download_Url = "https://www.zhibo18.live/upload/20200416/5e97519c6b061.mp4";
    public static final String Http_Full_Download_Url = "https://ae01.alicdn.com/kf/U6de089ce45ff468a8f06c50e19ad7379N.jpg";
    //    public static final String Http_Full_Download_Url = "https://i.imagseur.com/uploads/2020-05/14/3dec314beeb30d00ecfc0db657e07c80.jpg";
//    public static final String Http_Full_Download_Url = "http://i.imagseur.com/uploads/gifs/gif_16-05-2015/49737-beautiful-asian-banged-hard.gif";
//    public static final String Http_Full_Download_Url = "http://i.imagseur.com/uploads/gifs/gif_10-05-2015/9349392.gif";
    private DataService dataService;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            tvContent.setText(msg.obj.toString());
            return false;
        }
    });

    /*
     *通过sdk进行一个跳转*/
    public static final String WX_PACKAGE = "com.tencent.mm"; // 微信包名
    public static final String ZFB_PACKAGE = "com.eg.android.AlipayGphone"; // 支付宝包名


    @Override
    public void initView() {
        setContentView(R.layout.internet_activity_orignal);
        dataService = DataService.getInstance();
        tvContent = findViewById(R.id.tvContent);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        tvZfb = findViewById(R.id.tvZfb);
        ivPrimary = findViewById(R.id.ivPrimary);
        tvWx = findViewById(R.id.tvWx);


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

        new DownloadService().setCallback(this);

        registerReceiver();

        GlideEngine.getInstance().loadCircleImage(this, 10, Http_Full_Download_Url, ivPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                click1();
                break;
            default:
                liverySelfApi();
                break;
        }
    }

    /**
     * (1)原生Retrofit网络请求,原始数据，这里以获取知乎专栏数据为例
     * <p>
     * 这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
     */
    public void click1() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .baseUrl("https://zhuanlan.zhihu.com/api/")
                .build();
        LiveryInternetApi api = retrofit.create(LiveryInternetApi.class);
        Call<ResponseBody> call = api.get_tCall_Zhihu_Columns();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
//成功返回数据后在这里处理，使用response.body();获取得到的结果
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    try {
                        String result = Objects.requireNonNull(responseBody).string();
//stringData可能为空：https://blog.csdn.net/weixin_36570478/article/details/100679670
                        String stringData = responseBody.string();
//获取到的数据为Unicode,需要转换一次
                        JSONObject jsonObject = new JSONObject(result);
                        String turnResultData = jsonObject.toString();

                        if (!TextUtils.isEmpty(turnResultData)) {
                            printResult = btn1.getText().toString() + "\n请求成功2:\n" + turnResultData;
                        }
                    } catch (IOException | JSONException e) {
                        printResult = btn1.getText().toString() + "\n响应数据成功1:但是有异常:\n" + e.getMessage();
                        e.printStackTrace();
                    }
                } else {
                    printResult = btn1.getText().toString() + "\n获取数据失败";
                }
                tvContent.setText(printResult);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//请求失败在这里处理
                printResult = btn1.getText().toString() + "请求数据失败1:call=\n" + call.toString();
                tvContent.setText(printResult);
            }
        });
    }


    private void registerReceiver() {
        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MESSAGE_PROGRESS)) {
                Download download = intent.getParcelableExtra("download");
// progress.setProgress(download.getProgress());
                tvContent.setText("当前下载的进度：" + download.getProgress());
                if (download.getProgress() == 100) {
                    tvContent.setText("File Download Complete");
                } else {
                    DataService dataService = DataService.getInstance();
                    String currentDownloadSize = dataService.getDataSize(download.getCurrentFileSize());
                    String totalDownloadFileSize = dataService.getDataSize(download.getTotalFileSize());
                    String shouldTipsProgress = currentDownloadSize + "/" + totalDownloadFileSize;
                    tvContent.setText("当前下载的进度：" + shouldTipsProgress);
                }
            }
        }
    };

    private void downloadApk(String fileName, String pwdPath) {
        Intent intent = new Intent(OrignalRetrofitActivity.this, DownloadService.class);
        intent.putExtra(AnConstants.EXTRA.REQUEST_URL, Http_Full_Download_Url);
        intent.putExtra(AnConstants.EXTRA.REQUEST_FILENAME, fileName);
        intent.putExtra(AnConstants.EXTRA.REQUEST_DIRPATH, pwdPath);
        startService(intent);
    }

    @Override
    public void onDataChange(int data) {
        tvContent.setText("当前下载的进度：data=" + data);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        LaLog.d("验证-UI_MODE_NIGHT_NO111");

        int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
// 夜间模式未启用，我们正在使用浅色主题
            LaLog.d("验证-UI_MODE_NIGHT_NO");
        }
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
// 夜间模式启用，我们使用的是深色主题
            LaLog.d("验证-UI_MODE_NIGHT_YES");
        }

    }

    /**
     * 验证ok2
     */
    private void liverySelfApi() {
        BaseInternetApi api = InternetClient.getInstance().obtainBaseApi("https://www.wanandroid.com/banner/");

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

    /**
     * 检测是否安装支付宝
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


    /**
     * 是否安装微信 WXAPIFactory是微信官方sdk
     */
    public static boolean isInstallWx(Context context) {
//        return WXAPIFactory.createWXAPI(context, "wxb613184aa8f5718a").isWXAppInstalled();
        return FasterIntents.isAppInstalled(WX_PACKAGE);
    }

    /**
     * 是否安装支付宝
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
     * @return 跳转成功返回 true
     */
    public static boolean appToWx() {
//    Intent lan = mContext.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
//    Intent intent = new Intent(Intent.ACTION_MAIN);
//    intent.addCategory(Intent.CATEGORY_LAUNCHER);
//    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    intent.setComponent(Objects.requireNonNull(lan).getComponent());
//    startActivity(intent);
        if (FasterIntents.isAppInstalled(WX_PACKAGE)) {
            FasterIntents.launchApp(WX_PACKAGE);
            return true;
        } else {
            return true;
        }
    }

    /**
     * 跳转支付宝
     * @return 跳转成功返回 true
     */
    public boolean appToAli() {
        if (FasterIntents.isAppInstalled(ZFB_PACKAGE)) {
            FasterIntents.launchApp(ZFB_PACKAGE);
            return true;
        } else {
            return true;
        }
    }
}