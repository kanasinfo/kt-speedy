package com.kanasinfo.platform.rbac.context

import com.kanasinfo.ext.NoArg


@NoArg
data class Function(
        var title: String? = null,
        var name: String,
        var children: List<Function>? = null
)