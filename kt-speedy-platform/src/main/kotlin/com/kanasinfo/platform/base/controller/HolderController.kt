package com.kanasinfo.platform.base.controller

import com.kanasinfo.platform.base.model.holder.Holder
import com.kanasinfo.platform.base.service.HolderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author gefangshuai
 * @createdAt 2019-03-13 16:55
 **/
@RestController
@RequestMapping("/holders")
class HolderController {
    @Autowired
    private lateinit var holderService: HolderService

    @GetMapping("/active")
    fun getActiveHolders(): List<Holder> {
        return holderService.findByActiveTrue()
    }
}