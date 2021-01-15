package com.dbz.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.dbz.demo.base.BaseActivity;
import com.dbz.demo.base.viewmodel.BaseViewModel;
import com.dbz.demo.databinding.ActivityProgressBarBinding;

public class ProgressBarActivity extends BaseActivity {

    private ActivityProgressBarBinding binding;

    @Override
    protected View getContentView() {
        binding = ActivityProgressBarBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView(Bundle bundle) {
        setStatusBarColor(R.color.orange);
        binding.toolbar.setTitle("Android自定义进度条");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.onback_white);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            binding.dynamicProgress.reset();
            binding.dynamicProgress.setRevisionProgress(230, 888f);

            binding.dynamicProgress1.reset();
            binding.dynamicProgress1.setRevisionProgress(6300, 8520f);

            binding.dynamicProgress2.reset();
            binding.dynamicProgress2.setRevisionProgress(50, 100f);
        }, 1200);


        binding.progressBar.setProgressMax(100)
                .setProgressTextColor(Color.WHITE)
                .setShowProgressText(true)
                .setRadius(2)
                .setProgress(50);

        binding.progressBar2.setProgressMax(330)
                .setProgressColor(getResources().getColor(R.color.orange))
                .setProgressTextColor(Color.WHITE)
                .setShowProgressText(true)
                .setShowRate(true)
                .setRadius(5)
                .setProgress(150);

        binding.progressBar3.setProgressMax(100)
                .setProgressBackColor(getResources().getColor(R.color.color_bg))
                .setProgressColor(getResources().getColor(R.color.green_color))
                .setProgressTextColor(Color.WHITE)
                .setShowProgressText(true)
                .setRadius(10)
                .setProgress(80);

        binding.progressBar4.setProgressMax(100)
                .setProgressBackColor(getResources().getColor(R.color.color_bg))
                .setProgressColor(getResources().getColor(R.color.yellow))
                .setShowProgressText(false)
                .setRadius(10)
                .setProgress(70);

        binding.progressBar5.setProgressMax(1000)
                .setProgressBackColor(getResources().getColor(R.color.color_bg))
                .setProgressColor(getResources().getColor(R.color.orange))
                .setShowProgressText(false)
                .setRadius(20)
                .setProgress(500);

        binding.progressBar6.setProgressMax(1000)
                .setProgressBackColor(getResources().getColor(R.color.color_bg))
                .setProgressColor(getResources().getColor(R.color.green_color))
                .setShowProgressText(true)
                .setProgressTextColor(getResources().getColor(R.color.red))
                .setRadius(30)
                .setProgress(500);
    }

    @Override
    protected BaseViewModel obtainViewModel() {
        return null;
    }
}