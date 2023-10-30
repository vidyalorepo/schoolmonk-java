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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Component
@Table(name = "NOTICE_BOARD")
@EntityListeners(AuditingEntityListener.class)
public class EnhanceNoticeBoardVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "notice_id", length = 50)
	private String noticeId;

	@Column(name = "notice_content", length = 600)
	private String noticeContent;
	
	@Column(name = "starts_on", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date startsOn;
	
	@Column(name = "expires_on")
    @Temporal(TemporalType.TIMESTAMP)
	private Date expiresOn;
	
	@Column(name = "notice_type", length = 20)
	private String noticeType;			//(Public / Private)
	
	@Column(name = "notice_sub_cat", length = 20)
	private String noticeSubCat;
	
	@Column(name = "publish_flag", length = 5)
	private String publishFlag;				//(Y / N)
	
	@Column(name = "notification_flag", length = 5)
	private String notificationFlag;				//(Y / N)
	
	@Column(name = "notification_type", length = 50)
	private String notificationType;		//(email/ sms/ whatsapp)
	
	@Column(name = "schedule_reminder", length = 5)
	private String scheduleReminder;				//(Y / N)
	
	@Column(name = "schedule_interval", length = 50)
	private String scheduleInterval;
	
	@Column(name = "created_on", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedOn;

	@Column(name = "updated_by")
	private Long updatedBy;
	
	@Column(name = "school_id")
	private Long schoolId;
	
	@Transient
	private Long page;
	@Transient
	private Long size;
	@Transient
	private String orderByColName;
	@Transient
	private String orderBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Date getStartsOn() {
		return startsOn;
	}

	public void setStartsOn(Date startsOn) {
		this.startsOn = startsOn;
	}

	public Date getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(Date expiresOn) {
		this.expiresOn = expiresOn;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeSubCat() {
		return noticeSubCat;
	}

	public void setNoticeSubCat(String noticeSubCat) {
		this.noticeSubCat = noticeSubCat;
	}

	public String getPublishFlag() {
		return publishFlag;
	}

	public void setPublishFlag(String publishFlag) {
		this.publishFlag = publishFlag;
	}

	public String getNotificationFlag() {
		return notificationFlag;
	}

	public void setNotificationFlag(String notificationFlag) {
		this.notificationFlag = notificationFlag;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getScheduleReminder() {
		return scheduleReminder;
	}

	public void setScheduleReminder(String scheduleReminder) {
		this.scheduleReminder = scheduleReminder;
	}

	public String getScheduleInterval() {
		return scheduleInterval;
	}

	public void setScheduleInterval(String scheduleInterval) {
		this.scheduleInterval = scheduleInterval;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getOrderByColName() {
		return orderByColName;
	}

	public void setOrderByColName(String orderByColName) {
		this.orderByColName = orderByColName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public String toString() {
		return "NoticeBoardVo [id=" + id + ", noticeContent=" + noticeContent + ", startsOn=" + startsOn
				+ ", expiresOn=" + expiresOn + ", noticeType=" + noticeType + ", noticeSubCat=" + noticeSubCat
				+ ", publishFlag=" + publishFlag + ", notificationFlag=" + notificationFlag + ", notificationType="
				+ notificationType + ", scheduleReminder=" + scheduleReminder + ", scheduleInterval=" + scheduleInterval
				+ ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", updatedOn=" + updatedOn + ", updatedBy="
				+ updatedBy + "]";
	}
}
