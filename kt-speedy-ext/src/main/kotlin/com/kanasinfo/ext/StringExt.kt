package com.kanasinfo.ext

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.joda.time.DateTime
import java.util.*
import java.util.regex.Pattern


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
    return if (this.length <= count)
        this
    else {
        this.substring(0, count - 1)
    }
}

/**
 * 字符串不为空或空白
 */
fun String?.presentOrNull(): String?{
    return if(this.isNullOrBlank())
        null
    else
        this
}

fun String?.isPresent(): Boolean {
    return !this.isNullOrBlank()
}

fun String?.isNotPresent(): Boolean {
    return !this.isPresent()
}

fun <T> String.fromJsonToObject(t: Class<T>): T {
    return jacksonObjectMapper().readValue(this, t)
}

fun <T> String.fromJsonToList(t: Class<T>): List<T> {
    val mapper = jacksonObjectMapper()
    val javaType = mapper.typeFactory.constructParametricType(List::class.java, t)
    return mapper.readValue<List<T>>(this, javaType)
}

fun String.md5(): String {
    return EncryptExt.md5(this)
}

fun String.sh1(): String {
    return EncryptExt.sha1(this)
}

/**
 * 去除字符串中间所有的空格\t、回车\n、换行符\r、制表符\t
 */
fun String.trimForce(): String {
    val p = Pattern.compile("\\s*|\t|\r|\n")
    val m = p.matcher(this)
    return m.replaceAll("")
}

fun String.toDate(pattern: String): Date{
    return DateTimeExt.parse(this, pattern).toDate()
}
fun String.toDateTime(pattern: String): DateTime {
    return DateTimeExt.parse(this, pattern)
}

fun String.splitChild(delimiter: String, index: Int): String? {
    val sts = this.split(delimiter)
    return if (sts.size > index) {
        sts[index]
    }else{
        null
    }
}

/**
 * 根据传入的路径，查询json，返回json子对象，eg： {"a": {"b": 2}}.findFromJson("a.b") = 2
 */
fun String.findFromJson(path: String): Any? {
    val map = this.fromJsonToObject(HashMap::class.java)
    val paths = path.split(".")
    if (paths.isNotEmpty()) {
        val key = paths[0]
        val result = map[key]
        if (paths.size > 1 && result != null) {
            return result.toJson().findFromJson(path.substringAfter("$key."))
        } else if (paths.size == 1) {
            return result
        }
    }

    return null
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
