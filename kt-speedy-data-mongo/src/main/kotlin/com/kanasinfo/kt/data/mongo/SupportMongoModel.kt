package com.kanasinfo.kt.data.mongo

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.bson.types.ObjectId
import org.springframework.data.annotation.*

import java.io.Serializable

/**
 * Created by gefangshuai on 2015/11/13.
 */
open class SupportMongoModel : Serializable {
    @Id
    @JsonSerialize(using = ObjectIdSerializer::class)
    lateinit var id: ObjectId
}