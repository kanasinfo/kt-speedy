package com.kanasinfo.platform.rbac.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.kanasinfo.ext.KUID
import com.kanasinfo.platform.model.base.HolderSupportModel
import javax.persistence.*

@Entity
@Table(name = "sys_role")
@JsonIgnoreProperties("users", "permissions")
data class SysRole(
    @Id
    @Column(length = 19)
    val id: String = KUID.get()
) : HolderSupportModel() {
    var name: String? = null

    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var permissions: List<SysPermission>? = null

    fun getAllPermissions(): List<String>? {
        return permissions?.map { it.code }
    }
}