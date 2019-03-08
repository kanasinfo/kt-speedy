package com.kanasinfo.data.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

import java.io.Serializable

/**
 * 核心Dao接口
 * Created by gefangshuai on 2015/11/6.
 */
@NoRepositoryBean
interface SupportRepository<T, ID : Serializable> : JpaRepository<T, ID>, JpaSpecificationExecutor<T>
