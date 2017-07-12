package com.pslibrary.ad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.MediaView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.pingstart.adsdk.model.BaseNativeAd;
import com.pingstart.mobileads.AdMobAdvanceNativeAd;
import com.pingstart.mobileads.FacebookNativeAd;
import com.pslibrary.ad.adslayout.AdmobContentBiz;
import com.pslibrary.ad.adslayout.AdmobInstallBiz;
import com.pslibrary.ad.adslayout.FbMediaViewBiz;
import com.pslibrary.ad.adslayout.PsNativeBiz;
import com.pslibrary.ad.base.AdLayoutListener;
import com.pslibrary.ad.base.BaseAdsManager;
import com.pslibrary.ad.data.Constant;
import com.solo.ads.pslibrary.common.BaseSoloAdsManager;
import com.solo.ads.pslibrary.multiplenative.SoloMultiple;
import com.solo.ads.pslibrary.nativeads.SoloNative;

import java.util.List;


/**
 * Created by Messi on 17-1-9.
 */

public class PsNativeManager extends BaseAdsManager {
    private static final String TAG = PsNativeManager.class.getName();
    private static PsNativeManager sInstance;
    private BaseSoloAdsManager soloAdsManager;
    private LayoutInflater mlayoutInflater;
    private AdLayoutListener listener;


    private PsNativeManager(Context context) {
        mContext = context.getApplicationContext();
        mlayoutInflater = LayoutInflater.from(mContext);
    }

    public static PsNativeManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PsNativeManager(context);
        }
        return sInstance;
    }

    /**
     * @param soloAdsManager You must use the PSLibrary Class, Such as {@SoloNative},{@SoloMultiple}.
     *                       Don`t use {@PingStartNative} or {@PingStartMultiple}.
     * @return
     */
    public PsNativeManager setSoloAdsManager(BaseSoloAdsManager soloAdsManager) {
        this.soloAdsManager = soloAdsManager;
        return sInstance;
    }

    @Override
    public void registerNativeView(View view, BaseNativeAd baseNativeAd) {
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
    public void destroy() {
        isNeedPreLoadAd = true;
        if (soloAdsManager != null) {
            soloAdsManager.destroy();
            soloAdsManager = null;
        }
    }

    public BaseAdsManager setAdLayoutListener(AdLayoutListener listener) {
        this.listener = listener;
        return sInstance;
    }

    /**
     * This method is used to inflate Ads`s layout. Ha Ha!
     *
     * @param baseNativeAd 广告实体
     * @param mNatvieResId 原生布局
     * @param mNatvieResId Admob 内容布局
     * @param mNatvieResId Admob 安装布局
     * @return
     */
    @Override
    public void inflateAd(BaseNativeAd baseNativeAd, ViewGroup parent, int mNatvieResId) {
        String adType = null;
        String workName = baseNativeAd.getNetworkName();

        //add ads`s view into parent
        if (parent != null) {


            try {
                if (workName != null && workName.equalsIgnoreCase(Constant.AD_TYPE_FACEBOOK)) {
                    FbMediaViewBiz admobContentBiz = new FbMediaViewBiz(baseNativeAd, soloAdsManager, mContext);
                    admobContentBiz.setParent(parent, R.layout.common_facebook_ad_view, mNatvieResId, mlayoutInflater);

                    adType = workName;
                } else {
                    if (workName != null && workName.equalsIgnoreCase(Constant.AD_TYPE_ADMOB)) {
                        adType = ((AdMobAdvanceNativeAd) baseNativeAd).getAdType();

                        if (adType.equalsIgnoreCase(Constant.AD_TYPE_CONTENT)) {
                            AdmobContentBiz admobContentBiz = new AdmobContentBiz(baseNativeAd, soloAdsManager, mContext);
                            admobContentBiz.setParent(parent, R.layout.common_admob_content_ad_view ,mNatvieResId, mlayoutInflater);

                        } else if (adType.equalsIgnoreCase(Constant.AD_TYPE_INSTALL)) {
                            AdmobInstallBiz admobInstallBiz = new AdmobInstallBiz(baseNativeAd, soloAdsManager, mContext);
                            admobInstallBiz.setParent(parent, R.layout.common_admob_install_ad_view, mNatvieResId, mlayoutInflater);

                        }
                    } else {
                        adType = Constant.AD_PS;
                        PsNativeBiz psNativeBiz = new PsNativeBiz(baseNativeAd, soloAdsManager, mContext);
                        psNativeBiz.setParent(parent, R.layout.common_ps_ad_view, mNatvieResId, mlayoutInflater);

                    }
                }
            } catch (Exception e) {
                if (listener != null) {
                    PsDebugLogger.e(TAG, e.getMessage());
                    listener.onInflatError(e.getMessage());
                }
            } finally {
                if (listener != null) {
                    PsDebugLogger.d(TAG, Constant.INFLAT_SECUSS);
                    listener.onInflatSucess(adType);
                }
            }
        } else {
            PsDebugLogger.e(TAG, Constant.PARENT_NULL_ERROR);
        }

        PsDebugLogger.i(Constant.TAG, "onAd type:" + adType);
        PsDebugLogger.i(Constant.TAG, "onAd workName:" + workName);
    }

}
