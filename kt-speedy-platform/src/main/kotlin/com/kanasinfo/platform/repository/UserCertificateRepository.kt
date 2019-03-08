package com.kanasinfo.platform.repository

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.platform.model.UserCertificate
import org.springframework.stereotype.Repository

/**
 * @author gefangshuai
 * @createdAt 2019-03-06 14:12
 **/
@Repository
interface UserCertificateRepository : SupportRepository<UserCertificate, String> {
    fun findByLoginName(username: String): UserCertificate?
}