package com.pslibrary.ad.adslayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
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

public class AdmobInstallBiz extends BaseAdsLayout {

    private static final String TAG = AdmobInstallBiz.class.getName();

    public AdmobInstallBiz(BaseNativeAd ad, BaseSoloAdsManager soloAdsManager, Context context) {
        super(ad, soloAdsManager, context);
    }

    @Override
    public void setParent(ViewGroup parent, int baseResId, int resId, LayoutInflater layoutInflater) {
        //add ads`s view into parent
        if (parent != null) {
            NativeAppInstallAdView baseView = (NativeAppInstallAdView) layoutInflater.inflate(baseResId, parent, false);
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
        NativeAppInstallAd nativeAppInstallAd = adMobAdvanceNativeAd.getNativeInstallAd();
        NativeAppInstallAdView adView = (NativeAppInstallAdView) view;

        adView.setHeadlineView(view.findViewById(R.id.common_ad_title_text));
        adView.setImageView(view.findViewById(R.id.common_ad_cover_img));
        adView.setBodyView(view.findViewById(R.id.common_ad_content_text));
        adView.setCallToActionView(view.findViewById(R.id.common_ad_action_text));
        adView.setIconView(view.findViewById(R.id.common_ad_icon));
        if (adView.getHeadlineView() != null)
            ((TextView) adView.getHeadlineView()).setText(nativeAppInstallAd.getHeadline().toString());
        if (adView.getBodyView() != null) {
            ((TextView) adView.getBodyView()).setText(nativeAppInstallAd.getBody().toString());
        }
        if (adView.getCallToActionView() != null) {
            ((TextView) adView.getCallToActionView()).setText(nativeAppInstallAd.getCallToAction().toString());
        }
        NativeAd.Image icon = nativeAppInstallAd.getIcon();
        if (adView.getIconView() != null) {
            if (icon == null) {
                adView.getIconView().setVisibility(View.INVISIBLE);
            } else {
                ImageView imageView = (ImageView) adView.getIconView();
                if (imageView instanceof SimpleDraweeView) {
                    imageView.setImageURI(icon.getUri());
                } else {
                    imageView.setImageDrawable(null);
                    imageView.setImageDrawable(icon.getDrawable());
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        }
        List<NativeAd.Image> images = nativeAppInstallAd.getImages();
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
    }
}