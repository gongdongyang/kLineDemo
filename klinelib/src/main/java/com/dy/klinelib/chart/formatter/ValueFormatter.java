package com.dy.klinelib.chart.formatter;

/**
 * Created by gongdongyang 2019/9/12
 * Describe:
 */
public class ValueFormatter implements IValueFormatter {

    @Override
    public String format(float value) {
        return String.format("%.8f", value);
    }

}
