package com.kanasinfo.platform.core.security

import com.kanasinfo.platform.utils.sessionIndividualUserId
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

/**
 * 用于jpa自动生成创建人和更新人信息
 */
internal class SpringSecurityAuditorAwareForJpa : AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {

        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication == null || !authentication.isAuthenticated || authentication.principal == "anonymousUser") {
            return Optional.empty()
        }

        return Optional.ofNullable(sessionIndividualUserId())
    }
}