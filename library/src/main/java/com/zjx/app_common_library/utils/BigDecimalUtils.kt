package com.zjx.app_common_library.utils

import com.blankj.utilcode.util.StringUtils
import java.math.BigDecimal
import java.math.RoundingMode

object BigDecimalUtils {
    const val zero_double = 0.0
    const val zero_int = 0
    const val zero_long = 0L

    //四舍五入
    var mDefaultRoundingMode: RoundingMode? = null

    // 需要精确至小数点后几位
    var mDefaultDecimalPoint: Int? = null

    /**
     * 需要精确至小数点后几位
     */
    fun getDefaultDecimalPoint(): Int = mDefaultDecimalPoint ?: 2

    /**
     * 需要精确模式  四舍五入
     */
    fun getDefaultRoundingMode(): RoundingMode = mDefaultRoundingMode ?: RoundingMode.HALF_UP

    /**
     * 需要精确至小数点后几位
     */
    fun setDefaultDecimalPoint(mDecimalPoint: Int?) {
        if (mDecimalPoint != null) {
            mDefaultDecimalPoint = mDecimalPoint
        }
    }

    /**
     * 设置 需要精确模式
     * @param mRoundingMode RoundingMode?
     * RoundingMode.CEILING：取右边最近的整数
     * RoundingMode.DOWN：去掉小数部分取整，也就是正数取左边，负数取右边，相当于向原点靠近的方向取整
     * RoundingMode.FLOOR：取左边最近的正数
     * RoundingMode.HALF_DOWN:五舍六入，负数先取绝对值再五舍六入再负数
     * RoundingMode.HALF_UP:四舍五入，负数原理同上
     * RoundingMode.HALF_EVEN:这个比较绕，整数位若是奇数则四舍五入，若是偶数则五舍六入
     */
    fun setDefaultRoundingMode(mRoundingMode: RoundingMode?) {
        if (mRoundingMode != null) {
            this.mDefaultRoundingMode = mRoundingMode
        }
    }


    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 如果 小于 0 即返回 0
     * @param num Double?
     * @return Double
     */
    fun onLessZeroDoubleToZeroString(num: Double?, decimalPoint: Int): String {
        return toPlainString(onLessZeroDoubleToZero(num), decimalPoint)
    }

    /**
     * 如果 小于 0 即返回 0
     * @param num Double?
     * @return Double
     */
    fun onLessZeroDoubleToZeroString(num: Double?): String {
        return toPlainString(onLessZeroDoubleToZero(num))
    }

    /**
     * 如果 小于 0 即返回 0
     * @param num Double?
     * @return Double
     */
    fun onLessZeroDoubleToZeroString(num: Int?): String {
        return toPlainString(onLessZeroDoubleToZero(num))
    }


    /**
     * 如果 小于 0 即返回 0
     * @param num Double?
     * @return Double
     */
    fun onLessZeroDoubleToZero(num: Double?): Double {
        if (num != null) {
            if (compareTo(num, zero_int) > zero_int) {
                return num
            }
        }
        return zero_double
    }

