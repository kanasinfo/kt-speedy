package com.kanasinfo.platform.base.model.holder

import com.kanasinfo.data.jpa.SupportModel
import com.kanasinfo.platform.utils.holderId
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class HolderSupportModel : SupportModel {
    @Column(length = 19, nullable = false)
    lateinit var holderId: String

    constructor() : super(){
        com.kanasinfo.platform.utils.holderIdOrNull()?.let {
            this.holderId = it
        }
    }
}