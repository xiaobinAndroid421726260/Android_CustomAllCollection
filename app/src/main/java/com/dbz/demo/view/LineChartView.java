package com.dbz.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


import com.dbz.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author Db_z
 * date 2020/3/18 14:49
 * @version V1.0
 */
public class LineChartView extends View {

    /**
     * 折线画笔
     */
    private Paint mLinePaint;
    /**
     * Y轴提示框和Y轴点文本画笔
     */
    private Paint mDotTextPaint;
    /**
     * X轴文本画笔
     */
    private Paint mXTextPaint;
    /**
     * XY轴画笔
     */
    private Paint mXYPaint;
    /**
     * 折线点位画笔
     */
    private Paint mDotPaint;
    /**
     * 点位文本大小
     */
    private int mDotTextSize = dpToPx(13);
    /**
     * X轴文本大小
     */
    private int mXTextSize = dpToPx(13);
    /**
     * X轴文本颜色
     */
    private int mXTextColor = Color.BLACK;
    /**
     * 背景颜色
     */
    private int mBackColor = Color.WHITE;
    /**
     * 点位文本颜色
     */
    private int mDotTextColor = Color.WHITE;
    /**
     * XY轴颜色
     */
    private int mXYLineColor = Color.parseColor("#cccccc");
    /**
     * 折线颜色
     */
    private int mLineColor = Color.RED;
    /**
     * X轴线宽度
     */
    private int mXLineWidth = dpToPx(1);
    /**
     * 选中点的大小
     */
    private final int mSelectDotSize = dpToPx(5);
    /**
     * 未选中点的大小
     */
    private final int mUnSelectDotSize = dpToPx(3);
    /**
     * 当前选中的点
     */
    private int mCurrentSelectDot = 1;
    /**
     * 线距点的距离
     */
    private int mDotSpacing = dpToPx(10);
    /**
     * X轴文本上下的间距
     */
    private final int mTopBottomSpacing = dpToPx(5);
    /**
     * Y 轴距离上边距
     */
    private final int mYTopInterval = dpToPx(40);
    /**
     * X轴直线原点
     */
    private final int mXDotOrigin = dpToPx(15);
    private int mXTextXLineSpacing;
    /**
     * X轴文本高度
     */
    private int mXTextHeight;
    private int mWidth, mHeight;
    /**
     * 整体数据最大值
     */
    private int max = 0;
    private List<XValue> mXValue = new ArrayList<>();
    private List<LineValue> mLineValue = new ArrayList<>();

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initView();
//        initArrayList();
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);
        mBackColor = typedArray.getColor(R.styleable.LineChartView_dbz_back_color, Color.WHITE);
        mDotTextColor = typedArray.getColor(R.styleable.LineChartView_dbz_dot_text_color, Color.WHITE);
        mXTextColor = typedArray.getColor(R.styleable.LineChartView_dbz_x_text_color, Color.BLACK);
        mLineColor = typedArray.getColor(R.styleable.LineChartView_dbz_line_chart_color, Color.RED);
        mXYLineColor = typedArray.getColor(R.styleable.LineChartView_dbz_x_line_color, mXYLineColor);
        mXLineWidth = (int) typedArray.getDimension(R.styleable.LineChartView_dbz_x_line_width, mXLineWidth);
        mXTextSize = (int) typedArray.getDimension(R.styleable.LineChartView_dbz_x_text_size, mXTextSize);
        mDotTextSize = (int) typedArray.getDimension(R.styleable.LineChartView_dbz_dot_text_size, mDotTextSize);
        typedArray.recycle();
    }

    private void initView() {
        mXYPaint = new Paint();
        mXYPaint.setAntiAlias(true);
        mXYPaint.setColor(mXYLineColor);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(mXLineWidth);
        mLinePaint.setColor(mLineColor);

        mDotPaint = new Paint();
        mDotPaint.setAntiAlias(true);
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setColor(Color.WHITE);

        mXTextPaint = new Paint();
        mXTextPaint.setAntiAlias(true);
        mXTextPaint.setStyle(Paint.Style.FILL);
        mXTextPaint.setColor(mXTextColor);
        mXTextPaint.setTextSize(mXTextSize);

        mDotTextPaint = new Paint();
        mDotTextPaint.setAntiAlias(true);
        mDotTextPaint.setStyle(Paint.Style.FILL);
        mDotTextPaint.setColor(mDotTextColor);
        mDotTextPaint.setTextSize(mDotTextSize);
    }

    private void initArrayList() {
        mXValue.clear();
        mLineValue.clear();
        mXValue.add(new XValue(0, "12-14"));
        mXValue.add(new XValue(1, "12-15"));
        mXValue.add(new XValue(2, "12-16"));
        mXValue.add(new XValue(3, "12-17"));
        mXValue.add(new XValue(4, "12-18"));
        mXValue.add(new XValue(5, "12-19"));
        mXValue.add(new XValue(6, "12-20"));
        mXValue.add(new XValue(7, "12-21"));
        mXValue.add(new XValue(8, "12-22"));
        mXValue.add(new XValue(9, "12-23"));
        mXValue.add(new XValue(10, "12-24"));
        mXValue.add(new XValue(11, "12-25"));
        mXValue.add(new XValue(12, "12-26"));
        mXValue.add(new XValue(13, "12-27"));
        mXValue.add(new XValue(14, "12-28"));
        mLineValue.add(new LineValue(1100, "1100"));
        mLineValue.add(new LineValue(700, "700"));
        mLineValue.add(new LineValue(2000, "2000"));
        mLineValue.add(new LineValue(500, "500"));
        mLineValue.add(new LineValue(2600, "2600"));
        mLineValue.add(new LineValue(420, "420"));
        mLineValue.add(new LineValue(320, "320"));
        mLineValue.add(new LineValue(0, "0"));
        mLineValue.add(new LineValue(600, "600"));
        mLineValue.add(new LineValue(1500, "1500"));
        mLineValue.add(new LineValue(865, "865"));
        mLineValue.add(new LineValue(500, "500"));
        mLineValue.add(new LineValue(123, "123"));
        mLineValue.add(new LineValue(1200, "1200"));
        mLineValue.add(new LineValue(0, "0"));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();
            // 把屏幕整体分成 数据的个数份 多少数据就多少份 每份的间距
            mDotSpacing = (mWidth - (mWidth - getPaddingLeft() - getPaddingRight()) / mLineValue.size()) / mLineValue.size();
            // 获取X轴数据文本最后一个的高度
            Rect rect = getTextBounds(mXValue.get(mXValue.size() - 1).value + "-", mXTextPaint);
            // 文本立着显示，宽度即是高度
            mXTextHeight = rect.width();
            // 文本距离X轴直线的高度
            mXTextXLineSpacing = mHeight - mXTextHeight - mTopBottomSpacing * 2;
            // 取数据中的最大值
            for (int i = 0; i < mLineValue.size(); i++) {
                max = Math.max(max, mLineValue.get(i).num);
            }
            if (max == 0) max = 1;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBackColor);
        drawXLine(canvas);
        drawXTextValue(canvas);
        drawDotLine(canvas);
        drawDot(canvas);
    }

    /**
     * 绘制X轴直线
     */
    private void drawXLine(Canvas canvas) {
        canvas.drawLine(mXDotOrigin, (float) mXTextXLineSpacing,
                mWidth - getPaddingRight() - mXDotOrigin, (float) mXTextXLineSpacing, mXYPaint);
    }

    /**
     * 绘制垂直点位的线 和 X轴文本
     */
    private void drawXTextValue(Canvas canvas) {
        for (int i = 0; i < mXValue.size(); i++) {
            float x = mDotSpacing * i + mDotSpacing;
            if (x >= mDotSpacing) {
                // 如果是选中的 绘制垂直线
                if (i == mCurrentSelectDot - 1) {
                    canvas.drawLine(x, (float) mXTextXLineSpacing, x, (float) mXValue.get(i).num, mXYPaint);
                }
                //绘制X轴文本
                Rect rect = getTextBounds(mXValue.get(i).value, mXTextPaint);
                mXTextHeight = rect.width();
                float xV = x - (float) rect.width() / 2 + mTopBottomSpacing;
                float yV = mHeight - mXTextHeight + mXLineWidth;
                // 画布旋转80度
                canvas.rotate(-80, xV, yV);
                canvas.drawText(mXValue.get(i).value, xV - (mXTextHeight - (float) mXTextHeight / 3), yV + (mXTextHeight - (float) mXTextHeight / 2), mXTextPaint);
                canvas.rotate(80, xV, yV);
            }
        }
    }

    /**
     * 绘制点折线
     */
    private void drawDotLine(Canvas canvas) {
        if (mLineValue.size() == 0) return;
        mLinePaint.setStyle(Paint.Style.STROKE);
        // 绘制区域 = 总高度 - 下边距 - 上边距
        float totalHeight = mHeight - getPaddingBottom() - (mHeight - mXTextXLineSpacing) - getPaddingTop() - mYTopInterval;
        Path path = new Path();
        float x = mDotSpacing;
        // y轴上到下是数字变大 所以需要反着绘制  绘制区域 - 百分比高度(百分比高度 = 数值 * 总高度 / 数据最大值)， 相反过来就是从下到上的比例高度
        float y = (mHeight - getPaddingBottom() - (mHeight - mXTextXLineSpacing)) - (float) mLineValue.get(0).num * totalHeight / max;
        path.moveTo(x, y);
        for (int i = 0; i < mLineValue.size(); i++) {
            x = mDotSpacing * i + mDotSpacing;
            // y轴上到下是数字变大 所以需要反着绘制  绘制区域 - 百分比高度(百分比高度 = 数值 * 总高度 / 数据最大值)， 相反过来就是从下到上的比例高度
            y = (mHeight - getPaddingBottom() - (mHeight - mXTextXLineSpacing)) - (float) mLineValue.get(i).num * totalHeight / max;
            path.lineTo(x, y);
        }
        canvas.drawPath(path, mLinePaint);
    }

    /**
     * 绘制点位置
     */
    private void drawDot(Canvas canvas) {
        float x;
        float y;
        // 绘制区域 = 总高度 - 下边距 - 上边距
        float totalHeight = mHeight - getPaddingBottom() - (mHeight - mXTextXLineSpacing) - getPaddingTop() - mYTopInterval;
        for (int i = 0; i < mLineValue.size(); i++) {
            x = mDotSpacing * i + mDotSpacing;
            // y轴上到下是数字变大 所以需要反着绘制  绘制区域 - 百分比高度(百分比高度 = 数值 * 总高度 / 数据最大值)， 相反过来就是从下到上的比例高度
            y = (mHeight - getPaddingBottom() - (mHeight - mXTextXLineSpacing)) - (float) mLineValue.get(i).num * totalHeight / max;
            drawCircle(canvas, x, y, mUnSelectDotSize);
            if (i == mCurrentSelectDot - 1) {
                drawCircle(canvas, x, y, mSelectDotSize);
                drawTextBox(canvas, x, y - dpToPx(8), mLineValue.get(i).value);
            }
        }
    }

    /**
     * 绘制点
     */
    private void drawCircle(Canvas canvas, float x, float y, int dp) {
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, dp, mDotPaint);
        mDotPaint.setStyle(Paint.Style.STROKE);
        mDotPaint.setColor(mLineColor);
        mDotPaint.setStrokeWidth(mXLineWidth);
        canvas.drawCircle(x, y, dp, mDotPaint);
    }

    /**
     * 绘制提示框
     */
    private void drawTextBox(Canvas canvas, float x, float y, String text) {
        Rect rect = getTextBounds(text, mDotTextPaint);
        int dp5 = dpToPx(5);
        int dp6 = dpToPx(6);
        int dp20 = dpToPx(20);
        // 绘制路径三角
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x - dp6, y - dp6);
//        path.lineTo(x - dp20, y - dp6);
//        path.lineTo(x - dp20, y - dp6 - dp20);
//        path.lineTo(x + dp20, y - dp6 - dp20);
//        path.lineTo(x + dp20, y - dp6);
//        path.quadTo(x + dp18, y - dp4, x - dp18, y - dp4);
        path.lineTo(x + dp6, y - dp6);
        path.lineTo(x, y);
        path.close();
        mDotTextPaint.setStyle(Paint.Style.FILL);
        mDotTextPaint.setColor(Color.parseColor("#6f6f6f"));
        canvas.drawPath(path, mDotTextPaint);
        // 绘制矩形， 周边圆角
        RectF rectF = new RectF(x - dp20, y - dp5, x + dp20, y - dp20 - dp6);
        canvas.drawRoundRect(rectF, dp5, dp5, mDotTextPaint);
        mDotTextPaint.setColor(Color.WHITE);
        mDotTextPaint.setTextSize(mDotTextSize);
        // y点计算  以下两种方法均可
        // x减去文本的宽度  y - 提示框距离点的高度 - 三角的高度 - 提示框 / 2
