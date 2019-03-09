package com.kanasinfo.platform.demo.config

import com.kanasinfo.platform.core.inject.IWebSecurityConfigInject
import com.kanasinfo.platform.model.base.Holder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.stereotype.Component

/**
 * @author gefangshuai
 * @createdAt 2019-03-07 16:58
 **/
@Component
class WebSecurityInject: IWebSecurityConfigInject {
    override fun initBaseHolder(): Holder? {
        return Holder(
            name = "平台"
        )
    }

    override fun addUrlAntMatchers(urlRegistry: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry) {
        urlRegistry.antMatchers("/api/hello").permitAll()
        urlRegistry.antMatchers("/api/init").permitAll()
        urlRegistry.antMatchers("/api/data").permitAll()
    }
}