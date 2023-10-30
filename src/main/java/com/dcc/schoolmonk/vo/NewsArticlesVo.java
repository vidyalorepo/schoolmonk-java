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

@Entity
@Table(name = "NEWS_ARTICLES")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_ts" }, allowGetters = true)
public class NewsArticlesVo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "news_date",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date newsDate;

	@Column(name = "subject", unique = true, nullable = false, length = 200)
	private String subject;

	@Column(name = "notice_body", nullable = false, length = 2000)
	private String noticeBody;

	@Column(name = "status")
	private boolean status = false;

	@CreationTimestamp
	@Column(name = "created_ts", nullable = false, updatable = false)
	private Date createdTs;

	@Column(name = "slug", unique = true, nullable = false, length = 255)
	private String newsSlug;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(Date newsDate) {
		this.newsDate = newsDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getNoticeBody() {
		return noticeBody;
	}

	public void setNoticeBody(String noticeBody) {
		this.noticeBody = noticeBody;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public String getNewsSlug() {
		return newsSlug;
	}

	public void setNewsSlug(String newsSlug) {
		this.newsSlug = newsSlug;
	}

	
}
