package com.kanasinfo.platform.utils

/**
 * @author gefangshuai
 * @createdAt 2019-03-08 10:44
 **/
object RedisKey{
    fun getTokenKey(userId: String): String {
        return "kt_user_token_$userId"
    }
}