package com.zjx.app_common_library.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
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
}