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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ADMISSION_STATUS_MST")
@EntityListeners(AuditingEntityListener.class)
public class AdmissionStatusMstVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Column(name = "status_name")
	private String statusName;

	@Column(name = "order_no")
	private String orderNumber;

	public Long getId() {
		return id;
	}

	public String getStatusName() {
		return statusName;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

}
