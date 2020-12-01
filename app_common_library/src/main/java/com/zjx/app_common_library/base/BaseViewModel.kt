package com.zjx.app_common_library.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/12
 * 描述　: ViewModel的基类
 */
open class BaseViewModel : ViewModel() {
    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) {
        //viewModelScope 默认是 Dispatchers.Main
        viewModelScope.launch {
            block()
        }
    }

    fun launchIO(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    block()
                }
            }
        }
    }
}