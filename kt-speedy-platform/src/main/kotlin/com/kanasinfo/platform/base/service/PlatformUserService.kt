package com.kanasinfo.platform.base.service

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.data.jpa.SupportService
import com.kanasinfo.platform.exception.BusinessException
import com.kanasinfo.platform.exception.UserException
import com.kanasinfo.platform.base.model.PlatformUser
import com.kanasinfo.platform.base.model.UserCertificate
import com.kanasinfo.platform.base.repository.PlatformUserRepository
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
    fun createPlatformUser(
        loginName: String,
        password: String,
        holderId: String? = null,
        type: UserCertificate.Type = UserCertificate.Type.CUSTOMIZE
    ): PlatformUser {
        val platformUser = create(password)
        platformUser.userCertificate = userCertificateService.createUserCertificate(platformUser, loginName, type)
        holderId?.let {
            platformUser.holderProfile = holderProfileService.createHolderProfile(platformUser, holderId)
        }
        return platformUser
    }

    @Transactional
    fun addPlatformUser(loginName: String, nickname: String, holderId: String, type: UserCertificate.Type): PlatformUser {
        var platformUser = findUserCertificateByAccount(loginName)?.platformUser ?: PlatformUser()
        if (holderProfileService.findByHolderAndPlatform(holderId, platformUser) != null) {
            return platformUser
        }
        platformUser = save(platformUser)
        platformUser.userCertificate = userCertificateService.createUserCertificate(platformUser, loginName, type)
        platformUser.holderProfile = holderProfileService.createHolderProfile(platformUser, holderId, nickname)
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

    @Throws(BusinessException::class)
    @Transactional
    fun changePassword(
        userId: String,
        oldPassword: String,
        password: String,
        confirmPassword: String
    ) {
        val user = getOne(userId) ?: throw UserException.USER_NOT_FOUND_EXCEPTION
        if (passwordEncoder.matches(oldPassword, user.password)) {
            if (password != confirmPassword) {
                throw UserException.USER_PASSWORD_MISMATCH_EXCEPTION
            }
            user.password = passwordEncoder.encode(password)
            save(user)
        } else {
            throw UserException.USER_VERIFICATION_FAILED_EXCEPTION
        }
    }

}