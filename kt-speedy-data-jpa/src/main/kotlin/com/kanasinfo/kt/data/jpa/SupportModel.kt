package com.kanasinfo.kt.data.jpa

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
    @Id
    @Column(name = "id", length = 50)
    var id: String = UUID.randomUUID().toString().replace("-", "")

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    var createdDate: Date? = null

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_date")
    var lastModifiedDate: Date? = null

    @Column(name = "created_by")
    @CreatedBy
    val createdBy: String? = null

    @Column(name = "last_modified_by")
    @LastModifiedBy
    val lastModifiedBy: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as SupportModel?
        return id == that!!.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return "${this.javaClass.name}#${this.id}"
    }
}