package com.kanasinfo.ext

import java.util.*

/**
 * 生成19位的uuid
 */
object KUID {
    private val digits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
    private val digitMap = mutableMapOf<Char, Int>()
    /**
     * 支持的最大进制数
     */
    private val MAX_RADIX = digits.size

    /**
     * 支持的最小进制数
     */
    private const val MIN_RADIX = 2

    init {
        for (i in 0 until digits.size) {
            digitMap[digits[i]] = i
        }
    }

    private fun toString(i: Long, radix: Int): String {
        var j = i
        var r = radix
        if (r < MIN_RADIX || r > MAX_RADIX)
            r = 10
        if (r == 10)
            return java.lang.Long.toString(j)

        val size = 65
        var charPos = 64

        val buf = CharArray(size)
        val negative = j < 0

        if (!negative) {
            j = -j
        }

        while (j <= -r) {
            buf[charPos--] = digits[(-(j % r)).toInt()]
            j /= r
        }
        buf[charPos] = digits[(-j).toInt()]

        if (negative) {
            buf[--charPos] = '-'
        }

        return String(buf, charPos, size - charPos)
    }

    private fun forInputString(s: String): NumberFormatException {
        return NumberFormatException("For input string: \"$s\"")
    }

    /**
     * 将字符串转换为长整型数字
     *
     * @param s
     * 数字字符串
     * @param radix
     * 进制数
     * @return
     */
    private fun toNumber(s: String?, radix: Int): Long {
        if (s == null) {
            throw NumberFormatException("null")
        }

        if (radix < MIN_RADIX) {
            throw NumberFormatException("radix " + radix
                    + " less than Numbers.MIN_RADIX")
        }
        if (radix > MAX_RADIX) {
            throw NumberFormatException("radix " + radix
                    + " greater than Numbers.MAX_RADIX")
        }

        var result: Long = 0
        var negative = false
        var i = 0
        val len = s.length
        var limit = -java.lang.Long.MAX_VALUE
        val multmin: Long
        var digit: Int?

        if (len > 0) {
            val firstChar = s[0]
            if (firstChar < '0') {
                if (firstChar == '-') {
                    negative = true
                    limit = java.lang.Long.MIN_VALUE
                } else if (firstChar != '+')
                    throw forInputString(s)

                if (len == 1) {
                    throw forInputString(s)
                }
                i++
            }
            multmin = limit / radix
            while (i < len) {
                digit = digitMap[s[i++]]
                if (digit == null) {
                    throw forInputString(s)
                }
                if (digit < 0) {
                    throw forInputString(s)
                }
                if (result < multmin) {
                    throw forInputString(s)
                }
                result *= radix.toLong()
                if (result < limit + digit) {
                    throw forInputString(s)
                }
                result -= digit.toLong()
            }
        } else {
            throw forInputString(s)
        }
        return if (negative) result else -result
    }


    private fun digits(`val`: Long, digits: Int): String {
        val hi = 1L shl digits * 4
        return toString(hi or (`val` and hi - 1), MAX_RADIX).substring(1)
    }

    /**
     * 产生19位的UUID
     * @return
     */
    fun get(): String {
        //产生UUID
        val uuid = UUID.randomUUID()
        val sb = StringBuilder()
        //分区转换
        sb.append(digits(uuid.mostSignificantBits shr 32, 8))
        sb.append(digits(uuid.mostSignificantBits shr 16, 4))
        sb.append(digits(uuid.mostSignificantBits, 4))
        sb.append(digits(uuid.leastSignificantBits shr 48, 4))
        sb.append(digits(uuid.leastSignificantBits, 12))
        return sb.toString()
    }

    /**
     * 产生UUID
     */
    fun getUUID(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
}
