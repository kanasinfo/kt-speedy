package com.kanasinfo.platform.demo.controller

import com.kanasinfo.platform.model.PlatformUser
import com.kanasinfo.platform.model.UserCertificate
import com.kanasinfo.platform.service.HolderService
import com.kanasinfo.platform.service.PlatformUserService
import com.kanasinfo.platform.service.UserCertificateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author gefangshuai
 * @createdAt 2019-03-07 14:13
 **/
@RestController
class IndexController {
    @Autowired
    private lateinit var platformUserService: PlatformUserService
    @Autowired
    private lateinit var holderService: HolderService

    @GetMapping
    fun index() = "get index"

    @GetMapping("/hello")
    fun hello() = "hello world"

    @GetMapping("/init")
    fun initUser(): PlatformUser {
        SecurityContextHolder.getContext().authentication.principal
        return platformUserService.createPlatformUser("admin", "admin", holderService.findFirst().id)
    }

    @GetMapping("/data")
    fun getData(): Any? {
        return platformUserService.findAll()
    }
}