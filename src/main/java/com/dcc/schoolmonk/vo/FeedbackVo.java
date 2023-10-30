package com.dcc.schoolmonk.vo;


import java.util.List;

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
@Table(name = "FEEDBACK_MST")
@EntityListeners(AuditingEntityListener.class)
public class FeedbackVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "User_Name")
	private String userName;
	
	@Column(name = "User_Email")
	private String userEmail;
	
	@Column(name = "User_Phone")
	private String userPhone;
	
	@Column(name = "User_Feedback", length= 500)
	private String userFeedback;
	
	@Column(name = "Feedback_Status")
	private String feedbackStatus;

    @Column(name="is_agree")
    private Boolean isAgree;
	
	@Transient
	private List<Long> idLists;
	
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

	public String getUserName() {
		return userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserFeedback() {
		return userFeedback;
	}

	public void setUserFeedback(String userFeedback) {
		this.userFeedback = userFeedback;
	}

	public String getFeedbackStatus() {
		return feedbackStatus;
	}

	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}

	public List<Long> getIdLists() {
		return idLists;
	}

	public void setIdLists(List<Long> idLists) {
		this.idLists = idLists;
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
		return "FeedbackVo [id=" + id + ", userName=" + userName + ", userEmail=" + userEmail + ", userPhone="
				+ userPhone + ", userFeedback=" + userFeedback + "]";
	}
	public Boolean getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Boolean isAgree) {
		this.isAgree = isAgree;
	}
}
