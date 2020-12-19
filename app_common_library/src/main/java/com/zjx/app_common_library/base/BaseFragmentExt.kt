package com.zjx.app_common_library.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun BaseFragment.showToast(message: String?) {
    if (!StringUtils.isTrimEmpty(message)) {
        ToastUtils.showShort(message)
    }
}

fun BaseFragment.launchUI(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        block()
    }
}

fun BaseFragment.launchIO(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        runCatching {
            withContext(Dispatchers.IO) {
                block()
            }
        }
    }
}