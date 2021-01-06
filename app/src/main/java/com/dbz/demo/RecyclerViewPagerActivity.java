package com.dbz.demo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.dbz.demo.adapter.RecyclerViewAdapter;
import com.dbz.demo.base.BaseActivity;
import com.dbz.demo.databinding.ActivityRecyclerViewPagerBinding;
import com.dbz.demo.viewpager.CustPagerTransformer;
import com.dbz.demo.viewpager.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPagerActivity extends BaseActivity {

    private ViewPagerAdapter mPagerAdapter;
    private RecyclerViewAdapter mAdapter;
    private final List<String> mData = new ArrayList<>();
    private ActivityRecyclerViewPagerBinding binding;

    @Override
    protected View getContentView() {
        binding = ActivityRecyclerViewPagerBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initView(Bundle bundle) {
        setStatusBarColor(R.color.blue_color);
        binding.toolbar.setTitle("viewPager || RecyclerView");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.onback_white);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
        for (int i = 0; i < 6; i++) {
            mData.add("这是第" + (i + 1) + "个");
        }
        initPagerAdapter();
        initAdapter();
    }


    private void initPagerAdapter() {
        if (mPagerAdapter == null) {
            mPagerAdapter = new ViewPagerAdapter();
            // 设置页面之间的边距
            binding.viewPager.setPageMargin(dp2px(12));
            // 设置缩放 透明度
            binding.viewPager.setPageTransformer(false, new CustPagerTransformer());
        }
        //添加数据之后在设置适配器这样setPageTransformer会生效，否则两边的item没有透明的效果
        binding.viewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.addData(mData);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 滑动之后处理逻辑
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initAdapter() {
        if (mAdapter == null) {
            mAdapter = new RecyclerViewAdapter();
            binding.recyclerView.setAdapter(mAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            // 设置方向
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            binding.recyclerView.setLayoutManager(layoutManager);
            // 让item居中显示
            LinearSnapHelper snapHelper = new LinearSnapHelper();
            // 绑定到 mRecyclerView
            snapHelper.attachToRecyclerView(binding.recyclerView);
        }
        mAdapter.addData(mData);
        // 需要在添加数据时 再调用一次 不然会在滑动时才会显示效果
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final float MIN_SCALE = 0.85f;
                final float MIN_ALPHA = 0.5f;
                final float MAX_SCALE = 1.0f;
                final float MAX_ALPHA = 1.0f;
                int childCount = recyclerView.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = recyclerView.getChildAt(i);
                    int left = child.getLeft();
                    int paddingStart = recyclerView.getPaddingStart();
                    // 遍历recyclerView子项，以中间项左侧偏移量为基准进行缩放
                    float bl = Math.min(1, Math.abs(left - paddingStart) * 1f / child.getWidth());
                    float scale = MAX_SCALE - bl * (MAX_SCALE - MIN_SCALE);
                    float alpha = MAX_ALPHA - bl * (MAX_ALPHA - MIN_ALPHA);
                    child.setScaleY(scale);
                    child.setAlpha(alpha);
                }
            }
        });
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}