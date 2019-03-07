package com.kanasinfo.kt.platform.core.inject

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer

/**
 * @author gefangshuai
 * @createdAt 2019-03-07 16:48
 **/
interface IWebSecurityConfigInject {
    /**
     * 注入自定义url控制
     */
    fun addUrlAntMatchers(urlRegistry: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry)

}