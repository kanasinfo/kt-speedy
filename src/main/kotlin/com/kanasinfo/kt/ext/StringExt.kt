package com.kanasinfo.kt.ext

/**
 * 路径追加
 */
fun String.pathAppend(path: String): String {
    return this.removeSuffix("/") + path.removeSuffix("/")
}