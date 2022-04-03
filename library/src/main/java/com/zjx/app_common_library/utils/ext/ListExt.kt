package com.zjx.app_common_library.utils.ext

fun List<String>.forEachListToString(): String = forEachListToString({ it })

fun List<String>.forEachListToString(divider: String?): String =
    forEachListToString({ it }, divider)

fun <T> List<T>.forEachListToString(getParameterToString: (T) -> String?): String =
    forEachListToString(false, getParameterToString, ",")

fun <T> List<T>.forEachListToString(
    getParameterToString: (T) -> String?,
    divider: String?
): String = forEachListToString(false, getParameterToString, divider)

fun <T> List<T>.forEachListToString(
    isEnableNullOrEmpty: Boolean,
    getParameterToString: (T) -> String?,
): String = forEachListToString(isEnableNullOrEmpty, getParameterToString,",")

/**
 * 多个 字符串 拼接
 *
 * @param mTexts  字符串 组
 * @param mDelimiterDivider  拼接字符
 * @param isEnableNullOrEmpty  是否启用 空字符 ， true: 启用 空字符 ； false : 不启用空字符
 * @return
 */
fun <T> List<T>.forEachListToString(
    isEnableNullOrEmpty: Boolean,
    getParameterToString: (T) -> String?,
    divider: String? = ","
): String {
    val mStringBuilder = StringBuilder()
    for ((index, bean) in this.withIndex()) {
        val text = getParameterToString(bean)
        //字符 可以为 null
        if (isEnableNullOrEmpty ||
            //字符 不能为 null
            (!isEnableNullOrEmpty && text.isNoTrimEmpty())
        ) {
            if ((isEnableNullOrEmpty && index != 0) ||
                (mStringBuilder.toString().isNoTrimEmpty())
            ) {
                mStringBuilder.append(divider)
            }
            mStringBuilder.append(text)
        }
    }
    return mStringBuilder.toString()
}