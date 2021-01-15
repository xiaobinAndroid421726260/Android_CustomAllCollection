package com.dbz.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.dbz.demo.base.BaseActivity;
import com.dbz.demo.base.viewmodel.BaseViewModel;
import com.dbz.demo.databinding.ActivityRingChartBinding;

import java.util.ArrayList;
import java.util.List;

public class RingChartActivity extends BaseActivity {

    private ActivityRingChartBinding binding;

    private final ArrayList<Integer> mCircleColor = new ArrayList<>();
    private final List<Float> mCircleData = new ArrayList<>();

    private final ArrayList<Integer> mCircleColor2 = new ArrayList<>();
    private final List<Float> mCircleData2 = new ArrayList<>();

    private final ArrayList<Integer> mCircleColor3 = new ArrayList<>();
    private final List<Float> mCircleData3 = new ArrayList<>();

    @Override
    protected View getContentView() {
        binding = ActivityRingChartBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView(Bundle bundle) {
        setStatusBarColor(R.color.orange);
        binding.toolbar.setTitle("Android自定义环形图");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.onback_white);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        initArrayList1();
        initArrayList2();
        initArrayList3();
    }

    @Override
    protected BaseViewModel obtainViewModel() {
        return null;
    }

    private void initArrayList1(){
        mCircleColor.clear();
        mCircleData.clear();
        mCircleColor.add(getResources().getColor(R.color.orange));
        mCircleColor.add(getResources().getColor(R.color.blue_color));
        mCircleColor.add(getResources().getColor(R.color.red));

        mCircleData.add(180f);
        mCircleData.add(60f);
        mCircleData.add(120f);
        binding.ringChartView.setValueData(mCircleData, mCircleColor, true, true, false);
    }

    private void initArrayList2(){
        mCircleColor2.clear();
        mCircleData2.clear();
        mCircleColor2.add(getResources().getColor(R.color.orange));
        mCircleColor2.add(getResources().getColor(R.color.blue_color));
        mCircleColor2.add(getResources().getColor(R.color.red));
        mCircleColor2.add(getResources().getColor(R.color.yellow));
        mCircleColor2.add(getResources().getColor(R.color.green_color));

        mCircleData2.add(150f);
        mCircleData2.add(10f);
        mCircleData2.add(90f);
        mCircleData2.add(60f);
        mCircleData2.add(30f);
        binding.ringChartView2.setValueData(mCircleData2, mCircleColor2, true, true, true);
    }

    private void initArrayList3(){
        mCircleColor3.clear();
        mCircleData3.clear();
        mCircleColor3.add(getResources().getColor(R.color.orange));
        mCircleColor3.add(getResources().getColor(R.color.blue_color));
        mCircleColor3.add(getResources().getColor(R.color.red));

        mCircleData3.add(120f);
        mCircleData3.add(120f);
        mCircleData3.add(120f);
        binding.ringChartView3.setValueData(mCircleData3, mCircleColor3, true);

        binding.ringChartView4.setValueData(mCircleData3, mCircleColor3, false, true, true);
    }
}