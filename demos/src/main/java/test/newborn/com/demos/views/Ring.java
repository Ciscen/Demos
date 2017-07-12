package test.newborn.com.demos.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xiaochongzi on 17-6-29
 */

public class Ring extends View {
    private Paint mPaint;
    private int mHeight;
    private int mWidth;

    public Ring(Context context) {
        this(context, null);
    }

    public Ring(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = mWidth / 2;
        //绘制路径
        Path path = new Path();
        //从哪个点开始绘制
        path.moveTo(0, mWidth / 2);
        //然后绘制到哪个点
        path.lineTo(mWidth / 2, mWidth);
        //然后再绘制到哪个点
        path.lineTo(mWidth, mWidth / 2);
        path.close();
        //按路径绘制，就是一个菱形
        canvas.drawPath(path,mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);
        mPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Daweibalang717",mWidth/2,mWidth/2,mPaint);
    }
}
