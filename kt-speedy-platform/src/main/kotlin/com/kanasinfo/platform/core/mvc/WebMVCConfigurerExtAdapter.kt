package com.kanasinfo.platform.core.mvc

import com.kanasinfo.web.DateConverter
import org.springframework.boot.web.server.ErrorPage
import org.springframework.boot.web.server.ErrorPageRegistrar
import org.springframework.boot.web.server.ErrorPageRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ConversionServiceFactoryBean
import org.springframework.http.HttpStatus
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ThreadPoolExecutor


@Configuration
class WebMVCConfigurerExtAdapter : WebMvcConfigurer {

    val gmtTimeZoneSetListener: GMTTimeZoneSetListener
        @Bean
        get() = GMTTimeZoneSetListener()

    @Bean
    fun containerCustomizer(): ErrorPageRegistrar {
        return ErrorPageRegistrar { container ->
            val error404Page = ErrorPage(HttpStatus.NOT_FOUND, "/")
            container.addErrorPages(error404Page)
        }
    }

    /**
     * 国际化配置
     */
    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val lci = LocaleChangeInterceptor()
        lci.paramName = "lang"
        return lci
    }

    /**
     * 防止出现：Cannot change HTTP accept header - use a different locale resolution strategy异常
     *
     * @return
     */
    @Bean(name = ["localeResolver"])
    fun localeResolver(): LocaleResolver {
        val slr = CookieLocaleResolver()
        //设置默认区域,
        slr.setDefaultLocale(Locale.CHINA)
        slr.cookieMaxAge = 3600 * 24 * 7//设置cookie有效期.
        return slr
    }


    override fun addInterceptors(registry: InterceptorRegistry) {

    }

    @Bean
    fun conversionService(): ConversionServiceFactoryBean {
        val bean = ConversionServiceFactoryBean()
        bean.setConverters(setOf(DateConverter()))
        return bean
    }

    @Bean
    fun emailSendExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 50
        executor.setQueueCapacity(200)
        executor.threadNamePrefix = "EmailSend-"
        executor.setWaitForTasksToCompleteOnShutdown(true)
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(ThreadPoolExecutor.CallerRunsPolicy())
        executor.initialize()
        return executor
    }

    /**
     * 静态资源 映射
     */
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
            .setCachePeriod(3600 * 24)
    }

    @Bean
    fun errorPageRegistrar(): ErrorPageRegistrar {
        return MyErrorPageRegistrar()
    }
}


private class MyErrorPageRegistrar : ErrorPageRegistrar {

    override fun registerErrorPages(registry: ErrorPageRegistry) {
        registry.addErrorPages(ErrorPage(HttpStatus.NOT_FOUND, "/index.html"))
    }

}
