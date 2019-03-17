package com.kanasinfo.platform.base.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kanasinfo.data.jpa.SupportModel
import com.kanasinfo.ext.KUID
import com.kanasinfo.platform.base.model.holder.HolderProfile
import javax.persistence.*

/**
 * 平台用户
 * @author gefangshuai
 * @createdAt 2019-03-06 13:48
 **/
@Entity
@Table(name = "base_platform_user")
data class PlatformUser(
    @Id
    @Column(length = 19)
    val id: String = KUID.get()
) : SupportModel() {
    @JsonIgnore
    lateinit var password: String

    /**
     * 当前登录票据
     */
    @Transient
    var userCertificate: UserCertificate? = null

    /**
     * 当前租户
     */
    @Transient
    @JsonIgnore
    var holderProfile: HolderProfile? = null
}