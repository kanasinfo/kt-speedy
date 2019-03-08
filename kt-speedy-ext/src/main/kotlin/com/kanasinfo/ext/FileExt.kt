package com.kanasinfo.kt.ext

import net.sf.jmimemagic.Magic
import java.io.File

/**
 * 获取文件对应的mimeType
 * @param extensionHints whether or not to use extension to optimize order of content tests
 */
fun File.mimeType(extensionHints: Boolean): String? {
    return Magic.getMagicMatch(this, extensionHints).mimeType
}