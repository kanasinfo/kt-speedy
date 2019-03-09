package com.kanasinfo.platform.repository

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.platform.model.PlatformUser
import com.kanasinfo.platform.model.base.Holder
import org.springframework.stereotype.Repository

/**
 * @author gefangshuai
 * @createdAt 2019-03-06 14:04
 **/
@Repository
interface HolderRepository : SupportRepository<Holder, String>