package com.kanasinfo.platform.service

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.data.jpa.SupportService
import com.kanasinfo.platform.model.base.Holder
import com.kanasinfo.platform.repository.HolderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author gefangshuai
 * @createdAt 2019-03-09 12:30
 **/
@Service
@Transactional(readOnly = true)
class HolderService: SupportService<Holder, String>() {
    @Autowired
    private lateinit var holderRepository: HolderRepository

    override val repository: SupportRepository<Holder, String>
        get() = holderRepository


}