package com.sunsty.alidd.view.lilke;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

import androidx.annotation.ColorInt;

public class DotsView extends View {
    private static final int DOTS_COUNT = 7;
    private static final int OUTER_DOTS_POSITION_ANGLE = 51;
    private int COLOR_1 = -16121;
    private int COLOR_2 = -26624;
    private int COLOR_3 = -43230;
    private int COLOR_4 = -769226;
    private int width = 0;
    private int height = 0;
    private final Paint[] circlePaints = new Paint[4];
    private int centerX;
    private int centerY;
    private float maxOuterDotsRadius;
    private float maxInnerDotsRadius;
    private float maxDotSize;
    private float currentProgress = 0.0F;
    private float currentRadius1 = 0.0F;
    private float currentDotSize1 = 0.0F;
    private float currentDotSize2 = 0.0F;
    private float currentRadius2 = 0.0F;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    public static final Property<DotsView, Float> DOTS_PROGRESS = new Property<DotsView, Float>(Float.class, "dotsProgress") {
        public Float get(DotsView object) {
            return object.getCurrentProgress();
        }

        public void set(DotsView object, Float value) {
            object.setCurrentProgress(value);
        }
    };

    public DotsView(Context context) {
        super(context);
        this.init();
    }

    public DotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public DotsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        for (int i = 0; i < this.circlePaints.length; ++i) {
            this.circlePaints[i] = new Paint();
            this.circlePaints[i].setStyle(Style.FILL);
        }

    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.centerX = w / 2;
        this.centerY = h / 2;
        this.maxDotSize = 5.0F;
        this.maxOuterDotsRadius = (float) (w / 2) - this.maxDotSize * 2.0F;
        this.maxInnerDotsRadius = 0.8F * this.maxOuterDotsRadius;
    }

    protected void onDraw(Canvas canvas) {
        this.drawOuterDotsFrame(canvas);
        this.drawInnerDotsFrame(canvas);
    }

    private void drawOuterDotsFrame(Canvas canvas) {
        for (int i = 0; i < 7; ++i) {
            int cX = (int) ((double) this.centerX + (double) this.currentRadius1 * Math.cos((double) (i * 51) * 3.141592653589793D / 180.0D));
            int cY = (int) ((double) this.centerY + (double) this.currentRadius1 * Math.sin((double) (i * 51) * 3.141592653589793D / 180.0D));
            canvas.drawCircle((float) cX, (float) cY, this.currentDotSize1, this.circlePaints[i % this.circlePaints.length]);
        }

    }

    private void drawInnerDotsFrame(Canvas canvas) {
        for (int i = 0; i < 7; ++i) {
            int cX = (int) ((double) this.centerX + (double) this.currentRadius2 * Math.cos((double) (i * 51 - 10) * 3.141592653589793D / 180.0D));
            int cY = (int) ((double) this.centerY + (double) this.currentRadius2 * Math.sin((double) (i * 51 - 10) * 3.141592653589793D / 180.0D));
            canvas.drawCircle((float) cX, (float) cY, this.currentDotSize2, this.circlePaints[(i + 1) % this.circlePaints.length]);
        }

    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;
        this.updateInnerDotsPosition();
        this.updateOuterDotsPosition();
        this.updateDotsPaints();
        this.updateDotsAlpha();
        this.postInvalidate();
    }

    public float getCurrentProgress() {
        return this.currentProgress;
    }

    private void updateInnerDotsPosition() {
        if (this.currentProgress < 0.3F) {
            this.currentRadius2 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.0D, 0.30000001192092896D, 0.0D, (double) this.maxInnerDotsRadius);
        } else {
            this.currentRadius2 = this.maxInnerDotsRadius;
        }

        if (this.currentProgress == 0.0F) {
            this.currentDotSize2 = 0.0F;
        } else if ((double) this.currentProgress < 0.2D) {
            this.currentDotSize2 = this.maxDotSize;
        } else if ((double) this.currentProgress < 0.5D) {
            this.currentDotSize2 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.20000000298023224D, 0.5D, (double) this.maxDotSize, 0.3D * (double) this.maxDotSize);
        } else {
            this.currentDotSize2 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.5D, 1.0D, (double) (this.maxDotSize * 0.3F), 0.0D);
        }

    }

    private void updateOuterDotsPosition() {
        if (this.currentProgress < 0.3F) {
            this.currentRadius1 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.0D, 0.30000001192092896D, 0.0D, (double) (this.maxOuterDotsRadius * 0.8F));
        } else {
            this.currentRadius1 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.30000001192092896D, 1.0D, (double) (0.8F * this.maxOuterDotsRadius), (double) this.maxOuterDotsRadius);
        }

        if (this.currentProgress == 0.0F) {
            this.currentDotSize1 = 0.0F;
        } else if ((double) this.currentProgress < 0.7D) {
            this.currentDotSize1 = this.maxDotSize;
        } else {
            this.currentDotSize1 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.699999988079071D, 1.0D, (double) this.maxDotSize, 0.0D);
        }

    }

    private void updateDotsPaints() {
        float progress;
        if (this.currentProgress < 0.5F) {
            progress = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.0D, 0.5D, 0.0D, 1.0D);
            this.circlePaints[0].setColor((Integer) this.argbEvaluator.evaluate(progress, this.COLOR_1, this.COLOR_2));
            this.circlePaints[1].setColor((Integer) this.argbEvaluator.evaluate(progress, this.COLOR_2, this.COLOR_3));
            this.circlePaints[2].setColor((Integer) this.argbEvaluator.evaluate(progress, this.COLOR_3, this.COLOR_4));
            this.circlePaints[3].setColor((Integer) this.argbEvaluator.evaluate(progress, this.COLOR_4, this.COLOR_1));
        } else {
            progress = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.5D, 1.0D, 0.0D, 1.0D);
            this.circlePaints[0].setColor((Integer) this.argbEvaluator.evaluate(progress, this.COLOR_2, this.COLOR_3));
            this.circlePaints[1].setColor((Integer) this.argbEvaluator.evaluate(progress, this.COLOR_3, this.COLOR_4));
            this.circlePaints[2].setColor((Integer) this.argbEvaluator.evaluate(progress, this.COLOR_4, this.COLOR_1));
            this.circlePaints[3].setColor((Integer) this.argbEvaluator.evaluate(progress, this.COLOR_1, this.COLOR_2));
        }

    }

    public void setColors(@ColorInt int primaryColor, @ColorInt int secondaryColor) {
        this.COLOR_1 = primaryColor;
        this.COLOR_2 = secondaryColor;
        this.COLOR_3 = primaryColor;
        this.COLOR_4 = secondaryColor;
        this.invalidate();
    }

    private void updateDotsAlpha() {
        float progress = (float) Utils.clamp((double) this.currentProgress, 0.6000000238418579D, 1.0D);
        int alpha = (int) Utils.mapValueFromRangeToRange((double) progress, 0.6000000238418579D, 1.0D, 255.0D, 0.0D);
        this.circlePaints[0].setAlpha(alpha);
        this.circlePaints[1].setAlpha(alpha);
        this.circlePaints[2].setAlpha(alpha);
        this.circlePaints[3].setAlpha(alpha);
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
}
