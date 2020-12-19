package com.zjx.app_common_library.base.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zjx.app_common_library.base.BaseViewModel
import com.zjx.app_common_library.utils.ext.getVmClazz

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/12
 * 描述　: ViewModelActivity基类，把ViewModel注入进来了
 */
abstract class BaseVmActivity<VM : BaseViewModel> : AppCompatActivity() {
    lateinit var mViewModel: VM

    abstract fun layoutId(): Int
    open fun initListener() {}
    abstract fun initView()
    open fun initWindowParam() {}
    open fun initData() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindowParam()
        setContentView(layoutId())
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