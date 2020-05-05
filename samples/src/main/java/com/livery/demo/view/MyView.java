package com.livery.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.sunsta.bear.faster.LAScreen;
import com.livery.demo.take.IDCard.Card;
import com.livery.demo.take.IDCard.Faculae;
import com.livery.demo.take.IDCard.IDCardQuality;
import com.livery.demo.take.IDCard.PointF;
import com.livery.demo.take.IDCard.Shadow;

/*sunst 非可用字段2020*/
public class MyView extends View {

    private IDCardQuality iCardQuality;
    private Bitmap bitmap;
    float[] points;
    private Paint paint;
    private int width, height;
    private Context context;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setiCardQuality(Bitmap bitmap, IDCardQuality iCardQuality) {
        this.iCardQuality = iCardQuality;
        width = LAScreen.getInstance(context).getScreenWidth();
        float scale = bitmap.getWidth() * 1.0f / width;
        height = (int) (bitmap.getHeight() / scale);
        this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        paint = new Paint();
        paint.setStrokeWidth(5);

        this.postInvalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmap == null || iCardQuality == null) {
            super.onDraw(canvas);
            return;
        }
        canvas.drawBitmap(bitmap, 0, 20, null);
        Shadow[] Shadows = iCardQuality.Shadows;
        Faculae[] faculaes = iCardQuality.faculaes;
        Card[] cards = iCardQuality.cards;
        paint.setColor(0xffaa0000);
        for (int i = 0; i < Shadows.length; i++) {
            PointF[] vertex = Shadows[i].vertex;
            sqrt(Shadows[i].variance[0], Shadows[i].variance[1], Shadows[i].variance[2], "Shadows");
            for (int j = 0; j < vertex.length; j++) {
                float startx = vertex[j].x * width;
                float starty = vertex[j].y * height;
                float endx = vertex[(j + 1) % vertex.length].x * width;
                float endy = vertex[(j + 1) % vertex.length].y * height;
                canvas.drawLine(startx, starty, endx, endy, paint);
            }
        }
        paint.setColor(0xff00aa00);
        for (int i = 0; i < faculaes.length; i++) {
            PointF[] vertex = faculaes[i].vertex;
            sqrt(faculaes[i].variance[0], faculaes[i].variance[1], faculaes[i].variance[2], "faculaes");
            for (int j = 0; j < vertex.length; j++) {
                float startx = vertex[j].x * width;
                float starty = vertex[j].y * height;
                float endx = vertex[(j + 1) % vertex.length].x * width;
                float endy = vertex[(j + 1) % vertex.length].y * height;
                canvas.drawLine(startx, starty, endx, endy, paint);
            }
        }

        paint.setColor(0xff0000aa);
        for (int i = 0; i < cards.length; i++) {
            PointF[] vertex = cards[i].vertex;
            sqrt(cards[i].variance[0], cards[i].variance[1], cards[i].variance[2], "card");
            for (int j = 0; j < vertex.length; j++) {
                float startx = vertex[j].x * width;
                float starty = vertex[j].y * height;
                float endx = vertex[(j + 1) % vertex.length].x * width;
                float endy = vertex[(j + 1) % vertex.length].y * height;
                canvas.drawLine(startx, starty, endx, endy, paint);
            }
        }
    }

    private boolean isContinueDraw(float[] cardAverage, float[] cardVariance, float[] valueAverage, float[] valueVariance) {
        for (int i = 0; i < 3; i++) {
            if ((cardAverage[i] + (float) (Math.sqrt(cardVariance[i]) * 0.5)) < valueAverage[i])
                return true;
            if (cardVariance[i] < valueVariance[i])
                return true;

        }
        return false;
    }

    private boolean isContinueShadowDraw(float[] cardAverage, float[] cardVariance, float[] valueAverage, float[] valueVariance) {
        for (int i = 0; i < 3; i++) {
            if ((cardAverage[i] - (float) (Math.sqrt(cardVariance[i]) * 0.8)) > valueAverage[i])
                return true;
        }
        return false;
    }

    private void sqrt(float value0, float value1, float value2, String tag) {
        float result0 = (float) Math.sqrt(value0);
        float result1 = (float) Math.sqrt(value1);
        float result2 = (float) Math.sqrt(value2);
        float mean = (result0 + result1 + result2) / 3.0f;
    }


    /*个人测试冒泡排序*/
    public static void maoPao(int[] arr){
        int temp;
        boolean flag;
        for(int i=0;i<arr.length-1;i++){
            flag = false;
            for(int j = arr.length-1;j>i;j--){
                if(arr[j]<arr[j-1]){
                    temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                    flag = true;
                }
            }
            if(!flag) break;
        }
    }
}