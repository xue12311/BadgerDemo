package com.zjx.app_common_library.network

import android.text.TextUtils
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

/**
 * Http请求错误信息
 * User: ljx
 * Date: 2019-06-26
 * Time: 14:26
 */
data class ErrorInfo(
    //仅指服务器返回的错误码
    var errorCode:Int,
    //错误文案，网络错误、请求失败错误、服务器返回的错误等文案
    var errorMsg: String?,
    //错误详情
    var errorDetail: String?
)