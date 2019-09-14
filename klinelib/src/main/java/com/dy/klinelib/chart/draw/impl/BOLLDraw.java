package com.dy.klinelib.chart.draw.impl;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dy.klinelib.chart.draw.IBOLL;
import com.dy.klinelib.chart.draw.IChartDraw;
import com.dy.klinelib.chart.formatter.IValueFormatter;
import com.dy.klinelib.chart.formatter.ValueFormatter;
import com.dy.klinelib.chart.view.BaseChartView;

/**
 * Created by gongdongyang 2019/9/13
 * Describe:
 */
public class BOLLDraw implements IChartDraw<IBOLL> {
    private Paint mUpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mMbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTargetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTargetNamePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public BOLLDraw(BaseChartView view) {

    }

    @Override
    public void drawTranslated(@Nullable IBOLL lastPoint, @NonNull IBOLL curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseChartView view, int position) {

    }

    @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseChartView view, int position, float x, float y) {

    }

    @Override
    public float getMaxValue(IBOLL point) {
        if (Float.isNaN(point.getUp())) {
            return point.getMb();
        }
        return point.getUp();
    }

    @Override
    public void drawMaxAndMin(@NonNull BaseChartView view, Canvas canvas, float maxX, float minX, IBOLL maxPoint, IBOLL minPoint) {

    }

    @Override
    public float getMinValue(IBOLL point) {
        if (Float.isNaN(point.getDn())) {
            return point.getMb();
        }
        return point.getDn();
    }

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    @Override
    public void setTargetColor(int... color) {

    }

    /**
     * 设置up颜色
     */
    public void setUpColor(int color) {
        mUpPaint.setColor(color);
    }

    /**
     * 设置mb颜色
     *
     * @param color
     */
    public void setMbColor(int color) {
        mMbPaint.setColor(color);
    }

    /**
     * 设置dn颜色
     */
    public void setDnColor(int color) {
        mDnPaint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        mUpPaint.setStrokeWidth(width);
        mMbPaint.setStrokeWidth(width);
        mDnPaint.setStrokeWidth(width);
    }
}
