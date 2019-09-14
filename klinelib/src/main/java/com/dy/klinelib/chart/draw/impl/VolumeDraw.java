package com.dy.klinelib.chart.draw.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.dy.klinelib.R;
import com.dy.klinelib.chart.draw.IChartDraw;
import com.dy.klinelib.chart.draw.IVolume;
import com.dy.klinelib.chart.formatter.IValueFormatter;
import com.dy.klinelib.chart.formatter.ValueFormatter;
import com.dy.klinelib.chart.view.BaseChartView;
import com.dy.klinelib.util.DensityUtil;

/**
 * Created by gongdongyang 2019/9/13
 * Describe:
 */
public class VolumeDraw implements IChartDraw<IVolume> {

    private Paint mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mGreenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma5Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma10Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int pillarWidth = 0;

    public VolumeDraw(BaseChartView view){
        Context context = view.getContext();
        mRedPaint.setColor(ContextCompat.getColor(context, R.color.chart_red));
        mGreenPaint.setColor(ContextCompat.getColor(context, R.color.chart_green));
        pillarWidth = DensityUtil.dp2px(4);
    }

    @Override
    public void drawTranslated(@Nullable IVolume lastPoint, @NonNull IVolume curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseChartView view, int position) {
        drawHistogram(canvas, curPoint, lastPoint, curX, view, position);
        //view.drawChildLine(canvas, interestPaint, lastX, lastPoint.getInterest(), curX, curPoint.getInterest());
        drawMALine(canvas,curPoint,lastPoint,curX,lastX,view);
        //drawMA10Line();
    }

    private void drawHistogram(
            Canvas canvas, IVolume curPoint, IVolume lastPoint, float curX,
            BaseChartView view, int position) {

        float r = pillarWidth / 2;

        float top = view.getVolumeY(curPoint.getVolume());

        int bottom = view.getVolumeRect().bottom;

        Log.d("VolumeDraw","curX:"+curX+"top:"+top+"bottom:"+bottom);

        if (curPoint.getClosePrice() >= curPoint.getOpenPrice()) {
            canvas.drawRect(curX - r, top, curX + r, bottom, mRedPaint);
        } else {
            canvas.drawRect(curX - r, top, curX + r, bottom, mGreenPaint);
        }

    }

    private void drawMALine( Canvas canvas,IVolume curPoint,IVolume lastPoint,float curX, float lastX,BaseChartView view){
        canvas.drawLine(lastX,view.getVolumeY(lastPoint.getMA5Volume()),curX,view.getVolumeY(curPoint.getMA5Volume()),mGreenPaint);
        canvas.drawLine(lastX,view.getVolumeY(lastPoint.getMA10Volume()),curX,view.getVolumeY(curPoint.getMA10Volume()),mRedPaint);
    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseChartView view, int position, float x, float y) {

    }

    @Override
    public float getMaxValue(IVolume point) {
        float max = max(point.getMA10Volume(),point.getMA5Volume(),point.getVolume());
        return max;
    }

    @Override
    public void drawMaxAndMin(@NonNull BaseChartView view, Canvas canvas, float maxX, float minX, IVolume maxPoint, IVolume minPoint) {

    }

    @Override
    public float getMinValue(IVolume point) {
        return min(point.getMA10Volume(),point.getMA5Volume(),point.getVolume());
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
}
