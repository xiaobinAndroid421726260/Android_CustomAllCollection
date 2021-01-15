package com.dbz.demo.viewmodel;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.dbz.demo.R;
import com.dbz.demo.base.BaseActivity;
import com.dbz.demo.databinding.ActivityCustomChartBinding;
import com.dbz.demo.viewmodel.CustomChartViewModel;

public class CustomChartActivity extends BaseActivity<CustomChartViewModel> {

    private ActivityCustomChartBinding binding;

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
        mViewModel.getCacheDataAndLoad();
        mViewModel.getBaseCustomModel().observe(this, baseCustomModel -> {
            binding.lineChart1.setXYLineValue(baseCustomModel.getModelList1(), baseCustomModel.getLineValues1());
            binding.lineChart2.setXYLineValue(baseCustomModel.getModelList2(),baseCustomModel.getLineValues2());
        });
    }

    @Override
    protected CustomChartViewModel obtainViewModel() {
        return new ViewModelProvider(this, CustomChartViewModel.createViewModelFactory(
                new CustomChartViewModel(getApplication()))).get(CustomChartViewModel.class);
    }
}