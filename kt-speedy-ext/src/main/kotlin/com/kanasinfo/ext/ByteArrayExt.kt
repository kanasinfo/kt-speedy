package com.kanasinfo.ext

import net.sf.jmimemagic.Magic

/**
 * 获取数据所对应的mimeType
 */
fun ByteArray.mimeType(): String? {
    return Magic.getMagicMatch(this).mimeType
}

fun ByteArray.toHex() : String{
    val HEX_CHARS = "0123456789ABCDEF".toCharArray()
    val result = StringBuffer()
    forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(HEX_CHARS[firstIndex])
        result.append(HEX_CHARS[secondIndex])
    }

    return result.toString()
}