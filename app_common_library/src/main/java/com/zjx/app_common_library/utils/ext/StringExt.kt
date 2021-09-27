package com.zjx.app_common_library.utils.ext

import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.TimeUtils
import com.zjx.app_common_library.utils.BigDecimalUtils
import java.math.BigDecimal
import java.util.*

fun String?.null2Length0(): String = StringUtils.null2Length0(this)
fun String?.isNoTrimEmpty(): Boolean = !StringUtils.isTrimEmpty(this)
fun String?.isTrimEmpty(): Boolean = StringUtils.isTrimEmpty(this)
fun String?.isEmpty(): Boolean = StringUtils.isEmpty(this)
fun String?.isNoEmpty(): Boolean = !StringUtils.isEmpty(this)


/**
 * string 转 double
 */
fun String?.onStringToDouble(): Double =
    onStringToDouble(0.0)

/**
 * string 转 double
 */
fun String?.onStringToDouble(mDefaultsNum: Double): Double {
    if (StringUtils.isTrimEmpty(this)) {
        return mDefaultsNum
    } else {
        try {
            return this?.toDouble() ?: mDefaultsNum
        } catch (e: Exception) {
            return mDefaultsNum
        }
    }
}

/**
 * string 转 int
 */
fun String?.onStringToInt(): Int = onStringToInt(0)

/**
 * string 转 int
 */
fun String?.onStringToInt(mDefaultsNum: Int): Int {
    if (StringUtils.isTrimEmpty(this)) {
        return mDefaultsNum
    } else {
        try {
            return this?.toInt() ?: mDefaultsNum
        } catch (e: Exception) {
            if (e is NumberFormatException) {
                try {
                    return onStringToDouble(mDefaultsNum.toDouble()).toInt()
                } catch (e: Exception) {
                    return onStringToDouble().toInt()
                }
            } else {
                return mDefaultsNum
            }
        }
    }
}

/**
 * string 转 Long
 */
fun String?.onStringToLong(): Long =onStringToLong(0)
/**
 * string 转 Long
 */
fun String?.onStringToLong(mDefaultsNum: Long): Long {
    if (StringUtils.isTrimEmpty(this)) {
        return mDefaultsNum
    } else {
        try {
            return this?.toLong() ?: mDefaultsNum
        } catch (e: Exception) {
            if (e is NumberFormatException) {
                try {
                    return onStringToDouble(mDefaultsNum.toDouble()).toLong()
                } catch (e: Exception) {
                    return onStringToDouble().toLong()
                }
            } else {
                return mDefaultsNum
            }
        }
    }
}

/**
 *
 * @receiver String? a
 * @param otherText String b
 * @return Int  a < b, 返回 -1 ； a = b，返回 0 ；a > b, 返回 1
 */
fun String?.onCompareTo(otherText: String?): Int =
    BigDecimalUtils.compareTo(
        this.onStringToDouble(),
        StringUtils.null2Length0(otherText).onStringToDouble()
    )

/**
 *
 * @receiver String? a
 * @param otherText String b
 * @return Int  a < b, 返回 -1 ； a = b，返回 0 ；a > b, 返回 1
 */
fun String?.onCompareTo(otherText: Double): Int =
    BigDecimalUtils.compareTo(this.onStringToDouble(), BigDecimal.valueOf(otherText).toDouble())

/**
 *
 * @receiver String? a
 * @param otherText String b
 * @return Int  a < b, 返回 -1 ； a = b，返回 0 ；a > b, 返回 1
 */
fun String?.onCompareTo(otherText: Int): Int =
    BigDecimalUtils.compareTo(this.onStringToDouble(), otherText)

/**
 *  日期 时间  + 以后  , - 以前
 */
fun String?.onStringNextOrBeforeDateToCalendar(
    year: Int? = null,
    month: Int? = null,
    day: Int? = null,
) = onStringNextOrBeforeToCalendar(year, month, day, null, null, null)

/**
 *  年 时间  + 以后  , - 以前
 */
fun String?.onStringNextOrBeforeYearToCalendar(
    year: Int? = null,
) = onStringNextOrBeforeToCalendar(year, null, null, null, null, null)

/**
 *  月份 时间  + 以后  , - 以前
 */
fun String?.onStringNextOrBeforeMonthToCalendar(
    month: Int? = null,
) = onStringNextOrBeforeToCalendar(null, month, null, null, null, null)

/**
 *  天 时间  + 以后  , - 以前
 */
fun String?.onStringNextOrBeforeDayToCalendar(
    day: Int? = null,
) = onStringNextOrBeforeToCalendar(null, null, day, null, null, null)

/**
 *  日期 时间  + 以后  , - 以前
 */
fun String?.onStringNextOrBeforeTimeToCalendar(
    hour: Int? = null,
    minute: Int? = null,
    second: Int? = null,
) = onStringNextOrBeforeToCalendar(null, null, null, hour, minute, second)

/**
 *  时 时间  + 以后  , - 以前
 */
fun String?.onStringNextOrBeforeHourToCalendar(
    hour: Int? = null,
) = onStringNextOrBeforeToCalendar(null, null, null, hour, null, null)

/**
 *  分钟 时间  + 以后  , - 以前
 */
fun String?.onStringNextOrBeforeMinuteToCalendar(
    minute: Int? = null,
) = onStringNextOrBeforeToCalendar(null, null, null, null, minute, null)

/**
 *  秒 时间  + 以后  , - 以前
 */
fun String?.onStringNextOrBeforeSecondToCalendar(
    second: Int? = null,
) = onStringNextOrBeforeToCalendar(null, null, null, null, null, second)

/**
 *  时间  + 以后  , - 以前
 */
fun String?.onStringNextOrBeforeToCalendar(
    year: Int? = null,
    month: Int? = null,
    day: Int? = null,
    hour: Int? = null,
    minute: Int? = null,
    second: Int? = null,
): Calendar {
    return this.onStringToCalendar().apply {
        if (year != null) {
            add(Calendar.YEAR, year)
        }
        if (month != null) {
            add(Calendar.MONTH, month)
        }
        if (day != null) {
            add(Calendar.DATE, day)
        }
        if (hour != null) {
            //24 小时
            add(Calendar.HOUR_OF_DAY, hour)
        }
        if (minute != null) {
            add(Calendar.MINUTE, minute)
        }
        if (second != null) {
            add(Calendar.SECOND, second)
        }
    }
}

/**
 * String 转 Calendar
 */
fun String?.onStringToCalendar(text: String? = null): Calendar {
    var pattern: String = StringUtils.null2Length0(text)
    if (StringUtils.isTrimEmpty(pattern)) {
        if (StringUtils.null2Length0(this).length == 10) {
            pattern = "yyyy-MM-dd"
        } else {
            pattern = "yyyy-MM-dd HH:mm:ss"
        }
    }
    return setStringToCalendar(this, pattern)
}

private fun setStringToCalendar(str_time: String?, str_pattern: String): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time =
        if (StringUtils.isTrimEmpty(str_time)) {
            TimeUtils.getNowDate()
        } else {
            TimeUtils.string2Date(str_time, str_pattern).let {
                if (it != null) {
                    it
                } else {
                    TimeUtils.getNowDate()
                }
            }
        }
    return calendar
}
