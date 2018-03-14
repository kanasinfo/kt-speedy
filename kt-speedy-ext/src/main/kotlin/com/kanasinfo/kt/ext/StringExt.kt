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
 * like 查询拼接
 */
fun String.toLikeQuery(): String {
    return "%$this%"
}

/**
 * like 左侧模糊匹配
 */
fun String.toLeftLikeQuery() = "%$this"

/**
 * like 右侧模糊匹配
 */
fun String.toRightLikeQuery() = "$this%"

/**
 * 最大字符长度
 */
fun String.substringMax(count: Int): String {
    return if(this.length <= count)
        this
    else {
        this.substring(0, count - 1)
    }
}