package com.zjx.app_common_library.utils.ext

import com.blankj.utilcode.util.StringUtils

fun String?.isNoTrimEmpty(): Boolean = !StringUtils.isTrimEmpty(this)
fun String?.isTrimEmpty(): Boolean = StringUtils.isTrimEmpty(this)
fun String?.isEmpty(): Boolean = StringUtils.isEmpty(this)
fun String?.isNoEmpty(): Boolean = !StringUtils.isEmpty(this)
