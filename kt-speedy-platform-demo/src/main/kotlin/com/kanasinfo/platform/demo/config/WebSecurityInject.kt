package com.kanasinfo.platform.demo.config

import com.kanasinfo.platform.base.model.holder.Holder
import com.kanasinfo.platform.core.inject.IWebSecurityConfigInject
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.stereotype.Component

/**
 * @author gefangshuai
 * @createdAt 2019-03-07 16:58
 **/
@Component
class WebSecurityInject: IWebSecurityConfigInject {
    override fun configure(auth: AuthenticationManagerBuilder) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun configure(auth: WebSecurity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addFilter(
        httpSecurity: HttpSecurity,
        authenticationManager: AuthenticationManager
    ) {

    }

    override fun initBaseHolder(): Holder? {
        return Holder().apply {
            this.name = "平台"
        }
    }

    override fun addUrlAntMatchers(urlRegistry: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry) {
        urlRegistry.antMatchers("/api/hello").permitAll()
        urlRegistry.antMatchers("/api/init").permitAll()
//        urlRegistry.antMatchers("/api/data").permitAll()
    }
}