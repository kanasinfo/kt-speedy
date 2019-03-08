package com.kanasinfo.platform.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kanasinfo.kt.data.jpa.SupportModel
import com.kanasinfo.kt.ext.KUID
import javax.persistence.*

/**
 * 用户账号
 * @author gefangshuai
 * @createdAt 2019-03-06 13:51
 **/
@Entity
@Table(name = "kt_user_certificate")
data class UserCertificate(
    @Id
    val id: String = KUID.get(),
    /**
     * 登录名称
     */
    val loginName: String
) : SupportModel() {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    lateinit var platformUser: PlatformUser

    /**
     * 是否激活
     */
    @Column(name = "active")
    var active: Boolean = true

    /**
     * 账号类型
     */
    @Enumerated(value = EnumType.STRING)
    var type: Type = Type.CUSTOMIZE

    enum class Type {
        /**
         * 普通账号
         */
        CUSTOMIZE,
        /**
         * 邮箱账号
         */
        EMAIL,
        /**
         * 微信账号
         */
        WECHAT,
        /**
         * 阿里账号
         */
        ALI,
        /**
         * 手机账号
         */
        MOBILE
    }
}