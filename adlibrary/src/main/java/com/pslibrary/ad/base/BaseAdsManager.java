package com.pslibrary.ad.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.pingstart.adsdk.model.BaseNativeAd;

/**
 * Created by zk on 17-5-23.
 */

public abstract class BaseAdsManager {

    protected Context mContext;

    protected boolean isNeedPreLoadAd = true;

    public abstract void registerNativeView(View view, BaseNativeAd baseNativeAd);

    public abstract void destroy();

    public abstract void inflateAd(BaseNativeAd baseNativeAd, ViewGroup parent, int mNatvieResId);


    public void removeLayoutParent(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }
}
