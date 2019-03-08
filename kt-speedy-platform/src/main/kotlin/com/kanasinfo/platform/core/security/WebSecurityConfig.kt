package com.kanasinfo.platform.core.security

import com.kanasinfo.platform.core.inject.InjectContextLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.AuditorAware
import org.springframework.http.HttpMethod
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService
    @Autowired
    private lateinit var injectContextLoader: InjectContextLoader

    @Value("\${spring.mvc.servlet.path}")
    private var servletPath: String? = null

    fun getServletPath() = servletPath?: ""

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
            .userDetailsService<UserDetailsService>(customUserDetailsService)
    }

    @Bean
    fun restAuthenticationEntryPoint() = RestAuthenticationEntryPoint()

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val urlRegistry = http
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS)
            .permitAll()
        injectContextLoader.getWebSecurityConfigInject()?.addUrlAntMatchers(urlRegistry)
        urlRegistry.antMatchers("${getServletPath()}/webjars/**").permitAll()         // 允许
                    .antMatchers("${getServletPath()}/resources/**").permitAll()         // 允许
                    .antMatchers("${getServletPath()}/loginfail").permitAll()          // 允许
                    .antMatchers("${getServletPath()}/logout").permitAll()          // 允许
                    .antMatchers("${getServletPath()}/callback/**").permitAll()          // 允许
                    .antMatchers("${getServletPath()}/**").authenticated()
                .and()
                // mobile b login control
                .addFilterBefore(
                    jwtLoginFilter(),
                        UsernamePasswordAuthenticationFilter::class.java
                )
                // check control
                .addFilterBefore(
                        jwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter::class.java
                )
                .logout()
                .logoutUrl("${getServletPath()}/logout")
                .logoutSuccessHandler { _, response, _ ->
                    response.writer.println("logout success!")
                }

    }

    @Bean
    fun authenticationSuccessHandler() = CustomeAuthenticationSuccessHandler()

    /**
     * toB mobile 登录验证
     */
    @Bean
    fun jwtLoginFilter(): JWTLoginFilter {
        return JWTLoginFilter("${getServletPath()}/login", authenticationManagerBean())
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun jwtAuthenticationFilter(): JWTAuthenticationFilter {
        return JWTAuthenticationFilter()
    }

    @Bean
    fun customPermissionEvaluator(): PermissionEvaluator {
        return CustomPermissionEvaluator()
    }

    @Bean
    fun jpaAuditorProvider(): AuditorAware<String> {
        return SpringSecurityAuditorAwareForJpa()
    }
    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers("/static/**")
            .antMatchers("/css/**")
            .antMatchers("/lib/**")
            .antMatchers("/dll/**")
            .antMatchers("/img/**")
            .antMatchers("/js/**")
            .antMatchers("/fonts/**")
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(customUserDetailsService)
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
