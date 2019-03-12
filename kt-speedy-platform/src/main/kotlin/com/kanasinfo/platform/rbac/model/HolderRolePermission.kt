package com.kanasinfo.platform.rbac.model

import com.kanasinfo.platform.model.base.HolderSupportModel
import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity
@Table(name = "holder_permission")
class HolderPermission  : GrantedAuthority, HolderSupportModel {
    lateinit var code: String

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
    )
    lateinit var role: HolderRole



    constructor(code: String, role: HolderRole) : super() {
        this.code = code
        this.role = role
    }

    constructor() : super()

    override fun getAuthority(): String {
        return code
    }
}