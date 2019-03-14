package com.kanasinfo.platform.rbac.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.kanasinfo.ext.KUID
import com.kanasinfo.platform.base.model.holder.HolderProfile
import com.kanasinfo.platform.base.model.holder.HolderSupportModel
import com.kanasinfo.platform.utils.holderId
import javax.persistence.*
import java.util.HashSet
import javax.persistence.ManyToMany


@Entity
@Table(name = "holder_role")
@JsonIgnoreProperties("rolePermissions")
data class HolderRole(
    @Id
    @Column(length = 19)
    val id: String = KUID.get()
) : HolderSupportModel() {
    var name: String? = null

    @ManyToMany(targetEntity = HolderProfile::class, mappedBy = "roles", cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    private val profiles:Set<HolderProfile> = HashSet()

    @OneToMany(mappedBy = "role", cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH], fetch = FetchType.LAZY)
    var rolePermissions: List<HolderRolePermission>? = null

    fun getAllPermissions(): List<String>? {
        return rolePermissions?.map { it.code }
    }
}