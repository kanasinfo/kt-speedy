package com.kanasinfo.platform.service

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.data.jpa.SupportService
import com.kanasinfo.platform.exception.HolderException
import com.kanasinfo.platform.model.PlatformUser
import com.kanasinfo.platform.model.base.HolderProfile
import com.kanasinfo.platform.repository.HolderProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author gefangshuai
 * @createdAt 2019-03-09 16:32
 **/
@Service
@Transactional(readOnly = true)
class HolderProfileService : SupportService<HolderProfile, String>() {
    @Autowired
    private lateinit var holderProfileRepository: HolderProfileRepository
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

        return save(holderProfile)
    }

}