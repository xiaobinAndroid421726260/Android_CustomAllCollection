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
        new Handler(getMainLooper()).postDelayed(() -> {
            initArrayList1();
            binding.stockPrice.setValue(mXValue1, mYValue1);
            binding.stockPrice.setCurrentSelectPoint(mXValue1.size());
            binding.stockPrice.setOnSelectedActionClick((position, num, text) -> {
                LogUtils.e("---position : " + position + "   num : " + num + "   text : " + text);
                ToastUtils.showShort("position : " + position + "   num : " + num + "   text : " + text);
            });
        }, 500);
    }

    @Override
    protected BaseViewModel obtainViewModel() {
        return null;
    }

    private void initArrayList1(){
        mXValue1.clear();
        mYValue1.clear();
        mXValue1.add(new StockPriceView.XValue("03.01", "3.00"));
        mXValue1.add(new StockPriceView.XValue("03.02", "3.00"));
        mXValue1.add(new StockPriceView.XValue("03.03", "1.00"));
        mXValue1.add(new StockPriceView.XValue("03.04", "3.00"));
        mXValue1.add(new StockPriceView.XValue("03.05", "3.00"));
        mXValue1.add(new StockPriceView.XValue("03.06", "3.00"));
        mXValue1.add(new StockPriceView.XValue("03.07", "1.00"));
        mXValue1.add(new StockPriceView.XValue("03.08", "3.00"));
        mXValue1.add(new StockPriceView.XValue("03.09", "3.00"));
        mXValue1.add(new StockPriceView.XValue("03.10", "2.00"));
        mXValue1.add(new StockPriceView.XValue("03.11", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.12", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.13", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.14", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.15", "3.00"));
        mXValue1.add(new StockPriceView.XValue("03.16", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.17", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.18", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.19", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.20", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.21", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.22", "2.00"));
        mXValue1.add(new StockPriceView.XValue("03.23", "1.00"));
        mXValue1.add(new StockPriceView.XValue("03.24", "1.00"));
        mXValue1.add(new StockPriceView.XValue("03.25", "1.00"));
        mXValue1.add(new StockPriceView.XValue("03.26", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.27", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.28", "0.00"));
        mXValue1.add(new StockPriceView.XValue("03.29", "1.00"));
        mXValue1.add(new StockPriceView.XValue("03.30", "0.00"));

        mYValue1.add(new StockPriceView.YValue(0, "0.00"));
        mYValue1.add(new StockPriceView.YValue(2, "0.75"));
        mYValue1.add(new StockPriceView.YValue(4, "1.50"));
        mYValue1.add(new StockPriceView.YValue(6, "2.25"));
        mYValue1.add(new StockPriceView.YValue(8, "3.00"));
    }
}