package com.kanasinfo.platform.exception

import org.springframework.http.HttpStatus

/**
 * @author gefangshuai
 * @createdAt 2019-03-09 16:34
 **/
class HolderException{
    companion object {
        val HOLDER_NOT_FOUND_EXCEPTION = BusinessException("HOLDER_NOT_FOUND", "HOLDER_NOT_FOUND", HttpStatus.NOT_FOUND)
    }
}