package com.zjx.ecommerce.utils.app_common_library.livedata

import androidx.lifecycle.MutableLiveData

class LongLiveData(value: Long = 0) : MutableLiveData<Long>(value) {

    override fun getValue(): Long {
        return super.getValue()!!
    }
}