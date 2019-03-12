package com.kanasinfo.platform.rbac.context

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.kanasinfo.ext.isPresent
import com.kanasinfo.platform.rbac.model.HolderRolePermission
import com.kanasinfo.platform.rbac.model.HolderRole
import com.kanasinfo.web.SpringContextKit
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class FunctionContext {
    @Autowired
    private lateinit var springContextKit: SpringContextKit
    private lateinit var functions: ArrayList<Function>
    private val logger = LoggerFactory.getLogger(FunctionContext::class.java)

    @PostConstruct
    fun initFunctions() {
        val resource = springContextKit.getApplicationContext().getResource("classpath:access.json")
        if (resource.exists()) {
            functions = jacksonObjectMapper().readValue(resource.inputStream)
        } else {
            logger.debug("access.json not exists")
        }
    }

    fun getFunctions() = functions

    fun mapAllFunctionToPermission(
        permissions: MutableList<HolderRolePermission>,
        functions: List<Function>,
        role: HolderRole
    ) {
        functions.forEach {
            val permission = HolderRolePermission()
            permission.role = role
            permission.code = it.name
            permissions.add(permission)
            if (it.children.isPresent()) {
                mapAllFunctionToPermission(permissions, it.children!!, role)
            }
        }
    }
}