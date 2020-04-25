package com.sunsty.alidd.view.activity.download;

import android.os.AsyncTask;
import android.os.Environment;

import com.ali.faster.FileUtils;
import com.ali.faster.LAStorageFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String,Integer,Integer> {
    //0成功，1失败，2暂停，3取消
    private DownloadListener listener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;
    private RandomAccessFile randomAccessFile;

    public DownloadTask(DownloadListener listener){
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream inputStream = null;
        /*fixed :android getExternalStoragePublicDirectory 过时*/
        File file = null;
        long downloadedLength = 0;//记录下载文件的长度
        String downloadUrl = strings[0];
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        File nativeDownloadFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String directory = nativeDownloadFile.getPath();

        file = new File(directory + fileName);
        if (file.exists()) {
            downloadedLength = file.length();
        }

        file = LAStorageFile.INSTANCE.fouceTouchFile(FileUtils.getInstance().getPwdDownloadFolder(), FileUtils.getInstance().getCreateFileSuffixName(".apk"));
        if (file.exists()) {
            downloadedLength = file.length();
        }

//        file = LAStorageFile.INSTANCE.fouceTouchFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"sunst_supertest.apk");// failure

        long contentLength = 0;
        try {
            contentLength = getContentLength(downloadUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (contentLength == 0) {
            return 1;
        } else if (contentLength == downloadedLength) {
            return 0;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //断电 下载，指定从那个字节开始下载
                .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                .url(downloadUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            inputStream = response.body().byteStream();
            randomAccessFile = new RandomAccessFile(file,"rw");
//            saveFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(downloadedLength);//跳过已下载的字节
            byte[] bytes = new byte[1024];
            int total = 0;
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                if (isCanceled) {
                    return 3;
                } else if (isPaused) {
                    return 2;
                } else {
                    total += len;
                    randomAccessFile.write(bytes, 0, len);
                    //计算已经下载的百分比
                    int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                    publishProgress(progress);
                }
            }
            response.body().close();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if(progress>lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case 0:
                listener.onSuccess();
                break;
            case 1:
                listener.onFailed();
                break;
            case 2:
                listener.onPaused();
                break;
            case 3:
                listener.onCanceled();
                break;
            default:break;
        }
    }

    public void pauseDownload(){
        isPaused = true;
    }
    public void cancelDownload(){
        isCanceled = true;
    }

    private long getContentLength(String downlaodUrl) throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downlaodUrl).build();
        Response response = client.newCall(request).execute();
        if(response!=null && response.isSuccessful()){
            long contentLength =response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }
}
