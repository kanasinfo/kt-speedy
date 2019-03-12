package com.kanasinfo.platform.rbac.service

import com.kanasinfo.platform.rbac.repository.HolderRolePermissionRepository
import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.data.jpa.SupportService
import com.kanasinfo.platform.rbac.model.HolderRolePermission
import com.kanasinfo.platform.rbac.model.HolderRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class HolderRolePermissionService: SupportService<HolderRolePermission, String>() {
    @Autowired
    private lateinit var holderRolePermissionRepository: HolderRolePermissionRepository
    override val repository: SupportRepository<HolderRolePermission, String>
        get() = holderRolePermissionRepository

    @Transactional
    fun removeByRole(role: HolderRole): List<HolderRolePermission>{
        return holderRolePermissionRepository.removeByRole(role)
    }

    fun findByRole(role: HolderRole): List<HolderRolePermission> {
        return holderRolePermissionRepository.findByRole(role)
    }

}