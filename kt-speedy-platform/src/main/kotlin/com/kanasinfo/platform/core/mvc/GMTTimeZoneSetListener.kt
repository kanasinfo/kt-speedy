package com.kanasinfo.platform.core.mvc

import com.kanasinfo.ext.isPresent
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
    private var utcEnable: Boolean? = false

    @Value("\${ks.platform.timezone}")
    private var timezone: String? = null

    override fun contextInitialized(sce: ServletContextEvent) {
        if (utcEnable == true) {
            logger.info("set defalut timezone: GMT")
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        }
        if (timezone.isPresent()) {
            logger.info("set defalut timezone: $timezone")
            TimeZone.setDefault(TimeZone.getTimeZone(timezone))
        }
    }

    override fun contextDestroyed(sce: ServletContextEvent) {

    }

    companion object {
        private val logger = LoggerFactory.getLogger(GMTTimeZoneSetListener::class.java)
    }
}
