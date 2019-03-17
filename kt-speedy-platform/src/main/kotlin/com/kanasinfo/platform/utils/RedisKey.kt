package com.kanasinfo.platform.utils

/**
 * @author gefangshuai
 * @createdAt 2019-03-08 10:44
 **/
object RedisKey{
    fun getTokenKey(userId: String): String {
        return "ks_user_token_$userId"
    }

    fun getUserActiveCodeKey(userId: String): String {
        return "ks_user_active_code_$userId"
    }
}