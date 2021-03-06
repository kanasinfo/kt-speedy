package com.kanasinfo.platform.rbac.service

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.data.jpa.SupportService
import com.kanasinfo.platform.core.runnner.FunctionRunner
import com.kanasinfo.platform.rbac.feo.request.HolderRoleRequest
import com.kanasinfo.platform.rbac.model.HolderRolePermission
import com.kanasinfo.platform.rbac.model.HolderRole
import com.kanasinfo.platform.rbac.repository.HolderRoleRepository
import com.kanasinfo.platform.utils.holderId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class HolderRoleService : SupportService<HolderRole, String>() {
    @Autowired
    private lateinit var holderRoleRepository: HolderRoleRepository
    @Autowired
    private lateinit var holderRolePermissionService: HolderRolePermissionService
    @Autowired
    private lateinit var functionRunner: FunctionRunner

    private  val adminDefaultName = "超级管理员"

    override val repository: SupportRepository<HolderRole, String>
        get() = holderRoleRepository

    fun findByHolderRoles(): List<HolderRole> {
        return holderRoleRepository.findByHolderId("psfvRdHJajCuXyDvU4n", Sort.by(Sort.Direction.ASC, "createdDate"))
    }

    @Transactional
    fun editRole(roleRequest: HolderRoleRequest): HolderRole {
        val role = if (roleRequest.id != null) {
            getOne(roleRequest.id) ?: HolderRole()
        } else {
            HolderRole()
        }
        role.name = roleRequest.name
        return save(role)
    }

    @Transactional
    fun authorize(roleId: String, functions: Array<String>): HolderRole? {
        val roleOptional = findById(roleId)
        if (roleOptional.isPresent) {
            val role = roleOptional.get()
            holderRolePermissionService.removeByRole(role)
            val permissions = mutableListOf<HolderRolePermission>()
            functions.forEach {
                val permission = HolderRolePermission()
                permission.code = it
                permission.role = role
                permissions.add(permission)
            }
            role.rolePermissions = permissions
            return save(role)
        }
        return null
    }

    @Transactional
    fun initAdmin(): HolderRole {
        var role = getOne("admin")
        if (role == null) {
            role = HolderRole()
            role.name = adminDefaultName
            val permissions = mutableListOf<HolderRolePermission>()
            functionRunner.mapAllFunctionToPermission(permissions, functionRunner.getFunctions(), role)
            role.rolePermissions = permissions
            save(role)
        }
        return role
    }
}