package com.cf.basketball.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cf.basketball.R;
import com.example.admin.basic.utils.LogUtils;

/**
 * 趋势图详情
 *
 * @author admin xinxin Shi
 */
public class TrendChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_chart);
        LogUtils.e("趋势图详情页");
    }
}
