package com.dy.klinelib.chart.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.dy.klinelib.chart.adapter.IAdapter;
import com.dy.klinelib.chart.draw.ICandle;
import com.dy.klinelib.chart.draw.IChartDraw;
import com.dy.klinelib.chart.draw.IKLine;
import com.dy.klinelib.chart.entity.KLine;
import com.dy.klinelib.util.DensityUtil;

/**
 * Created by gongdongyang 2019/9/12
 * Describe:
 */
public abstract class BaseChartView extends ScrollAndScaleView {

    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float mTranslateX = Float.MIN_VALUE;
    private float mMainScaleY = 1;
    private float mVolumeScaleY = 1;
    private float mChildScaleY = 1;

    private float mMainMaxValue = Float.MAX_VALUE;
    private float mMainMinValue = Float.MIN_VALUE;
    private float mVolumeMaxValue = Float.MAX_VALUE;
    private float mVolumeMinValue = Float.MIN_VALUE;

    private float mChildMaxValue = Float.MAX_VALUE;
    private float mChildMinValue = Float.MIN_VALUE;



    private float mMaxValue;
    private float mMinValue;
    private float mMaxX;
    private float mMinX;

    private float mOverScrollRange = 0;
    private float mLineWidth;

    private int mWidth = 0;

    private int mStartIndex = 0;
    private int mStopIndex = 0;

    private int mMainHeight;//主视图高
    private int mVolumeHeght;//子图高度
    private int mChildHeght;//子图高度
    private int mItemCount;//当前点的个数

    private int displayHeight;
    private int h;

    private IAdapter mAdapter;

    private float mDataLen;

    private float mPointWidth = 6;

    private int mSelectedIndex;

    private IChartDraw mMainDraw;
    private IChartDraw mVolumeDraw;
    private IChartDraw mChildDraw;

    private ICandle mMaxPoint;
    private ICandle mMinPoint;

    private Rect mMainRect;
    private Rect mTabRect;
    private Rect mVolumeRect;
    private Rect mChildRect;

    private ValueAnimator mAnimator;

    private long mAnimationDuration = 500;

    private int mBasePaddingLeft = DensityUtil.dp2px(25);  //左padding
    private int mBasePaddingRight = DensityUtil.dp2px(25);//右padding
    private int mMainChildSpace = DensityUtil.dp2px(20);//中间选项高
    private int mTopPadding = DensityUtil.dp2px(0);//距顶部距离;
    public static int mBottomPadding = DensityUtil.dp2px(0);//距底部距离


    private Paint mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mGridRows = 6;
    private int mGridColumns = 5;

    public BaseChartView(Context context) {
        super(context);
        init();
    }

