package com.dy.klinelib.chart.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

/**
 * Created by gongdongyang 2019/9/12
 * Describe:
 */
public abstract class BaseKChartAdapter implements IAdapter {

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    public void notifyDataSetChanged() {
        if(getCount()>0){
            mDataSetObservable.notifyChanged();
        }else{
            mDataSetObservable.notifyInvalidated();
        }
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

}
