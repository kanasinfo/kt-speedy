package com.kanasinfo.platform.service

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.data.jpa.SupportService
import com.kanasinfo.platform.model.UserCertificate
import com.kanasinfo.platform.repository.UserCertificateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author gefangshuai
 * @createdAt 2019-03-06 14:13
 **/
@Service
@Transactional(readOnly = true)
class UserCertificateService : SupportService<UserCertificate, String>() {
    fun findByLoginName(username: String): UserCertificate? {
        return userCertificateRepository.findByLoginName(username)
    }

    @Autowired
    private lateinit var userCertificateRepository: UserCertificateRepository

    override val repository: SupportRepository<UserCertificate, String>
        get() = userCertificateRepository

}