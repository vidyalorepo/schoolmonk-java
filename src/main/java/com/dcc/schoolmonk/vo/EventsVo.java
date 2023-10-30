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

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Component
@Table(name = "EVENTS_DETAILS")
@EntityListeners(AuditingEntityListener.class)
public class EventsVo {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "event_id", length = 50)
	private String eventId;

	@Column(name = "event_content", length = 600)
	private String eventContent;
	
	@Column(name = "event_time")
	private String eventTime;
	
	@Column(name = "event_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date eventDate;
	
	@Column(name = "event_type", length = 20)
	private String eventType;			//(Public / Private)
	
	@Column(name = "event_flag", length = 5)
	private String eventFlag;				//(Y / N)
	
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

	public Long getId() {
		return id;
	}

	public String getEventId() {
		return eventId;
	}

	public String getEventContent() {
		return eventContent;
	}

	public String getEventTime() {
		return eventTime;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public String getEventType() {
		return eventType;
	}

	public String getEventFlag() {
		return eventFlag;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public void setEventContent(String eventContent) {
		this.eventContent = eventContent;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public void setEventFlag(String eventFlag) {
		this.eventFlag = eventFlag;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "EventsVo [id=" + id + ", eventId=" + eventId + ", eventContent=" + eventContent + ", eventTime="
				+ eventTime + ", eventDate=" + eventDate + ", eventType=" + eventType + ", eventFlag=" + eventFlag
				+ "]";
	}
	

	
}
