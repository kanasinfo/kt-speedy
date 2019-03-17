package com.kanasinfo.platform.exception

import org.springframework.http.HttpStatus

/**
 * @author gefangshuai
 * @createdAt 2019-03-09 16:34
 **/
class HolderProfileException{
    companion object {
        /**
         * 租户未找到
         */
        val HOLDER_PROFILE_NOT_FOUND_EXCEPTION = BusinessException("HOLDER_PROFILE_NOT_FOUND", "HOLDER_PROFILE_NOT_FOUND", HttpStatus.NOT_FOUND)


        val HOLDER_PROFILE_EXISTS_EXCEPTION = BusinessException("HOLDER_PROFILE_EXISTS", "HOLDER_PROFILE_EXISTS", HttpStatus.CONFLICT)
    }
}