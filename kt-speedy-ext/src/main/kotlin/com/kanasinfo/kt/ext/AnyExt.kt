package com.kanasinfo.kt.ext

import com.google.gson.Gson

fun Any.toJson(): String{
    return Gson().toJson(this)
}
fun Any?.notNull(f: ()-> Unit){
    if (this != null){
        f()
    }
}
