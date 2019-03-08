package com.kanasinfo.kt.ext

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object EncryptExt {

    @JvmStatic
    fun main(args: Array<String>) {
        // md5加密测试
        val md5_1 = md5("123")
        val md5_2 = md5("abc")
        println(md5_1 + "\n" + md5_2)
        println("md5 length: " + md5_1!!.length)
        // sha加密测试
        val sha_1 = sha1("123")
        val sha_2 = sha1("abc")
        println(sha_1 + "\n" + sha_2)
        println("sha length: " + sha_1!!.length)
    }

    // md5加密
    fun md5(inputText: String): String {
        return encrypt(inputText, "md5")
    }

    // sha加密
    fun sha1(inputText: String): String {
        return encrypt(inputText, "sha-1")
    }

    /**
     * md5或者sha-1加密
     *
     * @param inputText
     * 要加密的内容
     * @param algorithmName
     * 加密算法名称：md5或者sha-1，不区分大小写
     * @return
     */
    private fun encrypt(inputText: String?, algorithmName: String?): String {
        var algorithmName = algorithmName
        if (inputText == null || "" == inputText.trim { it <= ' ' }) {
            throw IllegalArgumentException("请输入要加密的内容")
        }
        if (algorithmName == null || "" == algorithmName.trim { it <= ' ' }) {
            algorithmName = "md5"
        }
        var encryptText = ""
        try {
            val m = MessageDigest.getInstance(algorithmName)
            m.update(inputText.toByteArray(charset("UTF8")))
            val s = m.digest()
            // m.digest(inputText.getBytes("UTF8"));
            return s.toHex()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return encryptText
    }

    /**
     * 生成摘要
     */
    fun sha1MessageDigest(saltKey: String, text: String): String? {
        return sha1(String.format("%s{%s}", text, saltKey))
    }
}