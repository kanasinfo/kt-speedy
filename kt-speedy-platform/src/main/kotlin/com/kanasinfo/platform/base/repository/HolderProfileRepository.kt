package com.kanasinfo.platform.base.repository

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.platform.base.model.PlatformUser
import com.kanasinfo.platform.base.model.holder.HolderProfile
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * @author gefangshuai
 * @createdAt 2019-03-06 14:04
 **/
@Repository
interface HolderProfileRepository : SupportRepository<HolderProfile, String> {
    fun findByHolderId(holderId: String): List<HolderProfile>
    @Query("from HolderProfile where holder.id = ?1 and platformUser = ?2")
    fun findByHolderAndPlatform(holderId: String, platformUser: PlatformUser): HolderProfile?
}