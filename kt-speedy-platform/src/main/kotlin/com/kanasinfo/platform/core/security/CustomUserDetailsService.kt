package com.kanasinfo.platform.core.security

import com.kanasinfo.platform.base.service.PlatformUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var platformUserService: PlatformUserService


    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val account = platformUserService.findUserCertificateByAccount(username) ?: throw UsernameNotFoundException(username)
        account.platformUser.userCertificate = account
        return CustomerUserPrincipal(account.platformUser, account.platformUser.id)
    }
}