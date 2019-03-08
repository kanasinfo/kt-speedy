package com.kanasinfo.kt.platform.demo.config

import com.kanasinfo.kt.platform.core.inject.IWebSecurityConfigInject
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer

/**
 * @author gefangshuai
 * @createdAt 2019-03-07 16:58
 **/
class WebSecurityInject: IWebSecurityConfigInject {

    override fun addUrlAntMatchers(urlRegistry: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry) {
        urlRegistry.antMatchers("/api/hello").permitAll()
        urlRegistry.antMatchers("/api/init").permitAll()
    }
}