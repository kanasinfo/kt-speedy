package com.kanasinfo.platform.rbac.repository

import com.kanasinfo.data.jpa.SupportRepository
import com.kanasinfo.platform.rbac.model.HolderRole
import org.springframework.stereotype.Repository

@Repository
interface HolderRoleRepository : SupportRepository<HolderRole, String> {
}