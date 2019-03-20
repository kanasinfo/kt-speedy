package com.kanasinfo.platform.core.runnner

import com.kanasinfo.platform.base.service.HolderService
import com.kanasinfo.platform.core.inject.IWebSecurityConfigInject
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

/**
 * 初始化holder
 * @author gefangshuai
 * @createdAt 2019-03-09 13:04
 **/
@Component
@Order(100)
class HolderInitRunner : ApplicationRunner {
    @Autowired(required = false)
    private var webSecurityConfigInject: IWebSecurityConfigInject? = null
    @Autowired
    private lateinit var holderService: HolderService
    private val logger = LoggerFactory.getLogger(HolderInitRunner::class.java)

    override fun run(args: ApplicationArguments?) {
        if (holderService.count() == 0L) {
            webSecurityConfigInject?.initBaseHolder()?.let {
                logger.info("初始化系统默认租户: $it")
                holderService.save(it)
            }
        }
    }

}