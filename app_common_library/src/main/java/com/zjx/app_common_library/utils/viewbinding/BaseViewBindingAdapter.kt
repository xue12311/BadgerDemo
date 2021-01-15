package com.zjx.app_common_library.utils.viewbinding

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dylanc.viewbinding.BaseViewBindingHolder
import com.dylanc.viewbinding.BindingViewHolder
import com.zjx.app_common_library.utils.inflateBindingWithGeneric


/**
 * How to modify the base class to use view binding, you need the following steps:
 * 1. Adds a generic of view binding to the base class.
 * 2. Uses [BindingViewHolder] class instead of the class of original view holder.
 * 3. Uses [inflateBindingWithGeneric] method to create the [BindingViewHolder] object and returns it
 * in [onCreateViewHolder] method.
 *
 * Here is the core code.
 *
 * @author Dylan Cai
 */
abstract class BaseBindingAdapter<T, VB : ViewBinding>(data: MutableList<T>? = null) : BaseQuickAdapter<T, BaseViewBindingHolder<VB>>(0, data) {
    //返回ViewHolder
    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseViewBindingHolder<VB> = BaseViewBindingHolder(inflateBindingWithGeneric<VB>(parent))
}