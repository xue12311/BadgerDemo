package com.zjx.app_common_library.utils.ext

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import com.blankj.utilcode.util.StringUtils
import com.zjx.app_common_library.utils.BaseTextWatcher

/**
 * EditText 文本监听
 */
fun EditText.setTextChangeListener(body: (key: String) -> Unit) {
    addTextChangedListener(object : BaseTextWatcher() {
        /**
         *  内容改变时调用 文本内容改变过程中调用
         * @param s CharSequence 输入框中改变后的字符串信息
         * @param start Int 输入框中改变后的字符串的起始位置
         * @param count Int 输入框中改变前的字符串的位置 默认为0
         * @param after Int 输入框中改变后的一共输入字符串的数量
         */
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s != null) {
                body(s.toString())
            }
        }
    })
}

/**
 * 内容要改变之前调用  文本内容将要被改变的时候调用
 * @param s CharSequence 输入框中改变前的字符串信息
 * @param start Int 输入框中改变前的字符串的起始位置
 * @param count Int 输入框中改变前后的字符串改变数量一般为0
 * @param after Int 输入框中改变后的字符串与起始位置的偏移量
 */
fun EditText.beforeTextChanged(action: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit) =
    textWatch(before = action)

/**
 *  内容改变时调用 文本内容改变过程中调用
 * @param s CharSequence 输入框中改变后的字符串信息
 * @param start Int 输入框中改变后的字符串的起始位置
 * @param count Int 输入框中改变前的字符串的位置 默认为0
 * @param after Int 输入框中改变后的一共输入字符串的数量
 */
fun EditText.onTextChanged(action: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit) =
    textWatch(change = action)

/**
 *  检测文本改变之后的状态 文本内容输入完成之后调用
 * @param s Editable  输入结束呈现在输入框中的信息
 */
fun EditText.afterTextChanged(action: (s: Editable?) -> Unit) = textWatch(after = action)

fun EditText.textWatch(
    before: ((s: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
    change: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null,
    after: ((s: Editable?) -> Unit)? = null
): EditText {
    return apply {
        // 这里返回EditText
        val listener = object : BaseTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                after?.invoke(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                before?.invoke(s, start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                change?.invoke(s, start, before, count)
            }

        }
        addTextChangedListener(listener)
    }
}


/**
 * 设置 [EditText] 文本，并且把光标定位到末尾
 */
fun EditText.setTextAndSelectionTextEnd(text: CharSequence?) {
    setTextAndSelectionTextEnd(text?.toString())
}

fun EditText.setTextAndSelectionTextEnd(text: String?) {
    setText(StringUtils.null2Length0(text))
    setSelectionTextEnd()
}

/**
 * 设置 [EditText] 文本，并且全选文本
 */
fun EditText.setTextWithSelection(text: CharSequence) {
    setText(text)
    setSelectionAllText()
}

/**
 * 移动光标至结尾
 */
fun EditText.setSelectionTextEnd() {
    if (!TextUtils.isEmpty(text)) {
        setSelection(getText().length)
    }
}

/**
 * [EditText] 文本 全选
 */
fun EditText.setSelectionAllText() {
    setSelection(0, getText().length)
}
