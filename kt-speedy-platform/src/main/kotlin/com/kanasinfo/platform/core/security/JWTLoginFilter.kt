package com.kanasinfo.platform.core.security

import cn.hutool.core.codec.Base64
import com.kanasinfo.ext.fromJsonToObject
import com.kanasinfo.ext.toJson
import com.kanasinfo.platform.base.service.PlatformUserService
import com.kanasinfo.platform.utils.RedisKey
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTLoginFilter(url: String, authManager: AuthenticationManager) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {


    @Value("\${ks.platform.token.expiration-day}")
    private lateinit var expirationDays: Duration
    @Value("\${ks.platform.multi-login:}")
    private var multiLogin: Boolean? = false

    @Autowired
    private lateinit var tokenAuthenticator: TokenAuthenticator
    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate
    @Autowired
    private lateinit var platformUserService: PlatformUserService


    init {
        authenticationManager = authManager
    }

    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication? {
        return try {
            val creds = IOUtils.toString(req.inputStream, Charsets.UTF_8.name()).fromJsonToObject(AccountCredentials::class.java)
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(creds.username, creds.password, Collections.emptyList())
            )
        } catch (e: Exception) {
            throw AccountExpiredException(e.message, e)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain,
        authentication: Authentication
    ) {

        val user = platformUserService.getOne((authentication.principal as CustomerUserPrincipal).userId)!!
        val token = tokenAuthenticator.createAuthentication(
            authentication,
            user.id,
            user.userCertificate?.type
        )

        logger.debug("Token[${user.userCertificate?.loginName}]: $token")

        val encodedToken = Base64.encode(token)

        if (false == multiLogin) {
            stringRedisTemplate.opsForValue()
                .set(RedisKey.getTokenKey(user.id), token, expirationDays.toDays(), TimeUnit.DAYS)
        }

        res.contentType = "application/json"
        res.characterEncoding = "UTF-8"
        res.status = HttpServletResponse.SC_OK

        res.writer?.println(
            hashMapOf(
                "token" to encodedToken
            ).toJson()
        )
    }
}