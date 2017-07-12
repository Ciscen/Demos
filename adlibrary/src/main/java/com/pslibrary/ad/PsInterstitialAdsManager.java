package com.pslibrary.ad;

import android.content.Context;
import android.text.TextUtils;

import com.pingstart.adsdk.listener.InterstitialListener;
import com.pingstart.adsdk.mediation.PingStartInterstitial;
import com.pslibrary.ad.data.Constant;
import com.solo.ads.pslibrary.interstitial.SoloInterstitial;
import com.solo.ads.pslibrary.interstitial.SoloInterstitialListener;

/**
 * Created by Messi on 17-1-9.
 */

public class PsInterstitialAdsManager implements SoloInterstitialListener {

    private static PsInterstitialAdsManager sInstance;
    private static String mId;
    private SoloInterstitial mSoloInterstitial;
    private Context mContext;
    private String mSlotId;

    private onInterstitialAdClickListener listener;

    private PsInterstitialAdsManager(Context context, String slotId) {
        mContext = context.getApplicationContext();
        mSlotId = slotId;
    }

    public static PsInterstitialAdsManager getInstance(Context context, String slotId) {
        if (sInstance == null || !TextUtils.equals(mId, slotId)) {
            sInstance = new PsInterstitialAdsManager(context, slotId);
            mId = slotId;
        }
        return sInstance;
    }

    public void loadAd() {
        PsDebugLogger.d(Constant.TAG, "start loadAd :" + mSlotId);
        mSoloInterstitial = SoloInterstitial.getInstance();
        mSoloInterstitial.loadAds(mContext, mSlotId, this);
    }

    public void destroy() {
        if (mSoloInterstitial != null) {
            mSoloInterstitial.destroy();
            mSoloInterstitial = null;
        }
    }

    public void setInterstitialAdClickListener(onInterstitialAdClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAdsClosed() {
        PsDebugLogger.d(Constant.TAG, "onAdClosed :" + mSlotId);
        if (listener != null) {
            listener.onAdClose();
        }
    }

    @Override
    public void onAdsLoaded() {
        PsDebugLogger.d(Constant.TAG, "onAdLoaded success:" + mSlotId);
        if (listener != null) {
            listener.onAdLoaded();
        }
    }

    @Override
    public void onAdsClick() {
        PsDebugLogger.d(Constant.TAG, "onAdClicked :" + mSlotId);
        if (listener != null) {
            listener.onAdClick();
        }
    }

    @Override
    public void onLoadAdsError(String s) {
        PsDebugLogger.d(Constant.TAG, "onAdError :" + mSlotId + ":" + s);
        if (listener != null) {
            listener.onAdLoadError();
        }
    }

    public void showAd() {
        if (isShowAd()) {
            mSoloInterstitial.show();
        }
    }

    public boolean isShowAd() {
        return mSoloInterstitial != null;
    }

    public interface onInterstitialAdClickListener {

        void onAdClose();

        void onAdLoaded();

        void onAdClick();

        void onAdLoadError();

    }

}
