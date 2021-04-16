package com.dbz.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.dbz.demo.base.BaseActivity;
import com.dbz.demo.base.viewmodel.BaseViewModel;
import com.dbz.demo.databinding.ActivityMainBinding;
import com.dbz.demo.viewmodel.CustomChartActivity;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected View getContentView() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView(Bundle bundle) {
        setStatusBarColor(R.color.blue_color);
        binding.toolbar.setTitle("Android");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.btnCustomGallery.setOnClickListener(v -> ActivityUtils.startActivity(RecyclerViewPagerActivity.class));
        binding.btnProgress.setOnClickListener(v -> ActivityUtils.startActivity(ProgressBarActivity.class));
        binding.btnCustomChart.setOnClickListener(v -> ActivityUtils.startActivity(CustomChartActivity.class));
        binding.btnCustomScrollChart.setOnClickListener(v -> ActivityUtils.startActivity(ChartReportActivity.class));
        binding.btnCustomStockPrice.setOnClickListener(v -> ActivityUtils.startActivity(StockPriceActivity.class));
        binding.btnRingChart.setOnClickListener(v -> ActivityUtils.startActivity(RingChartActivity.class));
    }

    @Override
    protected BaseViewModel obtainViewModel() {
        return null;
    }
}