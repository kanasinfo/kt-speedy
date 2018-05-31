package com.kanasinfo.kt.ext

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

fun Any.toJson(): String{
    return jacksonObjectMapper().writeValueAsString(this)
}
fun Any?.notNull(f: ()-> Unit){
    if (this != null){
        f()
    }
}
fun Any.toMap(): Map<*, *>? {
    return jacksonObjectMapper().readValue(this.toJson())
}
