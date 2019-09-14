package com.dy.klinelib.wigdet;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.dy.klinelib.R;

/**
 * Created by gongdongyang 2019/9/13
 * Describe:
 */
public class KTabLayout extends LinearLayout  {

    private int selectColor;

    private int unSelectColor;


    public KTabLayout(Context context) {
        this(context,null);
    }

    public KTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public KTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);

        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.KTabView);

        selectColor = typedArray.getColor(R.styleable.KTabView_k_select_color, Color.parseColor("#D81B60"));

        unSelectColor = typedArray.getColor(R.styleable.KTabView_k_unselect_color, Color.parseColor("#00574B"));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            KTabView kTabView =  (KTabView)getChildAt(i);
            kTabView.setOnClickListener(selectListener);
            //kTabView.titleTv.setTextColor(selectColor);
        }

    }

    View.OnClickListener selectListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            KTabView kTabView =  (KTabView)v;

            //重置
            for (int i = 0; i < getChildCount(); i++) {
                ((KTabView)getChildAt(i)).resetState(unSelectColor);
            }

            kTabView.setSelectState(selectColor,true);
        }
    };
}
