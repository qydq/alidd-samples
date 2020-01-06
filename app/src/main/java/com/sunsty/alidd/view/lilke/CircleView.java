package com.sunsty.alidd.view.lilke;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

import androidx.annotation.ColorInt;

public class CircleView extends View {
    private int START_COLOR = -43230;
    private int END_COLOR = -16121;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private Paint circlePaint = new Paint();
    private Paint maskPaint = new Paint();
    private Bitmap tempBitmap;
    private Canvas tempCanvas;
    private float outerCircleRadiusProgress = 0.0F;
    private float innerCircleRadiusProgress = 0.0F;
    private int width = 0;
    private int height = 0;
    private int maxCircleSize;
    public static final Property<CircleView, Float> INNER_CIRCLE_RADIUS_PROGRESS = new Property<CircleView, Float>(Float.class, "innerCircleRadiusProgress") {
        public Float get(CircleView object) {
            return object.getInnerCircleRadiusProgress();
        }

        public void set(CircleView object, Float value) {
            object.setInnerCircleRadiusProgress(value);
        }
    };
    public static final Property<CircleView, Float> OUTER_CIRCLE_RADIUS_PROGRESS = new Property<CircleView, Float>(Float.class, "outerCircleRadiusProgress") {
        public Float get(CircleView object) {
            return object.getOuterCircleRadiusProgress();
        }

        public void set(CircleView object, Float value) {
            object.setOuterCircleRadiusProgress(value);
        }
    };

    public CircleView(Context context) {
        super(context);
        this.init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.circlePaint.setStyle(Style.FILL);
        this.maskPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        this.invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.width != 0 && this.height != 0) {
            this.setMeasuredDimension(this.width, this.height);
        }

    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.maxCircleSize = w / 2;
        this.tempBitmap = Bitmap.createBitmap(this.getWidth(), this.getWidth(), Config.ARGB_8888);
        this.tempCanvas = new Canvas(this.tempBitmap);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.tempCanvas.drawColor(16777215, Mode.CLEAR);
        this.tempCanvas.drawCircle((float) (this.getWidth() / 2), (float) (this.getHeight() / 2), this.outerCircleRadiusProgress * (float) this.maxCircleSize, this.circlePaint);
        this.tempCanvas.drawCircle((float) (this.getWidth() / 2), (float) (this.getHeight() / 2), this.innerCircleRadiusProgress * (float) this.maxCircleSize, this.maskPaint);
        canvas.drawBitmap(this.tempBitmap, 0.0F, 0.0F, (Paint) null);
    }

    public void setInnerCircleRadiusProgress(float innerCircleRadiusProgress) {
        this.innerCircleRadiusProgress = innerCircleRadiusProgress;
        this.postInvalidate();
    }

    public float getInnerCircleRadiusProgress() {
        return this.innerCircleRadiusProgress;
    }

    public void setOuterCircleRadiusProgress(float outerCircleRadiusProgress) {
        this.outerCircleRadiusProgress = outerCircleRadiusProgress;
        this.updateCircleColor();
        this.postInvalidate();
    }

    private void updateCircleColor() {
        float colorProgress = (float) Utils.clamp((double) this.outerCircleRadiusProgress, 0.5D, 1.0D);
        colorProgress = (float) Utils.mapValueFromRangeToRange((double) colorProgress, 0.5D, 1.0D, 0.0D, 1.0D);
        this.circlePaint.setColor((Integer) this.argbEvaluator.evaluate(colorProgress, this.START_COLOR, this.END_COLOR));
    }

    public float getOuterCircleRadiusProgress() {
        return this.outerCircleRadiusProgress;
    }

    public void setStartColor(@ColorInt int color) {
        this.START_COLOR = color;
        this.invalidate();
    }

    public void setEndColor(@ColorInt int color) {
        this.END_COLOR = color;
        this.invalidate();
    }
}
