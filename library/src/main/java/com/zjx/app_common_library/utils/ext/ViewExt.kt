package com.zjx.app_common_library.utils.ext

import android.view.View

/**
 * 显示 View
 */
fun View.onShowView() {
    this.onShowView(true)
}

/**
 * 显示 View
 * @param isVisible Boolean true : VISIBLE , false : INVISIBLE
 */
fun View.onShowView(isVisible: Boolean) {
    if (isVisible) {
        this.setVisibility(View.VISIBLE)
    } else {
        this.setVisibility(View.INVISIBLE)
    }
}

/**
 * 隐藏 View
 * @receiver View
 */
fun View.onHideView() {
    this.setVisibility(View.GONE)
}
