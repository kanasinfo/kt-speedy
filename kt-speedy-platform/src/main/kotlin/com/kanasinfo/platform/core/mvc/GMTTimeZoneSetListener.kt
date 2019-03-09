package com.kanasinfo.platform.core.mvc

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import java.util.*
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

/**
 * 设置默认时区为格林威治
 * Created by gefangshuai on 2017/5/24.
 */
class GMTTimeZoneSetListener : ServletContextListener {

    @Value("\${ks.platform.timezone.utc}")
    private var utcEnable: Boolean = false

    override fun contextInitialized(sce: ServletContextEvent) {
        if(utcEnable) {
            logger.info("set defalut timezone: GMT")
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        }
    }

    override fun contextDestroyed(sce: ServletContextEvent) {

    }

    companion object {
        private val logger = LoggerFactory.getLogger(GMTTimeZoneSetListener::class.java)
    }
}
