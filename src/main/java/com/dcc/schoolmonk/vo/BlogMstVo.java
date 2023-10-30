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

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Blog_MST")
@EntityListeners(AuditingEntityListener.class)
public class BlogMstVo implements Serializable {
    
    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    	
	@Column(name = "title" ,unique = true, nullable = false, length = 200)
	private String title;
    
    @Column(name = "description" ,nullable = false, length = 500)
	private String description;

    @Column(name = "blog_date",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date blogDate;
       
    @Column(name = "blog_details" ,nullable = false, length = 5000)
	private String blogDetails;

        
    @Column(name = "blog_category" ,nullable = false, length = 100)
	private String blogCategory;

	@CreationTimestamp
	@Column(name = "created_ts", nullable = false, updatable = false)
	private Date createdTs;

    @Column(name = "updated_on", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedOn;

    @Column(name = "status",nullable = false)
	private Boolean status = false;
     

    @Column(name = "created_by")
	private String createdBY;

    @Column(name = "slug" ,nullable = false, length = 500,unique = true)
	private String slug;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Date getCreatedTs() {
        return createdTs;
    }


    public void setCreatedTs(Date createdTs) {
        this.createdTs = createdTs;
    }


    public Date getUpdatedOn() {
        return updatedOn;
    }


    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }


    public boolean isStatus() {
        return status;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getCreatedBY() {
        return createdBY;
    }


    public void setCreatedBY(String createdBY) {
        this.createdBY = createdBY;
    }


    public String getBlogDetails() {
        return blogDetails;
    }


    public void setBlogDetails(String blogDetails) {
        this.blogDetails = blogDetails;
    }


    public Date getBlogDate() {
        return blogDate;
    }


    public void setBlogDate(Date blogDate) {
        this.blogDate = blogDate;
    }


    public Boolean getStatus() {
        return status;
    }


    public void setStatus(Boolean status) {
        this.status = status;
    }


    public String getBlogCategory() {
        return blogCategory;
    }


    public void setBlogCategory(String blogCategory) {
        this.blogCategory = blogCategory;
    }


    public String getSlug() {
        return slug;
    }


    public void setSlug(String slug) {
        this.slug = slug;
    }

    
}
