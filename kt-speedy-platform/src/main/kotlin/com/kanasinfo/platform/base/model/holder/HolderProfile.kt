package com.kanasinfo.platform.base.model.holder

import com.kanasinfo.data.jpa.SupportModel
import com.kanasinfo.ext.KUID
import com.kanasinfo.platform.base.model.PlatformUser
import com.kanasinfo.platform.rbac.model.HolderRole
import javax.persistence.*
import java.util.HashSet


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

    @JoinTable(
        name = "holder_profile_role",
        joinColumns = [JoinColumn(name = "profile_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    val roles: Set<HolderRole> = HashSet()
    /**
     * 是否激活
     */
    var active: Boolean = false
    var loginName: String? = null
}