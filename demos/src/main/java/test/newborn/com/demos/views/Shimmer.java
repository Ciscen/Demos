package test.newborn.com.demos.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import test.newborn.com.demos.R;

/**
 * Created by xiaochongzi on 17-6-30
 */

public class Shimmer extends ImageView {

    private LinearGradient mGradient;
    private Paint mPaint;
    private int[] colors = {0x00ffffff, 0x33ffffff, 0x99ffffff, 0x33ffffff, 0x00ffffff};
    private float[] positions = {0, 0.35f, 0.5f, 0.65f, 1};
    private int mWidth;
    private int mHeight;
    private int mTranslation;
    private int mShaderWidth = 300;
    private float mAngle = 10;
    private int mDuaration = 3000;
    private Matrix matrix;

    public Shimmer(Context context) {
        this(context, null);
    }

    public Shimmer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Shimmer);
        mShaderWidth = (int) (ta.getFloat(R.styleable.Shimmer_shader_disperse_factor, 1f) * mShaderWidth);
        mAngle = ta.getFloat(R.styleable.Shimmer_angel, 0);
        mDuaration = (int) (mDuaration / ta.getFloat(R.styleable.Shimmer_speed_factor, 1));
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mTranslation = -mWidth;
        mGradient = new LinearGradient(getX0(), getY0(), getX1(), getY1(), colors, positions, Shader.TileMode.CLAMP);
        matrix = new Matrix();
        matrix.setTranslate(mTranslation, 0);
        mGradient.setLocalMatrix(matrix);
        mPaint.setShader(mGradient);
        ValueAnimator mAminator = new ValueAnimator();
        mAminator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTranslation = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

        mAminator = new ValueAnimator();
        mAminator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTranslation = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAminator.setIntValues(-(int) (mWidth * 1.5), (int) (mWidth * 1.5));
        mAminator.setDuration(mDuaration);
        mAminator.setRepeatCount(1000);
        mAminator.setInterpolator(new AccelerateInterpolator(1.5f));
        mAminator.start();
    }

    private float getY1() {
        return (float) (0.5 * mHeight + 0.5 * mShaderWidth * Math.sin(ang2rad(mAngle)));
    }

    private float getX1() {
        return (float) (0.5 * mWidth + 0.5 * mShaderWidth * Math.cos(ang2rad(mAngle)));
    }

    private float getY0() {
        return (float) (0.5 * mHeight - 0.5 * mShaderWidth * Math.sin(ang2rad(mAngle)));
    }

    private float getX0() {
        return (float) (0.5 * mWidth - 0.5 * mShaderWidth * Math.cos(ang2rad(mAngle)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.setTranslate(mTranslation, 0);
        mGradient.setLocalMatrix(matrix);
        canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
    }

    private double ang2rad(double angel) {
        return angel / 180 * Math.PI;
    }
}
