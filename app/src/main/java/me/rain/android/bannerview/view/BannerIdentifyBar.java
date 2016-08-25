package me.rain.android.bannerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class BannerIdentifyBar extends View {

    private Paint mPaint;
    private float mWidth;
    private int mPageSize;
    private float mPageWidth = 0f;
    private int mPosition;
    private float mPositionOffset;
    private int mLineColor = Color.RED;

    public BannerIdentifyBar(Context context) {
        this(context, null);
    }

    public BannerIdentifyBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerIdentifyBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;
        mPaint = new Paint();
        mPaint.setColor(mLineColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1000f);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mPosition == 0) {
            canvas.drawLine((mPageSize - 3) * mPageWidth + mPageWidth * mPositionOffset, 0, (mPageSize - 2) * mPageWidth + mPageWidth * mPositionOffset, 0, mPaint);
            canvas.drawLine(0, 0, mPageWidth * mPositionOffset, 0, mPaint);
        } else if (mPosition == mPageSize - 2) {
            canvas.drawLine((mPosition - 1) * mPageWidth + mPageWidth * mPositionOffset, 0, mPosition * mPageWidth + mPageWidth * mPositionOffset, 0, mPaint);
            canvas.drawLine(0, 0, mPageWidth * mPositionOffset, 0, mPaint);
        } else {
            canvas.drawLine((mPosition - 1) * mPageWidth + mPageWidth * mPositionOffset, 0, mPosition * mPageWidth + mPageWidth * mPositionOffset, 0, mPaint);
        }

    }

    public void setPageWidth(int pageSize) {
        mPageSize = pageSize;
        calcPageWidth();
    }

    private void calcPageWidth() {
        this.mPageWidth = this.mWidth / (this.mPageSize - 2);
    }

    public void setPageScrolled(int position, float positionOffset) {
        mPosition = position;
        mPositionOffset = positionOffset;
        invalidate();
    }

    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
        mPaint.setColor(mLineColor);
    }
}
