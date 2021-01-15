package com.zjx.app_common_library.base.viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.zjx.app_common_library.base.BaseViewModel
import com.zjx.app_common_library.utils.ext.getVmClazz
import com.zjx.app_common_library.utils.inflateBindingWithGeneric

abstract class BaseVbVmActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {

    lateinit var mViewModel: VM

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
        mViewModel = createViewModel()
        initView()
        initListener()
        createObserver()
        initData()
    }


    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this) as Class<VM>)
    }

    /**
     * 创建观察者
     */
    abstract fun createObserver()
}