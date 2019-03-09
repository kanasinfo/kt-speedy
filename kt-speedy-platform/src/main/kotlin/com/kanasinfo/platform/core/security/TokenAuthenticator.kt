package com.kanasinfo.platform.core.security

import cn.hutool.core.codec.Base64
import com.kanasinfo.ext.fromJsonToObject
import com.kanasinfo.ext.isPresent
import com.kanasinfo.platform.model.PlatformUser
import com.kanasinfo.platform.utils.RedisKey
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import java.time.Duration
import javax.servlet.http.HttpServletRequest

@Component
class TokenAuthenticator {
    val logger = LoggerFactory.getLogger(TokenAuthenticator::class.java)!!
    @Value("\${ks.platform.token-secret-key}")
    private lateinit var tokenSecretKey: String
    @Value("\${ks.platform.expiration-day}")
    private lateinit var expirationDay: Duration
    @Value("\${ks.platform.multi-login}")
    private var multiLogin: Boolean? = false
    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TOKEN_COOKIE_KEY = "Token"
    }

    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    private fun getExpiration() = DateTime.now().plusDays(expirationDay.toDays().toInt()).toDate()

    private fun createToken(authentication: Authentication, user: PlatformUser): String {

        return Jwts.builder()
                // 保护权限角色
                .setSubject(authentication.name)
                .claim("userId", user.id)
                .claim("type", user.userCertificate?.type)
                // 有效时间
                .setExpiration(getExpiration())
                // 签名设置
                .signWith(SignatureAlgorithm.HS512, tokenSecretKey.toByteArray(Charsets.UTF_8))
                .compact()
    }

    /**
     * 创建登录令牌
     */
    fun createAuthentication(authentication: Authentication, user: PlatformUser): String {
        return createToken(authentication, user)
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
                val data = String(org.apache.commons.codec.binary.Base64.decodeBase64(cookie.value)).fromJsonToObject(Map::class.java)
                token = data["token"]?.toString()
            }
        }
        logger.debug("token: $token")
        try {
            if (token.isPresent()) {
                token = token.replace("Bearer", "")
                // 解析 Token
                val claims = Jwts.parser()
                    // 验签
                    .setSigningKey(tokenSecretKey.toByteArray(Charsets.UTF_8))
                    // 去掉 Bearer
                    .parseClaimsJws(Base64.decodeStr(token))
                    .body

                // 拿用户名
                val user = claims.subject
                // 得到 权限（角色）
                val userId = claims["userId"] as String

                // 返回验证令牌
                return if (user.isPresent()) {
                    try {
                        if (false == multiLogin) {
                            val dbToken = stringRedisTemplate.opsForValue().get(RedisKey.getTokenKey(userId))
                            if (token != dbToken) {
                                return null
                            }
                        }
                        UsernamePasswordAuthenticationToken(CustomerUserPrincipal(userId, request), null, null)
                    } catch (e: Exception) {
                        logger.debug(e.message, e)
                        return null
                    }
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            logger.debug(e.message, e)
            return null
        }
        return null
    }
}