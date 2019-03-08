package com.kanasinfo.platform.core.security

import com.kanasinfo.kt.ext.NoArg

@NoArg
data class AccountCredentials (val username: String, val password: String)