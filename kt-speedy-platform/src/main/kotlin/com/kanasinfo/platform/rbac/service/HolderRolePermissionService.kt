package com.kanasinfo.platform.rbac.service

import com.kanasinfo.platform.rbac.repository.HolderPermissionRepository
import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.data.jpa.SupportService
import com.kanasinfo.platform.rbac.model.HolderRolePermission
import com.kanasinfo.platform.rbac.model.HolderRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class HolderPermissionService: SupportService<HolderRolePermission, String>() {
    @Autowired
    private lateinit var holderPermissionRepository: HolderPermissionRepository
    override val repository: SupportRepository<HolderRolePermission, String>
        get() = holderPermissionRepository

    @Transactional
    fun removeByRole(role: HolderRole): List<HolderRolePermission>{
        return holderPermissionRepository.removeByRole(role)
    }

    fun findByRole(role: HolderRole): List<HolderRolePermission> {
        return holderPermissionRepository.findByRole(role)
    }

}