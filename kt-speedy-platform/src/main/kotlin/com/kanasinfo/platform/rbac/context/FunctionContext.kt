package com.ghighcon.commute.core.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ghighcon.commute.master.model.SysPermission
import com.ghighcon.commute.master.model.SysRole
import com.kanasinfo.alps.boss.config.Function
import com.kanasinfo.kt.ext.isPresent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class FunctionContext {
    @Autowired
    private lateinit var springContextKit: SpringContextKit
    private lateinit var functions: ArrayList<Function>

    @PostConstruct
    fun initFunctions() {
        val resource = springContextKit.getApplicationContext().getResource("classpath:access.json")
        functions = jacksonObjectMapper().readValue(resource.inputStream)
    }

    fun getFunctions() = functions

    fun mapAllFunctionToPermission(permissions: MutableList<SysPermission>, functions: List<Function>, role: SysRole) {
        functions.forEach {
            val permission = SysPermission(it.name, role)
            permissions.add(permission)
            if (it.children.isPresent()) {
                mapAllFunctionToPermission(permissions, it.children!!, role)
            }
        }
    }
}