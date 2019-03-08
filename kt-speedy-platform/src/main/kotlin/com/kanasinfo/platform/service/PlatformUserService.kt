package com.kanasinfo.platform.service

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.data.jpa.SupportService
import com.kanasinfo.platform.model.PlatformUser
import com.kanasinfo.platform.model.UserCertificate
import com.kanasinfo.platform.repository.PlatformUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author gefangshuai
 * @createdAt 2019-03-06 14:03
 **/
@Service
@Transactional(readOnly = true)
class PlatformUserService: SupportService<PlatformUser, String>() {
    fun findUserCertificateByAccount(username: String): UserCertificate? {
        return userCertificateService.findByLoginName(username)
    }

    @Autowired
    private lateinit var platformUserRepository: PlatformUserRepository
    @Autowired
    private lateinit var userCertificateService: UserCertificateService

    override val repository: SupportRepository<PlatformUser, String>
        get() = platformUserRepository

}