//        canvas.drawText(text, x - (float) rect.width() / 2, y - dpToPx(10) - dp6 - dpToPx(5) - rectF.height() / 2, mDotTextPaint);
        // x减去文本的宽度  y - 三角的高度 - 文本高度 / 2
        canvas.drawText(text, 0, text.length(), x - (float) rect.width() / 2, y - dp6 - (float) rect.height() / 2, mDotTextPaint);
    }

    /**
     * 点击点位绘制
     */
    private void clickAction(MotionEvent event) {
        int dp8 = dpToPx(8);
        float eventX = event.getX();
        float eventY = event.getY();
        // 绘制区域 = 总高度 - 下边距 - 上边距
        float totalHeight = mHeight - getPaddingBottom() - (mHeight - mXTextXLineSpacing) - getPaddingTop() - mYTopInterval;
        for (int i = 0; i < mLineValue.size(); i++) {
            float x = mDotSpacing * i + mDotSpacing;
            float y = (mHeight - getPaddingBottom() - (mHeight - mXTextXLineSpacing)) - (float) mLineValue.get(i).num * totalHeight / max;
            if (eventX >= x - dp8 && eventX <= x + dp8 && eventY >= y - dp8 && eventY <= y + dp8 && mCurrentSelectDot != i + 1) {
                mCurrentSelectDot = i + 1;
                invalidate();
                return;
            }
            Rect rect = getTextBounds(mXValue.get(i).value, mXTextPaint);
            y = mXTextXLineSpacing - mXLineWidth + rect.height();
            if (eventX >= x - (float) rect.height() / 2 - dp8 && eventX <= x + rect.height() + (float) dp8 / 2 &&
                    eventY >= y - dp8 && eventY <= y + rect.width() + dp8 && mCurrentSelectDot != i + 1){
                mCurrentSelectDot = i + 1;
                invalidate();
                return;
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                clickAction(event);
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }

    public void setXYLineValue(List<XValue> mXValue, List<LineValue> mLineValue) {
        this.mXValue = mXValue;
        this.mLineValue = mLineValue;
        invalidate();
    }

    /**
     * 获取丈量文本的矩形
     *
     * @param text 文本
     * @param paint 画笔
     */
    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
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

    public static class XValue {
        private final int num;
        private final String value;

        public XValue(int num, String value) {
            this.num = num;
            this.value = value;
        }
    }

    public static class LineValue {
        private final int num;
        private final String value;

        public LineValue(int num, String value) {
            this.num = num;
            this.value = value;
        }
    }
}