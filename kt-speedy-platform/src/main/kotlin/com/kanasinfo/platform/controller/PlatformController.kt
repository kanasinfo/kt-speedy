package com.kanasinfo.platform.controller

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author gefangshuai
 * @createdAt 2019-03-11 15:26
 **/
@RestController
@RequestMapping("/pt")
class PlatformController {
    @GetMapping("/userinfo")
    fun getUserInfo(): Any? {
        return SecurityContextHolder.getContext().authentication.principal
    }
}