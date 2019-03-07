package com.kanasinfo.kt.platform.core.security

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kanasinfo.kt.platform.model.PlatformUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.servlet.http.HttpServletRequest

class CustomerUserPrincipal : UserDetails {

    constructor(user: PlatformUser) {
        this.user = user
        this.userId = user.id
    }


    constructor(userId: String, request: HttpServletRequest) {
        this.userId = userId
        this.request = request
    }

    constructor(user: PlatformUser, userId: String) {
        this.user = user
        this.userId = userId
    }

    var user: PlatformUser? = null
    var userId: String
    var access: MutableList<String> = mutableListOf()
    @JsonIgnore
    var request: HttpServletRequest? = null

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return access.map { SimpleGrantedAuthority(it) }
    }

    @JsonIgnore
    override fun getPassword() = this.user?.password


    override fun getUsername() = this.user?.userCertificate?.loginName

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isEnabled(): Boolean {
        return this.user?.userCertificate?.active == true
    }

}
