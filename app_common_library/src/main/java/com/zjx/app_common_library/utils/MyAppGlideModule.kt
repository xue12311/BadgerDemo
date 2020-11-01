package com.zjx.app_common_library.utils

import android.content.Context
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.zjx.app_common_library.constant.AppConstant
import java.io.File

@GlideModule
class MyAppGlideModule: AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        /**
         * DiskCacheStrategy.NONE： 表示不缓存任何内容。
         * DiskCacheStrategy.DATA： 表示只缓存原始图片。
         * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
         * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
         * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
         */
        builder.setDefaultRequestOptions(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
        //缓存大小(500 MB)
        val DISK_CACHE_SIZE = 500 * 1024 * 1024.toLong()
        //设置磁盘缓存保存在自己指定的目录下，且指定缓存大小
        builder.setDiskCache(
            DiskLruCacheFactory(File(Utils.getApp().getApplicationContext().getExternalCacheDir(),
                AppConstant.APP_IMAGE_CACHE_DIR).getAbsolutePath(), DISK_CACHE_SIZE)
        )
        val requestOptions = RequestOptions()
//        //加载中占位图
//        requestOptions.placeholder(R.mipmap.nav_top);
//        //错误占位图
//        requestOptions.error(R.mipmap.icon_photo_loading_failed)
//        //null 占位图
//        requestOptions.fallback(R.mipmap.icon_photo_loading_failed)
        builder.setDefaultRequestOptions(requestOptions)
    }
}