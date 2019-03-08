package com.kanasinfo.kt.web

import org.springframework.core.convert.converter.Converter

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

class DateConverter : Converter<String, Date> {

    override fun convert(source: String): Date? {
        val value = source.trim { it <= ' ' }
        if ("" == value) {
            return null
        }
        return when {
            source.matches("^\\d{4}-\\d{1,2}$".toRegex()) -> parseDate(source, formarts[0])
            source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$".toRegex()) -> parseDate(source, formarts[1])
            source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$".toRegex()) -> parseDate(source, formarts[2])
            source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$".toRegex()) -> parseDate(source, formarts[3])
            else -> throw IllegalArgumentException("Invalid boolean value '$source'")
        }
    }

    /**
     * 功能描述：
     * 格式化日期 * *
     * @param dateStr
     * String 字符型日期
     * @param format
     * String 格式
     * @return Date 日期
     */
    private fun parseDate(dateStr: String, format: String): Date? {
        var date: Date? = null
        try {
            val dateFormat = SimpleDateFormat(format)
            date = dateFormat.parse(dateStr)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return date
    }

    companion object {

        private val formarts = ArrayList<String>(4)

        init {
            formarts.add("yyyy-MM")
            formarts.add("yyyy-MM-dd")
            formarts.add("yyyy-MM-dd hh:mm")
            formarts.add("yyyy-MM-dd hh:mm:ss")
        }
    }

}