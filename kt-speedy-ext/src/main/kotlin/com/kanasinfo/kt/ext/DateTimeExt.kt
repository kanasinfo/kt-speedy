package com.kanasinfo.kt.ext

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.time.LocalDateTime
import java.time.ZoneId
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
    return this.millisOfDay().withMinimumValue()
}

/**
 * 获取一天的结束
 */
fun DateTime.endOfDay(): DateTime {
    return this.millisOfDay().withMaximumValue()
}

/**
 * 去除日期，只留时间
 */
fun DateTime.onlyTime(): DateTime {
    return DateTimeExt.parse(this.toString("HH:mm"), "HH:mm")
}

/**
 * 去除时间，只留日期
 */
fun DateTime.onlyDate(): DateTime {
    return DateTimeExt.parse(this.toString("yyyy-MM-dd"), "yyyy-MM-dd")
}

fun DateTime.startOfMonth(): DateTime {
    return this.dayOfMonth().withMinimumValue().millisOfDay().withMinimumValue()
}

fun DateTime.endOfMonth(): DateTime {
    return this.dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue()
}

fun Date.onlyTime(): Date {
    return DateTime(this).onlyTime().toDate()
}

fun Date.startOfMonth(): Date {
    return this.toDateTime().startOfMonth().toDate()
}

fun Date.endOfMonth(): Date {
    return this.toDateTime().endOfMonth().toDate()
}

fun Date.onlyDate(): Date {
    return DateTime(this).onlyDate().toDate()
}

fun Date.format(pattern: String): String {
    return DateTime(this).toString(pattern)
}

fun Date.toDateTime(): DateTime = DateTime(this)

/**
 * 转换为Date
 */
fun java.time.LocalDate.toDate(): Date{
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

/**
 * 转换为Date
 */
fun LocalDateTime.toDate(): Date{
    return Date.from(this.atZone(java.time.ZoneId.systemDefault()).toInstant())
}