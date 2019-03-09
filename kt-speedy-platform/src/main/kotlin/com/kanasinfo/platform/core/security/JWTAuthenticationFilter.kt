package com.kanasinfo.platform.core.security

import com.kanasinfo.ext.toJson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.util.WebUtils
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JWTAuthenticationFilter : GenericFilterBean() {
    @Autowired
    private lateinit var tokenAuthenticator: TokenAuthenticator

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        try {
            val req = request as HttpServletRequest
            val authentication = tokenAuthenticator.getAuthentication(req)
            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            e.printStackTrace()
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.writer.write(e.toJson())
        }
    }
}