package com.pslibrary.ad.imageloader;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by pandajoy on 16-6-30.
 */
public class FrescoImageLoader implements BaseImageLoader {

    @Override
    public void loadImage(Context ctx, ImageLoader img) {
        SimpleDraweeView view = (SimpleDraweeView) img.getImgView();
        if (img.getHierarchy() != null) {
            view.setHierarchy(img.getHierarchy());
        }
        view.setImageURI(Uri.parse(img.getUrl()));
    }
}
