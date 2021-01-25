package com.zjx.app_common_library.base.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.zjx.app_common_library.base.BaseViewModel
import com.zjx.app_common_library.utils.ext.getVmClazz

/**
 * 作者　: hegaojian
 * 时间　: 2019/12/12
 * 描述　: ViewModelFragment基类，自动把ViewModel注入Fragment
 */
abstract class BaseVmFragment<AVM : BaseViewModel> : Fragment() {

    //是否第一次加载
    private var isFirst: Boolean = true

    lateinit var mViewModel: AVM


    /**
     * 当前Fragment绑定的视图布局
     */
    abstract fun layoutId(): Int
    abstract fun initView(view: View)
    open fun initListener() {}
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initListener()
        onVisible()
        mViewModel = getActivityViewModel()
        createObserver()
    }
    /**
     * 创建viewModel
     */
//    private fun createViewModel(): VM {
//        return ViewModelProvider(this).get(getVmClazz(this) as Class<VM>)
//    }
    /**
     * 获得activity中的 ViewModel
     */
    private fun <AVM : BaseViewModel> getActivityViewModel(): AVM =
            ViewModelProvider(requireActivity()).get(getVmClazz(requireActivity()) as Class<AVM>)

    /**
     * 懒加载
     */
    open fun lazyLoadData() {}

    //首次加载
    abstract fun firstLazyLoadData()


    /**
     * 创建观察者
     */
    open fun createObserver() {}

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED) {
            if (isFirst) {
                firstLazyLoadData()
            } else {
                lazyLoadData()
            }
            isFirst = false
        }
    }
}