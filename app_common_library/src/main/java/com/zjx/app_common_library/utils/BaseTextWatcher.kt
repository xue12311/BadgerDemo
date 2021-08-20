package com.zjx.app_common_library.utils

import android.text.Editable
import android.text.TextWatcher

open class BaseTextWatcher : TextWatcher {
    /**
     *  检测文本改变之后的状态 文本内容输入完成之后调用
     * @param s Editable  输入结束呈现在输入框中的信息
     */
    override fun afterTextChanged(s: Editable?) {
    }

    /**
     * 内容要改变之前调用  文本内容将要被改变的时候调用
     * @param s CharSequence 输入框中改变前的字符串信息
     * @param start Int 输入框中改变前的字符串的起始位置
     * @param count Int 输入框中改变前后的字符串改变数量一般为0
     * @param after Int 输入框中改变后的字符串与起始位置的偏移量
     */
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    /**
     *  内容改变时调用 文本内容改变过程中调用
     * @param s CharSequence 输入框中改变后的字符串信息
     * @param start Int 输入框中改变后的字符串的起始位置
     * @param count Int 输入框中改变前的字符串的位置 默认为0
     * @param after Int 输入框中改变后的一共输入字符串的数量
     */
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}