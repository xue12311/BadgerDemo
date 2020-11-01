package com.zjx.app_common_library.utils

import com.blankj.utilcode.util.ActivityUtils

object CurrencyUtils {
    /**
     * 退出应用
     */
    fun QuitApp() {
        //退出应用
        ActivityUtils.finishAllActivities()
        System.exit(0)
    }
}