package com.kanasinfo.platform.repository

import com.kanasinfo.kt.data.jpa.SupportRepository
import com.kanasinfo.kt.platform.model.PlatformUser
import org.springframework.stereotype.Repository

/**
 * @author gefangshuai
 * @createdAt 2019-03-06 14:04
 **/
@Repository
interface PlatformUserRepository : SupportRepository<PlatformUser, String>