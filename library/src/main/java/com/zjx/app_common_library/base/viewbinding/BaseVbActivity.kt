package com.zjx.app_common_library.base.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.zjx.app_common_library.utils.ext.getVmClazz
import com.zjx.app_common_library.utils.inflateBindingWithGeneric

abstract class BaseVbActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var mViewBinding: VB
    open fun initView() {}
    open fun initListener() {}
    open fun initData() {}
    open fun initWindowParam() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindowParam()
        mViewBinding = inflateBindingWithGeneric(layoutInflater)
        setContentView(mViewBinding.root)
        initView()
        initListener()
        initData()
    }

}