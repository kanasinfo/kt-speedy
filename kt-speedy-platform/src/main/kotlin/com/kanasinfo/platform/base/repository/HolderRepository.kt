package com.kanasinfo.platform.base.repository

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.platform.base.model.holder.Holder
import org.springframework.stereotype.Repository

/**
 * @author gefangshuai
 * @createdAt 2019-03-06 14:04
 **/
@Repository
interface HolderRepository : SupportRepository<Holder, String> {
    fun findByActiveTrue(): List<Holder>
}