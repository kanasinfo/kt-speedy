package com.kanasinfo.kt.ext

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson

fun Any.toJson(): String{
    return ObjectMapper().writeValueAsString(this)
}
fun Any?.notNull(f: ()-> Unit){
    if (this != null){
        f()
    }
}
