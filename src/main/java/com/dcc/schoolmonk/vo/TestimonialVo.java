package com.dcc.schoolmonk.vo;

import java.util.ArrayList;
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
@Table(name = "TESTIMONIALS")
@EntityListeners(AuditingEntityListener.class)
public class TestimonialVo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "designation")
    private String designation;
    @Column(name = "institution")
    private String institution;
    @Column(name = "message")
    private String message;

    @Column(name = "active")
    private int isActive;
    @Transient
    List<AttachmentVo> docList = new ArrayList<AttachmentVo>();

    public TestimonialVo(Long id, String name, String designation, String institution, String message, int isActive,
            List<AttachmentVo> docList) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.institution = institution;
        this.message = message;
        this.isActive = isActive;
        this.docList = docList;
    }

    public TestimonialVo() {
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AttachmentVo> getDocList() {
        return docList;
    }

    public void setDocList(List<AttachmentVo> docList) {
        this.docList = docList;
    }

}
