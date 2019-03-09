package com.kanasinfo.platform.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kanasinfo.data.jpa.SupportModel
import com.kanasinfo.ext.KUID
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
}