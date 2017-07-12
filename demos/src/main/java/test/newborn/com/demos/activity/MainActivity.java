package test.newborn.com.demos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import test.newborn.com.demos.R;
import test.newborn.com.demos.utils.CountDownTimer;
import test.newborn.com.demos.utils.CountDownTimerImpl;

public class MainActivity extends AppCompatActivity {

    EditText mTotal;
    EditText mPeriod;
    EditText mDelay;
    private CountDownTimer mTimer = CountDownTimerImpl.getInstance();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mTotal = (EditText) findViewById(R.id.et_total);
        mPeriod = (EditText) findViewById(R.id.et_period);
        mDelay = (EditText) findViewById(R.id.et_delay);
    }

    private void initData() {
        reset();
    }

    private void reset() {
        mTimer.reset(Long.valueOf(mDelay.getText().toString()), Long.valueOf(mPeriod.getText().toString()),
                Long.valueOf(mTotal.getText().toString()), new CountDownTimer.OnCountDownListener() {
                    @Override
                    public void onCountDown(long curTime, long lastTime) {
                        System.out.println("-----curTime----" + curTime + "-----lastTime----" + lastTime);
                    }
                });
    }

    public void onStart(View view) {
        mTimer.start();
    }

    public void onPause(View view) {
        mTimer.pause();
        long curTime = mTimer.getCurTime();
        System.out.println("------curTime-----" + curTime);
    }

    public void onReset(View view) {
        reset();
    }

    public void onJumpPageTest(View view) {
        jump(TestShimmerActivity.class);
    }

    /**
     * 跳转到GreenDao测试页面
     *
     * @param view
     */
    public void onJumpToGreenDao(View view) {
        jump(GreenDaoActivity.class);
    }


    private void jump(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 跳转时间轴页面
     *
     * @param view
     */
    public void onJumpToTimePivate(View view) {
        jump(TimePivoteActivity.class);

    }

    public void onJumpPageBSheet(View view) {
        jump(BottomSheetActivity.class);
    }
}