    /**
     * 如果 小于 0 即返回 0
     * @param num Int?
     * @return Int
     */
    fun onLessZeroDoubleToZero(num: Int?): Int {
        if (num != null) {
            if (compareTo(num, zero_int) > zero_int) {
                return num
            }
        }
        return zero_int
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 将 字符串 转为 Double 或 Long ，再转为 字符串
     * @param num String?
     * @return String
     */
    fun toStringToDoubleStringOrLongString(num: String?): String {
        return toStringToDoubleStringOrLongString(num, getDefaultDecimalPoint())
    }

    /**
     * 将 字符串 转为 Double 或 Long ，再转为 字符串
     * @param num String?
     * @return String
     */
    fun toStringToDoubleStringOrLongString(num: String?, decimalPoint: Int): String {
        if (StringUtils.isTrimEmpty(num)) {
            return ""
        } else if (StringUtils.null2Length0(num).contains(".")) {
            try {
                return toPlainString(num?.toDouble(), decimalPoint)
            } catch (e: Exception) {
                return toPlainString(num)
            }
        } else {
            try {
                return toPlainString(num?.toLong(), decimalPoint)
            } catch (e: Exception) {
                return toPlainString(num)
            }
        }
    }

    /**
     * 将 字符串 转为 Double 或 Long ，再转为 字符串
     * @param num String?
     * @return String
     */
    fun isStringZero(num: String?): Boolean {
        if (StringUtils.isTrimEmpty(num)) {
            return false
        } else if (StringUtils.null2Length0(num).contains(".")) {
            try {
                return isZero(num!!.toDouble())
            } catch (e: Exception) {
                return num.equals("0")
            }
        } else {
            try {
                return isZero(num!!.toLong())
            } catch (e: Exception) {
                return num.equals("0")
            }
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    /**
     * 减
     */
    fun subToDouble(
        mOldNum: Double?, mNewNum: Double?,
    ): Double = subToDouble(mOldNum ?: zero_double, mNewNum ?: zero_double)

    /**
     * 减
     */
    fun subToDouble(
        mOldNum: Double, mNewNum: Double,
    ): Double {
        return subToDouble(mOldNum, mNewNum, getDefaultDecimalPoint(), getDefaultRoundingMode())
    }

    /**
     * 减
     */
    fun subToDouble(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
    ): Double {
        return subToDouble(mOldNum, mNewNum, decimalPoint, getDefaultRoundingMode())
    }

    /**
     * 减
     */
    private fun subToDouble(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): Double {
        return sub(mOldNum, mNewNum, decimalPoint, roundingMode).toDouble()
    }

    /**
     * 减
     */
    private fun sub(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): BigDecimal {
        return onAddOrSubtractOrMultiplyOrDivideBigDecimal(
            BigDecimal.valueOf(mOldNum),
            BigDecimal.valueOf(mNewNum),
            decimalPoint,
            roundingMode,
            BigDecimalCalculate.Subtract
        )
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    /**
     * 乘
     */
    fun mulToDouble(
        mOldNum: Double?, mNewNum: Double?,
    ): Double = mulToDouble(mOldNum ?: zero_double, mNewNum ?: zero_double)

    /**
     * 乘
     */
    fun mulToDouble(
        mOldNum: Double, mNewNum: Double,
    ): Double {
        return mulToDouble(
            mOldNum, mNewNum,
            getDefaultDecimalPoint(),
            getDefaultRoundingMode()
        )
    }


    /**
     * 乘
     */
    fun mulToDouble(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
    ): Double {
        return mulToDouble(mOldNum, mNewNum, decimalPoint, getDefaultRoundingMode())
    }

    /**
     * 乘
     */
    private fun mulToDouble(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): Double {
        return mul(mOldNum, mNewNum, decimalPoint, roundingMode).toDouble()
    }

    /**
     * 乘
     */
    private fun mul(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): BigDecimal {
        return onAddOrSubtractOrMultiplyOrDivideBigDecimal(
            BigDecimal.valueOf(mOldNum),
            BigDecimal.valueOf(mNewNum),
            decimalPoint,
            roundingMode,
            BigDecimalCalculate.Multiply
        )
    }

    /**
     * 乘
     */
    fun mulToLong(
        mOldNum: Long, mNewNum: Long,
    ): Long {
        return onAddOrSubtractOrMultiplyOrDivideBigDecimal(
            BigDecimal.valueOf(mOldNum),
            BigDecimal.valueOf(mNewNum),
            getDefaultDecimalPoint(),
            getDefaultRoundingMode(),
            BigDecimalCalculate.Multiply
        ).toLong()
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 加
     */
    fun addToDouble(
        mOldNum: Double?, mNewNum: Double?,
    ): Double {
        return addToDouble(mOldNum ?: zero_double, mNewNum ?: zero_double)
    }

    /**
     * 加
     */
    fun addToDouble(
        mOldNum: Double, mNewNum: Double,
    ): Double {
        return addToDouble(mOldNum, mNewNum, getDefaultDecimalPoint(), getDefaultRoundingMode())
    }

    /**
     * 加
     */
    fun addToDouble(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
    ): Double {
        return addToDouble(mOldNum, mNewNum, decimalPoint, getDefaultRoundingMode())
    }

    /**
     * 加
     */
    private fun addToDouble(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): Double {
        return add(mOldNum, mNewNum, decimalPoint, roundingMode).toDouble()
    }

    /**
     * 加
     */
    private fun add(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): BigDecimal {
        return onAddOrSubtractOrMultiplyOrDivideBigDecimal(
            BigDecimal.valueOf(mOldNum),
            BigDecimal.valueOf(mNewNum),
            decimalPoint,
            roundingMode,
            BigDecimalCalculate.Add
        )
    }


    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    /**
     * 除
     */
    fun divToDouble(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
    ): Double {
        return divToDouble(mOldNum, mNewNum, decimalPoint, getDefaultRoundingMode())
    }

    /**
     * 除
     */
    fun divToDouble(
        mOldNum: Double?, mNewNum: Double?,
    ): Double = divToDouble(mOldNum ?: zero_double, mNewNum ?: zero_double)

    /**
     * 除
     */
    fun divToDouble(
        mOldNum: Double, mNewNum: Double,
    ): Double {
        return divToDouble(mOldNum, mNewNum, getDefaultDecimalPoint(), getDefaultRoundingMode())
    }

    /**
     * 除
     */
    fun divToDouble(
        mOldNum: Double, mNewNum: Int,
    ): Double {
        return divToDouble(
            mOldNum,
            mNewNum.toDouble(),
            getDefaultDecimalPoint(),
            getDefaultRoundingMode()
        )
    }

    /**
     * 除
     */
    private fun divToDouble(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): Double {
        return div(mOldNum, mNewNum, decimalPoint, roundingMode).toDouble()
    }

    /**
     * 除
     */
    private fun div(
        mOldNum: Double, mNewNum: Double,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): BigDecimal {
        return onAddOrSubtractOrMultiplyOrDivideBigDecimal(
            BigDecimal.valueOf(mOldNum),
            BigDecimal.valueOf(mNewNum),
            decimalPoint,
            roundingMode,
            BigDecimalCalculate.Divide
        )
    }


    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * BigDecimal 转 Double
     */
    private fun onBigDecimalToDouble(
        mOldBigDecimal: BigDecimal,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): Double {
        return mOldBigDecimal.setScale(decimalPoint, roundingMode).toDouble()
    }


    /**
     * 运算 返回 BigDecimal
     */
    private fun onAddOrSubtractOrMultiplyOrDivideBigDecimal(
        mOldBigDecimal: BigDecimal,
        mNewBigDecimal: BigDecimal,
        decimalPoint: Int,
        roundingMode: RoundingMode,
        mCalculate: BigDecimalCalculate,

        ): BigDecimal {
        return if (mCalculate is BigDecimalCalculate.Add) {
            mOldBigDecimal.add(mNewBigDecimal)
        } else if (mCalculate is BigDecimalCalculate.Subtract) {
            mOldBigDecimal.subtract(mNewBigDecimal)
        } else if (mCalculate is BigDecimalCalculate.Multiply) {
            mOldBigDecimal.multiply(mNewBigDecimal)
        } else if (mCalculate is BigDecimalCalculate.Divide) {
            mOldBigDecimal.divide(mNewBigDecimal, decimalPoint, roundingMode)
        } else {
            BigDecimal(zero_int)
        }.setScale(decimalPoint, roundingMode)
    }


    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 两个数值比较
     * @param value Double
     * @param num Double
     * @return Int -1: value < num
     * @return Int 0: value = num
     * @return Int 1: value > num
     *
     */
    fun compareTo(value: Double, num: Double): Int {
        return compareTo(BigDecimal.valueOf(value), BigDecimal.valueOf(num))
    }

    /**
     * 两个数值比较
     * @param value Double?
     * @param num Double?
     * @return Int -1: value < num
     * @return Int 0: value = num
     * @return Int 1: value > num
     *
     */
    fun compareTo(value: Double?, num: Double?): Int =
        compareTo(value ?: zero_double, num ?: zero_double)

    /**
     * 两个数值比较
     * @param value Double
     * @param num Int
     * @return Int -1: value < num
     * @return Int 0: value = num
     * @return Int 1: value > num
     */
    fun compareTo(value: Double, num: Int): Int {
        return compareTo(BigDecimal.valueOf(value), BigDecimal(num))
    }

    /**
     * 两个数值比较
     * @param value Int
     * @param num Double
     * @return Int -1: value < num
     * @return Int 0: value = num
     * @return Int 1: value > num
     */
    fun compareTo(value: Int, num: Double): Int {
        return compareTo(BigDecimal(value), BigDecimal.valueOf(num))
    }

    /**
     * 两个数值比较
     * @param value Int
     * @param num Int
     * @return Int -1: value < num
     * @return Int 0: value = num
     * @return Int 1: value > num
     */
    fun compareTo(value: Int, num: Int): Int {
        return compareTo(BigDecimal(value), BigDecimal(num))
    }

    /**
     * 两个数值比较
     * @param value Float
     * @param num Int
     * @return Int -1: value < num
     * @return Int 0: value = num
     * @return Int 1: value > num
     */
    fun compareTo(value: Float, num: Int): Int {
        return compareTo(value.toBigDecimal(), BigDecimal(num))
    }

    /**
     * 两个数值比较
     * @param value Float
     * @param num Float
     * @return Int -1: value < num
     * @return Int 0: value = num
     * @return Int 1: value > num
     */
    fun compareTo(value: Float, num: Float): Int {
        return compareTo(value.toBigDecimal(), num.toBigDecimal())
    }

    /**
     * 两个数值比较
     * @param value Float
     * @param num Float
     * @return Int -1: value < num
     * @return Int 0: value = num
     * @return Int 1: value > num
     */
    fun compareTo(value: BigDecimal, num: BigDecimal): Int {
        return value.compareTo(num)
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    fun toPlainString(num: Any?): String {
        return toPlainString(num, getDefaultDecimalPoint(), getDefaultRoundingMode())
    }

    fun toPlainString(num: Any?, roundingMode: RoundingMode): String {
        return toPlainString(num, getDefaultDecimalPoint(), roundingMode)
    }

    fun toPlainString(num: Any?, decimalPoint: Int): String {
        return toPlainString(num, decimalPoint, getDefaultRoundingMode())
    }

    fun toPlainString(
        num: Any?,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): String {
        if (num != null) {
            if (num is Double) {
                return onBigDecimalToString(BigDecimal.valueOf(num), decimalPoint, roundingMode)
            } else if (num is Int) {
                return onBigDecimalToString(BigDecimal(num), decimalPoint, roundingMode)
            } else if (num is BigDecimal) {
                return onBigDecimalToString(num, decimalPoint, roundingMode)
            } else if (num is Float) {
                return onBigDecimalToString(num.toBigDecimal(), decimalPoint, roundingMode)
            } else if (num is Long) {
                return onBigDecimalToString(num.toBigDecimal(), decimalPoint, roundingMode)
            } else if (num is String) {
                return StringUtils.null2Length0(num)
            } else {
                return ""
            }
        } else {
            return ""
        }
    }

    /**
     * 转 String
     */
    private fun onBigDecimalToString(
        mBigDecimal: BigDecimal,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): String {
        return StringUtils.null2Length0(
            toPlainString(
                mBigDecimal, decimalPoint, roundingMode
            )
        )
    }

    fun toPlainString(
        value: BigDecimal,
        decimalPoint: Int,
        roundingMode: RoundingMode,
    ): String {
        //stripTrailingZeros()  ---  去掉末尾0
        //toPlainString()  ---  转为普遍计数法输出
        //RoundingMode.CEILING：取右边最近的整数
        //
        //RoundingMode.DOWN：去掉小数部分取整，也就是正数取左边，负数取右边，相当于向原点靠近的方向取整
        //RoundingMode.FLOOR：取左边最近的正数
        //RoundingMode.HALF_DOWN:五舍六入，负数先取绝对值再五舍六入再负数
        //RoundingMode.HALF_UP:四舍五入，负数原理同上
        //RoundingMode.HALF_EVEN:这个比较绕，整数位若是奇数则四舍五入，若是偶数则五舍六入
        if (isZero(value)) {
            return "0"
        } else {
            //小数位
            var scale = value.scale()
            if (scale > decimalPoint) {
                scale = decimalPoint
            }
            return value.setScale(scale, roundingMode)
                .stripTrailingZeros()
                .toPlainString()
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    fun isZeroToDoubleOrNull(value: Double?): Boolean {
        return isZero(value ?: zero_double)
    }

    fun isZeroToIntOrNull(value: Int?): Boolean {
        return isZero(value ?: zero_int)
    }

    /**
     * 数值是否为 0
     * @param value Double
     * @return Boolean true:数值为 0 ，false: 数值不为 0
     */
    fun isZero(value: Double): Boolean {
        return isZero(BigDecimal.valueOf(value))
    }

    fun isZero(value: Long): Boolean {
        return isZero(BigDecimal.valueOf(value))
    }

    fun isZero(value: Int): Boolean {
        return isZero(BigDecimal(value))
    }


    fun isZero(value: Float): Boolean {
        return isZero(value.toBigDecimal())
    }


    fun isZero(value: BigDecimal): Boolean {
        return compareTo(value, BigDecimal.valueOf(zero_double)) == zero_int
    }

    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    sealed class BigDecimalCalculate {
        /**
         * 加
         */
        object Add : BigDecimalCalculate()

        /**
         * 减
         */
        object Subtract : BigDecimalCalculate()

        /**
         * 乘
         */
        object Multiply : BigDecimalCalculate()

        /**
         * 除
         */
        object Divide : BigDecimalCalculate()
    }
}