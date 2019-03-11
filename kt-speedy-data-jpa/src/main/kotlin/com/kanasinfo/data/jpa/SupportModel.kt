package com.kanasinfo.data.jpa

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.*
import javax.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class SupportModel : Serializable {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    var createdDate: Date? = null

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    var lastModifiedDate: Date? = null

    @Column(name = "created_by", length = 127)
    @CreatedBy
    var createdBy: String? = null

    @Column(name = "last_modified_by", length = 127)
    @LastModifiedBy
    var lastModifiedBy: String? = null

}