package com.kanasinfo.platform.core.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kanasinfo.ext.fromJsonToObject
import com.kanasinfo.ext.isPresent
import com.kanasinfo.platform.model.PlatformUser
import com.kanasinfo.platform.utils.RSAUtils
import com.kanasinfo.platform.utils.RedisKey
import org.apache.commons.codec.binary.Base64
import org.apache.commons.io.IOUtils
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.Duration
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Component
class TokenAuthenticator {
    val logger = LoggerFactory.getLogger(TokenAuthenticator::class.java)!!
    @Value("\${ks.platform.token.private-key-path}")
    private lateinit var tokenPrivateKeyPath: String
    @Value("\${ks.platform.token.public-key-path}")
    private lateinit var tokenPublicKeyPath: String
    @Value("\${ks.platform.token.expiration-day}")
    private lateinit var expirationDay: Duration
    @Value("\${ks.platform.multi-login}")
    private var multiLogin: Boolean? = false

    private lateinit var publicKey: RSAPublicKey
    private lateinit var privateKey: RSAPrivateKey

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TOKEN_COOKIE_KEY = "Token"
        private const val TOKEN_ISSUER = "ks platform"
    }

    @PostConstruct
    fun initTokenKey(){
        this.publicKey = getPublicKey() as RSAPublicKey
        this.privateKey = getPrivateKey() as RSAPrivateKey
    }

    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    private fun getExpiration() = DateTime.now().plusDays(expirationDay.toDays().toInt()).toDate()
    private fun createToken(authentication: Authentication, user: PlatformUser): String {

        val algorithm = Algorithm.RSA256(publicKey, privateKey)
        return JWT.create()
            // 保护权限角色
            .withSubject(authentication.name)
            .withClaim("userId", user.id)
            .withClaim("type", user.userCertificate?.type.toString())
            .withExpiresAt(getExpiration())
            .withIssuer(TOKEN_ISSUER)
            .sign(algorithm)
    }

    /**
     * 创建登录令牌
     */
    fun createAuthentication(authentication: Authentication, user: PlatformUser): String {
        return createToken(authentication, user)
    }

    fun getPrivateKey(): PrivateKey {
        // 读取私钥
        val key = IOUtils.toString(ClassPathResource(tokenPrivateKeyPath).inputStream, Charsets.UTF_8)
        return RSAUtils.getPrivateKey(key)
    }

    fun getPublicKey(): PublicKey {
        // 读取公钥
        val key = IOUtils.toString(ClassPathResource(tokenPublicKeyPath).inputStream, Charsets.UTF_8)
        return RSAUtils.getPublicKey(key)
    }

    /**
     * 获取登录令牌
     */
    fun getAuthentication(request: HttpServletRequest): Authentication? {
        // 从Header中拿到token
        var token = request.getHeader(HEADER_AUTHORIZATION)
        if (token == null) {
            val cookie = WebUtils.getCookie(request, TOKEN_COOKIE_KEY)
            if (cookie != null) {
                token = String(Base64.decodeBase64(cookie.value))
            }
        } else {
            token = String(Base64.decodeBase64(token))
        }
        logger.debug("token: $token")
        try {
            if (token.isPresent()) {
                token = token.replace("Bearer", "")

                val algorithm = Algorithm.RSA256(publicKey, privateKey)
                val verifier = JWT.require(algorithm)
                    .withIssuer(TOKEN_ISSUER)
                    .build()
                val jwt = verifier.verify(token)


                // 拿用户名
                val user = jwt.subject
                // 得到 权限（角色）
                jwt.claims["userId"]?.asString()?.let { userId ->
                    // 返回验证令牌
                    return try {
                        if (false == multiLogin) {
                            val dbToken = stringRedisTemplate.opsForValue().get(RedisKey.getTokenKey(userId))
                            if (token != dbToken) {
                                logger.debug(
                                    "token is not match from redis",
                                    Exception("token is not match from redis")
                                )
                                return null
                            }
                        }
                        UsernamePasswordAuthenticationToken(CustomerUserPrincipal(userId, request), null, null)
                    } catch (e: Exception) {
                        logger.debug(e.message, e)
                        return null
                    }

                }
            }
        } catch (e: Exception) {
            logger.debug(e.message, e)
            return null
        }
        return null
    }
}