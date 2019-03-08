package com.kanasinfo.ext

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

fun Any.toJson(ignoreNull: Boolean? = null): String {
    return if (true == ignoreNull) {
        jacksonObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(this)
    } else {
        jacksonObjectMapper().writeValueAsString(this)
    }
}

fun Any?.notNull(f: () -> Unit) {
    if (this != null) {
        f()
    }
}

fun Any.toMap(): Map<String, Any> {
    return jacksonObjectMapper().readValue(this.toJson())
}

fun Any?.toMapOrNull(): Map<String, Any>? {
    if(this == null)
        return null
    return jacksonObjectMapper().readValue(this.toJson())
}