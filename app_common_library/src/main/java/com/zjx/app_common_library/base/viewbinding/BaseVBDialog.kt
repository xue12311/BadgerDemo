package com.zjx.app_common_library.base.viewbinding

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.zjx.app_common_library.utils.inflateBindingWithGeneric

abstract class BaseVBDialog<VB : ViewBinding>(context: Context, themeResId: Int) : Dialog(context, themeResId) {

    lateinit var binding: VB

    constructor(context: Context) : this(context, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBindingWithGeneric(layoutInflater)
        setContentView(binding.root)
    }

}