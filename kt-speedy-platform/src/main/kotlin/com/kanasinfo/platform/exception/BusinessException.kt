package com.kanasinfo.platform.exception

import org.springframework.http.HttpStatus

class BusinessException(val code: String, override val message: String?, var httpCode: HttpStatus? = null) : Exception() {
    companion object {
        /**
         * 数据未找到
         */
        val DATA_NOT_FOUND_EXCEPTION =
            BusinessException("DATA_NOT_FOUND", "data not found", HttpStatus.NOT_FOUND)
        /**
         * 数据重复
         */
        val DATA_DUPLICATED_EXCEPTION =
            BusinessException("DATA_DUPLICATED", "data duplicated", HttpStatus.CONFLICT)
    }
}