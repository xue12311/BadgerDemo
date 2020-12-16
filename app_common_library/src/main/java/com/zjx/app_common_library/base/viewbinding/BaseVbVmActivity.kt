package com.zjx.app_common_library.base.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.zjx.app_common_library.base.BaseViewModel
import com.zjx.app_common_library.utils.ext.getVmClazz

abstract class BaseVbVmActivity<VM : BaseViewModel, VB : ViewBinding> : AppCompatActivity() {

    lateinit var mViewModel: VM

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
     * 创建ViewBinding
     */
    private fun createViewBinding(): VB {
        val clazz = getVmClazz<Class<VB>>(this, 1)
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }

    /**
     * 创建观察者
     */
    abstract fun createObserver()

    fun showToast(message: String?) {
        if (!StringUtils.isTrimEmpty(message)) {
            ToastUtils.showShort(message)
        }
    }
}