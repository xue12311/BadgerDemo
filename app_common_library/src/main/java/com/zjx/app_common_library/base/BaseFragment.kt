package com.zjx.app_common_library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.zjx.app_common_library.utils.ext.getVmClazz

abstract class BaseFragment : Fragment() {
    private var isFirst: Boolean = true

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract fun layoutId(): Int
    open fun initListener() {}
    abstract fun initView(view: View)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onVisible()
        initView(view)
        initListener()
    }


    /**
     * 获得activity中的 ViewModel
     */
    fun <AVM : BaseViewModel> getActivityViewModel(): AVM? {
        if (activity != null) {
            return ViewModelProvider(requireActivity()).get(getVmClazz(requireActivity()) as Class<AVM>)
        } else {
            return null
        }
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
    fun showToast(message: String?) {
        if (!StringUtils.isTrimEmpty(message)) {
            ToastUtils.showShort(message)
        }
    }
}