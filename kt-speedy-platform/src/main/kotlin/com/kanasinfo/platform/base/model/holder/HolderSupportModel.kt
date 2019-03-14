package com.kanasinfo.platform.base.model.holder

import com.kanasinfo.data.jpa.SupportModel
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class HolderSupportModel : SupportModel() {
    @Column(length = 19, nullable = false)
    val holderId: String? = com.kanasinfo.platform.utils.holderId()

}