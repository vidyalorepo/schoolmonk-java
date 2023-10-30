package com.dcc.schoolmonk.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="SCHOOL_REVIEW_MST")
@EntityListeners(AuditingEntityListener.class)
public class SchoolReviewVo implements Serializable{

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="reviewer_name",length=255)
	private String reviewerName;	
	
	@Column(name="is_Approved")
	private Boolean isApproved;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "school_id", nullable = false)
	private SchoolMstVo schoolId;
	
	@CreationTimestamp
	@Column(name = "created_ts", nullable = false, updatable = false)
	private Date createdTs;

	@CreationTimestamp
	@Column(name = "updated_ts", nullable = false, updatable = false)
	private Date updatedTs;
	
	@Column(name="review_details", length=1500)
	private String reviewDetails;

    @Column(name="rating")
	private Integer rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public SchoolMstVo getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(SchoolMstVo schoolId) {
        this.schoolId = schoolId;
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

    public String getReviewDetails() {
        return reviewDetails;
    }

    public void setReviewDetails(String reviewDetails) {
        this.reviewDetails = reviewDetails;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
    

    
}
