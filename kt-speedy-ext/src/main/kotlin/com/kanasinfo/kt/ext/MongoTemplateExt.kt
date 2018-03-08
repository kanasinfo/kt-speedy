package com.kanasinfo.kt.ext

import com.mongodb.client.result.UpdateResult
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.*

/**
 * 更新并返回数据
 */
fun <T> MongoTemplate.modifyAndReturn(queryCriteria: Criteria, update: Update, ignoreFields: List<String>, entityClass: Class<T>): T {
    val query = Query.query(queryCriteria)
    val field = query.fields()

    ignoreFields.forEach {
        field.exclude(it)
    }

    return this.findAndModify(
            query,
            update,
            FindAndModifyOptions().returnNew(true),
            entityClass
    )
}

/**
 * 根据主键进行数据更新
 */
fun <T> MongoTemplate.modifyAndReturnById(id: ObjectId, update: Update, ignoreFields: List<String>, entityClass: Class<T>): T {
    return this.modifyAndReturn(
            Criteria("id").`is`(id),
            update,
            ignoreFields,
            entityClass
    )
}

/**
 * 根据主键更新数据
 */
fun <T> MongoTemplate.updateById(id: ObjectId, update: Update, entityClass: Class<T>) {
    this.updateFirst(
            Query.query(Criteria.where("id").`is`(id)),
            update,
            entityClass
    )
}

/**
 * 根据主键更新数据
 */
fun MongoTemplate.updateById(id: ObjectId, update: Update, collectionName: String) {
    this.updateFirst(
            Query.query(Criteria.where("id").`is`(id)),
            update,
            collectionName
    )
}

/**
 * 根据主键删除
 */
fun <T> MongoTemplate.removeById(id: ObjectId, entityClass: Class<T>) {
    this.remove(
            Query.query(Criteria.where("id").`is`(id)),
            entityClass
    )
}

/**
 * 通过id删除
 */
fun <T> MongoTemplate.deleteById(id: ObjectId, entityClass: Class<T>) {
    this.remove(
            Query.query(Criteria.where("id").`is`(id)),
            entityClass
    )
}

/**
 * 根据主键更新数据
 */
fun <T> MongoTemplate.updateMultiById(id: ObjectId, update: Update, entityClass: Class<T>): UpdateResult? {
    return this.updateMulti(
            Query.query(Criteria.where("id").`is`(id)),
            update,
            entityClass
    )
}

/**
 * 根据id进行查询
 */
fun <T> MongoTemplate.findWithIncludeFieldsById(id: ObjectId, includeFields: List<String>, entityClass: Class<T>): T? {
    val query = Query.query(Criteria.where("id").`is`(id))
    val field = query.fields()

    includeFields.forEach {
        field.include(it)
    }
    return this.findOne(query, entityClass)
}

/**
 * 根据id进行查询
 */
fun <T> MongoTemplate.findWithExcludeFieldsById(id: ObjectId, excludeFields: List<String>, entityClass: Class<T>): T? {
    val query = Query.query(Criteria.where("id").`is`(id))
    val field = query.fields()

    excludeFields.forEach {
        field.exclude(it)
    }
    return this.findOne(query, entityClass)
}

fun <T> MongoTemplate.findIncludeFields(criteria: Criteria, includeFields: List<String>, entityClass: Class<T>): List<T> {
    val query = Query.query(criteria)
    val field = query.fields()

    includeFields.forEach {
        field.include(it)
    }

    return this.find(query, entityClass)
}


fun <T> MongoTemplate.findOneIncludeFields(criteria: Criteria, includeFields: List<String>, entityClass: Class<T>): T? {
    val query = Query.query(criteria)
    val field = query.fields()

    includeFields.forEach {
        field.include(it)
    }

    return this.findOne(query, entityClass)
}
