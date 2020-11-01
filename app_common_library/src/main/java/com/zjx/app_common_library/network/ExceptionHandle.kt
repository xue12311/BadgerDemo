package com.zjx.app_common_library.network

import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.load.HttpException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import com.zjx.app_common_library.R
import org.apache.http.conn.ConnectTimeoutException
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLException

object ExceptionHandle {
    fun handleException(throwable: Throwable): ErrorInfo {
        val errorCode = throwable.getLocalizedMessage()?.toInt() ?: -1
        val errorDetail = throwable.toString()
        val errorMsg = if (throwable is UnknownHostException) {
            if (!NetworkUtils.isConnected()) {
                getString(R.string.network_error)
            } else {
                getString(R.string.notify_no_network)
            }
        } else if (throwable is HttpException) {
            getString(R.string.network_error2)
        } else if (throwable is SocketTimeoutException || throwable is TimeoutException
            || throwable is ConnectTimeoutException || throwable is SocketTimeoutException
        ) {
            //前者是通过OkHttpClient设置的超时引发的异常，后者是对单个请求调用timeout方法引发的超时异常
            getString(R.string.time_out_please_try_again_later)
        } else if (throwable is ConnectException) {
            getString(R.string.esky_service_exception)
        } else if (throwable is HttpStatusCodeException) { //请求失败异常
            if ("416".equals(errorCode)) {
                getString(R.string.network_error_request_scope)
            } else {
                getString(R.string.network_error_request_failure)
            }
        } else if (throwable is JsonSyntaxException || throwable is ParseException
            || throwable is MalformedJsonException
        ) { //请求成功，但Json语法异常,导致解析失败
            getString(R.string.network_error_json)
        } else if (throwable is SSLException) {
            getString(R.string.network_error_certificate_error)
        } else {
            getString(R.string.network_error_unknown)
        }
        return ErrorInfo(errorCode, errorMsg, errorDetail)
    }

    private fun getString(resId: Int): String {
        return Utils.getApp().getString(resId)
    }

    fun handleException(errorCode: Int?, errorMsg: String?): ErrorInfo {
        return ErrorInfo(errorCode?:-1, errorMsg, errorMsg)
    }
}