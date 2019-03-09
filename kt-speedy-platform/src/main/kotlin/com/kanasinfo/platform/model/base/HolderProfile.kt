package com.kanasinfo.platform.model.base

import com.kanasinfo.data.jpa.SupportModel
import com.kanasinfo.ext.KUID
import com.kanasinfo.platform.model.PlatformUser
import javax.persistence.*

/**
 * 租户用户对应表
 * @author gefangshuai
 * @createdAt 2019-03-09 11:45
 **/
@Entity
@Table(name = "base_holder_profile")
data class HolderProfile(
    @Id
    @Column(length = 19)
    val id: String = KUID.get()
) : SupportModel() {
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    lateinit var platformUser: PlatformUser

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holder_id", referencedColumnName = "id", nullable = true)
    lateinit var holder: Holder

    @Column(length = 100)
    var nickname: String? = null
}