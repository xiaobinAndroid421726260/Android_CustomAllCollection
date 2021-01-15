package com.dbz.demo.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dbz.demo.base.viewmodel.BaseViewModel;
import com.gyf.immersionbar.ImmersionBar;

import me.jessyan.autosize.AutoSizeCompat;
import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity implements CustomAdapt {

    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mViewModel = obtainViewModel();
        if (null != mViewModel){
            mViewModel.register();
        }
        initView(savedInstanceState);
    }

    protected abstract View getContentView();

    protected abstract void initView(Bundle bundle);

    protected abstract VM obtainViewModel();

    /**
     * 设置状态栏颜色
     *
     * @param color 状态栏颜色 默认状态栏字体颜色白
     */
    public void setStatusBarColor(int color) {
        setStatusBarColor(true, color, false);
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor(boolean fits, int color, boolean barDarkFont) {
        ImmersionBar.with(this)
                .statusBarDarkFont(barDarkFont)
                .fitsSystemWindows(fits)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(color)
                .init();
    }


    @Override
    public Resources getResources() {
        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources());
        return super.getResources();
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mViewModel){
            mViewModel.unRegister();
        }
    }
}
