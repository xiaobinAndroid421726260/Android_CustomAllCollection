package com.dbz.demo.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import com.dbz.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author Db_z
 * date 2020/11/18 15:20
 * @version V1.0
 */
public class ChartReportView extends View {

    /**
     * X、Y轴画笔
     */
    private Paint mXYPaint;
    /**
     * Y轴文本
     */
    private Paint mYTextPaint;
    /**
     * 折线画笔
     */
    private Paint mLinePaint;
    /**
     * 点位画笔
     */
    private Paint mPointPaint;
    /**
     * 背景颜色
     */
    private int mBackgroundColor = Color.WHITE;
    /**
     * X、Y轴文本颜色
     */
    private int mXYTextColor = Color.parseColor("#02bbb7");
    /**
     * X、Y轴文本大小
     */
    private int mXYTextSize = dpToPx(13);
    /**
     * 点位颜色
     */
    private int mPointColor = Color.parseColor("#804AFFFF");
    /**
     * 点位文本颜色
     */
    private int mPointTextColor = Color.WHITE;
    /**
     * 点位文本大小
     */
    private int mPointTextSize = dpToPx(13);
    /**
     * 折线颜色
     */
    private int mLineColor = Color.parseColor("#4AFFFF");
    /**
     * 折线宽度
     */
    private int mLineWidth = dpToPx(1);
    /**
     * X、Y轴的颜色
     */
    private int mXYColor = Color.parseColor("#02bbb7");
    /**
     * 两点之间的间隔
     */
    private int mInterval = dpToPx(30);
    /**
     * Y轴文本距左边间距
     */
    private final int mYTextLeftInterval = dpToPx(5);
    /**
     * X、 Y轴原点距左边的距离
     */
    private int mYLeftInterval = dpToPx(30);
    /**
     * X 轴 第一个坐标
     */
    private float mXFirstPoint;
    private float maxXFirstPoint;
    private float minXFirstPoint;
    /**
     * X、Y轴距离底边的距离
     */
    private final int mXBottomInterval = dpToPx(15);
    /**
     * Y轴距离上边距
     */
    private final int mYTopInterval = dpToPx(40);
    /**
     * 当前选中的点 默认在最后一位
     */
    private int mCurrentSelectPoint = 1;
    /**
     * 宽、高
     */
    private int mWidth, mHeight;
    /**
     * 整体数据最大值
     */
    private int max = 1;
    /**
     * 是否正在滑动
     */
    private boolean isScrolling = false;
    /**
     * 动画控制
     */
    private boolean aniLock = false;
    /**
     * 速度检测器
     */
    private VelocityTracker velocityTracker;
    private OnSelectedActionClick onSelectedActionClick;

    private List<XValue> mXValue = new ArrayList<>();
    private List<YValue> mYValue = new ArrayList<>();


    public ChartReportView(Context context) {
        this(context, null);
    }

