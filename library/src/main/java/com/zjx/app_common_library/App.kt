package com.zjx.app_common_library

import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.*
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
    }

    /**
     * 初始化 SharedPreferences
     * @param spName String SharedPreferences 实例名称
     */
    open fun initSPUtils(spName: String = AppUtils.getAppPackageName()) {
        SPStaticUtils.setDefaultSPUtils(SPUtils.getInstance(spName))
    }

    /**
     * 初始化
     * @param mLogSwitch Boolean log 总开关
     * @param mConsoleSwitch Boolean log 控制台开关
     * @param mLogTag String log 全局 tag
     */
    open fun initLogUtils(
        mLogSwitch: Boolean = BuildConfig.DEBUG,
        mConsoleSwitch: Boolean = BuildConfig.DEBUG,
        mLogTag: String = AppUtils.getAppName()
    ) {
        LogUtils.getConfig()
            //设置 log 总开关
            .setLogSwitch(mLogSwitch)
            //设置 log 控制台开关
            .setConsoleSwitch(mConsoleSwitch)
            //设置 log 全局 tag
            .setGlobalTag(mLogTag)
    }
}