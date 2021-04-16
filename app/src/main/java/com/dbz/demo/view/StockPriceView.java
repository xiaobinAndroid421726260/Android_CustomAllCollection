package com.dbz.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.dbz.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author Db_z
 * date 2021/4/15 14:20
 * @version V1.0
 */
public class StockPriceView extends View {

    /**
     * X轴画笔
     */
    private Paint mXPaint;
    /**
     * X轴文本
     */
    private Paint mXTextPaint;
    /**
     * Y轴文本
     */
    private Paint mYTextPaint;
    /**
     * 背景虚线画笔
     */
    private Paint mDottedPaint;
    /**
     * 折线画笔
     */
    private Paint mLinePaint;
    /**
     * 点位画笔
     */
    private Paint mPointPaint;
    /**
     * 点位直线画笔
     */
    private Paint mLinePointPaint;
    /**
     * 背景颜色
     */
    private int mBackgroundColor = Color.WHITE;
    /**
     * X、Y轴颜色
     */
    private int mXYColor = Color.parseColor("#D8D8D8");
    /**
     * X轴文本颜色
     */
    private int mXTextColor = Color.parseColor("#333333");
    /**
     * Y轴文本颜色
     */
    private int mYTextColor = Color.parseColor("#999999");
    /**
     * 折线阴影颜色
     */
    private int mLineShadowColor = Color.parseColor("#73FFE4E4");
    /**
     * 点位文本颜色
     */
    private int mPointTextSize = Color.parseColor("#333333");
    /**
     * 点位颜色
     */
    private int mPointColor = Color.parseColor("#FFFFFF");
    /**
     * 折线颜色
     */
    private int mLineColor = Color.parseColor("#FF0000");
    /**
     * 折线点位直线颜色
     */
    private int mLinePointColor = Color.parseColor("#FF8484");
    /**
     * 折线颜色
     */
    private int mPointTextColor = Color.parseColor("#333333");
    /**
     * X轴文本大小
     */
    private int mXTextSize = dpToPx(13);
    /**
     * Y轴文本大小
     */
    private int mYTextSize = dpToPx(12);
    /**
     * 折线宽度
     */
    private int mLineWidth = dpToPx(2);
    /**
     * 两点之间的间隔
     */
    private int mInterval = dpToPx(30);
    /**
     * X轴文本距左边间距
     */
    private final int mXTextLeftInterval = dpToPx(15);
    /**
     * X轴右边间距
     */
    private final int mXRightInterval = dpToPx(25);
    /**
     * X轴距底边的距离
     */
    private final int mXBottomInterval = dpToPx(10);
    /**
     * X、 Y轴原点距左边的距离
     */
    private int mYLeftInterval = dpToPx(30);
    /**
     * 当前选中的点 默认在最后一位
     */
    private int mCurrentSelectPoint = 1;
    /**
     * X 轴 第一个坐标
     */
    private float mXFirstPoint;
    /**
     * Y轴距离上边距
     */
    private final int mYTopInterval = dpToPx(40);
    /**
     * 宽、高
     */
    private int mWidth, mHeight;
    /**
     * 整体数据最大值
     */
    private float max = 1;
    private OnSelectedActionClick onSelectedActionClick;
    private Rect yRect;

    private String mXStartText = "2021.03.12";
    private String mXEndText = "2021.04.11";

    private List<XValue> mXValue = new ArrayList<>();
    private List<YValue> mYValue = new ArrayList<>();

    public StockPriceView(Context context) {
        this(context, null);
    }

