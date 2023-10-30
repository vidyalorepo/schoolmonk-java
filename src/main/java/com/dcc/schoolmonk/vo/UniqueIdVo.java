package com.dcc.schoolmonk.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "UNIQUE_ID")
@EntityListeners(AuditingEntityListener.class)
public class UniqueIdVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "unq_id", unique = true, nullable = false, updatable = false)
	private long Id;

	@Column(name = "uId")
	private String uId;

	@Column(name = "sId")
	private long sId;

	@Transient
	private String uniqueId;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public long getsId() {
		return sId;
	}

	public void setsId(long sId) {
		this.sId = sId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Override
	public String toString() {
		return "UniqueIdVo [Id=" + Id + ", uId=" + uId + ", sId=" + sId + ", uniqueId=" + uniqueId + "]";
	}

	

}
