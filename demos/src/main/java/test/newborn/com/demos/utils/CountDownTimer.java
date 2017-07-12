package test.newborn.com.demos.utils;

/**
 * Created by xiaochongzi on 17-6-28
 */

public interface CountDownTimer {

    /**
     * 初始化所有数据
     */
    void reset();

    /**
     * 重置倒计时数据
     *
     * @param deley               延时时间
     * @param period              执行间隔
     * @param totalTime           总时间
     * @param onCountDownListener 执行回调
     */
    void reset(long deley, long period, long totalTime, OnCountDownListener onCountDownListener);

    /**
     * 开始计时
     */
    void start();

    /**
     * 暂停计时
     */
    void pause();

    /**
     * 得到当前计时时间
     *
     * @return 当前实时时间，毫秒值
     */
    long getCurTime();

    public interface OnCountDownListener {
        /**
         * 执行时的回调，将会按计时间隔定时回调
         *
         * @param curTime  当前执行到的时间
         * @param lastTime 执行总时间
         */
        void onCountDown(long curTime, long lastTime);
    }
}
