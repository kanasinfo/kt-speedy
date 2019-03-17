package com.kanasinfo.platform.base.service

import cn.hutool.core.codec.Base64
import cn.hutool.core.util.RandomUtil
import cn.hutool.crypto.SecureUtil
import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.data.jpa.SupportService
import com.kanasinfo.ext.fromJsonToObject
import com.kanasinfo.ext.toJson
import com.kanasinfo.platform.exception.HolderException
import com.kanasinfo.platform.base.model.PlatformUser
import com.kanasinfo.platform.base.model.UserCertificate
import com.kanasinfo.platform.base.model.holder.HolderProfile
import com.kanasinfo.platform.base.repository.HolderProfileRepository
import com.kanasinfo.platform.core.mvc.MailSendContext
import com.kanasinfo.platform.exception.BusinessException
import com.kanasinfo.platform.exception.HolderProfileException
import com.kanasinfo.platform.exception.UserException
import com.kanasinfo.platform.utils.RedisKey
import com.kanasinfo.platform.utils.sessionUserPrincipal
import com.kanasinfo.web.getDomain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.Executor

/**
 * @author gefangshuai
 * @createdAt 2019-03-09 16:32
 **/
@Service
@Transactional(readOnly = true)
class HolderProfileService : SupportService<HolderProfile, String>() {
    @Autowired
    private lateinit var holderProfileRepository: HolderProfileRepository
    @Autowired
    private lateinit var platformUserService: PlatformUserService
    @Autowired
    private lateinit var emailSendExecutor: Executor
    @Autowired
    private lateinit var mailSendContext: MailSendContext
    @Value("\${spring.mvc.servlet.path}")
    private var servletPath: String? = null
    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    override val repository: SupportRepository<HolderProfile, String>
        get() = holderProfileRepository
    @Autowired
    private lateinit var holderService: HolderService

    @Transactional
    fun createHolderProfile(platformUser: PlatformUser, holderId: String, nickname: String? = null): HolderProfile {
        val holder = holderService.getOne(holderId) ?: throw HolderException.HOLDER_NOT_FOUND_EXCEPTION

        val holderProfile = HolderProfile()
        holderProfile.holder = holder
        holderProfile.platformUser = platformUser
        holderProfile.nickname = nickname
        holderProfile.loginName = platformUser.userCertificate?.loginName

        return save(holderProfile)
    }

    fun findByHolder(holderId: String): List<HolderProfile> {
        return holderProfileRepository.findByHolderId(holderId)
    }

    private fun generateActiveUri(userId: String, activeCode: String): String {
        return mapOf(
            "id" to userId,
            "code" to activeCode
        ).toJson().run {
            Base64.encode(this)
        }
    }

    @Throws(BusinessException::class)
    @Transactional
    fun addProfileByEmail(email: String, nickname: String, holderId: String): HolderProfile {
        val holderProfile =
            platformUserService.addPlatformUser(email, nickname, holderId, UserCertificate.Type.EMAIL).holderProfile ?: throw UserException.USER_CREATE_FAIL_EXCEPTION
        sendActivationCode(email, holderProfile.id)
        return holderProfile
    }

    fun sendActivationCode(email: String, profileId: String) {
        val request = sessionUserPrincipal()?.request
        val activeCode = RandomUtil.randomString(16)
        stringRedisTemplate.opsForValue().set(RedisKey.getTokenKey(profileId), activeCode)
        // 发送邮件
        emailSendExecutor.execute {
            mailSendContext.sendMail(
                to = arrayOf(email),
                subject = "用户激活",
                template = "userActive.ftl",
                model = mapOf(
                    "url" to "${request?.getDomain()}/holder/users/activation/${generateActiveUri(profileId, activeCode)}"
                )
            )
        }
    }

    @Transactional
    fun activeUser(info: String): HolderProfile? {
        return try {
            val codeInfo = Base64.decodeStr(info).fromJsonToObject(Map::class.java)
            val id = codeInfo["id"].toString()
            val code = codeInfo["code"].toString()
            var profile = getOne(id)

            if (profile?.active == true) {
                return profile
            }
            return if (code == stringRedisTemplate.opsForValue().get(RedisKey.getTokenKey(id))) {
                profile = getOne(id)?.apply {
                    this.active = true
                } ?: throw HolderProfileException.HOLDER_PROFILE_NOT_FOUND_EXCEPTION
                profile = save(profile)
                stringRedisTemplate.delete(RedisKey.getTokenKey(id))
                profile
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun findByHolderAndPlatform(holderId: String, platformUser: PlatformUser): HolderProfile? {
        return holderProfileRepository.findByHolderAndPlatform(holderId, platformUser)
    }

}