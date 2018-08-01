package com.kanasinfo.kt.data.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class SupportMongoModel {
	private ObjectId id;
	@CreatedDate
	private Date createdDate;

	@LastModifiedDate
	private Date lastModifiedDate;

	@CreatedBy
	private ObjectId createdBy;

	@LastModifiedBy
	private ObjectId lastModifiedBy;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public ObjectId getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(ObjectId createdBy) {
		this.createdBy = createdBy;
	}

	public ObjectId getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(ObjectId lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
}
