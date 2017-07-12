package com.pslibrary.ad.adslayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.pingstart.adsdk.model.BaseNativeAd;
import com.pingstart.mobileads.AdMobAdvanceNativeAd;
import com.pslibrary.ad.PsDebugLogger;
import com.pslibrary.ad.R;
import com.pslibrary.ad.data.Constant;
import com.solo.ads.pslibrary.common.BaseSoloAdsManager;
import com.solo.ads.pslibrary.multiplenative.SoloMultiple;
import com.solo.ads.pslibrary.nativeads.SoloNative;

import java.util.List;

/**
 * Created by zk on 17-5-23.
 */

public class AdmobContentBiz extends BaseAdsLayout {

    private static final String TAG = AdmobContentBiz.class.getName();

    public AdmobContentBiz(BaseNativeAd ad, BaseSoloAdsManager soloAdsManager, Context context) {
        super(ad, soloAdsManager, context);
    }

    @Override
    public void setParent(ViewGroup parent, int baseResId, int resId, LayoutInflater layoutInflater) {
        //add ads`s view into parent
        if (parent != null) {
            NativeContentAdView baseView = (NativeContentAdView) layoutInflater.inflate(baseResId, parent, false);
            parent.addView(baseView);

            ViewGroup containView = (ViewGroup) layoutInflater.inflate(resId, baseView, false);
            baseView.addView(containView);

            initPopulateAdView(ad, baseView);
            //TODO register Ads`s View
            if (baseView.getCallToActionView() != null) {
                registerNativeView(baseView.getCallToActionView(), ad);
            } else {
                registerNativeView(baseView, ad);
            }
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
        AdMobAdvanceNativeAd adMobAdvanceNativeAd = (AdMobAdvanceNativeAd) baseNativeAd;
        NativeContentAd nativeContentAd = adMobAdvanceNativeAd.getNativeContentAd();
        NativeContentAdView adView = (NativeContentAdView) view;

        adView.setHeadlineView(adView.findViewById(R.id.common_ad_title_text));
        adView.setImageView(adView.findViewById(R.id.common_ad_cover_img));
        adView.setBodyView(adView.findViewById(R.id.common_ad_content_text));
        adView.setCallToActionView(adView.findViewById(R.id.common_ad_action_text));
        adView.setLogoView(adView.findViewById(R.id.common_ad_icon));
        adView.setAdvertiserView(adView.findViewById(R.id.common_ad_flag_img));
        if (adView.getHeadlineView() != null)
            ((TextView) adView.getHeadlineView()).setText(nativeContentAd.getHeadline().toString());
        if (adView.getBodyView() != null) {
            ((TextView) adView.getBodyView()).setText(nativeContentAd.getBody().toString());
        }
        if (adView.getCallToActionView() != null) {
            ((TextView) adView.getCallToActionView()).setText(nativeContentAd.getCallToAction().toString());
        }
        List<NativeAd.Image> images = nativeContentAd.getImages();

        if (images.size() > 0) {
            if (adView.getImageView() != null) {
                ImageView imageView = (ImageView) adView.getImageView();
                if (imageView instanceof SimpleDraweeView) {
                    setSimpleDraweeView((SimpleDraweeView) imageView, images.get(0).getUri().toString(), mContext);
                } else {
                    imageView.setImageDrawable(null);
                    imageView.setImageDrawable(images.get(0).getDrawable());
                }
            }
        }
        NativeAd.Image logoImage = nativeContentAd.getLogo();

        if (adView.getLogoView() != null) {
            if (logoImage == null) {
                adView.getLogoView().setVisibility(View.INVISIBLE);
            } else {
                ImageView imageView = (ImageView) adView.getLogoView();
                if (imageView instanceof SimpleDraweeView) {
                    imageView.setImageURI(logoImage.getUri());
                } else {
                    imageView.setImageDrawable(null);
                    imageView.setImageDrawable(logoImage.getDrawable());
                    adView.getLogoView().setVisibility(View.VISIBLE);
                }
            }
        }
    }
}