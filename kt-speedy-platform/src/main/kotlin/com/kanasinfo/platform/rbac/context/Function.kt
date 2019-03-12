package com.kanasinfo.alps.boss.config

import com.kanasinfo.kt.ext.NoArg

@NoArg
data class Function(
        var title: String? = null,
        var name: String,
        var children: List<Function>? = null
)