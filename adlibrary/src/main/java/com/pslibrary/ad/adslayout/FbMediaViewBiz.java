package com.pslibrary.ad.adslayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.MediaView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.pingstart.adsdk.model.BaseNativeAd;
import com.pingstart.mobileads.FacebookNativeAd;
import com.pslibrary.ad.PsDebugLogger;
import com.pslibrary.ad.R;
import com.pslibrary.ad.data.Constant;
import com.solo.ads.pslibrary.common.BaseSoloAdsManager;
import com.solo.ads.pslibrary.multiplenative.SoloMultiple;
import com.solo.ads.pslibrary.nativeads.SoloNative;

/**
 * Created by zk on 17-5-23.
 */

public class FbMediaViewBiz extends BaseAdsLayout {

    private static final String TAG = FbMediaViewBiz.class.getName();

    public FbMediaViewBiz(BaseNativeAd ad, BaseSoloAdsManager soloAdsManager, Context context) {
        super(ad, soloAdsManager, context);
    }

    @Override
    public void setParent(ViewGroup parent, int baseResId, int resId, LayoutInflater layoutInflater) {
        //add ads`s view into parent
        if (parent != null) {
            MediaView baseView = (MediaView) layoutInflater.inflate(baseResId, parent, false);
            baseView.setAutoplay(true);
            parent.addView(baseView);

            ViewGroup containView = (ViewGroup) layoutInflater.inflate(resId, baseView, false);
            baseView.addView(containView);

            FacebookNativeAd nativeAd = (FacebookNativeAd) ad;
            baseView.setNativeAd(nativeAd.getNativeAd());

            initPopulateAdView(ad, containView);
            //TODO register Ads`s View
            registerNativeView(baseView, ad);
        }
    }

    @Override
    protected void registerNativeView(View view, BaseNativeAd baseNativeAd) {
        if (soloAdsManager != null) {
            if (soloAdsManager instanceof SoloNative) {
                (((SoloNative) soloAdsManager)).registerNativeAdView(view);
            } else if (soloAdsManager instanceof SoloMultiple) {
                (((SoloMultiple) soloAdsManager)).registerNativeAdView(baseNativeAd, view);
            }
        } else {
            PsDebugLogger.e(TAG, Constant.SOLO_ADMANAGER_NULL_ERROR);
        }
    }

    @Override
    protected void initPopulateAdView(BaseNativeAd baseNativeAd, ViewGroup view) {
        ImageView flagImgV = (ImageView) view.findViewById(R.id.common_ad_flag_img);
        if(flagImgV!=null)
        flagImgV.setBackgroundResource(R.mipmap.common_ad_icon);

        TextView adTitle = (TextView) view.findViewById(R.id.common_ad_title_text);
        if (adTitle != null) adTitle.setText(baseNativeAd.getTitle());
        TextView adContent = (TextView) view.findViewById(R.id.common_ad_content_text);
        if (adContent != null) adContent.setText(baseNativeAd.getDescription());
        Button adActionText = (Button) view.findViewById(R.id.common_ad_action_text);
        if (adActionText != null) adActionText.setText(baseNativeAd.getAdCallToAction());
        ImageView adIcon = (ImageView) view.findViewById(R.id.common_ad_icon);
        if (adIcon != null) {
            if (adIcon instanceof SimpleDraweeView) {
                ((SimpleDraweeView) adIcon).setImageURI(baseNativeAd.getIconUrl());
            } else {
                adIcon.setImageBitmap(null);
                baseNativeAd.displayIcon(adIcon);
            }
        }
        ImageView adCoverIcon = (ImageView) view.findViewById(R.id.common_ad_cover_img);
        if (adCoverIcon != null) {
            if (adCoverIcon instanceof SimpleDraweeView) {
                setSimpleDraweeView((SimpleDraweeView) adCoverIcon, baseNativeAd.getCoverImageUrl(), mContext);
            } else {
                adCoverIcon.setImageBitmap(null);
                baseNativeAd.displayCoverImage(adCoverIcon);
            }
        }
    }
}