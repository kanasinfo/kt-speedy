package com.kanasinfo.platform.base.repository

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.platform.base.model.PlatformUser
import org.springframework.stereotype.Repository

/**
 * @author gefangshuai
 * @createdAt 2019-03-06 14:04
 **/
@Repository
interface PlatformUserRepository : SupportRepository<PlatformUser, String>