package com.kanasinfo.web

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import org.springframework.util.Assert

@Component
class SpringContextKit : ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    private lateinit var applicationContext: ApplicationContext

    fun getApplicationContext(): ApplicationContext {
        return applicationContext
    }

    fun getBean(name: String): Any {
        return getApplicationContext().getBean(name)
    }

    fun <T> getBean(name: String, clazz: Class<T>): T {
        Assert.hasText(name, "name为空")
        return getApplicationContext().getBean(name, clazz)
    }
}