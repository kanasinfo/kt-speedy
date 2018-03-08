package com.kanasinfo.kt.ext

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

class DateTimeExt {
    companion object {
        fun parse(dateTime: String, pattern: String): DateTime {
            return DateTime.parse(dateTime, DateTimeFormat.forPattern(pattern))
        }
        /**
         * 今天
         */
        fun today(): Date {
            return DateTime.now().toDate()
        }
    }
}

/**
 * 获取一天的最开始
 */
fun DateTime.startOfDay(): DateTime {
    return this.withTime(0, 0, 0, 0)
}

/**
 * 获取一天的结束
 */
fun DateTime.endOfDay(): DateTime {
    return this.withTime(23, 59, 59, 999)
}

/**
 * 去除日期，只留时间
 */
fun DateTime.onlyTime(): DateTime {
    return DateTimeExt.parse(this.toString("HH:mm"), "HH:mm")
}

fun Date.onlyTime(): Date {
    return DateTime(this).onlyTime().toDate()
}

/**
 * 去除时间，只留日期
 */
fun DateTime.onlyDate(): DateTime {
    return DateTimeExt.parse(this.toString("yyyy-MM-dd"), "yyyy-MM-dd")
}

fun Date.onlyDate(): Date {
    return DateTime(this).onlyDate().toDate()
}

fun Date.format(pattern: String): String {
    return DateTime(this).toString(pattern)
}