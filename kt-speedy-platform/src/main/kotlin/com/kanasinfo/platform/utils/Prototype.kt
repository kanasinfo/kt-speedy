package com.kanasinfo.platform.utils

import com.kanasinfo.kt.platform.core.security.CustomerUserPrincipal
import org.springframework.security.core.context.SecurityContextHolder

/**
 * @author gefangshuai
 * @createdAt 2019-03-07 14:07
 **/

fun sessionIndividualUserId(): String {
    val principal = SecurityContextHolder.getContext().authentication?.principal ?: throw Exception("USER_AUTHENTICATE_FAIL_EXCEPTION")
    return (principal as CustomerUserPrincipal).userId
}

fun sessionUserPrincipal(): CustomerUserPrincipal {
    return SecurityContextHolder.getContext().authentication.principal as CustomerUserPrincipal
}