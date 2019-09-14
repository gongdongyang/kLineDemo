package com.dy.klinelib.chart.draw.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.dy.klinelib.chart.draw.ICandle;
import com.dy.klinelib.chart.draw.IChartDraw;
import com.dy.klinelib.chart.formatter.IValueFormatter;
import com.dy.klinelib.chart.formatter.ValueFormatter;
import com.dy.klinelib.chart.view.BaseChartView;
import com.dy.klinelib.util.DensityUtil;

/**
 * Created by gongdongyang 2019/9/12
 * Describe:
 */
public class MainDraw implements IChartDraw<ICandle> {
    private Context mContext;
    private int screenWidth=0;
    private int screenHeight=0;

    private boolean mShowMA = true;//显示boll

    private boolean mCandleSolid = true;

    private float mCandleWidth = 0;
    private float mCandleLineWidth = 0;

    private Paint mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mGreenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 设置ma5颜色
     *
     * @param color
     */
    public void setMa5Color(int color) {
        this.ma5Paint.setColor(color);
    }

    /**
     * 设置ma10颜色
     *
     * @param color
     */
    public void setMa10Color(int color) {
        this.ma10Paint.setColor(color);
    }

    /**
     * 设置ma20颜色
     *
     * @param color
     */
    public void setMa20Color(int color) {
        this.ma20Paint.setColor(color);
    }

    /**
     * 设置ma40颜色
     *
     * @param color
     */
    public void setMa40Color(int color) {
        this.ma40Paint.setColor(color);
    }

    /**
     * 设置ma40颜色
     *
     * @param color
     */
    public void setMa60Color(int color) {
        this.ma60Paint.setColor(color);
    }

