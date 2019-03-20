package com.kanasinfo.platform.core.runnner

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.kanasinfo.ext.isPresent
import com.kanasinfo.platform.rbac.context.Function
import com.kanasinfo.platform.rbac.model.HolderRolePermission
import com.kanasinfo.platform.rbac.model.HolderRole
import com.kanasinfo.web.SpringContextKit
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(150)
class FunctionRunner : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val resource = springContextKit.getApplicationContext().getResource("classpath:access.json")
        if (resource.exists()) {
            functions = jacksonObjectMapper().readValue(resource.inputStream)
            enable = true
            logger.info("function init finish")
        } else {
            logger.warn("access.json not exists")
        }
    }

    @Autowired
    private lateinit var springContextKit: SpringContextKit
    private lateinit var functions: ArrayList<Function>
    private val logger = LoggerFactory.getLogger(FunctionRunner::class.java)
    private var enable = false

    fun getRbacEnable() = enable

    fun getFunctions() = functions

    fun mapAllFunctionToPermission(permissions: MutableList<HolderRolePermission>, functions: List<Function>, role: HolderRole) {
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