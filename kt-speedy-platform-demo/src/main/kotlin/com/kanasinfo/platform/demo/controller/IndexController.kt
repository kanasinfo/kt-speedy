package com.kanasinfo.platform.demo.controller

import com.kanasinfo.platform.base.model.PlatformUser
import com.kanasinfo.platform.base.service.HolderService
import com.kanasinfo.platform.base.service.PlatformUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
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
        return platformUserService.createPlatformUser("admin", "admin", holderService.findFirst().id)
    }

    @GetMapping("/data")
    fun getData(): Any? {
        return platformUserService.findAll()
    }
}