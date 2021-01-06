package com.dbz.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ScreenUtils;
import com.dbz.demo.R;


public class DynamicProgressBar extends LinearLayout {

    private static final int duration = 5;                        // 间隔时间
    private static final int speedWidth = 2;                      // 运行宽度变化减多少值
    private static final int UPDATE_UI = 0x1;                     // 更新UI
    private final Context context;
    private RelativeLayout.LayoutParams layoutParams;
    private LinearLayout llProgressbar;
    private TextView textView;
    private ImageView viewDivider;
    private int totalWidth, startX;
    private float current;

    public DynamicProgressBar(Context context) {
        this(context, null);
    }

    public DynamicProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dynamic_progress, this, false);
        textView = view.findViewById(R.id.tv_num);
        llProgressbar = view.findViewById(R.id.ll_progressbar);
        viewDivider = view.findViewById(R.id.view_divider);
        ImageView imageView = view.findViewById(R.id.iv_revision);
        imageView.setBackgroundResource(R.drawable.progress_revision);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        imageView.post(animationDrawable::start);
        // 总宽度 = 屏幕宽度 - 距离两边的宽度
        totalWidth = ScreenUtils.getScreenWidth() - dpToPx(100);
        layoutParams = (RelativeLayout.LayoutParams) viewDivider.getLayoutParams();
        addView(view);
    }

    /**
     * 设置进度
     *
     * @param startX   平移开始值
     * @param endPoint 平移结束值
     */
    private void setProgress(float startX, float endPoint) {
        final int totalWidth = ScreenUtils.getScreenWidth() - dpToPx(100);
        final float endX = totalWidth - endPoint;
        TranslateAnimation animation = new TranslateAnimation(startX, endX, 0, 0);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        llProgressbar.startAnimation(animation);
    }

    /**
     * 设置数据
     * @param progress 当前
     * @param totalProgress 总宽度
     */
    public void setRevisionProgress(int progress, float totalProgress) {
        if (totalProgress == 0) return;
        current = progress * totalWidth / totalProgress;
        current = totalWidth - current;
        textView.setText(String.valueOf(progress));
        ProgressThread mProgressThread = new ProgressThread();
        mProgressThread.start();

    }


    /**
     * 重置进度
     */
    public void reset() {
        startX = 0;         // 重置X轴起始位置
        totalWidth = ScreenUtils.getScreenWidth() - dpToPx(100);
        // 重新设置遮挡位置的宽度
        layoutParams.width = totalWidth;
        viewDivider.setLayoutParams(layoutParams);
        // 移动到起始位置 0
        setProgress(0, totalWidth);
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_UI) {
                Bundle bundle = msg.getData();
                final float endPoint = bundle.getFloat("width");
                final float startX = bundle.getFloat("startX");
                layoutParams.width = (int) endPoint;
                viewDivider.setLayoutParams(layoutParams);
                setProgress(startX, endPoint);
            }
        }
    };


    private class ProgressThread extends Thread {

        private boolean isStop = true;

        @Override
        public void run() {
            super.run();
            while (isStop) {
                float width = totalWidth -= speedWidth;
                if (width <= current) {
                    width = current;
                    isStop = false;
                }
                startX += speedWidth;
                Bundle bundle = new Bundle();
                bundle.putFloat("width", width);
                bundle.putFloat("startX", startX);
                Message message = mHandler.obtainMessage(UPDATE_UI);
                message.setData(bundle);
                mHandler.sendMessage(message);
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * dp转化成为px
     *
     * @param dp 单位
     */
    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }
}