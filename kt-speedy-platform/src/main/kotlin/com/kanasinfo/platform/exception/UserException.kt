package com.kanasinfo.platform.exception

import org.springframework.http.HttpStatus

/**
 * @author gefangshuai
 * @createdAt 2019-03-09 16:34
 **/
class UserException{
    companion object {
        /**
         * 用户没找到
         */
        val USER_NOT_FOUND_EXCEPTION = BusinessException("USER_NOT_FOUND", "USER_NOT_FOUND", HttpStatus.NOT_FOUND)
        /**
         * 用户身份验证失败
         */
        val USER_VERIFICATION_FAILED_EXCEPTION = BusinessException("USER_VERIFICATION_FAILED", "USER_VERIFICATION_FAILED", HttpStatus.CONFLICT)
        /**
         * 用户两次密码不匹配
         */
        val USER_PASSWORD_MISMATCH_EXCEPTION = BusinessException("USER_PASSWORD_MISMATCH", "USER_PASSWORD_MISMATCH", HttpStatus.CONFLICT)

        /**
         * 用户创建失败
         */
        val USER_CREATE_FAIL_EXCEPTION = BusinessException("USER_CREATE_FAIL", "USER_CREATE_FAIL", HttpStatus.INTERNAL_SERVER_ERROR)
    }

}