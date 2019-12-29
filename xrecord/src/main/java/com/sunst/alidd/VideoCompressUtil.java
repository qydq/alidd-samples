package com.sunst.alidd;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.iceteck.silicompressorr.SiliCompressor;

import java.net.URISyntaxException;

/**
 * Desc: 视频压缩工具类
 */
public class VideoCompressUtil {

    private static final String TAG = "VideoCompressUtil";
    static final int COMPRESS_QUALITY_MEDIUM = 0x01; // 一般质量
    static final int COMPRESS_QUALITY_LOW = 0x02; // 低质量

    public VideoCompressAsyncTask compressVideoMedium(Context context, String filePath, OnCompressListener listener) {
        VideoCompressAsyncTask task = new VideoCompressAsyncTask(context, COMPRESS_QUALITY_MEDIUM, listener);
        task.execute(filePath, RecordFileUtil.createFolder(context));
        return task;
    }

    public VideoCompressAsyncTask compressVideoLow(Context context, String filePath, OnCompressListener listener) {
        VideoCompressAsyncTask task = new VideoCompressAsyncTask(context, COMPRESS_QUALITY_LOW, listener);
        task.execute(filePath, RecordFileUtil.createFolder(context));
        return task;
    }

    public class VideoCompressAsyncTask extends AsyncTask<String, Float, String> {

        int quality; // 压缩质量
        Context context;
        OnCompressListener listener;

        VideoCompressAsyncTask(Context context, int quality, OnCompressListener listener) {
            this.context = context;
            this.quality = quality;
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.onStart();
        }

        @Override
        protected String doInBackground(String... paths) {
            String filePath;
            try {
                Log.e(TAG, "paths[0]: " + paths[0]);
                Log.e(TAG, "paths[0]: " + RecordFileUtil.isFileSizeExceed(paths[0], 20));
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//                FFmpegMediaMetadataRetriever adasd = new FFmpegMediaMetadataRetriever();
                String compressPath = paths[0];
                if (!TextUtils.isEmpty(compressPath)) {
                    retriever.setDataSource(paths[0]);
                    int originalWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                    int originalHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                    int bitrate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));

                    Log.e(TAG, "originalWidth: " + originalWidth + "originalHeight: " + originalHeight +
                            "bitrate: " + bitrate);

                    double scale;
                    int resultWidth, resultHeight;
                    if ((originalWidth > 720 || originalHeight > 720) && COMPRESS_QUALITY_MEDIUM == quality) {
                        double scaleW = 1.0 * originalWidth / 720;
                        double scaleH = 1.0 * originalHeight / 720;
                        if (scaleH < scaleW) {
                            scale = scaleH;
                        } else {
                            scale = scaleW;
                        }
                        resultWidth = (int) (originalWidth / scale);
                        resultHeight = (int) (originalHeight / scale);
                        scale = scale * 2;

                        bitrate = (int) ((resultWidth / scale) * (resultHeight / scale) * 10);
                    } else {
                        double scaleW = 1.0 * originalWidth / 480;
                        double scaleH = 1.0 * originalHeight / 480;
                        if (scaleH < scaleW) {
                            scale = scaleH;
                        } else {
                            scale = scaleW;
                        }
                        resultWidth = (int) (originalWidth / scale);
                        resultHeight = (int) (originalHeight / scale);
                        bitrate = 900 * 1024;
                    }
                    if (resultWidth % 2 != 0) {
                        resultWidth--;
                    }
                    if (resultHeight % 2 != 0) {
                        resultHeight--;
                    }
                    Log.e(TAG, " resultWidth: " + resultWidth);
                    Log.e(TAG, " resultHeight: " + resultHeight);
                    Log.e(TAG, " bitrate: " + bitrate);
                    filePath = SiliCompressor.with(context).compressVideo(paths[0], paths[1],
                            resultWidth, resultHeight, bitrate);
                    return filePath;
                } else {
                    return "";
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String compressedFilePath) {
            super.onPostExecute(compressedFilePath);
            if (!TextUtils.isEmpty(compressedFilePath)) {
                listener.onEnd(compressedFilePath);
            } else {
                listener.onFailed();
            }
        }
    }

    public interface OnCompressListener {
        void onStart(); // 压缩开始

        void onEnd(String videoPath); // 压缩完成，返回视频路径

        void onFailed(); // 压缩失败
    }
}
