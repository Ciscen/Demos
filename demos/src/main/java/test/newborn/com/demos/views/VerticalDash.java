package test.newborn.com.demos.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xiaochongzi on 17-7-8
 */

public class VerticalDash extends View {

    private Paint mPaint;
    private int offset;

    public VerticalDash(Context context) {
        this(context, null);
    }

    public VerticalDash(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        PathEffect effects = new DashPathEffect(new float[]{5, 10}, 2);
        mPaint.setPathEffect(effects);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(getWidth() / 2, offset, getWidth() / 2, getHeight(), mPaint);
    }

    int x;

    public void setOffset(int dx) {
        x += dx;
        offset = -x;
        invalidate();
    }
}
