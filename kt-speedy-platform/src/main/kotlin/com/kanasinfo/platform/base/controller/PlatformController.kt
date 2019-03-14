package com.kanasinfo.platform.base.controller

import com.kanasinfo.platform.base.service.PlatformUserService
import com.kanasinfo.platform.utils.sessionIndividualUserId
import com.kanasinfo.web.EmptyJsonResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author gefangshuai
 * @createdAt 2019-03-11 15:26
 **/
@RestController
@RequestMapping("/pt")
class PlatformController {
    @Autowired
    private lateinit var platformUserService: PlatformUserService

    @GetMapping("/userinfo")
    fun getUserInfo(): Any? {
        return SecurityContextHolder.getContext().authentication.principal
    }

    @PostMapping("/password")
    fun modifyPassword(oldPassword: String, password: String, confirmPassword: String): EmptyJsonResponse{
        platformUserService.changePassword(sessionIndividualUserId(), oldPassword, password, confirmPassword)
        return EmptyJsonResponse()
    }
}