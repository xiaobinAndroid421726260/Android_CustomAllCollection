package com.dbz.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.dbz.demo.base.BaseActivity;
import com.dbz.demo.databinding.ActivityCustomChartBinding;
import com.dbz.demo.view.LineChartView;

import java.util.ArrayList;
import java.util.List;

public class CustomChartActivity extends BaseActivity {

    private ActivityCustomChartBinding binding;
    private final List<LineChartView.XValue> mXValue1 = new ArrayList<>();
    private final List<LineChartView.LineValue> mLineValue1 = new ArrayList<>();
    private final List<LineChartView.XValue> mXValue2 = new ArrayList<>();
    private final List<LineChartView.LineValue> mLineValue2 = new ArrayList<>();

    @Override
    protected View getContentView() {
        binding = ActivityCustomChartBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView(Bundle bundle) {
        setStatusBarColor(R.color.green_color);
        binding.toolbar.setTitle("Android自定义折线图");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.onback_white);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        initArrayList1();
        initArrayList2();
        binding.lineChart1.setXYLineValue(mXValue1, mLineValue1);
        binding.lineChart2.setXYLineValue(mXValue2, mLineValue2);
    }

    private void initArrayList1() {
        mXValue1.clear();
        mLineValue1.clear();
        mXValue1.add(new LineChartView.XValue(0, "12-14"));
        mXValue1.add(new LineChartView.XValue(1, "12-15"));
        mXValue1.add(new LineChartView.XValue(2, "12-16"));
        mXValue1.add(new LineChartView.XValue(3, "12-17"));
        mXValue1.add(new LineChartView.XValue(4, "12-18"));
        mXValue1.add(new LineChartView.XValue(5, "12-19"));
        mXValue1.add(new LineChartView.XValue(6, "12-20"));
        mXValue1.add(new LineChartView.XValue(7, "12-21"));
        mXValue1.add(new LineChartView.XValue(8, "12-22"));
        mXValue1.add(new LineChartView.XValue(9, "12-23"));

        mLineValue1.add(new LineChartView.LineValue(1100, "1100"));
        mLineValue1.add(new LineChartView.LineValue(700, "700"));
        mLineValue1.add(new LineChartView.LineValue(2000, "2000"));
        mLineValue1.add(new LineChartView.LineValue(500, "500"));
        mLineValue1.add(new LineChartView.LineValue(2600, "2600"));
        mLineValue1.add(new LineChartView.LineValue(420, "420"));
        mLineValue1.add(new LineChartView.LineValue(320, "320"));
        mLineValue1.add(new LineChartView.LineValue(0, "0"));
        mLineValue1.add(new LineChartView.LineValue(600, "600"));
        mLineValue1.add(new LineChartView.LineValue(1500, "1500"));
    }

    private void initArrayList2() {
        mXValue2.clear();
        mLineValue2.clear();
        mXValue2.add(new LineChartView.XValue(0, "12-14"));
        mXValue2.add(new LineChartView.XValue(1, "12-15"));
        mXValue2.add(new LineChartView.XValue(2, "12-16"));
        mXValue2.add(new LineChartView.XValue(3, "12-17"));
        mXValue2.add(new LineChartView.XValue(4, "12-18"));
        mXValue2.add(new LineChartView.XValue(5, "12-19"));
        mXValue2.add(new LineChartView.XValue(6, "12-20"));
        mXValue2.add(new LineChartView.XValue(7, "12-21"));
        mXValue2.add(new LineChartView.XValue(8, "12-22"));
        mXValue2.add(new LineChartView.XValue(9, "12-23"));
        mXValue2.add(new LineChartView.XValue(10, "12-24"));
        mXValue2.add(new LineChartView.XValue(11, "12-25"));
        mXValue2.add(new LineChartView.XValue(12, "12-26"));
        mXValue2.add(new LineChartView.XValue(13, "12-27"));
        mXValue2.add(new LineChartView.XValue(14, "12-28"));

        mLineValue2.add(new LineChartView.LineValue(1000, "1000"));
        mLineValue2.add(new LineChartView.LineValue(700, "700"));
        mLineValue2.add(new LineChartView.LineValue(1800, "1800"));
        mLineValue2.add(new LineChartView.LineValue(500, "500"));
        mLineValue2.add(new LineChartView.LineValue(2600, "2600"));
        mLineValue2.add(new LineChartView.LineValue(420, "420"));
        mLineValue2.add(new LineChartView.LineValue(620, "620"));
        mLineValue2.add(new LineChartView.LineValue(0, "0"));
        mLineValue2.add(new LineChartView.LineValue(600, "600"));
        mLineValue2.add(new LineChartView.LineValue(1500, "1500"));
        mLineValue2.add(new LineChartView.LineValue(865, "865"));
        mLineValue2.add(new LineChartView.LineValue(500, "500"));
        mLineValue2.add(new LineChartView.LineValue(123, "123"));
        mLineValue2.add(new LineChartView.LineValue(1200, "1200"));
        mLineValue2.add(new LineChartView.LineValue(0, "0"));
    }
}