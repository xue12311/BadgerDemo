package com.zjx.app_common_library

import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPStaticUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.zjx.app_common_library.base.BaseApp


//自定义Application
open class App : BaseApp() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        SPStaticUtils.setDefaultSPUtils(SPUtils.getInstance(AppUtils.getAppPackageName()))
//        LogUtils.getConfig()
//            //设置 log 总开关
//            .setLogSwitch(BuildConfig.DEBUG)
//            //设置 log 控制台开关
//            .setConsoleSwitch(BuildConfig.DEBUG)
//            //设置 log 全局 tag
//            .setGlobalTag("zjx")
//        //网络请求初始化
//        RxHttpManager.init()
    }
}