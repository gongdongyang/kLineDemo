package com.dy.klinelib.chart.view;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.OverScroller;
import android.widget.RelativeLayout;

/**
 * Created by gongdongyang 2019/9/12
 * Describe:
 */
public  abstract class ScrollAndScaleView extends RelativeLayout
        implements GestureDetector.OnGestureListener,
        ScaleGestureDetector.OnScaleGestureListener {

    public OverScroller mScroller;

    protected boolean isLongPress = false;
    protected boolean isClosePress = true;

    protected boolean touch = false;

    protected float mScaleX = 1;

    protected float mScaleXMax = 2f;

    protected float mScaleXMin = 0.5f;

    protected int mScrollX = 0;

    private boolean mScrollEnable = true;

    private boolean mScaleEnable = true;

    private boolean isScale = false;

    private boolean mMultipleTouch = false;


    protected GestureDetectorCompat mDetector;

    protected ScaleGestureDetector mScaleDetector;

    public ScrollAndScaleView(Context context) {
        super(context);
        init();
    }

    public ScrollAndScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollAndScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setWillNotDraw(false);
        mDetector = new GestureDetectorCompat(getContext(), this);
        mScaleDetector = new ScaleGestureDetector(getContext(),this);
        mScroller = new OverScroller(getContext());
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("TAG","distanceY=="+distanceY);
        if (!isScale && !isLongPress && !isMultipleTouch()) {
            scrollBy(Math.round(distanceX), 0);
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(mScrollX,0,Math.round(velocityX / mScaleX),0,
                Integer.MIN_VALUE, Integer.MAX_VALUE,0,0 );
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                touch = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch = false;
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                touch = false;
                invalidate();
                break;
        }
        mMultipleTouch = event.getPointerCount()>1;
        this.mDetector.onTouchEvent(event);
        this.mScaleDetector.onTouchEvent(event);
        return true;
    }

    /**
     * 获取位移的最小值
     *
     * @return
     */
    public abstract int getMinScrollX();

    /**
     * 获取位移的最大值
     *
     * @return
     */
    public abstract int getMaxScrollX();

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (!isScaleEnable()) {
            return false;
        }
        float oldScale = mScaleX;
        mScaleX *= detector.getScaleFactor();
        if (mScaleX < mScaleXMin) {
            mScaleX = mScaleXMin;
        } else if (mScaleX > mScaleXMax) {
            mScaleX = mScaleXMax;
        } else {
            onScaleChanged(mScaleX, oldScale);
        }

        if (mScaleX >= 2.0f || mScaleX <= 0.5f) {
            isScale = true;
        }
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        isScale = true;
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        isScale = false;
    }

    protected void onScaleChanged(float scale, float oldScale) {
        isScale = true;
        invalidate();
    }

    @Override
    public void scrollBy(int x, int y) {
        //super.scrollBy(x, y);
        scrollTo((int)(mScrollX - Math.round(x / mScaleX)), 0);
    }

    @Override
    public void scrollTo(int x, int y) {
        int oldX = mScrollX;
        mScrollX = x;
        if (mScrollX < getMinScrollX()) {
            mScrollX = getMinScrollX();
            mScroller.forceFinished(true);
        } else if (mScrollX > getMaxScrollX()) {
            mScrollX = getMaxScrollX();
            mScroller.forceFinished(true);
        }
        onScrollChanged(mScrollX, 0, oldX, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (!isTouch()) {
                //  scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
                scrollTo(mScroller.getCurrX(), 0);
            } else {
                mScroller.forceFinished(true);
            }
        }
    }

    /**
     * 是否在触摸中
     *
     * @return
     */
    public boolean isTouch() {
        return touch;
    }

    public boolean isScaleEnable() {
        return mScaleEnable;
    }

    /**
     * 是否是多指触控
     *
     * @return
     */
    public boolean isMultipleTouch() {
        return mMultipleTouch;
    }


}
