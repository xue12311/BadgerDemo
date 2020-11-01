package com.zjx.app_common_library.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.rxLifeScope
import androidx.lifecycle.viewModelScope
import com.zjx.app_common_library.network.ErrorInfo
import com.zjx.app_common_library.network.ExceptionHandle
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

    /**
     * 过滤请求结果，其他全抛异常 回调在Viewmodel
     * @param onStart 协程开始回调 UI线程
     * @param block 请求体 UI线程
     * @param success 成功回调 UI线程
     * @param error 失败回调 UI线程
     * @param complete 协程结束回调，不管成功/失败，都会回调 UI线程
     */
    fun <T> launchResultVM(
        onStart: (() -> Unit)? = null,
        block: suspend CoroutineScope.() -> BaseResponse<T>,
        success: ((BaseResponse<T>) -> Unit)? = null,
        error: ((ErrorInfo) -> Unit)? = null,
        complete: (() -> Unit)? = null
    ) {
        rxLifeScope.launch(
            {
                val data = block.invoke(this)
                if (data.isSucces()) {
                    success?.invoke(data)
                } else {
                    error(ExceptionHandle.handleException(data.getErrorCode(),data.getErrorMessage()))
                }
            }, {
                error(ExceptionHandle.handleException(it))
            }, {
                onStart?.invoke()
            }, {
                complete?.invoke()
            }
        )
    }

    /**
     * 过滤请求结果，其他全抛异常 回调在Viewmodel
     * @param block 请求体 UI线程
     * @param success 成功回调 UI线程
     * @param error 失败回调 UI线程
     * @param complete 协程结束回调，不管成功/失败，都会回调 UI线程
     */
    fun <T> launchResultVM(
        block: suspend CoroutineScope.() -> BaseResponse<T>,
        success: (BaseResponse<T>) -> Unit,
        error: (ErrorInfo) -> Unit,
        complete: () -> Unit
    ) {
        rxLifeScope.launch(
            {
                val data = block.invoke(this)
                if (data.isSucces()) {
                    success(data)
                } else {
                    error(ExceptionHandle.handleException(data.getErrorCode(),data.getErrorMessage()))
                }
            }, {
                error(ExceptionHandle.handleException(it))
            }, null, {
                complete()
            }
        )
    }

    /**
     * 过滤请求结果，其他全抛异常 回调在Viewmodel
     * @param block 请求体 UI线程
     * @param success 成功回调 UI线程
     * @param error 失败回调 UI线程
     * @param complete 协程结束回调，不管成功/失败，都会回调 UI线程
     */
    fun <T> launchResultVM(
        block: suspend CoroutineScope.() -> BaseResponse<T>,
        success: (BaseResponse<T>) -> Unit,
        error: (ErrorInfo) -> Unit,
    ) {
        rxLifeScope.launch(
            {
                val data = block.invoke(this)
                if (data.isSucces()) {
                    success(data)
                } else {
                    error(ExceptionHandle.handleException(data.getErrorCode(),data.getErrorMessage()))
                }
            }, {
                error(ExceptionHandle.handleException(it))
            }
        )
    }
}