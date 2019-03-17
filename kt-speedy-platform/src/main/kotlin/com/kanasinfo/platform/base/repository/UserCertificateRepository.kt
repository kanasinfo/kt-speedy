package com.kanasinfo.platform.base.repository

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.platform.base.model.PlatformUser
import com.kanasinfo.platform.base.model.UserCertificate
import org.springframework.stereotype.Repository

/**
 * @author gefangshuai
 * @createdAt 2019-03-06 14:12
 **/
@Repository
interface UserCertificateRepository : SupportRepository<UserCertificate, String> {
    fun findByLoginName(username: String): UserCertificate?
    fun findByPlatformUser(platformUser: PlatformUser): List<UserCertificate>
}