    public ChartReportView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartReportView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        initPaint();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartReportView, defStyleAttr, 0);
        mBackgroundColor = typedArray.getColor(R.styleable.ChartReportView_dbz_background_color, Color.WHITE);
        mLineColor = typedArray.getColor(R.styleable.ChartReportView_dbz_line_point_color, mLineColor);
        mPointColor = typedArray.getColor(R.styleable.ChartReportView_dbz_point_color, mPointColor);
        mPointTextColor = typedArray.getColor(R.styleable.ChartReportView_dbz_point_text_color, mPointTextColor);
        mXYColor = typedArray.getColor(R.styleable.ChartReportView_dbz_x_y_color, mXYColor);
        mXYTextColor = typedArray.getColor(R.styleable.ChartReportView_dbz_x_y_text_color, mXYTextColor);
        mPointTextSize = (int) typedArray.getDimension(R.styleable.ChartReportView_dbz_point_text_size, mPointTextSize);
        mLineWidth = (int) typedArray.getDimension(R.styleable.ChartReportView_dbz_line_width, mLineWidth);
        mXYTextSize = (int) typedArray.getDimension(R.styleable.ChartReportView_dbz_x_y_text_size,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, getResources().getDisplayMetrics()));
        mInterval = (int) typedArray.getDimension(R.styleable.ChartReportView_dbz_point_interval, mInterval);
        typedArray.recycle();
    }

    private void initPaint() {
        mXYPaint = new Paint();
        mXYPaint.setAntiAlias(true);
        mXYPaint.setColor(mXYColor);
        mXYPaint.setStrokeWidth(mLineWidth);

        mYTextPaint = new Paint();
        mYTextPaint.setAntiAlias(true);
        mYTextPaint.setColor(mXYTextColor);
        mYTextPaint.setTextSize(mXYTextSize);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setStyle(Paint.Style.STROKE);

        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setTextSize(mPointTextSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();
            // 测量Y轴数据的文本宽度
            Rect rect = getTextBounds(mYValue.get(mYValue.size() - 1).value, mYTextPaint);
            // 计算X轴的左边距
            mYLeftInterval = rect.width() + mYTextLeftInterval * 2;
            // 第一个X轴点的位置
            mXFirstPoint = mYLeftInterval + mInterval;
            // 遍历数据最大值 如果为0那么默认为1
            for (int i = 0; i < mXValue.size(); i++) {
                max = Math.max(max, mXValue.get(i).num);
            }
            if (max == 0) {
                max = 1;
            }
            minXFirstPoint = mWidth - (mWidth - mYLeftInterval) * 0.1f - mInterval * (mXValue.size() - 1);
            maxXFirstPoint = mXFirstPoint;
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBackgroundColor);
        drawXYLine(canvas);
        drawYText(canvas);
        drawBrokenLineAndPoint(canvas);
        if (!isScrolling && !aniLock) {
            scrollAtStart();
        }
    }

    /**
     * 绘制X、Y轴
     */
    private void drawXYLine(Canvas canvas) {
        mXYPaint.setColor(mXYColor);
        // 绘制X轴
        canvas.drawLine(mYLeftInterval, mHeight - getPaddingBottom() - mXBottomInterval,
                mWidth - getPaddingRight(), mHeight - getPaddingBottom() - mXBottomInterval, mXYPaint);
        // 绘制Y轴
        canvas.drawLine(mYLeftInterval, 0, mYLeftInterval, mHeight - getPaddingBottom() - mXBottomInterval, mXYPaint);
        //绘制y轴箭头
        mXYPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(mYLeftInterval - dpToPx(5), mXBottomInterval);
        path.lineTo(mYLeftInterval, 0);
        path.lineTo(mYLeftInterval + dpToPx(5), mXBottomInterval);
        canvas.drawPath(path, mXYPaint);
    }

    /**
     * 绘制Y轴文本
     */
    private void drawYText(Canvas canvas) {
        for (int i = 0; i < mYValue.size(); i++) {
            Rect rect = getTextBounds(mYValue.get(i).value, mYTextPaint);
            // 绘制区域 = 总高度 - 下边距 - 上边距
            // 绘制区域 / (绘制数据的数量 - 1) (减一是计算绘制之间的间距， 如果不减一， 那么在开始第一个绘制时会多出一段间距)
            float y = (float) (mHeight - getPaddingBottom() - mXBottomInterval - getPaddingTop() - mYTopInterval) / (mYValue.size() - 1);
            // X轴 文本水平居中X轴    Y轴 从最大值到最小值，反着绘制， Y点从上到下 但要加 上边距
            canvas.drawText(mYValue.get(mYValue.size() - (i + 1)).value, rect.centerX(), y * i + mYTopInterval, mYTextPaint);
        }
//        Rect rect = getTextBounds("100", mXYPaint);
//        // X轴  文本中间开始绘制 + 距离左边的距离      Y轴   从底部开始绘制， 要减去底边距离
//        canvas.drawText("0", (float) rect.width() / 2 + mYTextLeftInterval, mHeight - getPaddingBottom() - getPaddingTop() - mXBottomInterval, mXYPaint);
//        // X轴  文本中间开始绘制两位正好中间           Y轴  （总高度 - 顶部距离 - 底部距离） / 2 是整个的中心点，要在加上距离上边的边距才是绘制部分的中心点
//        canvas.drawText("50", (float) rect.width() / 2, (float) ((mHeight - mYTopInterval - mXBottomInterval - getPaddingBottom() - getPaddingTop()) / 2) + mYTopInterval, mXYPaint);
//        // X轴  文本中间开始绘制三位要减去左边的距离    Y轴   (要使文本在中间显示) 距离上边距是底边 + 文本的高度 / 2   (正常显示是 mYTopInterval 距离上边的边距)
//        canvas.drawText("100", (float) rect.width() / 2 - mYTextLeftInterval, mYTopInterval + (float) rect.height() / 2, mXYPaint);
    }

    /**
     * 绘制折线和折线交点处对应的点
     */
    private void drawBrokenLineAndPoint(Canvas canvas) {
        //重新开一个图层
        int layerId = canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);
        drawLine(canvas);
        drawLinePoint(canvas);
        // 将折线超出x轴坐标的部分截取掉
        mXYPaint.setStyle(Paint.Style.FILL);
        mXYPaint.setColor(mBackgroundColor);
        mXYPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        RectF rectF = new RectF(0, 0, mYLeftInterval, mHeight);
        canvas.drawRect(rectF, mXYPaint);
        mXYPaint.setXfermode(null);
        //保存图层
        canvas.restoreToCount(layerId);
    }

    /**
     * 绘制折线
     **/
    private void drawLine(Canvas canvas) {
        if (mXValue.size() <= 0) return;
        Path path = new Path();
        // 绘制区域 = 总高度 - 下边距 - 上边距
        float totalHeight = mHeight - getPaddingBottom() - mXBottomInterval - getPaddingTop() - mYTopInterval;
        // 起点x、y轴开始绘制
        float x = mXFirstPoint;
        float y = (mHeight - getPaddingBottom() - mXBottomInterval) - mXValue.get(0).num * totalHeight / max;
        // 绘制x、y轴左下角起点
        path.moveTo(mYLeftInterval, mHeight - getPaddingBottom() - mXBottomInterval);
        // 绘制第一个点的位置
        path.lineTo(x, y);
        // 因为绘制了第一个点，所以i起始是1
        for (int i = 1; i < mXValue.size(); i++) {
            // x点绘制的位置， mInterval是两点的间距 * 点的数量 + x轴距离左边距
            x = mXFirstPoint + mInterval * i;
            // y轴上到下是数字变大 所以需要反着绘制  绘制区域 - 百分比高度(百分比高度 = 数值 * 总高度 / 数据最大值)， 相反过来就是从下到上的比例高度
            y = (mHeight - getPaddingBottom() - mXBottomInterval) - mXValue.get(i).num * totalHeight / max;
            path.lineTo(x, y);
        }
        canvas.drawPath(path, mLinePaint);
    }

    /**
     * 绘制折线点和提示框
     */
    private void drawLinePoint(Canvas canvas) {
        // 绘制区域 = 总高度 - 下边距 - 上边距
        float totalHeight = mHeight - getPaddingBottom() - mXBottomInterval - getPaddingTop() - mYTopInterval;
        for (int i = 0; i < mXValue.size(); i++) {
            // x点绘制的位置， mInterval是两点的间距 * 点的数量 + x轴距离左边距
            float x = mInterval * i + mXFirstPoint;
            // y轴上到下是数字变大 所以需要反着绘制  绘制区域 - 百分比高度(百分比高度 = 数值 * 总高度 / 数据最大值)， 相反过来就是从下到上的比例高度
            float y = (mHeight - getPaddingBottom() - mXBottomInterval) - mXValue.get(i).num * totalHeight / max;
            // 绘制两次点中心两个颜色， 外层透明度50
            mPointPaint.setColor(mPointColor);
            mPointPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x, y, dpToPx(4), mPointPaint);
            // 绘制两次点中心两个颜色， 外层透明度50
            mPointPaint.setColor(mLineColor);
            mPointPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(x, y, dpToPx(2), mPointPaint);
            if (mCurrentSelectPoint == i + 1) {
                // 绘制选中的点
                drawCurrentSelectPoint(canvas, i + 1, x, y);
                // 绘制选中提示点
                drawCurrentTextBox(canvas, i + 1, x, y - dpToPx(10), mXValue.get(i).value);
            }
        }
    }

    /**
     * 绘制当前选中的点
     */
    private void drawCurrentSelectPoint(Canvas canvas, int i, float x, float y) {
        mPointPaint.setColor(Color.parseColor("#d0f3f2"));
        mPointPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, dpToPx(7), mPointPaint);
        mPointPaint.setColor(mPointColor);
        mPointPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, dpToPx(4), mPointPaint);
        mPointPaint.setColor(mLineColor);
        mPointPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, dpToPx(2), mPointPaint);
    }

    /**
     * 绘制选中提示框
     */
    private void drawCurrentTextBox(Canvas canvas, int i, float x, float y, String text) {
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
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(mLineColor);
        canvas.drawPath(path, mPointPaint);
        // 绘制矩形， 周边圆角
        RectF rectF = new RectF(x - dp20, y - dpToPx(5), x + dp20, y - dp6 - dp20);
        canvas.drawRoundRect(rectF, dpToPx(4), dpToPx(4), mPointPaint);
        mPointPaint.setColor(mPointTextColor);
        mPointPaint.setTextSize(mPointTextSize);
        Rect rect = getTextBounds(text, mPointPaint);
        // y点计算  以下两种方法均可
        // x减去文本的宽度  y - 提示框距离点的高度 - 三角的高度 - 提示框 / 2
//        canvas.drawText(text, x - (float) rect.width() / 2, y - dpToPx(10) - dp6 - dpToPx(5) - rectF.height() / 2, mPointPaint);
        // x减去文本的宽度  y - 三角的高度 - 文本高度 / 2
        canvas.drawText(text, x - (float) rect.width() / 2, y - dp6 - (float) rect.height() / 2, mPointPaint);
    }

    /**
     * 点击X轴坐标或者折线节点
     *
     * @param event 事件
     */
    private void clickAction(MotionEvent event) {
        int dp8 = dpToPx(8);
        float eventX = event.getX();
        float eventY = event.getY();
        // 绘制区域 = 总高度 - 下边距 - 上边距
        float totalHeight = mHeight - getPaddingBottom() - mXBottomInterval - getPaddingTop() - mYTopInterval;
        for (int i = 0; i < mXValue.size(); i++) {
            // x点绘制的位置， mInterval是两点的间距 * 点的数量 + x轴距离左边距
            float x = mInterval * i + mXFirstPoint;
            // y轴上到下是数字变大 所以需要反着绘制  绘制区域 - 百分比高度(百分比高度 = 数值 * 总高度 / 数据最大值)， 相反过来就是从下到上的比例高度
            float y = (mHeight - getPaddingBottom() - mXBottomInterval) - mXValue.get(i).num * totalHeight / max;
            // 判断点击的位置在点的旁边
            if (eventX >= x - dp8 && eventX <= x + dp8 && eventY >= y - dp8 && eventY <= y + dp8 && mCurrentSelectPoint != i + 1) {
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
     * 当宽度不足以呈现全部数据时 滚动
     */
    private void scrollAtStart() {
        // 整体数据的宽度 大于 绘制区域宽度
        if (mInterval * mXValue.size() > mWidth - mYLeftInterval) {
            float scrollLength = maxXFirstPoint - minXFirstPoint;
            ValueAnimator animator = ValueAnimator.ofFloat(0, scrollLength);
            animator.setDuration(500L);//时间最大为1000毫秒，此处使用比例进行换算
            animator.setInterpolator(new DecelerateInterpolator());
            animator.addUpdateListener(animation -> {
                float value = (float) animation.getAnimatedValue();
                mXFirstPoint = (int) Math.max(mXFirstPoint - value, minXFirstPoint);
                invalidate();
            });
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    isScrolling = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isScrolling = false;
                    aniLock = true;
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    isScrolling = false;
                    aniLock = true;
                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.start();
        }
    }

    /**
     * 手指抬起后的滑动处理
     */
    private void scrollAfterActionUp() {
        final float velocity = getVelocity();
        float scrollLength = maxXFirstPoint - minXFirstPoint;
        if (Math.abs(velocity) < 10000)//10000是一个速度临界值，如果速度达到10000，最大可以滑动(maxXInit - minXInit)
            scrollLength = (maxXFirstPoint - minXFirstPoint) * Math.abs(velocity) / 10000;
        ValueAnimator animator = ValueAnimator.ofFloat(0, scrollLength);
        animator.setDuration((long) (scrollLength / (maxXFirstPoint - minXFirstPoint) * 1000));//时间最大为1000毫秒，此处使用比例进行换算
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            if (velocity < 0 && mXFirstPoint > minXFirstPoint) {//向左滑动
                mXFirstPoint = Math.max(mXFirstPoint - value, minXFirstPoint);
            } else if (velocity > 0 && mXFirstPoint < maxXFirstPoint) {//向右滑动
                mXFirstPoint = Math.min(mXFirstPoint + value, maxXFirstPoint);
            }
            invalidate();
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isScrolling = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    private float startX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isScrolling) return super.onTouchEvent(event);
        this.getParent().requestDisallowInterceptTouchEvent(true);//当该view获得点击事件，就请求父控件不拦截事件
        obtainVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mInterval * mXValue.size() > mWidth - mYLeftInterval) {//当期的宽度不足以呈现全部数据
                    float scrollX = event.getX() - startX;
                    startX = event.getX();
                    if (mXFirstPoint + scrollX < minXFirstPoint) {
                        mXFirstPoint = (int) minXFirstPoint;
                    } else {
                        mXFirstPoint = Math.min(mXFirstPoint + scrollX, maxXFirstPoint);
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                clickAction(event);
//                scrollAfterActionUp();
                this.getParent().requestDisallowInterceptTouchEvent(false);
                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                recycleVelocityTracker();
                break;
        }
        return true;
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

    /**
     * 获取速度跟踪器
     */
    private void obtainVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    /**
     * 获取速度
     *
     * @return
     */
    private float getVelocity() {
        if (velocityTracker != null) {
            velocityTracker.computeCurrentVelocity(1000);
            return velocityTracker.getXVelocity();
        }
        return 0;
    }

    /**
     * 回收速度跟踪器
     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
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

    /**
     * 设置当前选中的点
     */
    public void setCurrentSelectPoint(int currentSelectPoint) {
        this.mCurrentSelectPoint = currentSelectPoint;
        invalidate();
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

    public static class XValue {
        private final int num;
        private final String value;

        public XValue(int num, String value) {
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
        void onActionClick(int position, int num, String text);
    }

    public void setOnSelectedActionClick(OnSelectedActionClick onSelectedActionClick) {
        this.onSelectedActionClick = onSelectedActionClick;
    }
}