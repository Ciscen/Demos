package com.pslibrary.ad.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


/**
 * Created by luxin on 16-7-5.
 */
public class FrescoUtils {
    public static void showThumb(Uri uri, SimpleDraweeView draweeView) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(144, 144))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        draweeView.setController(controller);
    }

    public static void loadBlurImg(Context context, String url, SimpleDraweeView targetIv) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setPostprocessor(new IterativeBoxBlurPostProcessor(70))// iterations, blurRadius
                .build();

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().setImageRequest(request)
                .setOldController(targetIv.getController())
                .build();

        targetIv.setController(controller);
    }

    public static void loadBlurImgWithCallback(Context context, String url, SimpleDraweeView targetIv, ImageLoadCallback callback) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setPostprocessor(new IterativeBoxBlurPostProcessor(70))// iterations, blurRadius
                .build();

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().setImageRequest(request)
                .setOldController(targetIv.getController())
                .setControllerListener(new ImageLoadControllerListener(callback))
                .build();

        targetIv.setController(controller);
    }

    /**
     * 支持圆形,默认图片,加载失败图片
     */
    public static void loadCircleImg(ImageLoadModel model) {
        if (checkImageParams(model)) {
            if (TextUtils.isEmpty(model.mUrl)) {
                model.mUrl = "";
            }
            ImageLoader.Builder builder = new ImageLoader.Builder()
                    .type(ImageLoaderUtil.PIC_LARGE)
                    .imgView(model.mTargetView)
                    .placeHolder(model.mPlaceHolderImageId)
                    .failure(model.mFailureImageId)
                    .hierarchyCircle(model.mContext.getResources(), model.mBorderColorId, model.mBorderWidthId, model.mScaleType)
                    .url(model.mUrl);
            ImageLoaderUtil.getInstance().loadImage(model.mContext, builder.build());
        }
    }

    /**
     * 支持矩形,默认图片,加载失败图片
     */
    public static void loadRectangleImg(ImageLoadModel model) {
        if (checkImageParams(model)) {
            if (TextUtils.isEmpty(model.mUrl)) {
                model.mUrl = "";
            }
            ImageLoader.Builder builder = new ImageLoader.Builder()
                    .type(ImageLoaderUtil.PIC_LARGE)
                    .imgView(model.mTargetView)
                    .placeHolder(model.mPlaceHolderImageId)
                    .failure(model.mFailureImageId)
                    .hierarchyRectangle(model.mContext.getResources(), model.mBorderColorId, model.mBorderWidthId, model.mScaleType)
                    .url(model.mUrl);
            ImageLoaderUtil.getInstance().loadImage(model.mContext, builder.build());
        }
    }

    public static boolean checkImageParams(ImageLoadModel model) {
        return !(model == null || model.mContext == null || model.mPlaceHolderImageId <= 0 ||
                model.mTargetView == null || model.mScaleType == null || model.mBorderColorId <= 0
                || model.mFailureImageId <= 0 || model.mBorderWidthId <= 0);
    }

    public static void loadImgWithResize(Uri uri, SimpleDraweeView draweeView, int width, int height) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).
                setResizeOptions(new ResizeOptions(width, height)).setProgressiveRenderingEnabled(true)
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder().
                setImageRequest(request).setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }


    public static void downloadBitmapFromUrl(Context context, String url, FrescoBitmapDownloadCallback callback) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Uri imageUri = Uri.parse(url);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(imageUri)
                .setRequestPriority(Priority.HIGH)
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .build();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
        dataSource.subscribe(new DefaultBitmapDataSubscriber(callback, dataSource), CallerThreadExecutor.getInstance());
    }

    public static void clearSingleUriCache(String localUrl) {
        Fresco.getImagePipeline().evictFromMemoryCache(Uri.parse(localUrl));
        Fresco.getImagePipelineFactory().getMainFileCache().remove(new SimpleCacheKey(Uri.parse(localUrl).toString()));
        Fresco.getImagePipelineFactory().getSmallImageFileCache().remove(new SimpleCacheKey(Uri.parse(localUrl).toString()));
    }

    public interface FrescoBitmapDownloadCallback {
        void onDownloadSuccess(Bitmap bitmap);

        void onDownloadFailure();
    }

    public interface ImageLoadCallback {
        void onLoadFinished(ImageInfo imageInfo);
    }

    private static class DefaultBitmapDataSubscriber extends BaseBitmapDataSubscriber {
        private FrescoBitmapDownloadCallback mCallback;
        private DataSource mDataSource;

        public DefaultBitmapDataSubscriber(FrescoBitmapDownloadCallback callback, DataSource dataSource) {
            this.mCallback = callback;
            mDataSource = dataSource;
        }

        @Override
        protected void onNewResultImpl(Bitmap bitmap) {
            if (bitmap != null && !bitmap.isRecycled() && mCallback != null) {
                mCallback.onDownloadSuccess(bitmap);
            }
            if (mDataSource != null) {
                mDataSource.close();
            }
        }

        @Override
        protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
            mCallback.onDownloadFailure();
            if (mDataSource != null) {
                mDataSource.close();
            }
        }
    }

    private static class ImageLoadControllerListener extends BaseControllerListener<ImageInfo> {
        private ImageLoadCallback mCallback;

        public ImageLoadControllerListener(ImageLoadCallback callback) {
            this.mCallback = callback;
        }

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            if (mCallback != null) {
                mCallback.onLoadFinished(imageInfo);
            }
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            if (mCallback != null) {
                mCallback.onLoadFinished(null);
            }
        }
    }

    public static class ImageLoadModel {
        public Context mContext;
        public String mUrl;
        public SimpleDraweeView mTargetView;
        public int mBorderColorId;
        public int mBorderWidthId;
        public int mPlaceHolderImageId;
        public int mFailureImageId;
        public ScalingUtils.ScaleType mScaleType;
        public ImageLoadCallback mCallback;
    }

}
