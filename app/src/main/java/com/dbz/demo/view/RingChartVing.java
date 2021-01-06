package com.dbz.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.dbz.demo.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * description:
 *
 * @author Db_z
 * date 2020/10/18 9:45
 * @version V1.0
 */
public class RingChartVing extends View {

    /**
     * 内圆的画笔
     */
    private Paint mCenterPaint;
    /**
     * 外圆的画笔
     */
    private Paint mCirclePaint;
    /**
     * 外圆文本的画笔
     */
    private Paint mCircleTextPaint;
    /**
     * 背景颜色
     */
    private int mBackgroundColor = Color.WHITE;
    /**
     * 内圆的颜色
     */
    private int mCenterColor;
    /**
     * 外圆文本的大小
     */
    private int mCircleTextSize = dpToPx(13);
    /**
     * 外圆大小
     */
    private int mCircleRadiusSize = dpToPx(40);
    /**
     * 内圆大小
     */
    private int mCenterRadiusSize = dpToPx(20);
    /**
     * 宽、高
     */
    private int mWidth, mHeight;
    /**
     * X、Y轴中心点
     */
    private int mXCentralPoint, mYCentralPoint;
    /**
     * 左边距
     */
    private int leftMargin;
    /**
     * 上边距
     */
    private int topMargin;
    /**
     * 开始绘制圆的角度
     */
    private float preAngle = -90;
    /**
     * 结束绘制圆的角度
     */
    private float endAngle = -90;

    private float preRate;

    private float rate = 0.4f;     //点的外延距离  与  点所在圆半径的长度比率

    private float extendLineWidth = dpToPx(20);     //点外延后  折的横线的长度
    /**
     * 绘制外圆时延线文本的点
     */
    private final HashMap<Integer, Point> pointArcCenterMap = new HashMap<>();
    /**
     * 是否绘制内圆 默认绘制
     */
    private boolean isRing = true;
    /**
     * 是否绘制外圆的文本占比 默认绘制
     */
    private boolean isShowRate = true;
    /**
     * 文本占比 是否显示整体数据还是占据比例  默认显示 占比比例 整体数据
     */
    private boolean isShowValueData = false;
    /**
     * 绘制圆时的颜色
     */
    private ArrayList<Integer> mValueColor = new ArrayList<>();
    /**
     * 绘制圆时的数据
     */
    private List<Float> mValueData = new ArrayList<>();


    public RingChartVing(Context context) {
        this(context, null);
    }

    public RingChartVing(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingChartVing(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        initPaint();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        @SuppressLint("Recycle") final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RingChartVing, defStyleAttr, 0);
        mBackgroundColor = typedArray.getColor(R.styleable.RingChartVing_dbz_ring_background_color, mBackgroundColor);
        mCenterColor = typedArray.getColor(R.styleable.RingChartVing_dbz_radius_color, mCenterColor);
        mCenterRadiusSize = (int) typedArray.getDimension(R.styleable.RingChartVing_dbz_radius_size, mCenterRadiusSize);
        mCircleRadiusSize = (int) typedArray.getDimension(R.styleable.RingChartVing_dbz_circle_radius_size, mCircleRadiusSize);
        mCircleTextSize = (int) typedArray.getDimension(R.styleable.RingChartVing_dbz_circle_radius_text_size, mCircleTextSize);
    }

