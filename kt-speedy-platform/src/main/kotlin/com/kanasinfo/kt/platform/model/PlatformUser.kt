package com.kanasinfo.kt.platform.model

import com.kanasinfo.kt.data.jpa.SupportModel
import com.kanasinfo.kt.ext.KUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient

/**
 * 平台用户
 * @author gefangshuai
 * @createdAt 2019-03-06 13:48
 **/
@Entity
@Table(name = "kt_platform_user")
data class PlatformUser(
    @Id
    val id: String = KUID.get()
) : SupportModel() {
    lateinit var password: String

    /**
     * 当前登录票据
     */
    @Transient
    var userCertificate: UserCertificate? = null
}