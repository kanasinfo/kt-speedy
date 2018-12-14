package com.kanasinfo.kt.ext

class BusinessException(val code: String, override val message: String?, var httpCode: Int? = null) : Exception() {
    companion object {
        /**
         * 数据未找到
         */
        val DATA_NOT_FOUND_EXCEPTION = BusinessException("DATA_NOT_FOUND", "data not found", 404)
        /**
         * 数据重复
         */
        val DATA_DUPLICATED_EXCEPTION = BusinessException("DATA_DUPLICATED", "data duplicated", 409)
    }
}