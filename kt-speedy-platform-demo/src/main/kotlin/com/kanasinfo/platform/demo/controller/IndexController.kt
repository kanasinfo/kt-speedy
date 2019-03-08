package com.kanasinfo.platform.demo.controller

import com.kanasinfo.platform.model.PlatformUser
import com.kanasinfo.platform.model.UserCertificate
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
    private lateinit var userCertificateService: UserCertificateService
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @GetMapping
    fun index() = "get index"

    @GetMapping("/hello")
    fun hello() = "hello world"

    @GetMapping("/init")
    fun initUser(): PlatformUser{
        var platformUser = PlatformUser()
        platformUser.password = passwordEncoder.encode("123456")
        platformUser = platformUserService.save(platformUser)

        val userCertificate = UserCertificate(loginName = "admin")
        userCertificate.platformUser = platformUser
        userCertificateService.save(userCertificate)
        return platformUser
    }

    @GetMapping("/data")
    fun getData(): Any? {
        return SecurityContextHolder.getContext().authentication.principal
    }
}