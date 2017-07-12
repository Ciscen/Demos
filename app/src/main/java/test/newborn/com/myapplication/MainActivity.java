package test.newborn.com.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.tts.TextToSpeechBeta;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeechBeta.OnInitListener {

    Button bt;
    Button bt2;
    Button bt3;
    EditText et;
    TextToSpeechBeta mTts;
    Context context;
    View.OnClickListener mListener = new OnClickLIstener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = (Button) findViewById(R.id.button);
        bt2 = (Button) findViewById(R.id.button2);
        bt3 = (Button) findViewById(R.id.button3);
        et = (EditText) findViewById(R.id.et);
        context = this;
        initListener();
        log(LanguageUtil.getTTSString(this, R.string.aaa));
        log(getResources().getString(R.string.action_settings));
    }

    private void initListener() {
        bt.setOnClickListener(mListener);
        bt2.setOnClickListener(mListener);
        bt3.setOnClickListener(mListener);
    }

    private class OnClickLIstener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button:
                    String content = et.getText().toString();
                    if (mTts != null) {
                        mTts.setPitch(1f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                        mTts.setSpeechRate(0.9f);
                        mTts.speak(content, TextToSpeech.QUEUE_FLUSH, null);
                    }
                    break;
                case R.id.button2:
                    boolean googleTTSInstalled = LanguageUtil.isGoogleTTSInstalled(MainActivity.this);
                    log(googleTTSInstalled + "");
                    checkTTS();
                    break;
                case R.id.button3:
                    startActivity(new Intent("com.android.settings.TTS_SETTINGS"));
//
//                    // 下载TTS对应的资源
//                    Intent dataIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.tts.local.voicepack.ui.VoiceDataInstallActivity");
//
//                    // LAUNCHER Intent
//                    Intent intent = new Intent(Intent.ACTION_MAIN);
//
//                    // 设置ComponentName参数1:packagename参数2:MainActivity路径
//                    ComponentName cn = new ComponentName("com.google.android.tts", "com.google.android.tts.local.voicepack.ui.VoiceDataInstallActivity");
//                    intent.setComponent(cn);
//                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 检查TTS是否可以使用
     */
    private void checkTTS() {
        Intent in = new Intent();
        in.setAction(TextToSpeechBeta.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(in, 100);
    }


    /**
     * 安装语音相关资源包
     */
    private void installTTS() {
        AlertDialog.Builder alertInstall = new AlertDialog.Builder(this)
                .setTitle("缺少语音包")
                .setMessage("下载语音包")
                .setPositiveButton("去下载",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // 下载eyes-free的语音数据包
//                                String ttsDataUrl = "http://eyes-free.googlecode.com/files/tts_3.1_market.apk";
                                String ttsDataUrl = "https://play.google.com/store/apps/details?id=com.google.android.tts";
                                Uri ttsDataUri = Uri.parse(ttsDataUrl);
                                Intent ttsIntent = new Intent(
                                        Intent.ACTION_VIEW, ttsDataUri);
                                startActivity(ttsIntent);
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertInstall.create().show();
    }

    private void log(String string) {
        Log.d("textToSpeech----->", string);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            switch (resultCode) {
                case TextToSpeechBeta.Engine.CHECK_VOICE_DATA_PASS:
                    mTts = new TextToSpeechBeta(context, this);
                    Toast.makeText(this, "恭喜您，TTS可用", Toast.LENGTH_SHORT).show();
                    break;
                case TextToSpeechBeta.Engine.CHECK_VOICE_DATA_BAD_DATA:// 发音数据已经损坏
                case TextToSpeechBeta.Engine.CHECK_VOICE_DATA_MISSING_DATA: // 需要的语音数据已丢失
                case TextToSpeechBeta.Engine.CHECK_VOICE_DATA_MISSING_VOLUME: // 发音数据丢失
                    // 下载TTS对应的资源
                    Intent dataIntent = new Intent(
                            TextToSpeechBeta.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(dataIntent);
                    break;
                case TextToSpeechBeta.Engine.CHECK_VOICE_DATA_FAIL:
                    // 发音失败
                    break;

            }
        }
    }

    @Override
    public void onInit(int arg0, int arg1) {
//        if (arg1 == -1) {
        log("arg0==" + arg0);
        log("arg1==" + arg1);
//            // 提示安装所需要的数据
//            installTTS();
//
//        } else {
        // 完成TTS的初始化
        if (arg0 == TextToSpeechBeta.SUCCESS) {
            // 设置TTS引擎，com.google.tts即eSpeak支持的语言包含中文
            String defaultEngineExtended = mTts.getDefaultEngineExtended();
            log(defaultEngineExtended + "------>");
            mTts.setEngineByPackageNameExtended("com.google.android.tts");
//            mTts.setEngineByPackageNameExtended("com.svox.pico");
            // 设置发音语言Locale.CHINA
            int result = mTts.setLanguage(Locale.CHINA);
            // 检查语言是否可用
            if (result == TextToSpeechBeta.LANG_MISSING_DATA
                    || result == TextToSpeechBeta.LANG_NOT_SUPPORTED) {
                bt.setEnabled(false);
                Toast.makeText(context, "语言功能不可用", Toast.LENGTH_SHORT).show();
            } else {
                bt.setEnabled(true);
            }
        } else {
            Toast.makeText(context, "TTS初始化失败", Toast.LENGTH_SHORT).show();
        }
//        }
    }
}
