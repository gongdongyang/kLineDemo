package com.dy.klinelib.chart.formatter;

/**
 * Created by gongdongyang 2019/9/12
 * Describe:
 */
public interface IValueFormatter {
    /**
     * 格式化value
     *
     * @param value 传入的value值
     * @return 返回字符串
     */
    String format(float value);
}
