package com.kanasinfo.platform.utils

import com.kanasinfo.platform.core.security.CustomerUserPrincipal
import com.kanasinfo.platform.exception.HolderException
import org.springframework.security.core.context.SecurityContextHolder

/**
 * @author gefangshuai
 * @createdAt 2019-03-07 14:07
 **/

fun sessionIndividualUserId(): String {
    val principal = SecurityContextHolder.getContext().authentication?.principal
        ?: throw Exception("USER_AUTHENTICATE_FAIL_EXCEPTION")
    return (principal as CustomerUserPrincipal).userId
}

fun sessionUserPrincipal(): CustomerUserPrincipal? {
    val principal = SecurityContextHolder.getContext().authentication.principal
    return if (principal == null) {
        null
    } else {
        principal as CustomerUserPrincipal
    }
}

fun holderId(): String {
    return sessionUserPrincipal()?.holderId ?: throw HolderException.ANONYMOUS_NOT_ALLOWED_EXCEPTION
}