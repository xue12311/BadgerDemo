package com.zjx.app_common_library.utils

import android.text.Editable
import com.blankj.utilcode.util.StringUtils
import java.util.regex.Pattern

/**
 * 功能描述：小数输入文本观察类
 *
 * @author (作者) edward（冯丰枫）
 * @link http://www.jianshu.com/u/f7176d6d53d2
 * 创建时间： 2018/3/12
 */
open class DecimalInputTextWatcher : BaseTextWatcher {
    private var mPattern: Pattern? = null

    constructor() : super()

    constructor(type: DecimalInputTextWatcherType, number: Int) : super() {
        if (type == DecimalInputTextWatcherType.decimal) {
            mPattern = Pattern.compile("^[0-9]+(\\.[0-9]{0,$number})?$")
        } else if (type == DecimalInputTextWatcherType.integer) {
            mPattern = Pattern.compile("^[0-9]{0,$number}+(\\.[0-9]{0,})?$")
        }
    }

    constructor(integers: Int, decimals: Int) : super() {
        mPattern = Pattern.compile("^[0-9]{0,$integers}+(\\.[0-9]{0,$decimals})?$")
    }

    override fun afterTextChanged(editable: Editable?) {
        super.afterTextChanged(editable)
        val text = StringUtils.null2Length0(editable?.toString())
        if (editable == null || StringUtils.isTrimEmpty(text)) {
            return
        }
        if (editable.length > 1 && editable.get(0) == '0' && editable.get(1) != '.') {
            //删除整数首位的“0”
            editable.delete(0, 1)
            return
        }
        if (text == ".") {
            //首位是“.”自动补“0”
            editable.insert(0, "0")
            return
        }
        if (mPattern != null && !mPattern!!.matcher(text).matches() && editable.length > 0) {
            editable.delete(editable.length - 1, editable.length)
            return
        }
    }


    enum class DecimalInputTextWatcherType {
        integer, decimal
    }
}