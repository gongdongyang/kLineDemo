package com.dy.klinechartdemo.data;

import android.content.Context;

import com.dy.klinelib.chart.entity.KLine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

/**
 * Created by gongdongyang 2019/9/12
 * Describe:
 */
public class DataRequest {

    private static Random random = new Random();

    public static String getStringFromAssert(Context context, String fileName) {
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            return new String(buffer, 0, buffer.length, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<KLine> getAll(Context context, int type) {

        String json = getStringFromAssert(context, "kline_day.json");//日K

        final IResponse<List<KLine>> resp
                = new Gson().fromJson(json, new TypeToken<IResponse<List<KLine>>>() {}.getType());
        DataHelper.calculate(resp.data);
        return resp.data;
    }
}
