package com.kanasinfo.platform.base.model.holder

import com.kanasinfo.data.jpa.SupportModel
import com.kanasinfo.ext.KUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * @author gefangshuai
 * @createdAt 2019-03-09 11:50
 **/
@Entity
@Table(name = "base_holder")
data class Holder(
    @Id
    @Column(length = 19)
    val id: String = KUID.get()
) : SupportModel() {
    @Column(length = 200)
    lateinit var name: String
    var active: Boolean = true
}