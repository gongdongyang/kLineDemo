package com.dy.klinelib.chart.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.dy.klinelib.chart.draw.impl.BOLLDraw;
import com.dy.klinelib.chart.draw.impl.KDJDraw;
import com.dy.klinelib.chart.draw.impl.MainDraw;
import com.dy.klinelib.chart.draw.impl.VolumeDraw;
import com.dy.klinelib.util.DensityUtil;

/**
 * Created by gongdongyang 2019/9/12
 * Describe:
 */
public class KChartView extends BaseChartView {

    private MainDraw mMainDraw;

    private BOLLDraw mBOLLDraw;

    private VolumeDraw mVolumeDraw;

    private KDJDraw mKDJDraw;

    private boolean mShowMA = true;//默认显示MA

    public KChartView(Context context) {
        super(context);
        init();
    }

    public KChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setPointWidth(DensityUtil.dp2px(6));
        mMainDraw = new MainDraw(this);
        mBOLLDraw = new BOLLDraw(this);
        mVolumeDraw = new VolumeDraw(this);
        mKDJDraw = new KDJDraw(this);

        setMainDraw(mMainDraw);
        setCandleWidth(DensityUtil.dp2px(4));
        setCandleLineWidth(DensityUtil.dp2px(1));
        setLineWidth(DensityUtil.dp2px(0.5f));

        setVolumeDraw(mVolumeDraw);
        setChildDraw(mKDJDraw);

        //boll
        setMbColor(Color.parseColor("#E7EDF5"));
        setUpColor(Color.parseColor("#EFD521"));
        setDnColor(Color.parseColor("#6774FF"));

        //main#67D9FF
        setMa5Color(Color.parseColor("#E7EDF5"));
        setMa10Color(Color.parseColor("#EFD521"));
        setMa20Color(Color.parseColor("#6774FF"));
        setMa40Color(Color.parseColor("#67D9FF"));
        setMa60Color(Color.parseColor("#EFAC21"));

        //mainboll by star
        setMainMbColor(Color.parseColor("#E7EDF5"));
        setMainUpColor(Color.parseColor("#EFD521"));
        setMainDnColor(Color.parseColor("#6774FF"));

        //kdj
        setKColor(Color.parseColor("#E7EDF5"));
        setDColor(Color.parseColor("#EFD521"));
        setJColor(Color.parseColor("#6774FF"));

        setGridLineWidth(1);
        setGridLineColor(Color.parseColor("#353945"));
    }


    public MainDraw getMainDraw() {
        return mMainDraw;
    }

    @Override
    public void setLineWidth(float lineWidth) {
        super.setLineWidth(lineWidth);
        mMainDraw.setLineWidth(lineWidth);
        mBOLLDraw.setLineWidth(lineWidth);
        mMainDraw.setBollLineWidth(lineWidth);
        mKDJDraw.setLineWidth(lineWidth);
    }

    /**
     * 设置蜡烛宽度
     *
     * @param candleWidth
     */
    public void setCandleWidth(float candleWidth) {
        mMainDraw.setCandleWidth(candleWidth);
    }

    /**
     * 设置蜡烛线宽度
     *
     * @param candleLineWidth
     */
    public void setCandleLineWidth(float candleLineWidth) {
        mMainDraw.setCandleLineWidth(candleLineWidth);
    }

    /**
     * 设置up颜色
     */
    public void setUpColor(int color) {
        mBOLLDraw.setUpColor(color);
    }

    /**
     * 设置mb颜色
     *
     * @param color
     */
    public void setMbColor(int color) {
        mBOLLDraw.setMbColor(color);
    }

    /**
     * 设置dn颜色
     */
    public void setDnColor(int color) {
        mBOLLDraw.setDnColor(color);
    }

    /**
     * 设置ma5颜色
     *
     * @param color
     */
    public void setMa5Color(int color) {
        mMainDraw.setMa5Color(color);
        //mVolumeDraw.setMa5Color(color);
    }

    /**
     * 设置ma10颜色
     *
     * @param color
     */
    public void setMa10Color(int color) {
        mMainDraw.setMa10Color(color);
        //mVolumeDraw.setMa10Color(color);
    }

    /**
     * 设置ma20颜色
     *
     * @param color
     */
    public void setMa20Color(int color) {
        mMainDraw.setMa20Color(color);
    }

    /**
     * 设置ma40颜色
     *
     * @param color
     */
    public void setMa40Color(int color) {
        mMainDraw.setMa40Color(color);
    }

    /**
     * 设置ma60颜色
     *
     * @param color
     */
    public void setMa60Color(int color) {
        mMainDraw.setMa60Color(color);
    }


    public void setShowMA(boolean showMA) {
        mShowMA = showMA;
        mMainDraw.setShowMA(showMA);
    }

    /**
     * 设置up颜色
     */
    public void setMainUpColor(int color) {
        mMainDraw.setBollUpColor(color);
    }

    /**
     * 设置mb颜色
     *
     * @param color
     */
    public void setMainMbColor(int color) {
        mMainDraw.setBollMbColor(color);
    }

    /**
     * 设置dn颜色
     */
    public void setMainDnColor(int color) {
        mMainDraw.setBollDnColor(color);
    }

    /**
     * 设置K颜色
     */
    public void setKColor(int color) {
        mKDJDraw.setKColor(color);
    }

    /**
     * 设置D颜色
     */
    public void setDColor(int color) {
        mKDJDraw.setDColor(color);
    }

    /**
     * 设置J颜色
     */
    public void setJColor(int color) {
        mKDJDraw.setJColor(color);
    }
}
