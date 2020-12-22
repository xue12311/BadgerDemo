package com.zjx.app_common_library.base.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.zjx.app_common_library.utils.ext.getVmClazz

abstract class BaseVbActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var mViewBinding: VB
    abstract fun initView()
    open fun initListener() {}
    open fun initData() {}
    open fun initWindowParam() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindowParam()
        mViewBinding = createViewBinding()
        setContentView(mViewBinding.root)
        initView()
        initListener()
        initData()
    }

    /**
     * 创建ViewBinding
     */
    private fun createViewBinding(): VB {
        val clazz = getVmClazz<Class<VB>>(this)
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }

}