    //MA
    private Paint ma5Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma10Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma20Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma40Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint ma60Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //Boll
    private Paint mUpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mMbPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mDnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MainDraw(BaseChartView view) {
        Context context = view.getContext();
        mContext = context;

        mRedPaint.setColor(Color.parseColor("#F27A68"));
        mGreenPaint.setColor(Color.parseColor("#3EB86A"));

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    @Override
    public void drawTranslated(@Nullable ICandle lastPoint, @NonNull ICandle curPoint, float lastX, float curX, @NonNull Canvas canvas, @NonNull BaseChartView view, int position) {
        //绘制柱体
        drawCandle(view, canvas, curPoint, curX, curPoint.getHighPrice(), curPoint.getLowPrice(), curPoint.getOpenPrice(), curPoint.getClosePrice());
        if (mShowMA) {
            //画ma5
            if (lastPoint.getMA5Price() != 0) {
                view.drawMainLine(canvas, ma5Paint, lastX, lastPoint.getMA5Price(), curX, curPoint.getMA5Price());
            }
            //画ma10
            if (lastPoint.getMA10Price() != 0) {
                view.drawMainLine(canvas, ma10Paint, lastX, lastPoint.getMA10Price(), curX, curPoint.getMA10Price());
            }
            //画ma20
            if (lastPoint.getMA20Price() != 0) {
                view.drawMainLine(canvas, ma20Paint, lastX, lastPoint.getMA20Price(), curX, curPoint.getMA20Price());
            }
            //画ma40
            if (lastPoint.getMA20Price() != 0) {
                view.drawMainLine(canvas, ma40Paint, lastX, lastPoint.getMA40Price(), curX, curPoint.getMA40Price());
            }
            //画ma60
            if (lastPoint.getMA60Price() != 0) {
                view.drawMainLine(canvas, ma60Paint, lastX, lastPoint.getMA60Price(), curX, curPoint.getMA60Price());
            }
        } else {
            //画boll
            if (lastPoint.getUp() != 0) {
                view.drawMainLine(canvas, mUpPaint, lastX, lastPoint.getUp(), curX, curPoint.getUp());
            }
            if (lastPoint.getMb() != 0) {
                view.drawMainLine(canvas, mMbPaint, lastX, lastPoint.getMb(), curX, curPoint.getMb());
            }
            if (lastPoint.getDn() != 0) {
                view.drawMainLine(canvas, mDnPaint, lastX, lastPoint.getDn(), curX, curPoint.getDn());
            }
        }
    }

    private void drawCandle(BaseChartView view, Canvas canvas, ICandle curPoint, float x, float high, float low, float open, float close) {
        high = view.getMainY(high);
        low = view.getMainY(low);
        open = view.getMainY(open);
        close = view.getMainY(close);
        float r = mCandleWidth / 2;
        float lineR = mCandleLineWidth / 2;
        if (open > close) {
            //实心
            if (mCandleSolid) {
                canvas.drawRect(x - r, close, x + r, open, mRedPaint);
                canvas.drawRect(x - lineR, high, x + lineR, low, mRedPaint);

            } else {
                mRedPaint.setStrokeWidth(mCandleLineWidth);
                canvas.drawLine(x, high, x, close, mRedPaint);
                canvas.drawLine(x, open, x, low, mRedPaint);
                canvas.drawLine(x - r + lineR, open, x - r + lineR, close, mRedPaint);
                canvas.drawLine(x + r - lineR, open, x + r - lineR, close, mRedPaint);
                mRedPaint.setStrokeWidth(mCandleLineWidth * view.getScaleX());
                canvas.drawLine(x - r, open, x + r, open, mRedPaint);
                canvas.drawLine(x - r, close, x + r, close, mRedPaint);
            }

        } else if (open < close) {
            canvas.drawRect(x - r, open, x + r, close, mGreenPaint);
            canvas.drawRect(x - lineR, high, x + lineR, low, mGreenPaint);

        } else {
            canvas.drawRect(x - r, open, x + r, close + 1, mRedPaint);
            canvas.drawRect(x - lineR, high, x + lineR, low, mRedPaint);
        }
    }


        @Override
    public void drawText(@NonNull Canvas canvas, @NonNull BaseChartView view, int position, float x, float y) {

    }

    @Override
    public float getMaxValue(ICandle point) {
        /*float bollMax = Float.isNaN(point.getUp()) ? point.getMb() : point.getUp();
        float maMax = Math.max(point.getMA60Price(),Math.max(point.getHighPrice(),point.getClosePrice()));
        return Math.max(bollMax, maMax);*/
        float maMax = 0;
        if(mShowMA){
            maMax = max(point.getMA5Price(), point.getLowPrice(),point.getMA10Price(),point.getMA20Price(),point.getMA40Price(),point.getMA60Price());
        }else{
            if(Float.isNaN(point.getDn())){
                maMax = max(point.getMb(),point.getHighPrice());
            }else{
                maMax = max(point.getDn(), point.getMb(),point.getUp(),point.getHighPrice());

            }
        }
        return maMax;

    }

    @Override
    public float getMinValue(ICandle point) {
        float maMin = 0;
        if(mShowMA){
             maMin = min(point.getMA5Price(), point.getLowPrice(),point.getMA10Price(),point.getMA20Price(),point.getMA40Price(),point.getMA60Price());
        }else{
            if(Float.isNaN(point.getDn())){
                maMin = min(point.getMb(),point.getLowPrice());
            }else{
                maMin = min(point.getDn(), point.getMb(),point.getUp(),point.getLowPrice());
            }
        }
        return maMin;

        //float maMin = Math.min(point.getMA5Price(), point.getLowPrice());
        //float bollMin = Float.isNaN(point.getDn()) ? point.getMb() : point.getDn();

    }

    @Override
    public void drawMaxAndMin(@NonNull BaseChartView view, Canvas canvas, float maxX, float minX, ICandle maxPoint, ICandle minPoint) {
        float high = view.getMainY(maxPoint.getHighPrice());
        float low = view.getMainY(minPoint.getLowPrice());

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GRAY);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(view.getTextSize());
        float aa = view.getSclase();

        if (aa < 1.5) {
            paint.setTextScaleX((2.0f - aa));
        } else {
            paint.setTextScaleX(0.5f);
        }

        Paint.FontMetrics fm = paint.getFontMetrics();
        float textHeight = fm.descent - fm.ascent;
        float baseLine = (textHeight - fm.bottom - fm.top) / 2;
        float paddingTop = DensityUtil.dp2px( 8);//距顶
        float paddingBottom = DensityUtil.dp2px( 1);//距底
        canvas.drawText(maxPoint.getHighPrice() + "", maxX, high - baseLine+paddingTop, paint);
        canvas.drawText(minPoint.getLowPrice() + "", minX, low + baseLine-paddingBottom, paint);
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

    @Override
    public IValueFormatter getValueFormatter() {
        return new ValueFormatter();
    }

    @Override
    public void setTargetColor(int... color) {

    }

    /**
     * 设置曲线宽度
     */
    public void setLineWidth(float width) {
        ma60Paint.setStrokeWidth(width);
        ma40Paint.setStrokeWidth(width);
        ma20Paint.setStrokeWidth(width);
        ma10Paint.setStrokeWidth(width);
        ma5Paint.setStrokeWidth(width);
    }

    /**
     * MA 和Boll 视图切换
     */
    public void setShowMA(boolean showMA) {
        mShowMA = showMA;
    }

    public boolean getShowMA() {
        return mShowMA;
    }

    /**
     * 设置蜡烛宽度
     *
     * @param candleWidth
     */
    public void setCandleWidth(float candleWidth) {
        mCandleWidth = candleWidth;
    }

    /**
     * 设置蜡烛线宽度
     *
     * @param candleLineWidth
     */
    public void setCandleLineWidth(float candleLineWidth) {
        mCandleLineWidth = candleLineWidth;
    }

    //boll 样式设置

    /**
     * 设置up颜色
     */
    public void setBollUpColor(int color) {
        mUpPaint.setColor(color);
    }

    /**
     * 设置mb颜色
     *
     * @param color
     */
    public void setBollMbColor(int color) {
        mMbPaint.setColor(color);
    }

    /**
     * 设置dn颜色
     */
    public void setBollDnColor(int color) {
        mDnPaint.setColor(color);
    }

    /**
     * 设置曲线宽度
     */
    public void setBollLineWidth(float width) {
        mUpPaint.setStrokeWidth(width);
        mMbPaint.setStrokeWidth(width);
        mDnPaint.setStrokeWidth(width);
    }
}
