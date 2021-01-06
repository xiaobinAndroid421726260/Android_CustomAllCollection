package com.dbz.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.dbz.demo.base.BaseActivity;
import com.dbz.demo.databinding.ActivityChartReportBinding;
import com.dbz.demo.view.ChartReportView;

import java.util.ArrayList;
import java.util.List;

public class ChartReportActivity extends BaseActivity {

    private final List<ChartReportView.XValue> mXValue1 = new ArrayList<>();
    private final List<ChartReportView.YValue> mYValue1 = new ArrayList<>();
    private final List<ChartReportView.XValue> mXValue2 = new ArrayList<>();
    private final List<ChartReportView.YValue> mYValue2 = new ArrayList<>();
    private ActivityChartReportBinding binding;

    @Override
    protected View getContentView() {
        binding = ActivityChartReportBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView(Bundle bundle) {
        setStatusBarColor(R.color.blue_color);
        binding.toolbar.setTitle("Android自定义滚动折线图");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.onback_white);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        initArrayList1();
        initArrayList2();

        binding.chartview1.setValue(mXValue1, mYValue1);
        binding.chartview1.setCurrentSelectPoint(mXValue1.size());
        binding.chartview1.setOnSelectedActionClick((position, num, text) -> {
            ToastUtils.showShort("position : " + position + "   num : " + num + "   text : " + text);
        });

        binding.chartview2.setValue(mXValue2, mYValue2);
        binding.chartview2.setCurrentSelectPoint(1);
        binding.chartview2.setOnSelectedActionClick((position, num, text) -> {
            ToastUtils.showShort("position : " + position + "   num : " + num + "   text : " + text);
        });
    }

    private void initArrayList1(){
        mXValue1.clear();
        mYValue1.clear();
        mXValue1.add(new ChartReportView.XValue(10, "10"));
        mXValue1.add(new ChartReportView.XValue(55, "55"));
        mXValue1.add(new ChartReportView.XValue(5, "5"));
        mXValue1.add(new ChartReportView.XValue(60, "60"));
        mXValue1.add(new ChartReportView.XValue(46, "46"));
        mXValue1.add(new ChartReportView.XValue(100, "100"));
        mXValue1.add(new ChartReportView.XValue(23, "23"));
        mXValue1.add(new ChartReportView.XValue(50, "50"));
        mXValue1.add(new ChartReportView.XValue(0, "0"));
        mXValue1.add(new ChartReportView.XValue(65, "65"));
        mXValue1.add(new ChartReportView.XValue(55, "55"));
        mXValue1.add(new ChartReportView.XValue(10, "10"));
        mXValue1.add(new ChartReportView.XValue(79, "79"));
        mXValue1.add(new ChartReportView.XValue(70, "70"));
        mXValue1.add(new ChartReportView.XValue(100, "100"));
        mXValue1.add(new ChartReportView.XValue(88, "88"));
        mXValue1.add(new ChartReportView.XValue(99, "99"));
        mXValue1.add(new ChartReportView.XValue(40, "40"));
        mXValue1.add(new ChartReportView.XValue(60, "60"));
        mXValue1.add(new ChartReportView.XValue(20, "20"));
        mXValue1.add(new ChartReportView.XValue(90, "90"));

        mYValue1.add(new ChartReportView.YValue(0, "0"));
        mYValue1.add(new ChartReportView.YValue(2, "20"));
        mYValue1.add(new ChartReportView.YValue(4, "40"));
        mYValue1.add(new ChartReportView.YValue(6, "60"));
        mYValue1.add(new ChartReportView.YValue(8, "80"));
        mYValue1.add(new ChartReportView.YValue(10, "100"));
    }

    private void initArrayList2(){
        mXValue2.clear();
        mYValue2.clear();
        mXValue2.add(new ChartReportView.XValue(450, "450"));
        mXValue2.add(new ChartReportView.XValue(0, "0"));
        mXValue2.add(new ChartReportView.XValue(888, "888"));
        mXValue2.add(new ChartReportView.XValue(650, "650"));
        mXValue2.add(new ChartReportView.XValue(1000, "1000"));
        mXValue2.add(new ChartReportView.XValue(310, "310"));

        mYValue2.add(new ChartReportView.YValue(0, "0"));
        mYValue2.add(new ChartReportView.YValue(1, "100"));
        mYValue2.add(new ChartReportView.YValue(2, "200"));
        mYValue2.add(new ChartReportView.YValue(3, "300"));
        mYValue2.add(new ChartReportView.YValue(4, "400"));
        mYValue2.add(new ChartReportView.YValue(5, "500"));
        mYValue2.add(new ChartReportView.YValue(6, "600"));
        mYValue2.add(new ChartReportView.YValue(7, "700"));
        mYValue2.add(new ChartReportView.YValue(8, "800"));
        mYValue2.add(new ChartReportView.YValue(9, "900"));
        mYValue2.add(new ChartReportView.YValue(10, "1000"));
    }
}