package com.zjx.app_common_library.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

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

    /**
     * @param block     协程代码块，运行在UI线程
     * @param onError   异常回调，运行在UI线程
     * @param onStart   协程开始回调，运行在UI线程
     * @param onFinally 协程结束回调，不管成功/失败，都会回调，运行在UI线程
     */
    fun launch(
            block: suspend CoroutineScope.() -> Unit,
            onError: ((Throwable) -> Unit)? = null,
            onStart: (() -> Unit)? = null,
            onFinally: (() -> Unit)? = null
    ): Job {
        return viewModelScope.launch {
            try {
                coroutineScope {
                    onStart?.invoke()
                    block()
                }
            } catch (e: Throwable) {
                if (onError != null && isActive) {
                    try {
                        onError(e)
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                } else {
                    e.printStackTrace()
                }
            } finally {
                onFinally?.invoke()
            }
        }
    }

}