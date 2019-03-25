package com.kanasinfo.platform.rbac.controller

import com.kanasinfo.platform.base.model.holder.HolderProfile
import com.kanasinfo.platform.base.service.HolderProfileService
import com.kanasinfo.platform.base.service.UserCertificateService
import com.kanasinfo.platform.exception.HolderProfileException
import com.kanasinfo.platform.utils.holderId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * @author gefangshuai
 * @createdAt 2019-03-14 17:55
 **/
@RestController
@RequestMapping("/holder/users")
class HolderUserController{
    @Autowired
    private lateinit var holderProfileService: HolderProfileService
    @Autowired
    private lateinit var userCertificateService: UserCertificateService

    @GetMapping
    fun getUsers(): List<HolderProfile>{
        return holderProfileService.findByHolder(holderId())
    }

    @PostMapping
    fun addUser(email: String, nickname: String): HolderProfile{
        return holderProfileService.addProfileByEmail(email, nickname, holderId())
    }

    @GetMapping("/{profileId}/activation/code")
    fun sendActivationCode(@PathVariable profileId: String) {
        val holderProfile = holderProfileService.getOne(profileId)?:throw HolderProfileException.HOLDER_PROFILE_NOT_FOUND_EXCEPTION


        holderProfileService.sendActivationCode(userCertificateService.findByPlatformUser(holderProfile.platformUser).first().loginName, profileId)
    }

    @GetMapping("/activation/{code}")
    fun activeUser(@PathVariable code: String): HolderProfile? {
        return holderProfileService.activeUser(code)
    }
}