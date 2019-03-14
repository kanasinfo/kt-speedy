package com.kanasinfo.platform.core.inject

import com.kanasinfo.platform.base.model.holder.Holder
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

    /**
     * 初始化基本的租户，如果集成系统为单租户（或者无租户），则实现此方法。platform会将所有的业务逻辑自动带入此租户进行逻辑处理
     */
    fun initBaseHolder(): Holder?

}