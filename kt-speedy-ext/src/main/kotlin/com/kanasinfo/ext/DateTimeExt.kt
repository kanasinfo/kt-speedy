package com.kanasinfo.ext

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
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

        fun todayUTC(): Date {
            return org.joda.time.DateTime.now(org.joda.time.DateTimeZone.forTimeZone(java.util.TimeZone.getTimeZone("UTC"))).toDate()
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

/**
 * 获取当前时间的utc时区值
 */

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
    return SimpleDateFormat(pattern).format(this)
}

fun Date.toDateTime(): DateTime = DateTime(this)
fun Date.toDateTime(zone: TimeZone): DateTime = DateTime(this, DateTimeZone.forTimeZone(zone))
fun Date.toDateTimeUTC(): DateTime = DateTime(this, DateTimeZone.forTimeZone(TimeZone.getTimeZone("UTC")))

/**
 * 转换为Date
 */
fun java.time.LocalDate.toDate(): Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

/**
 * 转换为Date
 */
fun LocalDateTime.toDate(): Date {
    return Date.from(this.atZone(java.time.ZoneId.systemDefault()).toInstant())
}