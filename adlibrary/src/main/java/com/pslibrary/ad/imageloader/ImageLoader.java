package com.pslibrary.ad.imageloader;

import android.content.res.Resources;
import android.widget.ImageView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;


/**
 * Created by Anthony on 2016/3/3.
 * Class Note:
 * encapsulation of ImageView,Build Pattern used
 */
public class ImageLoader {

    public static final String TYPE_FILE = "file://";
    public static final String TYPE_RES = "res://com.solo.security/";

    private int type;  //类型 (大图，中图，小图)
    private String url; //需要解析的url
    private int placeHolder; //当没有成功加载的时候显示的图片
    private ImageView imgView; //ImageView的实例
    private int wifiStrategy;//加载策略，是否在wifi下才加载
    private GenericDraweeHierarchy hierarchy;

    private ImageLoader(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;
        this.hierarchy = builder.hierarchy;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    public GenericDraweeHierarchy getHierarchy() {
        return hierarchy;
    }

    public static class Builder {
        private int type;
        private String url;
        private int placeHolder;
        private int failure;
        private ImageView imgView;
        private int wifiStrategy;
        private GenericDraweeHierarchy hierarchy;

        public Builder() {
            this.type = ImageLoaderUtil.PIC_SMALL;
            this.url = "";
            //this.placeHolder = R.mipmap.account_default_avatar;
            //this.failure = R.mipmap.account_default_avatar;
            this.imgView = null;
            this.wifiStrategy = ImageLoaderUtil.LOAD_STRATEGY_NORMAL;
            this.hierarchy = null;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder failure(int failure) {
            this.failure = failure;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
            return this;
        }

        public Builder strategy(int strategy) {
            this.wifiStrategy = strategy;
            return this;
        }

        public Builder hierarchy(Resources resources, int borderColor, int borderWidth) {
            this.hierarchy = new GenericDraweeHierarchyBuilder(resources)
                    .setPlaceholderImage(placeHolder)
                    .setFailureImage(failure)
                    .setRoundingParams(RoundingParams.asCircle().setBorder(resources.getColor(borderColor), resources.getDimension(borderWidth)))
                    .build();

            return this;
        }

        public Builder hierarchyCircle(Resources resources, int borderColor, int borderWidth, ScalingUtils.ScaleType scaleType) {
            this.hierarchy = new GenericDraweeHierarchyBuilder(resources)
                    .setPlaceholderImage(placeHolder)
                    .setFailureImage(failure)
                    .setRoundingParams(RoundingParams.asCircle().setBorder(resources.getColor(borderColor), resources.getDimension(borderWidth)))
                    .setPlaceholderImageScaleType(scaleType)
                    .setFailureImageScaleType(scaleType)
                    .setActualImageScaleType(scaleType)
                    .build();
            return this;
        }

        public Builder hierarchyRectangle(Resources resources, int borderColor, int borderWidth, ScalingUtils.ScaleType scaleType) {
            RoundingParams params = new RoundingParams();
            this.hierarchy = new GenericDraweeHierarchyBuilder(resources)
                    .setPlaceholderImage(placeHolder)
                    .setFailureImage(failure)
                    .setRoundingParams(params.setRoundAsCircle(false).setBorder(resources.getColor(borderColor), resources.getDimension(borderWidth)))
                    .setPlaceholderImageScaleType(scaleType)
                    .setFailureImageScaleType(scaleType)
                    .setActualImageScaleType(scaleType)
                    .build();
            return this;
        }

        public Builder hierarchy(Resources resources, ScalingUtils.ScaleType scaleType) {
            this.hierarchy = new GenericDraweeHierarchyBuilder(resources).setActualImageScaleType(scaleType).build();
            return this;
        }

        public Builder hierarchy(Resources resources) {
            this.hierarchy = new GenericDraweeHierarchyBuilder(resources)
                    .setPlaceholderImage(placeHolder)
                    .setFailureImage(failure)
                    .setRoundingParams(RoundingParams.asCircle())
                    .build();
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }

    }
}
