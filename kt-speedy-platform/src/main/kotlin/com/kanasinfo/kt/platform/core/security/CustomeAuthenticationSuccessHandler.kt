package com.kanasinfo.kt.platform.core.security

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * Created by gefangshuai on 2017/6/25.
 */
class CustomeAuthenticationSuccessHandler : AuthenticationSuccessHandler {
    
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        response.writer.println("success")
    }
}