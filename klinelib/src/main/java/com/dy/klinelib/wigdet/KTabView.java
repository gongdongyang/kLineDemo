package com.dy.klinelib.wigdet;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dy.klinelib.R;

/**
 * Created by gongdongyang 2019/9/13
 * Describe:
 */
public class KTabView extends LinearLayout implements View.OnClickListener {

    public TextView titleTv;

    public View indicatorView;

    public boolean seleted = false;


    public KTabView(Context context) {
        this(context,null);
    }

    public KTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public KTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_item_tab,this);

        setOnClickListener(this);

        titleTv = findViewById(R.id.tvTabText);
        indicatorView = findViewById(R.id.indicator);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.KTabView);
        float textSize = array.getDimension(R.styleable.KTabView_k_textSize,6);

        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);

        String text = array.getString(R.styleable.KTabView_k_text);
        titleTv.setText(text);
        array.recycle();
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(),"onclick",Toast.LENGTH_SHORT).show();
    }

    public void setSelectState(int color,boolean state){
        titleTv.setTextColor(color);
        seleted = state;
    }

    public void resetState(int color){
        titleTv.setTextColor(color);
        seleted = false;
    }
}