    public BaseChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mWidth == 0 || mItemCount == 0){
            return;
        }
        calculateValue();
        canvas.save();
        canvas.scale(1, 1);
        drawGird(canvas);
        drawK(canvas);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        initRect(w,h);
        setTranslateXFromScrollX(mScrollX);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        setTranslateXFromScrollX(mScrollX);
    }

    private DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            mItemCount = getAdapter().getCount();
            notifyChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            mItemCount = getAdapter().getCount();
            notifyChanged();
        }
    };

    private void init(){
        mAnimator = ValueAnimator.ofFloat(0f, 1f);
        mAnimator.setDuration(mAnimationDuration);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });

    }

    /**
     * 计算当前的显示区域
     */
    private void calculateValue() {
        mMainMaxValue = Float.MIN_VALUE;
        mMainMinValue = Float.MAX_VALUE;
        mVolumeMaxValue = Float.MIN_VALUE;
        mVolumeMinValue = Float.MAX_VALUE;
        mChildMaxValue = Float.MIN_VALUE;
        mChildMinValue = Float.MAX_VALUE;

        mStartIndex = indexOfTranslateX(xToTranslateX(0));
        mStopIndex = indexOfTranslateX(xToTranslateX(mWidth));

        for (int i = mStartIndex; i <= mStopIndex; i++) {
            IKLine point = (IKLine) getItem(i);
            if (mMainDraw != null) {
                mMainMaxValue = Math.max(mMainMaxValue, mMainDraw.getMaxValue(point));
                mMainMinValue = Math.min(mMainMinValue, mMainDraw.getMinValue(point));
            }

            if(mVolumeDraw != null){
                mVolumeMaxValue = Math.max(mVolumeMaxValue, mVolumeDraw.getMaxValue(point));
                mVolumeMinValue = Math.min(mVolumeMinValue, mVolumeDraw.getMinValue(point));
            }

            if(mChildDraw!=null){
                mChildMaxValue = Math.max(mChildMaxValue, mChildDraw.getMaxValue(point));
                mChildMinValue = Math.min(mChildMinValue, mChildDraw.getMinValue(point));
            }
        }

        if (mMainMaxValue != mMainMinValue) {
            float padding = (mMainMaxValue - mMainMinValue) * 0.05f;
            mMainMaxValue += padding;
            mMainMinValue -= padding;
        } else {
            //当最大值和最小值都相等的时候 分别增大最大值和 减小最小值
            mMainMaxValue += Math.abs(mMainMaxValue * 0.05f);
            mMainMinValue -= Math.abs(mMainMinValue * 0.05f);
            if (mMainMaxValue == 0) {
                mMainMaxValue = 1;
            }
        }
        mMainScaleY = mMainRect.height() * 1f / (mMainMaxValue - mMainMinValue);
        mVolumeScaleY = mVolumeRect.height() * 1f / (mVolumeMaxValue - mVolumeMinValue);
        mChildScaleY = mChildRect.height() * 1f / (mChildMaxValue - mChildMinValue);

        Log.d("ScaleY","mChildScaleY"+mChildScaleY+"mChildMaxValue: "+mChildMaxValue+"mChildMinValue:"+mChildMinValue);

        if (mAnimator.isRunning()) {
            float value = (float) mAnimator.getAnimatedValue();
            mStopIndex = mStartIndex + Math.round(value * (mStopIndex - mStartIndex));
        }

    }

    private void initRect(int w, int h) {
        this.h = h;
        displayHeight = h - mTopPadding - mBottomPadding - 2*mMainChildSpace;
        mMainHeight = (int) (displayHeight * 0.6f);
        mMainRect = new Rect(0, mTopPadding, mWidth, mTopPadding + mMainHeight);

        mVolumeHeght = (int) (displayHeight * 0.2f);
        mVolumeRect = new Rect(0,mMainRect.bottom+mMainChildSpace,
                mWidth,mMainRect.bottom+mMainChildSpace+mVolumeHeght);

        mChildHeght = (int) (displayHeight * 0.2f);
        mChildRect = new Rect();
        mChildRect.top = mVolumeRect.bottom+mMainChildSpace;
        mChildRect.left = 0;
        mChildRect.right = mWidth;
        mChildRect.bottom = mVolumeRect.bottom+mMainChildSpace+mChildHeght;
    }

    /**
     * 画表格
     * @param canvas
     */
    private void drawGird(Canvas canvas) {

        float rowSpace = mMainRect.height() / mGridRows;

        for (int i = 0; i <= mGridRows; i++) {
            canvas.drawLine(0, rowSpace * i + mMainRect.top, mWidth, rowSpace * i + mMainRect.top, mGridPaint);
        }


        //纵向的grid
        float columnSpace = (mWidth - mBasePaddingLeft - mBasePaddingRight) / mGridColumns;
        for (int i = 0; i <= mGridColumns; i++) {
            canvas.drawLine(columnSpace * i + mBasePaddingLeft, mMainRect.top, columnSpace * i + mBasePaddingRight, mMainRect.bottom, mGridPaint);
        }

        //成交量--纵向
        for (int i = 0; i <= mGridColumns; i++) {
            canvas.drawLine(columnSpace * i + mBasePaddingLeft, mVolumeRect.top,
                    columnSpace * i + mBasePaddingRight, mVolumeRect.bottom, mGridPaint);

        }

        float childRowSpace = (mVolumeRect.height()) ;

        for (int i = 0; i < 2; i++) {
            canvas.drawLine(0, mVolumeRect.top+i*childRowSpace, mWidth,
                    mVolumeRect.top+i*childRowSpace, mGridPaint);
        }

        //附图
        for (int i = 0; i <= mGridColumns; i++) {
            canvas.drawLine(columnSpace * i + mBasePaddingLeft, mChildRect.top,
                    columnSpace * i + mBasePaddingRight, mChildRect.bottom, mGridPaint);

        }

        for (int i = 0; i < 2; i++) {
            canvas.drawLine(0, mChildRect.top+i*childRowSpace, mWidth,
                    mChildRect.top+i*childRowSpace, mGridPaint);
        }

    }

    /**
     * 获取适配器
     *
     * @return
     */
    public IAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 设置数据适配器
     */
    public void setAdapter(IAdapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mDataSetObserver);
            mItemCount = mAdapter.getCount();
        } else {
            mItemCount = 0;
        }
        notifyChanged();
    }


    /**
     * 重新计算并刷新线条
     */
    public void notifyChanged() {
        if (mItemCount != 0) {
            mDataLen = (mItemCount - 1) * mPointWidth;
            checkAndFixScrollX();
            setTranslateXFromScrollX(mScrollX);
        } else {
            setScrollX(0);
        }
        invalidate();
    }



    /**
     *
     */
    private void checkAndFixScrollX(){
        if (mScrollX < getMinScrollX()) {
            mScrollX = getMinScrollX();
            mScroller.forceFinished(true);
        }else if(mScrollX > getMaxScrollX()){
            mScrollX = getMaxScrollX();
            mScroller.forceFinished(true);
        }
    }

    private void setTranslateXFromScrollX(float scrollX){
        mTranslateX = scrollX + getMinTranslateX();

    }

    /**
     * 获取平移的最小值
     *
     * @return
     */
    private float getMinTranslateX() {
        return -(mDataLen-mWidth/mScaleX+mPointWidth / 2);
    }

    /**
     * 数据是否充满屏幕
     *
     * @return
     */
    public boolean isFullScreen() {
        return mDataLen >= mWidth / mScaleX;
    }


    private float getMaxTranslateX() {
        if(!isFullScreen()){
            return getMinTranslateX();
        }
        return mPointWidth / 2;
    }

    /**
     * 根据索引获取实体
     *
     * @param position 索引值
     * @return
     */
    public Object getItem(int position) {
        if (mAdapter != null) {
            return mAdapter.getItem(position);
        } else {
            return null;
        }
    }

    public int indexOfTranslateX(float translateX) {
        return indexOfTranslateX(translateX, 0, mItemCount - 1);
    }


    /**
     * 二分查找当前值的index
     *
     * @return
     */
    public int indexOfTranslateX(float translateX, int start, int end) {
        if (end == start) {
            return start;
        }
        if (end - start == 1) {
            float startValue = getX(start);
            float endValue = getX(end);
            return Math.abs(translateX - startValue) < Math.abs(translateX - endValue) ? start : end;
        }
        int mid = start + (end - start) / 2;
        float midValue = getX(mid);
        if (translateX < midValue) {
            return indexOfTranslateX(translateX, start, mid);
        } else if (translateX > midValue) {
            return indexOfTranslateX(translateX, mid, end);
        } else {
            return mid;
        }
    }


    /**
     * 根据索引索取x坐标
     *
     * @param position 索引值
     * @return
     */
    public float getX(int position) {
        return position * mPointWidth;
    }

    /**
     * view中的x转化为TranslateX
     *
     * @param x
     * @return
     */
    public float xToTranslateX(float x) {
        return -mTranslateX + x / mScaleX;
    }

    public float translateXtoX(float translateX){
        return (translateX + mTranslateX) * mScaleX;
    }

    public float getMainY(float value) {
        return (mMainMaxValue - value) * mMainScaleY + mMainRect.top;
    }

    public float getVolumeY(float value) {
        return (mVolumeMaxValue - value) * mVolumeScaleY + mVolumeRect.top;
    }

    public float getChildY(float value) {
        return (mChildMaxValue - value) * mChildScaleY + mChildRect.top;
    }

    @Override
    public int getMinScrollX() {
        return (int) -(mOverScrollRange / mScaleX);
    }

    public int getMaxScrollX() {
        return Math.round(getMaxTranslateX() - getMinTranslateX());
    }

    /**
     * 获取文字大小
     */
    public float getTextSize() {
        return mTextPaint.getTextSize();
    }

    public float getSclase() {
        return mScaleX;
    }

    /**
     * 获取主区域的 IChartDraw
     *
     * @return IChartDraw
     */
    public IChartDraw getMainDraw() {
        return mMainDraw;
    }

    /**
     * 设置主区域的 IChartDraw
     *
     * @param mainDraw IChartDraw
     */
    public void setMainDraw(IChartDraw mainDraw) {
        mMainDraw = mainDraw;
    }

    public void setVolumeDraw(IChartDraw volumeDraw){
        mVolumeDraw = volumeDraw;
    }

    public void setChildDraw(IChartDraw childDraw) {
        mChildDraw = childDraw;
    }


    public Rect getVolumeRect(){
        return mVolumeRect;
    }

    /**
     * 设置每个点的宽度
     */
    public void setPointWidth(float pointWidth) {
        mPointWidth = pointWidth;
    }

    /**
     * 画k线图
     * @param canvas
     */
    private void drawK(Canvas canvas) {
        //保存之前的平移，缩放
        canvas.save();
        canvas.translate(mTranslateX * mScaleX, 0);
        canvas.scale(mScaleX, 1);
        mMaxValue = ((ICandle) getItem(mStartIndex)).getHighPrice();
        mMinValue = ((ICandle) getItem(mStartIndex)).getLowPrice();
        for (int i = mStartIndex; i <= mStopIndex; i++) {
            Object currentPoint = getItem(i);
            float currentPointX = getX(i);
            Object lastPoint = i == 0 ? currentPoint : getItem(i - 1);
            float lastX = i == 0 ? currentPointX : getX(i - 1);
            if (mMainDraw != null) {
                if (mMaxValue < ((ICandle) getItem(i)).getHighPrice()) {
                    mMaxValue = ((ICandle) getItem(i)).getHighPrice();
                    mMaxPoint = (ICandle) getItem(i);
                    mMaxX = currentPointX;
                } else if (mMinValue > ((ICandle) getItem(i)).getLowPrice()) {
                    mMinValue = ((ICandle) getItem(i)).getLowPrice();
                    mMinPoint = (ICandle) getItem(i);
                    mMinX = currentPointX;
                }
                mMainDraw.drawTranslated(lastPoint, currentPoint, lastX, currentPointX, canvas, this, i);
            }

            if(mVolumeDraw != null){
                mVolumeDraw.drawTranslated(lastPoint,currentPoint,lastX, currentPointX, canvas, this, i);
            }

            if(mChildDraw!=null){
                mChildDraw.drawTranslated(lastPoint,currentPoint,lastX, currentPointX, canvas, this, i);
            }
        }

        canvas.restore();
    }


    /**
     * 在主区域画线
     *
     * @param startX    开始点的横坐标
     * @param stopX     开始点的值
     * @param stopX     结束点的横坐标
     * @param stopValue 结束点的值
     */
    public void drawMainLine(Canvas canvas, Paint paint, float startX, float startValue, float stopX, float stopValue) {
        canvas.drawLine(startX, getMainY(startValue), stopX, getMainY(stopValue), paint);
    }


    /**
     * 开始动画
     */
    public void startAnimation() {
        if (mAnimator != null) {
            mAnimator.start();
        }
    }


    /**
     * 设置曲线的宽度
     */
    public void setLineWidth(float lineWidth) {
        mLineWidth = lineWidth;
    }


    /**
     * 设置表格线宽度
     */
    public void setGridLineWidth(float width) {
        mGridPaint.setStrokeWidth(width);
    }

    /**
     * 设置表格线颜色
     */
    public void setGridLineColor(int color) {
        mGridPaint.setColor(color);
    }
}