    private void initPaint() {
        mCenterPaint = new Paint();
        mCenterPaint.setAntiAlias(true);
        mCenterPaint.setColor(mCenterColor);
        mCenterPaint.setStyle(Paint.Style.FILL);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mCircleTextPaint = new Paint();
        mCircleTextPaint.setAntiAlias(true);
        mCircleTextPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();
            // 计算中心点
            mXCentralPoint = mWidth / 2;
            mYCentralPoint = mHeight / 2;
            // 距离左边的边距
            leftMargin = mWidth / 2 - mCircleRadiusSize;
            // 距离上边的边距
            topMargin = mHeight / 2 - mCircleRadiusSize;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBackgroundColor);
        drawCircleRadius(canvas);
        drawRadius(canvas);
    }

    private void drawCircleRadius(Canvas canvas) {
        RectF rectF = new RectF((float) (mWidth - getPaddingStart() - getPaddingEnd()) / 2 - mCircleRadiusSize,
                (float) (mHeight - getPaddingTop() - getPaddingBottom()) / 2 - mCircleRadiusSize,
                (float) (mWidth - getPaddingStart() - getPaddingEnd()) / 2 + mCircleRadiusSize,
                (float) (mHeight - getPaddingTop() - getPaddingBottom()) / 2 + mCircleRadiusSize);
        // 以下两个算法一致， 选择一个即可
        RectF rectFPoint = new RectF((float) (mWidth - getPaddingStart() - getPaddingEnd()) / 2 - mCircleRadiusSize + (float) (mCircleRadiusSize - mCenterRadiusSize) / 2,
                (float) (mHeight - getPaddingTop() - getPaddingBottom()) / 2 - mCircleRadiusSize + (float) (mCircleRadiusSize - mCenterRadiusSize) / 2,
                (float) (mWidth - getPaddingStart() - getPaddingEnd()) / 2 + mCircleRadiusSize - (float) (mCircleRadiusSize - mCenterRadiusSize) / 2,
                (float) (mHeight - getPaddingTop() - getPaddingBottom()) / 2 + mCircleRadiusSize - (float) (mCircleRadiusSize - mCenterRadiusSize) / 2);
//        rectFPoint = new RectF(leftMargin + (mCircleRadiusSize - mCenterRadiusSize) / 2,
//                topMargin + (mCircleRadiusSize - mCenterRadiusSize) / 2,
//                leftMargin + mCircleRadiusSize + mCenterRadiusSize + (mCircleRadiusSize - mCenterRadiusSize) / 2,
//                topMargin + mCircleRadiusSize + mCenterRadiusSize + (mCircleRadiusSize - mCenterRadiusSize) / 2);
        if (mValueColor != null) {
            for (int i = 0; i < mValueColor.size(); i++) {
                mCirclePaint.setStyle(Paint.Style.FILL);
                mCirclePaint.setColor(mValueColor.get(i));
                if (mValueData != null) {
                    // 取值数据中结束位置的占比 百分比
                    endAngle = getAngle(getPercent(mValueData, mValueData.get(i)));
                }
                // 开始绘制环形， 矩形， 开始绘制的位置， 结束的位置
                canvas.drawArc(rectF, preAngle, endAngle, true, mCirclePaint);
                // 开始绘制外延折线、文本、比例
                if (isShowRate) {
                    drawArcCenterPoint(canvas, i, rectFPoint);
                }
                // 下一个开始的位置， 要加上当前的位置
                preAngle = preAngle + endAngle;
            }
        }
    }

    /**
     * 开始绘制外延折线、文本、比例
     */
    private void drawArcCenterPoint(Canvas canvas, int position, RectF rectFPoint) {
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(dpToPx(1));
        mCirclePaint.setColor(Color.TRANSPARENT);
        canvas.drawArc(rectFPoint, preAngle, endAngle / 2, true, mCirclePaint);
        dealPoint(rectFPoint, preAngle, endAngle / 2, position);
        Point point = pointArcCenterMap.get(position);
        mCirclePaint.setColor(Color.WHITE);
        canvas.drawCircle(point.x, point.y, dpToPx(2), mCirclePaint);

        if (preRate / 2 + mValueData.get(position) / 2 < 5) {
            extendLineWidth += dpToPx(20);
            rate -= 0.05f;
        } else {
            extendLineWidth = dpToPx(20);
            rate = 0.4f;
        }

        //外延画折线
        float lineXPoint = (point.x - (leftMargin + mCircleRadiusSize)) * (1 + rate);
        float lineYPoint = (point.y - (topMargin + mCircleRadiusSize)) * (1 + rate);
        float[] floats = new float[8];
        floats[0] = point.x;
        floats[1] = point.y;
        floats[2] = leftMargin + mCircleRadiusSize + lineXPoint;
        floats[3] = topMargin + mCircleRadiusSize + lineYPoint;
        floats[4] = leftMargin + mCircleRadiusSize + lineXPoint;
        floats[5] = topMargin + mCircleRadiusSize + lineYPoint;
        if (point.x >= leftMargin + mCircleRadiusSize) {
            mCircleTextPaint.setTextAlign(Paint.Align.LEFT);
            floats[6] = leftMargin + mCircleRadiusSize + lineXPoint + extendLineWidth;
        } else {
            mCircleTextPaint.setTextAlign(Paint.Align.RIGHT);
            floats[6] = leftMargin + mCircleRadiusSize + lineXPoint - extendLineWidth;
        }
        floats[7] = topMargin + mCircleRadiusSize + lineYPoint;
        mCircleTextPaint.setColor(mValueColor.get(position));
        mCircleTextPaint.setStyle(Paint.Style.STROKE);
        mCircleTextPaint.setStrokeWidth(dpToPx(1));
        canvas.drawLines(floats, mCircleTextPaint);
        mCircleTextPaint.setStyle(Paint.Style.FILL);
        mCircleTextPaint.setTextSize(mCircleTextSize);
        // 是否显示占比数据的原始数据还是占比比例
        String value;
        if (isShowValueData) {
            value = String.valueOf(mValueData.get(position));
        } else {
            value = getPercentString(mValueData, mValueData.get(position));
        }
        canvas.drawText(value + "%", floats[6], floats[7] + (float) mCircleTextSize / 3, mCircleTextPaint);
        preRate = mValueData.get(position);
    }

    private void dealPoint(RectF rectF, float startAngle, float endAngle, int position) {
        Path path = new Path();
        //通过Path类画一个90度（180—270）的内切圆弧路径
        path.addArc(rectF, startAngle, endAngle);
        PathMeasure measure = new PathMeasure(path, false);
        float[] floats = new float[]{0f, 0f};
        //利用PathMeasure分别测量出各个点的坐标值floats
        int divisor = 1;
        measure.getPosTan(measure.getLength() / divisor, floats, null);
        float x = floats[0];
        float y = floats[1];
        Point point = new Point(Math.round(x), Math.round(y));
        pointArcCenterMap.put(position, point);
    }

    /**
     * 绘制内半径
     */
    private void drawRadius(Canvas canvas) {
        if (isRing) {
            canvas.drawCircle(mXCentralPoint, mYCentralPoint, mCenterRadiusSize, mCenterPaint);
        }
    }


    /**
     * 设置绘制数据、绘制数据的颜色
     */
    public void setValueData(List<Float> valueData, ArrayList<Integer> valueColor) {
        setValueData(valueData, valueColor, true);
    }

    /**
     * 设置绘制数据、绘制数据的颜色
     *
     * @param valueData  数据
     * @param valueColor 颜色
     * @param isRing     是否绘制内半径
     */
    public void setValueData(List<Float> valueData, ArrayList<Integer> valueColor, boolean isRing) {
        setValueData(valueData, valueColor, isRing, false);
    }

    /**
     * 设置绘制数据、绘制数据的颜色
     *
     * @param valueData  数据
     * @param valueColor 颜色
     * @param isRing     是否绘制内半径
     * @param isShowRate 是否显示占数据比例
     */
    public void setValueData(List<Float> valueData, ArrayList<Integer> valueColor, boolean isRing, boolean isShowRate) {
        setValueData(valueData, valueColor, isRing, isShowRate, false);
    }

    /**
     * 设置绘制数据、绘制数据的颜色
     *
     * @param valueData       数据
     * @param valueColor      颜色
     * @param isRing          是否绘制内半径
     * @param isShowRate      是否显示占数据比例
     * @param isShowValueData 是否显示占数据比例原始数据， 只有isShowRate = true时 有效
     */
    public void setValueData(List<Float> valueData, ArrayList<Integer> valueColor, boolean isRing, boolean isShowRate, boolean isShowValueData) {
        mValueData = valueData;
        mValueColor = valueColor;
        this.isRing = isRing;
        this.isShowRate = isShowRate;
        this.isShowValueData = isShowValueData;
        invalidate();
    }

    /**
     * @param percent 当前数据
     * @return 当前数据占据百分比
     */
    private float getPercent(List<Float> data, float percent) {
        if (data.size() == 0) return 0;
        float total = getTotal(data);
        return percent * 100 / total;
    }

    /**
     * @param percent 当前数据
     * @return 当前数据占据百分比
     */
    private String getPercentString(List<Float> data, float percent) {
        if (data.size() == 0) return "";
        float total = getTotal(data);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(percent * 100 / total);
    }

    /**
     * 获取所有值总和
     */
    private float getTotal(List<Float> data) {
        float total = 0;
        for (int i = 0; i < data.size(); i++) {
            total += data.get(i);
        }
        return total;
    }

    /**
     * @param percent 百分比
     */
    private float getAngle(float percent) {
        return 360f * percent / 100f;
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