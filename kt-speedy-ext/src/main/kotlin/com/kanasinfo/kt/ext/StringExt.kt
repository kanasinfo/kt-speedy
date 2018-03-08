package com.kanasinfo.kt.ext

/**
 * 路径追加
 */
fun String.pathAppend(path: String): String {
    return this.removeSuffix("/") + "/" + path.removeSuffix("/").removePrefix("/")
}

/**
 * 获取文件类型
 */
fun String.getFileType(): String {
    return this.substringAfterLast(".")
}

/**
 * like查询拼接
 */
fun String.toLikeQuery(): String {
    return "%$this%"
}