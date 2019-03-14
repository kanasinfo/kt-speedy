package com.kanasinfo.platform.exception

import org.springframework.http.HttpStatus

/**
 * @author gefangshuai
 * @createdAt 2019-03-09 16:34
 **/
class HolderException{
    companion object {
        /**
         * 租户未找到
         */
        val HOLDER_NOT_FOUND_EXCEPTION = BusinessException("HOLDER_NOT_FOUND", "HOLDER_NOT_FOUND", HttpStatus.NOT_FOUND)
        /**
         * 租户不能为空
         */
        val ANONYMOUS_NOT_ALLOWED_EXCEPTION = BusinessException("ANONYMOUS_NOT_ALLOWED", "ANONYMOUS_NOT_ALLOWED", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}