package com.kanasinfo.kt.data.mongo

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable

import javax.persistence.EntityListeners
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import java.util.Date

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class SupportMongoModel : Serializable {
    @Id
    @JsonSerialize(using = ToStringSerializer::class)
    var id: ObjectId = ObjectId.get()

    @CreatedDate
    var createdDate: Date? = null

    @LastModifiedDate
    var lastModifiedDate: Date? = null

    @CreatedBy
    var createdBy: ObjectId? = null

    @LastModifiedBy
    var lastModifiedBy: ObjectId? = null
}
