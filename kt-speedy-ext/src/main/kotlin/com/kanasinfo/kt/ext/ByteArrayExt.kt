package com.kanasinfo.kt.ext

import net.sf.jmimemagic.Magic

/**
 * 获取数据所对应的mimeType
 */
fun ByteArray.mimeType(): String? {
    return Magic.getMagicMatch(this).mimeType
}