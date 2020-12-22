package com.zjx.app_common_library.utils.ext


fun List<String>.forEachListToString(): String {
    var mStringBuffer = StringBuffer()
    this.forEach {
        if (mStringBuffer.toString().isNoTrimEmpty()) {
            mStringBuffer.append(",")
        }
        mStringBuffer.append(it)
    }
    return mStringBuffer.toString()
}

fun <T> List<T>.forEachListToString(getParameterToString: (T) -> String?): String {
    var mStringBuffer = StringBuffer()
    this.forEach {
        val sParameter = getParameterToString(it)
        if (sParameter != null && sParameter.isNoTrimEmpty()) {
            if (mStringBuffer.toString().isNoTrimEmpty()) {
                mStringBuffer.append(",")
            }
            mStringBuffer.append(sParameter)
        }
    }
    return mStringBuffer.toString()
}