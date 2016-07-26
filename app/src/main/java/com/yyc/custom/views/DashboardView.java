package com.yyc.custom.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.yyc.custom.R;

/**
 * Created by HFF on 16/7/26.
 * 仪表盘
 */
public class DashboardView extends View {

    int mBoardColor;    //颜色
    float mBoardWidth;  //stroke宽度
    Paint mPaint;
    int radius; //弧度
    int width;
    int height;
    private DisplayMetrics mDisplayMetrics;
    RectF mBounds;
    float smallLength = 10;
    float largeLength = 20;

    public DashboardView(Context context) {
        super(context);
        init();
    }

    public DashboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DashboardView, 0, 0);
        try {
            mBoardColor = typedArray.getColor(R.styleable.DashboardView_board_color, getResources().getColor(R.color.board_default));
            mBoardWidth = typedArray.getDimension(R.styleable.DashboardView_board_width, getResources().getDimension(R.dimen.board_default_width));
        } finally {
            typedArray.recycle();
        }
        init();
    }

    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mBoardColor);
        mPaint.setStrokeWidth(mBoardWidth);
        mDisplayMetrics = getResources().getDisplayMetrics();
        Log.d("DashboardView--get000", getLeft() + ":" + getTop() + ":" + getRight() + ":" + getBottom());
        mBounds = new RectF(getLeft(), getTop(), getRight(), getBottom());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("DashboardView", "---onDraw---");
        int centerX = width / 2;
        int centerY = height / 2;
        mPaint.setColor(mBoardColor);
        Log.d("DashboardView--mBounds", mBounds.centerX() + ":" + mBounds.centerY());
        Log.d("DashboardView--get1000", getLeft() + ":" + getTop() + ":" + getRight() + ":" + getBottom());

        canvas.drawCircle(centerX, centerY, radius, mPaint);
        float startX, startY;
        float endX, endY;
        for (int i = 0; i < 60; ++i) {
            startX = radius * (float) Math.cos(Math.PI / 180 * i * 6);
            startY = radius * (float) Math.sin(Math.PI / 180 * i * 6);
            if (i % 5 == 0) {
                endX = startX + largeLength * (float) Math.cos(Math.PI / 180 * i * 6);
                endY = startY + largeLength * (float) Math.sin(Math.PI / 180 * i * 6);
            } else {
                endX = startX + smallLength * (float) Math.cos(Math.PI / 180 * i * 6);
                endY = startY + smallLength * (float) Math.sin(Math.PI / 180 * i * 6);
            }
            startX += centerX;
            endX += centerX;
            startY += centerY;
            endY += centerY;
            canvas.drawLine(startX, startY, endX, endY, mPaint);
        }
        canvas.drawCircle(centerX, centerY, 20, mPaint);
        canvas.rotate(60, centerX, centerY);
        canvas.drawLine(centerX, centerY, centerX, centerY - radius, mPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("DashboardView", "---onMeasure---");
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST) {
            width = mDisplayMetrics.densityDpi * 50;
        } else {
            width = widthSpecSize;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            height = mDisplayMetrics.densityDpi * 50;
        } else {
            height = heightSpecSize;
        }
        if (width > height) {
            radius = height / 4;
        } else {
            radius = width / 4;
        }
        Log.d("DashboardView-width", width + ":" + height);
        setMeasuredDimension(width, height);
    }
}
