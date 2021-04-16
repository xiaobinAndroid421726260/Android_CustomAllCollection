package com.dbz.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dbz.demo.base.BaseActivity;
import com.dbz.demo.base.viewmodel.BaseViewModel;
import com.dbz.demo.databinding.ActivityStockPriceBinding;
import com.dbz.demo.view.StockPriceView;

import java.util.ArrayList;
import java.util.List;

public class StockPriceActivity extends BaseActivity {

    private ActivityStockPriceBinding binding;
    private final List<StockPriceView.XValue> mXValue1 = new ArrayList<>();
    private final List<StockPriceView.YValue> mYValue1 = new ArrayList<>();

    @Override
    protected View getContentView() {
        binding = ActivityStockPriceBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView(Bundle bundle) {
        setStatusBarColor(R.color.red);
        binding.toolbar.setTitle("Android自定义折线阴影");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.onback_white);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        initArrayList1();
        binding.stockPrice.setValue(mXValue1, mYValue1);
        binding.stockPrice.setCurrentSelectPoint(mXValue1.size());
        binding.stockPrice.setOnSelectedActionClick((position, num, text) -> {
            LogUtils.e("---position : " + position + "   num : " + num + "   text : " + text);
            ToastUtils.showShort("position : " + position + "   num : " + num + "   text : " + text);
        });
    }

    @Override
    protected BaseViewModel obtainViewModel() {
        return null;
    }

    private void initArrayList1() {
        mXValue1.clear();
        mYValue1.clear();
        mXValue1.add(new StockPriceView.XValue("03.01", "13.00"));
        mXValue1.add(new StockPriceView.XValue("03.02", "13.00"));
        mXValue1.add(new StockPriceView.XValue("03.03", "16.00"));
        mXValue1.add(new StockPriceView.XValue("03.04", "23.00"));
        mXValue1.add(new StockPriceView.XValue("03.05", "23.00"));
        mXValue1.add(new StockPriceView.XValue("03.06", "20.00"));
        mXValue1.add(new StockPriceView.XValue("03.07", "13.00"));
        mXValue1.add(new StockPriceView.XValue("03.08", "16.00"));
        mXValue1.add(new StockPriceView.XValue("03.09", "15.00"));
        mXValue1.add(new StockPriceView.XValue("03.10", "19.00"));
        mXValue1.add(new StockPriceView.XValue("03.11", "16.00"));
        mXValue1.add(new StockPriceView.XValue("03.12", "12.00"));
        mXValue1.add(new StockPriceView.XValue("03.13", "10.00"));
        mXValue1.add(new StockPriceView.XValue("03.14", "20.00"));
        mXValue1.add(new StockPriceView.XValue("03.15", "13.00"));
        mXValue1.add(new StockPriceView.XValue("03.16", "25.00"));
        mXValue1.add(new StockPriceView.XValue("03.17", "19.00"));
        mXValue1.add(new StockPriceView.XValue("03.18", "22.00"));
        mXValue1.add(new StockPriceView.XValue("03.19", "23.00"));
        mXValue1.add(new StockPriceView.XValue("03.20", "23.00"));
        mXValue1.add(new StockPriceView.XValue("03.21", "12.00"));
        mXValue1.add(new StockPriceView.XValue("03.22", "16.00"));
        mXValue1.add(new StockPriceView.XValue("03.23", "13.00"));
        mXValue1.add(new StockPriceView.XValue("03.24", "10.00"));
        mXValue1.add(new StockPriceView.XValue("03.25", "12.00"));
        mXValue1.add(new StockPriceView.XValue("03.26", "20.00"));
        mXValue1.add(new StockPriceView.XValue("03.27", "16.00"));
        mXValue1.add(new StockPriceView.XValue("03.28", "12.00"));
        mXValue1.add(new StockPriceView.XValue("03.29", "13.00"));
        mXValue1.add(new StockPriceView.XValue("03.30", "15.00"));

        mYValue1.add(new StockPriceView.YValue(0, "0.00"));
        mYValue1.add(new StockPriceView.YValue(1, "5.00"));
        mYValue1.add(new StockPriceView.YValue(2, "15.00"));
        mYValue1.add(new StockPriceView.YValue(3, "20.00"));
        mYValue1.add(new StockPriceView.YValue(4, "25.00"));
    }
}