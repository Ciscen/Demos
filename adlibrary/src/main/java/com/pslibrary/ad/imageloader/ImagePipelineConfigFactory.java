package com.pslibrary.ad.imageloader;

import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Sets;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import okhttp3.OkHttpClient;

/**
 * Created by pandajoy on 16-6-30.
 */
public class ImagePipelineConfigFactory {

    public static final int MAX_DISK_CACHE_SIZE = 40 * ByteConstants.MB;
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
    private static final String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";

    private static ImagePipelineConfig sImagePipelineConfig;
    private static ImagePipelineConfig sOkHttpImagePipelineConfig;

    /**
     * Creates config using android http stack as network backend.
     */
    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context);
            configureCaches(configBuilder, context);
            configureLoggingListeners(configBuilder);
            sImagePipelineConfig = configBuilder.build();
        }
        return sImagePipelineConfig;
    }

    /**
     * Creates config using OkHttp as network backed.
     */
    public static ImagePipelineConfig getOkHttpImagePipelineConfig(Context context) {
        if (sOkHttpImagePipelineConfig == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            ImagePipelineConfig.Builder configBuilder = OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient);
            configureCaches(configBuilder, context);
            configureLoggingListeners(configBuilder);
            configBuilder.setDownsampleEnabled(true);
            sOkHttpImagePipelineConfig = configBuilder.build();
        }
        return sOkHttpImagePipelineConfig;
    }

    /**
     * Configures disk and memory cache not to exceed common limits
     */
    private static void configureCaches(ImagePipelineConfig.Builder configBuilder, Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE,                     // Max entries in the cache
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE,                     // Max length of eviction queue
                Integer.MAX_VALUE);                    // Max cache entry size
        Supplier<MemoryCacheParams> bitmapMemoryCacheParamsSupplier = new Supplier<MemoryCacheParams>() {
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(context.getApplicationContext().getCacheDir())
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)
                .build();
        configBuilder.setBitmapMemoryCacheParamsSupplier(bitmapMemoryCacheParamsSupplier)
                .setMainDiskCacheConfig(diskCacheConfig);
    }

    private static void configureLoggingListeners(ImagePipelineConfig.Builder configBuilder) {
        configBuilder.setRequestListeners(
                Sets.newHashSet((RequestListener) new RequestLoggingListener()));
    }
}
