package com.kanasinfo.kt.ext

import java.util.Random



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

/**
 * 字符串不为空或空白
 */
fun String?.isPresent(): Boolean {
    return !this.isNullOrBlank()
}

class StringExt {
    companion object {
        /**
         * 生成指定长度的随机字符串
         */
        fun randomStr(len: Int): String {
            var i: Int  //生成的随机数
            var count = 0 //生成的密码的长度
            // 密码字典
            val str = charArrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
            val stringBuffer = StringBuffer("")
            val r = Random()
            while (count < len) {
                i = r.nextInt(str.size)
                stringBuffer.append(str[i])
                count++
            }
            return stringBuffer.toString()
        }
    }
}