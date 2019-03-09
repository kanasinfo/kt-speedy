package com.kanasinfo.platform.model.base

import com.kanasinfo.data.jpa.SupportModel
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class HolderSupportModel : SupportModel() {
    @Column(length = 19)
    lateinit var holderId: String
}