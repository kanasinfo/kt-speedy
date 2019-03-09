package com.kanasinfo.platform.service

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.data.jpa.SupportService
import com.kanasinfo.platform.model.PlatformUser
import com.kanasinfo.platform.model.UserCertificate
import com.kanasinfo.platform.repository.PlatformUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * @author gefangshuai
 * @createdAt 2019-03-06 14:03
 **/
@Service
@Transactional(readOnly = true)
class PlatformUserService : SupportService<PlatformUser, String>() {

    @Autowired
    private lateinit var platformUserRepository: PlatformUserRepository
    @Autowired
    private lateinit var userCertificateService: UserCertificateService
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var holderProfileService: HolderProfileService

    override val repository: SupportRepository<PlatformUser, String>
        get() = platformUserRepository

    /**
     * 创建用户
     */
    @Transactional
    fun createPlatformUser(loginName: String, password: String, holderId: String, type: UserCertificate.Type = UserCertificate.Type.CUSTOMIZE): PlatformUser {
        val platformUser = create(password)
        platformUser.userCertificate = userCertificateService.createUserCertificate(platformUser, loginName, type)
        holderProfileService.createHolderProfile(platformUser, holderId)
        return platformUser
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun create(password: String): PlatformUser {
        val platformUser = PlatformUser()
        platformUser.password = passwordEncoder.encode(password)
        return save(platformUser)
    }


    fun findUserCertificateByAccount(username: String): UserCertificate? {
        return userCertificateService.findByLoginName(username)
    }

}