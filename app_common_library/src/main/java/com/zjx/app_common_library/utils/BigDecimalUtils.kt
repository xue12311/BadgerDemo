package com.zjx.app_common_library.utils

import java.math.BigDecimal
import java.math.RoundingMode

object BigDecimalUtils {
    // 需要精确至小数点后几位
    const val DECIMAL_POINT_NUMBER: Int = 2

    // 加法运算
    @JvmStatic
    fun add(d1: Double, d2: Double): Double = add(BigDecimal.valueOf(d1), BigDecimal.valueOf(d2))

    // 减法运算
    @JvmStatic
    fun sub(d1: Double, d2: Double): Double = sub(BigDecimal.valueOf(d1), BigDecimal.valueOf(d2))

    // 乘法运算
    @JvmStatic
    fun mul(d1: Double, d2: Double, decimalPoint: Int): Double =
        mul(BigDecimal.valueOf(d1), BigDecimal.valueOf(d2), decimalPoint)

    // 除法运算
    @JvmStatic
    fun div(d1: Double, d2: Double): Double = div(BigDecimal.valueOf(d1), BigDecimal.valueOf(d2))

    @JvmStatic
    fun div(d1: Double, d2: Long): Double = div(BigDecimal.valueOf(d1), BigDecimal.valueOf(d2))
    @JvmStatic
    fun div(i1: Int, i2: Int): Double = div(BigDecimal(i1),BigDecimal(i2))

    @JvmStatic
    fun div(d1: Double, d2: Int): Double = div(BigDecimal.valueOf(d1), BigDecimal(d2))

    @JvmStatic
    fun div(d1: Double, d2: Double, scale: Int = DECIMAL_POINT_NUMBER): Double =
        div(BigDecimal.valueOf(d1), BigDecimal.valueOf(d2), scale)


    // 加法运算
    @JvmStatic
    fun add(b1: BigDecimal, b2: BigDecimal): Double =
        b1.add(b2).setScale(DECIMAL_POINT_NUMBER, BigDecimal.ROUND_DOWN)
            .toDouble()

    // 减法运算
    @JvmStatic
    fun sub(b1: BigDecimal, b2: BigDecimal): Double = b1.subtract(b2)
        .setScale(DECIMAL_POINT_NUMBER, BigDecimal.ROUND_DOWN).toDouble()

    // 乘法运算
    @JvmStatic
    fun mul(b1: BigDecimal, b2: BigDecimal, decimalPoint: Int): Double =
        b1.multiply(b2).setScale(decimalPoint, BigDecimal.ROUND_DOWN)
            .toDouble()

    // 除法运算
    @JvmStatic
    fun div(b1: BigDecimal, b2: BigDecimal): Double =
        b1.divide(b2, DECIMAL_POINT_NUMBER, BigDecimal.ROUND_DOWN)
            .toDouble()

    // 除法运算
    @JvmStatic
    fun div(b1: BigDecimal, b2: BigDecimal, scale: Int = DECIMAL_POINT_NUMBER): Double =
        b1.divide(b2, scale, BigDecimal.ROUND_DOWN)
            .toDouble()

    /**
     * 数值是否为 0
     * @param value Double
     * @return Boolean true:数值为 0 ，false: 数值不为 0
     */
    @JvmStatic
    fun isZero(value: Double): Boolean {
        return BigDecimal.valueOf(value).compareTo(BigDecimal(0)) == 0
    }

    /**
     * 两个数值比较
     * @param value Double
     * @param num Double
     * @return Int -1: value < num
     * @return Int 0: value = num
     * @return Int 1: value > num
     *
     */
    @JvmStatic
    fun compareTo(value: Double, num: Double): Int {
        return BigDecimal.valueOf(value).compareTo(BigDecimal.valueOf(num))
    }

    /**
     * 两个数值比较
     * @param value Double
     * @param num Int
     * @return Int -1: value < num
     * @return Int 0: value = num
     * @return Int 1: value > num
     */
    @JvmStatic
    fun compareTo(value: Double, num: Int): Int {
        return BigDecimal.valueOf(value).compareTo(BigDecimal(num))
    }


    @JvmStatic
    fun toPlainString(
        value: Double,
        roundingMode: RoundingMode = RoundingMode.DOWN
    ) = toPlainString(BigDecimal.valueOf(value), 2, roundingMode)

    @JvmStatic
    fun toPlainString(
        value: Double,
        decimalPoint: Int = 2,
        roundingMode: RoundingMode = RoundingMode.DOWN
    ) = toPlainString(BigDecimal.valueOf(value), decimalPoint, roundingMode)

    @JvmStatic
    fun toPlainString(
        value: BigDecimal,
        decimalPoint: Int = 2,
        roundingMode: RoundingMode = RoundingMode.DOWN
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
            return value.setScale(scale, roundingMode).stripTrailingZeros()
                .toPlainString()
        }
    }

    @JvmStatic
    fun toPlainString(value: Int): String {
        return BigDecimal(value).stripTrailingZeros().toPlainString()
    }

    @JvmStatic
    fun isZero(value: Int): Boolean {
        return BigDecimal(value).compareTo(BigDecimal(0)) == 0
    }

    @JvmStatic
    fun isZero(value: BigDecimal): Boolean {
        return value.compareTo(BigDecimal(0)) == 0
    }
}