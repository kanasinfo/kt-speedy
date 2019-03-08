package com.kanasinfo.platform.core.security

import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.util.Assert
import java.io.Serializable

class CustomPermissionEvaluator : PermissionEvaluator {
    override fun hasPermission(authentication: Authentication, targetDomainObject: Any?, permission: Any?): Boolean {
        if(targetDomainObject ==null)
            Assert.notNull(targetDomainObject, "targetDomainObject must be set!")
        val user = authentication.principal as CustomerUserPrincipal
        return user.access.contains(
                if (permission == null) {
                    targetDomainObject
                } else {
                    targetDomainObject.toString() + ":" + permission
                }
        )
    }

    override fun hasPermission(authentication: Authentication?, targetId: Serializable?, targetType: String?, permission: Any?): Boolean {
        return false
    }
}