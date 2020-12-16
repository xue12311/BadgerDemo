package com.zjx.app_common_library.utils.ext

import java.lang.reflect.ParameterizedType

/**
 * 获取vm clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any, index: Int = 0): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[index] as VM
}

