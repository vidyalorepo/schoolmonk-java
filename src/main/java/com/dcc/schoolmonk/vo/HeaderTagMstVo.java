package com.dcc.schoolmonk.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.LastModifiedDate;


@Entity
@Table(name = "HEADER_TAG_MST")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_ts" }, allowGetters = true)
public class HeaderTagMstVo implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Column(name = "created_ts", nullable = false, updatable = false)
	private Date createdTs;

	@Column(name = "updated_ts", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedTs;

	@Column(name = "header_tag_body", length = 500)
	private String headerTagBody;

	@Column(name = "is_Publish")
	private Boolean isPublish;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

 

	public Date getCreatedTs() {
		return createdTs;
	}

 

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

 

	public Date getUpdatedTs() {
		return updatedTs;
	}

 

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

 

	public String getHeaderTagBody() {
		return headerTagBody;
	}

 

	public void setHeaderTagBody(String headerTagBody) {
		this.headerTagBody = headerTagBody;
	}

 

	public Boolean getIsPublish() {
		return isPublish;
	}

 

	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}

 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
