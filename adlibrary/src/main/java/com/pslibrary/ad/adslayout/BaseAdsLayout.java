package com.pslibrary.ad.adslayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pingstart.adsdk.model.BaseNativeAd;
import com.pingstart.mobileads.AdMobAdvanceNativeAd;
import com.pslibrary.ad.R;
import com.pslibrary.ad.imageloader.FrescoUtils;
import com.solo.ads.pslibrary.common.BaseSoloAdsManager;

/**
 * Created by zk on 17-5-23.
 */

public abstract class BaseAdsLayout {
    protected Context mContext;
    protected boolean isCoverBorderShow;
    protected BaseNativeAd ad;
    protected BaseSoloAdsManager soloAdsManager;

    public BaseAdsLayout(BaseNativeAd ad, BaseSoloAdsManager soloAdsManager, Context context) {
        this.mContext = context;
        this.ad = ad;
        this.soloAdsManager = soloAdsManager;
    }

    public abstract void setParent(ViewGroup parent,int baseResId, int mNatvieResId, LayoutInflater layoutInflater);

    protected abstract void registerNativeView(View view, BaseNativeAd baseNativeAd);

    //    common_admob_content_ad_view
    protected abstract void initPopulateAdView(BaseNativeAd baseNativeAd, ViewGroup view);


    public void setCoverBorderShow(boolean coverBorderShow) {
        isCoverBorderShow = coverBorderShow;
    }

    protected void setSimpleDraweeView(SimpleDraweeView view, String url, Context mContext) {
        if (isCoverBorderShow) {
            FrescoUtils.ImageLoadModel imgModel = new FrescoUtils.ImageLoadModel();
            imgModel.mContext = mContext;
            imgModel.mUrl = url;
            imgModel.mTargetView = view;
            imgModel.mBorderColorId = R.color.common_round_border_color;
            imgModel.mBorderWidthId = R.dimen.common_round_corner;
            imgModel.mFailureImageId = R.drawable.common_border_normal_bg;
            imgModel.mPlaceHolderImageId = R.drawable.common_border_normal_bg;
            imgModel.mScaleType = ScalingUtils.ScaleType.CENTER_CROP;
            FrescoUtils.loadRectangleImg(imgModel);
        } else {
            view.setImageURI(url);
        }
    }

}
