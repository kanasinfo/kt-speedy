package com.kanasinfo.kt.data.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.Transactional

import java.io.Serializable
import java.util.*

/**
 * 核心 Service 类
 * Created by gefangshuai on 2015/11/16.
 */
@Transactional(readOnly = true)
abstract class SupportService<T, ID : Serializable> {
    protected abstract val repository: SupportRepository<T, ID>

    fun findById(id: ID): Optional<T> {
        return repository.findById(id)
    }

    /**
     * 获取一个对象
     */
    fun getOne(id: ID): T {
        return repository.getOne(id)
    }

    @Transactional
    open fun save(t: T): T {
        return repository.save(t)
    }

    fun findAll(): List<T> {
        return repository.findAll()
    }

    fun findAll(sort: Sort): List<T> {
        return repository.findAll(sort)
    }

    fun findAll(pageable: Pageable): Page<T> {
        return repository.findAll(pageable)
    }

    @Transactional
    open fun deleteById(id: ID) {
        repository.deleteById(id)
    }

    @Transactional
    open fun delete(entity: T) {
        repository.delete(entity)
    }
}
