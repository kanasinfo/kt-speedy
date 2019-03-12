package com.kanasinfo.platform.rbac.repository

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.platform.rbac.model.HolderRolePermission
import com.kanasinfo.platform.rbac.model.HolderRole
import org.springframework.stereotype.Repository

@Repository
interface HolderRolePermissionRepository : SupportRepository<HolderRolePermission, String> {
    fun removeByRole(role: HolderRole): List<HolderRolePermission>
    fun findByRole(role: HolderRole): List<HolderRolePermission>
}