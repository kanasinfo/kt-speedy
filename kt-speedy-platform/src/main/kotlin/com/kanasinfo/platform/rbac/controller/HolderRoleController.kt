package com.kanasinfo.platform.rbac.controller

import com.kanasinfo.platform.rbac.feo.request.HolderRoleRequest
import com.kanasinfo.platform.rbac.model.HolderRole
import com.kanasinfo.platform.rbac.service.HolderRoleService
import com.kanasinfo.web.EmptyJsonResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/holder/roles")
class HolderRoleController {
    @Autowired
    private lateinit var holderRoleService: HolderRoleService

    @GetMapping
    fun getRoles(): List<HolderRole> {
        return holderRoleService.findAll(Sort.by(Sort.Direction.ASC, "createdDate"))
    }

    @PostMapping
    fun editRole(@RequestBody roleRequest: HolderRoleRequest): HolderRole {
        return holderRoleService.editRole(roleRequest)
    }

    @PostMapping("/{roleId}/authorize")
    fun authorize(@PathVariable roleId: String, functions: Array<String>): HolderRole? {
        return holderRoleService.authorize(roleId, functions)
    }

    @DeleteMapping("/{roleId}")
    fun delete(@PathVariable roleId: String): EmptyJsonResponse {
        holderRoleService.deleteById(roleId)
        return EmptyJsonResponse()
    }
}