package com.zjx.app_common_library.base

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils


abstract class BaseFragmentActivity : FragmentActivity() {
    abstract fun layoutId(): Int
    abstract fun initView()
    open fun initData() {}
    open fun initListener() {}
    open fun initWindowParam(){}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindowParam()
        setContentView(layoutId())
        initView()
        initListener()
        initData()
    }
    fun showToast(message: String?) {
        if (!StringUtils.isTrimEmpty(message)) {
            ToastUtils.showShort(message)
        }
    }
}