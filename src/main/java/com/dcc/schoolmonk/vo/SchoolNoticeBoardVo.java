package com.dcc.schoolmonk.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SCHOOL_NOTICE_BOARD")
@EntityListeners(AuditingEntityListener.class)
public class SchoolNoticeBoardVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name = "School_Id", nullable = false )
    @JsonBackReference
	private SchoolMstVo schoolMstVo;
	
	@Column(name = "Notice")
	private String notice;
	
	@Column(name = "Start_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate = new Date();
	
	@Column(name = "Expiry_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;
	
	@Column(name = "Publish_Status", length = 1)
	private String publishStatus = "Y";
	
	public SchoolMstVo getSchoolMstVo() {
		return schoolMstVo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSchoolMstVo(SchoolMstVo schoolMstVo) {
		this.schoolMstVo = schoolMstVo;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	@Override
	public String toString() {
		return "SchoolNoticeBoardVo [id=" + id + ", schoolMstVo=" + schoolMstVo + ", notice=" + notice + ", startDate="
				+ startDate + ", expiryDate=" + expiryDate + ", publishStatus=" + publishStatus + "]";
	}
}
