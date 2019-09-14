package com.dy.klinechartdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dy.klinechartdemo.adapter.KChartAdapter;
import com.dy.klinechartdemo.data.DataRequest;
import com.dy.klinelib.chart.entity.KLine;
import com.dy.klinelib.chart.view.KChartView;

import java.util.List;

public class RateViewActivity extends AppCompatActivity {

    private KChartView mKChartView;

    private KChartAdapter mKAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rate_view);

        mKChartView = findViewById(R.id.kChart);
        mKChartView.setShowMA(false);
        mKAdapter = new KChartAdapter();

        mKChartView.setAdapter(mKAdapter);

        //List<KLine> list = DataRequest.getAll(this,0);

        //Log.d("TAG","size==="+list.size());

        new Handler().postDelayed(()->{
            fillData();
        },1000);

    }

    public void fillData(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                final List<KLine> kdata = DataRequest.getAll(RateViewActivity.this, 0);
                Log.d("TAG","size=="+kdata.size());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mKAdapter.addFooterData(kdata);
                        mKChartView.startAnimation();
                    }
                });


            }
        }).start();
    }
}
