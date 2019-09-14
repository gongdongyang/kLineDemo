package com.dy.klinelib.chart.draw.impl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dy.klinelib.chart.draw.IChartDraw;
import com.dy.klinelib.chart.draw.IKDJ;
import com.dy.klinelib.chart.formatter.IValueFormatter;
import com.dy.klinelib.chart.formatter.ValueFormatter;
import com.dy.klinelib.chart.view.BaseChartView;

/**
 * Created by gongdongyang 2019/9/13
 * Describe:
 */
public class KDJDraw implements IChartDraw<IKDJ> {

    private Paint mKPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mJPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTargetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTargetNamePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public KDJDraw(BaseChartView chartView) {

    }

    @Override
    public void drawTranslated(@Nullable IKDJ lastPoint, @NonNull IKDJ curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseChartView view, int position) {
        Log.d("KDJ-->Point","lastX:"+lastX+"lastY:"+view.getChildY(lastPoint.getK()));
        canvas.drawLine(lastX, view.getChildY(lastPoint.getK()), curX, view.getChildY(curPoint.getK()),mKPaint);
        canvas.drawLine(lastX, view.getChildY(lastPoint.getD()), curX, view.getChildY(curPoint.getD()),mDPaint);
        canvas.drawLine(lastX, view.getChildY(lastPoint.getJ()), curX, view.getChildY(curPoint.getJ()),mJPaint);
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseChartView view, int position, float x, float y) {

    }

    @Override
    public float getMaxValue(IKDJ point) {
        Log.d("KDJ","k"+point.getK()+"D:"+point.getD()+"J:"+point.getJ());
        return max(point.getD(),point.getJ(),point.getK());
    }

    @Override
    public void drawMaxAndMin(@NonNull BaseChartView view, Canvas canvas, float maxX, float minX, IKDJ maxPoint, IKDJ minPoint) {

    }

    @Override
    public float getMinValue(IKDJ point) {
        return min(point.getD(),point.getJ(),point.getK());
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    @Override
    public void setTargetColor(int... color) {

    }


    public float min(float ...v){
        float min = v[0];
        for (int i = 0; i < v.length; i++) {
            min = Math.min(min,v[i]);
        }
        return min;
    }

    public float max(float ...v){
        float max = v[0];
        for (int i = 0; i < v.length; i++) {
            max = Math.max(max,v[i]);
        }
        return max;
    }

    /**
     * 设置K颜色
     */
    public void setKColor(int color) {
        mKPaint.setColor(color);
    }

    /**
     * 设置D颜色
     */
    public void setDColor(int color) {
        mDPaint.setColor(color);
    }

    /**
     * 设置J颜色
     */
    public void setJColor(int color) {
        mJPaint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        mKPaint.setStrokeWidth(width);
        mDPaint.setStrokeWidth(width);
        mJPaint.setStrokeWidth(width);
        mTargetPaint.setStrokeWidth(width);
        mTargetNamePaint.setStrokeWidth(width);
    }
}
