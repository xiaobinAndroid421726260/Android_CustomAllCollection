package com.dbz.demo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dbz.demo.R;

/**
 * description:
 *
 * @author Db_z
 * date 2020/9/27 17:02
 * @version V1.0
 */
public class RoundRelativeLayout extends RelativeLayout {
    private final Paint mPaint;
    private final RectF mRectF;
    private float mRadius;
    private boolean isClipBackground = true;

    public RoundRelativeLayout(@NonNull Context context) {
        this(context, null);
    }

    public RoundRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundRelativeLayout);
        mRadius = ta.getDimension(R.styleable.RoundRelativeLayout_dbz_layout_radius, 0);
        isClipBackground = ta.getBoolean(R.styleable.RoundRelativeLayout_dbz_clip_back_ground, isClipBackground);
        ta.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    public void setRadius(float radius) {
        mRadius = radius;
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.set(0, 0, w, h);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        // 28
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            draw28(canvas);
        } else {
            draw27(canvas);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            dispatchDraw28(canvas);
        } else {
            dispatchDraw27(canvas);
        }
    }

    private void draw27(Canvas canvas) {
        if (isClipBackground) {
            canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG);
            super.draw(canvas);
            canvas.drawPath(getPath(), mPaint);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }

    private void draw28(Canvas canvas) {
        if (isClipBackground) {
            canvas.save();
            canvas.clipPath(getPath());
            super.draw(canvas);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }

    private void dispatchDraw27(Canvas canvas) {
        canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        canvas.drawPath(getPath(), mPaint);
        canvas.restore();
    }

    private void dispatchDraw28(Canvas canvas) {
        canvas.save();
        canvas.clipPath(getPath());
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    private Path getPath() {
        final Path path = new Path();
        path.reset();
        path.addRoundRect(mRectF, mRadius, mRadius, Path.Direction.CW);
        return path;
    }
}