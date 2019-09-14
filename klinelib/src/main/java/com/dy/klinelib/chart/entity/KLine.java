package com.dy.klinelib.chart.entity;

import com.dy.klinelib.chart.draw.IKLine;

/**
 * Created by gongdongyang 2019/9/12
 * Describe:
 */
public class KLine implements IKLine {

    public String m;

    public float o;//开盘价
    public float h;
    public float c;
    public float l;
    public float v;//成交量
    //public long t;

    public float MA5Price;

    public float MA10Price;

    public float MA20Price;

    public float MA26Price;

    public float MA40Price;

    public float MA60Price;

    public float dea;

    public float dif;

    public float macd;

    public float k;

    public float d;

    public float j;

    public float rsi1;

    public float rsi2;

    public float rsi3;

    public float up;

    public float mb;

    public float dn;

    public float MA5Volume;

    public float MA10Volume;

    @Override
    public float getOpenPrice() {
        return o;
    }

    @Override
    public float getHighPrice() {
        return h;
    }

    @Override
    public float getLowPrice() {
        return l;
    }

    @Override
    public float getClosePrice() {
        return c;
    }

    @Override
    public float getMA5Price() {
        return MA5Price;
    }

    @Override
    public float getMA10Price() {
        return MA10Price;
    }

    @Override
    public float getMA20Price() {
        return MA20Price;
    }

    @Override
    public float getMA26Price() {
        return MA26Price;
    }

    @Override
    public float getMA40Price() {
        return MA40Price;
    }

    @Override
    public float getMA60Price() {
        return MA60Price;
    }

    @Override
    public float getVolume() {
        return v;
    }

    @Override
    public float getChgVolume() {
        return 0;
    }

    @Override
    public float getInterest() {
        return 0;
    }

    @Override
    public String getChgInterest() {
        return null;
    }

    @Override
    public String getPreClose() {
        return null;
    }

    @Override
    public String getUpDown() {
        return null;
    }

    @Override
    public String getPercent() {
        return null;
    }

    @Override
    public float getUp() {
        return up;
    }

    @Override
    public float getMb() {
        return mb;
    }

    @Override
    public float getDn() {
        return dn;
    }


    @Override
    public float getMA5Volume() {
        return MA5Volume;
    }

    @Override
    public float getMA10Volume() {
        return MA10Volume;
    }


    @Override
    public float getK() {
        return k;
    }

    @Override
    public float getD() {
        return d;
    }

    @Override
    public float getJ() {
        return j;
    }
}
