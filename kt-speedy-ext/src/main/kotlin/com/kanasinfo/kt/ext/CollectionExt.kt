package com.kanasinfo.kt.ext

fun <T> Collection<T>?.isPresent(): Boolean {
    return this != null && this.isNotEmpty()
}
fun <T> Collection<T>?.isNotPresent(): Boolean {
    return this.isPresent()
}