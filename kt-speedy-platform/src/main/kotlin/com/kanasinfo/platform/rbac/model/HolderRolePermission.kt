package com.kanasinfo.platform.rbac.model

import com.kanasinfo.ext.KUID
import com.kanasinfo.platform.base.model.holder.HolderSupportModel
import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity
@Table(name = "holder_role_permission")
data class HolderRolePermission(
    @Id
    @Column(length = 19)
    val id: String = KUID.get()
) : GrantedAuthority, HolderSupportModel() {
    lateinit var code: String

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinColumn(
        name = "role_id",
        referencedColumnName = "id"
    )
    lateinit var role: HolderRole
    override fun getAuthority(): String {
        return code
    }
}