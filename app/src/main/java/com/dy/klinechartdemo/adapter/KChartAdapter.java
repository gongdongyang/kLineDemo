package com.dy.klinechartdemo.adapter;

import com.dy.klinelib.chart.adapter.BaseKChartAdapter;
import com.dy.klinelib.chart.entity.KLine;
import com.dy.klinelib.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gongdongyang 2019/9/12
 * Describe:
 */
public class KChartAdapter extends BaseKChartAdapter {

    private List<KLine> datas = new ArrayList<>();

    public KChartAdapter() {
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public Date getDate(int position) {
        /*try {
            String s = datas.get(position).get();
            return DateUtil.getDateByByStringDate(s);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null;
    }

    /**
     * 向尾部添加数据
     */
    public void addFooterData(List<KLine> data) {
        if (data != null && !data.isEmpty()) {
            datas.addAll( data);
            notifyDataSetChanged();
        }
    }
}
