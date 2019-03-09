package com.kanasinfo.platform.core.runnner

import com.kanasinfo.platform.core.inject.IWebSecurityConfigInject
import com.kanasinfo.platform.service.HolderService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

/**
 * 初始化holder
 * @author gefangshuai
 * @createdAt 2019-03-09 13:04
 **/
@Component
@Order(100)
class HolderInitRunner : CommandLineRunner {
    @Autowired(required = false)
    private var webSecurityConfigInject: IWebSecurityConfigInject? = null
    @Autowired
    private lateinit var holderService: HolderService
    private val logger = LoggerFactory.getLogger(HolderInitRunner::class.java)

    override fun run(vararg args: String?) {
        if (holderService.count() == 0L) {
            webSecurityConfigInject?.initBaseHolder()?.let {
                logger.info("初始化系统默认租户: $it")
                holderService.save(it)

            }
        }
    }

}