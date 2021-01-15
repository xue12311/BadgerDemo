package com.zjx.app_common_library.base.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.zjx.app_common_library.utils.inflateBindingWithGeneric

abstract class BaseVbFragment<VB : ViewBinding> : Fragment() {
    private var _mViewBinding: VB? = null
    val mViewBinding: VB
        get() = _mViewBinding!!
    private var isFirst: Boolean = true
    open fun initView() {}
    open fun initListener() {}
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _mViewBinding = inflateBindingWithGeneric(layoutInflater, container, false)
        return _mViewBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        onVisible()
    }

    /**
     * 懒加载
     */
    open fun lazyLoadData() {}

    //首次加载
    abstract fun firstLazyLoadData()


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

    override fun onDestroyView() {
        _mViewBinding = null
        super.onDestroyView()
    }
}