    public StockPriceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StockPriceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StockPriceView, defStyleAttr, 0);
        mBackgroundColor = typedArray.getColor(R.styleable.StockPriceView_background_color_dbz, Color.WHITE);
        mLinePointColor = typedArray.getColor(R.styleable.StockPriceView_line_point_color_dbz, mLinePointColor);
        mLineColor = typedArray.getColor(R.styleable.StockPriceView_line_color_dbz, mLineColor);
        mPointColor = typedArray.getColor(R.styleable.StockPriceView_point_color_dbz, mPointColor);
        mPointTextColor = typedArray.getColor(R.styleable.StockPriceView_point_text_color_dbz, mPointTextColor);
        mXYColor = typedArray.getColor(R.styleable.StockPriceView_x_y_color_dbz, mXYColor);
        mXTextColor = typedArray.getColor(R.styleable.StockPriceView_x_text_color_dbz, mXTextColor);
        mYTextColor = typedArray.getColor(R.styleable.StockPriceView_y_text_color_dbz, mYTextColor);
        mLineShadowColor = typedArray.getColor(R.styleable.StockPriceView_line_shadow_color_dbz, mLineShadowColor);
        mPointTextSize = (int) typedArray.getDimension(R.styleable.StockPriceView_point_text_size_dbz, mPointTextSize);
        mLineWidth = (int) typedArray.getDimension(R.styleable.StockPriceView_line_width_dbz, mLineWidth);
        mXTextSize = (int) typedArray.getDimension(R.styleable.StockPriceView_x_text_size_dbz, mXTextSize);
        mYTextSize = (int) typedArray.getDimension(R.styleable.StockPriceView_y_text_size_dbz, mYTextSize);
        typedArray.recycle();
    }

    private void initPaint() {
        mXPaint = new Paint();
        mXPaint.setAntiAlias(true);
        mXPaint.setColor(mXYColor);
        mXPaint.setStrokeWidth(dpToPx(1));

        mXTextPaint = new Paint();
        mXTextPaint.setAntiAlias(true);
        mXTextPaint.setColor(mXTextColor);
        mXTextPaint.setTextSize(mXTextSize);

        mYTextPaint = new Paint();
        mYTextPaint.setAntiAlias(true);
        mYTextPaint.setColor(mYTextColor);
        mYTextPaint.setTextSize(mYTextSize);

        mDottedPaint = new Paint();
        mDottedPaint.setAntiAlias(true);
        mDottedPaint.setColor(mXYColor);
        mDottedPaint.setStyle(Paint.Style.STROKE);
        mDottedPaint.setStrokeWidth(dpToPx(1));
        // DashPathEffect () 数组 第一个是线的宽度 第二个数据是虚线间隔，
        mDottedPaint.setPathEffect(new DashPathEffect(new float[]{dpToPx(4), dpToPx(2)}, 0));

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(mLineShadowColor);
        mLinePaint.setStrokeWidth(dpToPx(1));
        mLinePaint.setStyle(Paint.Style.FILL);

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setColor(mLineColor);
        mPointPaint.setStrokeWidth(mLineWidth);
        mPointPaint.setStyle(Paint.Style.STROKE);

        mLinePointPaint = new Paint();
        mLinePointPaint.setAntiAlias(true);
        mLinePointPaint.setColor(mLinePointColor);
        mLinePointPaint.setStrokeWidth(dpToPx(1));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();
        }
    }

    /**
     * 计算文本间距，和第一个点的位置  取数据最大值
     */
    private void calculation(){
        String text = mYValue.size() == 0 ? "00-00" : mYValue.get(mYValue.size() - 1).value;
        // 测量Y轴数据的文本宽度
        yRect = getTextBounds(text, mYTextPaint);
        // 计算X轴的左边距
        mYLeftInterval = yRect.width() + mXTextLeftInterval * 2;
        // 每个点位的间距 mXValue.size() - 2 减去2 是因为两边的点各占一个  不用间距
        mInterval = (mWidth - mYLeftInterval - mXRightInterval) / (mXValue.size() - 2);
        // 第一个X轴点的位置
        mXFirstPoint = mYLeftInterval;
        // 遍历数据最大值 如果为0那么默认为1
        for (int i = 0; i < mXValue.size(); i++) {
            max = Math.max(max, Float.parseFloat(mXValue.get(i).value));
        }
        if (max == 0) {
            max = 1;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculation();
        canvas.drawColor(mBackgroundColor);
        // 绘制X轴时间文本
        drawBottomText(canvas);
        // 绘制X轴
        drawXLine(canvas);
        // 绘制Y轴文本
        drawYText(canvas);
        // 绘制背景虚线
        drawBackDottedLine(canvas);
        // 绘制阴影折线
        drawLine(canvas);
        // 绘制折线点和提示文本
        drawLinePoint(canvas);
    }

    /**
     * 绘制X轴时间文本
     */
    private void drawBottomText(Canvas canvas) {
        Rect endText = getTextBounds(mXEndText, mXTextPaint);
        canvas.drawText(mXStartText, mXTextLeftInterval, mHeight - mXBottomInterval, mXTextPaint);
        canvas.drawText(mXEndText, mWidth - mXTextLeftInterval - endText.width(), mHeight - mXBottomInterval, mXTextPaint);
    }

    /**
     * 绘制X轴
     */
    private void drawXLine(Canvas canvas) {
        Rect startText = getTextBounds(mXStartText, mXTextPaint);
        // 距离底边的距离 = 底边距离 * 2 + X轴文本高度
        int xBottom = mHeight - getPaddingTop() - getPaddingBottom() - mXBottomInterval * 2 - startText.height();
        canvas.drawLine(mYLeftInterval, xBottom, mWidth - mXRightInterval + dpToPx(4), xBottom, mXPaint);
    }

    /**
     * 绘制Y轴文本
     */
    private void drawYText(Canvas canvas) {
        Rect startText = getTextBounds(mXStartText, mXTextPaint);
        for (int i = 0; i < mYValue.size(); i++) {
            // 绘制区域 = 总高度 - 下边距 - 上边距
            // 下边距 = 文本高度 + 文本距离上下的间距
            // 绘制区域 / (绘制数据的数量 - 1) (减一是计算绘制之间的间距， 如果不减一， 那么在开始第一个绘制时会多出一段间距)
            float y = (float) (mHeight - getPaddingBottom() - getPaddingTop() - mXBottomInterval * 2 - mYTopInterval - startText.height()) / (mYValue.size() - 1);
            // X轴 文本水平居中X轴    Y轴 从最大值到最小值，反着绘制， Y点从上到下 但要加 上边距
            // Y轴文本  距离左边边距, Y轴文本的宽度 / 2  居中绘制
            canvas.drawText(mYValue.get(mYValue.size() - (i + 1)).value, mXTextLeftInterval, y * i + mYTopInterval, mYTextPaint);
        }
    }

    /**
     * 绘制背景虚线
     */
    private void drawBackDottedLine(Canvas canvas) {
        Rect startText = getTextBounds(mXStartText, mXTextPaint);
        for (int i = 0; i < mYValue.size(); i++) {
            float y = (float) (mHeight - getPaddingBottom() - getPaddingTop() - mXBottomInterval * 2 - mYTopInterval - startText.height()) / (mYValue.size() - 1);
            // x轴距离左边 = Y轴文本宽度 + Y轴文本左右边距
            canvas.drawLine(mXTextLeftInterval * 2 + yRect.width(), y * i + mYTopInterval, mWidth - mXRightInterval + dpToPx(4), y * i + mYTopInterval, mDottedPaint);
        }
    }

    /**
     * 绘制阴影折线
     **/
    private void drawLine(Canvas canvas) {
        if (mXValue.size() <= 0) return;
        Rect startText = getTextBounds(mXStartText, mXTextPaint);
        Path path = new Path();
        // 绘制区域 = 总高度 - 下边距 - 上边距
        float totalHeight = mHeight - getPaddingBottom() - getPaddingTop() - mXBottomInterval * 2 - startText.height() - mYTopInterval;
        // 起点x、y轴开始绘制
        float x = mXFirstPoint;
        float y = (mHeight - getPaddingBottom() - mXBottomInterval * 2 - startText.height()) - Float.parseFloat(mXValue.get(0).value) * totalHeight / max;
        // 绘制x、y轴左下角起点
        path.moveTo(mYLeftInterval, mHeight - getPaddingBottom() - getPaddingTop() - mXBottomInterval * 2 - startText.height());
        // 绘制第一个点的位置
        path.lineTo(x, y);
        // 因为绘制了第一个点，所以i起始是1
        for (int i = 1; i < mXValue.size(); i++) {
            // x点绘制的位置， mInterval是两点的间距 * 点的数量 + x轴距离左边距
            x = mXFirstPoint + mInterval * i;
            // y轴上到下是数字变大 所以需要反着绘制  绘制区域 - 百分比高度(百分比高度 = 数值 * 总高度 / 数据最大值)， 相反过来就是从下到上的比例高度
            y = (mHeight - getPaddingBottom() - mXBottomInterval * 2 - startText.height()) - Float.parseFloat(mXValue.get(i).value) * totalHeight / max;
            path.lineTo(x, y);
        }
        // 如果不绘制点位到下方的路径， 那么 不会实现全部阴影效果， x 就是 绘制最后一个点的位置， y 就是底边的位置
        path.lineTo(x, mHeight - mXBottomInterval * 2 - startText.height());
        canvas.drawPath(path, mLinePaint);

        // 绘制折线
        Path linePath = new Path();
        // 起点x、y轴开始绘制
        float x1;
        float y1 = (mHeight - getPaddingBottom() - mXBottomInterval * 2 - startText.height()) - Float.parseFloat(mXValue.get(0).value) * totalHeight / max;
        // 绘制第一个点的位置
        linePath.moveTo(mXFirstPoint, y1);
        // 因为绘制了第一个点，所以i起始是1
        for (int i = 1; i < mXValue.size(); i++) {
            // x点绘制的位置， mInterval是两点的间距 * 点的数量 + x轴距离左边距
            x1 = mXFirstPoint + mInterval * i;
            // y轴上到下是数字变大 所以需要反着绘制  绘制区域 - 百分比高度(百分比高度 = 数值 * 总高度 / 数据最大值)， 相反过来就是从下到上的比例高度
            y1 = (mHeight - getPaddingBottom() - mXBottomInterval * 2 - startText.height()) - Float.parseFloat(mXValue.get(i).value) * totalHeight / max;
            linePath.lineTo(x1, y1);
        }
        // 绘制折线
        mPointPaint.setColor(mLineColor);
        mPointPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(linePath, mPointPaint);
    }

    /**
     * 绘制折线和提示文本
     */
    private void drawLinePoint(Canvas canvas) {
        Rect startText = getTextBounds(mXStartText, mXTextPaint);
        // 绘制区域 = 总高度 - 下边距 - 上边距
        float totalHeight = mHeight - getPaddingBottom() - getPaddingTop() - mXBottomInterval * 2 - startText.height() - mYTopInterval;
        for (int i = 0; i < mXValue.size(); i++) {
            // x点绘制的位置， mInterval是两点的间距 * 点的数量 + x轴距离左边距
            float x = mInterval * i + mXFirstPoint;
            // y轴上到下是数字变大 所以需要反着绘制  绘制区域 - 百分比高度(百分比高度 = 数值 * 总高度 / 数据最大值)， 相反过来就是从下到上的比例高度
            float y = (mHeight - getPaddingBottom() - getPaddingTop() - mXBottomInterval * 2 - startText.height()) - Float.parseFloat(mXValue.get(i).value) * totalHeight / max;
            if (mCurrentSelectPoint == i + 1) {
                // 绘制垂直直线、点、文本
                drawVerticalLinePointText(canvas, x, startText, mXValue.get(i).num);
                // 绘制选中的点
                drawCurrentSelectPoint(canvas, x, y);
                // 绘制选中提示点
                drawCurrentTextBox(canvas, x, y - dpToPx(10), mXValue.get(i).value);
            }
        }
    }

    /**
     * 绘制垂直直线、点、文本
     */
    private void drawVerticalLinePointText(Canvas canvas, float x, Rect startText, String text) {
        int y = mHeight - getPaddingBottom() - mXBottomInterval * 2 - startText.height();
        mLinePointPaint.setStyle(Paint.Style.STROKE);
        mLinePointPaint.setColor(mLinePointColor);
        canvas.drawLine(x, y, x, mYTopInterval, mLinePointPaint);
        mLinePointPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, dpToPx(4), mLinePointPaint);
        mLinePointPaint.setColor(mPointColor);
        canvas.drawCircle(x, y, dpToPx(2), mLinePointPaint);
        Rect rect = getTextBounds(text, mXTextPaint);
        canvas.drawText(text, x - (float) rect.width() / 2, y - dpToPx(6) - (float) rect.height() / 2, mXTextPaint);
    }

    /**
     * 绘制选中的点
     */
    private void drawCurrentSelectPoint(Canvas canvas, float x, float y) {
        mPointPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, dpToPx(4), mPointPaint);
        mPointPaint.setColor(mPointColor);
        mPointPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, dpToPx(2), mPointPaint);
    }

    /**
     * 绘制选中提示框
     */
    private void drawCurrentTextBox(Canvas canvas, float x, float y, String text) {
        Rect rect = getTextBounds(text, mXTextPaint);
        // y点计算  以下两种方法均可
        // x减去文本的宽度  y - 三角的高度 - 文本高度 / 2
        canvas.drawText(text, x - (float) rect.width() / 2, y - dpToPx(6) - (float) rect.height() / 2, mXTextPaint);
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

    /**
     * 获取丈量文本的矩形
     *
     * @param text  文本
     * @param paint 画笔
     */
    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.getParent().requestDisallowInterceptTouchEvent(true);//当该view获得点击事件，就请求父控件不拦截事件
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                clickAction(event);
                break;
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

    /**
     * 点击X轴坐标或者折线节点
     *
     * @param event 事件
     */
    private void clickAction(MotionEvent event) {
        Rect startText = getTextBounds(mXStartText, mXTextPaint);
        int dp3 = dpToPx(3);
        float eventX = event.getX();
        float eventY = event.getY();
        // 绘制区域 = 总高度 - 下边距 - 上边距
        float bottomInterval = mHeight - getPaddingBottom() - getPaddingTop() - mXBottomInterval * 2 - startText.height() - mYTopInterval;
        for (int i = 0; i < mXValue.size(); i++) {
            // x点绘制的位置， mInterval是两点的间距 * 点的数量 + x轴距离左边距
            float x = mInterval * i + mXFirstPoint;
            // y轴上到下是数字变大 所以需要反着绘制  绘制区域 - 百分比高度(百分比高度 = 数值 * 总高度 / 数据最大值)， 相反过来就是从下到上的比例高度
            if (eventX >= x - dp3 && eventX <= x + dp3 && eventY >= mYTopInterval && eventY <= bottomInterval) {
                mCurrentSelectPoint = i + 1;
                invalidate();
                if (onSelectedActionClick != null) {
                    onSelectedActionClick.onActionClick(i, mXValue.get(i).num, mXValue.get(i).value);
                }
                return;
            }
        }
    }

    /**
     * 设置开始日期数据
     */
    public void setXStartText(String mXStartText) {
        this.mXStartText = mXStartText;
    }

    /**
     * 设置结束日期数据
     */
    public void setXEndText(String mXEndText) {
        this.mXEndText = mXEndText;
    }

    /**
     * 设置数据
     */
    public void setValue(List<XValue> xValue, List<YValue> yValue) {
        this.mXValue = xValue;
        this.mYValue = yValue;
        // 设置选中的位置在最后一个
        mCurrentSelectPoint = mXValue.size();
        invalidate();
    }

    /**
     * 设置当前选中的点
     */
    public void setCurrentSelectPoint(int currentSelectPoint) {
        this.mCurrentSelectPoint = currentSelectPoint;
        invalidate();
    }

    public void setOnSelectedActionClick(OnSelectedActionClick onSelectedActionClick) {
        this.onSelectedActionClick = onSelectedActionClick;
    }

    public static class XValue {
        private final String num;
        private final String value;

        public XValue(String num, String value) {
            this.num = num;
            this.value = value;
        }
    }

    public static class YValue {
        private final int num;
        private final String value;

        public YValue(int num, String value) {
            this.num = num;
            this.value = value;
        }
    }

    public interface OnSelectedActionClick {
        void onActionClick(int position, String num, String text);
    }
}