package com.livery.demo.module.internet;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.livery.demo.BuildConfig;
import com.livery.demo.R;
import com.sunsta.bear.faster.EasyPermission;
import com.livery.demo.take.NewUploadApi;
import com.sunsta.bear.engine.DownloadService;
import com.sunsta.bear.faster.Convert;
import com.sunsta.bear.faster.DataService;
import com.sunsta.bear.faster.FileUtils;
import com.sunsta.bear.faster.LaLog;
import com.sunsta.bear.model.entity.Download;
import com.sunsta.bear.presenter.net.InternetAsyncManager;
import com.sunsta.bear.presenter.net.InternetClient;
import com.sunsta.bear.presenter.net.InternetException;
import com.sunsta.bear.view.AliActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class UpDownloadActivity extends AliActivity implements EasyPermission.PermissionCallback {
    public static final String MESSAGE_PROGRESS = "message_progress";
    private LocalBroadcastManager bManager;

    private AppCompatButton btn_download;
    private ProgressBar progress;
    private TextView progress_text;

    private String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MESSAGE_PROGRESS)) {
                Download download = intent.getParcelableExtra("download");
                progress.setProgress(download.getProgress());
                if (download.getProgress() == 100) {
                    progress_text.setText("File Download Complete");
                } else {

                    DataService dataService = DataService.getInstance();
                    String currentDownloadSize = dataService.getDataSize(download.getCurrentFileSize());
                    String totalDownloadFileSize = dataService.getDataSize(download.getTotalFileSize());
                    String shouldTipsProgress = currentDownloadSize + "/" + totalDownloadFileSize;
                    progress_text.setText(shouldTipsProgress);
                }
            }
        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.internet_activity_updownload);
        btn_download = findViewById(R.id.btn_download);
        progress = findViewById(R.id.progress);
        progress_text = findViewById(R.id.progress_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission();
        }
        registerReceiver();

        btn_download.setOnClickListener(v -> shouldDownLoad());
    }

    private void shouldDownLoad() {
        Intent intent = new Intent(UpDownloadActivity.this, DownloadService.class);
        startService(intent);
    }

    private void registerReceiver() {
        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除注册时，使用注册时的manager解绑
        bManager.unregisterReceiver(broadcastReceiver);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        if (EasyPermission.hasPermissions(this, permissions)) {
        } else {
            EasyPermission.with(this)
                    .rationale("打开我的权限")
                    .addRequestCode(2)
                    .permissions(permissions)
                    .request();
        }
    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {

    }

    /*上传普通图片的方法*/
    private void uploadImage(Uri uri) {
        //获取图片路径,项目开发中会有很多图片需要上传，一般从相册和相机里获取，并且保存到后台服务器时先定义名字，这样就不会发生图片替换（原因名字重复）
        String realFilePath = FileUtils.getInstance().getPath(this, uri);
        File mFile = new File(realFilePath);
        RequestBody mRequestBody = RequestBody.create(MediaType.parse("image/*"), mFile);
        HashMap<String, RequestBody> map = new HashMap<>();
        NewUploadApi api = InternetClient.getInstance().getRetrofit("https://www.wanandroid.com/banner/").create(NewUploadApi.class);

        map.put("files" + "\"; filename=\"" + mFile.getName() + "", mRequestBody);
        // map.put("files" + "\"; filename=\"" + mFile1.getName() + "", mRequestBody1);
        new InternetAsyncManager().add(api.uploadFiles(map).compose(Convert.io_main()).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody o) throws Exception {
//                Glide.with(UpDownloadActivity.this).load(mFile).into(natureIv);
                showToast("上传成功");
//                if (selectPosition == 0) {
//                    Glide.with(InternetActivity.this).load(mFile).into(natureIv);
//                    showToast("上传成功");
//                } else if (selectPosition == 1) {
//                    Glide.with(InternetActivity.this).load(mFile).into(natureIv2);
//                    showToast("上传成功");
//                }
            }
        }, new InternetException() {
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
        NewUploadApi api = InternetClient.getInstance().getRetrofit("https://www.wanandroid.com/banner/").create(NewUploadApi.class);

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
                }, new InternetException() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        showToast("网络异常，请重新上传");
                    }
                }));
    }
}