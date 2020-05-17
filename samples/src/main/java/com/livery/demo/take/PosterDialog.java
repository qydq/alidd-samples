package com.livery.demo.take;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.livery.demo.R;
import com.sunsta.bear.engine.GlideEngine;


public class PosterDialog extends Dialog {

    private PosterDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class PosterBuilder implements View.OnClickListener {
        private PosterDialog posterDialog;

        private Activity mActivity;
        private String mData;
        private setOnPosterSelectListener posterSelectListener;
        private ImageView iv_poster, iv_poster_close;
        private RelativeLayout ll_dialog_main;

        public PosterBuilder(Activity activity, String data) {
            this.mData = data;
            this.mActivity = activity;

            posterDialog = new PosterDialog(activity, R.style.custom_dialog);
            View dialogView = View.inflate(activity, R.layout.base_dialog_image, null);
            posterDialog.setContentView(dialogView);
            iv_poster = dialogView.findViewById(R.id.iv_poster);
            iv_poster_close = dialogView.findViewById(R.id.iv_poster_close);
            ll_dialog_main = dialogView.findViewById(R.id.ll_dialog_main);

            GlideEngine.getInstance().loadCircleImage(mActivity, 12, mData, iv_poster);
        }

        public PosterDialog create() {
            iv_poster.setOnClickListener(this);
            iv_poster_close.setOnClickListener(this);
            ll_dialog_main.setOnClickListener(this);
            posterDialog.setCancelable(true);
            posterDialog.setCanceledOnTouchOutside(true);
            if (posterDialog.getWindow() != null) {
                posterDialog.getWindow().setGravity(Gravity.BOTTOM);
            }
            return posterDialog;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_poster_close:
                    if (posterDialog != null) {
                        posterDialog.dismiss();
                    }
                    break;
                case R.id.ll_dialog_main:
                    if (posterDialog != null) {
                        posterDialog.dismiss();
                    }
                    break;
                case R.id.iv_poster:
                    if (posterSelectListener != null) {
                        posterSelectListener.posterJump();
                    }
                    break;
                default:
                    if (posterDialog != null) {
                        posterDialog.dismiss();
                    }
                    break;
            }
            if (posterDialog != null) {
                posterDialog.dismiss();
            }
        }

        public PosterBuilder setOnPosterSelectListener(setOnPosterSelectListener sexSelectListener) {
            this.posterSelectListener = sexSelectListener;
            return this;
        }

        public interface setOnPosterSelectListener {
            void cancelClick();

            void posterJump();

        }
    }
}