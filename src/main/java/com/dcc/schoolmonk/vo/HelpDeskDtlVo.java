package com.dcc.schoolmonk.vo;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "HELP_DESK_DTL")
@EntityListeners(AuditingEntityListener.class)
public class HelpDeskDtlVo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long issueId;

    @Column(name = "issuer_name")
    private String issuerName;

    @Column(name = "issuer_id")
    private Long issuerId;

    @Column(name = "issuer_email")
    private String issuerEmail;
    
    @Column(name = "issue_subject")
    private String issueSubject;

    @Column(name = "issue_desc", length = 500)
    private String issueDesc;

    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "issue_state")
    private String issueState;

    @Column(name = "issue_status_updated_by")
    private Long issueStatusUpdatedBy;
    
    @Column(name = "created_on", updatable = false, nullable = false, columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdOn;

    @Column(name = "issue_status_updated_on")
    private Long issueStatusUpdatedOn;
    
    @Column(name="is_agree")
    private Boolean isAgree;


	@javax.persistence.Transient
    List<Long> issueIdList = new ArrayList<Long>();

    @javax.persistence.Transient
    List<AttachmentVo> docList = new ArrayList<AttachmentVo>();

    public Long getIssueStatusUpdatedBy() {
        return issueStatusUpdatedBy;
    }

    public void setIssueStatusUpdatedBy(Long issueStatusUpdatedBy) {
        this.issueStatusUpdatedBy = issueStatusUpdatedBy;
    }

    public List<AttachmentVo> getDocList() {
        return docList;
    }

    public void setDocList(List<AttachmentVo> docList) {
        this.docList = docList;
    }

    public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public Long getIssueStatusUpdatedOn() {
        return issueStatusUpdatedOn;
    }

    public void setIssueStatusUpdatedOn(Long issueStatusUpdatedOn) {
        this.issueStatusUpdatedOn = issueStatusUpdatedOn;
    }

    public List<Long> getIssueIdList() {
        return issueIdList;
    }

    public void setIssueIdList(List<Long> issueIdList) {
        this.issueIdList = issueIdList;
    }

    public HelpDeskDtlVo() {
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public Long getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(Long issuerId) {
        this.issuerId = issuerId;
    }

    public String getIssuerEmail() {
        return issuerEmail;
    }

    public void setIssuerEmail(String issuerEmail) {
        this.issuerEmail = issuerEmail;
    }

    public String getIssueDesc() {
        return issueDesc;
    }

    public void setIssueDesc(String issueDesc) {
        this.issueDesc = issueDesc;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getIssueState() {
        return issueState;
    }

    public void setIssueState(String issueState) {
        this.issueState = issueState;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    
    public Boolean getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(Boolean isAgree) {
        this.isAgree = isAgree;
    } 
    
    public HelpDeskDtlVo(Long issueId, String issuerFirstName, String issuerLastName, Long issuerId, String issuerEmail,
            String issueSubject, String issueDesc, String ticketId, String issueState, Date createdOn,
            Long issueStatusUpdatedOn, List<Long> issueIdList, List<AttachmentVo> docList, Long issueStatusUpdatedBy) {
        this.issueId = issueId;
        this.issuerName = issuerFirstName;
//        this.issuerLastName = issuerLastName;
        this.issuerId = issuerId;
        this.issuerEmail = issuerEmail;
        this.issueSubject = issueSubject;
        this.issueDesc = issueDesc;
        this.ticketId = ticketId;
        this.issueState = issueState;
        this.createdOn = createdOn;
        this.issueStatusUpdatedOn = issueStatusUpdatedOn;
        this.issueIdList = issueIdList;
        this.docList = docList;
        this.issueStatusUpdatedBy = issueStatusUpdatedBy;
    }

    public String getIssueSubject() {
        return issueSubject;
    }

    public void setIssueSubject(String issueSubject) {
        this.issueSubject = issueSubject;
    }

    @Override
    public String toString() {
        return "HelpDeskDtlVo [issueId=" + issueId + ", issuerFirstName=" + issuerName + ", issuerLastName="
                + ", issuerId=" + issuerId + ", issuerEmail=" + issuerEmail + ", issueSubject="
                + issueSubject + ", issueDesc=" + issueDesc + ", ticketId=" + ticketId + ", issueState=" + issueState
                + ", createdOn=" + createdOn + ", issueStatusUpdatedOn=" + issueStatusUpdatedOn + ", issueIdList="
                + issueIdList + ", docList=" + docList + ", issueStatusUpdatedBy=" + issueStatusUpdatedBy + "]";
    }
   
}
