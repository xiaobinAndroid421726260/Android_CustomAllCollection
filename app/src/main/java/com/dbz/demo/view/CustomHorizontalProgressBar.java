package com.dbz.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.dbz.demo.R;

import java.text.DecimalFormat;


public class CustomHorizontalProgressBar extends View {

    private final Context mContext;
    /**
     * 进度条的圆角弧度
     */
    private float mRadius = dpToPx(5);

    /**
     * 进度条的背景色
     */
    private int mProgressBackColor;

    /**
     * 进度条颜色
     */
    private int mProgressColor = Color.GREEN;

    /**
     * 进度条的文本色
     */
    private int mProgressTextColor = Color.WHITE;

    /**
     * 是否显示文本 (默认不显示)
     */
    private boolean isShowProgressText = false;

    /**
     * 是否显示文本百分比 (默认显示)
     */
    private boolean isShowRate = true;
    /**
     * 进度条的最大进度
     */
    private int mProgressMax = 100;

    /**
     * 进度条的当前进度
     */
    private int mProgress = 50;

    /**
     * 画笔
     */
    private Paint mPaint;


    public CustomHorizontalProgressBar(Context context) {
        this(context, null);
    }

    public CustomHorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHorizontalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context, attrs, defStyleAttr);
        initPaint();
    }

    @SuppressLint("NonConstantResourceId")
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        /*获取自定义参数的颜色值*/
        @SuppressLint("CustomViewStyleable")
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomHorizontalProgressBar, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.CustomHorizontalProgressBar_dbz_radius:
                    mRadius = array.getDimension(attr, mRadius);
                    break;
                case R.styleable.CustomHorizontalProgressBar_dbz_progress_bar_back_color:
                    mProgressBackColor = array.getColor(attr, Color.parseColor("#E6E6E6"));
                    break;
                case R.styleable.CustomHorizontalProgressBar_dbz_progress_color:
                    mProgressColor = array.getColor(attr, mProgressColor);
                    break;
                case R.styleable.CustomHorizontalProgressBar_dbz_progress_bar_text_color:
                    mProgressTextColor = array.getColor(attr, mProgressTextColor);
                    break;
                case R.styleable.CustomHorizontalProgressBar_dbz_progress_max:
                    mProgressMax = array.getInteger(attr, mProgressMax);
                    break;
                case R.styleable.CustomHorizontalProgressBar_dbz_progress:
                    mProgress = array.getInteger(attr, mProgress);
                    break;
                case R.styleable.CustomHorizontalProgressBar_dbz_is_show_progress_text:
                    isShowProgressText = array.getBoolean(attr, true);
                    break;
            }
        }
        array.recycle();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRoundRect(canvas);
        drawProgress(canvas);
        drawText(canvas);
    }

    /**
     * 画进度条的背景颜色
     */
    private void drawRoundRect(Canvas canvas) {
        mPaint.setColor(mProgressBackColor);
        RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
    }

    /**
     * 画进度条进度
     */
    private void drawProgress(Canvas canvas) {
        mPaint.setColor(mProgressColor);
        RectF rectF = new RectF(0, 0, (float) getMeasuredWidth() * mProgress / mProgressMax, getMeasuredHeight());
        canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
    }

    /**
     * 画进度条文本
     */
    private void drawText(Canvas canvas) {
        if (isShowProgressText) {
            mPaint.setColor(mProgressTextColor);
            mPaint.setTextSize(this.getMeasuredHeight() / 1.2f);
            String text = mProgress + "%";
            float x = (float) this.getMeasuredWidth() * mProgress / mProgressMax - mPaint.measureText(text) - 10;
            float y = (float) this.getMeasuredHeight() / 2f - mPaint.getFontMetrics().ascent / 2f - mPaint.getFontMetrics().descent / 2f;
            // 显示百分比
            if (isShowRate) {
                text = getPercent(mProgress, mProgressMax) + "%";
            }
            if (mProgress > 10) {//解决百分比显示不全问题
                canvas.drawText(text, x, y, mPaint);
            } else {
                canvas.drawText(text, text.length() + 10, y, mPaint);
            }
        }
    }

    /**
     * 返回当前百分比
     */
    private String getPercent(int progress, int max) {
        DecimalFormat decimalFormat = new DecimalFormat("#");
        return decimalFormat.format(progress * 100 / max);
    }

    /**
     * 设置当前进度
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        if (mProgress > mProgressMax) {
            mProgress = mProgressMax;
        }
        if (mProgress < 0) {
            mProgress = 0;
        }
        invalidate();
    }

    /**
     * 设置圆角
     */
    public CustomHorizontalProgressBar setRadius(int radius) {
        this.mRadius = dpToPx(radius);
        return this;
    }

    /**
     * 是否显示文本 默认不显示
     */
    public CustomHorizontalProgressBar setShowProgressText(boolean showProgressText) {
        this.isShowProgressText = showProgressText;
        return this;
    }

    /**
     * 是否显示文本百分比 默认显示
     */
    public CustomHorizontalProgressBar setShowRate(boolean isShowRate) {
        this.isShowRate = isShowRate;
        return this;
    }

    /**
     * 设置字体颜色
     */
    public CustomHorizontalProgressBar setProgressTextColor(int progressTextColor) {
        this.mProgressTextColor = progressTextColor;
        return this;
    }

    /**
     * 设置进度背景颜色
     */
    public CustomHorizontalProgressBar setProgressBackColor(int progressBackColor) {
        this.mProgressBackColor = progressBackColor;
        return this;
    }

    /**
     * 设置进度颜色
     */
    public CustomHorizontalProgressBar setProgressColor(int progressColor) {
        this.mProgressColor = progressColor;
        return this;
    }

    /**
     * 设置进度最大值
     */
    public CustomHorizontalProgressBar setProgressMax(int progressMax) {
        this.mProgressMax = progressMax;
        return this;
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