package test.newborn.com.demos.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xiaochongzi on 17-6-28
 * 用于倒计时，单例。
 */

public class CountDownTimerImpl implements CountDownTimer {
    private static CountDownTimer mCountDownTimer;
    private long mCurTime;//记录实时时间
    private long mDelay;//延迟时间
    private long mPeriod;//时间间隔
    private long mTotalTime;//总时间
    private OnCountDownListener mListener;//执行任务回调
    private boolean isCounting;//是否正在计时
    private Timer mTimer;
    private long mOffsetStart;//用于处理倒计时在period中间中断时的续值,偏移量的起始值
    private long mOffset;//偏移量
    private boolean hasRunTask;//是否过了deley
    private boolean hasRecordStart;//是否记录过开始时间
    private TimerTask mTask;

    private CountDownTimerImpl() {
    }

    public static CountDownTimer getInstance() {
        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimerImpl();
        }
        return mCountDownTimer;
    }

    @Override
    public void reset() {
        reset(0, 0, 0, null);
    }

    @Override
    public void reset(long deley, long period, long totalTime, OnCountDownListener onCountDownListener) {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mDelay = deley;
        mPeriod = period;
        mTotalTime = totalTime;
        mListener = onCountDownListener;
        mCurTime = totalTime;
        mOffset = 0;
        hasRunTask = false;
        isCounting = false;
        hasRecordStart = false;
        mTimer = new Timer();
    }

    @Override
    public void start() {
        if (!isCounting) {
            isCounting = true;
            mOffsetStart = System.currentTimeMillis();
            System.out.println("mOffset---->" + mOffset);
            mTimer.purge();
            mTask = new MyTimerTask();
            long delay = hasRunTask ? mPeriod - mOffset : mDelay - mOffset;
            System.out.println("delay---->" + delay);
            mTimer.schedule(mTask, delay, mPeriod);
        }
    }

    @Override
    public void pause() {
        if (isCounting) {
            isCounting = false;
            long delta = System.currentTimeMillis() - mOffsetStart;
            if (!hasRunTask) {
                mOffset = mOffset + delta;
            } else {
                mOffset = delta;
            }
            mTask.cancel();
        }
    }

    @Override
    public long getCurTime() {
        return hasRunTask ? mCurTime - mOffset : mCurTime;
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (mCurTime >= mPeriod) {
                if (hasRunTask) {
                    mCurTime -= mPeriod;
                }
                hasRunTask = true;
                mListener.onCountDown(mCurTime, mTotalTime - mCurTime);
                mOffsetStart = System.currentTimeMillis();
            } else {
                hasRunTask = false;
                isCounting = false;
                mOffset = 0;
            }
        }
    }

    ;
}


