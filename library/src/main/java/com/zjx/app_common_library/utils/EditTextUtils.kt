package com.zjx.app_common_library.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.blankj.utilcode.util.StringUtils

object EditTextUtils {
    /**
     * 设置文本内容
     */
    fun setTextChangedListener(
        mEditText: EditText,
        str_text: String?,
        onAfterTextChanged: (String) -> Unit,
    ) {
        setTextChangedListener(mEditText, str_text, -1, -1, onAfterTextChanged)
    }

    /**
     * 设置文本内容
     */
    fun setTextChangedListener(
        mEditText: EditText,
        str_text: String?,
        mIntegers: Int,
        mDecimals: Int,
        onAfterTextChanged: (String) -> Unit,
    ) {
        if (mEditText.getTag() != null && mEditText.getTag() is TextWatcher) {
            //为了避免TextWatcher在调用settext（）时被调用，提前将它移除
            mEditText.removeTextChangedListener(mEditText.tag as TextWatcher?)
        }
        mEditText.setText(StringUtils.null2Length0(str_text))
        val mTextChanged = if (mIntegers <= -1 && mDecimals <= -1) {
            object : BaseTextWatcher() {
                override fun afterTextChanged(text: Editable?) {
                    super.afterTextChanged(text)
                    onAfterTextChanged.invoke(StringUtils.null2Length0(text?.toString()))
                }
            }
        } else {
            object : DecimalInputTextWatcher(mIntegers, mDecimals) {
                override fun afterTextChanged(text: Editable?) {
                    super.afterTextChanged(text)
                    onAfterTextChanged.invoke(StringUtils.null2Length0(text?.toString()))
                }
            }
        }
        //重新添加上TextWatcher监听
        mEditText.addTextChangedListener(mTextChanged)
        //将TextWatcher绑定到EditText
        mEditText.setTag(mTextChanged)
    }
}