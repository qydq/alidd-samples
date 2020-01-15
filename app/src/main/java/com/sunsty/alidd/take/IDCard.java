package com.sunsty.alidd.take;

import android.util.Log;

import java.io.Serializable;

/*sunst 非可用字段2020*/
public class IDCard {
    public static final int IMAGEMODE_GRAY = 0;
    public static final int IMAGEMODE_BGR = 1;
    public static final int IMAGEMODE_NV21 = 2;
    public static final int IMAGEMODE_RGBA = 3;
    private long IDCardHandle;
    int offset = 0;

    public IDCard() {
    }


    private boolean isContinueShadow(IDCard.Card[] cards, IDCard.Shadow[] Shadows) {
        float[] cardAverage = cards[0].average;
        float[] cardVariance = cards[0].variance;

        for (int i = 0; i < Shadows.length; ++i) {
            float[] ShadowAverage = Shadows[i].average;
            float[] ShadowVariance = Shadows[i].variance;

            for (int j = 0; j < 3; ++j) {
                if (cardAverage[j] - (float) (Math.sqrt((double) cardVariance[j]) * 0.8D) > ShadowAverage[j]) {
                    return true;
                }
            }
        }

        return false;
    }

    public IDCardQuality CalculateQuality(float faculaePass) {
        this.offset = 0;
        IDCard.IDCardQuality iCardQuality = new IDCard.IDCardQuality();
        float[] points = {0f, 1f, 1.1f};
        iCardQuality.Shadows = getShadowInfo(points, (int) points[this.offset++]);
        iCardQuality.faculaes = this.getFaculaeInfo(points, (int) points[this.offset++]);
        iCardQuality.cards = this.getCardInfo(points, (int) points[this.offset++]);
        if (iCardQuality.Shadows.length == 0) {
            iCardQuality.isShadowPass = true;
        }

        if (iCardQuality.faculaes.length == 0) {
            iCardQuality.isfaculaePass = true;
        }

        return iCardQuality;
    }

    private boolean isContinueFaculae(IDCard.Card[] cards, IDCard.Faculae[] faculaes, float faculaePass) {
        float[] cardAverage = cards[0].average;
        float[] cardVariance = cards[0].variance;

        for (int i = 0; i < faculaes.length; ++i) {
            float[] faculaeAverage = faculaes[i].average;
            float[] faculaeVariance = faculaes[i].variance;

            for (int j = 0; j < 3; ++j) {
                if (cardAverage[j] + (float) (Math.sqrt((double) cardVariance[j]) * (double) faculaePass) < faculaeAverage[j]) {
                    return true;
                }

                if (cardVariance[j] < faculaeVariance[j]) {
                    return true;
                }
            }
        }

        return false;
    }

    private IDCard.Shadow[] getShadowInfo(float[] points, int size) {
        IDCard.Shadow[] shadows = new IDCard.Shadow[size];

        for (int i = 0; i < size; ++i) {
            IDCard.Shadow shadow = new IDCard.Shadow();
            shadow.average = new float[3];

            int n;
            for (n = 0; n < shadow.average.length; ++n) {
                shadow.average[n] = points[this.offset++];
            }

            shadow.variance = new float[3];

            for (n = 0; n < shadow.variance.length; ++n) {
                shadow.variance[n] = points[this.offset++];
            }

            n = (int) points[this.offset++];
            shadow.vertex = new IDCard.PointF[n];

            for (int j = 0; j < n; ++j) {
                shadow.vertex[j] = new IDCard.PointF();
                shadow.vertex[j].x = points[this.offset++];
                shadow.vertex[j].y = points[this.offset++];
            }

            shadows[i] = shadow;
        }

        return shadows;
    }

    private IDCard.Faculae[] getFaculaeInfo(float[] points, int size) {
        IDCard.Faculae[] faculaes = new IDCard.Faculae[size];
        Log.w("ceshi", "size===" + size);

        for (int i = 0; i < size; ++i) {
            IDCard.Faculae faculae = new IDCard.Faculae();
            faculae.average = new float[3];

            int n;
            for (n = 0; n < faculae.average.length; ++n) {
                faculae.average[n] = points[this.offset++];
            }

            faculae.variance = new float[3];

            for (n = 0; n < faculae.variance.length; ++n) {
                faculae.variance[n] = points[this.offset++];
            }

            n = (int) points[this.offset++];
            faculae.vertex = new IDCard.PointF[n];

            for (int j = 0; j < n; ++j) {
                faculae.vertex[j] = new IDCard.PointF();
                faculae.vertex[j].x = points[this.offset++];
                faculae.vertex[j].y = points[this.offset++];
            }

            faculaes[i] = faculae;
        }

        return faculaes;
    }

    private IDCard.Card[] getCardInfo(float[] points, int size) {
        IDCard.Card[] cards = new IDCard.Card[size];

        for (int i = 0; i < size; ++i) {
            IDCard.Card card = new IDCard.Card();
            card.average = new float[3];

            int n;
            for (n = 0; n < card.average.length; ++n) {
                card.average[n] = points[this.offset++];
            }

            card.variance = new float[3];

            for (n = 0; n < card.variance.length; ++n) {
                card.variance[n] = points[this.offset++];
            }

            n = (int) points[this.offset++];
            card.vertex = new IDCard.PointF[n];

            for (int j = 0; j < n; ++j) {
                card.vertex[j] = new IDCard.PointF();
                card.vertex[j].x = points[this.offset++];
                card.vertex[j].y = points[this.offset++];
            }

            cards[i] = card;
        }

        return cards;
    }


    public static class Card implements Serializable {
        private static final long serialVersionUID = 3786070988580648667L;
        public float[] average;
        public float[] variance;
        public IDCard.PointF[] vertex;

        public Card() {
        }
    }

    public static class Faculae implements Serializable {
        private static final long serialVersionUID = 4644547927906498343L;
        public float[] average;
        public float[] variance;
        public IDCard.PointF[] vertex;

        public Faculae() {
        }
    }

    public static class IDCardConfig {
        public int orientation;
        public float shadowAreaTh;
        public float faculaAreaTh;
        public float cardAreaTh;
        public int shadowConfirmTh;
        public int faculaActivatedTh;
        public int faculaConfirmTh;
        public int roi_left;
        public int roi_top;
        public int roi_right;
        public int roi_bottom;
        public int need_filter;

        public IDCardConfig() {
        }
    }

    public static class IDCardDetect {
        public float inBound;
        public float isIdcard;
        public float clear;

        public IDCardDetect() {
        }
    }

    public static class IDCardQuality implements Serializable {
        private static final long serialVersionUID = 5507432037314593640L;
        public boolean isShadowPass = false;
        public boolean isfaculaePass = false;
        public IDCard.Shadow[] Shadows;
        public IDCard.Faculae[] faculaes;
        public IDCard.Card[] cards;

        public IDCardQuality() {
        }
    }

    public static class PointF implements Serializable {
        private static final long serialVersionUID = 7096991384851649494L;
        public float x;
        public float y;

        public PointF() {
        }
    }

    public static class Shadow implements Serializable {
        private static final long serialVersionUID = -5095788114139817829L;
        public float[] average;
        public float[] variance;
        public IDCard.PointF[] vertex;

        public Shadow() {
        }
    }
}